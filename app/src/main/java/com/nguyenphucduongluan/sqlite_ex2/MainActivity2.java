package com.nguyenphucduongluan.sqlite_ex2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.nguyenphucduongluan.adapters.RecycleAdapter;
import com.nguyenphucduongluan.models.Films;
import com.nguyenphucduongluan.sqlite_ex2.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
    RecycleAdapter adapter;
    ArrayList<Films> films;

    Databases db;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.LEFT){
                //Toast.makeText(MainActivity2.this, "To Left", Toast.LENGTH_SHORT).show();
                //Edit
            } else if (direction == ItemTouchHelper.RIGHT) {
                //Toast.makeText(MainActivity2.this, "To Right", Toast.LENGTH_SHORT).show();
                //Delete
                //films.remove(viewHolder.getAdapterPosition()); (Xóa giao diện)
                Films f = films.get(viewHolder.getAdapterPosition());
                boolean delete = db.execSql("DELETE FROM " +Databases.TBL_NAME + " WHERE " + Databases.COL_CODE + "=" + f.getFilmsCode());
                adapter.notifyDataSetChanged();
                addData();
            }


        }
    };
    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new Databases(MainActivity2.this);
        db.createSampleData();
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvFilms);



        addData();
    }

    private void addData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvFilms.setLayoutManager(linearLayoutManager);

        films = new ArrayList<>();
        Cursor cursor = db.querryData("SELECT * FROM " + Databases.TBL_NAME);
        while (cursor.moveToNext()){
            films.add(new Films(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2)));
        }



        adapter = new RecycleAdapter(MainActivity2.this, films);
        binding.rvFilms.setAdapter(adapter);

    }
}