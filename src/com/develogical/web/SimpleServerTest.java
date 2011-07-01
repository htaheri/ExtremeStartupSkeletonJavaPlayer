package com.develogical.web;

import junit.framework.TestCase;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: hoseri
 * Date: 6/30/11
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleServerTest {
    SimpleServer server = new SimpleServer();
    
    @Test
    public void LargestTest() {
        Integer[] list = new Integer[2];
        list[0] = 239;
        list[1] = 91;
        assertEquals("239", server.LargestNumber(list));
    }

    @Test
    public void PrimeTest() {
        assertTrue(server.isPrime(17));
        assertFalse(server.isPrime(35));
    }

    @Test
    public void PlusTest() {
        assertEquals("38", server.ProcessPlus("f0059ec0: what is 11 plus 10 plus 17"));
        assertEquals("21", server.ProcessPlus("f0059ec0: what is 11 plus 10"));
    }

      @Test
    public void ParsTest() {
          //5+4*(7-15
          assertEquals("3", server.parsFunction("1+2"));
    }
    @Test
    public void TranslateTest() {
          //5+4*(7-15
          assertEquals("11 + 5", server.TranslateFormula("7744d410: what is 11 plus 5"));

    }
@Test
    public void CalculateTest() {
          //5+4*(7-15
          assertEquals("26", server.calculateRequest("24e09410: what is 2 multiplied by 13"));
    }
@Test
    public void CubeTest() throws Exception {
          //5+4*(7-15
            assertEquals(true, server.isCubeNumber(27));
            assertFalse(server.isCubeNumber(10));

    }
@Test
    public void SquareTest() throws Exception {
          //5+4*(7-15
            assertEquals(true, server.isSquareNumber(9));
            assertFalse(server.isSquareNumber(10));

    }


}
