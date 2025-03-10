package src;

import static src.ExceptionMessage.*;

public class CostCalculation {
    private static final int MIN_COST = 400;
    public static double FinalCostCalculation (int distance, Size size , boolean fragile, Load load) {
         double result = 0;

        if (distance > 100) {
            throw new IllegalArgumentException(LONG_DISTANCE);
        }
        if (distance <= 0) {
            throw new IllegalArgumentException(SHORT_DISTANCE);
        }
        if(load.equals(null)) {
            throw new IllegalArgumentException(DELIVERY_WORKLOAD_IS_NULL);
        }
        if (fragile && distance > 30) {
            throw new IllegalArgumentException(LONG_DISTANCE_FOR_FRAGILE_CARGO);
        }
        if(size.equals(null)) {
            throw new IllegalArgumentException(CARGO_SIZE_IS_NULL);
        }

        if (distance > 30) {
            result = result + 300;
        } else if (distance > 10) {
            result = result + 200;
        } else if (distance > 2) {
            result = result + 100;
        } else if (distance > 0) {
            result = result + 50 ;
        }

       if (size.equals(Size.BIG)) {
            result = result + 200;

        } else result = result + 100;

        if (fragile && distance <= 30) {
            result = result + 300;
        }

         if(load.equals(Load.INCREASED)) {
            result = result * 1.2;

        } else if (load.equals(Load.VERY_HIGH)){
            result = result * 1.6;
        }
        else if(load.equals(Load.HIGH)) {
            result = result * 1.4;
        }

        if (result < MIN_COST) {
            result = MIN_COST;
        }
        return Math.round(result);

    }
}
