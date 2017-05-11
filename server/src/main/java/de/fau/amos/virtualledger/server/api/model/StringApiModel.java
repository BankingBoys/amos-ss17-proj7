package de.fau.amos.virtualledger.server.api.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Class that holds a string value and is required for JSON parsing
 */
@XmlRootElement
public class StringApiModel {
    /**
     *
     */
    private String data;

    /**
     *
     * @return
     * @methodtype getter
     */
    public String getData()
    {
        return data;
    }

    /**
     *
     * @param data
     * @methodtype setter
     */
    @XmlElement
    public void setData(String data)
    {
        this.data = data;
    }
}
