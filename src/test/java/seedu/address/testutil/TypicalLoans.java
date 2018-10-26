package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIKE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIKE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOANENDTIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOANENDTIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOANRATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOANRATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOANSTARTTIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOANSTARTTIME_BOB;
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

import seedu.address.model.loan.Loan;

/**
 * A utility class containing a list of {@code Loan} objects to be used in tests.
 */
public class TypicalLoans {

    public static final Loan ALICE = new LoanBuilder().withName("Alice Pauline")
            .withNric("S0848937H")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withBike("Bike001")
            .withLoanRate("1.1")
            .withLoanStartTime("12:33")
            .withLoanEndTime("23:54")
            .withTags("friends").build();
    public static final Loan BENSON = new LoanBuilder().withName("Benson Meier")
            .withNric("F1342714M")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withBike("Bike002")
            .withLoanRate("0.15")
            .withLoanStartTime("2017-10-12 06:08")
            .withLoanEndTime("2017-10-12 23:54")
            .withTags("owesMoney", "friends").build();
    public static final Loan CARL = new LoanBuilder().withName("Carl Kurz")
            .withNric("T0238282I")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withBike("Bike003")
            .withLoanRate("23.9")
            .withLoanStartTime("14:20")
            .withLoanEndTime("23:54")
            .build();
    public static final Loan DANIEL = new LoanBuilder().withName("Daniel Meier")
            .withNric("S9335895C")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withBike("Bike004")
            .withLoanRate("9.0")
            .withLoanStartTime("17:56")
            .withLoanEndTime("23:54")
            .withTags("friends").build();
    public static final Loan ELLE = new LoanBuilder().withName("Elle Meyer")
            .withNric("G9112925L")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withBike("Bike005")
            .withLoanRate("5.55")
            .withLoanStartTime("2010-12-30 10:30")
            .withLoanEndTime("2010-12-30 23:54")
            .build();
    public static final Loan FIONA = new LoanBuilder().withName("Fiona Kunz")
            .withNric("G9400645M")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withBike("Bike006")
            .withLoanRate("33.3")
            .withLoanStartTime("01:01")
            .withLoanEndTime("23:54")
            .build();
    public static final Loan GEORGE = new LoanBuilder().withName("George Best")
            .withNric("S8313623E")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withBike("Bike007")
            .withLoanRate("5.05")
            .withLoanStartTime("2013-03-13 12:08")
            .withLoanEndTime("2013-03-13 23:54")
            .build();

    // Manually added
    public static final Loan HOON = new LoanBuilder().withName("Hoon Meier")
            .withNric("T0127601D")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withBike("Bike008")
            .withLoanRate("8.9")
            .withLoanStartTime("16:45")
            .withLoanEndTime("23:54")
            .build();
    public static final Loan IDA = new LoanBuilder().withName("Ida Mueller")
            .withNric("S9739813E")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withBike("Bike009")
            .withLoanRate("7.65")
            .withLoanStartTime("2017-09-08 18:08")
            .withLoanEndTime("2017-09-08 23:54")
            .build();

    // Manually added - Loan's details found in {@code CommandTestUtil}
    public static final Loan AMY = new LoanBuilder().withName(VALID_NAME_AMY)
            .withNric(VALID_NRIC_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND)
            .withBike(VALID_BIKE_AMY)
            .withLoanRate(VALID_LOANRATE_AMY)
            .withLoanStartTime(VALID_LOANSTARTTIME_AMY)
            .withLoanEndTime(VALID_LOANENDTIME_AMY)
            .build();
    public static final Loan BOB = new LoanBuilder().withName(VALID_NAME_BOB)
            .withNric(VALID_NRIC_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withBike(VALID_BIKE_BOB)
            .withLoanRate(VALID_LOANRATE_BOB)
            .withLoanStartTime(VALID_LOANSTARTTIME_BOB)
            .withLoanEndTime(VALID_LOANENDTIME_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalLoans() {} // prevents instantiation

    public static List<Loan> getTypicalLoans() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
