package com.newco.marketplace.web.dto;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 * 
 * $Revision: 1.7 $ $Author: glacy $ $Date: 2008/04/26 01:13:44 $
 */

/*
 * Maintenance History 
 * $Log: SODetailsQuickLinksDTO.java,v $
 * Revision 1.7  2008/04/26 01:13:44  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.5.12.1  2008/04/23 11:41:29  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.6  2008/04/23 05:19:47  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.5  2008/02/14 23:44:46  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.4.10.1  2008/02/08 02:34:10  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.4  2007/12/20 14:28:00  mhaye05
 * fixed maintenance history
 *
 * Revision 1.3  2007/12/07 23:38:20  mhaye05 History added attribute cssStyle
 * 
 */
public class SODetailsQuickLinksDTO extends SerializedBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1392409627521812787L;
	private String onClick;
	private String action;
	private String jsp;
	private String height;
	private String cssStyle;

	/**
	 * empty constructor
	 */
	public SODetailsQuickLinksDTO() {}
	
	/**
	 * @param onClick
	 * @param action
	 * @param jsp
	 * @param height
	 * @param cssStyle
	 */
	public SODetailsQuickLinksDTO(String onClick, String action, String jsp,
			String height, String cssStyle) {
		this.onClick = onClick;
		this.action = action;
		this.jsp = jsp;
		this.height = height;
		this.cssStyle = cssStyle;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getJsp() {
		return jsp;
	}

	public void setJsp(String jsp) {
		this.jsp = jsp;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

}
