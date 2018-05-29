package com.example.szymon.mygameproject;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity {

    private Button buttonStart;
    private Spinner spinnerControl, spinnerDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        buttonStart = (Button) findViewById(R.id.buttonStart);
        spinnerControl = (Spinner) findViewById(R.id.spinnerControl);
        spinnerDifficulty = (Spinner) findViewById(R.id.spinnerDifficulty);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.control,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.difficulty,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerControl.setAdapter(adapter);
        spinnerDifficulty.setAdapter(adapter2);


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("Control", spinnerControl.getSelectedItem().toString());
                intent.putExtra("Difficulty", spinnerDifficulty.getSelectedItem().toString());
                startActivity(intent);
            }
        });

    }
}