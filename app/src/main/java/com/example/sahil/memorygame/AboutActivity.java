package com.example.sahil.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class AboutActivity extends Activity {

    TextView info_Text;
    Button app_Button;

    String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        info_Text = (TextView) findViewById(R.id.infoText);
        app_Button = (Button) findViewById(R.id.goToAppButton);

        info = "Yo\n\nI am an ardent programmer studying, you guessed it, Computer Science! " +
                "I built this game with my blood and soul and a sheep sacrifice, along with " +
                "some Red Bull and the occasional pizza. Hope you enjoy it and consider giving " +
                "this game 5 stars. Seriously, consider it. The button is like, right below." +
                "\n\nSincerely,\nA college student who needs money to fund his " +
                "unhealthy eating lifestyle of pizzas and Red Bulls to create the next app";

        info_Text.setText(info);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
