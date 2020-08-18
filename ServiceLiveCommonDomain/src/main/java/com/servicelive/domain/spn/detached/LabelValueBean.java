package com.servicelive.domain.spn.detached;

import java.io.Serializable;

/**
 * 
 *
 */
public class LabelValueBean implements Serializable, Comparable<LabelValueBean> {

	private static final long serialVersionUID = 20090513L;

	private String label;
	private String value;

	/**
	 * 
	 */
	public LabelValueBean() {
		super();
	}

	/**
	 * 
	 * @param label
	 * @param value
	 */
	public LabelValueBean(String label, String value) {
		this.label = label;
		this.value = value;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 
	 */
	public int compareTo(LabelValueBean o) {
		// Implicitly tests for the correct type, throwing
		// ClassCastException as required by interface
		String otherLabel = o.getLabel().toLowerCase();
		return this.getLabel().toLowerCase().compareTo(otherLabel);
	}
	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("LabelValueBean[");
		sb.append(this.label);
		sb.append(", ");
		sb.append(this.value);
		sb.append("]");
		return (sb.toString());
	}

	/**
	 * LabelValueBeans are equal if their values are both null or equal.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof LabelValueBean)) {
			return false;
		}

		LabelValueBean bean = (LabelValueBean) obj;
		int nil = (this.getValue() == null) ? 1 : 0;
		nil += (bean.getValue() == null) ? 1 : 0;

		if (nil == 2) {
			return true;
		} else if (nil == 1) {
			return false;
		} else {
			return this.getValue().equals(bean.getValue());
		}

	}

	/**
	 * The hash code is based on the object's value.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.getValue() == null) ? 17 : this.getValue().hashCode();
	}
}
