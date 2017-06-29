package de.fau.amos.virtualledger.server.factories;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.dtos.StringApiModel;


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
