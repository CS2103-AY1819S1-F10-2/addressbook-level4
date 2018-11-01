package loanbook.testutil;

import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;

import java.util.HashSet;
import java.util.Set;

import loanbook.model.bike.Bike;
import loanbook.model.loan.Email;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanRate;
import loanbook.model.loan.LoanStatus;
import loanbook.model.loan.LoanTime;
import loanbook.model.loan.Name;
import loanbook.model.loan.Nric;
import loanbook.model.loan.Phone;
import loanbook.model.tag.Tag;
import loanbook.model.util.SampleDataUtil;

/**
 * A utility class to help with building Loan objects.
 */
public class LoanBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_NRIC = "G1234567X";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_BIKE = VALID_NAME_BIKE1;
    public static final String DEFAULT_LOANRATE = "12.3";
    public static final String DEFAULT_LOANSTARTTIME = "11:45";
    public static final String DEFAULT_LOANENDTIME = "12:45";
    public static final String DEFAULT_LOANSTATUS = "ONGOING";

    private Name name;
    private Nric nric;
    private Phone phone;
    private Email email;
    private Bike bike;
    private LoanRate rate;
    private LoanTime startTime;
    private LoanTime endTime;
    private Set<Tag> tags;
    private LoanStatus loanStatus;

    public LoanBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        bike = new Bike(new Name(DEFAULT_BIKE));
        rate = new LoanRate(DEFAULT_LOANRATE);
        startTime = new LoanTime(DEFAULT_LOANSTARTTIME);
        endTime = new LoanTime(DEFAULT_LOANENDTIME);
        loanStatus = LoanStatus.valueOf(DEFAULT_LOANSTATUS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the LoanBuilder with the data of {@code loanToCopy}.
     */
    public LoanBuilder(Loan loanToCopy) {
        name = loanToCopy.getName();
        nric = loanToCopy.getNric();
        phone = loanToCopy.getPhone();
        email = loanToCopy.getEmail();
        bike = loanToCopy.getBike();
        rate = loanToCopy.getLoanRate();
        startTime = loanToCopy.getLoanStartTime();
        endTime = loanToCopy.getLoanEndTime();
        loanStatus = loanToCopy.getLoanStatus();
        tags = new HashSet<>(loanToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Loan} that we are building.
     */
    public LoanBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Loan} that we are building.
     */
    public LoanBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Loan} that we are building.
     */
    public LoanBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Loan} that we are building.
     */
    public LoanBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Loan} that we are building.
     */
    public LoanBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Bike} of the {@code Loan} that we are building.
     */
    public LoanBuilder withBike(String bikeName) {
        this.bike = new Bike(new Name(bikeName));
        return this;
    }

    /**
     * Sets the {@code LoanRate} of the {@code Loan} that we are building.
     */
    public LoanBuilder withLoanRate(String rate) {
        this.rate = new LoanRate(rate);
        return this;
    }

    /**
     * Sets the {@code LoanTime} of the {@code Loan} that we are building.
     * This method affects the start time of the loan
     */
    public LoanBuilder withLoanStartTime(String time) {
        this.startTime = new LoanTime(time);
        return this;
    }

    /**
     * Sets the {@code LoanTime} of the {@code Loan} that we are building.
     * This method affects the end time of the loan
     */
    public LoanBuilder withLoanEndTime(String time) {
        this.endTime = new LoanTime(time);
        return this;
    }

    /**
     * Sets the {@code loanStatus} of the {@code Loan} that we are building.
     */
    public LoanBuilder withLoanStatus(String loanStatus) {
        this.loanStatus = LoanStatus.valueOf(loanStatus);
        return this;
    }

    public Loan build() {
        return new Loan(name, nric, phone, email, bike, rate, startTime, endTime, loanStatus, tags);
    }
}
