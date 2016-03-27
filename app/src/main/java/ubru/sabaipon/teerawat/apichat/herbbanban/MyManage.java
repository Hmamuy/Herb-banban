package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by หมามุ้ย on 27/3/2559.
 */
public class MyManage {
    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;


    public MyManage(Context context) {

        //Create&Connected
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }   //Constructor

}   // Main Class
