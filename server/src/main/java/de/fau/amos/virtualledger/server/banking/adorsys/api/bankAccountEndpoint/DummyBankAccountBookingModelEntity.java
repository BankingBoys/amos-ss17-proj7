package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BookingModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "dummies_bank_account_booking_banking_model")
public class DummyBankAccountBookingModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Long bookingDate;
    private Double amount;

    public DummyBankAccountBookingModelEntity() {
    }

    public DummyBankAccountBookingModelEntity(BookingModel bookingModel) {
        bookingDate = bookingModel.getBookingDate();
        amount = bookingModel.getAmount();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Long bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BookingModel transformToBookingModel() {
        BookingModel bookingModel = new BookingModel();
        bookingModel.setBookingDate(bookingDate);
        bookingModel.setAmount(amount);
        return bookingModel;
    }
}
