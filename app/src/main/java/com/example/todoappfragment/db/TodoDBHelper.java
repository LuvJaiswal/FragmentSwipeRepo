package com.example.todoappfragment.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDBHelper  extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "todo.db";


    public TodoDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TodoCursorWrapper.TodoDbSchema.TodoTable.NAME + "(" +
                TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.UUID + ", " +
                TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.TITLE + ", " +
                TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.DETAIL + ", " +
                TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.DATE + ", " +
                TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.IS_COMPLETE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
