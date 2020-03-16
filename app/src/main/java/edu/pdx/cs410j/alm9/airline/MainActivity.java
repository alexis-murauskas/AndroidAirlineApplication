package edu.pdx.cs410j.alm9.airline;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_readme) {
            TextView text = findViewById(R.id.textview_readme);
            text.setVisibility(View.VISIBLE);
            ImageButton button = findViewById(R.id.button_close_readme);
            button.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onReadmeButtonClick(View view) {
        TextView text = findViewById(R.id.textview_readme);
        text.setVisibility(View.INVISIBLE);
        view.setVisibility(View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onAddButtonClick(View v) {
        try {
            EditText airline = findViewById(R.id.textinput_airline);
            EditText flightNumber = findViewById(R.id.textinput_flight_number);
            EditText src = findViewById(R.id.textinput_src);
            EditText depart = findViewById(R.id.textinput_depart);
            EditText dest = findViewById(R.id.textinput_dest);
            EditText arrive = findViewById(R.id.textinput_arrive);

            String[] array = {
                    airline.getText().toString(),
                    flightNumber.getText().toString(),
                    src.getText().toString(),
                    depart.getText().toString(),
                    dest.getText().toString(),
                    arrive.getText().toString()
            };

            AirlineCommand.parse(array);
        } catch (Exception e) {
            System.err.println("ADD AIRLINE FAILED" + e.getMessage());
        }
    }
}
