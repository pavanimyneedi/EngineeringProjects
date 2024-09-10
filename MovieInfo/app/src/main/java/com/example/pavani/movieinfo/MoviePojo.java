package com.example.pavani.movieinfo;

/**
 * Created by pavani on 26/5/18.
 */

class MoviePojo {

    String mov_name, poster_path, orig_lang, oview, o_tit, back_drop, rel_date;
    double vcount, mov_id, v_avg, popular;
    boolean video, adult;

    public MoviePojo(String mov_name, double vcount, double mov_id, boolean video, double v_avg, double popular, String orig_lang, String o_tit, String rel_date, String back_drop, String poster_path, boolean adult, String oview) {

        this.adult=adult;
        this.mov_id=mov_id;
        this.back_drop=back_drop;
        this.mov_name=mov_name;
        this.vcount=vcount;
        this.v_avg=v_avg;
        this.video=video;
        this.popular=popular;
        this.orig_lang=orig_lang;
        this.rel_date=rel_date;
        this.o_tit=o_tit;
        this.poster_path=poster_path;
        this.oview=oview;
    }

    public MoviePojo(double idd, double vavg, String otit, String reldate, String back, String image, String oview) {

        this.mov_id=idd;
        this.v_avg=vavg;
        this.o_tit=otit;
        this.rel_date=reldate;
        this.back_drop=back;
        this.poster_path=image;
        this.oview=oview;
    }


    public String getMov_name() {
        return mov_name;
    }

    public void setMov_name(String mov_name) {
        this.mov_name = mov_name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOrig_lang() {
        return orig_lang;
    }

    public void setOrig_lang(String orig_lang) {
        this.orig_lang = orig_lang;
    }

    public String getOview() {
        return oview;
    }

    public void setOview(String oview) {
        this.oview = oview;
    }

    public String getO_tit() {
        return o_tit;
    }

    public void setO_tit(String o_tit) {
        this.o_tit = o_tit;
    }

    public String getBack_drop() {
        return back_drop;
    }

    public void setBack_drop(String back_drop) {
        this.back_drop = back_drop;
    }

    public String getRel_date() {
        return rel_date;
    }

    public void setRel_date(String rel_date) {
        this.rel_date = rel_date;
    }

    public double getVcount() {
        return vcount;
    }

    public void setVcount(double vcount) {
        this.vcount = vcount;
    }

    public double getMov_id() {
        return mov_id;
    }

    public void setMov_id(double mov_id) {
        this.mov_id = mov_id;
    }

    public double getV_avg() {
        return v_avg;
    }

    public void setV_avg(double v_avg) {
        this.v_avg = v_avg;
    }

    public double getPopular() {
        return popular;
    }

    public void setPopular(double popular) {
        this.popular = popular;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }



    String mkey,mtype;

    public MoviePojo(String key, String type) {
        this.mkey=key;
        this.mtype=type;

    }

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }





    String mauthor,mcount,mid,murl_url;

    public MoviePojo(String auth, String cont, String id, String url_url) {

        this.mauthor=auth;
        this.mcount=cont;
        this.mid=id;
        this.murl_url=url_url;
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
}
