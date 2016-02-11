package is.robertreynisson.iskcurrency.presenter_layer.Currency;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;
import java.util.concurrent.TimeUnit;

import is.robertreynisson.iskcurrency.ISKCurrency;
import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.databinding.CurrencyItemBinding;
import is.robertreynisson.iskcurrency.presenter_layer.models.Currency;
import is.robertreynisson.iskcurrency.utils.Utils;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by robert on 10.2.2016.
 */
public class CurrencyRecyclerAdapter extends RecyclerView.Adapter<CurrencyRecyclerAdapter.ViewHolder> {

    private final List<Currency> currencies;

    public CurrencyRecyclerAdapter(List<Currency> _currencyList) {
        this.currencies = _currencyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CurrencyItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.currency_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CurrencyView.foreignCurrencyBus.toObserverable().throttleLast(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(o -> currencies.get(position).baseCurrencyAmount = Double.parseDouble(o.toString()));

        EditText e = (EditText) holder.getBinding().getRoot().findViewById(R.id.currency_item_edit_text);
        e.addTextChangedListener(holder.getCurrencyChanged(currencies.get(position)));

        holder.bindConnection(currencies.get(position));
    }

    @Override
    public int getItemCount() {
        return currencies != null? currencies.size() : 0;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final EditText currencyEditText;
        private boolean currecyHasFocus = false;
        // each data item is just a string in this case
        public CurrencyItemBinding currencyItemBinding;
        private Currency currency;

        public ViewHolder(CurrencyItemBinding currencyBinding) {
            super(currencyBinding.getRoot());
            this.currencyEditText = (EditText) currencyBinding.getRoot().findViewById(R.id.currency_item_edit_text);
            this.currencyEditText.setOnFocusChangeListener((v, hasFocus) -> {
                this.currecyHasFocus = hasFocus;
                if(hasFocus){
                    this.currencyEditText.setText("0");
                }
                else{
                    this.currencyEditText.setText(currency.getExchangeString());
                }
            });
            this.currencyItemBinding = currencyBinding;
        }

        public void bindConnection(Currency currency) {
            this.currency = currency;
            this.currencyItemBinding.setCurrency(currency);
        }

        public CurrencyItemBinding getBinding() {
            return this.currencyItemBinding;
        }

        public TextWatcher getCurrencyChanged(Currency currency) {
            return new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!currecyHasFocus) return;
                    Utils.logger(currency.currencyAbbrevaton, "changed");
                    currencyEditText.setError(null);
                    if(!CurrencyView.foreignCurrencyChanged(currency, currencyEditText.getText().toString())) currencyEditText.setError(ISKCurrency.getInstance().getResources().getString(R.string.input_validation_error_digits));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
        }
    }

}
