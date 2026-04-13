package org.greatbarrierreeve.daizoubu;

import static org.junit.Assert.assertTrue;

import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.Store;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import retrofit2.Response;

public class StoreServiceTest {
    private ErrandService storeService;

    @Before
    public void setUp(){
        storeService = RetrofitClient.getErrandService();
    }

    @Test
    public void testGetAllStores() throws Exception{
        Response<List<Store>> response = storeService.getAvailableStores().execute();
        assertTrue("Response failed " + response.code(), response.isSuccessful());
        List<Store> stores = response.body();
        if (stores != null && !stores.isEmpty()){
            for(Store store : stores){
                System.out.println(store.toString());
            }
        }
    }

    @Test
    public void testStore() throws Exception{
        Response<List<Store>> response = storeService.getStoreById("1").execute();
        assertTrue("Response failed " + response.code(), response.isSuccessful());
        List<Store> stores = response.body();
        if(stores != null && !stores.isEmpty()){
            Store store = stores.get(0);
            System.out.println(store.toString());
        }
    }
}
