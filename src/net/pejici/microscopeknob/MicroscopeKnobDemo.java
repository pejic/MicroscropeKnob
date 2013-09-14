/**
 * This file is part of Microscope Knob.
 *
 * Microscope Knob is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * Microscope Knob is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Microscope Knob.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.pejici.microscopeknob;

import net.pejici.multirotaryknob.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MicroscopeKnobDemo extends Activity {

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
