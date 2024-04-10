package com.nguyenphucduongluan.models;

import java.io.Serializable;

public class Films implements Serializable {
    int filmsCode;
    String filmsName;
    double filmsPrice;

    public int getFilmsCode() {
        return filmsCode;
    }

    public void setFilmsCode(int filmsCode) {
        this.filmsCode = filmsCode;
    }

    public String getFilmsName() {
        return filmsName;
    }

    public void setFilmsName(String filmsName) {
        this.filmsName = filmsName;
    }

    public double getFilmsPrice() {
        return filmsPrice;
    }

    public void setFilmsPrice(double filmsPrice) {
        this.filmsPrice = filmsPrice;
    }

    public Films(int filmsCode, String filmsName, double filmsPrice) {
        this.filmsCode = filmsCode;
        this.filmsName = filmsName;
        this.filmsPrice = filmsPrice;
    }
}
