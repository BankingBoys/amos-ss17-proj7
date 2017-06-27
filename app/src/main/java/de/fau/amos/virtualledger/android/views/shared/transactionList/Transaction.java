package de.fau.amos.virtualledger.android.views.shared.transactionList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.TimeZone;

import de.fau.amos.virtualledger.dtos.Booking;
import hirondelle.date4j.DateTime;

/**
 * Created by sebastian on 05.06.17.
 */

public class Transaction implements Parcelable {

    private String bankName;
    private Booking booking;
    private String bankAccountID;


    public Transaction(String bankName, String bankAccountID, Booking booking) {
        this.bankName = bankName;
        this.booking = booking;
        this.bankAccountID = bankAccountID;
    }

    public Booking booking() {
        return this.booking;
    }

    public String bankName() {
        return this.bankName;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String bankAccountID() {
        return bankAccountID;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bankName);
        dest.writeLong(booking.getDate().getTime());
        dest.writeDouble(booking.getAmount());
        dest.writeString(booking.getUsage());
    }


    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    protected Transaction(Parcel in) {
        bankName = in.readString();
        Date date = new Date(in.readLong());
        double amount = in.readDouble();
        String usage = in.readString();
        booking = new Booking(date, amount);
        booking.setUsage(usage);
    }
    private long cachedTimeAsLong = -1;

    public long getCachedTimeAsLong(){
        if (this.cachedTimeAsLong == -1){
            this.cachedTimeAsLong = this.getDateTime(booking().getDate()).getMilliseconds(TimeZone.getDefault());
        }
        return this.cachedTimeAsLong;
    }

    private DateTime getDateTime(Date date) {
        DateTime exactDateTime = DateTime.forInstant(date.getTime(), TimeZone.getDefault());
        return new DateTime(exactDateTime.getYear(), exactDateTime.getMonth(), exactDateTime.getDay(), 0, 0, 0, 0);
    }
}
