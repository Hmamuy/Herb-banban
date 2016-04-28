package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ReadAllHerbActivity extends AppCompatActivity {

    //Explicit
    private String[] myResultStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_all_herb);

        myResultStrings = getIntent().getStringArrayExtra("Data");

    }   //Main Method

    public void ClickReadAll(View view) {

        startActivity(new Intent(this, HerbListView.class));

    }

    public void ClickUpdate(View view) {

        startActivity(new Intent(this, UpdateHerb.class));

    }

    public void ClickApprove (View view) {

        if (myResultStrings[3].equals("0")) {
            startActivity(new Intent(this, ApproveHerb.class));

        } else {
            Toast.makeText(this, "คุณไม่มีสิทธิเข้าถึงข้อมูล ค่ะ", Toast.LENGTH_SHORT).show();

        }


    }

}   //Main Class
