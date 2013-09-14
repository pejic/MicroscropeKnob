package net.pejici.multirotaryknob;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MultiRotaryKnobDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_rotary_knob_demo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.multi_rotary_knob_demo, menu);
		return true;
	}

}
