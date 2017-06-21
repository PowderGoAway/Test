package com.example.meow.test;


public class Rate {
    int maxRate, minRate;

    public Rate(){
        this.minRate=0;
        this.maxRate=0;
    }

    public void setMinRate(Integer minRate){
        this.minRate=minRate;
    }
    public Integer getMinRate() {
        return this.minRate;
    }
    public void setMaxRate(Integer maxRate) {
        this.maxRate=maxRate;
    }
    public Integer getMaxRate() {
        return this.maxRate;
    }
}
