package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CountryDBHelper extends SQLiteOpenHelper{

    private static final String DEBUG_TAG = "CountryDBHelper";

    private static final String DB_NAME = "countryquiz.db";
    private static final int DB_VERSION = 1;


    public static final String TABLE_COUNTRIES = "countries";
    public static final String COUNTRIES_COLUMN_ID = "_id";
    public static final String COUNTRIES_COLUMN_COUNTRY = "country";
    public static final String COUNTRIES_COLUMN_CONTINENT = "continent";

    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_COLUMN_DATE = "date";
    public static final String QUIZZES_COLUMN_RESULT = "result";

    private static CountryDBHelper helperInstance;

    private static final String CREATE_COUNTRIES =
            "create table " + TABLE_COUNTRIES + " ("
                    + COUNTRIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COUNTRIES_COLUMN_COUNTRY + " TEXT, "
                    + COUNTRIES_COLUMN_CONTINENT + " TEXT "
                    + ")";

    private static final String CREATE_QUIZZES =
            "create table " + TABLE_QUIZZES + " ("
                    + QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZZES_COLUMN_DATE + " BIGINT, "
                    + QUIZZES_COLUMN_RESULT + " INT "
                    + ")";


    private CountryDBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }


    public static synchronized CountryDBHelper getInstance( Context context ) {
        if( helperInstance == null ) {
            helperInstance = new CountryDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }


    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_COUNTRIES );
        db.execSQL( CREATE_QUIZZES );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " created" );
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRIES + " created" );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_COUNTRIES );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRIES + " upgraded" );
    }
}
