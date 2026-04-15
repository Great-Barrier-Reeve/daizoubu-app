package org.greatbarrierreeve.daizoubu.ui.order.menu;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import org.greatbarrierreeve.daizoubu.data.model.MenuItem;
import org.greatbarrierreeve.daizoubu.data.repository.ErrandRepository;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuViewModel extends ViewModel {

    private final ErrandRepository errandRepository;
    private final MutableLiveData<List<MenuItem>> menuLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();


    public MenuViewModel() {

        errandRepository = new ErrandRepository(RetrofitClient.getErrandService());

    }


    public LiveData<List<MenuItem>> getMenu() { return menuLiveData; }


    public LiveData<String> getError() { return errorLiveData; }


    public void fetchMenu(String storeId) {

        errandRepository.getMenuByID(storeId, new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<List<MenuItem>> call, @NonNull Response<List<MenuItem>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    menuLiveData.postValue(response.body());

                } else {

                    errorLiveData.postValue("Failed to load menu: " + response.code());

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<MenuItem>> call, @NonNull Throwable t) {

                errorLiveData.postValue("Connection failed");

            }

        });

    }

}