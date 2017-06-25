package de.fau.amos.virtualledger.android.api;

import java.util.List;

import de.fau.amos.virtualledger.android.model.SavingsAccount;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.android.model.UserCredential;
import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Restapi {

    @POST("/api/auth/register")
    Call<StringApiModel> register(@Body UserCredential credential);

    @POST("/api/auth/login")
    Call<SessionData> login(@Body LoginData loginData);

    @POST("/api/auth/logout")
    Call<StringApiModel> logout(@Header("Authorization") String token);

    @GET("/api/banking")
    Call<List<BankAccess>> getBankAccesses(@Header("Authorization") String token);

    @POST("/api/banking")
    Call<BankAccess> addBankAccess(@Header("Authorization") String token, @Body BankAccessCredential bankAccessCredential);

    @DELETE("/api/banking/{accessId}")
    Call<Void> deleteBankAccess(@Header("Authorization") String token, @Path("accessId") String accessId);

    @DELETE("/api/banking/{accessId}/{accountId}")
    Call<Void> deleteBankAccount(@Header("Authorization") String token, @Path("accessId") String accessId, @Path("accountId") String accountId);

    @PUT("/api/banking/sync")
    Call<BankAccountSyncResult> getBookings(@Header("Authorization") String token, @Body List<BankAccountSync> bankAccountSyncList);

    @GET("/api/savings")
    Call<List<SavingsAccount>> getSavingAccounts(@Header("Authorization") String token);

}
