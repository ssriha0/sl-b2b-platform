package com.servicelive.wallet.batch.mocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.servicelive.wallet.batch.gl.IGLWriter;
import com.servicelive.wallet.batch.gl.vo.GLFeedVO;

/**
 * Class MockGLWriter.
 */
public class MockGLWriter implements IGLWriter {
	
	/** glFeeds. */
	private List<String> glFeeds;

	/**
	 * getGlFeeds.
	 * 
	 * @return List<String>
	 */
	public List<String> getGlFeeds() {
		return glFeeds;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.IGLWriter#writeGLFeed(java.util.ArrayList, java.lang.String)
	 */
	public String writeGLFeed(ArrayList<GLFeedVO> glFeedItems, String fileName) throws Exception {
		
		glFeeds = new ArrayList<String>();
		
		Iterator<GLFeedVO> i = glFeedItems.iterator();

		while (i.hasNext()) {
			glFeeds.add(i.next().toString());
		}
		
		return "fake_file_name.txt";
	}

}
