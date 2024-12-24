package org.jingtao8a.enums;


public enum PageSize {
    SIZE10(10), SIZE15(15), SIZE20(20), SIZE30(30), SIZE40(40), SIZE50(50);
    int size;
    private PageSize(int size) {this.size = size;}
    public int getSize() {return size;}
}
