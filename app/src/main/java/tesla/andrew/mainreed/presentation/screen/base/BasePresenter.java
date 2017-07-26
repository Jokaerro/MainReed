package tesla.andrew.mainreed.presentation.screen.base;

/**
 * Created by TESLA on 23.07.2017.
 */

public class BasePresenter<View> {
    protected View view;

    public void setView(View view) {
        this.view = view;
    }

    public void destroy() {
        this.view = null;
    }
}
