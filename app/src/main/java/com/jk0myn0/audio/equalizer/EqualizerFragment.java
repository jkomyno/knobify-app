package com.jk0myn0.audio.equalizer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jk0myn0.audio.R;
import com.jk0myn0.audio.ui.VerticalSeekBar;

/**
 * A placeholder fragment containing a simple view.
 */
public class EqualizerFragment extends Fragment {

    //Context.
    protected Context mContext;

    //Equalizer container elements.
    private RelativeLayout mRelativeView;

    // 50Hz equalizer controls.
    private VerticalSeekBar equalizer50HzSeekBar;
    private TextView text50HzGainTextView;
    private TextView text50Hz;

    // 130Hz equalizer controls.
    private VerticalSeekBar equalizer130HzSeekBar;
    private TextView text130HzGainTextView;
    private TextView text130Hz;

    // 320Hz equalizer controls.
    private VerticalSeekBar equalizer320HzSeekBar;
    private TextView text320HzGainTextView;
    private TextView text320Hz;

    // 800 Hz equalizer controls.
    private VerticalSeekBar equalizer800HzSeekBar;
    private TextView text800HzGainTextView;
    private TextView text800Hz;

    // 2 kHz equalizer controls.
    private VerticalSeekBar equalizer2kHzSeekBar;
    private TextView text2kHzGainTextView;
    private TextView text2kHz;

    // 5 kHz equalizer controls.
    private VerticalSeekBar equalizer5kHzSeekBar;
    private TextView text5kHzGainTextView;
    private TextView text5kHz;

    // 12.5 kHz equalizer controls.
    private VerticalSeekBar equalizer12_5kHzSeekBar;
    private TextView text12_5kHzGainTextView;
    private TextView text12_5kHz;

    // Equalizer preset controls.
    private RelativeLayout saveAsPresetButton;
    private RelativeLayout resetAllButton;
    private TextView savePresetText;
    private TextView resetAllText;

    // Temp variables that hold the equalizer's settings.
    private int fiftyHertzLevel = 16;
    private int oneThirtyHertzLevel = 16;
    private int threeTwentyHertzLevel = 16;
    private int eightHundredHertzLevel = 16;
    private int twoKilohertzLevel = 16;
    private int fiveKilohertzLevel = 16;
    private int twelvePointFiveKilohertzLevel = 16;

    // Temp variables that hold audio fx settings.
    private int virtualizerLevel;
    private int bassBoostLevel;
    private int reverbSetting;

    //Audio FX elements.
    private SeekBar bassBoostSeekBar;
    private TextView bassBoostTitle;

    //Misc flags.
    private boolean mDoneButtonPressed = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final View v = inflater.inflate(R.layout.content_equalizer, container, false);

        mContext = getActivity().getApplicationContext();
        mRelativeView = (RelativeLayout)v.findViewById(R.id.eq_wrapper);
        //50Hz equalizer controls.
        equalizer50HzSeekBar = (VerticalSeekBar) v.findViewById(R.id.equalizer50Hz);
        text50HzGainTextView = (TextView) v.findViewById(R.id.txt50HzGain);
        text50Hz = (TextView) v.findViewById(R.id.txt50Hz);

        //130Hz equalizer controls.
        equalizer130HzSeekBar = (VerticalSeekBar) v.findViewById(R.id.equalizer130Hz);
        text130HzGainTextView = (TextView) v.findViewById(R.id.txt130HzGain);
        text130Hz = (TextView) v.findViewById(R.id.txt130Hz);

        //320Hz equalizer controls.
        equalizer320HzSeekBar = (VerticalSeekBar) v.findViewById(R.id.equalizer320Hz);
        text320HzGainTextView = (TextView) v.findViewById(R.id.txt320HzGain);
        text320Hz = (TextView) v.findViewById(R.id.txt320Hz);

        //800Hz equalizer controls.
        equalizer800HzSeekBar = (VerticalSeekBar) v.findViewById(R.id.equalizer800Hz);
        text800HzGainTextView = (TextView) v.findViewById(R.id.txt800HzGain);
        text800Hz = (TextView) v.findViewById(R.id.txt800Hz);

        //2kHz equalizer controls.
        equalizer2kHzSeekBar = (VerticalSeekBar) v.findViewById(R.id.equalizer2kHz);
        text2kHzGainTextView = (TextView) v.findViewById(R.id.text2kHzGain);
        text2kHz = (TextView) v.findViewById(R.id.txt2kHz);

        //5kHz equalizer controls.
        equalizer5kHzSeekBar = (VerticalSeekBar) v.findViewById(R.id.equalizer5kHz);
        text5kHzGainTextView = (TextView) v.findViewById(R.id.txt5kHzGain);
        text5kHz = (TextView) v.findViewById(R.id.txt5kHz);

        //12.5kHz equalizer controls.
        equalizer12_5kHzSeekBar = (VerticalSeekBar) v.findViewById(R.id.equalizer12_5kHz);
        text12_5kHzGainTextView = (TextView) v.findViewById(R.id.txt12_5kHzGain);
        text12_5kHz = (TextView) v.findViewById(R.id.txt12_5kHz);

        //Equalizer preset controls.
/*        saveAsPresetButton = (RelativeLayout) v.findViewById(R.id.saveAsPresetButton);
        resetAllButton = (RelativeLayout) v.findViewById(R.id.resetAllButton);
        savePresetText = (TextView) v.findViewById(R.id.save_as_preset_text);
        resetAllText = (TextView) v.findViewById(R.id.reset_all_text);*/

        bassBoostSeekBar = (SeekBar) v.findViewById(R.id.bass_boost_seekbar);
        bassBoostTitle = (TextView) v.findViewById(R.id.bass_boost_title_text);

        //Set the max values for the seekbars.
        bassBoostSeekBar.setMax(1000);

        equalizer50HzSeekBar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener(){
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar.make(v, "50Hz: " + progressChanged, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        equalizer130HzSeekBar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener(){
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar.make(v, "130Hz: " + progressChanged, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        equalizer320HzSeekBar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener(){
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar.make(v, "320Hz: " + progressChanged, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        equalizer800HzSeekBar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener(){
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar.make(v, "800Hz: " + progressChanged, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        equalizer2kHzSeekBar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener(){
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar.make(v, "2kHz: " + progressChanged, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        equalizer5kHzSeekBar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener(){
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar.make(v, "5kHz: " + progressChanged, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        equalizer12_5kHzSeekBar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener(){
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar.make(v, "12,5kHz: " + progressChanged, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        bassBoostSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar.make(v, "Bass Boost: " + progressChanged, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

/*        resetAllButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Reset all sliders to 0.
                equalizer50HzSeekBar.setProgressAndThumb(16);
                equalizer130HzSeekBar.setProgressAndThumb(16);
                equalizer320HzSeekBar.setProgressAndThumb(16);
                equalizer800HzSeekBar.setProgressAndThumb(16);
                equalizer2kHzSeekBar.setProgressAndThumb(16);
                equalizer5kHzSeekBar.setProgressAndThumb(16);
                equalizer12_5kHzSeekBar.setProgressAndThumb(16);
                bassBoostSeekBar.setProgress(0);

                //Show a confirmation toast.
                Toast.makeText(mContext, "Reset", Toast.LENGTH_SHORT).show();
            }
        });*/

        return v;
    }
}
