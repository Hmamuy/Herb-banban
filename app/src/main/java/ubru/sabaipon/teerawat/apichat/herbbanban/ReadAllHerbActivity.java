package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ReadAllHerbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_all_herb);
    }   //Main Method

    public void ClickReadAll(View view) {

        startActivity(new Intent(this, HerbListView.class));

    }

    public void ClickUpdate(View view) {

    }

    public void ClickApprove (View view) {

    }
}   //Main Class
