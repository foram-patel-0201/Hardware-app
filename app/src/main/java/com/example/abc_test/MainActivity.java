package com.example.abc_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    rv1adapter adapter;
    ArrayList<model1> list;
    ImageView imageView;
    String categoryinput;
    Button buttonupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv1);
        database = FirebaseDatabase.getInstance().getReference("categories");
        recyclerView.setHasFixedSize(true);



        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                list = new ArrayList<>();
                adapter = new rv1adapter(list);
                recyclerView.setAdapter(adapter);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    model1 m1 = dataSnapshot.getValue(model1.class);
                    list.add(m1);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addcategory(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Category");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                categoryinput = input.getText().toString();
                Map<String,Object> map=new HashMap<>();
                map.put("category",categoryinput);
                FirebaseDatabase.getInstance().getReference().child("categories").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                categoryinput = "";
                                Toast.makeText(getApplicationContext(),"Inserted successfully",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getApplicationContext(),"Not Inserted",Toast.LENGTH_LONG).show();

                            }
                        });

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

    public void updatedata(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Category");

        final EditText editText = new EditText(this);

        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editText);



    }


}