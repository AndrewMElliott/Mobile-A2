package com.example.comp3074.a2100872220;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class ViewPatientActivity extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView room;
    private TextView doctor;
    private Patient  p;
    private Button   edtPatient;
    private Button   addTest;
    private Integer  patID;
    private ListView testListView;
    private Button   mainMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        firstName   = (TextView) findViewById(R.id.txtFirstName);
        //lastName    = (TextView) findViewById(R.id.txtLastName);
        room        = (TextView) findViewById(R.id.txtRoom);
        doctor      = (TextView) findViewById(R.id.txtDoctor);
        p           = (Patient)getIntent().getSerializableExtra("patient");
        testListView= (ListView) findViewById(R.id.lvTest);

        getPatient(String.valueOf(p.getId()));
        populateListView();

        edtPatient = (Button)findViewById(R.id.btnEditPat);
        addTest    = (Button)findViewById(R.id.btnAddTest);
        mainMenu   = (Button)findViewById(R.id.btnMainMenu);

        mainMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ViewPatientActivity.this,PatientActivity.class);
                startActivity(intent);
            }
        });

        edtPatient.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ViewPatientActivity.this,EditPatientActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("patient",getIntent().getSerializableExtra("patient"));
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        addTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ViewPatientActivity.this, AddTestActivity.class);
                intent.putExtra("patient",p.getId());
                startActivity(intent);
            }
        });
    }

    private void populateListView() {
        final List<Integer> testID = new ArrayList<>();

        DbHelper db = new DbHelper(getApplicationContext());
        //get data and append to the list
        Cursor data = db.getTests(String.valueOf(p.getId()));
        ArrayList<String> listData = new ArrayList<>();
        Integer count = 1;
        while(data.moveToNext()){

            testID.add(data.getInt(0));
            listData.add("Test " + String.valueOf(count));
            count++;
        }
        //create list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        testListView.setAdapter(adapter);

        //set onItemClickListener to ListView
        testListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent viewPatient = new Intent(ViewPatientActivity.this, ReviewTestActivity.class);
                Bundle b = new Bundle();
                b.putInt("testID",testID.get(i));
                viewPatient.putExtras(b);
                startActivity(viewPatient);

            }
        });
    }
    private void getPatient(String id){
        DbHelper db = new DbHelper(getApplicationContext());
        Cursor cur = db.getPatientById(id);

        while(cur.moveToNext()){
            firstName.setText("Patient: "+ cur.getString(1) + " " + cur.getString(2));
            doctor.setText("Attending Physician: " +getDoctorName(String.valueOf(cur.getInt(3))));
            room.setText("Located in Room: " +String.valueOf(cur.getInt(4)));
        }
    }

    private String getDoctorName(String id){
        DbHelper db = new DbHelper(getApplicationContext());
        Cursor cur  = db.getDoctorByID(id);
        String name = "";
        while(cur.moveToNext()){
            name = cur.getString(1)+ " " + cur.getString(2);
        }
        return name;
    }
    @Override
    public void onRestart() {
        super.onRestart();
        getPatient(String.valueOf(p.getId()));
    }
}
