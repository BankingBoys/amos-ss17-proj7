package de.fau.amos.virtualledger.server.api.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Georg on 11.05.2017.
 */
@XmlRootElement
public class StringApiModel {
    private String data;

    public String getData()
    {
        return data;
    }

    @XmlElement
    public void setData(String data)
    {
        this.data = data;
    }
}
