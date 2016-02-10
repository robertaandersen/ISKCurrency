package is.robertreynisson.iskcurrency.utils;

import android.util.Log;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by robert on 10.2.2016.
 */
public class Utils {
    public static void logger(String tag, String msg) { Log.d(tag, msg); }

    public static String PrettyDateFormatter(long date) {
        // Do additional formatting here (e.g. prettytime etc.)
        return NiceDate(new LocalDateTime(date));
    }

    private static String NiceDate(LocalDateTime localDateTime) {
        Locale l = Locale.getDefault();
        return
                localDateTime.dayOfWeek().getAsShortText(l)
                        +" " + localDateTime.getDayOfMonth()
                        +" " +localDateTime.toString("MMM")
                        +" " + localDateTime.getYear()
                        +" "+ getClock(localDateTime);
    }

    public static String getClock(LocalDateTime localDateTime) {
        int min = localDateTime.get(DateTimeFieldType.minuteOfHour());
        String minutes = min < 10 ? "0"+min : min+"";
        return localDateTime.get(DateTimeFieldType.hourOfDay())
                +":"+ minutes
                +":"+localDateTime.get(DateTimeFieldType.secondOfMinute());
    }

    public static String PrettyDateFormatter(String timestamp) {
        return timestamp;
    }

    public static String CurrencyFormat(double value) {
        char thousand = 'k';
        Locale l = Locale.getDefault();

        //Saudi does not want their numbers formatted to Indian numbers
        if(l.getLanguage().equals("ar")) l = new Locale("DE", l.getCountry());

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(l);
        numberFormat.setMaximumFractionDigits(2);
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

}
