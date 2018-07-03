package com.example.malick.apk.UI;

/**
 * Created by Malick on 3/27/2018.
 */

public class Spbu {
    String kode_spbu,alamat,jenis,nama_spbu,jarak,lat,lng,gambar;


    public Spbu() {
    }

    public Spbu(String kode_spbu, String alamat, String jenis, String nama_spbu, String jarak, String lat, String lng, String gambar) {
        this.kode_spbu = kode_spbu;
        this.alamat = alamat;
        this.jenis = jenis;
        this.nama_spbu = nama_spbu;
        this.jarak = jarak;
        this.lat = lat;
        this.lng = lng;
        this.gambar = gambar;
    }


    public String getKode_spbu() {
        return kode_spbu;
    }

    public void setKode_spbu(String kode_spbu) {
        this.kode_spbu = kode_spbu;
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

    public String getNama_spbu() {
        return nama_spbu;
    }

    public void setNama_spbu(String nama_spbu) {
        this.nama_spbu = nama_spbu;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
