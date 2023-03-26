package com.example.asmandroidcoban;

public class SinhVien {
    private String maLop,tenSV,ngaySinh;
    private String IDsv;

    public SinhVien(String maLop, String tenSV, String ngaySinh, String IDsv) {
        this.maLop = maLop;
        this.tenSV = tenSV;
        this.ngaySinh = ngaySinh;
        this.IDsv = IDsv;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getIDsv() {
        return IDsv;
    }

    public void setIDsv(String IDsv) {
        this.IDsv = IDsv;
    }
}
