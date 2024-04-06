package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private CountryData countryData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "Loading Countries");
        new LoadCountriesTask(this).execute();
        BottomNavigationView navView = findViewById(R.id.bottom_navigation_menu);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }


    public class LoadCountriesTask extends AsyncTask<Void, Void> {
        private Context context;

        public LoadCountriesTask(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i("LoadCountriesTask", "Loading Countries");
            try {
                InputStream inputStream = context.getAssets().open("country_continent.csv");
                CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));

                CountryData countryData = new CountryData(context);
                countryData.open();
                String[] nextLine;
                int numCountries = 0;
                while ((nextLine = csvReader.readNext()) != null) {
                    numCountries++;
                    String country = nextLine[0];
                    String continent = nextLine[1];
                    Country countryObj = new Country(country, continent);
                    countryData.storeCountry(countryObj); // Make sure this method exists and works correctly
                }
                countryData.close();

                csvReader.close();
                Log.i("LoadCountriesTask", String.format("%d",numCountries));
            } catch (Exception e) {
                Log.e("LoadCountriesTask", "Error loading countries from CSV", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
