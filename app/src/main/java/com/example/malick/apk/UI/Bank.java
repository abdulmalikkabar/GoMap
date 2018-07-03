package com.example.malick.apk.UI;

/**
 * Created by Malick on 3/19/2018.
 */

public class Bank {
String nama,cabang,kode,alamat,telp,web,gambar,lat,ing,jarak;

    public Bank() {
    }


    public Bank(String nama, String cabang, String kode, String alamat, String telp, String web, String gambar, String lat, String ing, String jarak) {
        this.nama = nama;
        this.cabang = cabang;
        this.kode = kode;
        this.alamat = alamat;
        this.telp = telp;
        this.web = web;
        this.gambar = gambar;
        this.lat = lat;
        this.ing = ing;
        this.jarak = jarak;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
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

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }
}
