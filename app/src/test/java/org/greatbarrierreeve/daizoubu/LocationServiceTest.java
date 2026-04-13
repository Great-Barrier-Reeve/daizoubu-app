package org.greatbarrierreeve.daizoubu;

import static org.junit.Assert.assertTrue;

import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.Location;
import org.greatbarrierreeve.daizoubu.data.model.Store;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import retrofit2.Response;

public class LocationServiceTest {

    private ErrandService locationService;

    @Before
    public void setUp(){
        locationService = RetrofitClient.getService();
    }

    @Test
    public void testGetAllStores() throws Exception{
        Response<List<Location>> response = locationService.getLocations().execute();
        assertTrue("Response failed " + response.code(), response.isSuccessful());
        List<Location> locations = response.body();
        if (locations != null && !locations.isEmpty()){
            for(Location location : locations){
                System.out.println(locations.toString());
            }
        }
    }
}
