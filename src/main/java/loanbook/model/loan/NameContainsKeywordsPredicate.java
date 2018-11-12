package loanbook.model.loan;

import java.util.List;
import java.util.function.Predicate;

import loanbook.commons.util.StringUtil;
import loanbook.model.bike.Bike;

/**
 * Tests that a {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Name> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Name name) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(name.value, keyword));
    }

    public Predicate<Bike> forBikes() {
        return bike -> test(bike.getName());
    }

    public Predicate<Loan> forLoans() {
        return loan -> test(loan.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
