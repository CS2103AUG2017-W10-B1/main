package seedu.address.model.person;

import java.util.Date;

import static java.util.Objects.requireNonNull;

public class Created {

    public final Date createdAt;

    public Created(Date date) {
        requireNonNull(date);

        this.createdAt = date;
    }

    @Override
    public String toString() {
        return createdAt.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.createdAt.equals(other));
    }

    @Override
    public int hashCode() {
        return createdAt.hashCode();
    }

}
