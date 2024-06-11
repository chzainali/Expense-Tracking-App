package com.example.expensetrackingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class IncomeActivity extends AppCompatActivity {
    ImageView ivBack;
    TextView tvTotalIncome, noBudgetFoundTV;
    RecyclerView budgetRV;
    DatabaseHelper databaseHelper;
    List<IncomeExpenseModel> list = new ArrayList<>();
    IncomeExpenseAdapter adapter;
    FloatingActionButton addIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        ivBack = findViewById(R.id.ivBack);
        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        noBudgetFoundTV = findViewById(R.id.noBudgetFoundTV);
        budgetRV = findViewById(R.id.budgetRV);
        addIncome = findViewById(R.id.addIncome);
        databaseHelper = new DatabaseHelper(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new IncomeExpenseAdapter(list, this, "income");
        budgetRV.setLayoutManager(new LinearLayoutManager(this));
        budgetRV.setAdapter(adapter);

        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IncomeActivity.this, AddIncomeActivity.class));
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        tvTotalIncome.setText("Total Income: " + databaseHelper.getTotalIncomeExpenseByType("income")+" HRK");
        list.clear();
        list = databaseHelper.getIncomesExpensesByType("income");
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