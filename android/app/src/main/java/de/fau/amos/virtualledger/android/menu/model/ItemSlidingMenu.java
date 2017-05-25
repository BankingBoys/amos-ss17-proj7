package de.fau.amos.virtualledger.android.menu.model;

/**
 * Created by Simon on 13.05.2017.
 */

public class ItemSlidingMenu {
    private int imageId;
    private String imageTitle;

    /**
     * @param imageId
     * @param imageTitle
     * @methodtype constructor
     */
    public ItemSlidingMenu(int imageId, String imageTitle) {
        this.imageId = imageId;
        this.imageTitle = imageTitle;
    }

    /**
     * @return imageId
     * @methodtype getter
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * @param imageId
     * @methodtype setter
     */
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    /**
     * @return imageTitle
     * @methodtype getter
     */
    public String getImageTitle() {
        return imageTitle;
    }

    /**
     * @param imageTitle
     * @methodtype setter
     */
    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }
}
