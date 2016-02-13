package is.robertreynisson.iskcurrency.presenter_layer.Currency;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import is.robertreynisson.iskcurrency.ISKCurrency;
import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.domain_layer.CurrencyViewModel;
import is.robertreynisson.iskcurrency.presenter_layer.MainActivity;
import is.robertreynisson.iskcurrency.presenter_layer.models.Currency;
import is.robertreynisson.iskcurrency.utils.RxUIBinderUtil;
import is.robertreynisson.iskcurrency.utils.Utils;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by robert on 10.2.2016.
 */
public class CurrencyView extends FrameLayout {

    private static final String TAG = CurrencyView.class.getSimpleName();

    private CurrencyRecyclerAdapter currencyRecyclerAdpater;

    //A util that contains this views subscriptions
    //and maintains them throughout the lifecycle.
    private RxUIBinderUtil rxUIBinderUtil = new RxUIBinderUtil(this);

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.currency_view_recycler)
    public RecyclerView recyclerView;

    @Bind(R.id.currency_base_value)
    public EditText baseValue;
    private CompositeSubscription compositeSubscription;

    @OnTextChanged(R.id.currency_base_value)
    public void baseValueChange() {
        baseValue.setError(null);
        if(!baseValue.hasFocus() || baseValue.getText().toString().equals("")) return;
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
        progressBar.setVisibility(VISIBLE);
        baseValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus()) baseValue.setText("");
        });
        subscribeToBus();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            currencyRecyclerAdpater.compositeSubscription.clear();
            currencyRecyclerAdpater = null;
            compositeSubscription.clear();
            MainActivity.reloadFragment();
        });
    }

    private void subscribeToBus() {
        if(compositeSubscription == null) compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(CurrencyViewModel.getNewBaseAmount().subscribe(o -> {
            baseValue.setError(null);
            if (!baseValue.hasFocus()) {
                try {
                    baseValue.setText(Utils.CurrencyFormat(Double.parseDouble(o.toString()), "ISK", false));
                } catch (NumberFormatException ex) {
                    baseValue.setError(ex.getMessage());
                }
            }
        }));
    }


    //This takes care of subscribing UI to the
    //viewModels observable objects via
    //the BinderUtil.
    public void setViewModel(CurrencyViewModel viewModel) {
        rxUIBinderUtil.clear();
        compositeSubscription.clear();
        if (viewModel != null) {
            subscribeToBus();
            rxUIBinderUtil.bindProperty(viewModel.getCurrencies(), this::updateUI);
        }
    }

    //This will be the method called by the observable
    //and is passed into the RxUIBinderUtil via this::updateUI
    //syntax (using Java 1.8 and RetroLambda Method Reference syntax)
    private void updateUI(List<Currency> currencyList) {
        progressBar.setVisibility(GONE);
        swipeRefreshLayout.setRefreshing(false);
        MainActivity.setTime();
        baseValue.setText("1000");
        currencyRecyclerAdpater = new CurrencyRecyclerAdapter(currencyList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ISKCurrency.getInstance().getApplicationContext());
        this.recyclerView.invalidate();
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(this.currencyRecyclerAdpater);
    }
}
