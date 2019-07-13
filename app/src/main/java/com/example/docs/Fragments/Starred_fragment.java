package com.example.docs.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docs.Document;
import com.example.docs.R;
import com.example.docs.RecyclerAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Starred_fragment extends Fragment {
    List<Document> myList = new ArrayList<>();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference myCollection = database.collection("My Documents");
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.star_fragment,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(Starred_fragment.this.getContext()
                ,2));
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.gridMenu:
                recyclerView.setLayoutManager(new GridLayoutManager(Starred_fragment.this.getContext()
                        ,2));
                return true;
            case R.id.listMenu:
                recyclerView.setLayoutManager(new LinearLayoutManager(Starred_fragment.this.getContext()));
                return true;
            case R.id.lastModifiedMenu:
                getSortedasModified();
                break;
            case R.id.nameMenu:
                getSortedasName();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    void getData()
    {
        myCollection.whereEqualTo("starred",true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e!=null)
                {
                    return;
                }else {
                    if (queryDocumentSnapshots==null)
                    {
                        return;
                    }
                    else {
                        myList.clear();
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                        {
                            Document doc = snapshot.toObject(Document.class);
                            doc.setDocID(snapshot.getId());
                            myList.add(doc);
                        }
                    }

                    adapter = new RecyclerAdapter(Starred_fragment.this.getContext(),myList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }
    void getSortedasName()
    {
        myCollection.orderBy("fileName")
                .whereEqualTo("starred",true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e!=null)
                        {
                            return;
                        }else {
                            if (queryDocumentSnapshots==null)
                            {
                                return;
                            }
                            else {
                                myList.clear();
                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                                {
                                    Document doc = snapshot.toObject(Document.class);
                                    myList.add(doc);
                                }
                            }
                            adapter = new RecyclerAdapter(Starred_fragment.this.getContext(),myList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    void getSortedasModified()
    {
        myCollection.orderBy("date", Query.Direction.DESCENDING)
                .whereEqualTo("starred",true)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e!=null)
                        {
                            return;
                        }else {
                            if (queryDocumentSnapshots==null)
                            {
                                return;
                            }
                            else {
                                myList.clear();
                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                                {
                                    Document doc = snapshot.toObject(Document.class);
                                    myList.add(doc);
                                }
                            }
                            adapter = new RecyclerAdapter(Starred_fragment.this.getContext(),myList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}
