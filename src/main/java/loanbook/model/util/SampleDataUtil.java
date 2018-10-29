package loanbook.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import loanbook.model.LoanBook;
import loanbook.model.ReadOnlyLoanBook;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Email;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanRate;
import loanbook.model.loan.LoanTime;
import loanbook.model.loan.Name;
import loanbook.model.loan.Nric;
import loanbook.model.loan.Phone;
import loanbook.model.tag.Tag;

/**
 * Contains utility methods for populating {@code LoanBook} with sample data.
 */
public class SampleDataUtil {
    public static Loan[] getSampleLoans() {
        return new Loan[] {
            new Loan(new Name("Alex Yeoh"), new Nric("S9013904E"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Bike(new Name("Bike001")), new LoanRate("12.9"), new LoanTime("2010-12-25 04:09"),
                    new LoanTime("2010-12-25 05:09"), getTagSet("friends")),
            new Loan(new Name("Bernice Yu"), new Nric("T0213176A"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Bike(new Name("Bike002")), new LoanRate("1.95"), new LoanTime("2010-12-25 14:09"),
                    new LoanTime("2010-12-26 08:34"), getTagSet("colleagues", "friends")),
            new Loan(new Name("Charlotte Oliveiro"), new Nric("F9576390K"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Bike(new Name("Silver Surfer")), new LoanRate("3.90"), new LoanTime("04:55"),
                    new LoanTime("09:42"), getTagSet("neighbours")),
            new Loan(new Name("David Li"), new Nric("G0846554T"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Bike(new Name("Blue Ocean")), new LoanRate("33"), new LoanTime("12:21"),
                    new LoanTime("19:21"), getTagSet("family")),
            new Loan(new Name("Irfan Ibrahim"), new Nric("S8830104H"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Bike(new Name("Bike013")), new LoanRate("6.7"), new LoanTime("2018-01-30 15:10"),
                    new LoanTime("2018-01-31 15:10"), getTagSet("classmates")),
            new Loan(new Name("Roy Balakrishnan"), new Nric("S7588900C"), new Phone("92624417"),
                    new Email("royb@example.com"),
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
