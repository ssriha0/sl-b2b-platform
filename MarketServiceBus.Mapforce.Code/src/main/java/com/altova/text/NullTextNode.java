////////////////////////////////////////////////////////////////////////
//
// NullTextNode.java
//
// This file was generated by MapForce 2011sp1.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

package com.altova.text;

public class NullTextNode implements ITextNode {
	private static NullTextNode s_Instance = null;

	public static NullTextNode getInstance() {
		if (null == s_Instance)
			s_Instance = new NullTextNode();
		return s_Instance;
	}

	private AlwaysEmptyTextNodeList m_Children = new AlwaysEmptyTextNodeList();

	private final String m_Name = "";

	private final String m_Value = "";

	private NullTextNode() {
	}

	public void setName(String rhs) {
	} // no-op

	public void setValue(String rhs) {
	} // no-op

	public void setParent(ITextNode rhs) {
	} // no-op

	public void setNodeClass(byte rhs) {
	} // no-op

	public void setHasDecimalData(boolean rhs) {
	} // no-op

	public void setMaximumLength(int rhs) {
	} // no-op

	public void setNativeName(String rhs) {
	} // no-op

	public ITextNode getRoot() {
		return this;
	}

	public ITextNode getParent() {
		return this;
	}

	public boolean isNull() {
		return true;
	}

	public ITextNodeList getChildren() {
		return m_Children;
	}

	public String getName() {
		return m_Name;
	}

	public String getValue() {
		return m_Value;
	}

	public byte getNodeClass() {
		return 0;
	}

	public boolean hasDecimalData() {
		return false;
	}

	public int getMaximumLength() {
		return 0;
	}

	public String getNativeName() {
		return m_Name;
	}

	public String getPrecedingSeparators() {
		return "";
	}

	public String getFollowingSeparators() {
		return "";
	}

	public int getPositionInFather() {
		return 0;
	}
}