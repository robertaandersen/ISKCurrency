package is.robertreynisson.iskcurrency.domain_layer;

import java.util.ArrayList;
import java.util.List;

import is.robertreynisson.iskcurrency.data_layer.models.CurrencyResponse;
import is.robertreynisson.iskcurrency.presenter_layer.models.Currency;
import rx.Observable;

/**
 * Created by robert on 10.2.2016.
 */
public class ModelConverters {
    public static List<Currency> currencyModelFromAPI(CurrencyResponse currencyResponse) {
        List<Currency> l = new ArrayList<>();
        Observable.from(currencyResponse.getResults()).map(currency -> {
            Currency c = new Currency();
            c.currencyValue = currency.getValue();
            c.currencyName = currency.getLongName();
            c.currencyAbbrevaton = currency.getShortName();
            l.add(c);
            return c;
        }).subscribe();
        return l;
    }
}
