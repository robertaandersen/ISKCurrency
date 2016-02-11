package is.robertreynisson.iskcurrency.presenter_layer.Currency;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.databinding.CurrencyItemBinding;
import is.robertreynisson.iskcurrency.domain_layer.CurrencyViewModel;
import is.robertreynisson.iskcurrency.presenter_layer.models.Currency;

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
        private Currency currency;
        // each data item is just a string in this case
        public CurrencyItemBinding currencyItemBinding;

        public ViewHolder(CurrencyItemBinding currencyItemBinding) {
            super(currencyItemBinding.getRoot());
            this.currencyItemBinding = currencyItemBinding;
        }

        public void bindConnection(Currency currency) {
            CurrencyViewModel.getNewBaseAmount().subscribe(o -> updateItem(o.toString()));
            this.currency = currency;
            this.currencyItemBinding.setCurrency(currency);
        }

        private void updateItem(String newAmount) {
            this.currency.baseCurrencyAmount = Double.parseDouble(newAmount);
            this.currencyItemBinding.setCurrency(currency);
        }
    }

}
