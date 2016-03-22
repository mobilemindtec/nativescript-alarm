package br.com.mobilemind.nativescript.alarmnotification.sql;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DBHelper extends SQLiteOpenHelper {

   public DBHelper(Context context, String dbName, int dbVersion)
   {
      super(context, dbName , null, dbVersion);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   }
   
   public Cursor getData(String query){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery(query, null);
      return res;
   }
}