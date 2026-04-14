package org.greatbarrierreeve.daizoubu.ui.order.location;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.Location;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.data.repository.ErrandRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationViewModel extends ViewModel {
    private MutableLiveData<List<Location>> locations;
    private ErrandService errandService = RetrofitClient.getErrandService();
    private ErrandRepository errandRepository = new ErrandRepository(errandService);


    public LiveData<List<Location>> getLocations(){
        if (locations == null){
            locations = new MutableLiveData<List<Location>>();
            loadLocation();
        }
        return locations;

    }

    private void loadLocation(){
        // do an asynchronous operation to fetch locations
        errandRepository.getLocations(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                locations.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable throwable) {
                Log.e("Network_Error", throwable.getMessage());
            }
        });

    }
}
