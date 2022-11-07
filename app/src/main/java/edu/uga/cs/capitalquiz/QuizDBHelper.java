package edu.uga.cs.capitalquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizDBHelper";

    private static final String DB_NAME = "quiz.db";
    private static final int DB_VERSION = 2;
    private static QuizDBHelper helperInstance; // private reference to the single instance


    /**
     * defining names for the questions table and its columns
     */

    public static final String TABLE_QUESTIONS = "questions";
    public static final String QUESTIONS_COLUMN_ID = "_id";
    public static final String QUESTIONS_STATE_NAME = "name";
    public static final String QUESTIONS_STATE_CAPITAL = "capital";
    public static final String QUESTIONS_ADDITIONAL_CITY_1 = "additional1";
    public static final String QUESTIONS_ADDITIONAL_CITY_2 = "additional2";

    private static final String CREATE_QUESTIONS =
            "create table " + TABLE_QUESTIONS + " ("
            + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUESTIONS_STATE_NAME + " TEXT, "
            + QUESTIONS_STATE_CAPITAL + " TEXT, "
            + QUESTIONS_ADDITIONAL_CITY_1 + " TEXT, "
            + QUESTIONS_ADDITIONAL_CITY_2 + " TEXT"
            + ")";


    /**
     * defining names for the quizzes table and its columns
     */
    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_DATE = "date";
    public static final String QUIZZES_QUESTION_1 = "question1";
    public static final String QUIZZES_QUESTION_2 = "question2";
    public static final String QUIZZES_QUESTION_3 = "question3";
    public static final String QUIZZES_QUESTION_4 = "question4";
    public static final String QUIZZES_QUESTION_5 = "question5";
    public static final String QUIZZES_QUESTION_6 = "question6";
    public static final String QUIZZES_RESULT = "result";
    public static final String QUIZZES_ANSWERED = "answered";

    private static final String CREATE_QUIZZES =
            "create table " + TABLE_QUIZZES + " ("
            + QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZZES_DATE + " TEXT, "
            + QUIZZES_QUESTION_1 + " TEXT, "
            + QUIZZES_QUESTION_2 + " TEXT, "
            + QUIZZES_QUESTION_3 + " TEXT, "
            + QUIZZES_QUESTION_4 + " TEXT, "
            + QUIZZES_QUESTION_5 + " TEXT, "
            + QUIZZES_QUESTION_6 + " TEXT, "
            + QUIZZES_RESULT + " TEXT, "
            + QUIZZES_ANSWERED + " TEXT"
            + ")";


    // private constructor
    private QuizDBHelper (Context context) {super (context, DB_NAME, null, DB_VERSION);}

    // access method to the single instance of the class
    public static synchronized QuizDBHelper getInstance(Context context) {
        if (helperInstance == null)
            helperInstance = new QuizDBHelper(context.getApplicationContext());
        return helperInstance;
    } // getInstance

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTIONS);
        db.execSQL(CREATE_QUIZZES);
        Log.d(DEBUG_TAG, "Tables created");
    } // onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        onCreate(db);
        Log.d(DEBUG_TAG, "Tables upgraded");
    } // onUpgrade

} // class
