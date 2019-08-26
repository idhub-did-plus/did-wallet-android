package com.idhub.wallet.common.zxinglib.widget.crop.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author wmli
 * @time:
 */
public class GraphicsUtil {

    public static Bitmap getOvalBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap( bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas( output);

        final int color = 0xffff0000;
        final Paint paint = new Paint();
        final Rect rect = new Rect( 0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF( rect);

        paint.setAntiAlias( true);
        paint.setDither( true);
        paint.setFilterBitmap( true);
        canvas.drawARGB( 0, 0, 0, 0);
        paint.setColor( color);
        canvas.drawOval( rectF, paint);

        paint.setColor( Color.BLUE);
        paint.setStyle( Paint.Style.STROKE);
        paint.setStrokeWidth( (float)4);
        paint.setXfermode( new PorterDuffXfermode( Mode.SRC_IN));
        canvas.drawBitmap( bitmap, rect, rect, paint);

        return output;
    }

    /**
     * @param bitmap src图片
     * @return
     */
    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap( bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas( output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect( 0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias( true);
        paint.setFilterBitmap( true);
        paint.setDither( true);
        canvas.drawARGB( 0, 0, 0, 0);
        paint.setColor( color);
        //在画布上绘制一个圆
        canvas.drawCircle( bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode( new PorterDuffXfermode( Mode.SRC_IN));
        canvas.drawBitmap( bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 125;
        int targetHeight = 125;
        Bitmap targetBitmap = Bitmap.createBitmap( targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas( targetBitmap);
        Path path = new Path();
        path.addCircle( ((float)targetWidth - 1) / 2, ((float)targetHeight - 1) / 2,
                (Math.min( ((float)targetWidth), ((float)targetHeight)) / 2), Path.Direction.CCW);

        canvas.clipPath( path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap( sourceBitmap, new Rect( 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), new Rect( 0, 0,
                targetWidth, targetHeight), null);
        return targetBitmap;
    }

}