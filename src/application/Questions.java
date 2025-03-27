package application;

import databasePart1.DatabaseHelper;
import java.util.ArrayList;
import java.sql.SQLException;
import java.time.LocalDateTime;
/**
 * @author John Gallagher
 * @author Cristina Hooe
 * @author Joshua Lee
 * @author Kylie Kim
 * @author Sajjad Sheykhi
 */
public class Questions {
	private ArrayList<Question> questions;
	private ArrayList<Question> replies;
	private final DatabaseHelper databaseHelper;
	private static User user;

	public Questions(DatabaseHelper databaseHelper, User user) {
		this.databaseHelper = databaseHelper;
		this.user = user;
	}

	// Add a new question with proper validation checks
	public int addQuestion(String questionTitle, String questionBody, Question question, User user) {
		int questionID = -1;
		try {
			questionID = databaseHelper.addQuestion(questionTitle, questionBody, question, user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questionID;
	}
	
	// Retrieve userName associated with a specific questionID
	public String getUserFromQuestionID(int questionID) {
		String UserName = databaseHelper.getUserFromQuestionID(questionID);
		return UserName;
	}
	
	/**
     * Generates a list of all questions in database.
     * 
     * @return A list of all questions in database
     */
	public ArrayList<Question> getAllQuestions() {
		questions = databaseHelper.getAllQuestions(user);
		return questions;
	}
	
	// Retrieve all questions
	public ArrayList<Question> getAllReplies() {
		replies = databaseHelper.getAllReplies();
		return replies;
	}

	// Retrieve all unresolved questions along with their potential answers
	public ArrayList<Question> getUnresolvedQuestions() {
		questions = databaseHelper.getUnresolvedQuestions();
		return questions;
	}

	// Retrieve only answered questions
	public ArrayList<Question> getAnsweredQuestions() {
		questions = databaseHelper.getAnsweredQuestions();
		return questions;
	}

	// Retrieve only unanswered questions
	public ArrayList<Question> getUnansweredQuestions() {
		questions = databaseHelper.getUnansweredQuestions();
		return questions;
	}

	// Retrieve questions posted after a given timestamp
	public ArrayList<Question> getRecentQuestions(LocalDateTime time) {
		questions = databaseHelper.getRecentQuestions(time);
		return questions;
	}

	// Retrieve questions by a specific user
	public ArrayList<Question> getQuestionsByUserName(String username) {
		questions = databaseHelper.getQuestionsByUserName(username);
		return questions;
	}

	// Count unread potential answers for each unresolved question
	public int countUnreadPotentialAnswers(int questionID) {
		return databaseHelper.countUnreadPotentialAnswers(questionID);
	}

	
	/**
     * Commented out as not currently used in StudentQuestionAnswers.java
     
	// Retrieve only resolved answers and link them to their respective questions
	public ArrayList<Question> onlyResolvedAnswers() {
		return databaseHelper.getResolvedAnswersWithQuestions();
	}
	
	*/

	// Create a new question based on an old question
	public int createNewQuestionfromOld(String newQuestionTitle, String newQuestionBody, Question newQuestion, User user, int oldQuestionID) {
		int questionID = databaseHelper.createNewQuestionfromOld(newQuestionTitle, newQuestionBody, newQuestion, user, oldQuestionID);
		return questionID;
	}

	// Soft delete a question (marks it as deleted instead of removing it)
	public void markQuestionDeleted(int questionID) {
		databaseHelper.markQuestionDeleted(questionID);
	}

	/**
	    * Deletes designated question from database.
	    * 
	    * @param questionID the index in the list of the question to delete.
	    */
	public void deleteQuestion(int questionID) {
		databaseHelper.deleteQuestion(questionID);
	}
	
	public void deleteRepliesForQuestion(int questionID) {
		databaseHelper.deleteRepliesForQuestion(questionID);
	}

	// Retrieve a question by its ID
	public Question getQuestionByID(int questionID) {
		return databaseHelper.getQuestionByID(questionID);
	}

	/**
	 * Edits an existing question in the database
	 * 
	 * @param modifiedQuestionTitle the new title text for the question being edited
	 * @param modifiedQuestionBody the new body text for the question being edited
	 * @param questionID the index in the list of the question being edited
	 */
	public void editQuestion(String modifiedQuestionTitle, String modifiedQuestionBody, int questionID) {
		databaseHelper.editQuestion(modifiedQuestionTitle, modifiedQuestionBody, questionID);
	}
	
	public int addReply(String replyText, int parentQuestionID, Question questionReply, User student, String replyingTo) {
        int replyID = databaseHelper.addReply(replyText, parentQuestionID, questionReply, student, replyingTo);
        System.out.printf("Reply ID is %d\n", replyID);
        return replyID;
    }
}
