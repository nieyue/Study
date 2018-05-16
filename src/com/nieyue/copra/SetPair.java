package com.nieyue.copra;

import java.util.TreeSet;

public class SetPair implements Comparable<SetPair> {
    public int id;
    public TreeSet<Integer> set;

    public SetPair(int var1, TreeSet<Integer> var2) {
        this.id = var1;
        this.set = var2;
    }

    public int compareTo(SetPair var1) {
        return this.set.size() < var1.set.size()?-1:(this.set.size() > var1.set.size()?1:(this.id < var1.id?-1:(this.id > var1.id?1:0)));
    }
}
