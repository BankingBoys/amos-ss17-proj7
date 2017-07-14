package de.fau.amos.virtualledger.android.api.sync;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sebastian on 12.07.17.
 */

public class Toaster implements ServerCallStatusHandler {

    private String technicalErrorMessage = "An technical error occurred. Please try again later.";
    private String conceptualErrorMessage = "Something went conceptual wrong. PLease contact your admin.";
    private String successMessage = "Success";

    private Context context;

    public Toaster(Context context) {
        this.context = context;
    }

    public Toaster pushTechnicalErrorMessage(String technicalErrorMessage) {
        this.technicalErrorMessage = technicalErrorMessage;
        return this;
    }

    public Toaster pushConceptualErrorMessage(String conceptualErrorMessage) {
        this.conceptualErrorMessage = conceptualErrorMessage;
        return this;
    }

    public Toaster pushSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
        return this;
    }


    @Override
    public void onTechnicalError() {
        Toast.makeText(this.context, this.technicalErrorMessage, Toast.LENGTH_LONG);
    }

    @Override
    public void onConceptualError() {
        Toast.makeText(this.context, this.conceptualErrorMessage, Toast.LENGTH_LONG);
    }

    @Override
    public void onOk() {
        Toast.makeText(this.context, this.successMessage, Toast.LENGTH_LONG);
    }
}
