package edu.wit.mobileapp.autismapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.wit.mobileapp.autismapp.Quiz_Contract.*;

import java.util.ArrayList;

public class Quiz_Database_Helper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Quiz_Location.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public Quiz_Database_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    /**
    private void fillQuestionsTable() {
        Quiz_Question q1 = new Quiz_Question("A is correct", "A", "B", "C", "D", 3);
        addQuestion(q1);
        Quiz_Question q2 = new Quiz_Question("B is correct", "A", "B", "C", "D", 2);
        addQuestion(q2);
        Quiz_Question q3 = new Quiz_Question("C is correct", "A", "B", "C", "D", 4);
        addQuestion(q3);
        Quiz_Question q4 = new Quiz_Question("D is correct", "A", "B", "C", "D", 1);
        addQuestion(q4);
    } */

    // A. Home;  B. School;  C. Park;  D. Museum
    private void fillQuestionsTable() {
        Quiz_Question q1 = new Quiz_Question("Park", "A", "B", "C", "D", 3);
        addQuestion(q1);
        Quiz_Question q2 = new Quiz_Question("School", "A", "B", "C", "D", 2);
        addQuestion(q2);
        Quiz_Question q3 = new Quiz_Question("Museum", "A", "B", "C", "D", 4);
        addQuestion(q3);
        Quiz_Question q4 = new Quiz_Question("Home", "A", "B", "C", "D", 1);
        addQuestion(q4);
    }

    private void addQuestion(Quiz_Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Quiz_Question> getAllQuestions() {
        ArrayList<Quiz_Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Quiz_Question question = new Quiz_Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}