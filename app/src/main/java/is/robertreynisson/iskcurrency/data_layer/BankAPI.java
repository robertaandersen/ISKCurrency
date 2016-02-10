package is.robertreynisson.iskcurrency.data_layer;

import is.robertreynisson.iskcurrency.data_layer.models.CurrencyResponse;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by robert on 10.2.2016.
 */
public interface BankAPI {

    @GET("/currency/{bank}")
    Observable<CurrencyResponse> getRates(@Path("bank") String bank);
}
