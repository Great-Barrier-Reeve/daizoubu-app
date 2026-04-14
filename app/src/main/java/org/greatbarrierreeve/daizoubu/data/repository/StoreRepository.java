package org.greatbarrierreeve.daizoubu.data.repository;


import java.util.List;

import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.Store;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;

import retrofit2.Callback;


public class StoreRepository {

    private final ErrandService errandService;


    public StoreRepository() {

        this.errandService = RetrofitClient.getErrandService();

    }


    public void getAvailableStores(Callback<List<Store>> callback) {

        errandService.getAvailableStores().enqueue(callback);

    }

}