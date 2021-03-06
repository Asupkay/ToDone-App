package com.alexsupkay.todone.todone;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alexsupkay.todone.todone.Fragments.DatePickerFragment;
import com.alexsupkay.todone.todone.data.TaskContract;
import com.alexsupkay.todone.todone.data.TaskContract.TaskEntry;


public class AddTaskActivity extends AppCompatActivity {

    // Declare a member variable to keep track of a task's selected mPriority
    private int mPriority;
    private TextView dueDateTV;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize to highest mPriority by default (mPriority = 1)
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        dueDateTV = (TextView) findViewById(R.id.due_date_text);
        mPriority = 1;
    }


    /**
     * onClickAddTask is called when the "ADD" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onClickAddTask(View view) {
        // Not yet implemented
        // Check if EditText is empty, if not retrieve input and store it in a ContentValues object
        // If the EditText input is empty -> don't create an entry
        String descriptionInput = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();
        String dateInput = getDateText();
        if (descriptionInput.length() == 0) {
            return;
        }

        // Insert new task data via a ContentResolver
        // Create new empty ContentValues object
        ContentValues contentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(TaskEntry.COLUMN_DESCRIPTION, descriptionInput);
        contentValues.put(TaskEntry.COLUMN_DUE_DATE, dateInput);
        contentValues.put(TaskEntry.COLUMN_PRIORITY, mPriority);
        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(TaskEntry.CONTENT_URI, contentValues);

        // Display the URI that's returned with a Toast
        // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        // Finish activity (this returns back to MainActivity)
        finish();

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public String getDateText() {
        return (String) dueDateTV.getText();
    }

    public void setDateText(String date) {
        dueDateTV.setText(date);
    }


    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */
    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        }
    }
}