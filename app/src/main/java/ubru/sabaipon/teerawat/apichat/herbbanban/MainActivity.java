package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        binWidget();
    }  //Main Method

    private void binWidget() {

    } //binWidget

    public void clickSignInMain(View view) {

    }

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }

    public void clickGuest(View view) {
        startActivity(new Intent(MainActivity.this,ReadAllHerbActivity.class));
    }

}  // Main Class
