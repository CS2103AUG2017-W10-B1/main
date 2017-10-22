package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's social media usernames in the address book.
 */
public class SocialMedia {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Social media username should be alphanumeric without spaces";
    public static final String USERNAME_VALIDATION_REGEX = "[\\w\\S\\.]+";

    public final String facebook;
    public final String twitter;
    public final String instagram;

    /**
     * All usernames are empty.
     */
    public SocialMedia() {
        facebook = "";
        twitter = "";
        instagram = "";
    }

    /**
     * Validates given usernames.
     *
     * @throws IllegalValueException if either of given username string is invalid.
     */
    public SocialMedia(String facebook, String twitter, String instagram) throws IllegalValueException {
        if(facebook == null) {
            facebook = "";
        }

        if(twitter == null) {
            twitter = "";
        }

        if(instagram == null) {
            instagram = "";
        }

        if(!isValidName(facebook) || !isValidName(twitter) || !isValidName(instagram)) {
            throw new IllegalValueException(MESSAGE_USERNAME_CONSTRAINTS);
        }

        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
    }

    /**
     * Create an object using an old one, and replace usernames if args are not null.
     *
     * @throws IllegalValueException if either of given username string is invalid.
     */
    public SocialMedia(SocialMedia old, String facebook, String twitter, String instagram) throws IllegalValueException {
        requireNonNull(old);

        if(facebook != null) {
            if(!isValidName(facebook)) {
                throw new IllegalValueException(MESSAGE_USERNAME_CONSTRAINTS);
            }
            this.facebook = facebook;
        }
        else {
            this.facebook = old.facebook;
        }

        if(twitter != null) {
            if(!isValidName(twitter)) {
                throw new IllegalValueException(MESSAGE_USERNAME_CONSTRAINTS);
            }
            this.twitter = twitter;
        }
        else {
            this.twitter = old.twitter;
        }

        if(instagram != null) {
            if(!isValidName(instagram)) {
                throw new IllegalValueException(MESSAGE_USERNAME_CONSTRAINTS);
            }
            this.instagram = instagram;
        }
        else {
            this.instagram = old.instagram;
        }
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "Facebook: " + facebook + " Twitter: " + twitter + " Instagram: " + instagram;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SocialMedia // instanceof handles nulls
                && this.facebook.equals(((SocialMedia) other).facebook)
                && this.twitter.equals(((SocialMedia) other).twitter)
                && this.instagram.equals(((SocialMedia) other).instagram)); // state check
    }
}
