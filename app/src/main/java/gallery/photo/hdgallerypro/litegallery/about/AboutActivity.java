package gallery.photo.hdgallerypro.litegallery.about;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.widget.ScrollView;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import gallery.photo.hdgallerypro.litegallery.BuildConfig;
import gallery.photo.hdgallerypro.litegallery.R;
import gallery.photo.hdgallerypro.litegallery.activities.base.BaseActivity;
import gallery.photo.hdgallerypro.litegallery.util.AlertDialogsHelper;
import gallery.photo.hdgallerypro.litegallery.util.ApplicationUtils;
import gallery.photo.hdgallerypro.litegallery.util.ChromeCustomTabs;
import gallery.photo.hdgallerypro.litegallery.util.preferences.Prefs;
import org.horaapps.liz.ui.ThemedTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static gallery.photo.hdgallerypro.litegallery.util.ServerConstants.CREDITS_URL;
import static gallery.photo.hdgallerypro.litegallery.util.ServerConstants.GITHUB_GALLERY;
import static gallery.photo.hdgallerypro.litegallery.util.ServerConstants.GALLERY_ISSUES;
import static gallery.photo.hdgallerypro.litegallery.util.ServerConstants.GALLERY_LICENSE;
import static gallery.photo.hdgallerypro.litegallery.util.ServerConstants.PRIVACY_POLICY;

/**
 * The Activity to show About application
 * <p>
 * Includes the following data:
 * - Developers
 * - Translators
 * - Relevant app links
 */
public class AboutActivity extends BaseActivity implements ContactListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.about_version_item_sub) ThemedTextView appVersion;
    @BindView(R.id.aboutAct_scrollView) ScrollView aboutScrollView;

    private ChromeCustomTabs chromeTabs;
    private int emojiEasterEggCount = 0;

    public static void startActivity(@NonNull Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        chromeTabs = new ChromeCustomTabs(AboutActivity.this);

        initUi();
        setTitle(R.string.about);
    }

    @Override
    public void onDestroy() {
        chromeTabs.destroy();
        super.onDestroy();
    }

    /** Select List */
    @OnClick(R.id.about_link_report_bug)
    public void onReportBug() {
        chromeTabs.launchUrl(GALLERY_ISSUES);
    }

    @OnClick(R.id.about_link_rate)
    public void onRate() {
        Uri uri = Uri.parse(String.format("market://details?id=%s", BuildConfig.APPLICATION_ID));
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        /** To count with Play market backstack, After pressing back button,
         * to taken back to our application, we need to add following flags to intent. */

        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;

        goToMarket.addFlags(flags);

        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format("http://play.google.com/store/apps/details?id=%s", BuildConfig.APPLICATION_ID))));
        }
    }

    @OnClick(R.id.about_link_github)
    public void onGitHub() {
        chromeTabs.launchUrl(GITHUB_GALLERY);
    }

    @OnClick(R.id.credits)
    public void onCredits() {
        chromeTabs.launchUrl(CREDITS_URL);
    }

    @OnClick(R.id.privacy_policy)
    public void onPrivacy() {
        chromeTabs.launchUrl(PRIVACY_POLICY);
    }

    @OnClick(R.id.about_link_license)
    public void onLicense() {
        chromeTabs.launchUrl(GALLERY_LICENSE);
    }

    private void emojiEasterEgg() {
        emojiEasterEggCount++;
        if (emojiEasterEggCount > 3) {
            boolean showEasterEgg = Prefs.showEasterEgg();
            Toast.makeText(this,
                    (!showEasterEgg ? this.getString(R.string.easter_egg_enable) : this.getString(R.string.easter_egg_disable))
                            + " " + this.getString(R.string.emoji_easter_egg), Toast.LENGTH_SHORT).show();
            Prefs.setShowEasterEgg(!showEasterEgg);
            emojiEasterEggCount = 0;
        }
    }

    private void mail(String mail) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + mail));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.send_mail_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void initUi() {
        setSupportActionBar(toolbar);
        appVersion.setText(ApplicationUtils.getAppVersion());
    }

    @CallSuper
    @Override
    public void updateUiElements() {
        super.updateUiElements();
        toolbar.setBackgroundColor(getPrimaryColor());
        toolbar.setNavigationIcon(getToolbarIcon(GoogleMaterial.Icon.gmd_arrow_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        setScrollViewColor(aboutScrollView);
        setStatusBarColor();
        setNavBarColor();
    }

    @Override
    public void onContactClicked(Contact contact) {
        chromeTabs.launchUrl(contact.getValue());
    }

    @Override
    public void onMailClicked(String mail) {
        mail(mail);
    }
}
