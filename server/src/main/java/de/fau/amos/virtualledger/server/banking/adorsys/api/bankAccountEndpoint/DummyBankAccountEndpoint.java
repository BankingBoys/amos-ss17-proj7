package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessBankingModelEntity;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpointRepository;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBalanceBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
@Scope("singleton")
@Qualifier("dummy")
public class DummyBankAccountEndpoint implements BankAccountEndpoint {

    private static final int MAX_AMOUNT_ABS = 100;
    private static final int DATE_DIFFERENCE = 3;
    private static final int PREDICTED_MAX_DAYS = 30;
    private static final int NUMBER_OF_GENERATED_ITEMS = 3;
    private static final int PREDICTABILITY_SEED = 0;


    private DummyBankAccessEndpointRepository bankAccessEndpointRepository;
    private DummyBankAccessEndpoint dummyBankAccessEndpoint;
    private DummyBankAccountBankingModelRepository bankAccountBankingModelRepository;

    private Map<String, List<BookingModel>> bookingModelMap = new HashMap<>();
    private Map<String, BankAccountBalanceBankingModel> accountBalanceMap = new HashMap<>();


    private int numberBankAccount = 0;
    private Random randomGenerator = new Random(PREDICTABILITY_SEED);

    @Autowired
    public DummyBankAccountEndpoint(DummyBankAccountBankingModelRepository bankAccountBankingModelRepository, DummyBankAccessEndpointRepository bankAccessEndpointRepository, @Qualifier("dummy") DummyBankAccessEndpoint dummyBankAccessEndpoint) {
        this.bankAccountBankingModelRepository = bankAccountBankingModelRepository;
        this.bankAccessEndpointRepository = bankAccessEndpointRepository;
        this.dummyBankAccessEndpoint = dummyBankAccessEndpoint;
    }

    protected DummyBankAccountEndpoint() {
    }

    /**
     * gets the dummy banking accounts.
     * Generates accounts if there weren't ones created.
     * Generates bookings if there weren't ones created (or saved).
     *
     * @param bankingAccessId
     * @return
     * @throws BankingException
     */
    @Override
    public List<BankAccountBankingModel> getBankAccounts(String bankingAccessId)
            throws BankingException {
        if (!dummyBankAccessEndpoint.existsBankAccess(bankingAccessId)) {
            // throw new BankingException("Dummy found no existing BankAccess
            // for Operation getBankAccounts!");
            // inconsistency -> on app can be accesses persisted that are not in
            // local storage of server
            return new ArrayList<BankAccountBankingModel>(); // TODO? better
            // solution?
        }
        if (!bankAccountBankingModelRepository.existBankAccountsForAccessId(bankingAccessId)) {
            this.generateDummyBankAccountModels(bankingAccessId);
        }

        List<DummyBankAccountBankingModelEntity> allByAccessId = bankAccountBankingModelRepository.findAllByAccessId(bankingAccessId);

        List<BankAccountBankingModel> bankAccountBankingModelList = new ArrayList<>();
        for (DummyBankAccountBankingModelEntity bankAccountBankingModelEntity : allByAccessId) {
            if (!bookingModelMap.containsKey(bankAccountBankingModelEntity.getId())) {
                generateDummyBookingModels(bankAccountBankingModelEntity.getId());
                updateAccountBalance(bankAccountBankingModelEntity.getId());
            }
            bankAccountBankingModelEntity.setBankAccountBalance(accountBalanceMap.get(bankAccountBankingModelEntity.getId()));
            bankAccountBankingModelList.add(bankAccountBankingModelEntity.transformIntoBankAccountBankingModel());
        }

        return bankAccountBankingModelList;
    }

    @Override
    public List<BookingModel> syncBankAccount(String bankAccessId, String bankAccountId, String pin)
            throws BankingException {

        if (!bookingModelMap.containsKey(bankAccountId)) {
            generateDummyBookingModels(bankAccountId);
            updateAccountBalance(bankAccountId);
        }
        return bookingModelMap.get(bankAccountId);
    }

    /**
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
     * generates a few BankAccountBankingModel and inserts them into
     * bankAccountMap; for the last 6 months, 5 bookings each are generated
     *
     * @param bankingAccessId
     * @return
     */
    private void generateDummyBankAccountModels(String bankingAccessId) {
        List<DummyBankAccountBankingModelEntity> bankAccountBankingModelEntityList = new ArrayList<>();
        DummyBankAccessBankingModelEntity bankAccessBankingModelEntity = bankAccessEndpointRepository.findOne(bankingAccessId);
        for (int i = 0; i < NUMBER_OF_GENERATED_ITEMS; ++i) {
            BankAccountBankingModel bankAccountBankingModel = this.generateDummyBankAccountModel(bankingAccessId);
            DummyBankAccountBankingModelEntity dummyBankAccountBankingModelEntity = new DummyBankAccountBankingModelEntity(bankAccountBankingModel, bankAccessBankingModelEntity);
            generateDummyBookingModels(dummyBankAccountBankingModelEntity.getId());
            updateAccountBalance(dummyBankAccountBankingModelEntity.getId());
            bankAccountBankingModelEntityList.add(dummyBankAccountBankingModelEntity);
        }
        bankAccountBankingModelRepository.save(bankAccountBankingModelEntityList);
    }

    /**
     * generates a few BookingModel and saves them into the bookingsMap
     *
     * @param accountId
     */
    private void generateDummyBookingModels(String accountId) {
        List<BookingModel> bookingModelList = new ArrayList<>();
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
        bookingModelMap.put(accountId, bookingModelList);
    }

    /**
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
     * updates the bankAccountBankingModel by the bookings in bankAccountBankingModel and stores into accountBalanceMap
     *
     * @param accountId
     */
    private void updateAccountBalance(String accountId) {
        List<BookingModel> bookingModelList = bookingModelMap.get(accountId);

        double value = 0.0;
        for (BookingModel bookingModel : bookingModelList) {

            value += bookingModel.getAmount();
        }
        BankAccountBalanceBankingModel bankAccountBalanceBankingModel = new BankAccountBalanceBankingModel();
        bankAccountBalanceBankingModel.setReadyHbciBalance(value);
        bankAccountBalanceBankingModel.setAvailableHbciBalance(value);
        accountBalanceMap.put(accountId, bankAccountBalanceBankingModel);
    }
}
