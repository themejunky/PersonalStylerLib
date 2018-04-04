package com.themejunky.personalstylerlib.bases.model;

public class Item {
    private String id;
    private String value;

    public Item(String nValue) {
        value = nValue;
    }

    public Item(String nId,String nValue) {
        id = nId;
        value = nValue;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Item){
            Item c = (Item )obj;
            if(c.getValue().equals(value) && c.getId()==id ) return true;
        }

        return false;
    }
}
