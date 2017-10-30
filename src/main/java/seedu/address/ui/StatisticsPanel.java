package seedu.address.ui;

import java.time.Clock;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SocialMedia;

/**
 * The Statistics Panel of the App.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private static final Integer PERSON_ADDED_EARLIEST_YEAR = 2000;

    private static Clock clock = Clock.systemDefaultZone();

    private Integer totalNumberOfPeople = 0;
    private Integer hasNoFacebook = 0;
    private Integer hasNoTwitter = 0;
    private Integer hasNoInstagram = 0;

    @FXML
    private PieChart pieChart0;
    @FXML
    private PieChart fbChart;
    @FXML
    private PieChart twChart;
    @FXML
    private PieChart igChart;

    public StatisticsPanel(ObservableList<ReadOnlyPerson> list) {
        super(FXML);

        tabulateTotalNumberOfPeople(list);
        tabulateSocialMediaUsage(list);

        pieChart0.setTitle("Record of Persons Added By Year0");
        pieChart0.setData(tabulateAddedByYear(list));

        fbChart.setTitle("% of Persons with Facebook");
        fbChart.setData(formatFacebookData());
        twChart.setTitle("% of Persons with Twitter");
        twChart.setData(formatTwitterData());
        igChart.setTitle("% of Persons with Instagram");
        igChart.setData(formatInstagramData());
    }

    /**
     * Tabulate the total number of people in the list
     */
    private void tabulateTotalNumberOfPeople (ObservableList<ReadOnlyPerson> list) {
        this.totalNumberOfPeople = list.size();
    }

    /**
     * Tabulates number of users of each social media platform
     */
    private void tabulateSocialMediaUsage (ObservableList<ReadOnlyPerson> list) {
        for (ReadOnlyPerson aList : list) {
            SocialMedia current = aList.getSocialMedia();
            if (current.facebook.isEmpty()) this.hasNoFacebook++;
            if (current.twitter.isEmpty()) this.hasNoTwitter++;
            if (current.instagram.isEmpty()) this.hasNoInstagram++;
        }
    }

    /**
     * Formats the number of users with Facebook recorded
     */
    private ObservableList<PieChart.Data> formatFacebookData() {

        ArrayList<PieChart.Data> data = new ArrayList<>();

        int hasFacebook = this.totalNumberOfPeople - this.hasNoFacebook;

        String onFacebookLabel = "On Facebook (" + hasFacebook + ")";
        String notOnFacebookLabel = "Not On Facebook (" + this.hasNoFacebook + ")";
        data.add(new PieChart.Data(onFacebookLabel, hasFacebook));
        data.add(new PieChart.Data(notOnFacebookLabel, this.hasNoFacebook));

        return FXCollections.observableArrayList(data);
    }

    /**
     * Formats the number of users with Twitter recorded
     */
    private ObservableList<PieChart.Data> formatTwitterData() {

        ArrayList<PieChart.Data> data = new ArrayList<>();

        int hasTwitter = this.totalNumberOfPeople - this.hasNoTwitter;

        String onTwitterLabel = "On Twitter (" + hasTwitter + ")";
        String notOnTwitterLabel = "Not On Twitter (" + this.hasNoTwitter + ")";
        data.add(new PieChart.Data(onTwitterLabel, hasTwitter));
        data.add(new PieChart.Data(notOnTwitterLabel, this.hasNoTwitter));

        return FXCollections.observableArrayList(data);
    }

    /**
     * Formats the number of users with Instagram recorded
     */
    private ObservableList<PieChart.Data> formatInstagramData() {

        ArrayList<PieChart.Data> data = new ArrayList<>();

        int hasInstagram = this.totalNumberOfPeople - this.hasNoInstagram;

        String onInstagramLabel = "On Instagram (" + hasInstagram + ")";
        String notOnInstagramLabel = "Not On Instagram (" + this.hasNoInstagram + ")";
        data.add(new PieChart.Data(onInstagramLabel, hasInstagram));
        data.add(new PieChart.Data(notOnInstagramLabel, this.hasNoInstagram));

        return FXCollections.observableArrayList(data);
    }

    /**
     * Formats the data into PieChart.Data for display
     */
    private ObservableList<PieChart.Data> tabulateAddedByYear(ObservableList<ReadOnlyPerson> list) {

        ArrayList<PieChart.Data> data = new ArrayList<>();

        ArrayList<Integer> yearData = collectYear(list);
        for (int y = 0; y < yearData.size(); y++) {
            if (yearData.get(y) > 0) {
                String yearLabel = Integer.toString(y + PERSON_ADDED_EARLIEST_YEAR)
                        + " - " + yearData.get(y);
                data.add(new PieChart.Data(yearLabel, yearData.get(y)));
            }
        }

        return FXCollections.observableArrayList(data);
    }

    /**
     * Collects the observablelist by year
     */
    private ArrayList<Integer> collectYear(ObservableList<ReadOnlyPerson> list) {

        int yearsToCollect = getCurrentYear() - PERSON_ADDED_EARLIEST_YEAR + 1;

        ArrayList<Integer> count = new ArrayList<>(Collections.nCopies(yearsToCollect + 1, 0));

        list.forEach((p) -> {
            Date givenDate = p.getCreatedAt();
            ZonedDateTime given = givenDate.toInstant().atZone(ZoneId.of("UTC"));

            int personAddedYear = Integer.parseInt(Year.from(given).toString());

            if (personAddedYear >= PERSON_ADDED_EARLIEST_YEAR && personAddedYear <= getCurrentYear()) {

                int indexOffset = personAddedYear - PERSON_ADDED_EARLIEST_YEAR;
                int oldValue = count.get(indexOffset);
                count.set(indexOffset, oldValue + 1);
            }
        });

        return count;
    }
    /**
     * Fetches the current year
     */
    private Integer getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
