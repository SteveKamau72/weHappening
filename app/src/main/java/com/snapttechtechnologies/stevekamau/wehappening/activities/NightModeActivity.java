package com.snapttechtechnologies.stevekamau.wehappening.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;
import com.snapttechtechnologies.stevekamau.wehappening.R;

public class NightModeActivity extends AppCompatActivity {
    final String PREF_NAME = "prefrences";
    public SharedPreferences spref;
    public String STATE = "state_value";
    ImageView img;
    boolean on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_mode);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img = (ImageView) findViewById(R.id.owl);
        final View one = findViewById(R.id.lay1);
        final View two = findViewById(R.id.toolbar);
        final View three = findViewById(R.id.txt);
        final View four = findViewById(R.id.owl);

        animateSampleOne(one, two, three, four);
    }

    private void animateSampleOne(View one, View two, View three, View four) {
        final PropertyAction headerAction = PropertyAction.newPropertyAction(one).interpolator(new DecelerateInterpolator()).translationY(-200).duration(750).alpha(0.4f).build();
        final PropertyAction headerAction2 = PropertyAction.newPropertyAction(three).interpolator(new DecelerateInterpolator()).translationY(-200).duration(750).alpha(0.4f).build();
        final PropertyAction headerAction3 = PropertyAction.newPropertyAction(four).interpolator(new DecelerateInterpolator()).translationY(-200).duration(750).alpha(0.4f).build();
        final PropertyAction headerActiontwo = PropertyAction.newPropertyAction(four).interpolator(new DecelerateInterpolator()).translationY(-200).duration(750).alpha(0.4f).build();

        Player.init().
                animate(headerAction).
                then().
                animate(headerAction2).
                then().
                animate(headerAction3).
                then().
                play();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_night_mode, menu);
        MenuItem item = menu.findItem(R.id.myswitch);
        MenuItemCompat.setActionView(item, R.layout.my_switch);
        View view = MenuItemCompat.getActionView(item);

        final ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.toggleButton1);

        spref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        on = spref.getBoolean("On", false);
        if (on) {
            toggleButton.setChecked(true);
            img.setImageDrawable(getResources().getDrawable(R.drawable.ic_owl_awake));

        } else {
            toggleButton.setChecked(false);
            img.setImageDrawable(getResources().getDrawable(R.drawable.ic_owl));
        }


        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                on = ((ToggleButton) v).isChecked();
                if (on) {
                    Toast toast = Toast.makeText(getApplicationContext(), "On : Night Mode is Enabled", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    SharedPreferences.Editor editor = spref.edit();
                    editor.putBoolean("On", true);
                    editor.commit();
                    img.setImageDrawable(getResources().getDrawable(R.drawable.ic_owl_awake));
                    boolean test_on = spref.getBoolean("On", true);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Off : Night Mode is Disabled", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();                    SharedPreferences.Editor editor = spref.edit();
                    editor.putBoolean("On", false); // value to store
                    editor.commit();
                    boolean test_on = spref.getBoolean("On", true);
                    img.setImageDrawable(getResources().getDrawable(R.drawable.ic_owl));
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
