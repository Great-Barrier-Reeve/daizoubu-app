package org.greatbarrierreeve.daizoubu.ui.bounties;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.repository.ErrandRepository;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;

import java.time.chrono.Era;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BountiesViewModel extends ViewModel {
    private MutableLiveData<List<Errand>> errands;
    private MutableLiveData<List<Errand>> buyerErrands;
    private MutableLiveData<List<Errand>> runnerErrands;
    private ErrandService errandService = RetrofitClient.getErrandService();
    private ErrandRepository errandRepository = new ErrandRepository(errandService);

    public LiveData<List<Errand>> getErrands(){
        if(errands == null){
            errands = new MutableLiveData<List<Errand>>();
            loadErrands();
        }
        return errands;
    }

    public LiveData<List<Errand>> getBuyerErrands(String id) {
        if (buyerErrands == null) {
            buyerErrands = new MutableLiveData<List<Errand>>();
            loadBuyerErrands(id);
        }
        return buyerErrands;
    }

    public LiveData<List<Errand>> getSellerErrands(String id){
        if(runnerErrands== null){
            runnerErrands = new MutableLiveData<List<Errand>>();
            loadSellerErrands(id);
        }
        return runnerErrands;
        }

    private void loadSellerErrands(String id){
        errandRepository.getErrandByRunnerId(id, new Callback<List<Errand>>() {


            @Override
            public void onResponse(Call<List<Errand>> call, Response<List<Errand>> response) {
                runnerErrands.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Errand>> call, Throwable throwable) {
                Log.e("Network_Error", throwable.getMessage());
            }
        });
    }

    private void loadErrands(){
        // do an asynchronous operation to fetch errands)
        errandRepository.getAvailableErrands(new Callback<List<Errand>>() {

            @Override
            public void onResponse(Call<List<Errand>> call, Response<List<Errand>> response) {
                errands.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Errand>> call, Throwable throwable) {
                Log.e("Network_Error", throwable.getMessage());
            }
        });
    }

    private void loadBuyerErrands(String id) {
        errandRepository.getErrandByBuyerId(id, new Callback<List<Errand>>() {
            @Override
            public void onResponse(Call<List<Errand>> call, Response<List<Errand>> response) {
                buyerErrands.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Errand>> call, Throwable throwable) {
                Log.e("Network_Error", throwable.getMessage());
            }
        });
    }


}
