package de.fau.amos.virtualledger.server.api.modelFactories;

import de.fau.amos.virtualledger.server.api.model.StringApiModel;

import javax.enterprise.context.RequestScoped;

/**
 * Factory class for StringApiModel
 */
@RequestScoped
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
