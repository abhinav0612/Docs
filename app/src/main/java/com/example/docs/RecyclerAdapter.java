package com.example.docs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docs.Fragments.Recent_fragment;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.myViewHolder>  {
    private Context mcontext;
    private List<Document> mList;


    public RecyclerAdapter(Context mcontext, List<Document> mList) {
        this.mcontext = mcontext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        view = layoutInflater.inflate(R.layout.cardview_layout,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
    final Document document = mList.get(position);
    holder.textView.setText("FileName : " +document.getFileName()+"\n"
    +"Date Modified : " + document.getDate());
    holder.linearLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mcontext,FileViewer.class);
            intent.putExtra("id",document.getDocID());
            intent.putExtra("title",document.getFileName());
            mcontext.startActivity(intent);
        }
    });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        LinearLayout linearLayout;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            textView = itemView.findViewById(R.id.cardText);
        }



    }


}

