package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Loan objects.
 */
public class LoanBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_NRIC = "G1234567X";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BIKE = "BIKE999";
    public static final String DEFAULT_LOANRATE = "12.3";
    public static final String DEFAULT_LOANTIME = "12:45";
    public static final String DEFAULT_LOANSTATUS = "ONGOING";

    private Name name;
    private Nric nric;
    private Phone phone;
    private Email email;
    private Address address;
    private Bike bike;
    private LoanRate rate;
    private LoanTime time;
    private Set<Tag> tags;
    private LoanStatus loanStatus;

    public LoanBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        bike = new Bike(new Name(DEFAULT_BIKE));
        rate = new LoanRate(DEFAULT_LOANRATE);
        time = new LoanTime(DEFAULT_LOANTIME);
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
        address = loanToCopy.getAddress();
        bike = loanToCopy.getBike();
        rate = loanToCopy.getLoanRate();
        time = loanToCopy.getLoanTime();
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
     * Sets the {@code Address} of the {@code Loan} that we are building.
     */
    public LoanBuilder withAddress(String address) {
        this.address = new Address(address);
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
    public LoanBuilder withBike(String bike) {
        this.bike = new Bike(new Name(bike));
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
     */
    public LoanBuilder withLoanTime(String time) {
        this.time = new LoanTime(time);
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
        return new Loan(name, nric, phone, email, address, bike, rate, time, loanStatus, tags);
    }
}
