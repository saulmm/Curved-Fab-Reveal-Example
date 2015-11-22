/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import saulmm.test.media.MediaFragment;

public class MainActivity extends AppCompatActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initMediaFragment();
	}

	private void initMediaFragment() {
		getSupportFragmentManager().beginTransaction()
			.add(android.R.id.content, MediaFragment.newInstance())
			.commit();
	}
}
