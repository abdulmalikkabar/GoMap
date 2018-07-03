package com.example.malick.apk.UI;

/**
 * Created by Malick on 3/16/2018.
 */

public class Atm {

    String nama,alamat,jenis,pecahan,lat,ing,gambar,jarak;

    public Atm() {
    }

    public Atm(String nama, String alamat, String jenis, String pecahan, String lat, String ing, String gambar, String jarak) {
        this.nama = nama;
        this.alamat = alamat;
        this.jenis = jenis;
        this.pecahan = pecahan;
        this.lat = lat;
        this.ing = ing;
        this.gambar = gambar;
        this.jarak = jarak;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getPecahan() {
        return pecahan;
    }

    public void setPecahan(String pecahan) {
        this.pecahan = pecahan;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }
}
