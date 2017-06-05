package dtos;

import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import org.junit.*;
/**
 * Created by Simon on 05.06.2017.
 */
public class BankAccessCredentialTest {

    private String bankcode = "testCode";
    private String banklogin = "testLogin";
    private String pin = "testPin";
    private BankAccessCredential credential;

    /**
     *
     */
    @Before
    public void setUp() {
        credential = new BankAccessCredential(bankcode, banklogin, pin);
    }

    /**
     *
     */
    @Test
    public void constructorTest() {
        BankAccessCredential credential = new BankAccessCredential(bankcode, banklogin, pin);
        Assert.assertNotNull(credential);
    }

    /**
     *
     */
    @Test
    public void getAndSetBankcodeTest() {
        String testCode = "newTestCode";
        credential.setBankcode(testCode);
        Assert.assertEquals(testCode, credential.getBankcode());
    }

    /**
     *
     */
    @Test
    public void getAndSetBankloginTest() {
        String testLogin = "newTestLogin";
        credential.setBanklogin(testLogin);
        Assert.assertEquals(testLogin, credential.getBanklogin());
    }

    /**
     *
     */
    @Test
    public void getAndSetPinTest() {
        String testPin = "newTestPin";
        credential.setPin(testPin);
        Assert.assertEquals(testPin, credential.getPin());
    }




}
