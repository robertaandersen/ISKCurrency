package is.robertreynisson.iskcurrency.presenter_layer.models;

import is.robertreynisson.iskcurrency.utils.Utils;

/**
 * Created by robert on 10.2.2016.
 */
public class Currency {
    public String currencyName;
    public String currencyAbbrevaton;
    public double currencyValue;

    public String currencyDisplayValue() {
        return Utils.CurrencyFormat(currencyValue);
    }

}
