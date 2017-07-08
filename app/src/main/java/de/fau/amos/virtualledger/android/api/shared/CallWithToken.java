package de.fau.amos.virtualledger.android.api.shared;

import android.util.Log;

import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class CallWithToken {

    private static final String TAG = CallWithToken.class.getSimpleName();

    final AuthenticationProvider authenticationProvider;

    public CallWithToken(final AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    public void callWithToken(final PublishSubject observable, final TokenCallback tokenCallback) {
        authenticationProvider.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {
                        tokenCallback.onReceiveToken(token);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        // did not get any token
                        Log.e(TAG, throwable.getMessage());
                        observable.onError(new Throwable("No authentication token available!"));
                    }
                });

    }
}
