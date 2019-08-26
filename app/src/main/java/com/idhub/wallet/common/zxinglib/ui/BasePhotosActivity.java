package com.idhub.wallet.common.zxinglib.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;


import com.idhub.wallet.common.zxinglib.util.SDcardUtil;
import com.idhub.wallet.common.zxinglib.util.TimeUtil;
import com.idhub.wallet.common.zxinglib.widget.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BasePhotosActivity extends Activity {
    private             File    mTempDir;
    protected           String  headIcon;
    protected           boolean isTakePhoto                 = false;
    protected           boolean isCircleCrop;
    protected           boolean isZing                      = false;
    protected           boolean isCrop                      = false;
    protected           String  zingPath;
    private             Uri     fileUri;
    public              Bitmap  bitmap;
    public static final int     REQUEST_CODE_CAPTURE_CAMEIA = 1711;
    public static final int     REQUEST_CODE_CAMERA         = 102;
    public static       String  FILE_PATH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }


    private void init() {
        mTempDir = new File(Environment.getExternalStorageDirectory(),
                getApplicationContext().getPackageName());
        if (!mTempDir.exists()) {
            mTempDir.mkdirs();
        }
    }

    public void getBitmapToPath(String path) {
        bitmapRecycle();
        bitmap = getLoacalBitmap(path);
        if (bitmap != null)
            bitmapToFile(imageZoom());
//        bitmap = rotateBitmap(bitmap, readPictureDegree(Constant.FILE_PATH));
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

    private void bitmapToFile(ByteArrayOutputStream byteArrayOutputStream) {
        File file = new File(Environment.getExternalStorageDirectory(), TimeUtil.getTimeYYYYMMDDHHMMSS() + ".jpg");
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
     * 手机拍照
     */
    protected void getImageFromCamera() {
        // create Intent to take a picture and return control to the calling
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = String.valueOf(System.currentTimeMillis());
        File cropFile = new File(mTempDir, fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", cropFile);
        } else {
            fileUri = Uri.fromFile(cropFile);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
        // start the image capture Intent
        startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
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

    @SuppressWarnings("resource")
    protected byte[] getLoacalImageFile(String url) {
        FileInputStream fis = null;
        byte[] byt = null;
        try {
            fis = new FileInputStream(url);
            byt = new byte[fis.available()];
            fis.read(byt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return byt;
    }

    protected void bitmapRecycle() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
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
                    .start(this);
        } else {
            getBitmapToPath(getRealFilePath(source));
            //图片处理结果
            dealImageResult();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Crop.REQUEST_PICK) {
                if (isZing) {
                    zingPath = getRealFilePath(result.getData());
                    dealImageResult();
                } else {
                    beginCrop(result.getData());
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                getBitmapToPath(headIcon);
                //图片处理结果
                dealImageResult();
            } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
                if (fileUri != null) {
                    beginCrop(fileUri);
                }
            }
        } else {
            dealCancelResult();
        }
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
                    cursor = getContentResolver().query(uri, proj, null, null, null);
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

    public void dealImageResult() {

    }

    public void dealCancelResult() {

    }

    public void takePhoto() {
        getImageFromCamera();
        isTakePhoto = false;
    }

    public void phoneAlbum() {
        Crop.pickImage(this);
    }

}
