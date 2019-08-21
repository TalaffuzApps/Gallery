package gallery.photo.hdgallerypro.litegallery.data.filter;

import gallery.photo.hdgallerypro.litegallery.data.Media;

/**
 * Created by dnld on 4/10/17.
 */
public class MediaFilter {
    public static IMediaFilter getFilter(FilterMode mode) {
        switch (mode) {
            case ALL: default:
                return media -> true;
            case GIF:
                return Media::isGif;
            case VIDEO:
                return Media::isVideo;
            case IMAGES: return Media::isImage;
        }
    }
}
