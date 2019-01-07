package edu.davenport.cisp340.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    private static final String TAG = "list";

    DBHelper db;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        final ListView mListView = findViewById(R.id.listView);
        db = new DBHelper(this);

        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        ArrayList<String> listData = new ArrayList<>();
        Cursor data = db.getData();
        if (data.getCount() ==0){
            Toast.makeText(List.this, R.string.DBalert, Toast.LENGTH_LONG).show();
        }
        else {
            while (data.moveToNext()) {
                //get the value from the database in column 1
                //then add it to the ArrayList
                listData.add(data.getString(1));
                ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
                mListView.setAdapter(adapter);
            }
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String animal_id = adapterView.getItemAtPosition(i).toString();
                String count = adapterView.getItemAtPosition(i).toString();
                String seen = adapterView.getItemAtPosition(i).toString();
                String comments = adapterView.getItemAtPosition(i).toString();
                String Loc_latitude = adapterView.getItemAtPosition(i).toString();
                String Loc_longitude = adapterView.getItemAtPosition(i).toString();

                Log.d(TAG, "onItemClick: You Clicked on " + animal_id);

                Cursor data = db.getItemID(animal_id); //get the id associated with that name
                Cursor data2 = db.getItemID(count);
                Cursor data3 = db.getItemID(seen);
                Cursor data4 = db.getItemID(comments);
                Cursor data5 = db.getItemID(Loc_latitude);
                Cursor data6 = db.getItemID(Loc_longitude);

                int itemID = -1;
                int itemID2 = -1;
                int itemID3 = -1;
                int itemID4 = -1;
                int itemID5 = -1;
                int itemID6 = -1;
                while (data.moveToNext() && data2.moveToNext() && data3.moveToNext() && data4.moveToNext()) {
                    itemID = data.getInt(0);
                    itemID2 = data.getInt(0);
                    itemID3 = data.getInt(0);
                    itemID4 = data.getInt(0);
                    //itemID5 = data.getInt(0);
                    //itemID6 = data.getInt(0);
                }
                if (itemID > -1 && itemID2 > -1 && itemID3 > -1 && itemID4 > -1 /*&& itemID5 > -1 && itemID6 > -1 */) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(List.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("animal_id", animal_id);
                    editScreenIntent.putExtra("count", count);
                    editScreenIntent.putExtra("seen", seen);
                    editScreenIntent.putExtra("comments", comments);
                    //editScreenIntent.putExtra("Loc_latitude", Loc_latitude);
                   // editScreenIntent.putExtra("Loc_longitude", Loc_longitude);
                    startActivity(editScreenIntent);
                } else {
                    toastMessage("No ID associated with that name");
                }

            }


        });
    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
