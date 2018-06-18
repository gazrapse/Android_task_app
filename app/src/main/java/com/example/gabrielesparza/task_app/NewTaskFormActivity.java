package com.example.gabrielesparza.task_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class NewTaskFormActivity extends AppCompatActivity {
    static String tag = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_form);

        initiateTheSeekbarPercentage();
        addListenerToTheSeekbarPercentage();

        initiateSwitchDone();
        addListenerToTheSwitchDone();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void initiateTheSeekbarPercentage() {
        SeekBar seekBarPercentage = findViewById(R.id.SeekBarPercentage);

        seekBarPercentage.setMax(100);
        int intPercentage = 0;
        seekBarPercentage.setProgress(intPercentage);
        intPercentage = seekBarPercentage.getProgress();

        TextView strPercentage = findViewById(R.id.TextViewLabelPercentage);
        strPercentage.setText(intPercentage + "%");

        /*
        SeekBar seekbarPercentage = findViewById(R.id.SeekBarPercentage);

        seekbarPercentage.setMax(100);

        int defaultTaskPercentage = 0;
        seekbarPercentage.setProgress(defaultTaskPercentage);
        defaultTaskPercentage = seekbarPercentage.getProgress();
        Log.d(tag, "Progress" + defaultTaskPercentage);

        TextView textViewPercentage = findViewById(R.id.TextViewPercentage);
        String strDefaultTaskPercentage = defaultTaskPercentage + "%";
        textViewPercentage.setText(strDefaultTaskPercentage);
        */
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void addListenerToTheSeekbarPercentage() {
        SeekBar seekBar = findViewById(R.id.SeekBarPercentage);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int intProgress = 0;

            public void onProgressChanged(SeekBar seekbar, int i, boolean fromUser) {
                intProgress = i;
            }

            public void onStartTrackingTouch(SeekBar seekbar) {}

            public void onStopTrackingTouch(SeekBar seekbar) {
                Log.d(tag, "Percentage changed: " + intProgress);

                Switch switchDone = findViewById(R.id.SwitchDone);
                TextView textViewPercentage = findViewById(R.id.TextViewLabelPercentage);
                if (switchDone.isChecked()) {
                    textViewPercentage.setText("100%");
                } else {
                    String strTaskPercentage = intProgress + "%";
                    textViewPercentage.setText(strTaskPercentage);
                }
            }
        });
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void initiateSwitchDone() {
        Switch switchDone = findViewById(R.id.SwitchDone);

        if (switchDone.isChecked()) {
            TextView textViewPercentage = findViewById(R.id.TextViewLabelPercentage);
            textViewPercentage.setText("100%");
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void addListenerToTheSwitchDone(){
        Switch switchDone = findViewById(R.id.SwitchDone);

        switchDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView textViewPercentage = findViewById(R.id.TextViewLabelPercentage);
                SeekBar seekbarPercentage = findViewById(R.id.SeekBarPercentage);
                if(isChecked){

                    textViewPercentage.setText("100%");
                }
                else {
                    int TaskPercentage = seekbarPercentage.getProgress();
                    String strTaskPercentage = TaskPercentage + "%";
                    textViewPercentage.setText(strTaskPercentage);
                }
            }
        });
    }

    //------------------------------------------------------------------------------------------------------------------
    public void CancelNewTask(View view) {

        //                                                  //Back to main activity.
        android.content.Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    //------------------------------------------------------------------------------------------------------------------
    public void SaveNewTask(View view) {
        Log.d(tag, "click ButtonSave");

        Task task = new Task();

        //                                                  //Reading the short description.
        TextView editTextShortDescription = findViewById(R.id.editTextShortDescription);
        String strShortDescription = editTextShortDescription.getText().toString();
        task.setShortDescription(strShortDescription);

        //                                                  //Reading the long description.
        TextView editTextLongDescription = findViewById(R.id.editTextLongDescription);
        String strLongDescription = editTextLongDescription.getText().toString();
        task.setLongDescription(strLongDescription);

        //                                                  //Reading the percentage.
        Switch switchDone = findViewById(R.id.SwitchDone);
        if (switchDone.isChecked())
            task.setPercentage(100);
        else{
            SeekBar seekbarPercentage = findViewById(R.id.SeekBarPercentage);
            task.setPercentage(seekbarPercentage.getProgress());
        }

        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBSaveNewTask(taskDBInstance, task);
        //                                                  //Back to main activity.
        android.content.Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
