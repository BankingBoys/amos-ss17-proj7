package de.fau.amos.virtualledger.android.api.sync;

/**
 * Created by sebastian on 20.07.17.
 */

public class DoNothing implements ServerCallStatusHandler {
    @Override
    public void onTechnicalError() {

    }

    @Override
    public void onConceptualError() {

    }

    @Override
    public void onOk() {

    }
}
