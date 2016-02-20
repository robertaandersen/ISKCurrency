package is.robertreynisson.iskcurrency.domain_layer;

import java.util.ArrayList;
import java.util.List;

import is.robertreynisson.iskcurrency.data_layer.models.APISCurrencyResponse;
import is.robertreynisson.iskcurrency.data_layer.models.ArionCurrencyResponse;
import is.robertreynisson.iskcurrency.presenter_layer.models.Currency;
import is.robertreynisson.iskcurrency.utils.Utils;
import rx.Observable;

/**
 * Created by robert on 10.2.2016.
 */
public class ModelConverters {
    public static List<Currency> currencyModelFromAPI(APISCurrencyResponse APISCurrencyResponse) {
        List<Currency> l = appendISK(APISCurrencyResponse);
        Observable.from(APISCurrencyResponse.getResults()).map(currency -> {
            Currency c = new Currency();
            c.currencyValue = currency.getValue();
            c.currencyName = currency.getLongName();
            c.currencyAbbrevaton = currency.getShortName();
            c.currencyIcon = Utils.getDrawableFromCurrency(currency.getShortName());
            l.add(c);
            return c;
        }).subscribe();
        return l;
    }

    private static List<Currency> appendISK(APISCurrencyResponse apisCurrencyResponse) {
        List<Currency> l = new ArrayList<>();
        for(int i = 0; i < apisCurrencyResponse.getResults().size(); i++){
            if(apisCurrencyResponse.getResults().get(i).getShortName().equals("ISK")) return l;
        }
        Currency isk = new Currency();
        isk.currencyIcon = Utils.getDrawableFromCurrency("ISK");
        isk.currencyValue = 1;
        isk.currencyName = "Króna";
        isk.currencyAbbrevaton = "ISK";
        l.add(isk);
        return l;
    }

    public static Currency currencyModelFromArionResponse(ArionCurrencyResponse arionCurrencyResponse) {
        Currency curr = new Currency();
        curr.currencyIcon = Utils.getDrawableFromCurrency(arionCurrencyResponse.getTicker());
        curr.currencyValue = arionCurrencyResponse.getAskValue();
        curr.currencyAbbrevaton = arionCurrencyResponse.getTicker();
        curr.currencyName = arionCurrencyResponse.getTitle();
        return curr;
    }

    public static List<Currency> currencyModelFromArionResponse(ArionCurrencyResponse[] arionCurrencyResponses) {
        List<Currency> l = new ArrayList<>();
        Observable.from(arionCurrencyResponses).subscribe(res -> l.add(currencyModelFromArionResponse(res)));
        return l;
    }
}
