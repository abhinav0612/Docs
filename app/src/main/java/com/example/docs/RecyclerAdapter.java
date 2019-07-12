package com.example.docs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.myViewHolder> {
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
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
    Document document = mList.get(position);
    holder.textView.setText("FileName : " +document.getFileName()+"\n"
    +"Date Modified : " + document.getDate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cardText);
        }



    }
}
