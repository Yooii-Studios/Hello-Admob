package com.yooiistudios.wooseongkim.hello_admob;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.yooiistudios.wooseongkim.hello_admob.common.network.InternetConnectionManager;


public class AdmobMainActivity extends Activity {
    private static final String FULLSCREEN__AD_UNIT_ID  = "ca-app-pub-2310680050309555/7982499025";
    private static final String BANNER_AD_UNIT_ID  = "ca-app-pub-2310680050309555/7499867427";
    private AdView mAdView;

    // Quit Ad Dialog
    private AdRequest mQuitAdRequest;
    private AdView mQuitMediumAdView;
    private AdView mQuitLargeBannerAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admob_main);

        // adView를 만듭니다.
        mAdView = new AdView(this);
        mAdView.setAdUnitId(BANNER_AD_UNIT_ID);
        mAdView.setAdSize(AdSize.BANNER);

        // android:id="@+id/mainLayout" 속성을 지정했다고 가정하고
        // LinearLayout을 찾습니다.
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.mainLayout);

        // 레이아웃에 adView를 추가합니다.
        layout.addView(mAdView);

        // 기본 요청을 시작합니다.
        AdRequest adRequest = new AdRequest.Builder().build();

        // 광고 요청으로 adView를 로드합니다.
        mAdView.loadAd(adRequest);

        initQuitAdView();
    }

    private void initQuitAdView() {
        // make AdView earlier for showing ad fast in the quit dialog
        // 애드몹 - Quit Dialog
        mQuitAdRequest = new AdRequest.Builder().build();
        mQuitMediumAdView = QuitAdDialogFactory.initAdView(this, AdSize.MEDIUM_RECTANGLE,
                mQuitAdRequest);
        mQuitLargeBannerAdView = QuitAdDialogFactory.initAdView(this, AdSize.LARGE_BANNER,
                mQuitAdRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
        mQuitMediumAdView.resume();
        mQuitLargeBannerAdView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdView.pause();
        mQuitMediumAdView.pause();
        mQuitLargeBannerAdView.pause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admob_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!SKIabProducts.containsSku(SKIabProducts.SKU_NO_ADS, this) &&
                InternetConnectionManager.isNetworkAvailable(this)) {
            AlertDialog adDialog = QuitAdDialogFactory.makeDialog(AdmobMainActivity.this,
                    mQuitMediumAdView, mQuitLargeBannerAdView);
            if (adDialog != null) {
                adDialog.show();
                // make AdView again for next quit dialog
                // prevent child reference
                // 가로 모드는 7.5% 가량 사용하고 있기에 속도를 위해서 광고를 계속 불러오지 않음
                mQuitMediumAdView = QuitAdDialogFactory.initAdView(this, AdSize.MEDIUM_RECTANGLE,
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

    public void onButtonClicked(View view) {
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
}
