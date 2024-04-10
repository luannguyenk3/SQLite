package com.nguyenphucduongluan.sqlite_ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nguyenphucduongluan.sqlite_ex2.databinding.ActivityAddDataBinding;

public class AddData extends AppCompatActivity {
    ActivityAddDataBinding binding;
    Databases databases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databases = new Databases(this);
        addEvents();
    }

    private void addEvents() {
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(Databases.COL_NAME, binding.edtName.getText().toString() );
                values.put(Databases.COL_PRICE, Double.parseDouble(binding.edtPrice.getText().toString()));

                long numbrow = MainActivity.db.getWritableDatabase().insert(Databases.TBL_NAME, null, values);
                if(numbrow > 0) {
                    Toast.makeText(AddData.this, "Success", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
                else {
                    Toast.makeText(AddData.this, "Fail", Toast.LENGTH_SHORT).show();
                    finish();
                }


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