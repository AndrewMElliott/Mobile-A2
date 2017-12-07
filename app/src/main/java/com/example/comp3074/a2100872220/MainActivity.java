package com.example.comp3074.a2100872220;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private Button btnLogin;
    private String user;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        userName = (EditText) findViewById(R.id.edtUsername);
        password = (EditText)  findViewById(R.id.edtPassword);
        //switchToPatientScreen();
        btnLogin.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
                if(checkLogin(userName.getText().toString().trim(),password.getText().toString().trim())){
                    switchToPatientScreen();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid User",
                            Toast.LENGTH_LONG).show();
                }
           }
        });
     }

    public boolean checkLogin(String username, String pw){
        DbHelper helper = new DbHelper(this);

        Cursor cur = helper.confirmLogin(username,pw);
        if(cur.moveToFirst()){
            user = cur.getString(cur.getColumnIndex("first_name"));
            return true;
        } else {
            return false;
        }
    }

    public void switchToPatientScreen(){
        Intent intent = new Intent(MainActivity.this, PatientActivity.class);
        intent.putExtra("user", this.user); //Optional parameters
        startActivity(intent);
    }
}
