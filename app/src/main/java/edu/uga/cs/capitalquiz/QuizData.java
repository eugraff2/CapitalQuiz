package edu.uga.cs.capitalquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class facilitates the storing and restoring of Quizzes
 * Along with the reading of questions from a CSV file
 */
public class QuizData {

    public static final String DEBUG_TAG = "QuizData";

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDBHelper;
    private static final String[] quizColumns = {
            QuizDBHelper.QUIZZES_COLUMN_ID,
            QuizDBHelper.QUIZZES_DATE,
            QuizDBHelper.QUIZZES_QUESTION_1,
            QuizDBHelper.QUIZZES_QUESTION_2,
            QuizDBHelper.QUIZZES_QUESTION_3,
            QuizDBHelper.QUIZZES_QUESTION_4,
            QuizDBHelper.QUIZZES_QUESTION_5,
            QuizDBHelper.QUIZZES_QUESTION_6,
            QuizDBHelper.QUIZZES_RESULT,
            QuizDBHelper.QUIZZES_ANSWERED
    };

    private static final String[] questionColumns =  {
            QuizDBHelper.QUESTIONS_STATE_NAME,
            QuizDBHelper.QUESTIONS_STATE_CAPITAL,
            QuizDBHelper.QUESTIONS_ADDITIONAL_CITY_1,
            QuizDBHelper.QUESTIONS_ADDITIONAL_CITY_2
    };



    public QuizData(Context context) {
        this.quizDBHelper = QuizDBHelper.getInstance(context);
    } // constructor

    // method to open database
    public void open() {
        db = quizDBHelper.getWritableDatabase();
    }

    // method to close database
    public void close() {
        if (quizDBHelper != null) {
            quizDBHelper.close();
        }
    }

    public boolean isDBOpen() { return db.isOpen(); }

    public List<Quiz> retrieveAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        // Execute the select query and get the Cursor to iterate over the retrieved rows
        try {
            cursor = db.query(QuizDBHelper.TABLE_QUIZZES, null,
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext() ) {

                    // get all attributes for quiz
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_ID);
                    long id = cursor.getLong(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_DATE);
                    String date = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_QUESTION_1);
                    String q1 = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_QUESTION_2);
                    String q2 = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_QUESTION_3);
                    String q3 = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_QUESTION_4);
                    String q4 = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_QUESTION_5);
                    String q5 = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_QUESTION_6);
                    String q6 = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_RESULT);
                    Double result = cursor.getDouble(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_ANSWERED);
                    int answered = cursor.getInt(columnIndex);
                    // creation of new Quiz object
                    Quiz quiz = new Quiz(date, result, answered, q1, q2, q3, q4, q5, q6);
                    quiz.setId(id);
                    quizzes.add(quiz);
                    Log.d(DEBUG_TAG, "quiz added: " + quiz.getDate());

                } // while loop

            } // if cursor not null or empty

            if( cursor != null )
                Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
            else
                Log.d( DEBUG_TAG, "Number of records from DB: 0" );

        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } // try catch
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return quizzes;
    } // retrieve quizzes

    public Quiz storeQuiz (Quiz quiz) {
        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the Quiz argument.
        // This is how we are providing persistence to a Quiz (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.QUIZZES_DATE, quiz.getDate());
        values.put(QuizDBHelper.QUIZZES_QUESTION_1, quiz.getQ1());
        values.put(QuizDBHelper.QUIZZES_QUESTION_2, quiz.getQ2());
        values.put(QuizDBHelper.QUIZZES_QUESTION_3, quiz.getQ3());
        values.put(QuizDBHelper.QUIZZES_QUESTION_4, quiz.getQ4());
        values.put(QuizDBHelper.QUIZZES_QUESTION_5, quiz.getQ5());
        values.put(QuizDBHelper.QUIZZES_QUESTION_6, quiz.getQ6());
        values.put(QuizDBHelper.QUIZZES_RESULT, quiz.getResult());
        values.put(QuizDBHelper.QUIZZES_ANSWERED, quiz.getNumAnswered());

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert(QuizDBHelper.TABLE_QUIZZES, null, values);
        quiz.setId(id);

        Log.d( DEBUG_TAG, "Stored new quiz with id: " + String.valueOf( quiz.getId() ) );
        return quiz;

    } // storeQuiz

    public Question storeQuestion (Question question) {

        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.QUESTIONS_STATE_NAME, question.getName());
        values.put(QuizDBHelper.QUESTIONS_STATE_CAPITAL, question.getCapital());
        values.put(QuizDBHelper.QUESTIONS_ADDITIONAL_CITY_1, question.getAdditional1());
        values.put(QuizDBHelper.QUESTIONS_ADDITIONAL_CITY_2, question.getAdditional2());


        long id = db.insert(QuizDBHelper.TABLE_QUESTIONS, null, values);
        question.setId(id);

        return question;

    } // storeQuestion

    public List<Question> generateQuestions() {

        ArrayList<Question> questions = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        // Execute the select query and get the Cursor to iterate over the retrieved rows
        try {
            cursor = db.query(QuizDBHelper.TABLE_QUESTIONS, null,
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext() ) {

                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_COLUMN_ID);
                    long id = cursor.getLong(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_STATE_NAME);
                    String name = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_STATE_CAPITAL);
                    String capital = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_ADDITIONAL_CITY_1);
                    String additional1 = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_ADDITIONAL_CITY_2);
                    String additional2 = cursor.getString(columnIndex);
                    // creation of new Question object

                    Question question = new Question(name, capital, additional1, additional2);
                    question.setId(id);

                    questions.add(question);
                    Log.d(DEBUG_TAG, "question added: " + question.getName());


                } // while loop

            } // if cursor not null

        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } // try catch
        finally {
            if (cursor != null)
                cursor.close();
        }

        Log.d(DEBUG_TAG, "List size : " + questions.size());
        return questions;

    } // generateQuestions


    public List<Question> getSpecificQuestions(ArrayList<String> names) {
        ArrayList<Question> questions = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        // Execute the select query and get the Cursor to iterate over the retrieved rows
        try {
            cursor = db.query(QuizDBHelper.TABLE_QUESTIONS, null,
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext() ) {

                    // get all attributes for question
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_COLUMN_ID);
                    long id = cursor.getLong(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_STATE_NAME);
                    String name = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_STATE_CAPITAL);
                    String capital = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_ADDITIONAL_CITY_1);
                    String additional1 = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_ADDITIONAL_CITY_2);
                    String additional2 = cursor.getString(columnIndex);
                    // creation of new Question object

                    Question question = new Question(name, capital, additional1, additional2);
                    question.setId(id);

                    for (int i = 0; i < names.size(); i++) {
                        if (name.equals(names.get(i))) {
                            questions.add(question);
                        }
                    }

                } // while loop

            } // if cursor not null

        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } // try catch
        finally {
            if (cursor != null)
                cursor.close();
        }

        return questions;
    }


    public void deleteAllQuestions() {
        db.execSQL("delete from " + QuizDBHelper.TABLE_QUESTIONS);
    }



} // QuizData
