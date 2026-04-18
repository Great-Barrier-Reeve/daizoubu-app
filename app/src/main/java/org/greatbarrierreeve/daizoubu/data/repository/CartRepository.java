package org.greatbarrierreeve.daizoubu.data.repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.greatbarrierreeve.daizoubu.data.model.OrderItem;


public class CartRepository {

    private static CartRepository instance;
    private final MutableLiveData<List<OrderItem>> items = new MutableLiveData<>(new ArrayList<>());


    private CartRepository() {}


    // singleton design pattern
    public static synchronized CartRepository getInstance() {

        if (instance == null) instance = new CartRepository();
        return instance;

    }


    public void add(OrderItem item) {

        // create new array list to update observers with set value method
        List<OrderItem> current = new ArrayList<>(items.getValue());
        current.add(item);
        items.setValue(current);

    }


    public void clear() {

        items.setValue(new ArrayList<>());

    }


    // getter for items
    public LiveData<List<OrderItem>> getItems() {

        return items;

    }


    public void remove(OrderItem item) {

        // create new array list to update observers with set value method
        List<OrderItem> current = new ArrayList<>(items.getValue());
        current.remove(item);
        items.setValue(current);

    }


    public BigDecimal getSubtotal() {

        // initialise total to zero
        BigDecimal total = BigDecimal.ZERO;

        // get current list of items
        List<OrderItem> current = items.getValue();
        if (current == null) return total;

        // add price of each item to total
        for (OrderItem item : current) {

            BigDecimal unit = new BigDecimal(item.getPrice());
            total = total.add(unit.multiply(BigDecimal.valueOf(item.getQty())));

        }

        return total;

    }

}