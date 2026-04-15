package org.greatbarrierreeve.daizoubu.api;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.model.ErrandStatus;
import org.greatbarrierreeve.daizoubu.data.model.Location;
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;
import org.greatbarrierreeve.daizoubu.data.model.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ErrandService {
    @GET("/api/errands/available")
    Call<List<Errand>> getAvailableErrands();

    @GET("/api/errands/{id}")
    Call<Errand> getErrandById(@Path("id") String id);

    @GET("/api/errands/runner/{runnerId}")
    Call<List<Errand>> getErrandByRunnerId(@Path("runnerId") String runnerId);

    @GET("/api/errands/buyer/{buyerId}")
    Call<List<Errand>> getErrandByBuyerId(@Path("buyerId") String buyerId);


    @GET("/api/errands/{id}/status")
    Call<String> getErrandStatusById(@Path("id") String id);

    @POST("/api/errands")
    Call<Errand> createErrand(@Body Errand errand);

    @POST("/api/errands/{id}/accept")
    Call<Errand> acceptErrand(@Path("id") String id, @Body String runnerId);

    @POST("/api/errands/{id}/transition")
    Call<Errand> transitionErrand(@Path("id") String id, @Body ErrandStatus status);

    @GET("/api/errands/stores/available")
    Call<List<Store>> getAvailableStores();

    @GET("/api/errands/stores/{id}")
    Call<List<Store>> getStoreById(@Path("id") String id);

    @GET("/api/errands/locations")
    Call<List<Location>> getLocations();

    @GET("/api/errands/menu/{id}")
    Call<List<MenuItem>> getMenu(@Path("id") String id);

}
