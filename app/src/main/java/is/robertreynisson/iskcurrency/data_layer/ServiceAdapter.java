package is.robertreynisson.iskcurrency.data_layer;

import is.robertreynisson.iskcurrency.data_layer.models.CurrencyResponse;
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
//                .setClient(new RetailCrestOkClient())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(request -> request.addHeader("Accept", "application/json"));
        RestAdapter adapter = restAdapter.build();
        bankAPI = adapter.create(BankAPI.class);
    }

    public Observable<CurrencyResponse> getRates(String bank){ return bankAPI.getRates(bank);}

}
