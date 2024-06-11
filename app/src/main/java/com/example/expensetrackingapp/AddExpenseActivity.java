package com.example.expensetrackingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {
    ImageView ivBack;
    TextView tvLabel;
    TextInputEditText etTitle, etExpense, etDetails;
    AppCompatButton btnAdd, btnDelete;
    IncomeExpenseModel previousModel;
    String title, amount, details;
    DatabaseHelper databaseHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        ivBack = findViewById(R.id.ivBack);
        tvLabel = findViewById(R.id.tvLabel);
        etTitle = findViewById(R.id.etTitle);
        etExpense = findViewById(R.id.etExpense);
        etDetails = findViewById(R.id.etDetails);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        databaseHelper = new DatabaseHelper(this);

        if (getIntent().getExtras() != null){
            previousModel = (IncomeExpenseModel) getIntent().getSerializableExtra("data");
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (previousModel != null) {
            tvLabel.setText("Update Expense");
            btnAdd.setText("Update");
            btnDelete.setVisibility(View.VISIBLE);
            etTitle.setText(previousModel.getTitle());
            etExpense.setText(previousModel.getAmount());
            etDetails.setText(previousModel.getDetails());
        } else {
            tvLabel.setText("Add Income");
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()) {
                    IncomeExpenseModel incomeExpenseModel;
                    if (previousModel != null) {
                        incomeExpenseModel = new IncomeExpenseModel(previousModel.getAmountId(), title, amount, details, previousModel.getDateTime(), previousModel.getType());
                        databaseHelper.updateIncomeExpense(incomeExpenseModel);
                        showMessage("Successfully Updated");
                    } else {
                        incomeExpenseModel = new IncomeExpenseModel(title, amount, details, getCurrentDateTime(), "expense");
                        databaseHelper.addIncomeExpenseByType(incomeExpenseModel);
                        showMessage("Successfully Added");
                    }
                    finish();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previousModel != null) {
                    databaseHelper.deleteIncomeExpense(previousModel);
                    showMessage("Deleted Successfully");
                    finish();
                }
            }
        });

    }

    @SuppressLint("SimpleDateFormat")
    public String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss aa");
        return dateFormat.format(calendar.getTime());
    }

    private Boolean isValidated() {
        title = etTitle.getText().toString().trim();
        amount = etExpense.getText().toString().trim();
        details = etDetails.getText().toString().trim();

        if (title.isEmpty()) {
            showMessage("Please enter title");
            return false;
        }

        if (amount.isEmpty()) {
            showMessage("Please enter amount");
            return false;
        }

        if (details.isEmpty()) {
            showMessage("Please enter details");
            return false;
        }

        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}