package com.ceng319.partsCrib;


public class ItemHandler {
    private String name;
    private int sid;
    private int quantity;

    public ItemHandler(String name, int sid, int quantity){
        this.name=name;
        this.sid=sid;
        this.quantity=quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ItemHandler{" +
                "name='" + name + '\'' +
                ", sid=" + sid +
                ", quantity=" + quantity +
                '}';
    }
}
