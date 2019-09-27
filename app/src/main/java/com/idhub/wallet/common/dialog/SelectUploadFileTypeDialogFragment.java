package com.idhub.wallet.common.dialog;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.FileProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.idhub.wallet.App;
import com.idhub.wallet.R;
import com.idhub.wallet.common.zxinglib.util.SDcardUtil;
import com.idhub.wallet.common.zxinglib.util.TimeUtil;
import com.idhub.wallet.common.zxinglib.widget.crop.Crop;
import com.idhub.wallet.utils.LogUtils;
import com.idhub.wallet.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.idhub.wallet.common.zxinglib.widget.crop.Crop.REQUEST_PICK;

public class SelectUploadFileTypeDialogFragment extends DialogFragment implements View.OnClickListener {

    private File mTempDir = new File(new File(Environment.getExternalStorageDirectory().getAbsolutePath()), "idhub/temps");
    private File mImagesDir = new File(new File(Environment.getExternalStorageDirectory().getAbsolutePath()), "idhub/images");
    private String mImageName = TimeUtil.getTimeYYYYMMDDHHMMSS() + ".jpg";
    private Uri fileUri;
    public static final int REQUEST_CODE_CAPTURE_CAMEIA = 1711;
    public static final int REQUEST_SELECT_FILE_CODE = 1928;
    protected boolean isCrop = false; //裁剪要处理，会有两次返回结果
    protected String headIcon;
    protected boolean isCircleCrop;
    private String FILE_PATH;
    private Bitmap bitmap;
    private SelectUploadFileTypeDialogFragmentListener mUploadFileTypeDialogFragmentListener;
    private String mSource;
    public static final String SOURCE_USER_BASIC_INFO = "user_basic_info";

    public static SelectUploadFileTypeDialogFragment getInstance(String surce) {
        SelectUploadFileTypeDialogFragment uploadFileTypeDialogFragment = new SelectUploadFileTypeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("source", surce);
        uploadFileTypeDialogFragment.setArguments(bundle);
        return uploadFileTypeDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setUploadFileTypeDialogFragmentListeneristener(SelectUploadFileTypeDialogFragmentListener listeneristener) {
        mUploadFileTypeDialogFragmentListener = listeneristener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mSource = bundle.getString("source");
        }
        View view = inflater.inflate(R.layout.wallet_popup_window_upload_file, container, false);
        Window window = getDialog().getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.findViewById(R.id.tv_take_photo).setOnClickListener(this);
        view.findViewById(R.id.tv_picture).setOnClickListener(this);
        View fileView = view.findViewById(R.id.tv_file);
        fileView.setOnClickListener(this);
        if (SOURCE_USER_BASIC_INFO.equals(mSource)) {
            fileView.setVisibility(View.GONE);
            mImagesDir = App.getInstance().getFilesDir();
            mImageName = "user_head.jpg";
        }
        init();
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_take_photo:
                getImageFromCamera();
                break;
            case R.id.tv_picture:
                Crop.pickImage(this);
                break;
            case R.id.tv_file:
                selectFile();
                break;
        }
    }

    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf").addCategory(Intent.CATEGORY_OPENABLE);
        if (getContext().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                startActivityForResult(Intent.createChooser(intent, "Choose File"), REQUEST_SELECT_FILE_CODE);
            } catch (ActivityNotFoundException e) {
                ToastUtils.showShortToast(e.getMessage());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK && resultCode == Activity.RESULT_OK) {
            if (SOURCE_USER_BASIC_INFO.equals(mSource)) {
                beginCrop(data.getData());
            }else {
                FILE_PATH = getRealFilePath(data.getData());
            }
            if (mUploadFileTypeDialogFragmentListener != null)
                mUploadFileTypeDialogFragmentListener.selectFileResult(FILE_PATH, mSource);
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA &&  resultCode == Activity.RESULT_OK) {
            if (fileUri != null) {
                beginCrop(fileUri);
            }
            if (mUploadFileTypeDialogFragmentListener != null)
                mUploadFileTypeDialogFragmentListener.selectFileResult(FILE_PATH, mSource);
        } else if (requestCode == Crop.REQUEST_CROP &&  resultCode == Activity.RESULT_OK) {
            getBitmapToPath(headIcon);
            if (mUploadFileTypeDialogFragmentListener != null)
                mUploadFileTypeDialogFragmentListener.selectFileResult(FILE_PATH, mSource);
        } else if (requestCode == REQUEST_SELECT_FILE_CODE && resultCode == Activity.RESULT_OK) {
            String path = getRealFilePath(data.getData());
            if (mUploadFileTypeDialogFragmentListener != null)
                mUploadFileTypeDialogFragmentListener.selectFileResult(path, mSource);
        }
    }

    private void init() {
        if (!mTempDir.exists()) {
            mTempDir.mkdirs();
        }
    }

    /**
     * 把图片圆形化
     *
     * @param source
     */
    private void beginCrop(Uri source) {
        if (isCrop) {
            String fileName = String.valueOf(System.currentTimeMillis());
            File cropFile = new File(mTempDir, fileName);
            Uri outputUri = Uri.fromFile(cropFile);
            headIcon = outputUri.getPath();
            new Crop(source).output(outputUri).setCropType(isCircleCrop)
                    .start(getContext(), this);
        } else {
            String path = getRealFilePath(source);
            getBitmapToPath(path);
            //图片处理结果
//            dealImageResult();
        }
    }

    public void getBitmapToPath(String path) {
        bitmapRecycle();
        bitmap = getLoacalBitmap(path);
        if (bitmap != null)
            bitmapToFile(imageZoom());
//        bitmap = rotateBitmap(bitmap, readPictureDegree(Constant.FILE_PATH));
    }

    protected void bitmapRecycle() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }

    private void bitmapToFile(ByteArrayOutputStream byteArrayOutputStream) {
        File file = new File(mImagesDir, mImageName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(byteArrayOutputStream.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FILE_PATH = file.getPath();
    }

    private ByteArrayOutputStream imageZoom() {
        int size = getBitmapSize(bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Matrix matrix = new Matrix();
        if (size > 1048576) {
            matrix.postScale(0.5f, 0.5f); // 长和宽放大缩小的比例
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 50是压缩率，表示压缩50%;
            // 如果不压缩是100，表示压缩率为0
        } else if (1048576 > size && size > 524288) {
            matrix.postScale(0.75f, 0.75f);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        } else if (524288 > size && size > 51400) {
            matrix.postScale(0.85f, 0.85f);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, baos);
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, baos);
        }

        return baos;
    }

    private int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
            // 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    private Bitmap getLoacalBitmap(String url) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(url);
            bitmap = BitmapFactory.decodeStream(fis); // 把流转化为Bitmap图片
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void getImageFromCamera() {
        // create Intent to take a picture and return control to the calling
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = String.valueOf(System.currentTimeMillis());
        File cropFile = new File(mTempDir, fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(App.getInstance(), App.getInstance().getPackageName() + ".fileprovider", cropFile);
        } else {
            fileUri = Uri.fromFile(cropFile);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
        // start the image capture Intent
        startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
    }

    private String getRealFilePath(Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String path = uri.getPath();
            if (path.contains("/camera-photos/"))
                data = uri.getPath().replace("/camera-photos/", SDcardUtil.getSdcardPath());//"/storage/emulated/0/"
            else {
                Cursor cursor = null;
                try {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    cursor = getContext().getContentResolver().query(uri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    data = cursor.getString(column_index);
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }
        return data;
    }

    public interface SelectUploadFileTypeDialogFragmentListener {
        void selectFileResult(String path, String source);
    }
}
