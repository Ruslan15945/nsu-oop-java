package ru.nsu.ccfit.morozov.companyemulator;

public class Road implements Comparable<Road>{

    private int length;

    public Road(int length){
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    @Override
    public int compareTo(Road o) {
        return Integer.compare(this.getLength(), o.getLength());
    }
}
