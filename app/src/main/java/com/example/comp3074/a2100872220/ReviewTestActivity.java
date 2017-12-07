package com.example.comp3074.a2100872220;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReviewTestActivity extends AppCompatActivity {

    private TextView id;
    private TextView BPS;
    private TextView BPD;
    private TextView temp;
    private TextView blood;
    private TextView eyes;

    private Integer testID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_test);

        id    = (TextView) findViewById(R.id.txtID);
        BPS   = (TextView) findViewById(R.id.txtBPS);
        BPD   = (TextView) findViewById(R.id.txtBPD);
        temp  = (TextView) findViewById(R.id.txtTemp);
        blood = (TextView) findViewById(R.id.txtBldS);
        eyes  = (TextView) findViewById(R.id.txtEyesA);

        Bundle bun = getIntent().getExtras();
        testID = bun.getInt("testID");
        getTest();
    }

    private void getTest(){
        DbHelper db = new DbHelper(getApplicationContext());
        Cursor cur = db.getTestById(String.valueOf(testID));
        while(cur.moveToNext()){
            id.setText(   "Test ID             : " + String.valueOf(cur.getInt(0)));
            BPS.setText(  "Systolic BP     :  " + String.valueOf(cur.getInt(2)));
            BPD.setText(  "Diastolic BP   : " + String.valueOf(cur.getInt(3)));
            temp.setText( "Temperature  : " + String.valueOf(cur.getInt(4)));
            blood.setText("Blood Sugar   : " + String.valueOf(cur.getInt(5)));
            eyes.setText( "Eye Acuity       : " + String.valueOf(cur.getInt(6)) + "/20");
        }
    }
}
