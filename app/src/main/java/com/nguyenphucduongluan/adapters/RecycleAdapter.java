package com.nguyenphucduongluan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenphucduongluan.models.Films;
import com.nguyenphucduongluan.sqlite_ex2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>{
    Context context;
    List<Films> films;

    public RecycleAdapter(Context context, List<Films> films) {
        this.context = context;
        this.films = films;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtInfor.setText(films.get(position).getFilmsName() + " - " + films.get(position).getFilmsPrice());

    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtInfor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInfor = itemView.findViewById(R.id.txtInfor);
        }
    }

}
