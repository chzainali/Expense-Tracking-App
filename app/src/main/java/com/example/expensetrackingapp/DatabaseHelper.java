package com.example.expensetrackingapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "expense_tracking_app.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "amount_table";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DETAILS = "details";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TYPE = "type";


    private static final String CREATE_TABLE_BUDGET = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " INTEGER NOT NULL, " +
            COLUMN_AMOUNT + " INTEGER NOT NULL, " +
            COLUMN_DETAILS + " TEXT NOT NULL, " +
            COLUMN_DATE + " TEXT NOT NULL, " +
            COLUMN_TYPE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_BUDGET);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addIncomeExpenseByType(IncomeExpenseModel incomeExpenseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, incomeExpenseModel.getTitle());
        values.put(COLUMN_AMOUNT, incomeExpenseModel.getAmount());
        values.put(COLUMN_DETAILS, incomeExpenseModel.getDetails());
        values.put(COLUMN_DATE, incomeExpenseModel.getDateTime());
        values.put(COLUMN_TYPE, incomeExpenseModel.getType());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public ArrayList<IncomeExpenseModel> getIncomesExpensesByType(String type) {
        ArrayList<IncomeExpenseModel> incomeExpenseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = '" + type + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                IncomeExpenseModel incomeExpenseModel = new IncomeExpenseModel();
                incomeExpenseModel.setAmountId(cursor.getString(0));
                incomeExpenseModel.setTitle(cursor.getString(1));
                incomeExpenseModel.setAmount(cursor.getString(2));
                incomeExpenseModel.setDetails(cursor.getString(3));
                incomeExpenseModel.setDateTime(cursor.getString(4));
                incomeExpenseModel.setType(cursor.getString(5));
                incomeExpenseList.add(incomeExpenseModel);
            } while (cursor.moveToNext());
        }
        return incomeExpenseList;
    }

    public int updateIncomeExpense(IncomeExpenseModel incomeExpenseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, incomeExpenseModel.getTitle());
        values.put(COLUMN_AMOUNT, incomeExpenseModel.getAmount());
        values.put(COLUMN_DETAILS, incomeExpenseModel.getDetails());
        values.put(COLUMN_DATE, incomeExpenseModel.getDateTime());
        values.put(COLUMN_TYPE, incomeExpenseModel.getType());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(incomeExpenseModel.getAmountId())});
    }

    public void deleteIncomeExpense(IncomeExpenseModel incomeExpenseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(incomeExpenseModel.getAmountId())});
        db.close();
    }

    public int getTotalIncomeExpenseByType(String type) {
        int total = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = '" + type + "'", null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }
        cursor.close();
        return total;
    }
}

