package com.example.esxpense_tracking_app_risfath;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText editTextAmount;
    private EditText editTextCategory;
    private EditText editTextDate;
    private EditText editTextNotes;
    private ExpenseDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        dbHelper = new ExpenseDbHelper(this);

        editTextAmount = findViewById(R.id.editTextAmount);
        editTextCategory = findViewById(R.id.editTextCategory);
        editTextDate = findViewById(R.id.editTextDate);
        editTextNotes = findViewById(R.id.editTextNotes);

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExpense();
            }
        });
    }

    private void saveExpense() {
        String amountString = editTextAmount.getText().toString();
        String category = editTextCategory.getText().toString();
        String date = editTextDate.getText().toString();
        String notes = editTextNotes.getText().toString();

        if (amountString.isEmpty() || category.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill out all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountString);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExpenseContract.ExpenseEntry.COLUMN_AMOUNT, amount);
        values.put(ExpenseContract.ExpenseEntry.COLUMN_CATEGORY, category);
        values.put(ExpenseContract.ExpenseEntry.COLUMN_DATE, date);
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NOTES, notes);

        long newRowId = db.insert(ExpenseContract.ExpenseEntry.TABLE_NAME, null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Expense added successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddExpenseActivity.this, ExpenseListActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error adding expense", Toast.LENGTH_SHORT).show();
        }
    }
}
