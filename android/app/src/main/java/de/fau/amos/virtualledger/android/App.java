package de.fau.amos.virtualledger.android;

import android.app.Application;

import de.fau.amos.virtualledger.android.dagger.component.DaggerNetComponent;
import de.fau.amos.virtualledger.android.dagger.component.NetComponent;
import de.fau.amos.virtualledger.android.dagger.module.AppModule;
import de.fau.amos.virtualledger.android.dagger.module.NetModule;

/**
 * Created by Simon on 07.05.2017. taken from https://adityaladwa.wordpress.com/2016/05/09/
 * dagger-2-with-retrofit-and-okhttp-and-gson/)
 */

public class App extends Application{

    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://192.168.178.27:8080 "))
                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

}
