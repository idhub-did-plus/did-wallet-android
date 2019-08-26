package com.idhub.wallet.common.zxinglib.widget.crop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.View;

public class CircleHighlightView extends HighlightView {

	public CircleHighlightView(View context) {
		super(context);
	}

	@Override
	protected void draw(Canvas canvas) {
		canvas.save();
		Path path = new Path();
		outlinePaint.setStrokeWidth(outlineWidth);
		if (!hasFocus()) {// 没焦点是，直接画一个黑色的矩形框
			outlinePaint.setColor(Color.BLACK);
			canvas.drawRect(drawRect, outlinePaint);
		} else {
			Rect viewDrawingRect = new Rect();
			viewContext.getDrawingRect(viewDrawingRect);

			// 已裁剪框drawRect，算出圆的半径
			float radius = (drawRect.right - drawRect.left) / 2;
			// 添加一个圆形
			path.addCircle(drawRect.left + radius, drawRect.top + radius,
					radius, Direction.CW);
			outlinePaint.setColor(highlightColor);

			// 裁剪画布，path之外的区域，以outsidePaint填充
			canvas.clipPath(path, Region.Op.DIFFERENCE);
			canvas.drawRect(viewDrawingRect, outsidePaint);

			canvas.restore();
			// 绘制圆上高亮线，这里outlinePaint定义的Paint.Style.STROKE：表示只绘制几何图形的轮廓。
			canvas.drawPath(path, outlinePaint);

			// 当modifyMode为grow时，绘制handles,也就是那四个小圆
			if (handleMode == HandleMode.Always
					|| (handleMode == HandleMode.Changing && modifyMode == ModifyMode.Grow)) {
				drawHandles(canvas);
			}
		}
	}

	// Determines which edges are hit by touching at (x, y)
	@Override
	public int getHit(float x, float y) {
		return getHitOnCircle(x, y);
	}

	@Override
	void handleMotion(int edge, float dx, float dy) {
		Rect r = computeLayout();
		if (edge == MOVE) {
			// Convert to image space before sending to moveBy()
			moveBy(dx * (cropRect.width() / r.width()), dy
					* (cropRect.height() / r.height()));
		} else {
			if (((GROW_LEFT_EDGE | GROW_RIGHT_EDGE) & edge) == 0) {
				dx = 0;
			}

			if (((GROW_TOP_EDGE | GROW_BOTTOM_EDGE) & edge) == 0) {
				dy = 0;
			}
			// 那个方向变化大就取其做参考;默认参考dx
			if (Math.abs(dx) < Math.abs(dy)) {
				dx = 0.0f;// dx设置为0，growBy就会以dy参考，按照1:1计算出dx
			}
			float xDelta = dx * (cropRect.width() / r.width());
			float yDelta = dy * (cropRect.height() / r.height());
			growBy((((edge & GROW_LEFT_EDGE) != 0) ? -1 : 1) * xDelta,
					(((edge & GROW_TOP_EDGE) != 0) ? -1 : 1) * yDelta);
		}
	}

	/**
	 * 根据x,y坐标，计算其与圆的关系（圆上、圆内、圆外）
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	private int getHitOnCircle(float x, float y) {
		Rect r = computeLayout();
		int retval = GROW_NONE;
		final float hysteresis = 20F;
		int radius = (r.right - r.left) / 2;

		int centerX = r.left + radius;
		int centerY = r.top + radius;

		// 判断触摸位置是否在圆上
		float ret = (x - centerX) * (x - centerX) + (y - centerY)
				* (y - centerY);
		double rRadius = Math.sqrt(ret);
		double gap = Math.abs(rRadius - radius);

		if (gap <= hysteresis) {// 圆上。这里由于是继承至HighlightView（绘制矩形框的）来处理，所以模拟返回了左右上下，而非纯圆上，亲测可用。你也可以自定义。
			if (x < centerX) {// left
				retval |= GROW_LEFT_EDGE;
			} else {
				retval |= GROW_RIGHT_EDGE;
			}

			if (y < centerY) {// up
				retval |= GROW_TOP_EDGE;
			} else {
				retval |= GROW_BOTTOM_EDGE;
			}
		} else if (rRadius > radius) {// outside
			retval = GROW_NONE;
		} else if (rRadius < radius) {// inside，圆内就执行move
			retval = MOVE;
		}

		return retval;
	}

}
