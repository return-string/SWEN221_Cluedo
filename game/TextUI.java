package game;

import java.util.Scanner;

/**
 *
 * @author jamesba
 *
 */
public class TextUI {

	private Scanner scan;

	public TextUI(){
		scan = new Scanner(System.in);
	}

	/**Prints the inputed text to System.out
	  *@param text Text to print out
	  */
	public void printText(String text){
		System.out.println(text);
	}

	/**Prints the given message to System.out then
	  *asks user for an interger. Can be used to get
	  *user to select an option etc
	  *@param question Message to print out
	  *@return User inputed integer
	  */
	public int askInt(String question){
		System.out.print(question);
		return scan.nextInt();
	}

	public void printArray(String[] textArray){

	}

}
