package premiumMode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;

    public static final String DATABASE_NAME = "maindatabase";
    public static final String TABLE_NAME = "MynewTable";

    public static final String COLUMN_ID = "ID";


    public static final String COLUMN_TIME= "TIME";

    public static final String COLUMN_MESSAGE = "MESSAGE";
    public static final String COLUMN_NAME = "NAME";

    public static final String TABLE_REFERENCE_ID = "reference_id";



    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {



        String SQL_CREATE = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_MESSAGE + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TIME+ " BIGINT, "+
                 TABLE_REFERENCE_ID + " TEXT, "+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ) ";

        db.execSQL(SQL_CREATE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        db.execSQL(SQL_DELETE);
        onCreate(db);

    }




    public void insertDataChats(String message,String name,long second,String referenceID){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_MESSAGE,message);
        values.put(COLUMN_TIME,second);
        values.put(TABLE_REFERENCE_ID,referenceID);

        db.insert(TABLE_NAME,null,values);


    }




    public Cursor searchDataMessages(String  reference_id) {
        SQLiteDatabase db = getWritableDatabase();

        String orderBy = COLUMN_TIME + " DESC";

        String[] columns = new String[]{COLUMN_MESSAGE, COLUMN_NAME, COLUMN_TIME};
        Cursor cursor = db.query(TABLE_NAME, columns, " reference_id = ? ", new String[]{reference_id}, null, null, orderBy, String.valueOf(50));
        return cursor;

    }

    public Cursor searchAfterDataMessages(String  reference_id, long earlyMessageSecond) {
        SQLiteDatabase db = getWritableDatabase();

        String orderBy = COLUMN_TIME + " DESC";

        String[] columns = new String[]{COLUMN_MESSAGE, COLUMN_NAME, COLUMN_TIME};
        Cursor cursor = db.query(TABLE_NAME, columns, " reference_id = ?  AND TIME < ? ", new String[]{reference_id, String.valueOf(earlyMessageSecond)}, null, null, orderBy, String.valueOf(25));
        return cursor;

    }






    public Cursor readAllData(){
        SQLiteDatabase db = getWritableDatabase();

        String query= "SELECT * FROM "+TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }



    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME);


    }






}
