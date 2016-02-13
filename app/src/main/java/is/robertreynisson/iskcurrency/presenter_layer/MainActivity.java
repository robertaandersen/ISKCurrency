package is.robertreynisson.iskcurrency.presenter_layer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import is.robertreynisson.iskcurrency.ISKCurrency;
import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.data_layer.ServiceAdapter;
import is.robertreynisson.iskcurrency.presenter_layer.Currency.CurrencyFragment;
import is.robertreynisson.iskcurrency.utils.Utils;


public class MainActivity extends AppCompatActivity {

    public static ServiceAdapter serviceAdapter;
    public static MainActivity mainActivity;

    private static CurrencyFragment currencyFragment;

    static TextView time;
    public static TextView noNetwork;
    public static LinearLayout fragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        serviceAdapter = new ServiceAdapter(ISKCurrency.getServerInfo());
        mainActivity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        time = (TextView) toolbar.findViewById(R.id.toolbar_time);
        noNetwork = (TextView) findViewById(R.id.no_network);
        fragmentContainer = (LinearLayout) findViewById(R.id.container);
//        setSupportActionBar(toolbar);
//        if(getSupportActionBar() != null) getSupportActionBar().setTitle("ISK Currency converter");
        currencyFragment = new CurrencyFragment();
        loadFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void setTime(){
        Date d = new Date();
        String lastUpdate = ISKCurrency.getInstance().getResources().getString(R.string.update_msg);
        time.setText(lastUpdate+" "+Utils.PrettyDateFormatter(d.getTime()));
    }

    private static void loadFragment() {
        if(currencyFragment == null) reloadFragment();
        mainActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, currencyFragment, currencyFragment.getTag())
                .commit();

    }

    public static void reloadFragment() {
        if(currencyFragment == null) loadFragment();
        else {
            currencyFragment.reloadFragment();
        }
    }

    public static boolean isOnline() {
        noNetwork.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.VISIBLE);
        ConnectivityManager cm = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void setOffLine() {
        noNetwork.setVisibility(View.VISIBLE);
        fragmentContainer.setVisibility(View.GONE);
    }
}
