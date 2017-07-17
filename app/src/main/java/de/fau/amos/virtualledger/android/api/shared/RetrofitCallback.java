package de.fau.amos.virtualledger.android.api.shared;

import android.util.Log;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallback<T> implements Callback<T> {

    private static final String TAG = RetrofitCallback.class.getSimpleName();

    private final PublishSubject<T> observable;

    public RetrofitCallback(final PublishSubject<T> observable) {
        this.observable = observable;
    }

    @Override
    public void onResponse(final Call<T> call, final Response<T> response) {
        // got response from server
        final String requestString = "Request " + call.request().method() + " " + call.request().url() + " ";
        if (response.isSuccessful()) {
            final T result = response.body();
            logger().log(Level.INFO, "#####'Body" + result);
            Log.v(TAG, requestString + "was successful: " + response.code());
            if (result == null) {
                observable.onComplete();
                return;
            }
            observable.onNext(result);
        } else {
            Log.e(TAG, requestString + "was not successful! ERROR " + response.code());
            observable.onError(new IOException(requestString + " was not successful!"));
        }
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }


    @Override
    public void onFailure(final Call<T> call, final Throwable t) {
        // response of server failed
        Log.e(TAG, "No connection to server!");
        observable.onError(t);
    }
}
