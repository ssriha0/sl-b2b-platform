package org.hdiv.filter;

import javax.servlet.http.HttpServletRequest;

import org.hdiv.dataComposer.IDataComposer;
import org.hdiv.exception.HDIVException;
import org.hdiv.util.HDIVUtil;

public class SLValidatorHelperRequest extends SLAbstractValidatorHelper {

	/**
	 * Data composer
	 */
	private IDataComposer dataComposer;

	/**
	 * Initialization of the objects needed for the validation process.
	 * 
	 * @param request HTTP servlet request
	 * @throws HDIVException if there is an initialization error.
	 */
	public void init(HttpServletRequest request) {

		try {
			super.init(request);
			this.dataComposer = (IDataComposer) super.getBeanFactory().getBean(HDIVUtil.getDataComposerName());
			request.setAttribute("dataComposer", this.dataComposer);

		} catch (Exception e) {
			String errorMessage = HDIVUtil.getMessage("helper.init");
			throw new HDIVException(errorMessage, e);
		}
	}

	/**
	 * It is called in the pre-processing stage of each user request.
	 */
	public void startPage() {
		this.dataComposer.startPage();
	}

	/**
	 * Handle the storing of HDIV's state, which is done after action
	 * invocation.
	 * 
	 * @param request http request
	 * @throws Exception if there is an error in storing process.
	 */
	public void endPage() {

		if (super.getRequestWrapper().getSession(false) != null) {
			this.dataComposer.endPage();
		}
	}

}
