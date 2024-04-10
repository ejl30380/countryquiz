package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
// comment for testing to commit

public class CountryData {
    public static final String TAG = "Country Data";
    private SQLiteDatabase db;
    private SQLiteOpenHelper countryDbHelper;

    private static final String[] allColumns = {
            CountryDBHelper.COUNTRIES_COLUMN_ID,
            CountryDBHelper.COUNTRIES_COLUMN_COUNTRY,
            CountryDBHelper.COUNTRIES_COLUMN_CONTINENT
    };
    public CountryData(Context context) {
        this.countryDbHelper = CountryDBHelper.getInstance(context);
    }
    public void open() {
        db = countryDbHelper.getWritableDatabase();
    }
    public void close() {
        if (countryDbHelper != null)
            countryDbHelper.close();
    }
    public Country storeCountry( Country country ) {
        ContentValues values = new ContentValues();
        values.put( CountryDBHelper.COUNTRIES_COLUMN_COUNTRY, country.getCountry());
        values.put( CountryDBHelper.COUNTRIES_COLUMN_CONTINENT, country.getContinent() );

        long id = db.insert( CountryDBHelper.TABLE_COUNTRIES, null, values );
        country.setId( id );
        return country;
    }

    public ArrayList<Country> retrieveAllCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;
        Log.i(TAG, "Executed");
        try {
            cursor = db.query(CountryDBHelper.TABLE_COUNTRIES, allColumns,
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    if (cursor.getColumnCount() >= 2) {

                        columnIndex = cursor.getColumnIndex(CountryDBHelper.COUNTRIES_COLUMN_ID);
                        long id = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(CountryDBHelper.COUNTRIES_COLUMN_COUNTRY);
                        String countryName = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(CountryDBHelper.COUNTRIES_COLUMN_CONTINENT);
                        String continent = cursor.getString(columnIndex);

                        Country country = new Country(countryName, continent);
                        country.setId(id); // set the id (the primary key) of this object
                        countries.add(country);
                    }
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return countries;
    }
}
