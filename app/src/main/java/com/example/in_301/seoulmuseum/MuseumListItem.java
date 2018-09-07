package com.example.in_301.seoulmuseum;

public class MuseumListItem {
    private String no;
    private String name;
    private String kind;
    private String address;
    private String tel;
    private String homepage;
    private String closeday;
    private String gpsX;
    private String gpsY;
    private String desc;

    public String getName() {
        return name;
    }
    public String getKind() {
        return kind;
    }
    public String getAddress() {
        return address;
    }
    public String getTel() {
        return tel;
    }
    public String getHomepage() {
        return homepage;
    }
    public String getCloseday() {
        return closeday;
    }
    public String getGpsX() {
        return gpsX;
    }
    public String getGpsY() {
        return gpsY;
    }
    public String getDesc() {
        return address;
    }
    public String getNo() {
        return no;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
    public void setCloseday(String closeday) {
        this.closeday = closeday;
    }
    public void setGpsX(String gpsX) {
        this.gpsX = gpsX;
    }
    public void setGpsY(String gpsY) {
        this.gpsY = gpsY;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setNo(String no) {
        this.no = no;
    }

    public MuseumListItem(String no, String name, String kind, String address, String tel, String homepage, String closeday) {
        this.no = no;
        this.name = name;
        this.kind = kind;
        this.address = address;
        this.tel = tel;
        this.homepage = homepage;
        this.closeday = closeday;
    }
}
