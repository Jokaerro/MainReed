package tesla.andrew.mainreed.data.datasource.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by TESLA on 22.07.2017.
 */

public class SubscribeContract {
    public static final String CONTENT_AUTHORITY = "tesla.andrew.mainreed";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SUBSCRIBES = "subscribes";

    public static final class SubscribeEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBSCRIBES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBSCRIBES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBSCRIBES;

        public static Uri buildSubscribeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "subscribes";
        public static final String COLUMN_CAPTION = "caption";
        public static final String COLUMN_SOURCE = "source";
        public static final String COLUMN_VISIBLE = "visible";
    }
}
