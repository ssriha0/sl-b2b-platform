package com.servicelive.wallet.alert;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * Class AlertHashMap.
 */
public class AlertHashMap extends HashMap<String, Object> {

	/** serialVersionUID. */
	private static final long serialVersionUID = -6686142168610205921L;

	/* (non-Javadoc)
	 * @see java.util.AbstractMap#toString()
	 */
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (String key : this.keySet()) {

			Object val = this.get(key);
			sb.append(key);
			sb.append("=");
			sb.append(val);
			sb.append("|");
		}

		return sb.toString();
	}

}
