package tesla.andrew.mainreed.domain.entity;

/**
 * Created by TESLA on 22.07.2017.
 */

public class Subscribe {
    private String itemId;
    private String caption;
    private String source;
    private Boolean visible;

    public Subscribe(String itemId, String caption, String source, Boolean visible) {
        this.itemId = itemId;
        this.caption = caption;
        this.source = source;
        this.visible = visible;
    }

    public Boolean isVisible() {
        return this.visible;
    }

    public String getItemId() {
        return itemId;
    }

    public String getCaption() {
        return caption;
    }

    public String getSource() {
        return source;
    }
}
