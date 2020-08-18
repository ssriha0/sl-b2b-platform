package com.newco.marketplace.dto.api;

public class APIUrlPermission {

	private String url;
	private int id;
	private String name;
	private Boolean get;
	private Boolean put;
	private Boolean delete;
	private Boolean post;
	private String groupName;

	public APIUrlPermission(String url, Integer id) {
		this.url = url;
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setHttpMethod (String method) {
		if (method != null) {
			if (method.equalsIgnoreCase("GET")) {
				this.get = true;
			} else if (method.equalsIgnoreCase("PUT")) {
				this.put = true;
			} else if (method.equalsIgnoreCase("POST")) {
				this.post = true;
			} else if (method.equalsIgnoreCase("DELETE")) {
				this.delete = true;
			}
		}
	}

	public void setHttpMethod (String method, boolean value) {
		if (method.equalsIgnoreCase("GET")) {
			this.get = value;
		} else if (method.equalsIgnoreCase("PUT")) {
			this.put = value;
		} else if (method.equalsIgnoreCase("POST")) {
			this.post = value;
		} else if (method.equalsIgnoreCase("DELETE")) {
			this.delete = value;
		}
	}

	public boolean isHttpMethodAllowd(String method) {
		Boolean obj = null;
		if (method.equalsIgnoreCase("GET")) {
			obj = this.get;
		} else if (method.equalsIgnoreCase("PUT")) {
			obj = this.put;
		} else if (method.equalsIgnoreCase("POST")) {
			obj = this.post;
		} else if (method.equalsIgnoreCase("DELETE")) {
			obj = this.delete;
		}

		if (obj == null)
			return false;
		return obj.booleanValue();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getGet() {
		return get;
	}

	public void setGet(Boolean get) {
		this.get = get;
	}

	public Boolean getPut() {
		return put;
	}


	public void setPut(Boolean put) {
		this.put = put;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean getPost() {
		return post;
	}

	public void setPost(Boolean post) {
		this.post = post;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public boolean isAllowed(){
		if (this.delete != null && this.delete == true) 
			return true;
		
		if (this.post != null && this.post== true)
			return true;
		
		if (this.put != null && this.put == true)
			return true;
		
		if (this.get != null && this.get== true)
			return true;
		
		return false;
	}

}