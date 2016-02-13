package is.robertreynisson.iskcurrency.presenter_layer.models;

import is.robertreynisson.iskcurrency.ISKCurrency;
import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.utils.Utils;

/**
 * Created by robert on 10.2.2016.
 */
public class Currency {
    public String currencyName;
    public String currencyAbbrevaton;
    public double currencyValue;
    public double baseCurrencyAmount = 1000;

    public String getExchange() {
        return Utils.CurrencyFormat((baseCurrencyAmount / currencyValue), currencyAbbrevaton, false);
    }
    public String currencyDisplayValue() { return Utils.FormatNumber(currencyValue, false) +" "+ ISKCurrency.getInstance().getResources().getString(R.string.isk);}

}
