package net.pejici.multirotaryknob;

import android.content.Context;
import android.drm.DrmStore.Action;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Path.FillType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MultiRotaryKnobView extends View {
	
	private Paint knobColour = null;
	private Paint marksColour = null;
	protected float value = 0.0f;

	private float valueOld1 = 0.0f;
	
	private int depth = 4;
	private int power = 2;

	private static boolean arcs = true;

	private int selectionDepth = -1;
	private float selectionStartAngle = 0.0f;
	private float selectionStartValue = 0.0f;

	public MultiRotaryKnobView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		knobColour = new Paint(Paint.ANTI_ALIAS_FLAG);
		knobColour.setARGB(255, 220, 180, 40);
		marksColour = new Paint(Paint.ANTI_ALIAS_FLAG);
		marksColour.setStyle(Style.FILL);
		marksColour.setStrokeWidth(1.f);
		marksColour.setStrokeJoin(Join.BEVEL);
		marksColour.setARGB(255, 50, 25, 16);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		float w = this.getWidth();
		float h = this.getHeight();
		float mx = w/2;
		float my = h/2;
		float rWmin = w/depth;
		float rHmin = h/2;
		float r = Math.min(rWmin, rHmin);
		int d = 0;
		float velocity = (value - valueOld1);
		for (d = depth; d > 0; d--) {
			Paint p = new Paint(knobColour);
			int colour = p.getColor();
			int red = (colour & 0x00FF0000) >> 16;
			red -= d * 20;
			colour = (colour & 0xFF00FFFF) | ((red << 16) & 0x00FF0000);
			p.setColor(colour);
			float R = r*d;
			float Rsub = r*d-r;
			if (d == 1) {
				Rsub = (float) (r*(d-0.75));
			}
			canvas.drawCircle(0, my, R, p);
			Path arc = null;
			float radVelocity = (float) (Math.abs(velocity) * Math.pow(power, d));
			radVelocity = Math.min(10f/360f*2f*(float)Math.PI, radVelocity);
			radVelocity = Math.max(0.6f/Rsub, radVelocity); // minimum size
			radVelocity = radVelocity/2;
			float alpha = (float) ((0.4f/360.f * 2 * Math.PI / radVelocity));
			alpha = Math.min(1.0f, alpha);
			if (arcs) {
				arc = new Path();
				float lx1 = (float) ((R-10)*Math.cos(-radVelocity));
				float ly1 = (float) ((R-10)*Math.sin(-radVelocity));
				float lxSub1 = (float) ((Rsub+10)*Math.cos(-radVelocity));
				float lySub1 = (float) ((Rsub+10)*Math.sin(-radVelocity));

				float lx2 = (float) ((R-10)*Math.cos(radVelocity));
				float ly2 = (float) ((R-10)*Math.sin(radVelocity));
				float lxSub2 = (float) ((Rsub+10)*Math.cos(radVelocity));
				float lySub2 = (float) ((Rsub+10)*Math.sin(radVelocity));
				arc.moveTo(lxSub1, lySub1);
				arc.lineTo(lx1, ly1);
				arc.lineTo(lx2, ly2);
				arc.lineTo(lxSub2, lySub2);
				arc.close();
				arc.setFillType(FillType.EVEN_ODD);
			}
			canvas.save();
			canvas.rotate(value * 360.0f * ((float) Math.pow(power, d)) / 2 / (float) Math.PI, 0, my);
			Paint marksC = new Paint(marksColour);
			marksC.setAlpha((int)(alpha * 255));
			for (float a = 0; a < 359.8; a += 10f) {
				canvas.save();
				canvas.rotate(a, 0, my);
				canvas.translate(0, my);
				if (arcs) {
					canvas.drawPath(arc, marksC);
				}
				else {
					canvas.drawLine(Rsub, 0, R, 0, marksC);
				}
				canvas.restore();
			}
			canvas.restore();
		}
		if (value != valueOld1) {
			valueOld1 = value;
			this.invalidate();
		}
	}
	
	float angleFrom(MotionEvent event) {
		return (float) (Math.atan2(event.getY()-this.getHeight()/2, event.getX()));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			selectionDepth = 0;
			selectionStartValue = value;
			selectionStartAngle = angleFrom(event);
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			float angle = angleFrom(event);
			float aDiff = angle - selectionStartAngle;
			// TODO: adjust for selection Depth
			value = selectionStartValue + aDiff;
			this.invalidate();
			break;
		}
		return true;
	}

}
