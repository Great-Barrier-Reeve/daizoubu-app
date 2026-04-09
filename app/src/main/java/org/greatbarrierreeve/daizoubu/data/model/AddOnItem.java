package org.greatbarrierreeve.daizoubu.data.model;


import android.os.Parcel;
import android.os.Parcelable;


public class AddOnItem implements StoreItem {

    String basePrice;
    String desc;
    String itemId;
    String name;


    public AddOnItem(String itemId, String name, String desc, String basePrice) {

        this.basePrice = basePrice;
        this.desc = desc;
        this.itemId = itemId;
        this.name = name;

    }


    private AddOnItem(Parcel in) {

        basePrice = in.readString();
        desc = in.readString();
        itemId = in.readString();
        name = in.readString();

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

    }


    public static final Parcelable.Creator<AddOnItem> CREATOR
            = new Parcelable.Creator<>() {

        public AddOnItem createFromParcel(Parcel in) {

            return new AddOnItem(in);

        }


        public AddOnItem[] newArray(int size) {

            return new AddOnItem[size];

        }

    };

}