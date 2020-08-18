package com.newco.marketplace.buyeroutboundnotification.vo;

public enum ExceptionCodesEnum {
	Unauthorized(401),
	Forbidden(403),
	NotFound(404),
	MethodNotAllowed(405),
	NotAcceptable(406),
	ProxyAuthenticationRequired(407),
	RequestTimeout(408),
	InternalServerError(500),
	BadGateway(502),
	ServiceUnavailable(503),
	GatewayTimeout(504),
	HTTPVersionNotSupported(505),
	None(11);

	public int id;

	private ExceptionCodesEnum(int i) {
		id = i;
	}

	public int GetID() {
		return id;
	}

	public boolean IsEmpty() {
		return this.equals(ExceptionCodesEnum.None);
	}

	public boolean Compare(Integer i) {
		return id == i;
	}

	public static ExceptionCodesEnum GetValue(Integer _id) {
		ExceptionCodesEnum[] As = ExceptionCodesEnum.values();
		for (int i = 0; i < As.length; i++) {
			if (As[i].Compare(_id))
				return As[i];
		}
		return ExceptionCodesEnum.None;
	}
	
	public static boolean containsValue(Integer _id) {
		ExceptionCodesEnum[] As = ExceptionCodesEnum.values();
		for (int i = 0; i < As.length; i++) {
			if (As[i].Compare(_id))
				return true;
		}
		return false;
	}
}
