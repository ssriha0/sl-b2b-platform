/**
 * 
 */
package com.newco.marketplace.gwt.providersearch.client;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


/**
 * @author HOZA
 *
 */
public class HeaderFieldComparator implements Comparator {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	Map currentProvidersMap = new HashMap();
	String sortKey = null;
	String sortOrder = null;
	public HeaderFieldComparator(Map cMap, String skey,String sorder) {
		sortKey = skey;
		sortOrder = sorder;
		currentProvidersMap = cMap;
	}
	
	public int compare(Object arg0, Object arg1) {
		if ((arg0 instanceof Integer) && (arg1 instanceof Integer)) {
			Integer a0 = (Integer) arg0;
			Integer a1 = (Integer) arg1;
			SimpleProviderSearchProviderResultVO voleft = (SimpleProviderSearchProviderResultVO) currentProvidersMap.get(a0);
			SimpleProviderSearchProviderResultVO voright = (SimpleProviderSearchProviderResultVO) currentProvidersMap.get(a1);
			if (sortKey != null) {
				if (GWTProviderSearchConstants.PERCENTMTACH_COLUMN.equals(sortKey)) {
					if ("ASC".equals(sortOrder)) {
						return (int) (voleft.getPercentageMatch().doubleValue() - voright.getPercentageMatch().doubleValue());
					} else if ("DESC".equals(sortOrder)) {
						return (int) (voright.getPercentageMatch().doubleValue() - voleft.getPercentageMatch().doubleValue());
					}
				} else if (GWTProviderSearchConstants.DISTANCE_COLUMN.equals(sortKey)) {
					if ("ASC".equals(sortOrder)) {
						if (voleft.getDistance().doubleValue() < voright.getDistance().doubleValue())
							return 1;
						else if (voleft.getDistance().doubleValue() > voright.getDistance().doubleValue())
							return -1;
						else
							return 0;
					} else if ("DESC".equals(sortOrder)) {

						if (voleft.getDistance().doubleValue() > voright.getDistance().doubleValue())
							return 1;
						else if (voleft.getDistance().doubleValue() < voright.getDistance().doubleValue())
							return -1;
						else
							return 0;
					}
				} else if (GWTProviderSearchConstants.RATINGS_COLUMN.equals(sortKey)) {
					if ("ASC".equals(sortOrder)) {
						if (voleft.getProviderStarRating() > voright.getProviderStarRating())
							return 1;
						else if (voleft.getProviderStarRating() < voright.getProviderStarRating())
							return -1;
						else
							return 0;
					} else if ("DESC".equals(sortOrder)) {

						if (voleft.getProviderStarRating() < voright.getProviderStarRating())
							return 1;
						else if (voleft.getProviderStarRating() > voright.getProviderStarRating())
							return -1;
						else
							return 0;
					}
				} else if (GWTProviderSearchConstants.ORDERS_COLUMN.equals(sortKey)) {
					if ("ASC".equals(sortOrder)) {
						return voleft.getTotalSOCompleted().intValue()	- voright.getTotalSOCompleted().intValue();
					} else if ("DESC".equals(sortOrder)) {

						return voright.getTotalSOCompleted().intValue() 	- voleft.getTotalSOCompleted().intValue();
					}
				} else if (GWTProviderSearchConstants.PROVIDER_COLUMN.equals(sortKey)) {
					
					String lname = voleft.getProviderLastName() + "," + voleft.getProviderFirstName();
					String rname = voright.getProviderLastName() + "," + voright.getProviderFirstName();
					
					if ("ASC".equals(sortOrder)) {
						return rname.compareTo(lname);
					} else if ("DESC".equals(sortOrder)) {
						return rname.compareTo(lname);
					}
				}
			}
			else {
				return 0;
			}
		}
		
		return 0;
	}

}
