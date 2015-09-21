package com.yooiistudios.wooseongkim.hello_admob;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.yooiistudios.wooseongkim.hello_admob.common.network.InternetConnectionManager;


public class AdmobMainActivity extends AppCompatActivity {
    private static final String FULLSCREEN__AD_UNIT_ID  = "ca-app-pub-2310680050309555/7982499025";
    private static final String BANNER_AD_UNIT_ID  = "ca-app-pub-2310680050309555/7499867427";
    protected static final String QUIT_AD_UNIT_ID = "ca-app-pub-2310680050309555/3689313020";

    private AdView mAdView;
    private AdView mMediumAdView;

    // Quit Ad Dialog
    private AdRequest mQuitAdRequest;
    private AdView mQuitPortraitAdView;
    private AdView mQuitLandscapeAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admob_main);

        // adView 를 만듭니다.
        initBannerAdView();
        initMediumAdView();

        // android:id="@+id/mainLayout" 속성을 지정했다고 가정하고
        // LinearLayout 을 찾습니다.
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.mainLayout);

        // 레이아웃에 adView 를 추가합니다.
        layout.addView(mAdView);
        layout.addView(mMediumAdView);

        // 기본 요청을 시작합니다.
        AdRequest adRequest = new AdRequest.Builder().build();

        // 광고 요청으로 adView 를 로드합니다.
        mAdView.loadAd(adRequest);
        mMediumAdView.loadAd(adRequest);

        initQuitAdView();
    }

    private void initBannerAdView() {
        mAdView = new AdView(this);
        mAdView.setAdUnitId(BANNER_AD_UNIT_ID);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setId(R.id.banner_ad);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mAdView.setLayoutParams(params);
    }

    private void initMediumAdView() {
        mMediumAdView = new AdView(this);
        mMediumAdView.setAdUnitId(BANNER_AD_UNIT_ID);
        mMediumAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        mMediumAdView.setId(R.id.medium_banner_ad);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, mAdView.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = 50;
        mMediumAdView.setLayoutParams(params);
    }

    private void initQuitAdView() {
        // make AdView earlier for showing ad fast in the quit dialog
        // 애드몹 - Quit Dialog
        mQuitAdRequest = new AdRequest.Builder().build();
        mQuitPortraitAdView = QuitAdDialogFactory.initPortraitAdView(this, QUIT_AD_UNIT_ID,
                mQuitAdRequest);
        mQuitLandscapeAdView = QuitAdDialogFactory.initLandscapeAdView(this, QUIT_AD_UNIT_ID,
                mQuitAdRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
        mQuitPortraitAdView.resume();
        mQuitLandscapeAdView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdView.pause();
        mQuitPortraitAdView.pause();
        mQuitLandscapeAdView.pause();
    }

    @Override
    public void onBackPressed() {
        if (!IabProducts.containsSku(IabProducts.SKU_NO_ADS, this) &&
                InternetConnectionManager.isNetworkAvailable(this)) {

            QuitAdDialogFactory.Options options =
                    new QuitAdDialogFactory.Options(this, mQuitPortraitAdView);
            options.isRotatable = true;
            options.landscapeAdView = mQuitLandscapeAdView;

            Dialog adDialog = QuitAdDialogFactory.makeDialog(options);
            if (adDialog != null) {
                adDialog.show();
                // make AdView again for next quit dialog
                // prevent child reference
                // 가로 모드는 7.5% 가량 사용하고 있기에 속도를 위해서 광고를 계속 불러오지 않음
                mQuitPortraitAdView = QuitAdDialogFactory.initPortraitAdView(this, QUIT_AD_UNIT_ID,
                        mQuitAdRequest);
            } else {
                // just finish activity when dialog is null
                super.onBackPressed();
            }
        } else {
            // just finish activity when no ad item is bought
            super.onBackPressed();
        }
    }

    public void onButtonClick(View view) {
        // Admob
        final InterstitialAd fullScreenAdView = new InterstitialAd(this);
        fullScreenAdView.setAdUnitId(FULLSCREEN__AD_UNIT_ID);
        fullScreenAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                fullScreenAdView.show();
            }
        });
        AdRequest fullAdRequest = new AdRequest.Builder().build();
        fullScreenAdView.loadAd(fullAdRequest);
    }

    public void onActivityButtonClick(View view) {
        startActivity(new Intent(this, NonAppCompatActivity.class));
    }
}
