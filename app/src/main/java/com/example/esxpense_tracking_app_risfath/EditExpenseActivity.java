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

public class EditExpenseActivity extends AppCompatActivity {

    private EditText editTextAmount;
    private EditText editTextCategory;
    private EditText editTextDate;
    private EditText editTextNotes;
    private ExpenseDbHelper dbHelper;
    private int expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        dbHelper = new ExpenseDbHelper(this);

        editTextAmount = findViewById(R.id.editTextAmount);
        editTextCategory = findViewById(R.id.editTextCategory);
        editTextDate = findViewById(R.id.editTextDate);
        editTextNotes = findViewById(R.id.editTextNotes);

        Intent intent = getIntent();
        expenseId = intent.getIntExtra("expense_id", -1);
        double amount = intent.getDoubleExtra("amount", 0);
        String category = intent.getStringExtra("category");
        String date = intent.getStringExtra("date");
        String notes = intent.getStringExtra("notes");

        editTextAmount.setText(String.valueOf(amount));
        editTextCategory.setText(category);
        editTextDate.setText(date);
        editTextNotes.setText(notes);

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateExpense();
            }
        });
    }

    private void updateExpense() {
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

        int rowsAffected = db.update(ExpenseContract.ExpenseEntry.TABLE_NAME, values, ExpenseContract.ExpenseEntry._ID + "=?", new String[]{String.valueOf(expenseId)});
        if (rowsAffected > 0) {
            Toast.makeText(this, "Expense updated successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditExpenseActivity.this, ExpenseListActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error updating expense", Toast.LENGTH_SHORT).show();
        }
    }
}