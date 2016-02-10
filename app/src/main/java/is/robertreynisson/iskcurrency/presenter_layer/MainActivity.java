package is.robertreynisson.iskcurrency.presenter_layer;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import is.robertreynisson.iskcurrency.ISKCurrency;
import is.robertreynisson.iskcurrency.R;
import is.robertreynisson.iskcurrency.data_layer.ServiceAdapter;
import is.robertreynisson.iskcurrency.presenter_layer.Currency.CurrencyFragment;

public class MainActivity extends AppCompatActivity {

    public static ServiceAdapter serviceAdapter;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceAdapter = new ServiceAdapter(ISKCurrency.getServerInfo());
        mainActivity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);
        CurrencyFragment currencyFragment = new CurrencyFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, currencyFragment, currencyFragment.getTag())
                .commit();
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
}
