package com.example.expensetrackingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {
    ImageView ivBack;
    TextView tvTotalExpense, noBudgetFoundTV;
    RecyclerView budgetRV;
    DatabaseHelper databaseHelper;
    List<IncomeExpenseModel> list = new ArrayList<>();
    IncomeExpenseAdapter adapter;
    FloatingActionButton addExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        ivBack = findViewById(R.id.ivBack);
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        noBudgetFoundTV = findViewById(R.id.noBudgetFoundTV);
        budgetRV = findViewById(R.id.budgetRV);
        addExpense = findViewById(R.id.addExpense);
        databaseHelper = new DatabaseHelper(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new IncomeExpenseAdapter(list, this, "expense");
        budgetRV.setLayoutManager(new LinearLayoutManager(this));
        budgetRV.setAdapter(adapter);

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpenseActivity.this, AddExpenseActivity.class));
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        tvTotalExpense.setText("Total Expense: " + databaseHelper.getTotalIncomeExpenseByType("expense")+" HRK");
        list.clear();
        list = databaseHelper.getIncomesExpensesByType("expense");
        if (list.isEmpty()) {
            noBudgetFoundTV.setVisibility(View.VISIBLE);
            budgetRV.setVisibility(View.GONE);
        } else {
            adapter.setList(list);
            noBudgetFoundTV.setVisibility(View.GONE);
            budgetRV.setVisibility(View.VISIBLE);
        }
    }

}