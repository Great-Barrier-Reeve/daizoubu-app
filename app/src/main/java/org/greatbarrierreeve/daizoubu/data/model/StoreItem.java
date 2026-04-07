package org.greatbarrierreeve.daizoubu.data.model;


import android.os.Parcelable;

public interface StoreItem extends Parcelable {

    String getDesc();

    String getItemId();

    String getName();

    String getPrice();

}
