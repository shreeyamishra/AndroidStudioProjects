package edu.miami.c11926684.miniapp15;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by woodyjean-louis on 11/6/16.
 */

public class DataBase {

    public static final String DATABASE_NAME = "Thoughts.db";
    private static final int DATABASE_VERSION = 9;

    private static final String THOUGHTS_TABLE_NAME = "Thoughts";
    private static final String CREATE_THOUGHTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + THOUGHTS_TABLE_NAME +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "thought TEXT NOT NULL," +
                    "date STRING," +
                    "time STRING" +
                    ");";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase theDB;
    //-----------------------------------------------------------------------------
    public DataBase(Context theContext) {

        dbHelper = new DatabaseHelper(theContext);
        theDB = dbHelper.getWritableDatabase();
    }
    //-----------------------------------------------------------------------------
    public void close() {

        dbHelper.close();
        theDB.close();
    }
    //-----------------------------------------------------------------------------
    public boolean addThought(ContentValues thought) {

        return(theDB.insert(THOUGHTS_TABLE_NAME,null,thought) >= 0);
    }
    //-----------------------------------------------------------------------------
    public boolean updateThought(long textID,ContentValues thought) {

        return(theDB.update(THOUGHTS_TABLE_NAME,thought,
                "_id =" + textID,null) > 0);
    }
    //-----------------------------------------------------------------------------
    public boolean deleteThought(long id) {

        return(theDB.delete(THOUGHTS_TABLE_NAME,"_id =" + id,
                null) > 0);
    }
    //-----------------------------------------------------------------------------
    public Cursor fetchAllThoughts() {

        String[] fieldNames = {"_id","thought","date", "time"};

        return(theDB.query(THOUGHTS_TABLE_NAME,fieldNames,null,null,
                null,null,"thought"));
    }
    //-----------------------------------------------------------------------------
    public ContentValues getThoughtById(long thoughtId) {

        Cursor cursor;
        ContentValues thought;

        cursor = theDB.query(THOUGHTS_TABLE_NAME,null,
                "_id = \"" + thoughtId + "\"",null,null,null,null);
        thought = thoughtDataFromCursor(cursor);
        cursor.close();
        return(thought);
    }
    //----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    private ContentValues thoughtDataFromCursor(Cursor cursor) {

        String[] fieldNames;
        int index;
        ContentValues thoughtData;

        if (cursor != null && cursor.moveToFirst()) {
            fieldNames = cursor.getColumnNames();
            thoughtData = new ContentValues();
            for (index=0;index < fieldNames.length;index++) {
                if (fieldNames[index].equals("_id")) {
                    thoughtData.put("_id",cursor.getInt(index));
                } else if (fieldNames[index].equals("thought")) {
                    thoughtData.put("thought",cursor.getString(index));
                } else if (fieldNames[index].equals("date")) {
                    thoughtData.put("date",cursor.getInt(index));
                } else if (fieldNames[index].equals("time")) {
                    thoughtData.put("time",cursor.getInt(index));
                }
            }
            return(thoughtData);
        } else {
            return(null);
        }
    }
    //=============================================================================
    private static class DatabaseHelper extends SQLiteOpenHelper {
        //-------------------------------------------------------------------------
        public DatabaseHelper(Context context) {

            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        //-------------------------------------------------------------------------
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_THOUGHTS_TABLE);
        }
        //-------------------------------------------------------------------------
        @Override
        public void onOpen(SQLiteDatabase db) {

            super.onOpen(db);
        }
        //-------------------------------------------------------------------------
        public void onUpgrade(SQLiteDatabase db,int oldVersion,
                              int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + THOUGHTS_TABLE_NAME);
            onCreate(db);
        }
        //-------------------------------------------------------------------------
    }
}
