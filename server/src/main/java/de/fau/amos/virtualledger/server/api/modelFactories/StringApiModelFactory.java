package de.fau.amos.virtualledger.server.api.modelFactories;

import de.fau.amos.virtualledger.server.api.model.StringApiModel;

import javax.enterprise.context.RequestScoped;

/**
 * Created by Georg on 11.05.2017.
 */
@RequestScoped
public class StringApiModelFactory {

    public StringApiModel createStringApiModel(String string)
    {
        StringApiModel stringApiModel = new StringApiModel();
        stringApiModel.setData(string);
        return stringApiModel;
    }
}
