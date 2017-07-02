package de.fau.amos.virtualledger.android.dagger.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 07.05.2017. taken from https://adityaladwa.wordpress.com/2016/05/09/
 * dagger-2-with-retrofit-and-okhttp-and-gson/)
 */
@Module
public class AppModule {
    Application virtualLedger;

    public AppModule(Application virtualLedger) {
        this.virtualLedger = virtualLedger;
    }

    @Provides
    Application provideApplication() {
        return virtualLedger;
    }

}
