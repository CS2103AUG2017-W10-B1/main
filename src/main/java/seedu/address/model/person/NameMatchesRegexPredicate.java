package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameMatchesRegexPredicate implements Predicate<ReadOnlyPerson> {
    private final Pattern pattern;

    public NameMatchesRegexPredicate(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return pattern.matcher(person.getName().fullName).matches();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameMatchesRegexPredicate // instanceof handles nulls
                && this.pattern.toString().equals(((NameMatchesRegexPredicate) other).pattern.toString())); // state check
    }

}
