package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import user.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="dulieukhachhang.db";
    private static final String TABLE_NAME="khachhang";
    private static final int VERSION=1;
    private static final String COLUMN_SDT="phone";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_PASSWORD="pass";
    private static final String TABLE_CREATE=" create table khachhang ( phone text primary key, pass text not null,name text not null) ";
    SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
        this.db=sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String query=" DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
    public void insertTK(User user){
        db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_SDT,user.getEmail());
        values.put(COLUMN_PASSWORD,user.getPassword());
        values.put(COLUMN_NAME,user.getName());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public boolean checkSDT(String tk){
            db=this.getReadableDatabase();
            String query=" select " +COLUMN_SDT + " from "+TABLE_NAME;
            Cursor cursor= db.rawQuery(query,null);
            String name;
            if(cursor.moveToFirst()){
                do {
                    name=cursor.getString(0);
                    if(name.equals(tk)){
                        db.close();
                        return true;
                    }
                }while (cursor.moveToNext());
            }
            db.close();
            return false;
    }
    public String searchPass(String tk){
        db=this.getReadableDatabase();
        String query= "select phone ,pass from " +TABLE_NAME;
        Cursor cursor= db.rawQuery(query,null);
        String sdt;
        String pass="không tìm thấy";
        if(cursor.moveToFirst()){
            do {
                sdt=cursor.getString(0);
                if(sdt.equals(tk)){
                    pass =cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        db.close();
        return pass;
    }
    public void resetPass(String sdt,String pass){
        db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_PASSWORD,pass);
        db.update(TABLE_NAME,values,COLUMN_SDT+"= ?",new String[]{String.valueOf(sdt)});
        db.close();
    }

}
