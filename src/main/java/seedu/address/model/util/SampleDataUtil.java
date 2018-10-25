package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.LoanBook;
import seedu.address.model.ReadOnlyLoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Address;
import seedu.address.model.loan.Email;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanRate;
import seedu.address.model.loan.LoanTime;
import seedu.address.model.loan.Name;
import seedu.address.model.loan.Nric;
import seedu.address.model.loan.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code LoanBook} with sample data.
 */
public class SampleDataUtil {
    public static Loan[] getSampleLoans() {
        return new Loan[] {
            new Loan(new Name("Alex Yeoh"), new Nric("S9013904E"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Bike(new Name("Bike001")), new LoanRate("12.9"), new LoanTime("2010-12-25 04:09"),
                    new LoanTime("2010-12-25 05:09"), getTagSet("friends")),
            new Loan(new Name("Bernice Yu"), new Nric("T0213176A"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Bike(new Name("Bike002")), new LoanRate("1.95"), new LoanTime("2010-12-25 14:09"),
                    new LoanTime("2010-12-26 08:34"), getTagSet("colleagues", "friends")),
            new Loan(new Name("Charlotte Oliveiro"), new Nric("F9576390K"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Bike(new Name("Silver Surfer")), new LoanRate("3.90"), new LoanTime("04:55"),
                    new LoanTime("09:42"), getTagSet("neighbours")),
            new Loan(new Name("David Li"), new Nric("G0846554T"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Bike(new Name("Blue Ocean")), new LoanRate("33"), new LoanTime("12:21"),
                    new LoanTime("19:21"), getTagSet("family")),
            new Loan(new Name("Irfan Ibrahim"), new Nric("S8830104H"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Bike(new Name("Bike013")), new LoanRate("6.7"), new LoanTime("2018-01-30 15:10"),
                    new LoanTime("2018-01-31 15:10"), getTagSet("classmates")),
            new Loan(new Name("Roy Balakrishnan"), new Nric("S7588900C"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Bike(new Name("Bike099")), new LoanRate("3.95"), new LoanTime("2018-10-20 20:10"),
                    new LoanTime("2018-01-30 11:17"), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyLoanBook getSampleLoanBook() {
        LoanBook sampleAb = new LoanBook();
        for (Loan sampleLoan : getSampleLoans()) {
            sampleAb.addLoan(sampleLoan);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
