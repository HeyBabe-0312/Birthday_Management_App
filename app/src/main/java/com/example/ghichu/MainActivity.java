package com.example.ghichu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    GhiChuHelper ghiChuHelper;
    ArrayList<GhiChu> ghiChus;
    GhiChuAdapter ghiChuAdapter;
    ImageView imgAdd;
    TextView date;
    static String getThu[] = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
    static int days[] = new int[100];
    static String  dates[] = new String[100];
    static String  names[] = new String[100];
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.txt_Now);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date.setText("Ngày hiện tại: " +sdf.format(new Date()));
        imgAdd = findViewById(R.id.addView);
        final ListView listView = (ListView) findViewById(R.id.listView);
        ghiChus = new ArrayList<>();
        ghiChuAdapter = new GhiChuAdapter(this,R.layout.noidung_ghichu,ghiChus);
        listView.setAdapter(ghiChuAdapter);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogNew();
            }
        });

        //Create Database
        ghiChuHelper = new GhiChuHelper(this,"Birthday.sql",null,1);
        //Create Table
        ghiChuHelper.queryData("CREATE TABLE IF NOT EXISTS NgaySinh(Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR(50), Date VARCHAR(20))");
        //Insert Data
        //ghiChuHelper.queryData("INSERT INTO NgaySinh VALUES (null,'Ho Minh Hieu','03/12/2001')");
        //ghiChuHelper.queryData("INSERT INTO NgaySinh VALUES (null,'Mom','03/09/1971')");
        //ghiChuHelper.queryData("INSERT INTO Noidung VALUES (null,'Ghi Chu 3')");
        //ghiChuHelper.queryData("INSERT INTO Noidung VALUES (null,'Ghi Chu 4')");

        //Show Data
        showData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showData() {
        int i = 0, j = 0;
        Cursor data = ghiChuHelper.getData("SELECT * FROM NgaySinh");
        ghiChus.clear();
        while (data.moveToNext()) {
            dates[i] = data.getString(2);
            names[i] = data.getString(1);
            //ghiChus.add(new GhiChu(id,name,date));
            i++;
        }
        InsertSort();
        while(names[j]!=null){
            Cursor data1 = ghiChuHelper.getData("SELECT * FROM NgaySinh WHERE Ten = '"+names[j]+"'");
            while (data1.moveToNext()) {
                String date = data1.getString(2);
                String name = data1.getString(1);
                int id = data1.getInt(0);
                ghiChus.add(new GhiChu(id, name, date));
            }
            j++;
        }
        ghiChuAdapter.notifyDataSetChanged();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void InsertSort(){
        int m = 0, i = 1;
        while(dates[m]!=null){
            days[m] = Integer.parseInt(tinhNgay(dates[m]));
            m++;
        }
        while(dates[i]!=null){
            for (int j = i; j > 0; j--) {
                if (days[j] < days [j - 1]) {
                    int temp = days[j];
                    days[j] = days[j - 1];
                    days[j - 1] = temp;
                    String tmp = names[j];
                    names[j] = names[j - 1];
                    names[j - 1] = tmp;
                }
            }
            i++;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String tinhNgay(String Date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String date = "",date1 = "";
        for (int i = 0; i < 6; i++) date += Date.charAt(i);
        date1 = date;
        date += year;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate firstDate = LocalDate.parse(sdf.format(new Date()), formatter);
        LocalDate secondDate = LocalDate.parse(date, formatter);
        long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        if(days<0){
            date1 += ""+(Integer.parseInt(year)+1);
            firstDate = LocalDate.parse(sdf.format(new Date()), formatter);
            secondDate = LocalDate.parse(date1, formatter);
            days = ChronoUnit.DAYS.between(firstDate, secondDate);
        }
        return String.valueOf(days);
    }
    public void thoat(View v){
        androidx.appcompat.app.AlertDialog.Builder e = new androidx.appcompat.app.AlertDialog.Builder(this);
        e.setTitle("Đóng ứng dụng");
        e.setMessage("Chắc chưa đó ???");
        e.setPositiveButton("Yub", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
            }
        });
        e.setNegativeButton("Hmm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        androidx.appcompat.app.AlertDialog a = e.create();
        a.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int tinhTuoi(String Date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate secondDate = LocalDate.parse(sdf.format(new Date()), formatter);
        LocalDate firstDate = LocalDate.parse(Date, formatter);
        long years = ChronoUnit.YEARS.between(firstDate, secondDate);
        return (int)years;
    }
    public static String getthu(String d, int tuoi){
        String c = "";
        for(int i = 0; i < 2; i++){
            c += d.charAt(i);
        }
        int day = Integer.parseInt(c);
        c = "";
        for(int i = 3; i < 5; i++){
            c += d.charAt(i);
        }
        int month = Integer.parseInt(c);
        c = "";
        for(int i = 6; i < 10; i++){
            c += d.charAt(i);
        }
        int year = Integer.parseInt(c);
        year += tuoi + 1;
        int JMD;
        JMD = (day + ((153 * (month + 12 * ((14 - month) / 12) - 3) + 2) / 5) +
                (365 * (year + 4800 - ((14 - month) / 12))) +
                ((year + 4800 - ((14 - month) / 12)) / 4) -
                ((year + 4800 - ((14 - month) / 12)) / 100) +
                ((year + 4800 - ((14 - month) / 12)) / 400)  - 32045) % 7;
        return getThu[JMD];
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void DialogView(String name, String d){
        if(Integer.parseInt(tinhNgay(d))==0) Toast.makeText(MainActivity.this,  "Hôm nay là, sinh nhật của "+name+".", Toast.LENGTH_LONG).show();
        else Toast.makeText(MainActivity.this,name+" ("+tinhTuoi(d)+" tuổi)\nĐếm ngược sinh nhật: "+ tinhNgay(d)+" ngày ("+getthu(d,tinhTuoi(d))+")", Toast.LENGTH_LONG).show();
    }
    public void DialogUpdate(String nd, int i,String d){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_form);
        EditText e = dialog.findViewById(R.id.etxt_edit);
        EditText da = dialog.findViewById(R.id.etxt_date);
        Button ok = dialog.findViewById(R.id.btn_ok);
        Button cancel = dialog.findViewById(R.id.btn_cancel);
        e.setText(nd);
        da.setText(d);
        ok.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String ndmoi = e.getText().toString().trim();
                String dmoi = da.getText().toString().trim();
                if(!TextUtils.isEmpty(ndmoi)&&!TextUtils.isEmpty(dmoi)){
                    if (isDateValid(dmoi)) {
                        Toast.makeText(MainActivity.this,"Sửa thành công !",Toast.LENGTH_SHORT).show();
                        ghiChuHelper.queryData("UPDATE NgaySinh SET Ten = '"+ndmoi+"', Date = '"+dmoi+"' WHERE Id = "+i+"");
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Nhập sai định dạng 'dd/mm/yyyy' !",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Nhập đầy đủ nội dung cần sửa !",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                showData();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void DialogNew(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_form);
        EditText e = dialog.findViewById(R.id.etxt_edit);
        EditText da = dialog.findViewById(R.id.etxt_date);
        Button ok = dialog.findViewById(R.id.btn_ok);
        Button cancel = dialog.findViewById(R.id.btn_cancel);
        TextView title = dialog.findViewById(R.id.txt_Title);
        title.setText("THÊM NỘI DUNG");
        ok.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String ndmoi = e.getText().toString().trim();
                String dmoi = da.getText().toString().trim();
                if(!TextUtils.isEmpty(ndmoi)&&!TextUtils.isEmpty(dmoi)){
                    if (isDateValid(dmoi)) {
                        Toast.makeText(MainActivity.this,"Thêm thành công !",Toast.LENGTH_SHORT).show();
                        ghiChuHelper.queryData("INSERT INTO NgaySinh VALUES(null,'"+ndmoi+"','"+dmoi+"')");
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Nhập sai định dạng 'dd/mm/yyyy' !",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Nhập đầy đủ nội dung cần thêm !",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                showData();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public static boolean isDateValid(String strDate) {
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            format.parse(strDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public void DialogDelete(String nd, final int id){
        AlertDialog.Builder a = new AlertDialog.Builder(this);
        a.setMessage("Bạn có muốn xoá "+nd+" ?");
        a.setTitle("Cảnh Báo !");
        a.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        a.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ghiChuHelper.queryData("DELETE FROM NgaySinh WHERE Id = "+id+"");
                showData();
            }
        });
        a.show();
    }
}