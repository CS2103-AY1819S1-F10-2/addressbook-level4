package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.loan.Loan;

/**
 * A utility class containing a list of {@code Loan} objects to be used in tests.
 */
public class TypicalLoans {

    public static final Loan ALICE = new LoanBuilder().withName("Alice Pauline").withNric("S1020304A")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Loan BENSON = new LoanBuilder().withName("Benson Meier").withNric("S1234567B")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Loan CARL = new LoanBuilder().withName("Carl Kurz").withPhone("95352563").withNric("S1234567C")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Loan DANIEL = new LoanBuilder().withName("Daniel Meier").withNric("S1234567D")
            .withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Loan ELLE = new LoanBuilder().withName("Elle Meyer").withPhone("9482224").withNric("S1234567E")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Loan FIONA = new LoanBuilder().withName("Fiona Kunz").withPhone("9482427").withNric("S1234567F")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Loan GEORGE = new LoanBuilder().withName("George Best").withNric("S1234567G")
            .withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Loan HOON = new LoanBuilder().withName("Hoon Meier").withPhone("8482424").withNric("S1234567H")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Loan IDA = new LoanBuilder().withName("Ida Mueller").withPhone("8482131").withNric("S1234567J")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Loan's details found in {@code CommandTestUtil}
    public static final Loan AMY = new LoanBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Loan BOB = new LoanBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalLoans() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical loans.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Loan loan : getTypicalLoans()) {
            ab.addLoan(loan);
        }
        return ab;
    }

    public static List<Loan> getTypicalLoans() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}