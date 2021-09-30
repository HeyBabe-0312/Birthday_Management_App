package com.example.ghichu;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GhiChu {
    private int id;
    private String ten;
    private String date;
    public GhiChu(int id, String Ten, String Date) {
        this.id = id;
        this.ten = Ten;
        this.date = Date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoidung() {
        return ten;
    }

    public void setNoidung(String noidung) {
        this.ten = noidung;
    }
}
