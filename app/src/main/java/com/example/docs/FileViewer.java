package com.example.docs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileViewer extends AppCompatActivity {
    TextView textView;
    String myContent;
    String docID;
    String contetnforEdit,filenameforEdit;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("My Documents");
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.starDocument_menu:
                starDocument();
                return true;
            case R.id.saveonDevice :
                saveOnDevice();
                return true;
            case R.id.openDocument_menu:
                editDoc();
                return true;
            case R.id.deleteFile :
                movetoBin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_viewer);
        Intent intent = getIntent();
        myContent = intent.getStringExtra("title");
        docID = intent.getStringExtra("id");

        textView = findViewById(R.id.fileViewTextView);
        collectionReference.document(docID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        contetnforEdit = documentSnapshot.getString("content");
                        filenameforEdit = documentSnapshot.getString("fileName");
                        textView.setText(contetnforEdit);
                        Log.d("______", "content : " + documentSnapshot.getString("content"));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("______", "content : " + e.toString());

            }
        });

    }

    void starDocument() {
        collectionReference.document(docID).update("starred", true);
        Toast.makeText(this, "Document Starred", Toast.LENGTH_SHORT).show();
    }
    void saveOnDevice(){
        FileOutputStream fos = null;
        try {
            fos=openFileOutput(filenameforEdit+".txt",MODE_PRIVATE);
            fos.write(contetnforEdit.getBytes());
            Toast.makeText(this,"File saved to "+getFilesDir()+"/"+filenameforEdit,Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fos!=null)
            {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    void editDoc(){
        Intent intent = new Intent(this,Editor.class);
        intent.putExtra("id",docID);
        intent.putExtra("fileName",filenameforEdit);
        intent.putExtra("content",contetnforEdit);
        intent.putExtra("forEdit",true);
        startActivity(intent);
    }
    void  movetoBin(){
        collectionReference.document(docID).update("onBin",true);
        Toast.makeText(this, "Document Deleted", Toast.LENGTH_SHORT).show();

    }
}
