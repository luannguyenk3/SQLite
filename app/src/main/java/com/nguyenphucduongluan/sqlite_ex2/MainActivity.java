package com.nguyenphucduongluan.sqlite_ex2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nguyenphucduongluan.adapters.FilmsAdapter;
import com.nguyenphucduongluan.models.Films;
import com.nguyenphucduongluan.sqlite_ex2.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static Databases db = null;
    FilmsAdapter adapter;
    ArrayList<Films> films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createDB();

    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnAdd){
            // Mở AddData và chờ kết quả trả về
            Dialog dialog = new Dialog(MainActivity.this);
//            dialog.requestWindowFeature();
            dialog.setContentView(R.layout.activity_add_data); //Nạp giao diện

            // Find views in dialog layout
            EditText edtName = dialog.findViewById(R.id.edtName);
            EditText edtPrice = dialog.findViewById(R.id.edtPrice);
            Button btnSave = dialog.findViewById(R.id.btnSave);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);

            // Click event for save button
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean inserted =

                    // Insert data to database
                    db.execSql("INSERT INTO " + Databases.TBL_NAME + " VALUES(null, '" + edtName.getText().toString()+ "', " + edtPrice.getText().toString() + " ) ");

                    if (inserted) {
                        Toast.makeText(MainActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        loadData();
                        dialog.dismiss();

                    } else {
                        Toast.makeText(MainActivity.this, "Failed to add data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void createDB() {
        db = new Databases(MainActivity.this);
        db.createSampleData();
    }

    private void loadData() {
        films = new ArrayList<>();
        //Đọc từ db
        Cursor cursor = db.querryData(" SELECT * FROM " + Databases.TBL_NAME);

        while (cursor.moveToNext()) {
            films.add(new Films(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2)));
        } cursor.close();

        adapter = new FilmsAdapter(MainActivity.this, R.layout.item_list, films);
        binding.lvFilm.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }
    public void openDialogEdit(Films f) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_edit_data);
        EditText edtName = dialog.findViewById(R.id.edtName);
        EditText edtPrice = dialog.findViewById(R.id.edtPrice);
        edtName.setText(f.getFilmsName());
        edtPrice.setText(String.valueOf(f.getFilmsPrice()));

        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean updated = db.execSql("UPDATE " + Databases.TBL_NAME + " SET " + Databases.COL_NAME + " = " + edtName.getText().toString() + Databases.COL_PRICE + " = " + edtPrice.getText().toString() + " WHERE " + Databases.COL_CODE + " = " + f.getFilmsCode());                if(updated) {
                    Toast.makeText(MainActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    public void openDialogDelete(Films f) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xác nhận xóa?");
        builder.setIcon(R.drawable.baseline_delete_24);
        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm" + f.getFilmsName() + "?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean delete = db.execSql("DELETE FROM " + Databases.TBL_NAME + "WHERE " + Databases.COL_CODE + " = " + f.getFilmsCode());
                if(delete){
                    Toast.makeText(MainActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }







}