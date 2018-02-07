package sports.mgl.mgl;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Created by HP on 01-Mar-17.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        View easySplashScreenView = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(SignInActivity.class)
                .withSplashTimeOut(2000)
                .withBackgroundResource(R.color.specail)
                .withFooterText("@Copyright 2017")
                .withLogo(R.drawable.mgl)
                .create();

        setContentView(easySplashScreenView);
    }

}
