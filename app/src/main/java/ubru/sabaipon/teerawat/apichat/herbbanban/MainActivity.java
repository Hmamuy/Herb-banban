package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    // ต้องการสร้างฐานข้อมูล
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);


        myManage = new MyManage(MainActivity.this);

        //Test Add Value
        //testAddValue

        //Delete SQLite
        deleteSQLite();

        //การ Syn JSON
        synJSON();


        //Bind Widget
        binWidget();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }  //Main Method


    private void synJSON() {

        //การ connect Http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        int intIndex = 0;
        while (intIndex <= 1) {

            //1 create inputStream
            InputStream inputStream = null;
            String[] urlStrings = {"http://project-com-tech.ubru.ac.th/~cpt2ys5702/php_get_user.php",
                    "http://project-com-tech.ubru.ac.th/~cpt2ys5702/php_get_herb.php"};

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlStrings[intIndex]);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();


            } catch (Exception e) {
                Log.d("herb", "Error1==>" + e.toString());
            }

            //2 create strJSON

            String strJSON = null;

            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(strLine);
                }
                inputStream.close();
                strJSON = stringBuilder.toString();

            } catch (Exception e) {
                Log.d("herb", "Error2==>" + e.toString());
            }
            //3 create SQLite

            try {

                JSONArray jsonArray = new JSONArray(strJSON);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    switch (intIndex) {

                        case 0:

                            String strColumn1 = jsonObject.getString(MyManage.column_User);
                            String strColumn2 = jsonObject.getString(MyManage.column_Password);
                            String strColumn3 = jsonObject.getString(MyManage.column_Status);
                            String strColumn4 = jsonObject.getString(MyManage.column_Name);
                            String strColumn5 = jsonObject.getString(MyManage.column_Surname);
                            String strColumn6 = jsonObject.getString(MyManage.column_Phone);
                            String strColumn7 = jsonObject.getString(MyManage.column_Address);

                            myManage.addValueToSQLite(0, strColumn1, strColumn2, strColumn3,
                                    strColumn4, strColumn5, strColumn6, strColumn7);


                            break;
                        case 1:

                            String strColumn8 = jsonObject.getString(MyManage.column_Name);
                            String strColumn9 = jsonObject.getString(MyManage.column_Image);
                            String strColumn10 = jsonObject.getString(MyManage.column_Description);
                            String strColumn11 = jsonObject.getString(MyManage.column_HowTo);
                            String strColumn12 = jsonObject.getString(MyManage.column_Lat);
                            String strColumn13 = jsonObject.getString(MyManage.column_Lng);
                            String strColumn14 = jsonObject.getString(MyManage.column_Status);

                            myManage.addValueToSQLite(1, strColumn8, strColumn9, strColumn10,
                                    strColumn11, strColumn12, strColumn13, strColumn14);


                            break;

                    }   //switch

                } //for


            } catch (Exception e) {
                Log.d("herb", "Error3==>" + e.toString());
            }

            intIndex += 1;
        }   //while

    }   //synJSON

    private void deleteSQLite() {

        SQLiteDatabase sqLiteDatabas = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabas.delete(MyManage.user_table, null, null);
        sqLiteDatabas.delete(MyManage.herb_table, null, null);
    }   //delete

    private void testAddValue() {
        int intTime = 0;
        while (intTime <= 1) {

            myManage.addValueToSQLite(intTime, "test1", "test2", "test3",
                    "test4", "test5", "test6", "test7");

            intTime += 1;

        }
    }

    private void binWidget() {


    } //binWidget

    public void clickSignInMain(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //check Space
        if (userString.equals("")||passwordString.equals("")) {
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.myDialog(this,"มีช่องว่าง","กรุณากรอกทุกช่อง ค่ะ");

        } else {

            checkUser();

        }


    }   //signIn

    private void checkUser() {

        try {

            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE,null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " +"'" +userString+"'" , null);
            cursor.moveToFirst();
            String[] resultStrings = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {

                resultStrings[i] = cursor.getString(i);

            }   // for
            cursor.close();
            //check password

            if (passwordString.equals(resultStrings[2])) {
                Toast.makeText(this,"ยินดีต้อนรับ ค่ะ"+resultStrings[4],Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ReadAllHerbActivity.class);
                intent.putExtra("Data", resultStrings);
                startActivity(intent);
                finish();

            } else {
                MyAlertDialog myAlertDialog = new MyAlertDialog();
                myAlertDialog.myDialog(this, "Password False" , "กรุณากรอกใหม่ Password ผิด");

            }



        } catch (Exception e) {
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.myDialog(this, "ไม่พบข้อมูลค่ะ" , "ไม่มี" +userString+ "ในฐานข้อมูลของเรา");
        }

    }   //checkUser

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

    public void clickGuest(View view) {
        Intent intent = new Intent(this, HerbListView.class);
        intent.putExtra("Status", "1");
        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ubru.sabaipon.teerawat.apichat.herbbanban/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ubru.sabaipon.teerawat.apichat.herbbanban/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}  // Main Class
