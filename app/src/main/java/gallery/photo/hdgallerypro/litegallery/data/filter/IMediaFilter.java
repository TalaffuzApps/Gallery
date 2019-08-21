package gallery.photo.hdgallerypro.litegallery.data.filter;

import gallery.photo.hdgallerypro.litegallery.data.Media;

/**
 * Created by dnld on 4/10/17.
 */

public interface IMediaFilter {
    boolean accept(Media media);
}
