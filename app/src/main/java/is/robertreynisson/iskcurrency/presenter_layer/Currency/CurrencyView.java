package is.robertreynisson.iskcurrency.presenter_layer.Currency;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import is.robertreynisson.iskcurrency.ISKCurrency;
import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.domain_layer.CurrencyViewModel;
import is.robertreynisson.iskcurrency.presenter_layer.models.Currency;
import is.robertreynisson.iskcurrency.utils.RxBus;
import is.robertreynisson.iskcurrency.utils.RxUIBinderUtil;
import is.robertreynisson.iskcurrency.utils.Utils;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by robert on 10.2.2016.
 */
public class CurrencyView extends FrameLayout {

    private static final String TAG = CurrencyView.class.getSimpleName();

    private CurrencyRecyclerAdapter currencyRecyclerAdpater;

    //A util that contains this views subscriptions
    //and maintains them throughout the lifecycle.
    private RxUIBinderUtil rxUIBinderUtil = new RxUIBinderUtil(this);

    public static RxBus foreignCurrencyBus = new RxBus();
    public static RxBus baseCurrencyBus = new RxBus();
    private Currency baseCurrency;

    @Bind(R.id.currency_view_recycler)
    public RecyclerView recyclerView;

    @Bind(R.id.currency_base_value)
    public EditText baseValue;
    private boolean baseValueFocus;

    @OnTextChanged(R.id.currency_base_value)
    public void baseValueChange() {
        if (!baseValueFocus) return;
        String xxx = baseValue.getText().toString();
        baseValue.setError(null);
        if (validate(xxx)) {
            foreignCurrencyBus.send(xxx);
            currencyRecyclerAdpater.notifyDataSetChanged();
        } else {
            baseValue.setError(ISKCurrency.getInstance().getResources().getString(R.string.input_validation_error_digits));
        }
    }

    public CurrencyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        baseCurrency = new Currency();
        baseCurrency.baseCurrencyAmount = 1000;
        baseCurrency.currencyAbbrevaton = "ISK";
        baseCurrency.currencyValue = 1;
        baseCurrencyBus.toObserverable().throttleLast(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(o ->
        {
            baseValue.setText(Utils.CurrencyFormat(Double.parseDouble(o.toString()), new Locale("is", "IS"), false));
            baseCurrency.baseCurrencyAmount = Double.parseDouble(o.toString());
            foreignCurrencyBus.send(Double.parseDouble(o.toString()));
            currencyRecyclerAdpater.notifyDataSetChanged();
        });
        baseValue.setOnFocusChangeListener((v, hasFocus) -> {
            baseValueFocus = hasFocus;
            if (hasFocus) {
                baseValue.setText(String.valueOf(baseCurrency.getExchange()));
            }
        });
    }


    //This takes care of subscribing UI to the
    //viewModels observable objects via
    //the BinderUtil.
    public void setViewModel(CurrencyViewModel viewModel) {
        rxUIBinderUtil.clear();
        if (viewModel != null) {
            rxUIBinderUtil.bindProperty(viewModel.getCurrencies(), this::updateUI);
        }
    }

    //This will be the method called by the observable
    //and is passed into the RxUIBinderUtil via this::updateUI
    //syntax (using Java 1.8 and RetroLambda Method Reference syntax)
    private void updateUI(List<Currency> currencyList) {
        currencyRecyclerAdpater = new CurrencyRecyclerAdapter(currencyList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ISKCurrency.getInstance().getApplicationContext());
        this.recyclerView.invalidate();
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(this.currencyRecyclerAdpater);

    }

    public static boolean foreignCurrencyChanged(Currency currency, String text) {
        if (text == null || text.equals("")) return true;
        double value = 0;
        try {
            value = Utils.doubleFromFormattedCurrency(currency.currencyAbbrevaton, text);
            baseCurrencyBus.send(value * currency.currencyValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validate(String xxx) {
        if (xxx == null || xxx.equals("")) return false;
        try {
            Utils.doubleFromFormattedCurrency("ISK", xxx);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
