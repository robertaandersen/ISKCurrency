package is.robertreynisson.iskcurrency.domain_layer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import is.robertreynisson.iskcurrency.presenter_layer.MainActivity;
import is.robertreynisson.iskcurrency.presenter_layer.models.Currency;
import is.robertreynisson.iskcurrency.utils.RxBus;
import is.robertreynisson.iskcurrency.utils.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by robert on 10.2.2016.
 */
public class CurrencyViewModel extends AbstractViewModel{

    private static final String TAG = CurrencyViewModel.class.getSimpleName();

    private final BehaviorSubject<String> time = BehaviorSubject.create();
    private final BehaviorSubject<List<Currency>> currencyList = BehaviorSubject.create();


    @Override
    protected void subscribeToDataStoreInternal(CompositeSubscription compositeSubscription) {
        Utils.logger(TAG, "Subscribed");
        compositeSubscription.add(MainActivity.serviceAdapter.getRates("m5").map(ModelConverters::currencyModelFromAPI).subscribe(currencyList));
    }

    public BehaviorSubject<String> getTime() { return time; }
    public BehaviorSubject<List<Currency>> getCurrencies() { return currencyList; }
}
