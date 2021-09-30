package com.example.ghichu;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GhiChuAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<GhiChu> ListGhiChu;

    public GhiChuAdapter(MainActivity context, int layout, List<GhiChu> listGhiChu) {
        this.context = context;
        this.layout = layout;
        ListGhiChu = listGhiChu;
    }

    @Override
    public int getCount() {
        return ListGhiChu.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    static class ViewHolder{
        TextView txt,txtDate;
        ImageView imgEdit,imgDelete;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.txtDate = (TextView) convertView.findViewById(R.id.txt_Date);
            holder.txt = (TextView) convertView.findViewById(R.id.textView);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.img_edit);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.img_delete);
            convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();
        GhiChu ghiChu = ListGhiChu.get(position);
        holder.txt.setText(ghiChu.getNoidung());
        holder.txtDate.setText(ghiChu.getDate());
        holder.txtDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                context.DialogView(ghiChu.getNoidung(),ghiChu.getDate());
            }
        });
        holder.txt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                context.DialogView(ghiChu.getNoidung(),ghiChu.getDate());
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               context.DialogUpdate(ghiChu.getNoidung(),ghiChu.getId(),ghiChu.getDate());
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogDelete(ghiChu.getNoidung(),ghiChu.getId());
            }
        });
        return convertView;
    }

}
