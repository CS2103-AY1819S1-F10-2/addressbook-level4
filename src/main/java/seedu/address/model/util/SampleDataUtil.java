package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.loan.Address;
import seedu.address.model.loan.Email;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.Name;
import seedu.address.model.loan.Nric;
import seedu.address.model.loan.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Loan[] getSampleLoans() {
        return new Loan[] {
            new Loan(new Name("Alex Yeoh"), new Nric("S1234567A"), new Phone("87438807"), new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"), getTagSet("friends")
            ),
            new Loan(new Name("Bernice Yu"), new Nric("S1234567B"), new Phone("99272758"), new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), getTagSet("colleagues", "friends")
            ),
            new Loan(new Name("Charlotte Oliveiro"), new Nric("T7654321Z"), new Phone("93210283"), new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("neighbours")
            ),
            new Loan(new Name("David Li"), new Nric("S1200007A"), new Phone("91031282"), new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("family")
            ),
            new Loan(new Name("Irfan Ibrahim"), new Nric("S1234567G"), new Phone("92492021"), new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates")
            ),
            new Loan(new Name("Roy Balakrishnan"), new Nric("S1234567F"), new Phone("92624417"), new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"), getTagSet("colleagues")
            )
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
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
