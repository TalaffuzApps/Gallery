package gallery.photo.hdgallerypro.litegallery;

import androidx.multidex.MultiDexApplication;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;
import com.onesignal.OneSignal;
import com.orhanobut.hawk.Hawk;
import com.splunk.mint.Mint;
import com.squareup.leakcanary.LeakCanary;

import gallery.photo.hdgallerypro.litegallery.R;

import gallery.photo.hdgallerypro.litegallery.util.ApplicationUtils;
import gallery.photo.hdgallerypro.litegallery.util.preferences.Prefs;

import static gallery.photo.hdgallerypro.litegallery.CardViewStyle.COMPACT;

/**
 * Created by dnld on 28/04/16.
 */
public class App extends MultiDexApplication {

    private static App mInstance;
    public static int countInterstitial=0;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        Mint.initAndStartSession(this, getResources().getString(R.string.mint_id));

        ApplicationUtils.init(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        /** This process is dedicated to LeakCanary for heap analysis.
         *  You should not init your app in this process. */

        
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        registerFontIcons();
        initialiseStorage();
    }

    public static App getInstance() {
        return mInstance;
    }

    private void registerFontIcons() {
        Iconics.registerFont(new GoogleMaterial());
        Iconics.registerFont(new CommunityMaterial());
        Iconics.registerFont(new FontAwesome());
    }

    private void initialiseStorage() {
        Prefs.init(this);
        Hawk.init(this).build();
        //Log.e("HAWK P>>",Hawk.get(getString(R.string.preference_primary_color), "DEF")+"");
        //Log.e("HAWK A>>",Hawk.get(getString(R.string.preference_accent_color), "DEF ACC")+"");
        if(String.valueOf(Hawk.get(getString(R.string.preference_primary_color), "FALSE")).equalsIgnoreCase("FALSE")){
            Hawk.put(getString(R.string.preference_primary_color), getResources().getColor(R.color.colorPrimary));
            Hawk.put(getString(R.string.preference_accent_color),getResources().getColor(R.color.colorAccent));

            //set default card style
            CardViewStyle cardViewStyle;
            cardViewStyle=COMPACT;
            Prefs.setCardStyle(cardViewStyle);

        }


        //Log.e("HAWK P>>",Hawk.get(getString(R.string.preference_primary_color), "DEF")+"");
        //Log.e("HAWK A>>",Hawk.get(getString(R.string.preference_accent_color), "DEF ACC")+"");
    }
}
