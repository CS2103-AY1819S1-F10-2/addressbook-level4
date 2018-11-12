package loanbook.testutil;

import static loanbook.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_LOANENDTIME_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_LOANENDTIME_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_LOANRATE_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_LOANRATE_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_LOANSTARTTIME_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_LOANSTARTTIME_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static loanbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import loanbook.model.loan.Loan;

/**
 * A utility class containing a list of {@code Loan} objects to be used in tests.
 */
public class TypicalLoans {

    public static final Loan ALICE = new LoanBuilder()
            .withLoanId("0")
            .withName("Alice Pauline")
            .withNric("S0848937H")
            .withPhone("94351253")
            .withBike(VALID_NAME_BIKE1)
            .withLoanRate("1.1")
            .withEmail("alice@example.com")
            .withLoanStartTime("12:33")
            .withLoanEndTime("23:54")
            .withLoanStatus("ONGOING")
            .withTags("friends")
            .build();
    public static final Loan BENSON = new LoanBuilder()
            .withLoanId("1")
            .withName("Benson Meier")
            .withNric("F1342714M")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withBike(VALID_NAME_BIKE1)
            .withLoanRate("0.15")
            .withLoanStartTime("2017-10-12 06:08")
            .withLoanStatus("ONGOING")
            .withTags("owesMoney", "friends")
            .build();
    public static final Loan CARL = new LoanBuilder()
            .withLoanId("2")
            .withName("Carl Kurz")
            .withNric("T0238282I")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withBike(VALID_NAME_BIKE1)
            .withLoanRate("23.9")
            .withLoanStartTime("14:20")
            .withLoanStatus("ONGOING")
            .build();
    public static final Loan DANIEL = new LoanBuilder()
            .withLoanId("3")
            .withName("Daniel Meier")
            .withNric("S9335895C")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withBike(VALID_NAME_BIKE2)
            .withLoanRate("9.0")
            .withLoanStartTime("17:56")
            .withLoanEndTime("23:54")
            .withLoanStatus("ONGOING")
            .withTags("friends")
            .build();
    public static final Loan ELLE = new LoanBuilder()
            .withLoanId("4")
            .withName("Elle Meyer")
            .withNric("G9112925L")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withBike(VALID_NAME_BIKE2)
            .withLoanRate("5.55")
            .withLoanStartTime("2010-12-30 10:30")
            .withLoanEndTime("2010-12-30 23:54")
            .withLoanStatus("RETURNED")
            .build();
    public static final Loan FIONA = new LoanBuilder()
            .withLoanId("5")
            .withName("Fiona Kunz")
            .withNric("G9400645M")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withBike(VALID_NAME_BIKE2)
            .withLoanRate("33.3")
            .withLoanStartTime("01:01")
            .withLoanEndTime("23:54")
            .withLoanStatus("ONGOING")
            .build();
    public static final Loan GEORGE = new LoanBuilder()
            .withLoanId("6")
            .withName("George Best")
            .withNric("S8313623E")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withBike(VALID_NAME_BIKE2)
            .withLoanRate("5.05")
            .withLoanStartTime("2013-03-13 12:08")
            .withLoanEndTime("2013-03-13 23:54")
            .withLoanStatus("RETURNED")
            .build();

    // Manually added
    public static final Loan HOON = new LoanBuilder()
            .withLoanId("10")
            .withName("Hoon Meier")
            .withNric("T0127601D")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withBike(VALID_NAME_BIKE2)
            .withLoanRate("8.9")
            .withLoanStartTime("16:45")
            .withLoanEndTime("23:54")
            .withLoanStatus("ONGOING")
            .build();
    public static final Loan IDA = new LoanBuilder()
            .withLoanId("11")
            .withName("Ida Mueller")
            .withNric("S9739813E")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withBike(VALID_NAME_BIKE2)
            .withLoanRate("7.65")
            .withLoanStartTime("2017-09-08 18:08")
            .withLoanEndTime("2017-09-08 23:54")
            .withLoanStatus("RETURNED")
            .build();

    // Manually added - Loan's details found in {@code CommandTestUtil}
    public static final Loan AMY = new LoanBuilder().withName(VALID_NAME_AMY)
            .withNric(VALID_NRIC_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withTags(VALID_TAG_FRIEND)
            .withBike(VALID_NAME_BIKE1)
            .withLoanRate(VALID_LOANRATE_AMY)
            .withLoanStartTime(VALID_LOANSTARTTIME_AMY)
            .withLoanEndTime(VALID_LOANENDTIME_AMY)
            .build();
    public static final Loan BOB = new LoanBuilder().withName(VALID_NAME_BOB)
            .withNric(VALID_NRIC_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withBike(VALID_NAME_BIKE2)
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
