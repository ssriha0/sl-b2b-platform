package com.newco.marketplace.test;

import java.util.List;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.persistence.iDao.onsiteVisit.IOnsiteVisitDao;

public class SoOnsiteVisitTest {

	public static void main(String[] args) {
		IOnsiteVisitDao IOnsiteVisitDao = (IOnsiteVisitDao) MPSpringLoaderPlugIn.getCtx()
				.getBean("onsiteVisitDao");
		SOOnsiteVisitVO soOnsiteVisitVO = new SOOnsiteVisitVO();
		String soid = "198-0343-6317-61";
		try {
			List<SOOnsiteVisitVO> results = IOnsiteVisitDao
					.getVisitResults(soid);
			for (SOOnsiteVisitVO soonsitevisitrecord : results) {
				if (soonsitevisitrecord != null) {
					System.out.println("\nThe new record"
							+ soonsitevisitrecord.toString());
				}

			}

		} catch (Exception e) {
			e.getMessage();
		}

	}
}