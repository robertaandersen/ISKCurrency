package is.robertreynisson.iskcurrency.presenter_layer.Currency;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import is.robertreynisson.iskcurrency.ISKCurrency;
import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.domain_layer.CurrencyViewModel;
import is.robertreynisson.iskcurrency.presenter_layer.models.Currency;
import is.robertreynisson.iskcurrency.utils.RxUIBinderUtil;

/**
 * Created by robert on 10.2.2016.
 */
public class CurrencyView extends FrameLayout {

    private static final String TAG = CurrencyView.class.getSimpleName();

    private CurrencyRecyclerAdapter currencyRecyclerAdpater;

    //A util that contains this views subscriptions
    //and maintains them throughout the lifecycle.
    private RxUIBinderUtil rxUIBinderUtil = new RxUIBinderUtil(this);

    @Bind(R.id.currency_view_recycler)
    public RecyclerView recyclerView;

    @Bind(R.id.currency_base_value)
    public EditText baseValue;

    @OnTextChanged(R.id.currency_base_value)
    public void baseValueChange() {
        baseValue.setError(null);
        if(!CurrencyViewModel.broadcastNewValue(baseValue.getText().toString())) {
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
        this.requestFocus();
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

}
