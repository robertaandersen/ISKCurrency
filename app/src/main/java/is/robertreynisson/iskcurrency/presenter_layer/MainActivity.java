package is.robertreynisson.iskcurrency.presenter_layer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import is.robertreynisson.iskcurrency.ISKCurrency;
import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.data_layer.ServiceAdapter;
import is.robertreynisson.iskcurrency.presenter_layer.Currency.CurrencyFragment;


public class MainActivity extends AppCompatActivity {

    public static ServiceAdapter serviceAdapter;
    public static MainActivity mainActivity;

    private static CurrencyFragment currencyFragment;

    public static TextView errorMSG;
    public static RelativeLayout fragmentContainer;
    public static String selectedBank = "m5";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceAdapter = new ServiceAdapter(ISKCurrency.getServerInfo());
        mainActivity = this;
        setContentView(R.layout.activity_main);
        MainToolbar.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(MainToolbar.toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");
        MainToolbar.setupServiceSelector();

        errorMSG = (TextView) findViewById(R.id.main_errorMSG);
        fragmentContainer = (RelativeLayout) findViewById(R.id.container);
        currencyFragment = new CurrencyFragment();
        loadFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private static void loadFragment() {
        if (currencyFragment == null) currencyFragment = new CurrencyFragment();
        mainActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, currencyFragment, currencyFragment.getTag())
                .commit();
    }

    public static void reloadFragment() {
        if (currencyFragment == null) loadFragment();
        else {
            currencyFragment.reloadFragment();
        }
    }

    public static boolean isOnline() {
        errorMSG.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.VISIBLE);
        ConnectivityManager cm = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void setOffLine() {
        errorMSG.setVisibility(View.VISIBLE);
        fragmentContainer.setVisibility(View.GONE);
    }

    public static void showError(String message) {
        if (message != null) {
            errorMSG.setText(message);
            setOffLine();
            return;
        }
        errorMSG.setVisibility(View.GONE);
    }

    public static String getBank() {
        return selectedBank;
    }
}
