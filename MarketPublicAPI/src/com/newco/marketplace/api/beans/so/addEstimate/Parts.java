package com.newco.marketplace.api.beans.so.addEstimate;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class Parts {

		@XStreamImplicit(itemFieldName="part")
		private List<Part> part;

		public List<Part> getPart() {
			return part;
		}

		public void setPart(List<Part> part) {
			this.part = part;
		}

		
}
