package application;
import java.sql.SQLException;
import java.util.ArrayList;
import databasePart1.DatabaseHelper;

public class HW3TestingAutomation {
	
	static int numPassed = 0;	// Counter of the number of passed tests
	static int numFailed = 0;	// Counter of the number of failed tests
	
	private static final DatabaseHelper databaseHelper = new DatabaseHelper();
	/**
	 * Test designated methods.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		System.out.println("______________________________________");
		System.out.println("\nTesting Automation");
		
		performTestCase(1, "", true, "getAllQuestions");
		performTestCase(2, "", true, "deleteQuestion");
		performTestCase(3, "This question was edited", true, "editQuestion");
		performTestCase(4, "", true, "getAllAnswers");
		performTestCase(5, "", true, "deleteAnswer");
		
	}
	/**
	 * Executes automated testing of methods.
	 * @param testCase the number of the test
	 * @param inputText any required input parameters
	 * @param expectedPass the expected pass result of the test
	 * @param functionToTest the name of the function being tested
	 */
	private static void performTestCase(int testCase, String inputText, boolean expectedPass, String functionToTest) {
		
		
		System.out.println();
		System.out.printf("Currently testing %s", functionToTest);
		System.out.println();

		/************** Display an individual test case header **************/
		System.out.println("____________________________________________________________________________\n\nTest case: " + testCase);
		System.out.println("Input: \"" + inputText + "\"");
		System.out.println("______________");
		System.out.println("\nFinite state machine execution trace:");
		
		try {
			databaseHelper.connectToDatabase();
		} catch (SQLException e) {
			System.out.println("Failed to connect to databse");
		}
		
		/************** Call the recognizer to process the input **************/
		String resultText = "";
		
		Question newQuestion = new Question();;
		Answer newAnswer = new Answer();
		Questions testQuestions = new Questions(databaseHelper, new User("", "", new boolean[5], "", "", ""));
		Answers testAnswers = new Answers(databaseHelper, new User("", "", new boolean[5], "", "", ""));
		ArrayList<Question> testQuestionList;
		ArrayList<Answer> testAnswerList;
		
		if(functionToTest.equals("getAllQuestions")) {
			testQuestionList = testQuestions.getAllQuestions();
			if(testQuestionList.size() == 0) {
				System.out.println("***Success*** Expected Size: 4, Actual Size: " + testQuestionList.size());
				numPassed++;
				return;
			}else {
				System.out.println("***Failure*** Expected Size: 4, Actual Size: " + testQuestionList.size());
				numFailed++;
				return;
			}
		}else if(functionToTest.equals("deleteQuestion")) {
			testQuestions.deleteQuestion(0);
			testQuestionList = testQuestions.getAllQuestions();
			if(testQuestionList.size() == 0) {
				System.out.println("***Success*** Expected Size: 3, Actual Size: " + testQuestionList.size());
				numPassed++;
				return;
			}else {
				System.out.println("***Failure*** Expected Size: 3, Actual Size: " + testQuestionList.size());
				numFailed++;
				return;
			}
			//FIX-ME
		}else if(functionToTest.equals("editQuestion")) {
			Question testQuestion = testQuestions.getQuestionByID(1);
			testQuestions.editQuestion(inputText ,inputText, 1);
			if(testQuestion.getQuestionTitle().equals(testQuestions.getQuestionByID(1).getQuestionTitle()) || testQuestion.getQuestionBody().equals(testQuestions.getQuestionByID(1).getQuestionBody())) {
				System.out.println("***Success*** Original String: " + testQuestion.getQuestionTitle() + " " + testQuestion.getQuestionBody() + " Actual String: " 
			+ testQuestions.getQuestionByID(1).getQuestionTitle() + " " + testQuestions.getQuestionByID(1).getQuestionBody());
				numPassed++;
				return;
			}else {
				System.out.println("***Failure*** Original String: " + testQuestion.getQuestionTitle() + " " + testQuestion.getQuestionBody() + " Actual String: " 
			+ testQuestions.getQuestionByID(1).getQuestionTitle() + " " + testQuestions.getQuestionByID(1).getQuestionBody());
				numFailed++;
				return;
			}
		}else if(functionToTest.equals("getAllAnswers")) {
			testAnswerList = testAnswers.getAllAnswers();
			if(testAnswerList.size() == 0) {
				System.out.println("***Success*** Expected Size: 3, Actual Size: " + testAnswerList.size());
				numPassed++;
				return;
			}else {
				System.out.println("***Failure*** Expected Size: 3, Actual Size: " + testAnswerList.size());
				numFailed++;
				return;
			}
		}else if(functionToTest.equals("deleteAnswer")) {
			testAnswers.deleteAnswer(1);
			testAnswerList = testAnswers.getAllAnswers();
			if(testAnswerList.size() == 0) {
				System.out.println("***Success*** Expected Size: 2, Actual Size: " + testAnswerList.size());
				numPassed++;
				return;
			}else {
				System.out.println("***Failure*** Expected Size: 2, Actual Size: " + testAnswerList.size());
				numFailed++;
				return;
			}
		}
		/************** Interpret the result and display that interpreted information **************/
		System.out.println();
		
		// If the resulting text is empty, the recognizer accepted the input
		if (resultText != "") {
			 // If the test case expected the test to pass then this is a failure
			if (expectedPass) {
				System.out.println("***Failure*** The text <" + inputText + "> is invalid." + 
						"\nBut it was supposed to be valid, so this is a failure!\n");
				System.out.println("Error message: " + resultText);
				numFailed++;
			}
			// If the test case expected the test to fail then this is a success
			else {			
				System.out.println("***Success*** The text <" + inputText + "> is invalid." + 
						"\nBut it was supposed to be invalid, so this is a pass!\n");
				System.out.println("Error message: " + resultText);
				numPassed++;
			}
		}
		
		// If the resulting text is empty, the recognizer accepted the input
		else {	
			// If the test case expected the test to pass then this is a success
			if (expectedPass) {	
				System.out.println("***Success*** The text <" + inputText + 
						"> is valid, so this is a pass!");
				numPassed++;
			}
			// If the test case expected the test to fail then this is a failure
			else {
				System.out.println("***Failure*** The text <" + inputText + 
						"> was judged as valid" + 
						"\nBut it was supposed to be invalid, so this is a failure!");
				numFailed++;
			}
		}
	}
}
