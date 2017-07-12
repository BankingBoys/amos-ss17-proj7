package de.fau.amos.virtualledger.android.api.sync;

/**
 * Created by sebastian on 12.07.17.
 */

public interface ServerCallStatusHandler {

    void onTechnicalError();

    void onConceptualError();

    void onOk();
}
