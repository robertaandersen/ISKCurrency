package is.robertreynisson.iskcurrency;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import is.robertreynisson.iskcurrency.utils.Utils;

/**
 * Created by robert on 10.2.2016.
 */
public class ISKCurrency extends Application {

    private static final String TAG = ISKCurrency.class.getSimpleName();

    private static ISKCurrency instance;
    public static ISKCurrency getInstance() { return instance; }


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.logger(TAG, "onCreate");
        instance = this;
        JodaTimeAndroid.init(this);
    }

    public static String getServerInfo() { return "http://apis.is";}

}
