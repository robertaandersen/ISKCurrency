package is.robertreynisson.iskcurrency.domain_layer;

import is.robertreynisson.iskcurrency.utils.Utils;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by robert on 10.2.2016.
 */
public abstract class AbstractViewModel {
    private static final String TAG = AbstractViewModel.class.getSimpleName();
    private CompositeSubscription compositeSubscription;


    final public void subscribeToDataStore() {
        Utils.logger(TAG, "subscribeToDataStore");
        unsubscribeFromDataStore();
        compositeSubscription = new CompositeSubscription();
        subscribeToDataStoreInternal(compositeSubscription);
    }

    public void unsubscribeFromDataStore() {
        Utils.logger(TAG, "unsubscribe FromDataStore");
        if (compositeSubscription != null) {
            compositeSubscription.clear();
            compositeSubscription = null;
        }
    }

    public void dispose() {
        if (compositeSubscription != null) {
            // Unsubscribe in case we are still for some reason subscribed
            unsubscribeFromDataStore();
        }
    }

    protected abstract void subscribeToDataStoreInternal(CompositeSubscription compositeSubscription);
}
