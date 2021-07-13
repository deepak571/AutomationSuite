import Base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlightStatusTest extends TestBase {

    String departureAirport = "DUS";
    String destinationAirport = "PMI";
    String flightNumber = "EW9580";


    @Test
    public void TC01_FlightStatusTestByDepartureAndDestination() throws InterruptedException {
        logger.info("=======Starting TC01_FlightStatusTestByDepartureAndDestination ========");
        flightStatusPage.acceptCookies().closeFlyout().inputDepartureAirport(departureAirport)
                .inputDestinationAirport(destinationAirport).inputTodayDate().showFlightStatus();

        String expectedDepartureAirport = flightStatusPage.getResultDepartureAirport();
        Assert.assertEquals(expectedDepartureAirport, departureAirport, "Input and Result departure airport are not matching");

        String expectedDestinationAirport = flightStatusPage.getResultDestinationAirport();
        Assert.assertEquals(expectedDestinationAirport, destinationAirport, "Input and Result destination airport are not matching");

        Assert.assertTrue(flightStatusPage.getResultTravelDate(), "Input and Result Dates are not matching");
    }


    @Test
    public void TC02_FlightStatusTestByFlightNumber() {
        logger.info("=======Starting TC02_FlightStatusTestByFlightNumbert ========");
        flightStatusPage.acceptCookies().closeFlyout().selectSearchByFlightNumber().inputFlightNumber(flightNumber).inputTodayDate().showFlightStatus();

        String expectedFlightNumber = flightStatusPage.getResultFlightNumber();
        Assert.assertEquals(expectedFlightNumber, flightNumber, "Input and Result Flight Numbers are not matching");

        Assert.assertTrue(flightStatusPage.getResultTravelDate(), "Input and Result Dates are not matching");
    }

}
