import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import src.CostCalculation;
import src.ExceptionMessage;
import src.Load;
import src.Size;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static src.Load.*;
import static src.Size.BIG;
import static src.Size.SMALL;


public class DeliveryCostCalculationTests {

    @DataProvider(name = "smallCargoDelivery")
    public Object[][] costBasedSmallCargoSize() {
        return new Object[][]{
                {3, SMALL, false, HIGH, 400},
                {1, SMALL, true, NORMAL, 450},
                {31, SMALL, false, VERY_HIGH, 640},
                {3, SMALL, true, INCREASED, 600},
                {11, SMALL, true, NORMAL, 600},
                {11, SMALL, false, INCREASED, 400},
                {31, SMALL, false, INCREASED, 480}
        };
    }

    @Test(description = "Calculate cost of delivery for small cargo size", dataProvider = "smallCargoDelivery")

    public void calculateSmallCargoDeliveryCost(int distance, Size size, boolean fragile, Load load, int expected) {

        double actual = CostCalculation.FinalCostCalculation(distance, size, fragile, load);
        assertEquals(expected, actual);
    }


    @DataProvider(name = "bigCargoDelivery")
    public Object[][] costBasedBigCargoSize() {
        return new Object[][]{
                {4, BIG, true, NORMAL, 600},
                {31, BIG, false, HIGH, 700},
                {1, BIG, false, INCREASED, 400},
                {1, BIG, true, HIGH, 770},
                {11, BIG, false, INCREASED, 480},
                {1, BIG, true, VERY_HIGH, 880},
                {3, BIG, false, VERY_HIGH, 480},
                {11, BIG, true, HIGH, 980},
                {100, BIG, false, NORMAL, 500},
        };
    }
    @Test(description = "Calculate cost of delivery for big cargo size", dataProvider = "bigCargoDelivery")
    public void calculateBigCargoDeliveryCost(int distance, Size size, boolean fragile, Load load, double expected) {

        double actual = CostCalculation.FinalCostCalculation(distance, size, fragile, load);
        assertEquals(expected, actual);
    }


    @DataProvider(name = "longDistanceForFragile")
    public Object[][] costBasedLongDistanceFragile() {
        return new Object[][]{
                {31, BIG, true, NORMAL},
        };
    }

    @Test(description = "Verify error when distance for fragile cargo is more than 30 km", dataProvider = "longDistanceForFragile")

    public void calculateFragileCostDelivery(int distance, Size size, boolean fragile, Load load) {

        Exception e = Assert.expectThrows(
                IllegalArgumentException.class,
                () -> CostCalculation.FinalCostCalculation(distance, size, fragile, load)
        );
        assertEquals(ExceptionMessage.LONG_DISTANCE_FOR_FRAGILE_CARGO, e.getMessage());
    }


    @DataProvider(name = "negativeDistance")
    public Object[][] costBasedNegativeDistance() {
        return new Object[][]{
                {-1, SMALL, false, HIGH},
                {0, BIG, false, NORMAL},
        };
    }

        @Test(description = "Verify error for negative distance", dataProvider = "negativeDistance")

        public void calculateZeroDistanceDelivery(int distance, Size size, boolean fragile, Load load) {

        Exception e = Assert.expectThrows(
                IllegalArgumentException.class,
                () -> CostCalculation.FinalCostCalculation(distance, size, fragile, load)
        );
        assertEquals(ExceptionMessage.SHORT_DISTANCE, e.getMessage());
    }
}

