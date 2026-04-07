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


    public MenuItem(String itemId, String name, String desc, String basePrice, List<AddOnItem> optionAddOns) {

        this.basePrice = basePrice;
        this.desc = desc;
        this.itemId = itemId;
        this.name = name;
        this.optionAddOns = optionAddOns;

    }


    private MenuItem(Parcel in) {

        basePrice = in.readString();
        desc = in.readString();
        itemId = in.readString();
        name = in.readString();
        optionAddOns = in.createTypedArrayList(AddOnItem.CREATOR);

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
