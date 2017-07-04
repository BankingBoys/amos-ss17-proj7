package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBalanceBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;

@Component
@Scope("singleton")
@Qualifier("dummy")
public class DummyBankAccountEndpoint implements BankAccountEndpoint {

    private static final int MAX_AMOUNT_ABS = 100;

    private static final int DATE_DIFFERENCE = 3;

    private static final int PREDICTED_MAX_DAYS = 30;

    private static final int NUMBER_OF_GENERATED_ITEMS = 5;

    private static final int PREDICTABILITY_SEED = 0;

    private DummyBankAccessEndpoint dummyBankAccessEndpoint;

    private Map<String, List<BankAccountBankingModel>> bankAccountMap = new HashMap<String, List<BankAccountBankingModel>>();
    private int numberBankAccount = 0;

    private Map<BankAccountBankingModel, List<BookingModel>> bankBookingMap = new HashMap<BankAccountBankingModel, List<BookingModel>>();

    private Random randomGenerator = new Random(PREDICTABILITY_SEED);

    @Autowired
    public DummyBankAccountEndpoint(@Qualifier("dummy") DummyBankAccessEndpoint dummyBankAccessEndpoint) {
        this.dummyBankAccessEndpoint = dummyBankAccessEndpoint;
    }

    protected DummyBankAccountEndpoint() {
    }

    @Override
    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankingAccessId)
            throws BankingException {
        if (!dummyBankAccessEndpoint.existsBankAccess(bankingAccessId)) {
            // throw new BankingException("Dummy found no existing BankAccess
            // for Operation getBankAccounts!");
            // inconsistency -> on app can be accesses persisted that are not in
            // local storage of server
            return new ArrayList<BankAccountBankingModel>(); // TODO? better
                                                             // solution?
        }
        if (!bankAccountMap.containsKey(bankingAccessId)) {
            this.generateDummyBankAccountModels(bankingAccessId);
        }
        return bankAccountMap.get(bankingAccessId);
    }

    @Override
    public List<BookingModel> syncBankAccount(String userId, String bankAccessId, String bankAccountId, String pin)
            throws BankingException {

        List<BankAccountBankingModel> bankAccountBankingModelList = this.getBankAccounts(userId, bankAccessId);
        BankAccountBankingModel matchingBankAccountBankingModel;
        try {
            matchingBankAccountBankingModel = this.findBankAccountBankingModel(bankAccountBankingModelList,
                    bankAccountId);
        } catch (BankingException ex) { // inconsistency -> on app can be
                                        // accesses persisted that are not in
                                        // local storage of server
            return new ArrayList<BookingModel>(); // TODO? better solution?
        }
        if (!bankBookingMap.containsKey(matchingBankAccountBankingModel)) {
            return new ArrayList<BookingModel>();
        }
        return bankBookingMap.get(matchingBankAccountBankingModel);
    }

    /**
     *
     * generates a BankAccountBankingModel with dummy data
     * 
     * @param bankingAccessId
     * @return
     */
    private BankAccountBankingModel generateDummyBankAccountModel(String bankingAccessId) {
        BankAccountBankingModel bankAccountBankingModel = new BankAccountBankingModel();
        BankAccountBalanceBankingModel bankAccountBalanceBankingModel = new BankAccountBalanceBankingModel();
        bankAccountBalanceBankingModel.setAvailableHbciBalance(0.00);
        bankAccountBalanceBankingModel.setReadyHbciBalance(0.00);

        String idPostfix = numberBankAccount++ + "_" + System.nanoTime();

        bankAccountBankingModel.setBankAccountBalance(bankAccountBalanceBankingModel);
        bankAccountBankingModel.setCountryHbciAccount("DE");
        bankAccountBankingModel.setBlzHbciAccount("TestBLZ");
        bankAccountBankingModel.setNumberHbciAccount("TestHbciAccountNummer " + idPostfix);
        bankAccountBankingModel.setTypeHbciAccount("TestKonto " + idPostfix);
        bankAccountBankingModel.setCurrencyHbciAccount("EUR");
        bankAccountBankingModel.setNameHbciAccount("TestUser");
        bankAccountBankingModel.setBicHbciAccount("TestBIC");
        bankAccountBankingModel.setIbanHbciAccount("TestIBAN");
        bankAccountBankingModel.setId("TestID" + idPostfix);
        bankAccountBankingModel.setBankAccessId(bankingAccessId);

        return bankAccountBankingModel;
    }

    /**
     *
     * generates a few BankAccountBankingModel and inserts them into
     * bankAccountMap; for the last 6 months, 5 bookings each are generated
     * 
     * @param bankingAccessId
     * @return
     */
    private void generateDummyBankAccountModels(String bankingAccessId) {
        List<BankAccountBankingModel> bankAccountBankingModelList = new ArrayList<BankAccountBankingModel>();
        for (int i = 0; i < NUMBER_OF_GENERATED_ITEMS; ++i) {
            BankAccountBankingModel bankAccountBankingModel = this.generateDummyBankAccountModel(bankingAccessId);
            this.generateDummyBookingModels(bankAccountBankingModel);
            this.updateAccountBalance(bankAccountBankingModel);
            bankAccountBankingModelList.add(bankAccountBankingModel);
        }

        this.bankAccountMap.put(bankingAccessId, bankAccountBankingModelList);
    }

    /**
     * generates a few BookingModel and inserts them into bankBookingMap
     * 
     * @param bankAccountBankingModel
     */
    private void generateDummyBookingModels(BankAccountBankingModel bankAccountBankingModel) {
        List<BookingModel> bookingModelList = new ArrayList<BookingModel>();
        Date now = new Date();

        for (int i = 0; i <= NUMBER_OF_GENERATED_ITEMS; ++i) { // bookings for
                                                               // last 6 months
            int month = Calendar.getInstance().get(Calendar.MONTH) - i;

            for (int day = PREDICTED_MAX_DAYS; day >= 0; day -= DATE_DIFFERENCE) { // day
                                                                                   // of
                                                                                   // the
                                                                                   // booking
                long targetDateLong = this.getDate(day, month, Calendar.getInstance().get(Calendar.YEAR));
                Date targetDate = new Date(targetDateLong);

                if (!targetDate.after(now)) {
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
     * 
     * @param month
     * @param day
     * @return
     */
    private BookingModel generateDummyBookingModel(int month, int day) {
        BookingModel bookingModel = new BookingModel();

        // amount between -50.0 and +51.0
        double amount = randomGenerator.nextInt(MAX_AMOUNT_ABS) - (MAX_AMOUNT_ABS / 2);
        amount += randomGenerator.nextInt(MAX_AMOUNT_ABS) / new Double(MAX_AMOUNT_ABS).doubleValue();
        bookingModel.setAmount(amount);

        long date = this.getDate(day, month, Calendar.getInstance().get(Calendar.YEAR));
        bookingModel.setBookingDate(date);

        return bookingModel;
    }

    /**
     * generates a data in long format (milliseconds);
     * 
     * @param day
     * @param month
     * @param year
     * @return
     */
    private long getDate(int day, int month, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }

    /**
     * finds account by bankAccountId in bankAccountBankingModelList
     * 
     * @param bankAccountBankingModelList
     * @param bankAccountId
     * @return
     * @throws BankingException
     *             if bankAccount with id does not exist
     */
    private BankAccountBankingModel findBankAccountBankingModel(
            List<BankAccountBankingModel> bankAccountBankingModelList, String bankAccountId) throws BankingException {
        BankAccountBankingModel matchingBankAccountBankingModel = null;
        for (BankAccountBankingModel bankAccountBankingModel : bankAccountBankingModelList) {
            if (bankAccountBankingModel.getId().equals(bankAccountId)) {
                matchingBankAccountBankingModel = bankAccountBankingModel;
                break;
            }
        }

        if (matchingBankAccountBankingModel == null) {
            throw new BankingException("Dummy found no existing BankAccount for Operation Sync!");
        }
        return matchingBankAccountBankingModel;
    }

    /**
     * updates the bankAccountBankingModel by the bookings in bankBookingMap
     * 
     * @param bankAccountBankingModel
     */
    private void updateAccountBalance(BankAccountBankingModel bankAccountBankingModel) {
        List<BookingModel> bookingModelList = this.bankBookingMap.get(bankAccountBankingModel);

        for (BookingModel bookingModel : bookingModelList) {
            BankAccountBalanceBankingModel bankAccountBalanceBankingModel = bankAccountBankingModel
                    .getBankAccountBalance();
            double oldAvailableBalance = bankAccountBalanceBankingModel.getAvailableHbciBalance();
            bankAccountBalanceBankingModel.setAvailableHbciBalance(oldAvailableBalance + bookingModel.getAmount());
            double oldReadyBalance = bankAccountBalanceBankingModel.getReadyHbciBalance();
            bankAccountBalanceBankingModel.setReadyHbciBalance(oldReadyBalance + bookingModel.getAmount());
        }
    }
}
