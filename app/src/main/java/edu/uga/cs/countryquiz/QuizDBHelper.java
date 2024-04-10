package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "QuizzesDBHelper";
    private static final String DB_NAME = "countryquiz.db";
    private static final int DB_VERSION = 1;

    // Define all names (strings) for table and column names.
    // This will be useful if we want to change these names later.
    public static final String TABLE_COUNTRIES = "countries";
    public static final String COUNTRIES_COLUMN_ID = "_id";
    public static final String COUNTRIES_COLUMN_COUNTRY = "country";
    public static final String COUNTRIES_COLUMN_CONTINENT = "continent";

    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_COLUMN_DATE = "date";
    public static final String QUIZZES_COLUMN_RESULT = "result";

    // This is a reference to the only instance for the helper.
    private static QuizDBHelper helperInstance;

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

    private QuizDBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }


    public static synchronized QuizDBHelper getInstance( Context context ) {
        if( helperInstance == null ) {
            helperInstance = new QuizDBHelper( context.getApplicationContext() );
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
        db.execSQL( "drop table if exists " + TABLE_QUIZZES );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " upgraded" );
    }
}