package org.greatbarrierreeve.daizoubu.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class MenuItem implements StoreItem {

    String basePrice;
    String desc;
    String itemId;
    String name;
    List<AddOnItem> optionAddOns;


    @Override
    public String toString() {
        return "MenuItem{" +
                "basePrice='" + basePrice + '\'' +
                ", desc='" + desc + '\'' +
                ", itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", optionAddOns=" + optionAddOns +
                '}';
    }

    public MenuItem(Builder builder) {

        this.basePrice = builder.basePrice;
        this.desc = builder.desc;
        this.itemId = builder.itemId;
        this.name = builder.name;
        this.optionAddOns = builder. optionAddOns;

    }


    private MenuItem(Parcel in) {

        basePrice = in.readString();
        desc = in.readString();
        itemId = in.readString();
        name = in.readString();
        optionAddOns = in.createTypedArrayList(AddOnItem.CREATOR);

    }

    public static class Builder{
        private String basePrice;
        private String desc;
        private String itemId;
        private String name;
        private List<AddOnItem> optionAddOns;

        public Builder(String itemId, String name, String desc, String basePrice) {
            this.basePrice = basePrice;
            this.desc = desc;
            this.itemId = itemId;
            this.name = name;
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

    @Override
    public String getDesc() { return desc; }


    @Override
    public String getItemId() { return itemId; }


    @Override
    public String getName() { return name; }


    @Override
    public String getPrice() { return basePrice; }


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
