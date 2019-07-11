package edu.utep.cs.cs4330.ires10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DoReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView textView;
    private EditText description;
    private Button submit;
    private double lat, lon;
    private String type, spinnerSelection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_report);

        textView = findViewById(R.id.selectionTextV);
        submit = findViewById(R.id.submitReportButton);
        description = findViewById(R.id.descriptionEditT);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        lat = intent.getDoubleExtra("latitude", 0);
        lon = intent.getDoubleExtra("longitude", 0);

        //Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        int reportType = type.equals("infrastructure") ? R.array.infrastructure : type.equals("crime") ? R.array.crime : R.array.environment;
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(reportType));

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public void submitReport(View view){
        new MyAsyncTask(this).execute(type, lat, lon, System.currentTimeMillis(), spinnerSelection, description.getText());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelection = parent.getSelectedItem().toString();
        textView.setText(spinnerSelection);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Create options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.about:
//                startActivity(new Intent(this, About.class));
                return true;
            case R.id.help:
//                startActivity(new Intent(this, Help.class));
                return true;
            case R.id.viewReports:
                startActivity(new Intent(this, ViewReports.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
