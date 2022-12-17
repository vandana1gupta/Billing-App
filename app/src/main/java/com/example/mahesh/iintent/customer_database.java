package com.example.mahesh.iintent;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class customer_database extends SQLiteOpenHelper {


    private static final String TABLE_NAME = "customer";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";

    private static final String COL_date = "date";
    private static final String COL_time = "time";
    private static final String COL_service = "service";

    private static final String COL_item1 = "item1";
    private static final String COL_item1_qty = "qty1";
    private static final String COL_item1_amt = "amt1";

    private static final String COL_item2 = "item2";
    private static final String COL_item2_qty = "qty2";
    private static final String COL_item2_amt = "amt2";

    private static final String COL_item3 = "item3";
    private static final String COL_item3_qty = "qty3";
    private static final String COL_item3_amt = "amt3";

    private static final String COL_item4 = "item4";
    private static final String COL_item4_qty = "qty4";
    private static final String COL_item4_amt = "amt4";

    private static final String COL_item5 = "item5";
    private static final String COL_item5_qty = "qty5";
    private static final String COL_item5_amt = "amt5";

    private static final String COL_total = "total";



    public customer_database(Context context) { super(context, TABLE_NAME, null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "name TEXT,date TEXT,time TEXT,service TEXT,"+
                "item1 TEXT, qty1 TEXT, amt1 TEXT, item2 TEXT, qty2 TEXT, amt2 TEXT, item3 TEXT, qty3 TEXT,amt3 TEXT,"+
                 " item4 TEXT,qty4 TEXT,amt4 TEXT,item5 TEXT,qty5 TEXT,amt5 TEXT,total TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addData(String one) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, one);
        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addData2(String name_str,String date_str,String time_str,String service_str,String item1_str,String qty1_str,String amt1_str,
                            String item2_str,String qty2_str,String amt2_str,String item3_str,String qty3_str,String amt3_str,
                            String item4_str,String qty4_str,String amt4_str,String item5_str,String qty5_str,String amt5_str,
                            String total_str)
   // public boolean addData2(String name_str)
    {
        SQLiteDatabase db2 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name_str);

        contentValues.put(COL_date, date_str);
        contentValues.put(COL_time, time_str);
        contentValues.put(COL_service, service_str);

        contentValues.put(COL_item1, item1_str);
        contentValues.put(COL_item1_qty, qty1_str);
        contentValues.put(COL_item1_amt, amt1_str);

        contentValues.put(COL_item2, item2_str);
        contentValues.put(COL_item2_qty, qty2_str);
        contentValues.put(COL_item2_amt, amt2_str);

        contentValues.put(COL_item3, item3_str);
        contentValues.put(COL_item3_qty, qty3_str);
        contentValues.put(COL_item3_amt, amt3_str);

        contentValues.put(COL_item4, item4_str);
        contentValues.put(COL_item4_qty, qty4_str);
        contentValues.put(COL_item4_amt, amt4_str);

        contentValues.put(COL_item5, item5_str);
        contentValues.put(COL_item5_qty, qty5_str);
        contentValues.put(COL_item5_amt, amt5_str);

        contentValues.put(COL_total, total_str);

        long result = db2.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean updateData(int id,String name_str,String date_str,String time_str,String service_str,String item1_str,String qty1_str,String amt1_str,
                            String item2_str,String qty2_str,String amt2_str,String item3_str,String qty3_str,String amt3_str,
                            String item4_str,String qty4_str,String amt4_str,String item5_str,String qty5_str,String amt5_str,
                            String total_str)
    // public boolean addData2(String name_str)
    {
        SQLiteDatabase db2 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, id);
        contentValues.put(COL2, name_str);

        contentValues.put(COL_date, date_str);
        contentValues.put(COL_time, time_str);
        contentValues.put(COL_service, service_str);

        contentValues.put(COL_item1, item1_str);
        contentValues.put(COL_item1_qty, qty1_str);
        contentValues.put(COL_item1_amt, amt1_str);

        contentValues.put(COL_item2, item2_str);
        contentValues.put(COL_item2_qty, qty2_str);
        contentValues.put(COL_item2_amt, amt2_str);

        contentValues.put(COL_item3, item3_str);
        contentValues.put(COL_item3_qty, qty3_str);
        contentValues.put(COL_item3_amt, amt3_str);

        contentValues.put(COL_item4, item4_str);
        contentValues.put(COL_item4_qty, qty4_str);
        contentValues.put(COL_item4_amt, amt4_str);

        contentValues.put(COL_item5, item5_str);
        contentValues.put(COL_item5_qty, qty5_str);
        contentValues.put(COL_item5_amt, amt5_str);

        contentValues.put(COL_total, total_str);

        db2.update(TABLE_NAME,contentValues,COL1 + "=" + id,null);
        return true;

    }




    public Cursor getItemID(String name){
        SQLiteDatabase db2 = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db2.rawQuery(query, null);
        return data;
    }
    public Cursor getdata(String name){
        SQLiteDatabase db2 = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COL2 + " = '" + name + "'";
        Cursor data =db2.rawQuery(query,null);
        return data;
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getdata2(int id){
        SQLiteDatabase db2 = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'";
        Cursor data =db2.rawQuery(query,null);
        return data;
    }

    public boolean updateName(int id ,String one){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2, one);

        db.update(TABLE_NAME,contentValues,COL1 + "=" + id,null);
        return true;
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";

        db.execSQL(query);
    }

    public boolean deleteName2( String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL2 + " = '" + name + "'";
        db.execSQL(query);
        return true;
    }

    public List<String> getalldata(){
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }
}


