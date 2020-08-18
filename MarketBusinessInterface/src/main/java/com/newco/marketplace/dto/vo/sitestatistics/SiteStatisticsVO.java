package com.newco.marketplace.dto.vo.sitestatistics;

import com.sears.os.vo.SerializableBaseVO;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */
public class SiteStatisticsVO extends SerializableBaseVO implements Comparable<SiteStatisticsVO>{

	private static final long serialVersionUID = -2162516487358952104L;
	private Integer satisfactionRating;
	private Integer registeredServicePros;
	
	public Integer getSatisfactionRating() {
		return satisfactionRating;
	}
	public void setSatisfactionRating(Integer satisfactionRating) {
		this.satisfactionRating = satisfactionRating;
	}
	public Integer getRegisteredServicePros() {
		return registeredServicePros;
	}
	public void setRegisteredServicePros(Integer registeredServicePros) {
		this.registeredServicePros = registeredServicePros;
	}
	
	public int compareTo(SiteStatisticsVO voToCompare) {
		if (null != voToCompare) {
			if ( voToCompare.getRegisteredServicePros().intValue() ==  this.registeredServicePros.intValue() &&
				  voToCompare.getSatisfactionRating().intValue() == this.satisfactionRating.intValue() ) {  
				return 0;
			}
			return -1;
		}
		return -1;
	}
	
	
}
