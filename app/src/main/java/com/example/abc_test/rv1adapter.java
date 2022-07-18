package com.example.abc_test;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class rv1adapter extends RecyclerView.Adapter<rv1adapter.rv1viewholder>
{
    Context context;
    ArrayList<model1> list;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public rv1adapter(Context context, ArrayList<model1> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public rv1viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category,parent,false);
        return new rv1viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rv1viewholder holder, int position) {
        model1 m1= list.get(position);
        holder.category.setText(m1.getCategory());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class rv1viewholder extends RecyclerView.ViewHolder
    {
        TextView category;
        ImageButton updatebutton,deletebutton;

        public rv1viewholder(@NonNull View itemView) {
            super(itemView);

            category=itemView.findViewById(R.id.category);
            updatebutton=itemView.findViewById(R.id.buttonupdate);
            deletebutton=itemView.findViewById(R.id.buttondelete);


        }
    }

}
