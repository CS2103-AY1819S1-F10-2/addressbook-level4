package loanbook.model.loan;

import java.util.List;
import java.util.function.Predicate;

import loanbook.commons.util.StringUtil;

/**
 * Tests that a {@code Loan}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Loan> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Loan loan) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(loan.getName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
