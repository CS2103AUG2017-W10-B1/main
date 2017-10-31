package seedu.address.ui;

import java.time.Month;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SocialMedia;

/**
 * The Statistics Panel of the App.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private static final Integer PERSON_ADDED_DISPLAY_YEARS = 2;

    private static final String FACEBOOK_BREAKDOWN_CHART_TITLE = "% of Persons with Facebook";
    private static final String INSTAGRAM_BREAKDOWN_CHART_TITLE = "% of Persons with Instagram";
    private static final String TWITTER_BREAKDOWN_CHART_TITLE = "% of Persons with Twitter";

    private static final Double PERSON_ADDED_CHART_BAR_GAP = 0.1;
    private static final String PERSON_ADDED_CHART_TITLE = "Persons Added in Recent Months";

    private Integer totalNumberOfPeople = 0;
    private Integer hasNoFacebook = 0;
    private Integer hasNoTwitter = 0;
    private Integer hasNoInstagram = 0;

    private Integer currentYear;
    private Integer currentMonth;

    @FXML
    private BarChart personAddedChart;
    @FXML
    private PieChart fbChart;
    @FXML
    private PieChart twChart;
    @FXML
    private PieChart igChart;

    private void initialiseStatisticsPanel(ObservableList<ReadOnlyPerson> list) {
        tabulateTotalNumberOfPeople(list);
        tabulateSocialMediaUsage(list);

        personAddedChart.setTitle(PERSON_ADDED_CHART_TITLE);
        personAddedChart.setData(getPersonAddedChartData(list));
        personAddedChart.setBarGap(PERSON_ADDED_CHART_BAR_GAP);

        fbChart.setTitle(FACEBOOK_BREAKDOWN_CHART_TITLE);
        fbChart.setData(formatFacebookData());
        twChart.setTitle(TWITTER_BREAKDOWN_CHART_TITLE);
        twChart.setData(formatTwitterData());
        igChart.setTitle(INSTAGRAM_BREAKDOWN_CHART_TITLE);
        igChart.setData(formatInstagramData());
    }

    public StatisticsPanel(ObservableList<ReadOnlyPerson> list) {
        super(FXML);

        this.currentYear = this.getCurrentYear();
        this.currentMonth = this.getCurrentMonth();

        initialiseStatisticsPanel(list);
    }

    public StatisticsPanel(ObservableList<ReadOnlyPerson> list, int currentMonth, int currentYear) {
        super(FXML);

        this.currentYear = currentYear;
        this.currentMonth = currentMonth;

        initialiseStatisticsPanel(list);

    }

    private ObservableList<XYChart.Series<String, Integer>> getPersonAddedChartData(ObservableList<ReadOnlyPerson> list) {

        ObservableList<XYChart.Series<String, Integer>> answer = FXCollections.observableArrayList();
        XYChart.Series<String, Integer> aSeries = new XYChart.Series<String, Integer>();
        aSeries.setName("Persons added");

        int endYear = this.currentYear;
        int startYear = endYear - PERSON_ADDED_DISPLAY_YEARS;

        int startMonth;
        int endMonth;
        int monthCount = 0;

        ArrayList<Integer> monthPersonsAdded = getNewPersonsAddByMonth(list);

        for (int i = startYear; i <= endYear; i++) {

            startMonth = 1;
            endMonth = 12;

            if (i == startYear) {
                startMonth = this.currentMonth;
            }

            if (i == endYear) {
                endMonth = this.currentMonth;
            }

            for (int m = startMonth; m <= endMonth; m++) {

                String labelName = Month.of(m).name().substring(0, 3) + " " + Integer.toString(i);
                aSeries.getData().add(new XYChart.Data(labelName, monthPersonsAdded.get(monthCount)));

                monthCount++;
            }
        }
        answer.addAll(aSeries);
        return answer;
    }

    private ArrayList<Integer> getNewPersonsAddByMonth(ObservableList<ReadOnlyPerson> list) {


        ArrayList<Integer> countByMonth = new ArrayList<>(Collections.nCopies(PERSON_ADDED_DISPLAY_YEARS * 12 + 1, 0));

        list.forEach((p) -> {
            Date givenDate = p.getCreatedAt();
            ZonedDateTime given = givenDate.toInstant().atZone(ZoneId.of("UTC"));

            int personAddedYear = Integer.parseInt(Year.from(given).toString());
            int personAddedMonth = Month.from(given).getValue();

            int indOffset = calculateCountByMonthOffset(personAddedMonth, personAddedYear);
            if ( indOffset >= 0 && indOffset <= PERSON_ADDED_DISPLAY_YEARS * 12) {
                countByMonth.set(indOffset, countByMonth.get(indOffset) + 1);
            }
        });

        return countByMonth;
    }
    /**
     * Count the offset when adding to the array list of sum by months
     */
    public int calculateCountByMonthOffset(int personAddedMonth, int personAddedYear) {
        return (this.currentYear - personAddedYear) * 12 +
                (this.currentMonth - personAddedMonth);
    }

    /**
     * Tabulate the total number of people in the list
     */
    public void tabulateTotalNumberOfPeople(ObservableList<ReadOnlyPerson> list) {
        this.totalNumberOfPeople = list.size();
    }

    /**
     * Tabulates number of users of each social media platform
     */
    public void tabulateSocialMediaUsage(ObservableList<ReadOnlyPerson> list) {
        for (ReadOnlyPerson aList : list) {
            SocialMedia current = aList.getSocialMedia();
            if (current.facebook.isEmpty()) {
                this.hasNoFacebook++;
            }
            if (current.twitter.isEmpty()) {
                this.hasNoTwitter++;
            }
            if (current.instagram.isEmpty()) {
                this.hasNoInstagram++;
            }
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
     * Fetches the current year
     */
    private Integer getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Fetches the current month
     */
    private Integer getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * Fetches number of persons with no facebook information added
     */
    public Integer getHasNoFacebook() {
        return this.hasNoFacebook;
    }

    /**
     * Fetches number of persons with no twitter information added
     */
    public Integer getHasNoTwitter() {
        return this.hasNoTwitter;
    }

    /**
     * Fetches number of persons with no instagram information added
     */
    public Integer getHasNoInstagram() {
        return this.hasNoInstagram;
    }

    /**
     * Fetches total number of persons
     */
    public Integer getTotalNumberOfPeople() {
        return this.totalNumberOfPeople;
    }

}