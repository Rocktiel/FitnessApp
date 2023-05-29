package com.example.fitnessappproject;

public class Calorie {

    String zaman,kalori,vakit;

    public Calorie(String zaman, String kalori, String vakit) {
        this.zaman = zaman;
        this.kalori = kalori;
        this.vakit = vakit;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }

    public String getKalori() {
        return kalori;
    }

    public void setKalori(String kalori) {
        this.kalori = kalori;
    }

    public String getVakit() {
        return vakit;
    }

    public void setVakit(String vakit) {
        this.vakit = vakit;
    }
}
