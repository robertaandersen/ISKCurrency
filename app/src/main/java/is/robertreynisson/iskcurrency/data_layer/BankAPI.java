package is.robertreynisson.iskcurrency.data_layer;

import java.util.List;

import is.robertreynisson.iskcurrency.data_layer.models.APISCurrencyResponse;
import is.robertreynisson.iskcurrency.data_layer.models.ArionCurrencyResponse;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by robert on 10.2.2016.
 */
public interface BankAPI {

    //apis.is
    @GET("/currency/{bank}")
    Observable<APISCurrencyResponse> getAPISRates(@Path("bank") String bank);

    //https://www.arionbanki.is
    //@GET("/Webservice/PortalCurrency.ashx?m=GetCurrencies&beginDate=2016-02-14&finalDate=2016-02-14&currencyType=KortaGengi&currenciesAvailable=ISK,USD,GBP,EUR,CAD,DKK,NOK,SEK,CHF,JPY")
    @GET("/Webservice/PortalCurrency.ashx")
    Observable<ArionCurrencyResponse[]> getArionRates(@Query("m") String method, @Query("beginDate") String dateFrom, @Query("finalDate") String finalDate, @Query("currencyType") String currencyType, @Query("currenciesAvailable") String currencies);

}
