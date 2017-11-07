// @@author donjar

package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the font size.
 */
public class FontSizeChangeRequestEvent extends BaseEvent {

    public final int sizeChange;

    public FontSizeChangeRequestEvent(int sizeChange) {
        this.sizeChange = sizeChange;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
