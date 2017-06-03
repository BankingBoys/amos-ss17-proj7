package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import java.util.*;

import javax.enterprise.context.ApplicationScoped;

import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiDummy;
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

    Map<String, List<BankAccountBankingModel>> bankAccountMap = new HashMap<String, List<BankAccountBankingModel>>();
    int numberBankAccount = 0;

    Map<BankAccountBankingModel, List<BookingModel>> bankBookingMap = new HashMap<BankAccountBankingModel, List<BookingModel>>();

    Random randomGenerator = new Random(System.nanoTime());


    @Override
    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankingAccessId) throws BankingException {

        if(!bankAccountMap.containsKey(bankingAccessId))
        {
            this.generateDummyBankAccountModels(bankingAccessId);
        }
        return bankAccountMap.get(bankingAccessId);
    }

    @Override
    public List<BookingModel> syncBankAccount(String userId, String bankAccessId, String bankAccountId, String pin) throws BankingException {

        if(!bankAccountMap.containsKey(bankAccessId))
        {
            throw new BankingException("Dummy found no existing BankAccess for Operation Sync!");
        }
        List<BankAccountBankingModel> bankAccountBankingModelList = bankAccountMap.get(bankAccessId);

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
            bankAccountBankingModelList.add(bankAccountBankingModel);
            this.generateDummyBookingModels(bankAccountBankingModel);
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
                Calendar calendar = new GregorianCalendar();
                calendar.set(Calendar.getInstance().get(Calendar.YEAR), month, day);
                long targetDateInMilli = calendar.getTimeInMillis();
                Date targetDate = new Date(targetDateInMilli);

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

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.getInstance().get(Calendar.YEAR), month, day);
        long date = calendar.getTimeInMillis();
        bookingModel.setBookingDate(date);

        return bookingModel;
    }
}
