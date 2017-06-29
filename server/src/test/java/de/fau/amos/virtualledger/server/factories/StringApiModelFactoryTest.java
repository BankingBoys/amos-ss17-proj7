package de.fau.amos.virtualledger.server.factories;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fau.amos.virtualledger.dtos.StringApiModel;

/**
 * Created by Georg on 21.06.2017.
 */
public class StringApiModelFactoryTest {

    StringApiModelFactory stringApiModelFactory;

    @Before
    public void setup() {
        stringApiModelFactory = new StringApiModelFactory();
    }

    @Test
    public void create_successful() {
        // SETUP
        String testString = "test";

        // ACT
        StringApiModel stringApiModel = stringApiModelFactory.createStringApiModel(testString);

        // ASSERT
        Assert.assertNotNull(stringApiModel);
        Assert.assertEquals(testString, stringApiModel.getData());
    }
}
