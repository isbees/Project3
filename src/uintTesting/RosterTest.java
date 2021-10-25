package uintTesting;

import static org.junit.Assert.*;

/**
 * The RosterTest class tests that the add() and remove() methods works correctly.
 *
 * @author Zachary Goldman
 */
public class RosterTest {

    /**
     * The add() method tests the add() method of the Roster class
     * It checks that we can, for all types of student:
     * 1. Add to empty
     * 2. Add to a roster of size 1
     * 3. Add to a roster of size 2
     * Then finally, that we can mix types of students in a roster, and can't add a student twice.
     */
    @org.junit.Test
    public void add() {
        Major itMajor = Major.valueOf("IT");
        Major csMajor = Major.valueOf("CS");
        Major eeMajor = Major.valueOf("EE");

        //Test 1: Testing adding international students to roster size 0,1,2
        Roster test1Roster = new Roster();

        //Add to empty
        Profile IProfile1 = new Profile("John Adams", csMajor);
        International test1Inter = new International(IProfile1, 14, false);
        assertTrue(test1Roster.add(test1Inter));

        //Add to size 1
        Profile IProfile2 = new Profile("Mark Sams", itMajor);
        International test2Inter = new International(IProfile2, 14, false);
        assertTrue(test1Roster.add(test2Inter));

        //Add to size 2
        Profile IProfile3 = new Profile("Andy Can", eeMajor);
        International test3Inter = new International(IProfile3, 16, false);
        assertTrue(test1Roster.add(test3Inter));

        //Test 2: Testing adding tristate students to roster size 0,1,2
        Roster test2Roster = new Roster();

        //Add to empty
        TriState test1Tri = new TriState("John Adams", csMajor, 14, "NY");
        assertTrue(test2Roster.add(test1Tri));
        //Add to size 1
        TriState test2Tri = new TriState("Mark Sams", itMajor, 14, "CT");
        assertTrue(test2Roster.add(test2Tri));
        //Add to size 2
        TriState test3Tri = new TriState("Andy Can", eeMajor, 14, "NY");
        assertTrue(test2Roster.add(test3Tri));


        //Test 3: Testing adding nonresident students to roster size 0,1,2
        Roster test3Roster = new Roster();

        //Add to empty
        Profile NRProfile1 = new Profile("John Adams", csMajor);
        NonResident test1NR = new NonResident(NRProfile1, 14);
        assertTrue(test3Roster.add(test1NR));
        //Add to size 1
        Profile NRProfile2 = new Profile("Mark Sams", itMajor);
        NonResident test2NR = new NonResident(NRProfile2, 14);
        assertTrue(test3Roster.add(test2NR));
        //Add to size 2
        Profile NRProfile3 = new Profile("Andy Can", eeMajor);
        NonResident test3NR = new NonResident(NRProfile3, 14);
        assertTrue(test3Roster.add(test3NR));

        //Test 4: Testing adding resident students to roster size 0,1,2
        Roster test4Roster = new Roster();

        //Add to emptty
        Profile RProfile1 = new Profile("John Adams", csMajor);
        Resident test1R = new Resident(RProfile1, 14);
        assertTrue(test4Roster.add(test1R));
        //Add to size 1
        Profile RProfile2 = new Profile("Mark Sams", itMajor);
        Resident test2R = new Resident(RProfile2, 14);
        assertTrue(test4Roster.add(test2R));
        //Add to size 2
        Profile RProfile3 = new Profile("Andy Can", eeMajor);
        Resident test3R = new Resident(RProfile3, 14);
        assertTrue(test4Roster.add(test3R));

        //Test 5: Testing adding all types of students to a single roster
        Roster test5Roster = new Roster();

        //Add international student
        Profile interProfile = new Profile("John Adams", csMajor);
        International interStudent = new International(interProfile, 14, false);
        assertTrue(test5Roster.add(interStudent));

        //Add TriState student
        TriState triStudent = new TriState("Mark Sams", itMajor, 14, "NY");
        assertTrue(test5Roster.add(triStudent));

        //Add NonResident student
        Profile nonResProfile = new Profile("Andy Can", eeMajor);
        NonResident nonResStudent = new NonResident(nonResProfile, 14);
        assertTrue(test5Roster.add(nonResStudent));

        //Add Resident student
        Profile resProfile = new Profile("Hughe Landough", itMajor);
        Resident resStudent = new Resident(resProfile, 14);
        assertTrue(test5Roster.add(resStudent));

        //Test 6 - can't add same student twice to the same roster
        Roster test6 = new Roster();
        assertTrue(test6.add(resStudent));
        assertFalse(test6.add(resStudent));
    }

    /**
     * The remove() method tests the remove() method of the Roster class
     * It checks that, for all types of student:
     * 1. We can't remove a student from an empty roster
     * 2. We can remove only a student that's in the roster, in a roster size 1
     */
    @org.junit.Test
    public void remove() {
        Major csMajor = Major.valueOf("CS");

        //Making usable international, nonresident, tristate, resident, and student.
        Profile interProfile = new Profile("John Adams", csMajor);
        International interStudent = new International(interProfile, 14, false);
        TriState triStudent = new TriState("Mark Sams", csMajor, 14, "NY");
        Profile nonResProfile = new Profile("Paul Jeff", csMajor);
        NonResident nonResStudent = new NonResident(nonResProfile, 14);
        Profile resProfile = new Profile("Andy Can", csMajor);
        NonResident resStudent = new NonResident(resProfile, 14);
        Student testStudent = new Student("Landon Ades", csMajor, 14);


        //Test 1 - Unable to remove any type of student from an empty roster
        Roster test1 = new Roster();
        assertFalse(test1.remove(interStudent));
        assertFalse(test1.remove(triStudent));
        assertFalse(test1.remove(nonResStudent));
        assertFalse(test1.remove(resStudent));
        assertFalse(test1.remove(testStudent));

        //Test 2 - Unable to remove a student not in the roster, can remove a student that's in the roster
        Roster test2 = new Roster();
        test2.add(testStudent);
        assertFalse(test2.remove(interStudent));
        assertFalse(test2.remove(triStudent));
        assertFalse(test2.remove(nonResStudent));
        assertFalse(test2.remove(resStudent));
        assertTrue(test2.remove(testStudent));

        //Test 3 - Unable to remove a student not in the roster, can remove a resident that's in the roster
        Roster test3 = new Roster();
        test3.add(resStudent);
        assertFalse(test3.remove(interStudent));
        assertFalse(test3.remove(triStudent));
        assertFalse(test3.remove(nonResStudent));
        assertTrue(test3.remove(resStudent));
        assertFalse(test3.remove(testStudent));


        //Test 4 - Unable to remove a student not in the roster, can remove a nonresident that's in the roster
        Roster test4 = new Roster();
        test4.add(nonResStudent);
        assertFalse(test4.remove(interStudent));
        assertFalse(test4.remove(triStudent));
        assertTrue(test4.remove(nonResStudent));
        assertFalse(test4.remove(resStudent));
        assertFalse(test4.remove(testStudent));


        //Test 5 - Unable to remove a student not in the roster, can remove a tristate that's in the roster
        Roster test5 = new Roster();
        test5.add(triStudent);
        assertFalse(test5.remove(interStudent));
        assertTrue(test5.remove(triStudent));
        assertFalse(test5.remove(nonResStudent));
        assertFalse(test5.remove(resStudent));
        assertFalse(test5.remove(testStudent));

        //Test 6 - Unable to remove a student not in the roster, can remove an international that's in the roster
        Roster test6 = new Roster();
        test6.add(interStudent);
        assertTrue(test6.remove(interStudent));
        assertFalse(test6.remove(triStudent));
        assertFalse(test6.remove(nonResStudent));
        assertFalse(test6.remove(resStudent));
        assertFalse(test6.remove(testStudent));
    }
}