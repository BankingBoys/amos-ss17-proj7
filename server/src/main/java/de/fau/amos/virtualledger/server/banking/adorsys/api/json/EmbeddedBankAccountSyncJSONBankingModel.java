package de.fau.amos.virtualledger.server.banking.adorsys.api.json;

import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import java.util.List;

public class EmbeddedBankAccountSyncJSONBankingModel {

    private List<BookingModel> bookingEntityList;

    public EmbeddedBankAccountSyncJSONBankingModel() {
    }

    public List<BookingModel> getBookingEntityList() {
        return bookingEntityList;
    }

    public void setBookingEntityList(List<BookingModel> bookingEntityList) {
        this.bookingEntityList = bookingEntityList;
    }
}
