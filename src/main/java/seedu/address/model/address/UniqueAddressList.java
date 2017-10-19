package seedu.address.model.address;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of addresses that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Address#equals(Object)
 */
public class UniqueAddressList implements Iterable<Address> {

    private final ObservableList<Address> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AddressList.
     */
    public UniqueAddressList() {}

    /**
     * Creates a UniqueAddressList using given Addresses.
     * Enforces no nulls.
     */
    public UniqueAddressList(Set<Address> Addresses) {
        requireAllNonNull(Addresses);
        internalList.addAll(Addresses);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Addresses in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Address> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Addresses in this list with those in the argument Address list.
     */
    public void setAddresses(Set<Address> Addresses) {
        requireAllNonNull(Addresses);
        internalList.setAll(Addresses);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every Address in the argument list exists in this object.
     */
    public void mergeFrom(UniqueAddressList from) {
        final Set<Address> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(Address -> !alreadyInside.contains(Address))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Address as the given argument.
     */
    public boolean contains(Address toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Address to the list.
     *
     * @throws DuplicateAddressException if the Address to add is a duplicate of an existing Address in the list.
     */
    public void add(Address toAdd) throws DuplicateAddressException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAddressException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Address> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Address> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueAddressList // instanceof handles nulls
                && this.internalList.equals(((UniqueAddressList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueAddressList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateAddressException extends DuplicateDataException {
        protected DuplicateAddressException() {
            super("Operation would result in duplicate Addresses");
        }
    }

}
