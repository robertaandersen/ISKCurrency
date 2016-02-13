package is.robertreynisson.iskcurrency.presenter_layer.Currency;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.databinding.CurrencyItemBinding;
import is.robertreynisson.iskcurrency.domain_layer.CurrencyViewModel;
import is.robertreynisson.iskcurrency.presenter_layer.models.Currency;
import is.robertreynisson.iskcurrency.utils.Utils;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by robert on 10.2.2016.
 */
public class CurrencyRecyclerAdapter extends RecyclerView.Adapter<CurrencyRecyclerAdapter.ViewHolder> {

    private final List<Currency> currencies;
    public static CompositeSubscription compositeSubscription = new CompositeSubscription();

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
        holder.bindConnection(currencies.get(position));
    }

    @Override
    public int getItemCount() {
        return currencies != null ? currencies.size() : 0;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final EditText input;
        private Currency currency;
        // each data item is just a string in this case
        public CurrencyItemBinding currencyItemBinding;

        public ViewHolder(CurrencyItemBinding currencyItemBinding) {
            super(currencyItemBinding.getRoot());
            this.input = (EditText) currencyItemBinding.getRoot().findViewById(R.id.currency_item_edit_text);
            this.input.setOnFocusChangeListener((v, hasFocus) -> {
                if (this.input.hasFocus()) this.input.setText("");
            });

            this.input.addTextChangedListener(
                    new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            input.setError(null);
                            if (!input.hasFocus() || input.getText().toString().equals("")) return;
                            try {
                                double val = Double.parseDouble(input.getText().toString()) * currency.currencyValue;
                                CurrencyViewModel.broadcastNewValue(String.valueOf(val));

                            } catch (NumberFormatException ex) {
                                input.setError(ex.getMessage());
                            }

                        }
                    }
            );
            this.currencyItemBinding = currencyItemBinding;
            compositeSubscription.add(CurrencyViewModel.getNewBaseAmount().subscribe(o -> updateItem(o.toString())));
        }


        public void bindConnection(Currency currency) {
            this.currency = currency;
            this.currencyItemBinding.setCurrency(currency);
        }

        private void updateItem(String newAmount) {
            if (input == null || input.hasFocus() || this.currency == null) return;
            try {
                input.setError(null);
                this.currency.baseCurrencyAmount = Double.parseDouble(newAmount);
                this.currencyItemBinding.setCurrency(currency);
            } catch (NumberFormatException ex) {
                input.setError(ex.getMessage());
            }
        }
    }

}
