package com.xxh.start1.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevison;
    private  boolean showfirstpage;
    private  boolean shownext;
    private  boolean showend;
    private Integer page;
    private  Integer totalpage;
    private  List<Integer> pages=new ArrayList<>();



    public boolean isShowPrevison() {
        return showPrevison;
    }

    public void setShowPrevison(boolean showPrevison) {
        this.showPrevison = showPrevison;
    }

    public boolean isShowfirstpage() {
        return showfirstpage;
    }

    public void setShowfirstpage(boolean showfirstpage) {
        this.showfirstpage = showfirstpage;
    }

    public boolean isShownext() {
        return shownext;
    }

    public void setShownext(boolean shownext) {
        this.shownext = shownext;
    }

    public boolean isShowend() {
        return showend;
    }

    public void setShowend(boolean showend) {
        this.showend = showend;
    }

    public Integer getPag() {
        return page;
    }

    public void setPag(Integer pag) {
        this.page = pag;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public void setPagination(Integer totalpage, Integer page) {
        this.totalpage=totalpage;
        this.page=page;
        pages.add(page);
        for(int i=1;i<3;i++)
        {
            if(page-i>0) {
                pages.add(0 ,page-i);
            }
            if(page+i<=totalpage){
                pages.add(page+i);
            }
        }

        if(page==1){
            showPrevison=false;
        }else {
            showPrevison=true;
        }
        if(page==totalpage){
            shownext=false;
        }else{
            shownext=true;
        }
        //
        if(pages.contains(1)){
            showfirstpage=false;
        }
        else{
            showfirstpage=true;
        }
        if(pages.contains(totalpage)){
            showend=false;
        }
        else {
            showend=true;
        }
    }
}
