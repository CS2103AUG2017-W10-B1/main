package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.address.Address;

/**
 * JAXB-friendly adapted version of the Address.
 */
public class XmlAdaptedAddress {

    @XmlValue
    private String AddressName;

    /**
     * Constructs an XmlAdaptedAddress.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAddress() {}

    /**
     * Converts a given Address into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAddress(Address source) {
        AddressName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted Address object into the model's Address object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Address toModelType() throws IllegalValueException {
        return new Address(AddressName);
    }

}
