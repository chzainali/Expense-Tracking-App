package com.example.expensetrackingapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class IncomeExpenseAdapter extends RecyclerView.Adapter<IncomeExpenseAdapter.ViewHolder> {
    List<IncomeExpenseModel> list;
    Context context;
    String checkFrom;

    public IncomeExpenseAdapter(List<IncomeExpenseModel> list, Context context, String checkFrom) {
        this.list = list;
        this.context = context;
        this.checkFrom = checkFrom;
    }

    public void setList(List<IncomeExpenseModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_items,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        IncomeExpenseModel amountModel = list.get(position);
        holder.tvName.setText(amountModel.getTitle());
        holder.tvPrice.setText("HRK"+amountModel.getAmount());
        holder.tvDate.setText(amountModel.getDateTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (checkFrom.equals("income")){
                    intent = new Intent(context, AddIncomeActivity.class);
                }else{
                    intent = new Intent(context, AddExpenseActivity.class);
                }
                intent.putExtra("data", amountModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDetails);
            tvPrice = itemView.findViewById(R.id.tvMoney);
        }

    }
}
