package entity;

import org.junit.Assert;
import org.junit.Test;
import org.nucleus.utility.AllocationCalculation;

import java.util.Optional;

public class AllocationCalculationTest {

    @Test
    public void checkDistributionAmount(){
        Assert.assertArrayEquals(AllocationCalculation.distributeAmount(60000,2000.00,2000.00,2000.00),new Double[]{2000.00,2000.00,2000.00});
    }

    @Test
    public void checkDoubleValue(){
        Assert.assertEquals(Optional.of(AllocationCalculation.processDoubleValue(null)),Optional.of(0.0));
    }

    @Test
    public void checkIntegerValue(){
        Assert.assertEquals(Optional.of(AllocationCalculation.processIntegerValue(null)),Optional.of(0));
    }

    @Test
    public void checkNumberOfBatches(){
        Assert.assertEquals(1, AllocationCalculation.calculateNumberOfBatches(10,1));
    }

}
