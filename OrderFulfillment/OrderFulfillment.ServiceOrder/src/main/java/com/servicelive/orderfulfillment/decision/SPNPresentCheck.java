package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SPNPresentCheck extends AbstractServiceOrderDecision {

	private static final long serialVersionUID = 7258373364727565809L;
	/**
	 * Checks if the template has a spn associated with it
	 */
	@SuppressWarnings("unchecked")
	public String decide(OpenExecution execution) {
		ServiceOrder so = getServiceOrder(execution);
		if (null != so.getSpnId()) {
			return "HasSPN";
		} else {
			String templateName = (String) execution
					.getVariable(ProcessVariableUtil.TEMPLATE_NAME);
			if (null != templateName && null != so.getBuyerId()) {
				try {
					String templateXml = serviceOrderDao.getTemplateData(
							templateName, so.getBuyerId().intValue());
					if (null != templateXml) {
						Integer spnId = getSPNIdFromTemplateXML(templateXml);
						if (null != spnId) {
							return "HasSPN";
						} else {
							return "NoSPN";
						}
					} else {
						return "NoSPN";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "NoSPN";
	}

	/**
	 * Retrieves template id from template data using xstream
	 * @param xml
	 * @return
	 */
	public Integer getSPNIdFromTemplateXML(String xml) {
		XStream xstream = new XStream(new DomDriver());
		BuyerSOTemplateDTO dto = null;
		try {
			xstream.alias("buyerTemplate", BuyerSOTemplateDTO.class);
			dto = (BuyerSOTemplateDTO) xstream.fromXML(xml);
			if (null != dto) {
				if (dto.getIsNewSpn())
					return dto.getSpnId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}