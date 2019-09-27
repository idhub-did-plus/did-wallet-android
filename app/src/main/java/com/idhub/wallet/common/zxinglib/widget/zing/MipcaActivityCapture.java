package com.idhub.wallet.common.zxinglib.widget.zing;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.idhub.wallet.R;
import com.idhub.wallet.common.zxinglib.ui.BasePhotosActivity;
import com.idhub.wallet.utils.LogUtils;
import com.idhub.wallet.utils.ToastUtils;

import java.io.IOException;
import java.util.Vector;

/**
 * Initial the camera
 *
 * @author wmli
 */
public class MipcaActivityCapture extends BasePhotosActivity implements Callback {

    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    public InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    public static String SCAN_RESULT;
    private Camera camera;
    private CameraManager cameraManager;
    private SurfaceView previewView;
    private ViewfinderView viewfinderView;
    public TextView tv_titile, right_txt;

    public static void startAction(Activity context, int requestCode) {
        Intent intent = new Intent(context, MipcaActivityCapture.class);
        context.startActivityForResult(intent, requestCode);
    }

    public static void startAction(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), MipcaActivityCapture.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                ToastUtils.showShortToast(getString(R.string.scan_camera_permission));
                finish();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        100);
            }
        }
    }

    private void init() {
        SCAN_RESULT = null;
        setContentView(R.layout.activity_capture);

        previewView = findViewById(R.id.preview_view);
        viewfinderView = findViewById(R.id.viewfinder_view);
        CameraManager.init(this);
        cameraManager = CameraManager.get();
        hasSurface = false;
        isZing = true;
        inactivityTimer = new InactivityTimer(this);
        tv_titile = findViewById(R.id.tv_titile);
        right_txt = findViewById(R.id.right_txt);
        View captureView = findViewById(R.id.capture_view);
        captureView.setSelected(true);
        captureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFlash(v);
            }
        });
        findViewById(R.id.capture_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.capture_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneAlbum();
            }
        });
    }


    /**
     * 闪光灯开关
     */
    private void switchFlash(View view) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            boolean isOff = parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF);
            if (isOff) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            } else {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            }
            camera.setParameters(parameters);
            view.setSelected(!isOff);
        } catch (Exception e) {
            e.printStackTrace();
            finishFlashUtils();
        }
    }

    private void finishFlashUtils() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
        camera = null;
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = previewView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }


    /**
     * 扫描成功
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        SCAN_RESULT = result != null ? result.getText() : "";
        LogUtils.e("did", "handleDecode:qrcode " + SCAN_RESULT );
        Intent intent = new Intent();
        intent.putExtra("qrcode", SCAN_RESULT);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            cameraManager.openDriver(surfaceHolder);
            camera = CameraManager.getCamera();
        } catch (Exception ioe) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
        if (camera != null) {
            cameraManager.stopPreview();
        }
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    public void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
    }

    /**
     * 图片识别二维码
     */
    @Override
    public void dealImageResult() {
        super.dealImageResult();
        new Thread() {
            @Override
            public void run() {
                super.run();
                final Result result = QRCodeDecoder.syncDecodeQRCode(zingPath);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handleDecode(result, null);
                    }
                });
            }
        }.start();
    }

}