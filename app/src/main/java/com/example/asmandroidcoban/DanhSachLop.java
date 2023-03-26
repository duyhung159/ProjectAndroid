package com.example.asmandroidcoban;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DanhSachLop extends AppCompatActivity {
    private MyAddapter myAddapter;
    private ListView lvDSLop;
    private ArrayList<LopHoc> listLop = new ArrayList<>();
    private DataBase mDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_lop);

        lvDSLop = findViewById(R.id.lvDanhSachLop);
        mDataBase = new DataBase(getApplicationContext());

        Cursor mCursor = mDataBase.getData("select * from listLop");
        while (mCursor.moveToNext()){
            String maLop = mCursor.getString(0);
            String tenLop = mCursor.getString(1);
            listLop.add(new LopHoc(maLop,tenLop));
        }
        //        addData();

        myAddapter = new MyAddapter(this,listLop,R.layout.item_lop);
        lvDSLop.setAdapter(myAddapter);

        lvDSLop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DanhSachLop.this, listLop.get(position).getTenLop(), Toast.LENGTH_SHORT).show();

            }
        });

        lvDSLop.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachLop.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa lớp " + listLop.get(position).getTenLop() + " ?");

                builder.setNegativeButton("No",null);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String maLop = listLop.get(position).getMaLop();
                        mDataBase.querSQL("DELETE FROM listLop WHERE maLop ='"+ maLop +"'");

                        listLop.remove(position);
                        myAddapter = new MyAddapter(getApplicationContext(),listLop,R.layout.item_lop);
                        lvDSLop.setAdapter(myAddapter);
                        mDataBase.querSQL("DELETE FROM sinhvie WHERE maLop ='" + maLop + "'");
                    }
                });
                builder.show();

                return false;
            }
        });
    }

    private void addData(){
        listLop.add(new LopHoc("ma001","duy"));
        listLop.add(new LopHoc("ma002","duy2"));
        listLop.add(new LopHoc("ma003","duy3"));
    }
}