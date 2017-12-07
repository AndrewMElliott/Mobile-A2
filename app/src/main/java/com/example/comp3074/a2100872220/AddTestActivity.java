package com.example.comp3074.a2100872220;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTestActivity extends AppCompatActivity {
    private EditText BPS;
    private EditText BPD;
    private EditText temp;
    private EditText bloodSug;
    private EditText eyes;
    private Button   addTest;
    private Integer  patID;
    private boolean canContinue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
        BPS = (EditText)findViewById(R.id.edtBPsys);
        BPD = (EditText)findViewById(R.id.edtBPdy);
        temp = (EditText)findViewById(R.id.edtTemp);
        bloodSug = (EditText)findViewById(R.id.edtBldSug);
        eyes = (EditText)findViewById(R.id.edtEyes);


        Bundle bun = getIntent().getExtras();
        patID = bun.getInt("patient");

        addTest = (Button)findViewById(R.id.btnAddTest);
        addTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                validateFields();
                if(canContinue){
                    Intent intent = new Intent(AddTestActivity.this, ViewPatientActivity.class);
                    Bundle b = new Bundle();
                    Patient p = new Patient(patID);
                    b.putSerializable("patient", p);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });
    }


    private void addTestInformation(){
        DbHelper db = new DbHelper(getApplicationContext());
        db.insertTest(patID,
                    Integer.parseInt(BPS.getText().toString()),
                    Integer.parseInt(BPD.getText().toString()),
                    Integer.parseInt(temp.getText().toString()),
                    Integer.parseInt(bloodSug.getText().toString()),
                    Integer.parseInt(eyes.getText().toString())
                );
        db.close();



    }

    private void validateFields(){
        boolean valid = true;

        if(BPS.getText().toString().trim().equals("")){
            BPS.setError("Cannot be empty");
            valid = false;
        }
        if(BPD.getText().toString().trim().equals("")){
            BPD.setError("Cannot be empty");
            valid = false;
        }
        if(temp.getText().toString().trim().equals("")){
            temp.setError("Cannot be empty");
            valid = false;
        }
        if(bloodSug.getText().toString().trim().equals("")){
            bloodSug.setError("Cannot be empty");
            valid = false;
        }
        if(eyes.getText().toString().trim().equals("")){
            eyes.setError("Cannot be empty");
            valid = false;
        }
        if(valid){
            addTestInformation();
            canContinue = true;
        } else {

        }
    }
}
