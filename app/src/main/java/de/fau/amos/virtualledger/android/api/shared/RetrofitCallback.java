package de.fau.amos.virtualledger.android.api.shared;

import android.util.Log;

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
            Log.v(TAG, requestString + "was successful: " + response.code());
            observable.onNext(result);
        } else {
            Log.e(TAG, requestString + "was not successful! ERROR " + response.code());
            observable.onError(new Throwable(requestString + " was not successful!"));
        }
    }

    @Override
    public void onFailure(final Call<T> call, final Throwable t) {
        // response of server failed
        Log.e(TAG, "No connection to server!");
        observable.onError(t);
    }
}
