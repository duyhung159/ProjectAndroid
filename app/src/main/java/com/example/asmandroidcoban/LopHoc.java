package com.example.asmandroidcoban;

public class LopHoc {
    private String MaLop,TenLop;

    public LopHoc(String maLop, String tenLop) {
        this.MaLop = maLop;
        this.TenLop = tenLop;
    }

    public String getMaLop() {
        return MaLop;
    }

    public void setMaLop(String maLop) {
        MaLop = maLop;
    }

    public String getTenLop() {
        return TenLop;
    }

    public void setTenLop(String tenLop) {
        TenLop = tenLop;
    }
}
