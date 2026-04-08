package org.greatbarrierreeve.daizoubu.repository;

import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.model.ErrandStatus;

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
     * @param runnerId
     * @param callback
     */
    public void acceptErrand(String id, String runnerId, Callback<Errand> callback){
        Call<Errand> call = apiService.acceptErrand(id, runnerId);
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
     * @param status
     * @param callback
     */
    public void transitionErrand(String id, ErrandStatus status, Callback<Errand> callback){
        Call<Errand> call = apiService.transitionErrand(id, status);
        call.enqueue(callback);
    }


}
