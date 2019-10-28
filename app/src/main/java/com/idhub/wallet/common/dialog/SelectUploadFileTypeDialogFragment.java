package com.idhub.wallet.common.dialog;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
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

import com.idhub.base.App;
import com.idhub.wallet.R;
import com.idhub.wallet.common.zxinglib.util.TimeUtil;
import com.idhub.wallet.common.zxinglib.widget.crop.Crop;
import com.idhub.wallet.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
            } else {
                FILE_PATH = getRealFilePath(data.getData());
                int i = readPictureDegree(FILE_PATH);
                Log.e("LYW", "onActivityResult: " + i );
                if (i != 0) {
                    getBitmapToPath(FILE_PATH);
                }
            }

            if (mUploadFileTypeDialogFragmentListener != null)
                mUploadFileTypeDialogFragmentListener.selectFileResult(FILE_PATH, mSource);
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA && resultCode == Activity.RESULT_OK) {
            if (fileUri != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        InputStream inputStream = getContext().getContentResolver().openInputStream(fileUri);
                        inputStreamToFile(inputStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    int i = readPictureDegree(FILE_PATH);
                    Log.e("LYW", "onActivityResult: " + i );
                    if (i != 0) {
                        getBitmapToPath(FILE_PATH);
                    }
                } else {
                    beginCrop(fileUri);
                }
            }
            if (mUploadFileTypeDialogFragmentListener != null)
                mUploadFileTypeDialogFragmentListener.selectFileResult(FILE_PATH, mSource);
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
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
        if (!mImagesDir.exists()) {
            mImagesDir.mkdirs();
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
        if (bitmap != null) {
            int i = readPictureDegree(path);
            bitmap = rotateBitmap(bitmap, i);
            bitmapToFile(imageZoom());
        }
//        bitmap = rotateBitmap(bitmap, readPictureDegree(Constant.FILE_PATH));
    }

    protected void bitmapRecycle() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }

    private void inputStreamToFile(InputStream inputStream) {
        File file = new File(mImagesDir, mImageName);
        try {
            int index;
            byte[] bytes = new byte[1024];
            FileOutputStream downloadFile = new FileOutputStream(file);
            while ((index = inputStream.read(bytes)) != -1) {
                downloadFile.write(bytes, 0, index);
                downloadFile.flush();
            }
            downloadFile.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FILE_PATH = file.getPath();
    }

    private void bitmapToFile(ByteArrayOutputStream byteArrayOutputStream) {
        File file = new File(mImagesDir, mImageName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(byteArrayOutputStream.toByteArray());
                fos.flush();
                fos.close();
                byteArrayOutputStream.close();
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

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    private int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return degree;
        }
        return degree;
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     *
     * @param bitmap  原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    private Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }


    private String getRealFilePath(Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
            // DocumentProvider
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(getContext(), uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(getContext(), contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(getContext(), contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(getContext(), uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return "";
    }


    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public interface SelectUploadFileTypeDialogFragmentListener {
        void selectFileResult(String path, String source);
    }
}
