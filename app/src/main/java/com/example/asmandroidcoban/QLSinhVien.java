package com.example.asmandroidcoban;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QLSinhVien extends AppCompatActivity {
    private DataBase mDataBase;
    private ArrayList<String> listMaLop = new ArrayList<>();
    private ArrayList<SinhVien> listSinhVien = new ArrayList<>();
    private ArrayList<SinhVien> listVSV = new ArrayList<>();
    private ArrayAdapter spnArrAdapter;
    private MyAdapterSV myAdapterSV;
    private ListView lvSinhVien;
    private Spinner mSpinner;
    private Button btnAddSV;
    private EditText edtTenSV,edtNgaySinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlsinh_vien);


        mDataBase = new DataBase(getApplicationContext());
        mSpinner = findViewById(R.id.spnLop);
        lvSinhVien = findViewById(R.id.lvDanhSachSV);
        btnAddSV = findViewById(R.id.btnAddSV);
        edtTenSV = findViewById(R.id.edtTenSV);
        edtNgaySinh  = findViewById(R.id.edtNgaySinh);

        edtNgaySinh.setFocusable(false);

        Cursor mCursor = mDataBase.getData("SELECT * FROM listLop");
        while(mCursor.moveToNext()){
            String maLop = mCursor.getString(0);
            listMaLop.add(maLop);
        }

        spnArrAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listMaLop);
        spnArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spnArrAdapter);

        mDataBase.querSQL("CREATE TABLE IF NOT EXISTS sinhvie(IDsv varchar(50) PRIMARY KEY,maLop varchar(10),tenSV nvarchar(30),ngaySinh date)");

        Cursor mCursorSV = mDataBase.getData("SELECT * FROM sinhvie");

        while (mCursorSV.moveToNext()){
            String IDsv = mCursorSV.getString(0);
            String malop = mCursorSV.getString(1);
            String name = mCursorSV.getString(2);
            String NgaySinh = mCursorSV.getString(3);
            listSinhVien.add(new SinhVien(malop,name,NgaySinh,IDsv));
        }

        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listVSV.removeAll(listVSV);
                String maLop = mSpinner.getItemAtPosition(position).toString();

                loadListView(maLop);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        loadListView(mSpinner.getSelectedItem().toString());


        btnAddSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtTenSV.getText().toString().trim();
                String NgaySinh = edtNgaySinh.getText().toString();
                String maLop = mSpinner.getSelectedItem().toString();
                String IDsv = maLop + "_" + name + "_" + NgaySinh;
                AlertDialog.Builder builder = new AlertDialog.Builder(QLSinhVien.this);

                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                Date date = new Date();
                String dateNow = format.format(date);

                if (name.isEmpty()){
                    Toast.makeText(QLSinhVien.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                }else if(NgaySinh.isEmpty()){
                    Toast.makeText(QLSinhVien.this, "Vui lòng nhập ngày", Toast.LENGTH_SHORT).show();
                } else if(!chkDate(NgaySinh,dateNow)){
                    Toast.makeText(QLSinhVien.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                }else if(chkMasv(IDsv)) {
                    Toast.makeText(QLSinhVien.this, "Sinh viên đã tồn tại", Toast.LENGTH_SHORT).show();
                } else{
                    mDataBase.querSQL("INSERT INTO sinhvie VALUES('"+ IDsv +"','" + maLop + "','"+ name +"','" + NgaySinh + "')");
                    listSinhVien.add(new SinhVien(maLop,name,NgaySinh,IDsv));
                    loadListView(maLop);
                }
            }
        });

        lvSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(QLSinhVien.this,"ID : " + listVSV.get(position).getIDsv(), Toast.LENGTH_SHORT).show();
            }
        });

        lvSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QLSinhVien.this);
                builder.setTitle("THÔNG BÁO");
                builder.setMessage("Bạn có muốn xóa sinh viên này ?");
                builder.setNegativeButton("No",null);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String IDsv = listVSV.get(position).getIDsv();
                        mDataBase.querSQL("DELETE FROM sinhvie where IDsv = '" + IDsv + "'");
                        listSinhVien.remove(listVSV.get(position));
                        listVSV.remove(position);
                        loadListView(mSpinner.getSelectedItem().toString());
                        Toast.makeText(QLSinhVien.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return false;
            }
        });

    }

    private void loadListView(String maLop){

        listVSV.removeAll(listVSV);
        for (SinhVien sv : listSinhVien) {
            if (maLop.equals(sv.getMaLop())){
                listVSV.add(sv);
            }
        }
        myAdapterSV = new MyAdapterSV(QLSinhVien.this,listVSV,R.layout.item_sv);
        lvSinhVien.setAdapter(myAdapterSV);
    }

    private void getDate(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edtNgaySinh.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, dateSetListener,2022,06,01);
        datePickerDialog.show();
    }

    private boolean chkDate(String date1,String date2){
        String arrdate1[] = date1.split("-");
        String arrdate2[] = date2.split("-");

        int year1 = Integer.parseInt(arrdate1[2].trim());
        int year2 = Integer.parseInt(arrdate2[2].trim());

        if (year2 - year1 <= 18) {
            return false;
        }
        return true;
    }

    private boolean chkMasv(String masv){
        for (SinhVien sv : listSinhVien) {
            if (masv.equals(sv.getIDsv())){
                return  true;
            }
        }
        return false;
    }


}