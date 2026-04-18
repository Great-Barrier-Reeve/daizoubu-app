package org.greatbarrierreeve.daizoubu.ui.order.menu;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.greatbarrierreeve.daizoubu.data.model.AddOnItem;
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;
import org.greatbarrierreeve.daizoubu.data.model.OrderItem;
import org.greatbarrierreeve.daizoubu.data.repository.CartRepository;


public class ItemDetailViewModel extends ViewModel {


    private MenuItem menuItem;
    private final Set<AddOnItem> selectedAddOns = new HashSet<>();
    private final MutableLiveData<String> totalPrice = new MutableLiveData<>();


    public void addToCart() {

        if (menuItem == null) return;

        // build order item from menu item and selected addons
        OrderItem item = new OrderItem.Builder(menuItem, 1)
                .setUserAddOns(getSelectedAddOns())
                .build();
        CartRepository.getInstance().add(item);

    }


    public void calculateTotal() {

        // get base price of menu item
        BigDecimal total = new BigDecimal(menuItem.getPrice());

        // add price of each selected addon
        for (AddOnItem addOn : selectedAddOns) {

            total = total.add(new BigDecimal(addOn.getPrice()));

        }

        // update total price
        totalPrice.setValue(total.setScale(2, RoundingMode.HALF_UP).toPlainString());

    }


    // getter for menu item
    public MenuItem getMenuItem() { return menuItem; }


    // getter for selected addons
    public List<AddOnItem> getSelectedAddOns() {

        List<AddOnItem> out = new ArrayList<>();

        if (menuItem != null) {

            // check if addon is selected through menu item addon list to preserve order
            for (AddOnItem addOn : menuItem.getOptionAddOns()) {

                if (selectedAddOns.contains(addOn)) out.add(addOn);

            }

        }

        return out;

    }


    // getter for total price
    public LiveData<String> getTotalPrice() { return totalPrice; }


    // getter for addon selected state
    public boolean isAddOnSelected(AddOnItem addOn) { return selectedAddOns.contains(addOn); }


    // setter for addon selected state
    public void setAddOnSelected(AddOnItem addOn, boolean isSelected) {

        // update selected addons and store whether change was made
        boolean isChanged = isSelected ? selectedAddOns.add(addOn) : selectedAddOns.remove(addOn);

        // calculate total if change was made
        if (isChanged) calculateTotal();

    }


    // setter for menu item
    public void setMenuItem(MenuItem menuItem) {

        // only allow single instance of menu item
        if (this.menuItem == null) {

            this.menuItem = menuItem;
            calculateTotal();

        }

    }


}
