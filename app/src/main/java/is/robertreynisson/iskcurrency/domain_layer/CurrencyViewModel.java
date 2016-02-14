package is.robertreynisson.iskcurrency.domain_layer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import is.robertreynisson.iskcurrency.presenter_layer.MainActivity;
import is.robertreynisson.iskcurrency.presenter_layer.models.Currency;
import is.robertreynisson.iskcurrency.utils.RxBus;
import is.robertreynisson.iskcurrency.utils.Utils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by robert on 10.2.2016.
 */
public class CurrencyViewModel extends AbstractViewModel {

    private static final String TAG = CurrencyViewModel.class.getSimpleName();

    private final BehaviorSubject<String> time = BehaviorSubject.create();
    private final BehaviorSubject<List<Currency>> currencyList = BehaviorSubject.create();
    private final static BehaviorSubject<Object> newBaseAmount = BehaviorSubject.create();
    public static RxBus foreignCurrencyBus = new RxBus();
    long updateTime;

    @Override
    protected void subscribeToDataStoreInternal(CompositeSubscription compositeSubscription) {
        Utils.logger(TAG, "Subscribed");
        compositeSubscription.add(MainActivity.serviceAdapter.getArionRates()
                .map(ModelConverters::currencyModelFromArionResponse)
                .map(x -> {
                    Date d = new Date();
                    updateTime = d.getTime();
                    return x;})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currencyList));
        //compositeSubscription.add(MainActivity.serviceAdapter.getAPISRates("m5").map(ModelConverters::currencyModelFromAPI).subscribe(currencyList));
        //compositeSubscription.add(getDummyCurrencies().subscribeOn(AndroidSchedulers.mainThread()).subscribe(currencyList));
        compositeSubscription.add(Observable.interval(1, TimeUnit.MINUTES).map(s -> Utils.PrettyDateFormatter(updateTime)).subscribe(time));
        compositeSubscription.add(
                foreignCurrencyBus.toObserverable()
                        .throttleLast(250, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                        .subscribe(newBaseAmount)
        );
    }

    public BehaviorSubject<String> getTime() { return time; }

    public BehaviorSubject<List<Currency>> getCurrencies() {
        Date date = new Date();
        updateTime = date.getTime();
        return currencyList;
    }
    public Observable<List<Currency>> getDummyCurrencies() {
        List<Currency> currlist = new ArrayList();
        Currency usd = new Currency();
        usd.currencyValue = 136.6;
        usd.currencyAbbrevaton = "USD";
        usd.currencyName ="Bandaríkjadalur";
        currlist.add(usd);

        Currency eur = new Currency();
        eur.currencyValue = 142.2;
        eur.currencyAbbrevaton = "EUR";
        eur.currencyName ="Evra";
        currlist.add(eur);

        Currency dkk = new Currency();
        dkk.currencyValue = 21.5;
        dkk.currencyAbbrevaton = "DKK";
        currlist.add(dkk);
        return Observable.just(currlist);

    }
    public static BehaviorSubject<Object> getNewBaseAmount() { return newBaseAmount;}

    private static String purge(String xxx){
        try{
            return new BigDecimal(xxx).toPlainString();
        }
        catch(NumberFormatException ex) {
            xxx = xxx.replace(" ", "");
            xxx = xxx.replace(",", ".");
            String ret = "";
            for (int i = 0; i < xxx.length(); i++) {
                if (Character.isDigit(xxx.charAt(i)) || xxx.charAt(i) == '.') ret += xxx.charAt(i);
            }
            return ret;
        }
    }
    public static boolean validate(String xxx) {
        //Todo implement 'nicer' validation
        if (xxx.equals("")) return true;
        for (int i = 0; i < xxx.length(); i++) {
            if (!Character.isDigit(xxx.charAt(i)) && xxx.charAt(i) != '.') return false;
        }
        return true;
    }

    public static boolean broadcastNewValue(String newValue) {
        String purged = purge(newValue);
        if (validate(purged)) {
            foreignCurrencyBus.send(purged.equals("") ? "0" : purged);
            return true;
        }
        return false;
    }

}
