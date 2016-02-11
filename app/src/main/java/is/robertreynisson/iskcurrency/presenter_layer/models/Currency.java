package is.robertreynisson.iskcurrency.presenter_layer.models;

import java.util.Locale;

import is.robertreynisson.iskcurrency.utils.Utils;

/**
 * Created by robert on 10.2.2016.
 */
public class Currency {
    public String currencyName;
    public String currencyAbbrevaton;
    public double currencyValue;
    public double baseCurrencyAmount = 1000;

    public String getExchangeString() {
        return Utils.CurrencyFormat(baseCurrencyAmount / currencyValue, currencyAbbrevaton, false);
    }

    public double getExchange(){
        return Utils.roundToDouble(baseCurrencyAmount / currencyValue, 2);
    }

    public String currencyDisplayValue() { return Utils.CurrencyFormat(currencyValue, new Locale("is", "IS"), false);}



}
