package com.example.whiterabbittest;


import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.whiterabbittest.models.Address;
import com.example.whiterabbittest.models.Company;
import com.example.whiterabbittest.models.Item;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EmployeeManager";
    private static final String TABLE_EMPLOYEE = "employee";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERNAME = "uname";
    private static final String KEY_EMAIL = "email";

    private static final String KEY_PROFILEIMAGE = "profile_image";
    private static final String KEY_STREET = "street";
    private static final String KEY_SUITE = "suite";
    private static final String KEY_CITY = "city";
    private static final String KEY_ZIPCODE = "zipcode";
    private static final String KEY_WEBSITE = "website";
    private static final String KEY_COMPANYNAME = "companyname";
    private static final String KEY_COMPANYCATCHPHRASE = "catchphrase";

    private static final String KEY_COMPANYBS = "bs";



    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_itemS_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                +KEY_USERNAME + " TEXT,"
                +KEY_PH_NO + " TEXT,"
                +KEY_EMAIL+ " TEXT,"
                + KEY_STREET + " TEXT,"
                +KEY_SUITE + " TEXT,"
                +KEY_ZIPCODE + " TEXT,"
                +KEY_CITY + " TEXT,"
                +KEY_COMPANYNAME + " TEXT,"
                +KEY_COMPANYCATCHPHRASE + " TEXT,"
                +KEY_COMPANYBS + " TEXT,"
                +KEY_WEBSITE + " TEXT,"
                + KEY_PROFILEIMAGE + " TEXT" + ")";
        db.execSQL(CREATE_itemS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);

        // Create tables again
        onCreate(db);
    }

    // code to add the new item
    void additem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_USERNAME, item.getUsername());

        try {
            values.put(KEY_PH_NO, item.getPhone());
        }
        catch(NullPointerException e)
        {
            values.put(KEY_PH_NO,"no data available");

        }


        try {
            values.put(KEY_PROFILEIMAGE, item.getProfileImage());
        }
        catch(NullPointerException e)
        {
            values.put(KEY_PROFILEIMAGE,"no data available");

        }
        values.put(KEY_STREET, item.getAddress().getStreet());
        values.put(KEY_SUITE, item.getAddress().getSuite());
        values.put(KEY_CITY, item.getAddress().getCity());
        values.put(KEY_ZIPCODE, item.getAddress().getZipcode());
        try {
            values.put(KEY_COMPANYNAME, item.getCompany().getName());
        }
        catch(NullPointerException e)
        {
            values.put(KEY_COMPANYNAME,"no data available");

        }

        try {
            values.put(KEY_COMPANYCATCHPHRASE, item.getCompany().getCatchPhrase());
        }
        catch(NullPointerException e)
        {
            values.put(KEY_COMPANYCATCHPHRASE,"no data available");

        }

        try {
            values.put(KEY_COMPANYBS, item.getCompany().getBs());
        }
        catch(NullPointerException e)
        {
            values.put(KEY_COMPANYBS,"no data available");

        }
        values.put(KEY_WEBSITE, item.getWebsite());
        values.put(KEY_EMAIL, item.getEmail());


        // Inserting Row
        db.insert(TABLE_EMPLOYEE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single item
//    Item getitem(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[] { KEY_ID,
//                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Item item = new item(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        // return item
//        return item;
//    }

    public List<Item> getAllItems() {
        List<Item> ItemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
//                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setUsername(cursor.getString(2));
                item.setPhone(cursor.getString(3));
                item.setEmail(cursor.getString(4));
                item.setAddress(new Address(cursor.getString(5),cursor.getString(6),cursor.getString(8),cursor.getString(7)));
                item.setCompany(new Company(cursor.getString(9),cursor.getString(10),cursor.getString(11)));
                item.setWebsite(cursor.getString(12));
                item.setProfileImage(cursor.getString(13));


                // Adding item to list
                ItemList.add(item);


            } while (cursor.moveToNext());
        }

        // return item list
        return ItemList;
    }

//    // code to update the single item
//    public int updateitem(item item) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, item.getName());
//        values.put(KEY_PH_NO, item.getPhoneNumber());
//
//        // updating row
//        return db.update(TABLE_EMPLOYEE, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(item.getID()) });
//    }
//
//    // Deleting single item
//    public void deleteitem(item item) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?",
//                new String[] { String.valueOf(item.getID()) });
//        db.close();
//    }

    // Getting items Count
    public int getitemsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        try {
            return cursor.getCount();
        }
        catch(IllegalStateException e)
        {
            return 0;
        }
    }

}  
