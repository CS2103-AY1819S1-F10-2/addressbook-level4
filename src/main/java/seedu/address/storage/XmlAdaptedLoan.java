package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Address;
import seedu.address.model.loan.Email;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanRate;
import seedu.address.model.loan.LoanStatus;
import seedu.address.model.loan.LoanTime;
import seedu.address.model.loan.Name;
import seedu.address.model.loan.Nric;
import seedu.address.model.loan.Phone;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Loan.
 */
public class XmlAdaptedLoan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Loan's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String nric;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String loanStatus;
    @XmlElement(required = true)
    private String bike;
    @XmlElement(required = true)
    private String rate;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement
    private String endTime;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedLoan.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLoan() {}

    /**
     * Constructs an {@code XmlAdaptedLoan} with the given loan details.
     */
    public XmlAdaptedLoan(String name,
                          String nric,
                          String phone,
                          String email,
                          String address,
                          String bike,
                          String rate,
                          String startTime,
                          String endTime,
                          String loanStatus,
                          List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.bike = bike;
        this.rate = rate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.loanStatus = loanStatus;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Constructs an {@code XmlAdaptedLoan} with the given loan details.
     * This constructor is called if no loanStatus is given
     */
    public XmlAdaptedLoan(String name,
                          String nric,
                          String phone,
                          String email,
                          String address,
                          String bike,
                          String rate,
                          String startTime,
                          String endTime,
                          List<XmlAdaptedTag> tagged) {
        this(name, nric, phone, email, address, bike, rate, startTime, endTime, "ONGOING", tagged);
    }

    /**
     * Converts a given Loan into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLoan
     */
    public XmlAdaptedLoan(Loan source) {
        name = source.getName().value;
        nric = source.getNric().nric;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        bike = source.getBike().getName().value;
        rate = source.getLoanRate().toString();
        startTime = source.getLoanStartTime().toString();
        endTime = source.getLoanEndTime() == null ? null : source.getLoanEndTime().toString();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        loanStatus = source.getLoanStatus().name();
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
     * Throws an {@code IllegalValueException} if {@code nric} does not exist or is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkNricValid() throws IllegalValueException {
        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
    }

    /**
     * Throws an {@code IllegalValueException} if {@code phone} does not exist or is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkPhoneValid() throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
    }

    /**
     * Throws an {@code IllegalValueException} if {@code email} does not exist or is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkEmailValid() throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
    }

    /**
     * Throws an {@code IllegalValueException} if {@code address} does not exist or is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkAddressValid() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
    }

    /**
     * Throws an {@code IllegalValueException} if {@code loanStatus} does not exist or is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkLoanStatusValid() throws IllegalValueException {
        if (loanStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LoanStatus.class.getSimpleName()));
        }
        if (!LoanStatus.isValidLoanStatus(loanStatus)) {
            throw new IllegalValueException(LoanStatus.MESSAGE_LOANSTATUS_CONSTRAINTS);
        }
    }

    /**
     * Throws an {@code IllegalValueException} if {@code bike} does not exist or is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkBikeValid() throws IllegalValueException {
        if (bike == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Bike.class.getSimpleName()));
        }
        if (!Name.isValidName(bike)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
    }

    /**
     * Throws an {@code IllegalValueException} if {@code rate} does not exist or is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkLoanRateValid() throws IllegalValueException {
        if (rate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LoanRate.class.getSimpleName()));
        }
        if (!LoanRate.isValidRate(rate)) {
            throw new IllegalValueException(LoanRate.MESSAGE_LOANRATE_CONSTRAINTS);
        }
    }

    /**
     * Throws an {@code IllegalValueException} if {@code startTime} does not exist or is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkLoanStartTimeValid() throws IllegalValueException {
        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LoanTime.class.getSimpleName()));
        }
        if (!LoanTime.isValidLoanTime(startTime)) {
            throw new IllegalValueException(LoanTime.MESSAGE_LOANTIME_CONSTRAINTS);
        }
    }

    /**
     * Throws an {@code IllegalValueException} if {@code endTime} does not exist or is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkLoanEndTimeValid() throws IllegalValueException {
        if (endTime == null) {
            return; // Because endTime can be null
        }
        if (!LoanTime.isValidLoanTime(endTime)) {
            throw new IllegalValueException(LoanTime.MESSAGE_LOANTIME_CONSTRAINTS);
        }
    }

    /**
     * Converts this jaxb-friendly adapted loan object into the model's Loan object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted loan
     */
    public Loan toModelType() throws IllegalValueException {
        final List<Tag> loanTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            loanTags.add(tag.toModelType());
        }

        checkNameValid();
        final Name modelName = new Name(name);

        checkNricValid();
        final Nric modelNric = new Nric(nric);

        checkPhoneValid();
        final Phone modelPhone = new Phone(phone);

        checkEmailValid();
        final Email modelEmail = new Email(email);

        checkAddressValid();
        final Address modelAddress = new Address(address);

        checkLoanStatusValid();
        final LoanStatus modelLoanStatus = LoanStatus.valueOf(loanStatus);

        checkBikeValid();
        final Bike modelBike = new Bike(new Name(bike));

        checkLoanRateValid();
        final LoanRate modelRate = new LoanRate(rate);

        checkLoanStartTimeValid();
        final LoanTime modelStartTime = new LoanTime(startTime);

        checkLoanEndTimeValid();
        final LoanTime modelEndTime = endTime == null ? null : new LoanTime(endTime);


        final Set<Tag> modelTags = new HashSet<>(loanTags);
        return new Loan(modelName,
                modelNric,
                modelPhone,
                modelEmail,
                modelAddress,
                modelBike,
                modelRate,
                modelStartTime,
                modelEndTime,
                modelLoanStatus,
                modelTags
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedLoan)) {
            return false;
        }

        XmlAdaptedLoan otherLoan = (XmlAdaptedLoan) other;
        return Objects.equals(name, otherLoan.name)
                && Objects.equals(nric, otherLoan.nric)
                && Objects.equals(phone, otherLoan.phone)
                && Objects.equals(email, otherLoan.email)
                && Objects.equals(address, otherLoan.address)
                && Objects.equals(bike, otherLoan.bike)
                && Objects.equals(rate, otherLoan.rate)
                && Objects.equals(startTime, otherLoan.startTime)
                && Objects.equals(endTime, otherLoan.endTime)
                && tagged.equals(otherLoan.tagged);
    }
}
