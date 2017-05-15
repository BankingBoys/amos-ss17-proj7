package de.fau.amos.virtualledger.server;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class TestRestProviderTest {
    private final TestRestProvider testRestProvider = new TestRestProvider();

    @Test
    public void helloWorld_noInput_200Status() {
        //arrange
        //act
        final Response result = testRestProvider.helloWorld();
        //assert
        Assert.assertEquals(200, result.getStatus());
    }

    @Test
    public void helloWorld_noInput_helloWorldEntity() {
        //arrange
        //act
        final Response result = testRestProvider.helloWorld();
        //assert
        Assert.assertEquals("hallo!", result.getEntity());
    }
}
