package org.greatbarrierreeve.daizoubu.data.repository;

import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.AcceptRequest;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.model.ErrandStatus;
import org.greatbarrierreeve.daizoubu.data.model.Location;
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;
import org.greatbarrierreeve.daizoubu.data.model.Store;
import org.greatbarrierreeve.daizoubu.data.model.TransitionRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ErrandRepository {
    private ErrandService apiService;

    public ErrandRepository(ErrandService apiService) {
        this.apiService = apiService;
    }

    /**
     * Get a list of available errands.
     * @param callback
     */
    public void getAvailableErrands(Callback<List<Errand>> callback){
        Call<List<Errand>> call = apiService.getAvailableErrands();
        call.enqueue(callback);
    }

    /**
     * Get an errand by its ID.
     * @param id
     * @param callback
     */
    public void getErrandById(String id, Callback<Errand> callback){
        Call<Errand> call = apiService.getErrandById(id);
        call.enqueue(callback);
    }

    /**
     * Get an errand by its runner ID.
     * @param runnerId
     * @param callback
     */
    public void getErrandByRunnerId(String runnerId, Callback<List<Errand>>callback){
        Call<List<Errand>> call = apiService.getErrandByRunnerId(runnerId);
        call.enqueue(callback);
    }

    /**
     * Get an errand by its buyer ID.
     * @param buyerId
     * @param callback
     */
    public void getErrandByBuyerId(String buyerId, Callback<List<Errand>> callback){
        Call<List<Errand>> call = apiService.getErrandByBuyerId(buyerId);
        call.enqueue(callback);
    }


    /**
     * Create a new errand.
     * @param errand
     * @param callback
     */
    public void createErrand(Errand errand, Callback<Errand> callback){
        Call<Errand> call = apiService.createErrand(errand);
        call.enqueue(callback);
    }

    /**
     * Accept an errand.
     * @param id
     * @param request
     * @param callback
     */
    public void acceptErrand(String id, AcceptRequest request, Callback<Errand> callback){
        Call<Errand> call = apiService.acceptErrand(id, request);
        call.enqueue(callback);
    }

    /**
     *
     * Get the status of an errand.
     *
     * @param id
     * @param callback
     */
    public void getErrandStatusById(String id, Callback<String> callback){
        Call<String> call = apiService.getErrandStatusById(id);
        call.enqueue(callback);
    }

    /**
     * Transition the status of an errand.
     *
     * @param id
     * @param request
     * @param callback
     */
    public void transitionErrand(String id, TransitionRequest request, Callback<Errand> callback){
        Call<Errand> call = apiService.transitionErrand(id, request);
        call.enqueue(callback);
    }

    /**
     * Get a list of available stores.
     * @param callback
     */
    public void getAvailableStores(Callback<List<Store>> callback){
        Call<List<Store>> call = apiService.getAvailableStores();
        call.enqueue(callback);
    }

    /**
     * Get a store by its ID.
     * @param id
     * @param callback
     */
    public void getStoreById(String id, Callback <List<Store>> callback){
        Call<List<Store>> call = apiService.getStoreById(id);
        call.enqueue(callback);
    }

    /**
     * Get a list of locations.
     * @param callback
     */
    public void getLocations(Callback<List<Location>> callback){
        Call<List<Location>> call = apiService.getLocations();
        call.enqueue(callback);
    }

    /**
     *
     * @param id
     * @param callback
     */
    public void getMenuByID(String id, Callback<List<MenuItem>> callback) {
        Call<List<MenuItem>> call = apiService.getMenu(id);
        call.enqueue(callback);
    }





}
