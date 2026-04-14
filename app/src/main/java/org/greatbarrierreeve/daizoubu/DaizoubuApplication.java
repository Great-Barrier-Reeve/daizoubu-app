package org.greatbarrierreeve.daizoubu;

import android.app.Application;

import org.greatbarrierreeve.daizoubu.data.repository.UserInfoRepository;

public class DaizoubuApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        UserInfoRepository.init(this);
    }
}
