package com.newco.marketplace.exception;

/**
 * <p>Title:SessionInvalidException </p>
 *
 * <p>Description:Exception class used throw if the session is invalid</p>
 * 
 * @author RHarini
 * Created on Jul 8, 2006
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SessionInvalidException extends MPException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**              
			* Default constructor.
			*/
		public SessionInvalidException() {
			super();
		}

		/**
		* Constructor that takes an error message.
		*
		* @param   message     A user-friendly description of the exception.
		*/
		public SessionInvalidException(String message) {
			super(message);
		}

		/**
		* Constructor that takes an error message.
		*
		* @param   message     A user-friendly description of the exception.
		* @param   code		   An error code of the exception.
		*/
		public SessionInvalidException(String message, String code) {
			super(message, code);
		}

		/**
		 * @param errorCode
		 * @param exception
		 */
		public SessionInvalidException(int errorCode, Throwable exception) {
			super("" + errorCode, exception);
		}

		/**
		* Constructor that takes an error message.
		*
		* @param   message		A user-friendly description of the exception.
		* @param   exception	A low level exception to be chained.
		*/

		public SessionInvalidException(String message, Throwable exception) {
			super(message, exception);
		}
		/**
		* Constructor that takes an error message.
		*
		* @param   message		A user-friendly description of the exception.
		* @param   exception	A low level exception to be chained.
		* @param   code			An error code of the exception.
		*/

		public SessionInvalidException(
			String message,
			String code,
			Throwable exception) {
			super(message, code, exception);
		}
		/**
		* Constructor that takes an error message.
		*
		* @param   exception	A low level exception to be chained.
		*/

		public SessionInvalidException(Throwable exception) {
			super(exception);
		}
		/**
		* Constructor that takes an error message.
		*
		* @param   exception	A low level exception to be chained.
		* @param   code			An error code of the exception.
		*/

		public SessionInvalidException(Throwable exception, String code) {
			super(exception, code);
		}
}
