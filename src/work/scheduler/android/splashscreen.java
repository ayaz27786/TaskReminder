package work.scheduler.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;

public class splashscreen extends  Activity {
    private static final int SPLASH_DISPLAY_TIME = 200;  /* 2 seconds */

    /**
     * The thread to process splash screen events
     */
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	   // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Splash screen view
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    /* Create an intent that will start the main activity. */
                            Intent mainIntent = new Intent(splashscreen.this,
                    		TaskReminder.class);
                            splashscreen.this.startActivity(mainIntent);
                   
                    /* Finish splash activity so user cant go back to it. */
                    splashscreen.this.finish();
                   
                    /* Apply our splash exit (fade out) and main
                       entry (fade in) animation transitions. */
                    overridePendingTransition(R.anim.fadein,
                            R.anim.fadeout);
            }
    }, SPLASH_DISPLAY_TIME);
}
}