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
import java.util.List;

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

        list = (ListView) findViewById(R.id.lstView);
        populateListView();
    }

    public void switchToAddPatientScreen(){
        Intent intent = new Intent(PatientActivity.this, AddPatientActivity.class);
        startActivity(intent);
    }

    private void populateListView() {
        final List<Patient> patients = new ArrayList<>();
        DbHelper db = new DbHelper(getApplicationContext());
        //get data and append to the list
        Cursor data = db.getPatients();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            Patient p = new Patient(data.getInt(0));
            patients.add(p);
            listData.add(data.getString(1));
        }
        //create list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        list.setAdapter(adapter);

        //set onItemClickListener to ListView
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // String name = adapterView.getItemAtPosition(i).toString();
                Intent viewPatient = new Intent(PatientActivity.this, ViewPatientActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("patient", patients.get(i));
                viewPatient.putExtras(b);
                startActivity(viewPatient);

            }
        });
    }
    @Override
    public void onRestart() {
        super.onRestart();
        populateListView();
    }
}
