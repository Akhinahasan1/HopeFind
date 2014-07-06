package com.tugasbesar.hopefind.model;

import java.util.Date;

/**
 * Created by Akhinahasan on 06/07/2014.
 */
public class EntitasContent {
    private int idcontent;
    private int idmember;
    private String title;
    private String type;
    private String lokasi;
    private String kategori;
    private String detail;
    private Date dibuat;

    public EntitasContent(){
        title="";
        type="";
        lokasi="";
        kategori="";
        detail="";
        dibuat=null;
    }

    public Date getDibuat() {
        return dibuat;
    }

    public void setDibuat(Date dibuat) {
        this.dibuat = dibuat;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdcontent() {
        return idcontent;
    }

    public void setIdcontent(int idcontent) {
        this.idcontent = idcontent;
    }

    public int getIdmember() {
        return idmember;
    }

    public void setIdmember(int idmember) {
        this.idmember = idmember;
    }
}
