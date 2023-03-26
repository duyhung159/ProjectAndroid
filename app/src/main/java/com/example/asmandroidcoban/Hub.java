package com.example.asmandroidcoban;


import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Hub extends AppCompatActivity {

    private Button btnThemLop,btnDanhSachLop,btnQLSinhVien;
    private DataBase mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnThemLop = findViewById(R.id.btnThemLop);
        btnDanhSachLop = findViewById(R.id.btnXemLop);
        btnQLSinhVien = findViewById(R.id.btnQLSV);

        mDataBase = new DataBase(Hub.this);
        mDataBase.querSQL("CREATE TABLE IF NOT EXISTS listLop(maLop varchar(10) primary key,tenLop nvarchar(20))");

        btnThemLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickThemLop();
            }
        });

        btnDanhSachLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickXemLop();
            }
        });

        btnQLSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickQLSV();
            }
        });
    }

    private void clickThemLop(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.themlop_dialog);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnThem = dialog.findViewById(R.id.btnLuuLop);
        Button btnXoaTrang = dialog.findViewById(R.id.btnXoaTrang);
        EditText edtMaLop = dialog.findViewById(R.id.edtMaLop);
        EditText edtTenLop = dialog.findViewById(R.id.edtTenLop);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLop = edtTenLop.getText().toString();
                String maLop = edtMaLop.getText().toString();

                try {
                    mDataBase.querSQL("INSERT INTO listLop VALUES('" + maLop + "','"+ tenLop +"')");
                    Toast.makeText(Hub.this, "Thêm lớp thành công", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(Hub.this, "Mã lớp "+maLop+" đã tồn tại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnXoaTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMaLop.setText("");
                edtTenLop.setText("");
            }
        });
        dialog.show();
    }

    private void clickXemLop(){
        Intent intent = new Intent(getApplicationContext(),DanhSachLop.class);
        startActivity(intent);
    }

    private void clickQLSV(){
        Cursor mCursorSV2 = mDataBase.getData("SELECT * FROM listLop");
        ArrayList<LopHoc> ltLop = new ArrayList<>();

        while (mCursorSV2.moveToNext()){
            ltLop.add(new LopHoc(mCursorSV2.getString(0),mCursorSV2.getString(1)));
        }
        if (ltLop.size() == 0){
            Toast.makeText(this, "Chưa có lớp nào được tạo", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(getApplicationContext(),QLSinhVien.class);
            startActivity(intent);
        }

    }

}