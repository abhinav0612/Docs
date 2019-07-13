package com.example.docs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Editor extends AppCompatActivity {
    EditText content,filename;
    Button save;
    String id;
    Boolean forEdit = false;
    final private String FILE_NAME = "file_name";
    final private String CONTENT = "content";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference myCollection = db.collection("My Documents");

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        content=findViewById(R.id.editor_editText);
        save = findViewById(R.id.editor_save);
        filename = findViewById(R.id.editor_filename);
        Intent intent = getIntent();
        if (intent.hasExtra("forEdit"))
        {
            id=intent.getStringExtra("id");
            forEdit =true;
            getSupportActionBar().setTitle("Edit");
            save.setText("Update");
            content.setText(intent.getStringExtra("content"));
            filename.setText(intent.getStringExtra("fileName"));
        }
        else {
            getSupportActionBar().setTitle("Add");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filename.getText().toString().isEmpty())
                {
                    Toast.makeText(Editor.this,"Please Enter File Name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    save();
                    finish();
                }
            }
        });

    }
    void save()
    {
        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        String time = DateFormat.getTimeInstance().format(calendar.getTime());
        Log.d("______","date : " + date + time);
        String file = filename.getText().toString().trim();
        String myContent =  content.getText().toString();
        Document document = new Document(file,myContent,date);
        if (forEdit==false){
            myCollection.add(document).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(Editor.this,"Document Saved",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("____","error : " + e.toString());
                }
            });
        }
        if (forEdit==true){
            myCollection.document(id).update("content",myContent);
            myCollection.document(id).update("fileName",file);
        }


    }
}
