package edu.utep.cs.cs4330.ires10;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText description;
    private TextView textView;
    private int categoryID = R.array.crime;
    private Spinner spinner;
    private String spinnerSelection, category = "crime";
    private Button buttonLibrary, buttonCamera;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Create Report");

        relativeLayout = findViewById(R.id.relativeLayout);

        // Buttons
        buttonLibrary = findViewById(R.id.button_image_library);
        buttonCamera = findViewById(R.id.button_image_camera);


        description = (EditText)findViewById(R.id.description);
        textView = (TextView)findViewById(R.id.selection_spinner);

        //Spinner element
        spinner = (Spinner) findViewById(R.id.spinner2);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(categoryID));

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelection = parent.getSelectedItem().toString();
        textView.setText(spinnerSelection);
    }

    public void setSpinner(int category){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(category));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void crimeReport(View view){
        toastMssg("Criminal category");
        setSpinner(R.array.crime);
//        buttonLibrary.setTextColor(Color.WHITE);
//        buttonCamera.setTextColor(Color.WHITE);
//        buttonCamera.setBackgroundColor(Color.BLACK);
//        buttonLibrary.setBackgroundColor(Color.BLACK);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.spinnerDefault));
        category = "crime";

    }
    public void suspiciousReport(View view){
        toastMssg("Suspicious category");
        setSpinner(R.array.suspicious);
//        buttonCamera.setBackgroundColor(getResources().getColor(R.color.grayCustom));
//        buttonLibrary.setBackgroundColor(getResources().getColor(R.color.grayCustom));
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.grayCustom));
        category = "suspicious";

    }
    public void infrastructureReport(View view){
        toastMssg("Infrastructure category");
        setSpinner(R.array.infrastructure);
//        buttonCamera.setBackgroundColor(getResources().getColor(R.color.orangeCustom));
//        buttonLibrary.setBackgroundColor(getResources().getColor(R.color.orangeCustom));
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.orangeCustom));
        category = "infrastructure";

    }
    public void environmentReport(View view){
        toastMssg("Environment category");
        setSpinner(R.array.environment);
//        buttonCamera.setBackgroundColor(getResources().getColor(R.color.greenCustom));
//        buttonLibrary.setBackgroundColor(getResources().getColor(R.color.greenCustom));
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.greenCustom));
        category = "environment";
    }
    public void mobilityReport(View view){
        toastMssg("Mobility category");
        setSpinner(R.array.mobility);
//        buttonCamera.setBackgroundColor(getResources().getColor(R.color.blueCustom));
//        buttonLibrary.setBackgroundColor(getResources().getColor(R.color.blueCustom));
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.blueCustom));
        category = "mobility";

    }
    public void submit(View view){
        new MyAsyncTask(this).execute(category, MainActivity.lat, MainActivity.lon, System.currentTimeMillis(), spinnerSelection, description.getText());
        toastMssg("Thank you for your submission");
    }

    public void toastMssg(String mssg){
        Toast.makeText(AddReport.this, "You selected: " + mssg, Toast.LENGTH_SHORT).show();
    }


}
