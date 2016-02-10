package is.robertreynisson.iskcurrency.presenter_layer.Currency;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.databinding.CurrencyItemBinding;
import is.robertreynisson.iskcurrency.presenter_layer.MainActivity;
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
        // each data item is just a string in this case
        public CurrencyItemBinding currency;

        public ViewHolder(CurrencyItemBinding currency) {
            super(currency.getRoot());
            this.currency = currency;
        }

        public void bindConnection(Currency currency) {
            this.currency.setCurrency(currency);
        }
    }

}
