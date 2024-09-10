package com.example.pavani.movieapp2;

/**
 * Created by pavani on 17/5/18.
 */

public class AssessPojo {

    String mauthor,mcount,mid,murl_url,mtpages,mtresults;

    public AssessPojo(String auth, String cont, String id, String url_url) {

        this.mauthor=auth;
        this.mcount=cont;
        this.mid=id;
        this.murl_url=url_url;
        /*this.mtpages=tpages;
        this.mtresults=tresults;*/
    }

    public String getMauthor() {
        return mauthor;
    }

    public void setMauthor(String mauthor) {
        this.mauthor = mauthor;
    }

    public String getMcount() {
        return mcount;
    }

    public void setMcount(String mcount) {
        this.mcount = mcount;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMurl_url() {
        return murl_url;
    }

    public void setMurl_url(String murl_url) {
        this.murl_url = murl_url;
    }

    public String getMtpages() {
        return mtpages;
    }

    public void setMtpages(String mtpages) {
        this.mtpages = mtpages;
    }

    public String getMtresults() {
        return mtresults;
    }

    public void setMtresults(String mtresults) {
        this.mtresults = mtresults;
    }


}
