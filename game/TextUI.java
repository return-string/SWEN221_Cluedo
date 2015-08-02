package game;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author badiJames
 *
 */
public class TextUI {
	private static final String DIVIDE = "=========================";

	private Scanner scan;

	public TextUI(){
		scan = new Scanner(System.in);
	}

	/**Prints the inputed text to System.out
	  *@param text Text to print out
	  */
	public void printText(String text){
		System.out.println();
		System.out.print(text);
	}

	/**Prints the given message to System.out then
	  *asks user for an integer. Can be used to get
	  *user to select an option etc
	  *@param question Message to print out
	  *@return User inputed integer
	  */
	public int askInt(String question){
		System.out.println();
		System.out.print(question);
		return scan.nextInt();
	}

	public void printArray(String[] textArray){
		System.out.println();
		for(int i = 0; i < textArray.length; i++){
			System.out.printf("%d : %s\n", i+1, textArray[i]);
		}
	}
	
	public void printList(List<? extends String> list) {
		System.out.println();
		int i = 0;
		for(String s : list) {
			System.out.printf("%d : %s\n", i+1, s);
			i++;
		}
	}

	public void printDivide() {
		System.out.println();
		System.out.print(DIVIDE);
	}

	public int askIntBetween(String question, int min, int max){
		System.out.println();
		System.out.print(question);
		int answer = scan.nextInt();
		while(answer < min || answer > max){
			System.out.printf("Answer has to be between %d and %d inclusive. Please re-enter: ", min, max);
			answer = scan.nextInt();
		}
		return answer;
	}
}
