package uintTesting;

import static org.junit.Assert.*;

/**
 * The InternationalTest class tests that the tuitionDue method in International by calling it then accessing
 * it through getTuitionFee() and checking that the amounts line up to where it should.
 *
 * @author Zachary Goldman
 */
public class InternationalTest {
    /**
     * The tuitionDue() method tests the tuitionDue() method of the International class
     */
    @org.junit.Test
    public void tuitionDue() {
        //Test 1 - If study abroad parttime
        Major major1 = Major.valueOf("CS");
        Profile student1 = new Profile("John Smith", major1, false);
        International test1 = new International(student1, 9, true);
        test1.tuitionDue();
        assertTrue(test1.getTuitionFee() == (3268 * 0.8));

        //Test 2 - if study abroad full time
        Major major2 = Major.valueOf("CS");
        Profile student2 = new Profile("John Smith", major2, true);
        International test2 = new International(student2, 12, true);
        test2.tuitionDue();
        assertTrue(test2.getTuitionFee() == 3268 + 2650);

        //Test 3 - if not student abroad parttime
        Major major3 = Major.valueOf("CS");
        Profile student3 = new Profile("John Smith", major3, false);
        International test3 = new International(student3, 9, false);
        test3.tuitionDue();
        assertTrue(test3.getTuitionFee() == ((966 * 9) + (0.8 * 3268)));

        //Test 4 - if not study abroad full time + no extra credits
        Major major4 = Major.valueOf("CS");
        Profile student4 = new Profile("John Smith", major4, false);
        International test4 = new International(student4, 9, false);
        test4.tuitionDue();
        assertTrue(test4.getTuitionFee() == (966 * 9 + .8 * 3268));

        //Test 5 - if not study abroad full time + extra credits
        Major major5 = Major.valueOf("CS");
        Profile student5 = new Profile("John Smith", major5, true);
        International test5 = new International(student5, 18, false);
        test5.tuitionDue();
        assertTrue(test5.getTuitionFee() == (3268 + 29737 + 2650 + (2 * 966)));
    }
}