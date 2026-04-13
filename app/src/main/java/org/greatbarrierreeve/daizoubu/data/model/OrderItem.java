package org.greatbarrierreeve.daizoubu.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;


public class OrderItem implements UserItem {

    MenuItem menuItem;
    int qty;

    String specialReq;
    List<AddOnItem> userAddOns;


    private OrderItem(Parcel in) {

        menuItem = in.readParcelable(MenuItem.class.getClassLoader());
        qty = in.readInt();
        specialReq = in.readString();
        userAddOns = in.createTypedArrayList(AddOnItem.CREATOR);

    }

    public OrderItem(Builder builder){
        this.menuItem = builder.menuItem;
        this.qty = builder.qty;
        this.specialReq = builder.specialReq;
        this.userAddOns = builder.userAddOns;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSpecialReq() {
        return specialReq;
    }

    public void setSpecialReq(String specialReq) {
        this.specialReq = specialReq;
    }

    public List<AddOnItem> getUserAddOns() {
        return userAddOns;
    }

    public void setUserAddOns(List<AddOnItem> userAddOns) {
        this.userAddOns = userAddOns;
    }


    @Override
    public int describeContents() { return 0; }


    @Override
    public String getDesc() { return menuItem.getDesc(); }


    @Override
    public String getItemId() { return menuItem.getItemId(); }


    @Override
    public String getName() { return menuItem.getName(); }


    @Override
    public String getPrice() {

        // loop through each addon and add to price
        BigDecimal price = new BigDecimal(menuItem.getPrice());
        for (AddOnItem addOnItem : userAddOns) {

            price = price.add(new BigDecimal(addOnItem.getPrice()));

        }

        return price.toPlainString();

    }


    @Override
    public int getQty() { return qty; }


    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeParcelable(menuItem, flags);
        out.writeInt(qty);
        out.writeString(specialReq);
        out.writeTypedList(userAddOns);

    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "menuItem=" + menuItem +
                ", qty=" + qty +
                ", specialReq='" + specialReq + '\'' +
                ", userAddOns=" + userAddOns +
                '}';
    }


    // implement builder design pattern
    public static class Builder {

        private MenuItem menuItem;
        private int qty;
        private String specialReq;
        private List<AddOnItem> userAddOns;


        public Builder(MenuItem menuitem, int qty) {
            this.menuItem = menuitem;
            this.qty = qty;
            // TODO: Build Builder constructor and static setter methods
        }
        public Builder setSpecialReq(String specialReq) {
            this.specialReq = specialReq;
            return this;
        }

        public Builder setUserAddOns(List<AddOnItem> userAddOns) {
            this.userAddOns = userAddOns;
            return this;
        }

        public OrderItem build(){
            return new OrderItem(this);
        }
    }


    public static final Parcelable.Creator<OrderItem> CREATOR
            = new Parcelable.Creator<>() {

        public OrderItem createFromParcel(Parcel in) {

            return new OrderItem(in);

        }


        public OrderItem[] newArray(int size) {

            return new OrderItem[size];

        }

    };

}