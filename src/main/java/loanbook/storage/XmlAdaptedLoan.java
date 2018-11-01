package loanbook.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import loanbook.commons.exceptions.IllegalValueException;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Email;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.LoanRate;
import loanbook.model.loan.LoanStatus;
import loanbook.model.loan.LoanTime;
import loanbook.model.loan.Name;
import loanbook.model.loan.Nric;
import loanbook.model.loan.Phone;
import loanbook.model.tag.Tag;

/**
 * JAXB-friendly version of the Loan.
 */
public class XmlAdaptedLoan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Loan's %s field is missing!";

    @XmlElement(required = true)
    private XmlAdaptedLoanId id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String nric;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
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
    public XmlAdaptedLoan(XmlAdaptedLoanId id,
                          String name,
                          String nric,
                          String phone,
                          String email,
                          String bike,
                          String rate,
                          String startTime,
                          String endTime,
                          String loanStatus,
                          List<XmlAdaptedTag> tagged) {
        this.id = id;
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
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
                          String bike,
                          String rate,
                          String time,
                          List<XmlAdaptedTag> tagged) {
        this(null, name, nric, phone, email, bike, rate, time, null, "ONGOING", tagged);
    }

    /**
     * Constructs an {@code XmlAdaptedLoan} with the given loan details.
     * This constructor is called if no loanStatus is given
     */
    public XmlAdaptedLoan(String name,
                          String nric,
                          String phone,
                          String email,
                          String bike,
                          String rate,
                          String startTime,
                          String endTime,
                          List<XmlAdaptedTag> tagged) {
        this(null, name, nric, phone, email, bike, rate, startTime, endTime, "ONGOING", tagged);
    }

    /**
     * Converts a given Loan into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLoan
     */
    public XmlAdaptedLoan(Loan source) {
        id = new XmlAdaptedLoanId(source.getLoanId());
        name = source.getName().value;
        nric = source.getNric().nric;
        phone = source.getPhone().value;
        email = source.getEmail().value;
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
     * Throws an {@code IllegalValueException} if {@code field} does not exist or is not valid.
     *
     * @param field The data field of the Loan class to check for.
     * @param fieldClass The class which the data field should belong to.
     * @param isValid A predicate to check if {@code field} is valid.
     * @param msgConstraints A message to display to the user if {@code field} is not valid.
     *
     * @throws IllegalValueException
     */
    private void checkFieldValid(
            String field,
            Class fieldClass,
            Predicate<String> isValid,
            String msgConstraints) throws IllegalValueException {

        if (field == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, fieldClass.getSimpleName()));
        }
        if (!isValid.test(field)) {
            throw new IllegalValueException(msgConstraints);
        }
    }

    /**
     * Throws an {@link IllegalValueException} if {@code id} does not exist.
     *
     * @throws IllegalValueException
     */
    private void checkLoanIdValid() throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    XmlAdaptedLoanId.class.getSimpleName()));
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

        checkLoanIdValid();
        final LoanId modelId = id.toModelType();

        checkFieldValid(name, Name.class, Name::isValidName, Name.MESSAGE_NAME_CONSTRAINTS);
        final Name modelName = new Name(name);

        checkFieldValid(nric, Nric.class, Nric::isValidNric, Nric.MESSAGE_NRIC_CONSTRAINTS);
        final Nric modelNric = new Nric(nric);

        checkFieldValid(phone, Phone.class, Phone::isValidPhone, Phone.MESSAGE_PHONE_CONSTRAINTS);
        final Phone modelPhone = new Phone(phone);

        checkFieldValid(email, Email.class, Email::isValidEmail, Email.MESSAGE_EMAIL_CONSTRAINTS);
        final Email modelEmail = new Email(email);

        checkFieldValid(loanStatus, LoanStatus.class, LoanStatus::isValidLoanStatus,
            LoanStatus.MESSAGE_LOANSTATUS_CONSTRAINTS);
        final LoanStatus modelLoanStatus = LoanStatus.valueOf(loanStatus);

        checkFieldValid(bike, Bike.class, Name::isValidName, Name.MESSAGE_NAME_CONSTRAINTS);
        final Bike modelBike = new Bike(new Name(bike));

        checkFieldValid(rate, LoanRate.class, LoanRate::isValidRate, LoanRate.MESSAGE_LOANRATE_CONSTRAINTS);
        final LoanRate modelRate = new LoanRate(rate);

        checkLoanStartTimeValid();
        final LoanTime modelStartTime = new LoanTime(startTime);

        checkLoanEndTimeValid();
        final LoanTime modelEndTime = endTime == null ? null : new LoanTime(endTime);

        final Set<Tag> modelTags = new HashSet<>(loanTags);
        return new Loan(modelId,
                modelName,
                modelNric,
                modelPhone,
                modelEmail,
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
        return Objects.equals(id, otherLoan.id)
                && Objects.equals(name, otherLoan.name)
                && Objects.equals(nric, otherLoan.nric)
                && Objects.equals(phone, otherLoan.phone)
                && Objects.equals(email, otherLoan.email)
                && Objects.equals(bike, otherLoan.bike)
                && Objects.equals(rate, otherLoan.rate)
                && Objects.equals(startTime, otherLoan.startTime)
                && Objects.equals(endTime, otherLoan.endTime)
                && tagged.equals(otherLoan.tagged);
    }
}
