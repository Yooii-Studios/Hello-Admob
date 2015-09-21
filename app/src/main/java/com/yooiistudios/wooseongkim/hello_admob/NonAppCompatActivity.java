package com.yooiistudios.wooseongkim.hello_admob;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.yooiistudios.quitaddialog.QuitAdDialogFactory;
import com.yooiistudios.wooseongkim.hello_admob.common.network.InternetConnectionManager;

public class NonAppCompatActivity extends Activity {

    // Quit Ad Dialog
    private AdRequest mQuitAdRequest;
    private AdView mQuitPortraitAdView;
    private AdView mQuitLandscapeAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_app_compat);
        initQuitAdView();
    }

    private void initQuitAdView() {
        // make AdView earlier for showing ad fast in the quit dialog
        // 애드몹 - Quit Dialog
        mQuitAdRequest = new AdRequest.Builder().build();
        mQuitPortraitAdView = QuitAdDialogFactory.initPortraitAdView(this,
                AdmobMainActivity.QUIT_AD_UNIT_ID, mQuitAdRequest);
        mQuitLandscapeAdView = QuitAdDialogFactory.initLandscapeAdView(this,
                AdmobMainActivity.QUIT_AD_UNIT_ID, mQuitAdRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQuitPortraitAdView.resume();
        mQuitLandscapeAdView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQuitPortraitAdView.pause();
        mQuitLandscapeAdView.pause();
    }

    @Override
    public void onBackPressed() {
        if (!IabProducts.containsSku(IabProducts.SKU_NO_ADS, this) &&
                InternetConnectionManager.isNetworkAvailable(this)) {

            QuitAdDialogFactory.Options options =
                    new QuitAdDialogFactory.Options(this, mQuitPortraitAdView);
            options.isAppCompat = false;
            options.isRotatable = true;
            options.landscapeAdView = mQuitLandscapeAdView;

            Dialog adDialog = QuitAdDialogFactory.makeDialog(options);
            if (adDialog != null) {
                adDialog.show();
                // make AdView again for next quit dialog
                // prevent child reference
                // 가로 모드는 7.5% 가량 사용하고 있기에 속도를 위해서 광고를 계속 불러오지 않음
                mQuitPortraitAdView = QuitAdDialogFactory.initPortraitAdView(this,
                        AdmobMainActivity.QUIT_AD_UNIT_ID, mQuitAdRequest);
            } else {
                // just finish activity when dialog is null
                super.onBackPressed();
            }
        } else {
            // just finish activity when no ad item is bought
            super.onBackPressed();
        }
    }
}
