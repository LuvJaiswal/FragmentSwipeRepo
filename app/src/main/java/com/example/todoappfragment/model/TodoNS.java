package com.example.todoappfragment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todoappfragment.db.TodoCursorWrapper;
import com.example.todoappfragment.db.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TodoNS {

    private static TodoNS todoInstance;

    //private ArrayList<Todo> mTodoList;

    private SQLiteDatabase mDatabase;
    private static Context mContext;


    private TodoNS(Context context){

        mDatabase = new DBHelper(context)
                .getWritableDatabase();


        /** mTodoList = new ArrayList<>();

        for (int i=0; i < 3; i++){
            Todo todo = new Todo();
            todo.setTitle("Todo title " + i);
            todo.setDetail("Detail for task " + todo.getId().toString());
            todo.setComplete(false);
            mTodoList.add(todo);
        }*/
    }

    public static TodoNS getInstance(Context context){
        mContext = context.getApplicationContext();
        if(todoInstance == null){
            todoInstance = new TodoNS(mContext);

        }
            return todoInstance;
    }


        private TodoCursorWrapper queryTodoList(String whereClause, String[] whereArgs) {
            //   private Cursor queryTodoList(String whereClause, String[] whereArgs) {
            Cursor cursor = mDatabase.query(
                    TodoCursorWrapper.TodoDbSchema.TodoTable.NAME,
                    null, // null for all columns
                    whereClause,
                    whereArgs,
                    null,
                    null,
                    null
            );

            return new TodoCursorWrapper(cursor);
//      return cursor;
        }

    public List<Todo> getTodos() {

        List<Todo> todoList = new ArrayList<>();

        TodoCursorWrapper cursor = queryTodoList(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                todoList.add(cursor.getTodo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return todoList;

    }

    public void addTodo(Todo todo){

        //mTodoList.add(todo);
        ContentValues values = getContentValues(todo);
        mDatabase.insert(TodoCursorWrapper.TodoDbSchema.TodoTable.NAME, null, values);

    }

    public Todo getTodo(UUID id) {

        TodoCursorWrapper cursor = queryTodoList(
                TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.UUID + " = ?",
                new String[] {id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTodo();
        } finally {
            cursor.close();
        }
    }

    public void updateTodo(Todo todo){
        String uuidString = todo.getId().toString();
        ContentValues values = getContentValues(todo);

        /* stop sql injection, pass uuidString to new String
        so, it is treated as string rather than code */
        mDatabase.update( TodoCursorWrapper.TodoDbSchema.TodoTable.NAME, values,
                TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }


    private static ContentValues getContentValues(Todo todo) {
        ContentValues values = new ContentValues();
        values.put(TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.UUID, todo.getId().toString());
        values.put(TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.TITLE, todo.getTitle());
        values.put(TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.DETAIL, todo.getDetail());
        values.put(TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.DATE, todo.getDate().getTime());
        values.put(TodoCursorWrapper.TodoDbSchema.TodoTable.Cols.IS_COMPLETE, todo.isComplete()==1 ? 1 : 0);

        return values;
    }

}
