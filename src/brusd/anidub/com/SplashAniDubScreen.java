package brusd.anidub.com;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

import brusd.anidub.com.DawnloadAndParser.DawnloadRssAsyncTask;

/**
 * Created by BruSD on 04.08.13.
 */
public class SplashAniDubScreen extends SherlockActivity{


    /**
     * <p>Required method for class Activity.</p>
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.splash_anidub_layout);
        getSupportActionBar().hide();

    }

    @Override
    public void onStart(){
        super.onStart();
        new DawnloadRssAsyncTask(this, null).execute();
    }

    public void startMainActivity(){
        Intent intent;
        intent = new Intent(SplashAniDubScreen.this, MainActivity.class);
        startActivity(intent);

    }
}
