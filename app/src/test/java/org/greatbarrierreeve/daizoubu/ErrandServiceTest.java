package org.greatbarrierreeve.daizoubu;

import static org.junit.Assert.assertTrue;

import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.model.ErrandStatus;
import org.greatbarrierreeve.daizoubu.data.model.PriorityLevel;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ErrandServiceTest {
    private ErrandService errandService;

    @Before
    public void setUp() {
        errandService = RetrofitClient.getService();
    }

    //normal test case
    @Test
    public void testGetAvailableErrands() throws Exception {
        // Call the API method
        Response<List<Errand>> response = errandService.getAvailableErrands().execute();
        assertTrue("Response failed " + response.code(), response.isSuccessful());
        List<Errand> errands = response.body();
        if (errands != null && !errands.isEmpty()){
            for (Errand errand : errands) {
                System.out.println(errand.toString());
            }
        }
    }

    //async don't have to use latch, latch only necessary to simulate await in test env
    //enqueue needed for retrofit to spawn background thread for api call
//    @Test
//    public void asyncTestGetAvailableErrands() throws Exception {
//
//        CountDownLatch latch = new CountDownLatch(1);
//        // Call the API method
//        Call<List<Errand>> response = errandService.getAvailableErrands();
//        response.enqueue(new Callback<List<Errand>>() {
//            @Override
//            public void onResponse(Call<List<Errand>> call, Response<List<Errand>> response) {
//                if(response.isSuccessful()) {
//                    assert response.body() != null;
//                    if (!response.body().isEmpty()) {
//                        List<Errand> errands = response.body();
//                            for (Errand errand : errands) {
//                                System.out.println("async WORKS!" + errand.toString());
//                            }
//                            latch.countDown();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Errand>> call, Throwable throwable) {
//                latch.countDown();
//                System.out.println(throwable.getMessage());
//            }
//        });
//        boolean completed = latch.await(3, TimeUnit.SECONDS);
//        assertTrue("Test timed out!", completed);
//    }
        @Test
        public void testGetErrandById() throws Exception{
            Response<Errand> response = errandService.getErrandById("t1nuktTTCuasTYHh73D1").execute();
            assertTrue("Response failed " + response.code(), response.isSuccessful());
            Errand errand = response.body();
            if (errand != null){
                System.out.println(errand.toString());
            }
        }

        @Test
        public void createErrand() throws Exception{
            Errand errand = new Errand();
            errand.setBuyerId("123");
            errand.setRunnerId("234");
            errand.setBounty(new BigDecimal(100));
            errand.setStatus(ErrandStatus.REQUESTED);
            errand.setPriorityLevel(PriorityLevel.NORMAL);
            Response<Errand> response = errandService.createErrand(errand).execute();
            assertTrue("Response failed " + response.code(), response.isSuccessful());
            Errand errandResponse = response.body();
            if (errandResponse != null){
                System.out.println(errandResponse.toString());
            }

        }

}
