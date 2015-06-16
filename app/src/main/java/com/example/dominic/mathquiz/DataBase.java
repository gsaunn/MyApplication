package com.example.dominic.mathquiz;

/**
 * Created by dominic on 15/06/2015.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase
        extends SQLiteOpenHelper
{
    private static final String DATABASE = "highscores.db";
    private static final int VERSION = 2;

    public DataBase(Context paramContext)
    {
        super(paramContext, "highscores.db", null, 2);
    }

    public DataBase(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
    {
        super(paramContext, paramString, paramCursorFactory, paramInt);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
        paramSQLiteDatabase.execSQL("CREATE TABLE player ( id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT);");
        paramSQLiteDatabase.execSQL("CREATE TABLE scores ( id INTEGER PRIMARY KEY AUTOINCREMENT,category INTEGER, score INTEGER);");
        for (int i = 0;; i++)
        {
            if (i >= 7) {
                return;
            }
            paramSQLiteDatabase.execSQL("INSERT INTO scores (category,score) VALUES (" + i + "," + 0 + ")");
        }
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
        if ((paramInt1 == 1) && (paramInt2 == 2)) {
            paramSQLiteDatabase.execSQL("INSERT INTO scores (category,score) VALUES (6,0)");
        }
    }
}
