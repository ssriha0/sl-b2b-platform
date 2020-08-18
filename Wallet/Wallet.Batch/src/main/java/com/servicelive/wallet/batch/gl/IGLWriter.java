package com.servicelive.wallet.batch.gl;

import java.util.ArrayList;

import com.servicelive.wallet.batch.gl.vo.GLFeedVO;

public interface IGLWriter {

	/**
	 * 
	 * 
	 * @param glFeedItems 
	 * @param fileName 
	 * 
	 * @return 
	 * 
	 * @throws Exception 
	 */
	public String writeGLFeed(ArrayList<GLFeedVO> glFeedItems, String fileName) throws Exception;
}
