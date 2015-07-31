/**
 * 
 */
package game;

/** Exception thrown when the Game checks any of its fields
 * and finds them out of sync.  
 * @author Vicki
 *
 */
public class ParameterDesyncException extends Exception {

	/**
	 * 
	 */
	public ParameterDesyncException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ParameterDesyncException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ParameterDesyncException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ParameterDesyncException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ParameterDesyncException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
