package uintTesting;

import static org.junit.Assert.*;

/**
 * The DateTest class tests that the isValid() method works correctly.
 *
 * @author Zachary Goldman
 */
public class DateTest {
    /**
     * The isValid() method tests the isValid() method of the Date class
     */
    @org.junit.Test
    public void isValid() {
        //Test 1: Current date invalid
        Date test1 = new Date();
        assertFalse(test1.isValid());

        //Test 2: Years < 2021 invalid
        Date test2 = new Date("1/20/2020");
        assertFalse(test2.isValid());

        //Test 3: Years > 2021 invalid
        Date test3 = new Date("1/20/2022");
        assertFalse(test3.isValid());

        //Test 4: Year = 2021, Month>Current month invalid
        Date test4 = new Date("12/10/2021");
        assertFalse(test4.isValid());

        //Test 5: Year = 2021, Month=Current month, Day>Current day invalid
        Date test5 = new Date("10/28/2021");
        assertFalse(test5.isValid());

        //Test 6: Year = 2021, Month<Current month, Day>Current day valid
        Date test6 = new Date("9/28/2021");
        assertTrue(test6.isValid());

        //Test 7: Year = 2021, Month=Current month, Day<Current day valid
        Date test7 = new Date("10/08/2021");
        assertTrue(test7.isValid());

        //Test 8: Year = 2021, Month=Feb, Day=29, testing that 2021 is not leap year
        Date test8 = new Date("2/29/2021");
        assertFalse(test8.isValid());

        //Test 9: Year = 2021, Month = valid month like January, Day > 31 invalid
        Date test9 = new Date("1/32/2021");
        assertFalse(test9.isValid());
    }
}