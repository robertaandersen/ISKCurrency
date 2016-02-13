package is.robertreynisson.iskcurrency.utils;

import android.util.Log;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by robert on 10.2.2016.
 */
public class Utils {
    public static void logger(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static String PrettyDateFormatter(long date) {
        // Do additional formatting here (e.g. prettytime etc.)
        return NiceDate(new LocalDateTime(date));
    }

    private static String NiceDate(LocalDateTime localDateTime) {
        Locale l = Locale.getDefault();
        return
                localDateTime.dayOfWeek().getAsShortText(l)
                        + " " + localDateTime.getDayOfMonth()
                        + " " + localDateTime.toString("MMM")
                        + " " + localDateTime.getYear()
                        + " " + getClock(localDateTime);
    }

    public static String getClock(LocalDateTime localDateTime) {
        int min = localDateTime.get(DateTimeFieldType.minuteOfHour());
        String minutes = min < 10 ? "0" + min : min + "";
        return localDateTime.get(DateTimeFieldType.hourOfDay())
                + ":" + minutes
                + ":" + localDateTime.get(DateTimeFieldType.secondOfMinute());
    }

    public static String PrettyDateFormatter(String timestamp) {
        return timestamp;
    }


    public static String CurrencyFormat(double value, String currencyAbbrevaton, boolean scientificNotation) {
        return CurrencyFormat(value, getLocaleFromCurrency(currencyAbbrevaton), scientificNotation);
    }

    public static String CurrencyFormat(double value, Locale l, boolean scientificNotation) {
        char thousand = 'k';

        //Saudi does not want their numbers formatted to Indian numbers
        if (l.getLanguage().equals("ar")) l = new Locale("DE", l.getCountry());

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(l);
        numberFormat.setMaximumFractionDigits(2);
        if(!scientificNotation) return numberFormat.format(value);
        int magnitude = value > 999999 ? 1000000 : value > 999 ? 1000 : value < -999 ? value < -999999 ? 1000000 : 1000 : 1;
        char postFix = magnitude == 1000000 ? 'M' : magnitude == 1000 ? thousand : ' ';
        String ret = numberFormat.format(value / magnitude);
        ret = attachPostFix(ret, postFix);
        return ret;
    }

    private static String attachPostFix(String ret, char postFix) {
        if (ret == null || ret.length() <= 0) return null;
        if (!Character.isDigit(ret.charAt(0)) && ret.charAt(0) != '-') return ret + postFix;

        List<Character> currencySymbol = new ArrayList<>();
        String symbol = "";
        for (int i = ret.length() - 1; i >= 0; i--) {
            char currentChar = ret.charAt(i);
            if (!Character.isDigit(currentChar) && currentChar != ',' && currentChar != '.' && currentChar != ' ') {
                currencySymbol.add(currentChar);
                continue;
            }
            if (Character.isDigit(currentChar)) {
                for (int j = currencySymbol.size() - 1; j >= 0; j--) {
                    if (!currencySymbol.get(j).equals(' '))
                        symbol += currencySymbol.get(j);
                }
                return ret.substring(0, ret.length() - currencySymbol.size()) + postFix + symbol;
            }
        }
        return null;
    }

    public static Locale getLocaleFromCurrency(String currencyAbbrevaton) {
        Locale locale = Locale.getDefault();
        switch (currencyAbbrevaton) {
            case "ISK":
                locale = new Locale("is", "IS");
                break;
            case "USD":
                locale = new Locale("en", "US");
                break;
            case "EUR":
                locale = new Locale("de", "DE");
                break;
            case "GBP":
                locale = new Locale("en", "GB");
                break;
            case "JPY":
                locale = new Locale("ja", "JP");
                break;
            case "NOK":
                locale = new Locale("nb", "NO");
                break;
            case "SEK":
                locale = new Locale("sv", "SE");
                break;
            case "DKK":
                locale = new Locale("da", "DK");
                break;
            case "CAD":
                locale = new Locale("en", "CA");
                break;
            case "AUD":
                locale = new Locale("en", "AU");
                break;
            case "ZAR":
                locale = new Locale("en", "ZA");
                break;
            case "HKD":
                locale = new Locale("en", "HK");
                break;
            case "NZD":
                locale = new Locale("en", "NZ");
                break;
            case "PLN":
                locale = new Locale("pl", "PL");
                break;
        }
        return locale;
    }

    public static double doubleFromFormattedCurrency(String currency, String amount) throws NumberFormatException {
        Locale l = Utils.getLocaleFromCurrency(currency);
        java.util.Currency curr = java.util.Currency.getInstance(l);
        curr.getSymbol(l);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(l);
        String thousand = decimalFormatSymbols.getDecimalSeparator() == '.' ? "," : ".";
        String x = amount.toString().replace(curr.getSymbol(l), "");
        x = x.replaceAll("\\s+", "");
        x = x.replace(thousand, "");
        x = x.replace(",", ".");
        if (x.equals("")) return 0;
        return Double.parseDouble(x);
    }

    public static String FormatNumber(double value, boolean abbreviate) {

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        numberFormat.setMaximumFractionDigits(2);
        if(!abbreviate) return numberFormat.format(value);
        int magnitude = value > 999999 ? 1000000 : value > 999 ? 1000 : value < -999 ? value < -999999 ? 1000000 : 1000 : 0;
        char postFix = magnitude == 1000000 ? 'M' : magnitude == 1000 ? 'k' : ' ';
        return magnitude > 0 ? numberFormat.format(value / magnitude) : numberFormat.format(value);
    }

    public static double roundToDouble(float value, int i) { return i > 0 ? (double) Math.round(value * i) / i : (int) value; }
}
