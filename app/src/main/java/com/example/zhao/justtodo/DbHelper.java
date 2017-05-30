package com.example.zhao.justtodo;

/**
 * Created by Zhao on 2017/5/28.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String CREATE_DATABASE_TABLE = "create table tb_bwl(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "_id integer primary key autoincrement," +
            "title text," +
            "content text)";
    public DbHelper(Context context, String name, CursorFactory factory, int version){
        super(context,name,factory,version);
        }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_DATABASE_TABLE);
        }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub  
        }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
