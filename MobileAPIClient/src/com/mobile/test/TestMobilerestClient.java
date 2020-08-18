package com.mobile.test;


public class TestMobilerestClient {
	
	static String consumerKey = "4ea7bf2a68d7aef22cf427421aaae9e9"; // Give your key here
	static String consumerSecret = "6f1e2664fac6e88efd750548d98c35be"; // Give your secret here
	//static String token = "ksTK79TUtwMRedwc2bAlYCV/rXqwE+cIqPEdYvb64W2bRyZt/XsV64afWWvpO6f3SoKRtN+BiX8Kz/HpjTYGwwxULIh++3l5nDlH3XYI3G4="; // Give your token here;
	static String apiBaseUrl = "http://151.149.159.235:8080/"; // Give your local URL here
	static String token = "";
	static String path = "mobile/v1.0/provider/authenticate";  // Give the path here
	//static String path = "mobile/v1.0/provider/10254/getProviderSOList";  // Give the path here
	static String oauth = "on"; // oauth
	static int method = 3; // 1. GET, 2.PUT, 3.POST,4.DELETE
	static String payload = "{\"loginProviderRequest\":{\"username\":\"homesweethometheatre\",\"password\":\"Test123!\",\"deviceId\":\"356938035645380912\",}}"; // XML request here as a string
	
	
	public static void main(String[] args) {
		JerseyRestClient jerseyRestClient = new JerseyRestClient(oauth, consumerKey, consumerSecret, apiBaseUrl,token);
		jerseyRestClient.setPath(path);
		
		switch(method){
			case 1: get(jerseyRestClient);
				break;
			case 2: put(jerseyRestClient);
				break;
			case 3: post(jerseyRestClient);
				break;
			case 4: delete(jerseyRestClient);
				break;
			default: System.out.println("Invalid HTTP Method");
		}
	}
	
	private static void get(JerseyRestClient jerseyRestClient) {
		String response = jerseyRestClient.get(String.class);
		System.out.println("response:::"+response);
	}
	
	
	// PUT 
	private static void put(JerseyRestClient jerseyRestClient) {
		String response = jerseyRestClient.put(String.class, payload);
		System.out.println("response:::"+response);
	}
	
	// POST 
	private static void post(JerseyRestClient jerseyRestClient) {
		String response = jerseyRestClient.post(String.class, payload);
		System.out.println("response:::"+response);
	}
	
	// DELETE 
	private static void delete(JerseyRestClient jerseyRestClient) {
		String response = jerseyRestClient.delete(String.class);
		System.out.println("response:::"+response);
	}
}
