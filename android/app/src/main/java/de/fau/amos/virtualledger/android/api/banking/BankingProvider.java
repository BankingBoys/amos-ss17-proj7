package de.fau.amos.virtualledger.android.api.banking;

import android.content.Context;

import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccess;
import io.reactivex.Observable;

/**
 * Created by sebastian on 14.05.17.
 */

public interface BankingProvider {

    Observable<List<BankAccess>> getBankingOverview();
}
