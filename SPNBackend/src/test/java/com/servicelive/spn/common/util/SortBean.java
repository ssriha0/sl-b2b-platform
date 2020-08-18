package com.servicelive.spn.common.util;

import java.sql.Date;


/**
 * @author steve
 * Temp testing class to store data for sorting
 */
public class SortBean {
    private String _a;
    private String _b;
    private String _c;
    private Date _d;

    SortBean(String a, String b, String c, Date d) {
        _a = a;
        _b = b;
        _c = c;
        _d = d;
    }

    /**
     * 
     * @return String
     */
    public String getA() {
        return _a;
    }

    /**
     * 
     * @return String
     */
    public String getB() {
        return _b;
    }

    /**
     * 
     * @return String
     */
    public String getC() {
        return _c;
    }

    /**
	 * @return the d
	 */
	public Date getD() {
		return _d;
	}

	/**
	 * 
	 */
    @Override
	public String toString() {
        return "SortBean [A=" + _a + ", B=" + _b + ", C=" + _c + ", D=" + _d + "]";
    }


}
