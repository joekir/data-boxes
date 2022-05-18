package com.joek.databoxes;

import manifold.ext.rt.api;

// https://javadoc.io/static/systems.manifold/manifold-ext-rt/2020.1.51/manifold/ext/rt/api/Structural.html
@Structural
public class DataBox {
    private int num;

    public DataBox plus(int that) {
        return new DataBox(this.getNum() + that);
    } 

    public DataBox(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
