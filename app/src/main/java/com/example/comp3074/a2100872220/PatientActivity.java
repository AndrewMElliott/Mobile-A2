package com.example.comp3074.a2100872220;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PatientActivity extends AppCompatActivity {

    private Button addPatient;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient2);

        addPatient = (Button) findViewById(R.id.btnAddPatient);
        addPatient.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                switchToAddPatientScreen();
            }
        });
        Log.d( "whatever","populateListView: Oncreate");
        list = (ListView) findViewById(R.id.lstView);
        populateListView();
    }

    public void switchToAddPatientScreen(){
        Intent intent = new Intent(PatientActivity.this, AddPatientActivity.class);
       // intent.putExtra("user", this.user);
        startActivity(intent);
    }

    private void populateListView() {
        Toast.makeText(getBaseContext(), "populating list",
                Toast.LENGTH_LONG).show();
        DbHelper db = new DbHelper(getApplicationContext());
        //get the data and append to a list
        Cursor data = db.getPatients();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        list.setAdapter(adapter);

        //set an onItemClickListener to the ListView
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String name = adapterView.getItemAtPosition(i).toString();
//                Log.d(TAG, "onItemClick: You Clicked on " + name);
//
//                Cursor data = mDatabaseHelper.getItemID(name); //get the id associated with that name
//                int itemID = -1;
//                while(data.moveToNext()){
//                    itemID = data.getInt(0);
//                }
//                if(itemID > -1){
//                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
//                    editScreenIntent.putExtra("id",itemID);
//                    editScreenIntent.putExtra("name",name);
//                    startActivity(editScreenIntent);
//                }
//                else{
//
//                }
//            }
//        });
    }
    @Override
    public void onRestart() {
        super.onRestart();
        populateListView();
    }
}
