package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class HerbListView extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herb_list_view);

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyManage.herb_table, null);
        cursor.moveToFirst();
        int intCount = cursor.getCount();

        String[] nameStrings = new String[intCount];
        String[] imageStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            nameStrings[i] = cursor.getString(1);
            imageStrings[i] = cursor.getString(2);

            cursor.moveToNext();
       }   //for
        cursor.close();

        HerbAdapter herbAdapter = new HerbAdapter(this, imageStrings, nameStrings);
        ListView herbListView = (ListView) findViewById(R.id.listView);
        herbListView.setAdapter(herbAdapter);

        herbListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(HerbListView.this, DetailActivity.class);
                startActivity(intent);

            }   //onItem
        });


    }   //Main Method


}   //Main Class
