package is.robertreynisson.iskcurrency.data_layer;

import java.text.SimpleDateFormat;
import java.util.Date;

import is.robertreynisson.iskcurrency.data_layer.models.APISCurrencyResponse;
import is.robertreynisson.iskcurrency.data_layer.models.ArionCurrencyResponse;
import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by robert on 10.2.2016.
 */
public class ServiceAdapter {

    private static final String TAG = ServiceAdapter.class.getSimpleName();
    private final BankAPI bankAPI;

    public ServiceAdapter(String endpoint) {
        RestAdapter.Builder restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(request -> request.addHeader("Accept", "application/json"));
        RestAdapter adapter = restAdapter.build();
        bankAPI = adapter.create(BankAPI.class);
    }

    public Observable<APISCurrencyResponse> getAPISRates(String bank){ return bankAPI.getAPISRates(bank);}

    public Observable<ArionCurrencyResponse[]> getArionRates(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currAvailable = "ISK,USD,GBP,EUR,CAD,DKK,NOK,SEK,CHF,JPY";
        return bankAPI.getArionRates("GetCurrencies", sdf.format(d.getTime()), sdf.format(d.getTime()), "KortaGengi", currAvailable);
    }



}
