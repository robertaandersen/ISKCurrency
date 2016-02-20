package is.robertreynisson.iskcurrency.presenter_layer;

import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import is.robertreynisson.iskcurrency.ISKCurrency;
import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.data_layer.ServiceAdapter;

/**
 * Created by robert on 20.2.2016.
 */
public class MainToolbar extends MainActivity {


    public static Toolbar toolbar;

    public static void setupServiceSelector() {
        AppCompatSpinner serviceSelector = (AppCompatSpinner) toolbar.findViewById(R.id.toolbar_service_selector);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_dropdown_item, new String[]{"m5", "Arion", "Landsbanki", "Arion - Visa"});
        serviceSelector.setAdapter(spinnerAdapter);
        serviceSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ISKCurrency.setUrl("http://apis.is");
                        selectedBank = "m5";
                        break;
                    case 1:
                        ISKCurrency.setUrl("http://apis.is");
                        selectedBank = "arion";
                        break;
                    case 2:
                        ISKCurrency.setUrl("http://apis.is");
                        selectedBank = "lb";
                        break;
                    case 3:
                        ISKCurrency.setUrl("http://arionbanki.is");
                        break;

                }
                serviceAdapter = new ServiceAdapter(ISKCurrency.getServerInfo());
                reloadFragment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static void setTime(String s) {
        if (s == null || s.equals("")) return;
        String lastUpdate = ISKCurrency.getInstance().getResources().getString(R.string.update_msg);
        ((TextView)toolbar.findViewById(R.id.toolbar_time)).setText(lastUpdate + " " + s + " ago");
    }

}
