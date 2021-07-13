package pages;

import Base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FlightStatusPage extends PageBase {

    @FindBy(xpath = "//button[contains(@class,'accept cookie-consent')]")
    WebElement acceptCookies;

    @FindBy(xpath = "//button[@class='cv-myew-flyout__close-btn']")
    WebElement closeFlyoutButton;

    @FindBy(xpath = "(//button[@class='a-cta a-cta--block a-cta-prio3 o-compact-search__cta-button-label'])[1]")
    WebElement departureInputButton;

    @FindBy(xpath = "//input[@aria-label='Departure airport']")
    WebElement departureInput;

    @FindBy(xpath = "(//button[@class='a-cta a-cta--block a-cta-prio3 o-compact-search__cta-button-label'])[2]")
    WebElement destinationInputButton;

    @FindBy(xpath = "//input[@aria-label='Destination airport']")
    WebElement destinationInput;

    @FindBy(xpath = "//div[@class='fieldset-form-footer']/button")
    WebElement showFlightStatusButton;

    @FindBy(xpath = "//div[@class='m-form-datepicker']/div[1]")
    WebElement travelDateInput;

    @FindBy(xpath = "(//div[@class='m-form-radiobutton__wrapper'])[2]/div")
    WebElement flightNumberRadioButton;

    @FindBy(name = "flightNumber")
    WebElement flightNumberInput;


    @FindBy(xpath = "//table[@class='widget-toggle-table']//td[@class='date-head']")
    WebElement getDate;

    @FindBy(xpath = "//th[@class='a11y-th time']")
    WebElement deptime;

    @FindBy(xpath = "//td[@class='time']//span[@class='nowrap']")
    WebElement arrivalTime;

    @FindBy(xpath = "//td[@class='number']")
    WebElement flightnumnber;

    @FindBy(xpath = "//td[@class='status']")
    WebElement flightstatus;

    @FindBy(xpath = " //div[contains(@id,'form_result_id')]//p[@class='refresh-date']")
    WebElement Lastupdated;

    @FindBy(xpath = "//table[@class='widget-toggle-table']")
    List<WebElement> flightstatusresult;

    @FindBy(xpath = "//div[@class='tab-content active']//div[contains(@id,'form_result_id')]")
    WebElement Noresultfound;

    @FindBy(xpath = " //div[@class='reinitContent']//button[@class='btn-refresh']")
    WebElement updatestatus;

    @FindBy(xpath = "(//div[@class='o-search-flight-status__card-airports'])[1]/p[1]")
    WebElement resultDepartureAirport;

    @FindBy(xpath = "(//div[@class='o-search-flight-status__card-airports'])[1]/p[2]")
    WebElement resultDestinationAirport;

    @FindBy(xpath = "//button[@class='o-search-flight-status__date-navigation__date o-search-flight-status__date-navigation__" +
            "date--active o-search-flight-status__date-navigation__date--align-center']")
    WebElement resultTravelDate;

    @FindBy(xpath = "//div[@class='o-search-flight-status__card-flight-number']/p")
    WebElement resultFlightNumber;

    public FlightStatusPage(WebDriver webDriver) {
        super(webDriver);
    }

    public FlightStatusPage acceptCookies() {
        browserHelper.waitForElementClickable(acceptCookies);
        acceptCookies.click();
        return this;
    }

    public FlightStatusPage closeFlyout() {
        if (browserHelper.isDisplayed(closeFlyoutButton)) {
            browserHelper.waitForElementClickable(closeFlyoutButton);
            closeFlyoutButton.click();
        }
        return this;
    }

    public FlightStatusPage inputDepartureAirport(String departureAirportCode) {
        browserHelper.scrollIntoViewIfNeeded(departureInputButton);
        browserHelper.waitForElementClickable(departureInputButton);
        departureInputButton.click();
        browserHelper.waitForElementClickable(departureInput);
        departureInput.sendKeys(departureAirportCode);
        browserHelper.waitForAjax();
        departureInput.sendKeys(Keys.TAB);
        return this;
    }

    public FlightStatusPage inputDestinationAirport(String destinationAirportCode) {
        browserHelper.waitForElementClickable(destinationInput);
        destinationInput.clear();
        destinationInput.sendKeys(destinationAirportCode);
        browserHelper.smallWait(2000);
        destinationInput.sendKeys(Keys.TAB);
        return this;
    }

    public FlightStatusPage inputTodayDate() {
        String travelDate = getcurrentdate();
        logger.info("Value of travelDate is: " + travelDate);
        browserHelper.waitForElementClickable(travelDateInput);
        travelDateInput.click();

        List<WebElement> allDates = webDriver.findElements(By.xpath("//td[@class='calendar-table__cell']/div/input"));
        for (WebElement ele : allDates) {

            String date = ele.getAttribute("value");
            if (date.equalsIgnoreCase(travelDate)) {
                ele.click();
                break;
            }
        }
        return this;
    }

    public String getcurrentdate() {
        Date date = new Date();
        String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return todaysDate;
    }

    public FlightStatusPage showFlightStatus() {
        browserHelper.waitForElementClickable(showFlightStatusButton);
        showFlightStatusButton.click();
        browserHelper.waitForPageToLoad();
        return this;
    }

    public FlightStatusPage selectSearchByFlightNumber() {
        browserHelper.waitForElementClickable(flightNumberRadioButton);
        flightNumberRadioButton.click();
        return this;
    }

    public FlightStatusPage inputFlightNumber(String flightNumber) {
        browserHelper.waitForElementClickable(flightNumberInput);
        flightNumberInput.sendKeys(flightNumber);
        return this;
    }

    public String getResultDepartureAirport() {
        browserHelper.waitForPageToLoad();
        browserHelper.waitForAjax();
        browserHelper.scrollIntoViewIfNeeded(resultDepartureAirport);
        browserHelper.waitForElementVisible(resultDepartureAirport);
        return resultDepartureAirport.getText();
    }

    public String getResultDestinationAirport() {
        browserHelper.waitForElementClickable(resultDestinationAirport);
        return resultDestinationAirport.getText();
    }

    public String getResultFlightNumber() {
        browserHelper.waitForElementClickable(resultFlightNumber);
        return resultFlightNumber.getText();
    }


    public Boolean getResultTravelDate() {
        browserHelper.waitForElementClickable(resultTravelDate);
        String outputDate = resultTravelDate.getText();
        outputDate = outputDate.substring(5);;
        String inputDate = travelDateInput.getAttribute("eventmodelvalue");

        return inputDate.contains(outputDate);
    }


}
