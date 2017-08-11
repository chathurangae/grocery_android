package com.groceryapp.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Natasha Mendis on 19-May-17.
 */

public class DBhelper extends SQLiteOpenHelper {

    protected Context context;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "grocery.db";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Table Name
    public static final String TABLE_USER_SIGNUP = "tblusersignup";
    public static final String TABLE_USER_LOGIN = "tbluserlogin";
    public static final String TABLE_ITEM_DETAIL = "tblitemdetail";
    public static final String TABLE_BARCODE_ITEM_DETAIL = "tblbarcodeitemdetail";
    public static final String TABLE_ITEM_MASTER_DETAIL = "tblitemmasterdetail";
    public static final String TABLE_USER_PAYMENT_DETAIL = "tbluserpayementdetail";
    public static final String TABLE_SHOPPING_LIST_DETAIL = "tblshoppinglistdetail";

    // Table tblusersignup columns
    public static final String COLOUMN_USER_ID = "user_id";
    public static final String COLOUMN_USER_NAME = "user_name";
    public static final String COLOUMN_USER_EMAIL = "user_email";
    public static final String COLOUMN_USER_TP = "user_tp";
    public static final String COLOUMN_USER_FN = "user_fn";
    public static final String COLOUMN_USER_LN = "user_ln";
    public static final String COLOUMN_USER_NIC = "user_nic";
    public static final String COLOUMN_USER_CITY = "user_city";
    public static final String COLOUMN_USER_PIN = "user_pin";
    public static final String COLOUMN_USER_PINVERIFY = "user_pinverify";

    // Table tbluserlogin columns
    //public static final String COLOUMN_USER_ID = "user_id";
    //public static final String COLOUMN_USER_PIN = "user_pin";
    //public static final String COLOUMN_USER_EMAIL = "user_email";

    // Table tblitemdetail columns
    public static final String COLOUMN_ITEM_ID = "item_id";
    //public static final String COLOUMN_USER_ID = "user_id";
    //public static final String COLOUMN_USER_PIN = "user_pin";
    public static final String COLOUMN_BARCODE_DESC_ID = "barcode_descid";

    // Table tblbarcodeitemdetail columns
    public static final String COLOUMN_BARCODE_ID = "barcode_id";
    public static final String COLOUMN_ITEM_NAME = "item_name";
    public static final String COLOUMN_ITEM_PRICE = "item_price";

    // Table tblitemmasterdetail columns
    //public static final String COLOUMN_USER_ID = "user_id";
    //public static final String COLOUMN_USER_PIN = "user_pin";
    //public static final String COLOUMN_BARCODE_ID = "barcode_id";
    //public static final String COLOUMN_ITEM_ID = "item_id";
    public static final String COLOUMN_QUANTITY = "quantity";
    public static final String COLOUMN_TOTAL_PRICE = "total_price";
    public static final String COLOUMN_DATE_TIME = "date_time";

    // Table tbluserpayementdetail columns
    public static final String COLOUMN_USER_PAYMENT_ID = "user_paymentid";
    //public static final String COLOUMN_USER_ID = "user_id";
    //public static final String COLOUMN_USER_PIN = "user_pin";
    public static final String COLOUMN_DATE = "date";
    //public static final String COLOUMN_TOTAL_PRICE = "total_price";

    // Table tblshoppinglistdetail columns
    //public static final String COLOUMN_USER_ID = "user_id";
    //public static final String COLOUMN_BARCODE_ID = "barcode_id";
    //public static final String COLOUMN_ITEM_NAME = "item_name";
    //public static final String COLOUMN_QUANTITY = "quantity";
    //public static final String COLOUMN_DATE = "date";
    public static final String COLOUMN_SELECT_ITEM = "select_item";

    // Table questions columns


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_SIGNUP_TABLE = "CREATE TABLE " + TABLE_USER_SIGNUP + "("
                + COLOUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLOUMN_USER_NAME + " TEXT,"
                + COLOUMN_USER_EMAIL + " TEXT,"
                + COLOUMN_USER_TP + " INTEGER,"
                + COLOUMN_USER_FN + " TEXT,"
                + COLOUMN_USER_LN + " TEXT,"
                + COLOUMN_USER_NIC + " TEXT,"
                + COLOUMN_USER_CITY + " TEXT,"
                + COLOUMN_USER_PIN + " INTEGER,"
                + COLOUMN_USER_PINVERIFY + " INTEGER," + ")";
        db.execSQL(CREATE_USER_SIGNUP_TABLE);


        String CREATE_USER_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER_LOGIN + "("
                + COLOUMN_USER_ID + " INTEGER,"
                + COLOUMN_USER_EMAIL + " TEXT,"
                + COLOUMN_USER_PIN + " INTEGER," + ")";
        db.execSQL(CREATE_USER_LOGIN_TABLE);


        String CREATE_ITEM_DETAIL_TABLE = "CREATE TABLE " + TABLE_ITEM_DETAIL + "("
                + COLOUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLOUMN_USER_ID + " INTEGER,"
                + COLOUMN_USER_PIN + " INTEGER,"
                + COLOUMN_BARCODE_DESC_ID + " TEXT," + ")";
        db.execSQL(CREATE_ITEM_DETAIL_TABLE);


        String CREATE_BARCODE_ITEM_DETAIL_TABLE = "CREATE TABLE " + TABLE_BARCODE_ITEM_DETAIL + "("
                + COLOUMN_BARCODE_ID + " INTEGER,"
                + COLOUMN_ITEM_NAME + " TEXT,"
                + COLOUMN_ITEM_PRICE + " TEXT," + ")";
        db.execSQL(CREATE_BARCODE_ITEM_DETAIL_TABLE);


        String CREATE_ITEM_MASTER_DETAIL_TABLE = "CREATE TABLE " + TABLE_ITEM_MASTER_DETAIL + "("
                + COLOUMN_USER_ID + " INTEGER,"
                + COLOUMN_USER_PIN + " INTEGER,"
                + COLOUMN_BARCODE_ID + " INTEGER,"
                + COLOUMN_ITEM_ID + " TEXT,"
                + COLOUMN_QUANTITY + " INTEGER,"
                + COLOUMN_TOTAL_PRICE + " TEXT,"
                + COLOUMN_DATE_TIME + " DATETIME," + ")";
        db.execSQL(CREATE_ITEM_MASTER_DETAIL_TABLE);


        String CREATE_USER_PAYMENT_DETAIL_TABLE = "CREATE TABLE " + TABLE_USER_PAYMENT_DETAIL + "("
                + COLOUMN_USER_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLOUMN_USER_ID + " INTEGER,"
                + COLOUMN_USER_PIN + " INTEGER,"
                + COLOUMN_DATE + " DATE,"
                + COLOUMN_TOTAL_PRICE + " TEXT," + ")";
        db.execSQL(CREATE_USER_PAYMENT_DETAIL_TABLE);


        String CREATE_SHOPPING_LIST_DETAIL_TABLE = "CREATE TABLE " + TABLE_SHOPPING_LIST_DETAIL + "("
                + COLOUMN_USER_ID + " INTEGER,"
                + COLOUMN_BARCODE_ID + " INTEGER,"
                + COLOUMN_ITEM_NAME + " TEXT,"
                + COLOUMN_QUANTITY + " INTEGER,"
                + COLOUMN_DATE + " DATE,"
                + COLOUMN_SELECT_ITEM + " TEXT," + ")";
        db.execSQL(CREATE_SHOPPING_LIST_DETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_SIGNUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARCODE_ITEM_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_MASTER_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PAYMENT_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST_DETAIL);

        onCreate(db);


    }
}
