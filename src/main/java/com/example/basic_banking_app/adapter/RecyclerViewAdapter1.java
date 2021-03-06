package com.example.basic_banking_application.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basic_banking_application.All_Costumers;
import com.example.basic_banking_application.R;
import com.example.basic_banking_application.data.DBHelper;
import com.example.basic_banking_application.data.MyDbHandler;
import com.example.basic_banking_application.model.Customers;

import java.util.List;

public class RecyclerViewAdapter1  extends RecyclerView.Adapter<RecyclerViewAdapter1.ViewHolder> {

    public Context context;
    public List<Customers> customersList;
    DBHelper DB;

    public RecyclerViewAdapter1(Context context, List<Customers> customersList) {
        this.context = context;
        this.customersList = customersList;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        context=parent.getContext();
        return new ViewHolder(view);
    }

    // What will happen after we create the viewholder object
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter1.ViewHolder holder, int position) {
        Customers customers = customersList.get(position);

        holder.customerName.setText(customers.getName());
        holder.amount.setText(customers.getAmount());
        Drawable drawable = context.getResources().getDrawable(customers.getphoto());
        holder.photo.setImageDrawable(drawable);
    }

    // How many items?
    @Override
    public int getItemCount() {
        return customersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView customerName;
        public TextView amount;
        public ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            customerName = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            photo = itemView.findViewById(R.id.photo);

            photo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //      Log.d("dbkaushal", "Clicked");
            DB = new DBHelper(context);

            int position = this.getAdapterPosition();
            Customers customers = customersList.get(position);
            String name = customers.getName();
            String money = customers.getAmount();
            int id = position+1;

            // Toast.makeText(context, "Successfully transfer money to '"+ name +"'", Toast.LENGTH_SHORT).show();
            Log.d("dbkaushal", "Successfully transfer money to " + name );
            Toast.makeText(context, "Successfully transfer money to '" + name + "'", Toast.LENGTH_LONG).show();

            Customers customers11 = new Customers();
            Double newmoney =customers11.getNewmoney();

            String newaddedamout = Double.toString(Double.parseDouble(money)+newmoney);

            MyDbHandler db;
            db = new MyDbHandler(context);
            db.UpdateCustomersById(id,newaddedamout);

            //insert into History
            String sender = customers11.getSender();
            DB.inserthistory(sender,name,newmoney.toString());

            Intent intent = new Intent(context,All_Costumers.class);
            context.startActivities(new Intent[]{intent});

        }

    }
}