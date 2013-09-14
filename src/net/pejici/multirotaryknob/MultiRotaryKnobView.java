package net.pejici.multirotaryknob;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MultiRotaryKnobView extends View {
	
	private Paint knobColour = null;
	private Paint marksColour = null;
	private float value = 0.0f;
	
	private int depth = 3;
	private int power = 4;

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
			for (float a = 0; a < 359.8; a += 3.6) {
				float rad = (float) (a/360.0*2*Math.PI);
				rad += value * Math.pow(power, d);
				float lx = (float) ((R-10)*Math.cos(rad));
				float ly = my+(float) ((R-10)*Math.sin(rad));
				float lxSub = (float) ((Rsub+10)*Math.cos(rad));
				float lySub = my+(float) ((Rsub+10)*Math.sin(rad));
				canvas.drawLine(lxSub, lySub, lx, ly, marksColour);
			}
		}
		value += Math.pow(power, -depth)/60;
		this.invalidate();
	}

}
