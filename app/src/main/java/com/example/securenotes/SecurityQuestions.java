package com.example.securenotes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SecurityQuestions extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    MainActivity m = new MainActivity();
    QuestionsDatabase myDbQ;
    EditText ans;
    Button btnSubmit;
    TextView queTextView;
    String que;
    Cursor cursor;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_questions);

        myDbQ = new QuestionsDatabase(SecurityQuestions.this);
        cursor = myDbQ.getAllData();
        Spinner spinner = findViewById(R.id.spinner);
        ans = findViewById(R.id.editTextAnswer);
        queTextView = findViewById(R.id.textViewQuestion);
        btnSubmit = findViewById(R.id.buttonSubmit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.questions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        if(cursor.getCount()==0){
            spinner.setVisibility(View.VISIBLE);
            queTextView.setVisibility(View.INVISIBLE);
        }

        else{
            spinner.setVisibility(View.INVISIBLE);
            queTextView.setVisibility(View.VISIBLE);
            cursor.moveToNext();
            queTextView.setText(m.decrypt(cursor.getString(0)));
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(cursor.getCount()==0) {
                    String answer = ans.getText().toString();
                    myDbQ.insertData(m.encrypt(que), m.encrypt(answer));
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        que = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), que, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}