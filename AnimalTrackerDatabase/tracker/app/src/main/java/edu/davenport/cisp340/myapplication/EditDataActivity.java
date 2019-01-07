package edu.davenport.cisp340.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {
    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete;
    private EditText editable_item;
    private EditText editable2_item;
    private EditText editable3_item;
    private EditText editable4_item;

    DBHelper mDatabaseHelper;

    private String selectedName;
    private String selectedCount;
    private String selectedSeenon;
    private String selectedComments;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        editable2_item = findViewById(R.id.editable2_item);
        editable3_item = findViewById(R.id.editable3_item);
        editable4_item = findViewById(R.id.editable4_item);
        mDatabaseHelper = new DBHelper(this);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("animal_id");

        selectedCount = receivedIntent.getStringExtra("count");

        selectedSeenon = receivedIntent.getStringExtra("seen");

        selectedComments = receivedIntent.getStringExtra("comments");

        //set the text to show the current selected name
        editable_item.setText(selectedName);
        editable2_item.setText(selectedCount);
        editable3_item.setText(selectedSeenon);
        editable4_item.setText(selectedComments);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                String item2 = editable2_item.getText().toString();
                String item3 = editable3_item.getText().toString();
                String item4 = editable4_item.getText().toString();
                if(!item.equals("") && !item2.equals("") && !item3.equals("") && !item4.equals("")){
                    mDatabaseHelper.updateName(item,selectedID,selectedName);
                    mDatabaseHelper.updateCount(item,selectedID,selectedCount);
                    mDatabaseHelper.updateSeenon(item,selectedID,selectedSeenon);
                    mDatabaseHelper.updateComments(item,selectedID,selectedComments);
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedID,selectedName,selectedCount,selectedSeenon,selectedComments);
                editable_item.setText("");
                editable2_item.setText("");
                editable3_item.setText("");
                editable4_item.setText("");
                toastMessage("removed from database");
            }
        });

    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

