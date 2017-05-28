package de.fau.amos.virtualledger.android.menu.model;

/**
 * Created by Simon on 13.05.2017.
 */

public class ItemSlidingMenu {
    private int imageId;
    private String imageTitle;

    /**
     *
     */
    public ItemSlidingMenu(int imageId, String imageTitle) {
        this.imageId = imageId;
        this.imageTitle = imageTitle;
    }

    /**
     *
     */
    public int getImageId() {
        return imageId;
    }

    /**
     *
     */
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    /**
     *
     */
    public String getImageTitle() {
        return imageTitle;
    }

    /**
     *
     */
    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }
}
