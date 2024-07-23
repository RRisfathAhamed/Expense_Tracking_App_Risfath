package com.example.esxpense_tracking_app_risfath;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExpenseListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private List<Expense> expenseList;
    private TextView textViewTotalExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        recyclerView = findViewById(R.id.recyclerView);
        textViewTotalExpenses = findViewById(R.id.textViewTotalExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(this, expenseList);
        recyclerView.setAdapter(expenseAdapter);

        if (getIntent().hasExtra("updated_amount")) {
            double updatedAmount = getIntent().getDoubleExtra("updated_amount", 0);
            updateTotalExpenses(getCurrentTotalExpenses() + updatedAmount);
        } else if (getIntent().hasExtra("deleted_amount")) {
            double deletedAmount = getIntent().getDoubleExtra("deleted_amount", 0);
            updateTotalExpenses(getCurrentTotalExpenses() - deletedAmount);
        }

        loadExpenses();
    }

    private double getCurrentTotalExpenses() {
        String currentText = textViewTotalExpenses.getText().toString();
        String currentAmountString = currentText.replace("Total Expenses: $", "");
        return Double.parseDouble(currentAmountString);
    }


    private void loadExpenses() {
        ExpenseDbHelper dbHelper = new ExpenseDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                ExpenseContract.ExpenseEntry._ID,
                ExpenseContract.ExpenseEntry.COLUMN_AMOUNT,
                ExpenseContract.ExpenseEntry.COLUMN_CATEGORY,
                ExpenseContract.ExpenseEntry.COLUMN_DATE,
                ExpenseContract.ExpenseEntry.COLUMN_NOTES
        };

        Cursor cursor = db.query(
                ExpenseContract.ExpenseEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        double totalExpenses = 0;

        if (cursor != null) {
            expenseList.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry._ID));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_AMOUNT));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_CATEGORY));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_DATE));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NOTES));

                Expense expense = new Expense(id, amount, category, date, notes);
                expenseList.add(expense);
                totalExpenses += amount;
            }
            cursor.close();
        }

        expenseAdapter.notifyDataSetChanged();
        updateTotalExpenses(totalExpenses);
    }

    public void updateTotalExpenses(double totalExpenses) {
        textViewTotalExpenses.setText("Total Expenses: $" + totalExpenses);
    }
}
