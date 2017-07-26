package tesla.andrew.mainreed.presentation.screen.subscribes;

import java.util.List;

import tesla.andrew.mainreed.domain.entity.Subscribe;

/**
 * Created by TESLA on 26.07.2017.
 */

public interface SubscribesView {
    void updateSubscribesList(List<Subscribe> subscribes);
    void errorHandling();
}
