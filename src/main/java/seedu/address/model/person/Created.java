package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represent's the timestamp when the Person object was created
 * Guarantees: immutable
 */
public class Created {

    public final String createdAt;

    public Created(Date date) {
        requireNonNull(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setLenient(false);

        this.createdAt = sdf.format(date);
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
