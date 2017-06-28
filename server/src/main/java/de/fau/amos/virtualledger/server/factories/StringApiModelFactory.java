package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.StringApiModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.enterprise.context.RequestScoped;


@Component
@Scope("request")
public class StringApiModelFactory {

    /**
     * creates a StringApiModel
     * @param string
     * @return
     */
    public StringApiModel createStringApiModel(String string)
    {
        StringApiModel stringApiModel = new StringApiModel();
        stringApiModel.setData(string);
        return stringApiModel;
    }
}
