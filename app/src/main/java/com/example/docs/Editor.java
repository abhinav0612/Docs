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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Editor extends AppCompatActivity {
    EditText content,filename;
    Button save;
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
        filename = findViewById(R.id.editor_filename);
        save = findViewById(R.id.editor_save);
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
        String myCOntent =  content.getText().toString();
        Document document = new Document(file,myCOntent,date);

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
}
