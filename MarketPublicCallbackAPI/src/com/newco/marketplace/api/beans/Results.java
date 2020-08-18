package com.newco.marketplace.api.beans;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("results")
public class Results {

	@XStreamImplicit(itemFieldName = "result")
	private List<Result> result;

	@XStreamImplicit(itemFieldName = "error")
	private List<ErrorResult> error;

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}

	public List<ErrorResult> getError() {
		return error;
	}

	public void setError(List<ErrorResult> error) {
		this.error = error;
	}

	public void addError(ErrorResult newError) {
		if (this.error == null)
			this.error = new ArrayList<ErrorResult>();
		this.error.add(newError);
	}

	public static final Results getSuccess() {
		Results results = new Results();
		List<Result> resultList = new ArrayList<Result>();
		Result result = new Result();
		result.setCode("");
		result.setMessage("");
		resultList.add(result);
		results.setResult(resultList);
		return results;
	}

	public static final Results getSuccess(String successMessage) {
		Results results = new Results();
		List<Result> resultList = new ArrayList<Result>();
		Result result = new Result();
		result.setCode("");
		result.setMessage(successMessage);
		resultList.add(result);
		results.setResult(resultList);
		return results;
	}

	public static final Results getSuccess(String code, String msg) {
		Results results = new Results();
		List<Result> resultList = new ArrayList<Result>();
		Result result = new Result();
		result.setCode(code);
		result.setMessage(msg);
		resultList.add(result);
		results.setResult(resultList);
		return results;
	}

	public static final Results getError(String msg, String code) {
		Results results = new Results();
		List<ErrorResult> resultList = new ArrayList<ErrorResult>();
		ErrorResult result = new ErrorResult();
		result.setCode(code);
		result.setMessage(msg);
		resultList.add(result);
		results.setError(resultList);
		return results;
	}

}
