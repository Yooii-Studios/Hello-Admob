package com.yooiistudios.wooseongkim.hello_admob;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StevenKim in Morning Kit from Yooii Studios Co., LTD. on 2014. 1. 9.
 *
 * IabProducts
 *  유이스튜디오 앱에 일반적으로 있는 결제 아이템 클래스. 구매여부를 확인할 때에도 사용
 */
public class IabProducts {
    public static final String SKU_FULL_VERSION = "full_version"; // "full_version_test"
    public static final String SKU_MORE_ALARM_SLOTS = "functions.more_alarm_slots"; // "more_alarm_slots_test"
    public static final String SKU_NO_ADS = "functions.no_ads"; // "no_ads_test"
    public static final String SKU_PANEL_MATRIX_2X3 = "panel_matrix_2_3"; // "panel_matrix_2_3_test"
    public static final String SKU_DATE_COUNTDOWN = "panels.date_countdown"; // "date_countdown_test";
    public static final String SKU_MEMO = "panels.memo"; // "memo_test";
    public static final String SKU_PHOTO_FRAME = "panel.photo_frame"; // "photo_frame_test";
    public static final String SKU_MODERNITY = "themes.modernity"; // "modernity_test";
    public static final String SKU_CELESTIAL = "themes.celestial"; // "celestial_test";
    public static final String SKU_CAT = "cat"; // 구글 플레이 판매용이 아닌 언락과 풀버전 전용 아이템

    private static final String SHARED_PREFERENCES_IAB = "SHARED_PREFERENCES_IAB";
    private static final String SHARED_PREFERENCES_IAB_DEBUG = "SHARED_PREFERENCES_IAB_DEBUG";

    public static List<String> makeProductKeyList() {
        List<String> iabKeyList = new ArrayList<String>();
        iabKeyList.add(SKU_FULL_VERSION);
        iabKeyList.add(SKU_MORE_ALARM_SLOTS);
        iabKeyList.add(SKU_NO_ADS);
        iabKeyList.add(SKU_PANEL_MATRIX_2X3);
        iabKeyList.add(SKU_DATE_COUNTDOWN);
        iabKeyList.add(SKU_MEMO);
        iabKeyList.add(SKU_PHOTO_FRAME);
        iabKeyList.add(SKU_MODERNITY);
        iabKeyList.add(SKU_CELESTIAL);
        iabKeyList.add(SKU_CAT);
        return iabKeyList;
    }

    public static boolean containsSku(String sku, Context context) {
        return loadOwnedIabProducts(context).contains(sku);
    }

    // 구매된 아이템들을 로드
    public static List<String> loadOwnedIabProducts(Context context) {
        List<String> ownedSkus = new ArrayList<String>();

        SharedPreferences prefs;
        prefs = context.getSharedPreferences(SHARED_PREFERENCES_IAB_DEBUG, Context.MODE_PRIVATE);
        if (prefs.getBoolean(SKU_FULL_VERSION, false)) {
            ownedSkus.add(SKU_FULL_VERSION);
            ownedSkus.add(SKU_MORE_ALARM_SLOTS);
            ownedSkus.add(SKU_NO_ADS);
            ownedSkus.add(SKU_PANEL_MATRIX_2X3);
            ownedSkus.add(SKU_DATE_COUNTDOWN);
            ownedSkus.add(SKU_MEMO);
            ownedSkus.add(SKU_PHOTO_FRAME);
            ownedSkus.add(SKU_MODERNITY);
            ownedSkus.add(SKU_CELESTIAL);
            ownedSkus.add(SKU_CAT);
        } else {
            if (prefs.getBoolean(SKU_MORE_ALARM_SLOTS, false)) {
                ownedSkus.add(SKU_MORE_ALARM_SLOTS);
            }
            if (prefs.getBoolean(SKU_NO_ADS, false)) {
                ownedSkus.add(SKU_NO_ADS);
            }
            if (prefs.getBoolean(SKU_PANEL_MATRIX_2X3, false)) {
                ownedSkus.add(SKU_PANEL_MATRIX_2X3);
            }
            if (prefs.getBoolean(SKU_DATE_COUNTDOWN, false)) {
                ownedSkus.add(SKU_DATE_COUNTDOWN);
            }
            if (prefs.getBoolean(SKU_MEMO, false)) {
                ownedSkus.add(SKU_MEMO);
            }
            if (prefs.getBoolean(SKU_PHOTO_FRAME, false)) {
                ownedSkus.add(SKU_PHOTO_FRAME);
            }
            if (prefs.getBoolean(SKU_MODERNITY, false)) {
                ownedSkus.add(SKU_MODERNITY);
            }
            if (prefs.getBoolean(SKU_CELESTIAL, false)) {
                ownedSkus.add(SKU_CELESTIAL);
            }
            if (prefs.getBoolean(SKU_CAT, false)) {
                ownedSkus.add(SKU_CAT);
            }
        }
        return ownedSkus;
    }

    /**
     * For Debug Mode
     */
    public static void resetIabProductsDebug(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(SHARED_PREFERENCES_IAB_DEBUG,
                Context.MODE_PRIVATE).edit();
        edit.clear().apply();
    }
}
