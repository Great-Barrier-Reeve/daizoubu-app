package org.greatbarrierreeve.daizoubu.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


//Getter and Setter methods needed for jackson json serialisation

public class MenuItem implements StoreItem {

    String basePrice;
    String desc;
    String itemId;
    String name;
    List<AddOnItem> optionAddOns;

    String storeId;


    public MenuItem(Builder builder) {

        this.basePrice = builder.basePrice;
        this.desc = builder.desc;
        this.itemId = builder.itemId;
        this.name = builder.name;
        this.optionAddOns = builder. optionAddOns;
        this.storeId = builder.storeId;

    }


    private MenuItem(Parcel in) {

        basePrice = in.readString();
        desc = in.readString();
        itemId = in.readString();
        name = in.readString();
        optionAddOns = in.createTypedArrayList(AddOnItem.CREATOR);

    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "basePrice='" + basePrice + '\'' +
                ", desc='" + desc + '\'' +
                ", itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", optionAddOns=" + optionAddOns +
                ", storeId='" + storeId + '\'' +
                '}';
    }

    public static class Builder{
        private String basePrice;
        private String desc;
        private String itemId;
        private String name;
        private List<AddOnItem> optionAddOns;

        private String storeId;


        public Builder(String itemId, String name, String desc, String basePrice, String storeId) {
            this.basePrice = basePrice;
            this.desc = desc;
            this.itemId = itemId;
            this.name = name;
            this.storeId = storeId;
        }

        public Builder setOptionAddOns(List<AddOnItem> optionAddOns) {
            this.optionAddOns = optionAddOns;
            return this;
        }

        public MenuItem build(){
            return new MenuItem(this);
        }

    }


    @Override
    public int describeContents() { return 0; }


    public List<AddOnItem> getOptionAddOns() { return optionAddOns; }


    @Override
    public String getDesc() { return desc; }


    @Override
    public String getItemId() { return itemId; }


    @Override
    public String getName() { return name; }


    @Override
    public String getPrice() { return basePrice; }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setOptionAddOns(List<AddOnItem> optionAddOns) {
        this.optionAddOns = optionAddOns;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(basePrice);
        out.writeString(desc);
        out.writeString(itemId);
        out.writeString(name);
        out.writeTypedList(optionAddOns);

    }


    public static final Parcelable.Creator<MenuItem> CREATOR
            = new Parcelable.Creator<>() {

        @Override
        public MenuItem createFromParcel(Parcel in) {

            return new MenuItem(in);

        }


        @Override
        public MenuItem[] newArray(int size) {

            return new MenuItem[size];

        }

    };

}
