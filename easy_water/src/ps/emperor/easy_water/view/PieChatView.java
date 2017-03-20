package ps.emperor.easy_water.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PieChatView extends View {
	private Paint paint;
	private RectF rectF;
	private int sweepAngle;
	private int wight, hight;
	private int color;
	private boolean isfind = false;

	public PieChatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		color = Color.RED;
		paint.setAntiAlias(true);// ����� �����
	}

	public void setAngle(final int angle) {
		isfind = true;
		new Thread() {
			@Override
			public void run() {
				while (isfind) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					sweepAngle += 10;
					if (sweepAngle >= angle) {
						sweepAngle = angle;
						isfind = false;
					}
					postInvalidate();
				}
			}
		}.start();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		wight = MeasureSpec.getSize(widthMeasureSpec);
		hight = MeasureSpec.getSize(heightMeasureSpec);
		rectF = new RectF(0, 0, wight, hight);
		setMeasuredDimension(wight, hight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(color);
		canvas.drawArc(rectF, -90, 360, true, paint);
		paint.setColor(Color.GREEN);
		canvas.drawArc(rectF, -90, sweepAngle, true, paint);
	}
}
