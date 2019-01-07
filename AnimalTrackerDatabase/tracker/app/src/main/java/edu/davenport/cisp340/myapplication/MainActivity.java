package edu.davenport.cisp340.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    DBHelper db;
    private Button btnAdd, btnViewData, btnShowLocation;
    private EditText editText, editText2, editText3, editText4;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnView);
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        db = new DBHelper(this);

        db.addAnimal("Rabbit");
        db.addAnimal("Deer");
        db.addAnimal("Fox");
        db.addAnimal("Skunk!");
        db.addAnimal("Bald Eagle");






        Toast.makeText(this, "Values saved", Toast.LENGTH_LONG).show();

        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(MainActivity.this);

                if (gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Toast.makeText(getApplicationContext(), "Your Locations is \nLat: " + latitude + "\nLong: " + longitude,
                            Toast.LENGTH_LONG).show();
                    db.addLocation(latitude, longitude);
                }else {
                    gps.showSettingAlert();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                String newEntry2 = editText2.getText().toString();
                String newEntry3 = editText3.getText().toString();
                String newEntry4 = editText4.getText().toString();
                if (editText.length() != 0 || editText2.length() != 0 || editText3.length() != 0 || editText4.length() != 0) {
                    AddData(newEntry,newEntry2, newEntry3, newEntry4);
                    editText.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                } else {
                    toastMessage("You must put something in the text field!");
                }

            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, List.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newEntry, String newEntry2, String newEntry3, String newEntry4) {
        boolean insertData = db.addData(newEntry, newEntry2, newEntry3, newEntry4);

        if (insertData ==true) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
