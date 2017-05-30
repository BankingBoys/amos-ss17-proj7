package de.fau.amos.virtualledger.android.Config;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Simon on 30.05.2017.
 */

public class PropertyReader {
    private Properties properties;
    private Context context;

    public PropertyReader(Context context) {
        this.context = context;
        properties = new Properties();
    }

    public Properties getCustomProperties(String property) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(property);
            properties.load(inputStream);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return properties;
    }
}
