package com.snapttechtechnologies.stevekamau.wehappening.activities;

import android.app.Activity;
import android.os.Bundle;

import com.snapttechtechnologies.stevekamau.wehappening.R;


/**
 * This is the activity that is started when the user presses the notification in the status bar
 * @author paul.blundell
 */
public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
	}
	
}
