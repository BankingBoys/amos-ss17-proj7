package de.fau.amos.virtualledger.android.api.sync;

/**
 * Created by sebastian on 20.07.17.
 */

public class DoNothing implements ServerCallStatusHandler {
    @Override
    public void onTechnicalError() {
        //nothing to do here
    }

    @Override
    public void onConceptualError() {
        //nothing to do here
    }

    @Override
    public void onOk() {
        //nothing to do here
    }
}
