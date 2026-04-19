package org.greatbarrierreeve.daizoubu;

import static org.junit.Assert.assertTrue;

import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.AddOnItem;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.model.ErrandStatus;
import org.greatbarrierreeve.daizoubu.data.model.Location;
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;
import org.greatbarrierreeve.daizoubu.data.model.OrderItem;
import org.greatbarrierreeve.daizoubu.data.model.PriorityLevel;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        errandService = RetrofitClient.getErrandService();
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
        public void testGetErrandByRunnerId() throws Exception {
            Response<List<Errand>> response = errandService.getErrandByRunnerId("123").execute();
            assertTrue("Response failed " + response.code(), response.isSuccessful());
            List<Errand> errandResponse = response.body();
            if (errandResponse != null) {
                for (Errand errand : errandResponse) {
                    System.out.println(errand.toString());
                }
            }
        }

        @Test
        public void testGetErrandByBuyerId() throws Exception {
            Response<List<Errand>> response = errandService.getErrandByBuyerId("123").execute();
            assertTrue("Response failed " + response.code(), response.isSuccessful());
            List<Errand> errandResponse = response.body();
            if (errandResponse != null) {
                for (Errand errand : errandResponse) {
                    System.out.println(errand.toString());
                }
            }
        }

        @Test
        public void createErrand() throws Exception{
            List<AddOnItem> list = new ArrayList<AddOnItem>();
            list.add(new AddOnItem("123", "asd", "asd", "123"));
            MenuItem menuItem = new MenuItem.Builder(
                    "asd",
                    "asd, asd",
                    "asd",
                    "123" ,"1")
                    .setOptionAddOns(list)
                    .build();
//            if (menuItem != null)
//            orderItem.setMenuItem(menuItem);
//            orderItem.setQty(5);
//            orderItem.setSpecialReq("asd");
//            orderItem.setUserAddOns(list);

//            errand.setBuyerId("123");
//            errand.setRunnerId("234");
//            errand.setBounty(new BigDecimal(100));
//            errand.setStatus(ErrandStatus.REQUESTED);
//            errand.setPriorityLevel(PriorityLevel.NORMAL);
//            errand.setOrderItem(orderItem);

            List<OrderItem> orderItem = new ArrayList<OrderItem>();

            OrderItem item = new OrderItem.Builder(menuItem,5).build();
            orderItem.add(item);

            Location location = new Location("Albert Hong", "1.102");
            Errand errand = new Errand(
                    "123",
                    "234",
                    new BigDecimal(100),
                    orderItem,
                    ErrandStatus.REQUESTED,
                    PriorityLevel.NORMAL,
                    location,
                    "Cai fan",
                    new BigDecimal(200));
            Response<Errand> response = errandService.createErrand(errand).execute();
            assertTrue("Response failed " + response.code(), response.isSuccessful());
            Errand errandResponse = response.body();
            if (errandResponse != null){
                System.out.println(errandResponse.toString());
            }

        }

}
