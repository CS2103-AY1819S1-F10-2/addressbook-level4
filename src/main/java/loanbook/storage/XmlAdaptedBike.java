package loanbook.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import loanbook.commons.exceptions.IllegalValueException;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Name;

/**
 * JAXB-friendly version of the Bike.
 */
public class XmlAdaptedBike {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Bike's %s field is missing!";

    @XmlElement(required = true)
    private String name;

    /**
     * Constructs an XmlAdaptedBike.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBike() {}

    /**
     * Constructs an {@code XmlAdaptedBike} with the given bike details.
     */
    public XmlAdaptedBike(String name) {
        this.name = name;
    }

    /**
     * Converts a given Bike into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedBike
     */
    public XmlAdaptedBike(Bike source) {
        name = source.getName().value;
    }

    /**
     * Throws an {@code IllegalValueException} if {@code name} does not exist or is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkNameValid() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
    }

    /**
     * Converts this jaxb-friendly adapted bike object into the model's Bike object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted bike
     */
    public Bike toModelType() throws IllegalValueException {
        checkNameValid();
        final Name modelName = new Name(name);

        return new Bike(modelName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedBike)) {
            return false;
        }

        XmlAdaptedBike otherBike = (XmlAdaptedBike) other;
        return Objects.equals(name, otherBike.name);
    }
}
