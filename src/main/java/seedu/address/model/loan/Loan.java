package seedu.address.model.loan;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.UniqueListItem;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.exceptions.SameLoanStatusException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Loan in the loan book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Loan implements UniqueListItem<Loan> {

    // Identity fields
    private final Bike bike;
    private final Name name;
    private final Nric nric;
    //TODO: add LoanId filed
    //private final LoanId id;

    // Data fields
    private final LoanRate rate;
    private final LoanTime startTime;
    private final LoanTime endTime; // Note that endTime can be null
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private LoanStatus loanStatus;

    /**
     * Every field must be present and not null.
     * Old constructor that does not take into account the LoanStatus.
     */
    public Loan(Name name,
                Nric nric,
                Phone phone,
                Email email,
                Address address,
                Bike bike,
                LoanRate rate,
                LoanTime startTime,
                LoanTime endTime,
                LoanStatus loanStatus,
                Set<Tag> tags) {
        // Note that endTime can be null. This loans in progress do not have an endTime.
        requireAllNonNull(name, nric, phone, email, address, bike, rate, startTime, tags);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.bike = bike;
        this.rate = rate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags.addAll(tags);

        // Initialise the loan to be ongoing.
        this.loanStatus = loanStatus;
    }

    /**
     * Every field must be present and not null.
     * This constructor is used for the add command
     */
    public Loan(Name name,
                Nric nric,
                Phone phone,
                Email email,
                Address address,
                Bike bike,
                LoanRate rate,
                Set<Tag> tags) {
        this(name, nric, phone, email, address, bike, rate,
                new LoanTime(), null, LoanStatus.ONGOING, tags);
    }

    /**
     * Every field must be present and not null.
     * This constructor is used when you know the start and end times.
     * If you know the end time, then the loan would be returned.
     */
    public Loan(Name name,
                Nric nric,
                Phone phone,
                Email email,
                Address address,
                Bike bike,
                LoanRate rate,
                LoanTime startTime,
                LoanTime endTime,
                Set<Tag> tags) {
        this(name, nric, phone, email, address, bike, rate,
                startTime, endTime, LoanStatus.RETURNED, tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public Nric getNric() {
        return nric;
    }

    public Bike getBike() {
        return bike;
    }

    public LoanRate getLoanRate() {
        return rate;
    }

    public LoanTime getLoanStartTime() {
        return startTime;
    }

    public LoanTime getLoanEndTime() {
        return endTime;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Change the loan status to the newStatus as provided.
     * Throws SameLoanStatusException if the newStatus is the same as the previous status.
     * @param newStatus
     * @return true if the function managed to complete.
     * @throws SameLoanStatusException
     */
    public boolean changeLoanStatus(LoanStatus newStatus) throws SameLoanStatusException {
        if (loanStatus.equals(newStatus)) {
            throw new SameLoanStatusException();
        } else {
            loanStatus = newStatus;
            return true;
        }
    }

    /**
     * Returns true if both loans of the same name have at least one other identity field that is the same.
     */
    @Override
    public boolean isSame(Loan other) {
        if (other == this) {
            return true;
        }

        return other != null
                && other.getName().equals(getName())
                && other.getNric().equals(getNric())
                && other.getBike().equals(getBike())
                && (other.getEmail().equals(getEmail()) || other.getPhone().equals(getPhone())
                || other.getLoanRate().equals(getLoanRate()) || other.getLoanStartTime().equals(getLoanStartTime()));
    }

    /**
     * Returns true if both loans have the same identity and data fields.
     * This defines a stronger notion of equality between two loans.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Loan)) {
            return false;
        }

        Loan otherLoan = (Loan) other;
        return otherLoan.getName().equals(getName())
                && otherLoan.getNric().equals(getNric())
                && otherLoan.getPhone().equals(getPhone())
                && otherLoan.getEmail().equals(getEmail())
                && otherLoan.getAddress().equals(getAddress())
                && otherLoan.getLoanStatus().equals(getLoanStatus())
                && otherLoan.getBike().equals(getBike())
                && otherLoan.getLoanRate().equals(getLoanRate())
                && otherLoan.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, phone, email, address, bike, rate, startTime, endTime, loanStatus, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Nric: ")
                .append(getNric())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Status: ")
                .append(getLoanStatus())
                .append(" Bike: ")
                .append(getBike())
                .append(" LoanRate: ")
                .append(getLoanRate())
                .append(" LoanStartTime: ")
                .append(getLoanStartTime())
                .append(" LoanEndTime: ")
                .append(getLoanEndTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
