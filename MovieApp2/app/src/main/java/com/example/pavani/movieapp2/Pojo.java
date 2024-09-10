package com.example.pavani.movieapp2;

/**
 * Created by pavani on 15/5/18.
 */

public class Pojo {

    String mTitle,mPoster,mlang,mtitle,back,oview,mReldate;
    boolean adlt,vid;
    double vcount,idd,vavg,popul;

    public Pojo(String name, double vcount, double idd, boolean vid, double vavg, double pop, String lang, String otit, String reldate, String back, String image, boolean adlt, String oview) {

        this.mTitle = name;
        this.mPoster = image;
        this.mlang = lang;
        this.mtitle = otit;
        this.back = back;
        this.oview = oview;
        this.adlt = adlt;
        this.vid = vid;
        this.vcount = vcount;
        this.idd = idd;
        this.vavg = vavg;
        this.popul = pop;
        this.mReldate=reldate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPoster() {
        return mPoster;
    }

    public void setmPoster(String mPoster) {
        this.mPoster = mPoster;
    }

    public String getMlang() {
        return mlang;
    }

    public void setMlang(String mlang) {
        this.mlang = mlang;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getOview() {
        return oview;
    }

    public void setOview(String oview) {
        this.oview = oview;
    }

    public String getmReldate() {
        return mReldate;
    }

    public void setmReldate(String mReldate) {
        this.mReldate = mReldate;
    }

    public boolean isAdlt() {
        return adlt;
    }

    public void setAdlt(boolean adlt) {
        this.adlt = adlt;
    }

    public boolean isVid() {
        return vid;
    }

    public void setVid(boolean vid) {
        this.vid = vid;
    }

    public double getVcount() {
        return vcount;
    }

    public void setVcount(double vcount) {
        this.vcount = vcount;
    }

    public double getIdd() {
        return idd;
    }

    public void setIdd(double idd) {
        this.idd = idd;
    }

    public double getVavg() {
        return vavg;
    }

    public void setVavg(double vavg) {
        this.vavg = vavg;
    }

    public double getPopul() {
        return popul;
    }

    public void setPopul(double popul) {
        this.popul = popul;
    }


}
