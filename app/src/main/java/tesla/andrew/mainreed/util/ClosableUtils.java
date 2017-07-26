package tesla.andrew.mainreed.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Tesla on 25.07.2017.
 */

public class ClosableUtils {

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
