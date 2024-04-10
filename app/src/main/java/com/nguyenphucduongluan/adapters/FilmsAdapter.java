package com.nguyenphucduongluan.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nguyenphucduongluan.models.Films;
import com.nguyenphucduongluan.sqlite_ex2.EditData;
import com.nguyenphucduongluan.sqlite_ex2.MainActivity;
import com.nguyenphucduongluan.sqlite_ex2.R;

import java.io.Serializable;
import java.util.List;

public class FilmsAdapter extends BaseAdapter{
    MainActivity context; //Màn hình
    int item_list;
    List<Films> films;

    public FilmsAdapter(MainActivity context, int item_list, List<Films> films) {
        this.context = context;
        this.item_list = item_list;
        this.films = films;
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        return films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_list, null);
            holder.txtInfor = convertView.findViewById(R.id.txtInfor);
            holder.imvDelete = convertView.findViewById(R.id.imvDelete);
            holder.imvEdit = convertView.findViewById(R.id.imvEdit);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Films f = films.get(position);
        holder.txtInfor.setText(f.getFilmsName() + " - " + f.getFilmsPrice());
        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openDialogEdit(f);
            }
        });
        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openDialogDelete(f);
            }
        });
        return convertView;


    }
    public static class ViewHolder {
        TextView txtInfor;
        ImageView imvEdit, imvDelete;
    }
}
