package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.loan.Address;
import seedu.address.model.loan.Email;
import seedu.address.model.loan.Loan;
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
    public static final String DEFAULT_NRIC = "S1234567A";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Nric nric;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public LoanBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the LoanBuilder with the data of {@code loanToCopy}.
     */
    public LoanBuilder(Loan loanToCopy) {
        name = loanToCopy.getLoanerName();
        nric = loanToCopy.getLoanerNric();
        phone = loanToCopy.getLoanerPhone();
        email = loanToCopy.getLoanerEmail();
        address = loanToCopy.getAddress();
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

    public Loan build() {
        return new Loan(name, nric, phone, email, address, tags);
    }

}
