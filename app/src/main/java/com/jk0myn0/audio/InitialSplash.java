package com.jk0myn0.audio;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jk0myn0.audio.helper.MyPreferenceManager;
import com.jk0myn0.audio.knobify.MainActivity;
import com.jk0myn0.audio.slider.FlowAnimation;

import static com.jk0myn0.audio.R.layout.splash_initial;

/**
 * Created by Root on 03/01/2016.
 */
public class InitialSplash extends AppCompatActivity {

    MyPreferenceManager session;
    boolean soft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings_page_1, false);

/*        if (Build.VERSION.SDK_INT > 16) {
            View decorView = getWindow().getDecorView();
// Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }*/

        soft = ViewConfiguration.get(this).hasPermanentMenuKey();

        setContentView(splash_initial);
        session = new MyPreferenceManager(getApplicationContext());
        startHeavyProcessing();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void startHeavyProcessing() {
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            //some heavy processing resulting in a Data String
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "whatever result you have";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent i;
            if (session.isLoggedIn()) {
                // User is already logged in. Take him to main activity
                i = new Intent(InitialSplash.this, MainActivity.class);
                i.putExtra("data", result);
            } else {
                // User still has to login
                i = new Intent(InitialSplash.this, FlowAnimation.class);
                i.putExtra("data", result);
            }
            startActivity(i);
            finish();
        }

        @Override
        protected void onPreExecute() {
            StartAnimations();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        private void StartAnimations() {
            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
            anim.reset();
            LinearLayout l = (LinearLayout) findViewById(R.id.splash_layout);
            l.clearAnimation();
            l.startAnimation(anim);

            anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_y_down_to_up);
            anim.reset();

            ImageView iv = (ImageView) findViewById(R.id.splash);
            Drawable drawable;

            // determine if there's soft keyboard to adjust splash image's size
            if (soft){
                drawable = getDrawable(R.drawable.splash_initial_fg_no_soft);
            } else {
                drawable = getDrawable(R.drawable.splash_initial_fg_soft);
            }
            iv.setImageDrawable(drawable);

            iv.clearAnimation();
            iv.startAnimation(anim);
        }
    }
}
