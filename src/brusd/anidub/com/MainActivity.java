package brusd.anidub.com;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.slidingmenu.lib.SlidingMenu;

import brusd.anidub.com.AnidabFragment.UpdataListFragment;

public class MainActivity extends SherlockFragmentActivity {
    /**
     * Called when the activity is first created.
     */
    private SlidingMenu slidingMenu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);

        slidingMenu = new SlidingMenu(MainActivity.this);
        slidingMenu.setSlidingEnabled(true);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.SLIDING_WINDOW);
        slidingMenu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_WINDOW);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        slidingMenu.setBehindOffset((int) (metrics.widthPixels *0.15));
        slidingMenu.setMenu(R.layout.menu_layout);


        if(findViewById(R.id.fragment_holder) != null)
        {
            // if we are being restored from a previous state, then we dont need to do anything and should
            // return or else we could end up with overlapping fragments.
            if(savedInstanceState != null)
                return;

            // Create an instance of editorFrag
            UpdataListFragment updateListFragment = new UpdataListFragment();

            // add fragment to the fragment container layout

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, updateListFragment).commit();
        }
    }
}
