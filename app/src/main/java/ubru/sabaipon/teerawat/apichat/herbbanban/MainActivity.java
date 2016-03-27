package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myManage = new MyManage(MainActivity.this);

        //Test Add Value
        //testAddValue

        //Delete SQLite
        deleteSQLite();

        //การ Syn JSON
        synJSON();


        //Bind Widget
        binWidget();
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
            String[] urlStrings = {"http://swiftcodingthai.com/herb/php_get_user.php",
                    "http://swiftcodingthai.com/herb/php_get_herb.php"};

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

    }

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

    public void clickGuest(View view) {
        startActivity(new Intent(MainActivity.this, ReadAllHerbActivity.class));
    }

}  // Main Class
