package com.example.comp3074.a2100872220;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class EditPatientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner doctors;
    private EditText firstName;
    private EditText lastName;
    private EditText room;
    private Button enter;
    private List<Integer> docIDs;

    private Patient pat;
    private boolean canContinue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);

        doctors = (Spinner) findViewById(R.id.spnEdtDoc);
        firstName = (EditText) findViewById(R.id.edtPaFN);
        lastName = (EditText) findViewById(R.id.edtPaLn);
        room = (EditText) findViewById(R.id.edtPaRoom);
        enter = (Button) findViewById(R.id.btnEditPat);
        pat = (Patient)getIntent().getSerializableExtra("patient");
        docIDs = new ArrayList<>();
        setText();
        enter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                validateFields();
                if(canContinue) {

                    Intent intent = new Intent(EditPatientActivity.this, ViewPatientActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("patient",pat);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

        loadDoctorSpinner();
    }

    private void setText(){
        DbHelper db = new DbHelper(getApplicationContext());
        Cursor cur = db.getPatientById(String.valueOf(pat.getId()));
        while(cur.moveToNext()){
            firstName.setText(cur.getString(1));
            lastName.setText(cur.getString(2));
            room.setText(String.valueOf(cur.getInt(4)));
        }
    }

    private void updatePatientInformation(){
        DbHelper db = new DbHelper(getApplicationContext());
        db.updatePatient(firstName.getText().toString(),
                lastName.getText().toString(),
                Integer.parseInt(room.getText().toString()),
                docIDs.get(doctors.getSelectedItemPosition()),
                pat.getId());
        db.close();

    }

    private void validateFields(){
        boolean validFN = true;
        boolean validLN = true;
        boolean validRoom = true;
        if(firstName.getText().toString().trim().equals("")){
            firstName.setError("Cannot be empty, no numbers.");
            validFN = false;
        }
        if(lastName.getText().toString().trim().equals("")){
            lastName.setError("Cannot be empty, no numbers.");
            validLN = false;
        }
        if(room.getText().toString().trim().equals("")){
            room.setError("Cannot be empty. only numbers");
            validRoom = false;
        }
        if(validFN && validLN && validRoom){
            updatePatientInformation();
            canContinue = true;
        } else {

        }
    }
    //Load spinner with doctor names from database
    private void loadDoctorSpinner() {
        DbHelper db = new DbHelper(getApplicationContext());
        Cursor cur = db.getDoctorData();

        final List<String> spinNames = new ArrayList<>();
        if(cur.moveToNext()) {
            do{
                String name = cur.getString(1);
                spinNames.add(name);
                docIDs.add(cur.getInt(0));
            }while(cur.moveToNext());
        }
        cur.close();
        db.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinNames
        );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctors.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //selectedID = docIDs.get(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
