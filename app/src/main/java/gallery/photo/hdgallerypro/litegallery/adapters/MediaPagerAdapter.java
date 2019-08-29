package gallery.photo.hdgallerypro.litegallery.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import gallery.photo.hdgallerypro.litegallery.data.Media;
import gallery.photo.hdgallerypro.litegallery.fragments.GifFragment;
import gallery.photo.hdgallerypro.litegallery.fragments.ImageFragment;
import gallery.photo.hdgallerypro.litegallery.fragments.VideoFragment;

import java.util.ArrayList;

/**
 * Created by dnld on 18/02/16.
 */

public class MediaPagerAdapter extends FragmentStatePagerAdapter {

    private final String TAG = "asd";
    private ArrayList<Media> media;
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public MediaPagerAdapter(FragmentManager fm, ArrayList<Media> media) {
        super(fm);
        this.media = media;
    }
    /*@Override
    public Parcelable saveState() {
        Bundle bundle = (Bundle) super.saveState();
        Log.e("BUNDLE ",bundle.toString());
        bundle.putParcelableArray("states", null); // Never maintain any states from the base class, just null it out
        return bundle;
    }*/
    @Override
    public Parcelable saveState()
    {
        Bundle bundle = (Bundle) super.saveState();
        if (bundle != null)
        {
            // Never maintain any states from the base class, just null it out
            bundle.putParcelableArray("states", null);
        } else
        {
            // do nothing
        }
        return bundle;
    }
    @Override
    public Fragment getItem(int pos) {
        //Log.e("MEDIA POS:",pos+"");
        Media media = this.media.get(pos);
        //Log.e("MEDIA ",media.getMimeType());
        //Log.e("MEDIA ",media.getUri()+"");
        //Log.e("MEDIA ",media.getPath());
        //Log.e("MEDIA ",media.getName());
        if (media.isVideo()) return VideoFragment.newInstance(media);
        if (media.isGif()) return GifFragment.newInstance(media);
        else return ImageFragment.newInstance(media);
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public void swapDataSet(ArrayList<Media> media) {
        this.media = media;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return media.size();
    }
}