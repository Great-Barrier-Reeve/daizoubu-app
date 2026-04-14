package org.greatbarrierreeve.daizoubu.ui.order;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import org.greatbarrierreeve.daizoubu.data.model.Store;
import org.greatbarrierreeve.daizoubu.data.repository.StoreRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderViewModel extends ViewModel {

    private final MutableLiveData<List<Store>> storesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final StoreRepository storeRepository;


    public OrderViewModel() {

        storeRepository = new StoreRepository();
        fetchStores();

    }


    public LiveData<List<Store>> getStores() {

        return storesLiveData;

    }


    public LiveData<String> getError() {

        return errorLiveData;

    }


    public void fetchStores() {

        storeRepository.getAvailableStores(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<List<Store>> call, @NonNull Response<List<Store>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    storesLiveData.postValue(response.body());

                } else {

                    errorLiveData.postValue("Failed to load stores: " + response.code());

                }

            }


            @Override
            public void onFailure(@NonNull Call<List<Store>> call, @NonNull Throwable t) {

                errorLiveData.postValue("Connection failed");

            }

        });

    }

}