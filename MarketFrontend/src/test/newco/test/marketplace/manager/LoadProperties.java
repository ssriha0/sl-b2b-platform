package test.newco.test.marketplace.manager;

import java.util.ResourceBundle;

public class LoadProperties {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResourceBundle rb = null; 
		rb = ResourceBundle.getBundle("resources/properties/servicelive");
		System.out.println(rb.getString("MainService_Category_Validation"));
	}

}
