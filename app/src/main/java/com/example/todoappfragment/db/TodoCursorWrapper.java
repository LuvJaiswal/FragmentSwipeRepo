package com.example.todoappfragment.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.todoappfragment.model.Todo;


import java.util.Date;
import java.util.UUID;

public class TodoCursorWrapper extends CursorWrapper {

    public TodoCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Todo getTodo() {
        String uuidString = getString(getColumnIndex(TodoDbSchema.TodoTable.Cols.UUID));
        String title = getString(getColumnIndex(TodoDbSchema.TodoTable.Cols.TITLE));
        String detail = getString(getColumnIndex(TodoDbSchema.TodoTable.Cols.DETAIL));
        Long date = getLong(getColumnIndex(TodoDbSchema.TodoTable.Cols.DATE));
        int isComplete = getInt(getColumnIndex(TodoDbSchema.TodoTable.Cols.IS_COMPLETE));

        Todo todo = new Todo(UUID.fromString(uuidString));
        todo.setTitle(title);
        todo.setDetail(detail);
        todo.setDate(new Date(date));
        todo.setComplete(isComplete);

        return todo;
    }

    public static class TodoDbSchema {

        public static final class TodoTable {
            public static final String NAME = "todos";
            public static final class Cols {
                public static final String UUID = "uuid";
                public static final  String TITLE = "title";
                public static final  String DETAIL = "detail";
                public static final  String DATE = "date";
                public static final  String IS_COMPLETE = "isComplete";
            }
        }
    }
}
