package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import java.util.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiDummy;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBalanceBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;

/**
 * Created by Georg on 18.05.2017.
 */
@ApplicationScoped
@BankingApiDummy
public class DummyBankAccountEndpoint implements BankAccountEndpoint {

    DummyBankAccessEndpoint dummyBankAccessEndpoint;

    Map<String, List<BankAccountBankingModel>> bankAccountMap = new HashMap<String, List<BankAccountBankingModel>>();
    int numberBankAccount = 0;

    Map<BankAccountBankingModel, List<BookingModel>> bankBookingMap = new HashMap<BankAccountBankingModel, List<BookingModel>>();

    Random randomGenerator = new Random(System.nanoTime());

    @Inject
    public DummyBankAccountEndpoint(@BankingApiDummy DummyBankAccessEndpoint dummyBankAccessEndpoint)
    {
        this.dummyBankAccessEndpoint = dummyBankAccessEndpoint;
    }
    protected DummyBankAccountEndpoint()    { }


    @Override
    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankingAccessId) throws BankingException {
        if(!dummyBankAccessEndpoint.existsBankAccess(bankingAccessId))
        {
            throw new BankingException("Dummy found no existing BankAccess for Operation getBankAccounts!");
        }
        if(!bankAccountMap.containsKey(bankingAccessId))
        {
            this.generateDummyBankAccountModels(bankingAccessId);
        }
        return bankAccountMap.get(bankingAccessId);
    }

    @Override
    public List<BookingModel> syncBankAccount(String userId, String bankAccessId, String bankAccountId, String pin) throws BankingException {

        List<BankAccountBankingModel> bankAccountBankingModelList = this.getBankAccounts(userId, bankAccessId);

        BankAccountBankingModel matchingBankAccountBankingModel = this.findBankAccountBankingModel(bankAccountBankingModelList, bankAccountId);

        if(!bankBookingMap.containsKey(matchingBankAccountBankingModel))
        {
            return new ArrayList<BookingModel>();
        }
        return bankBookingMap.get(matchingBankAccountBankingModel);
    }


    /**
     *
     * generates a BankAccountBankingModel with dummy data
     * @param bankingAccessId
     * @return
     */
    private BankAccountBankingModel generateDummyBankAccountModel(String bankingAccessId)
    {
        BankAccountBankingModel bankAccountBankingModel = new BankAccountBankingModel();
        BankAccountBalanceBankingModel bankAccountBalanceBankingModel = new BankAccountBalanceBankingModel();
        bankAccountBalanceBankingModel.setAvailableHbciBalance(500.00);
        bankAccountBalanceBankingModel.setReadyHbciBalance(500.00);

        String id_postfix = numberBankAccount++ + "_" + System.nanoTime();

        bankAccountBankingModel.setBankAccountBalance(bankAccountBalanceBankingModel);
        bankAccountBankingModel.setCountryHbciAccount("DE");
        bankAccountBankingModel.setBlzHbciAccount("TestBLZ");
        bankAccountBankingModel.setNumberHbciAccount("TestHbciAccountNummer " + id_postfix);
        bankAccountBankingModel.setTypeHbciAccount("TestKonto " + id_postfix);
        bankAccountBankingModel.setCurrencyHbciAccount("EUR");
        bankAccountBankingModel.setNameHbciAccount("TestUser");
        bankAccountBankingModel.setBicHbciAccount("TestBIC");
        bankAccountBankingModel.setIbanHbciAccount("TestIBAN");
        bankAccountBankingModel.setId("TestID" + id_postfix);
        bankAccountBankingModel.setBankAccessId(bankingAccessId);

        return bankAccountBankingModel;
    }

    /**
     *
     * generates a few BankAccountBankingModel and inserts them into bankAccountMap;
     * for the last 6 months, 5 bookings each are generated
     * @param bankingAccessId
     * @return
     */
    private void generateDummyBankAccountModels(String bankingAccessId)
    {
        List<BankAccountBankingModel> bankAccountBankingModelList = new ArrayList<BankAccountBankingModel>();
        for(int i = 0; i < 5; ++i)
        {
            BankAccountBankingModel bankAccountBankingModel = this.generateDummyBankAccountModel(bankingAccessId);
            this.generateDummyBookingModels(bankAccountBankingModel);
            this.updateAccountBalance(bankAccountBankingModel);
            bankAccountBankingModelList.add(bankAccountBankingModel);
        }

        this.bankAccountMap.put(bankingAccessId, bankAccountBankingModelList);
    }


    /**
     * generates a few BookingModel and inserts them into bankBookingMap
     * @param bankAccountBankingModel
     */
    private void generateDummyBookingModels(BankAccountBankingModel bankAccountBankingModel)
    {
        List<BookingModel> bookingModelList = new ArrayList<BookingModel>();
        Date now = new Date();

        for(int i = 0; i <= 5; ++i)
        { // bookings for last 6 months
            int month = Calendar.getInstance().get(Calendar.MONTH) - i;

            for(int day = 30; day >= 0; day -= 3)
            { // day of the booking
                long targetDateLong = this.getDate(day, month, Calendar.getInstance().get(Calendar.YEAR));
                Date targetDate = new Date(targetDateLong);

                if(!targetDate.after(now)) {
                    // no bookings in future!
                    BookingModel bookingModel = this.generateDummyBookingModel(month, day);
                    bookingModelList.add(bookingModel);
                }
            }
        }

        this.bankBookingMap.put(bankAccountBankingModel, bookingModelList);
    }

    /**
     *
     * generates a BookingModel with dummy data
     * @param month
     * @param day
     * @return
     */
    private BookingModel generateDummyBookingModel(int month, int day)
    {
        BookingModel bookingModel = new BookingModel();

        // amount between -50.0 and +51.0
        double amount = randomGenerator.nextInt(100) - 50;
        amount += randomGenerator.nextInt(100) / 100.0;
        bookingModel.setAmount(amount);

        long date = this.getDate(day, month, Calendar.getInstance().get(Calendar.YEAR));
        bookingModel.setBookingDate(date);

        return bookingModel;
    }

    /**
     * generates a data in long format (milliseconds);
     * @param day
     * @param month
     * @param year
     * @return
     */
    private long getDate(int day, int month, int year)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }

    /**
     * finds account by bankAccountId in bankAccountBankingModelList
     * @param bankAccountBankingModelList
     * @param bankAccountId
     * @return
     * @throws BankingException if bankAccount with id does not exist
     */
    private BankAccountBankingModel findBankAccountBankingModel(List<BankAccountBankingModel> bankAccountBankingModelList, String bankAccountId) throws BankingException
    {
        BankAccountBankingModel matchingBankAccountBankingModel = null;
        for (BankAccountBankingModel bankAccountBankingModel: bankAccountBankingModelList)
        {
            if(bankAccountBankingModel.getId().equals(bankAccountId))
            {
                matchingBankAccountBankingModel = bankAccountBankingModel;
                break;
            }
        }

        if(matchingBankAccountBankingModel == null)
        {
            throw new BankingException("Dummy found no existing BankAccount for Operation Sync!");
        }
        return matchingBankAccountBankingModel;
    }

    /**
     * updates the bankAccountBankingModel by the bookings in bankBookingMap
     * @param bankAccountBankingModel
     */
    private void updateAccountBalance(BankAccountBankingModel bankAccountBankingModel)
    {
        List<BookingModel> bookingModelList = this.bankBookingMap.get(bankAccountBankingModel);

        for(BookingModel bookingModel: bookingModelList)
        {
            BankAccountBalanceBankingModel bankAccountBalanceBankingModel = bankAccountBankingModel.getBankAccountBalance();
            double oldAvailableBalance = bankAccountBalanceBankingModel.getAvailableHbciBalance();
            bankAccountBalanceBankingModel.setAvailableHbciBalance(oldAvailableBalance + bookingModel.getAmount());
            double oldReadyBalance = bankAccountBalanceBankingModel.getReadyHbciBalance();
            bankAccountBalanceBankingModel.setReadyHbciBalance(oldReadyBalance + bookingModel.getAmount());
        }
    }
}
