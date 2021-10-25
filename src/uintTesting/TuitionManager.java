package uintTesting;

import java.util.StringTokenizer;
import java.util.Scanner;

/**
 * The TuitionManager class runs from Project2's main method and is the interface for commands of roster.
 *
 * @author Zachary Goldman
 */

public class TuitionManager {
    private static final int QUIT_COMMAND = 9;
    private static final int INVALID_COMMAND = -1;

    /**
     * Main method
     *
     * @param args from command line, not given in our class.
     */
    public static void main(String[] args) {
    }

    //Takes in the input and sends it off to check its validity,
    // then calls to do the command

    /**
     * Run is called by RunProject2, scans in the user's lines, checks its validity with checkCmdValidity,
     * then sends it to doCommand unless the user entered invalid input or a Q, in which case it goes to
     * the next line or stops the manager in the respective cases.
     */
    public static void run() {
        Scanner sc = new Scanner(System.in);
        Roster rost = new Roster();
        System.out.println("TuitionManager Started");
        //Read in the inputs
        while (sc.hasNextLine()) {
            String inputLine = sc.nextLine();
            StringTokenizer st = new StringTokenizer(inputLine, ",");
            //Deal with empty lines
            if (!(st.hasMoreTokens())) {
                continue;
            }
            String letters = st.nextToken();
            //cmdType is index from: {"AI","AT","AN","AR","R","C","S","F","T","Q"}
            int cmdType = checkCmdValidity(letters, st);
            //If it's invalid command
            if (cmdType == INVALID_COMMAND) {
                continue;
            }
            //If it's a Q
            if (cmdType == QUIT_COMMAND) {
                break;
            }
            doCommand(letters, st, rost);
        }
        System.out.println("TuitionManager Terminated.");
        sc.close();
    }

    //Checks validity of command

    /**
     * checkCmdValidity takes in the command and rest of the string in the tokenizer, checks that the
     * command is uppercase, valid, and that the rest of the line is the correct number of tokens
     *
     * @param letters that are the command letters in a string
     * @param st      representing the StringTokenizer on the second token already having read in the cmd
     * @return cmdIndex returns the index of command from the array of valid commands, or -1 if invalid line or command
     */
    private static int checkCmdValidity(String letters, StringTokenizer st) {
        //If not capitalized, invalid
        if (!(letters.toUpperCase().equals(letters))) {
            System.out.println("Command " + "'" + letters + "'" + "not supported!");
            return INVALID_COMMAND;
        }
        //If not in list of good commands, invalid.
        String[] validCommands = {"AI", "AT", "AN", "AR", "R", "C", "S", "F", "T", "Q", "P", "PT", "PN"};
        int cmdIndex = -1;
        for (int i = 0; i < validCommands.length; i++) {
            if (validCommands[i].equals(letters)) {
                cmdIndex = i;
            }
        }
        if (cmdIndex == -1) {
            System.out.println("Not a valid command!");
            return INVALID_COMMAND;
        }
        //Check that there's the right amount of tokens
        int totalTokens = st.countTokens() + 1;

        //Check that of C,P,PT,PN there's 1 total token.
        if (letters.equals("C") || letters.equals("P") || letters.equals("PT") || letters.equals("PN")) {
            if (totalTokens != 1) {
                System.out.println("Missing data in command line.");
                return INVALID_COMMAND;
            }
        }
        //Check that for remove, there's 3 total tokens.
        if (letters.equals("R")) {
            if (totalTokens != 3) {
                System.out.println("Missing data in command line.");
                return INVALID_COMMAND;
            }
        }
        //Check that for S, F, AR, AN there's 4 total tokens.
        if (letters.equals("S") || letters.equals("F") || letters.equals("AR") || letters.equals("AN")) {
            if (totalTokens != 4) {
                if (totalTokens == 3) {
                    if (letters.equals("AR") || letters.equals("AN")) {
                        System.out.println("Credit hours missing.");
                        return INVALID_COMMAND;
                    }
                    if (letters.equals("F")) {
                        System.out.println("Missing the amount.");
                        return INVALID_COMMAND;
                    }
                }
                System.out.println("Missing data in command line.");
                return INVALID_COMMAND;
            }
        }
        //Check that for AI, AT 5 tokens
        if (letters.equals("AI") || letters.equals("AT")) {
            if (totalTokens != 5) {
                if (totalTokens == 3) {
                    System.out.println("Credit hours missing.");
                    return INVALID_COMMAND;
                } else {
                    System.out.println("Missing data in command line.");
                    return INVALID_COMMAND;
                }
            }
        }
        //Check for T that there's 5 total tokens, and valid amounts
        if (letters.equals("T")) {
            if (totalTokens != 5) {
                if (totalTokens == 3) {
                    System.out.println("Payment amount missing.");
                    return INVALID_COMMAND;
                }
            }
        }
        return cmdIndex;
    }

    /**
     * AddStudentWithPrinting is called by the addStudentPartOne method below when testing to see if
     * adding a student is successful.
     * This method calls add() from the Roster class, then prints out if it worked or not.
     *
     * @param roster     that is the roster we're dealing with
     * @param newStudent which is the student we're trying to add
     */
    private static void addStudentWithPrinting(Roster roster, Student newStudent) {
        boolean added = roster.add(newStudent);
        if (!added) {
            System.out.println("Student already in the roster.");
        } else {
            System.out.println("Student added.");
        }
    }

    /**
     * isNumber returns true if the string we're testing is a number, false otherwise in the case that we have
     * a NumberFormatException since it can't be converted as it's not a number
     *
     * @param test string that we're checking if it's a number or not
     * @return boolean that's true if a number, false if not
     */
    private static boolean isNumber(String test) {
        try {
            double testDouble = Double.parseDouble(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * isString returns false if the string we're testing is an integer, true in the case that we have
     * a NumberFormatException since it can't be converted as it's not a number.
     *
     * @param test string that we're checking if it's a string or not
     * @return boolean that's true if a string, false if not
     */
    private static boolean isString(String test) {
        try {
            int testInt = Integer.parseInt(test);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * addStudentCommandPartOne is called by the doCommand if the command is to add a student.
     * This is in a separate method so as to make the class more modularized.
     * Here we get the credits and extra info about the student we're adding,
     * and then call the addStudentWithPrinting method to actually add them and
     * print if successful.
     *
     * @param cmd    the command we're dealing with
     * @param name   the name of the student we're adding
     * @param major  the major of the student we're adding
     * @param st     the StringTokenizer holding the rest of the information of the student
     * @param roster the roster we're adding the student to
     */
    private static void addStudentCommandPartOne(String cmd, String name, Major major, StringTokenizer st, Roster roster) {
        //Now can go onto adds, finding credits

        String creditsString = st.nextToken();
        boolean isNumber = isNumber(creditsString);
        if (!isNumber) {
            System.out.println("Invalid credit hours.");
            return;
        }
        int credits = Integer.parseInt(creditsString);
        if (credits < 3 || credits > 24) {
            if (credits < 3) {
                if (credits < 0) {
                    System.out.println("Credit hours cannot be negative.");
                } else {
                    System.out.println("Minimum credit hours is 3.");
                }
            } else {
                System.out.println("Credit hours exceed the maximum 24.");
            }
            return;
        }
        if (cmd.equals("AN") || cmd.equals("AR")) {
            if (cmd.equals("AN")) {
                NonResident newStudent = new NonResident(name, major, credits);
                addStudentWithPrinting(roster, newStudent);
                return;
            }
            //Command = AR
            else {
                Resident newStudent = new Resident(name, major, credits);
                addStudentWithPrinting(roster, newStudent);
            }
        }

        //Last comes AI, AT with 5 tokens. For both, we need to use the next token.
        // Now just need state or studyAbroad
        if (cmd.equals("AT")) {

            String state = st.nextToken();
            state = state.toUpperCase();
            if (!(state.equals("NY") || state.equals("CT"))) {
                System.out.println("Not part of the tri-state area.");
                return;
            }
            TriState newStudent = new TriState(name, major, credits, state);
            addStudentWithPrinting(roster, newStudent);
            return;
        }
        if (cmd.equals("AI")) {
            if (credits < 12) {
                System.out.println("International students must enroll at least 12 credits.");
                return;
            }

            String studyAbroadTest = st.nextToken();
            if (!(studyAbroadTest.equals("true") || studyAbroadTest.equals("false"))) {
                System.out.println("You need to give a boolean for studyabroad, not a string");
                return;
            }
            boolean studyAbroad = Boolean.parseBoolean(studyAbroadTest);
            boolean fullTime = true;

            Profile intStudentProfile = new Profile(name, major, fullTime);
            International newStudent = new International(intStudentProfile, credits, studyAbroad);
            addStudentWithPrinting(roster, newStudent);
        }
    }

    /**
     * doCommand takes in the command and does the respective thing. If it isn't a print statement it'll check
     * that the name and major are valid before calling other commands. If it's an adding student command,
     * it'll call the addStudent method above.
     *
     * @param cmd    the command we're dealing with
     * @param st     the stringtokenizer holding the rest of the information of the student
     * @param roster the roster we're adding the student to
     */
    private static void doCommand(String cmd, StringTokenizer st, Roster roster) {
        if (cmd.equals("P")) {
            roster.print();
            return;
        }
        if (cmd.equals("PT")) {
            roster.printByTuition();
            return;
        }
        if (cmd.equals("PN")) {
            roster.printByName();
            return;
        }
        if (cmd.equals("C")) {
            roster.calculateTuition();
            System.out.println("Calculation completed.");
            return;
        }

        //Now need to quickly check that the rest of the line is valid.
        //Check the name and major are strings:
        String name = st.nextToken();
        boolean isNameString = isString(name);

        if (!isNameString) {
            System.out.println("You put a number for name. Invalid.");
            return;
        }
        String major = st.nextToken();
        boolean isMajorString = isString(major);

        if (!isMajorString) {
            System.out.println("You put a number for name. Invalid.");
            return;
        }

        //Check that it's a valid major
        int validMajor = -1;
        String[] validMajors = {"CS", "IT", "BA", "EE", "ME"};
        String majorHolder = major;
        major = major.toUpperCase();                            // added the toUpperCase to get rid of redundant code

        for (int i = 0; i < validMajors.length; i++) {
            if (validMajors[i].equals(major)) {
                validMajor = i;
            }
        }
        if (validMajor == -1) {
            System.out.println("'" + majorHolder + "'" + ", invalid major name.");
            return;
        }   // after this point we know that major is valid so we convert it to the Major enum

        Major majorM = Major.valueOf(major);

        //We have name, major. Since remove requires no more tokens, deal with it first.
        if (cmd.equals("R")) {
            Student newStudent = new Student(name, majorM, 0);
            boolean removed = roster.remove(newStudent);
            if (removed) {
                System.out.println("Student removed from the roster.");
            } else {
                System.out.println("Student is not in the roster.");
            }

            return;
        }

        //Next comes S,F, AT, AR, AN. Gotta check the next token for them.
        //To make it simpler, I put the AT, AR, and AN all with AI in addStudentCommandPartOne()
        if (cmd.equals("S")) {
            //Check the last token is "true" or "false"
            String studyAbroadTest = st.nextToken();
            if (!(studyAbroadTest.equals("true") || studyAbroadTest.equals("false"))) {
                System.out.println("You need to give a boolean for studyabroad, not a string");
                return;
            }
            boolean studyAbroad = Boolean.parseBoolean(studyAbroadTest);

            Profile intProfile = new Profile(name, majorM);
            International newStudent = new International(intProfile, 0, false);
            boolean updated = roster.setStudyAbroad(newStudent, studyAbroad);
            if (updated) {
                System.out.println("Tuition updated.");
            } else {
                System.out.println("Couldn't find international student.");
            }
            return;
        }

        if (cmd.equals("F")) {
            String finAid = st.nextToken();
            boolean validDouble = isNumber(finAid);
            if (!validDouble) {
                System.out.println("Invalid financial aid amount - not a number");
                return;
            }
            double financialAid = Double.parseDouble(finAid);
            //Check it's 0<x<=10k
            if (financialAid <= 0 || financialAid > 10000) {
                System.out.println("Invalid amount.");
                return;
            }
            Student newStudent = new Student(name, majorM, 0);

            //Check if student is in roster
            Student s = roster.findStudent(newStudent);
            if (null == s) {
                System.out.println("Student not in roster.");
                return;
            }

            //Check if student is Resident
            boolean isResident = (s instanceof Resident);
            if (!isResident) {
                System.out.println("Not a resident student.");
                return;
            }

            //Check if student is fulltime
            boolean fullTime = s.getCredit() >= 12;
            if (!fullTime) {
                System.out.println("Parttime student doesn't qualify for the award.");
                return;
            }
            boolean addedAid = s.setFinancialAid(financialAid);
            if (addedAid) {
                System.out.println("Tuition updated.");
            } else {
                System.out.println("Awarded once already.");
            }
            return;
        }

        //Gotta check their credits for Adding any type! So before I do that, I'll deal with my last command, T
        if (cmd.equals("T")) {
            //Check it's a valid payment amount
            String paymentAmountString = st.nextToken();
            boolean isNumber = isNumber(paymentAmountString);
            if (!isNumber) {
                System.out.println("Amount of payment isn't a number!");
                return;
            }
            double paymentAmount = Double.parseDouble(paymentAmountString);
            if (paymentAmount <= 0) {
                System.out.println("Invalid amount.");
                return;
            }
            //If we don't have a date, say missing data.
            if (!st.hasMoreTokens()) {
                System.out.println("Missing data in command line.");
                return;
            }
            //Check the date is valid
            String dateOfPaymentString = st.nextToken();
            Date dateOfPayment = new Date(dateOfPaymentString);
            if (!dateOfPayment.isValid()) {
                System.out.println("Payment date invalid.");
                return;
            }

            //Send the payment
            //Check that the student is in the roster
            Student newStudent = new Student(name, majorM, 0);
            Student s = roster.findStudent(newStudent);
            if (null == s) {
                System.out.println("Student not in roster.");
                return;
            }
            boolean paidDone = roster.pay(s, paymentAmount, dateOfPayment);
            //Check if payment <= tuition due
            if (paidDone) {
                System.out.println("Payment applied.");
            } else {
                System.out.println("Amount is greater than amount due.");
            }
            return;
        }
        //We're adding a student as those are the only commands left -> Go deal with that
        addStudentCommandPartOne(cmd, name, majorM, st, roster);
    }
}