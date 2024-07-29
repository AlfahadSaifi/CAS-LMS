package entity;

import org.junit.Assert;
import org.junit.Test;
import org.nucleus.utility.EmiUtility;

public class EmiCalculationTest {
    EmiUtility emiUtility= new EmiUtility();

    @Test
    public void checkCalculatedEMIEdgeCase1(){
        Assert.assertEquals(emiUtility.calculateEMI(123456,5,12,11),2684.00,.50);
    }
    @Test
    public void checkCalculatedEMIEdgeCase2(){
        Assert.assertEquals(emiUtility.calculateEMI(123456,200,12,5),514,.50);
    }
    @Test
    public void checkCalculatedEMIEdgeCase3(){
        Assert.assertEquals(emiUtility.calculateEMI(4000000,20,12,12),44043,.50);
    }
    @Test
    public void checkCalculatedEMIEdgeCase4(){
        Assert.assertEquals(emiUtility.calculateEMI(123456,5,0,11),0,.50);
    }
    @Test
    public void checkCalculatedEMIEdgeCase5(){
        Assert.assertEquals(emiUtility.calculateEMI(123456,5,12,0),0,.50);
    }
}
