package gallery.photo.hdgallerypro.litegallery.interfaces;

import gallery.photo.hdgallerypro.litegallery.data.Album;
import gallery.photo.hdgallerypro.litegallery.data.Media;

import java.util.ArrayList;

public interface MediaClickListener {

    void onMediaClick(Album album, ArrayList<Media> media, int position);
}
