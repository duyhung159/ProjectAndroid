package com.example.asmandroidcoban;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAddapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<LopHoc> listLopHoc;
    private int itemLayout;

    public MyAddapter(Context mContext, ArrayList<LopHoc> listLopHoc, int itemLayout) {
        this.mContext = mContext;
        this.listLopHoc = listLopHoc;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        return listLopHoc.size();
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

            TextView tvSTT = convertView.findViewById(R.id.tvSTT);
            TextView tvMaLop = convertView.findViewById(R.id.tvMaLop);
            TextView tvTenLop = convertView.findViewById(R.id.tvTenLop);

            tvSTT.setText( "" + (position + 1));
            tvMaLop.setText(listLopHoc.get(position).getMaLop());
            tvTenLop.setText(listLopHoc.get(position).getTenLop());
        }
        return convertView;
    }
}
