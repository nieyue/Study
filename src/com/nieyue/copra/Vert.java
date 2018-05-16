package com.nieyue.copra;

public class Vert implements Comparable<Vert> {
    public int id;
    public int degree;

    public Vert(int var1, int var2) {
        this.id = var1;
        this.degree = var2;
    }

    @Override
    public int compareTo(Vert var1) {
        return this.degree < var1.degree?-1:(this.degree > var1.degree?1:(this.id < var1.id?-1:(this.id > var1.id?1:0)));
    }
}
