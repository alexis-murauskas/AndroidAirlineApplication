package edu.pdx.cs410j.alm9.airline;

import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

import java.text.ParseException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AirlineController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new AirlineController();
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_readme) {
            View current = getWindow().getDecorView().getRootView();

            Snackbar snackbar = Snackbar.make(current, R.string.readme, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Dismiss", view -> {
                snackbar.dismiss(); });

            TextView view = snackbar.getView().findViewById(R.id.snackbar_text);
            view.setLines(25);
            snackbar.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onAddButtonClick(View v) {
        String message = "Airline added successfully!";

        try {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

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

            InputModel flight = AirlineCommand.parse(array);
            controller.addFlight(flight);
        } catch (NumberFormatException e) {
            message = "Error: Flight code isn't an integer";
        } catch (ArrayIndexOutOfBoundsException e) {
            message = "Error: Arguments could not be parsed";
        } catch (ParseException e) {
            message = "Error: Time is malformatted";
        } catch (Exception e) {
            message = "Error" + e.getMessage();
        }

        Snackbar.make(v, message, Snackbar.LENGTH_LONG)
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onSearchButtonClick(View view) {
        String results = "";

        try {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

            EditText airlineInput = findViewById(R.id.searchinput_airline);
            EditText srcInput = findViewById(R.id.searchinput_src);
            EditText dstInput = findViewById(R.id.searchinput_dest);

            String airline = airlineInput.getText().toString();
            String source = srcInput.getText().toString();
            String destination = dstInput.getText().toString();

            if (!source.isEmpty() && !destination.isEmpty())
                results = GetAirlines(airline, source, destination);
            else
                results = GetAirlines(airline);

            if (results != "") {
                TextView textviewResults = findViewById(R.id.textview_results);
                textviewResults.setText(results);
            }

        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String GetAirlines(String airline) {
        Airline rv = controller.findAirline(airline);

        if (rv == null)
            return null;

        return rv.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String GetAirlines(String airline, String src, String dest) {
        Airline rv = controller.findAirline(airline, src, dest);

        if (rv == null)
            return null;

        return rv.toString();
    }


}
