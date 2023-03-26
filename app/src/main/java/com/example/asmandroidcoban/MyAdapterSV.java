package com.example.asmandroidcoban;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapterSV extends BaseAdapter {

    private Context mContext;
    private ArrayList<SinhVien> listSV = new ArrayList<>();
    private int itemLayout;

    public MyAdapterSV(Context mContext, ArrayList<SinhVien> listSV, int itemLayout) {
        this.mContext = mContext;
        this.listSV = listSV;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        return listSV.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = mInflater.inflate(itemLayout,null);

            TextView STT = convertView.findViewById(R.id.tvSTT2);
            TextView tvName = convertView.findViewById(R.id.tvTenSV);
            TextView tvNgaySinh = convertView.findViewById(R.id.tvNgaySinh);

            STT.setText(""+(position + 1));
            tvName.setText(listSV.get(position).getTenSV());
            tvNgaySinh.setText(listSV.get(position).getNgaySinh());
        }
        return convertView;
    }
}
