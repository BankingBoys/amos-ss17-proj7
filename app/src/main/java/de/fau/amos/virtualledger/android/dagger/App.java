package de.fau.amos.virtualledger.android.dagger;

import android.app.Application;
import android.content.Context;

import java.util.Properties;

import de.fau.amos.virtualledger.android.config.PropertyReader;
import de.fau.amos.virtualledger.android.dagger.component.DaggerNetComponent;
import de.fau.amos.virtualledger.android.dagger.component.NetComponent;
import de.fau.amos.virtualledger.android.dagger.module.AppModule;
import de.fau.amos.virtualledger.android.dagger.module.NetModule;

/**
 * Created by Simon on 07.05.2017. taken from https://adityaladwa.wordpress.com/2016/05/09/
 * dagger-2-with-retrofit-and-okhttp-and-gson/)
 */

public class App extends Application {

    private NetComponent netComponent;

    private PropertyReader reader;
    private Properties properties;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        reader = new PropertyReader(context);
        properties = reader.getCustomProperties("config.properties");
        String ip = properties.getProperty("IPAddress");

        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(ip))
                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

}
