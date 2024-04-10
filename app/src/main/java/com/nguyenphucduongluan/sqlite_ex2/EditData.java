package com.nguyenphucduongluan.sqlite_ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nguyenphucduongluan.models.Films;
import com.nguyenphucduongluan.sqlite_ex2.databinding.ActivityEditDataBinding;

public class EditData extends AppCompatActivity {
    ActivityEditDataBinding binding;
    Films f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getData();
        addEvents();
    }

    private void getData() {
        f = (Films) getIntent().getSerializableExtra("data");
        binding.edtName.setText(f.getFilmsName());
        binding.edtPrice.setText(String.valueOf(f.getFilmsPrice()));
    }
    private void updateData() {
        ContentValues values = new ContentValues();
        values.put(Databases.COL_NAME, binding.edtName.getText().toString());
        values.put(Databases.COL_PRICE, Double.parseDouble(binding.edtPrice.getText().toString()));
        int numberOfRows = MainActivity.db.getWritableDatabase().update(
                Databases.TBL_NAME, // Tên bảng
                values, // Dữ liệu mới
                Databases.COL_CODE + "=?", // Điều kiện (theo film_code)
                new String[]{String.valueOf(f.getFilmsCode())});

        if(numberOfRows > 0) {
            Toast.makeText(this, "Success nè!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
        }
        setResult(RESULT_OK);
        finish();
    }

    private void addEvents() {
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}