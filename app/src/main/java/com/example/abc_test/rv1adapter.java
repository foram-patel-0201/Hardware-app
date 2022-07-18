package com.example.abc_test;

import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class rv1adapter extends RecyclerView.Adapter<rv1adapter.rv1viewholder> {
    Context context;
    static ArrayList<model1> list;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public rv1adapter(ArrayList<model1> list) {

        this.list = list;
    }

    @NonNull
    @Override
    public rv1viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category, parent, false);
        return new rv1viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rv1viewholder holder, int position) {
        model1 m1 = list.get(position);
        holder.bind(m1);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class rv1viewholder extends RecyclerView.ViewHolder {
        TextView category;
        ImageButton updatebutton;
         ImageButton deletebutton;

        public rv1viewholder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            updatebutton = itemView.findViewById(R.id.buttonupdate);
            deletebutton = itemView.findViewById(R.id.buttondelete);
            final String[] categoryinput = new String[1];
            itemView.findViewById(R.id.buttondelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(updatebutton.getContext(),"deleting"+list.get(getAdapterPosition()).category.toString() , Toast.LENGTH_SHORT).show();

                   //TODO: Remove data from firebase
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), list.size());
                }
            });

            itemView.findViewById(R.id.buttonupdate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //TODO:Update in Firebase

                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Update Category: "+list.get(getAdapterPosition()).category);
                    final EditText input = new EditText(itemView.getContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            categoryinput[0] = input.getText().toString();
                            list.get(getAdapterPosition()).category = categoryinput[0];
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();



                }
            });

        }



        public void bind(model1 m1) {
            category.setText(m1.getCategory());

        }
    }

}
