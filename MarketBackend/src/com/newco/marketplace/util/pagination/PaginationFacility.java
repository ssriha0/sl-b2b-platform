package com.newco.marketplace.util.pagination;

import java.util.ArrayList;
import java.util.HashMap;

import com.newco.marketplace.persistence.daoImpl.pagination.PaginationDaoImpl;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.vo.PaginetVO;

/**
 * PaginationBOImpl.java - This class constructs the PaginationFacility object
 * with the following: 1. Paginets in the current bucket 2. Page Size paginets
 * to display the 25, 50, 100 feature 3. Next and Previous indicator
 * 
 * @author Siva
 * @version 1.0
 */

public class PaginationFacility {

	private String pageSizeConfiguration[] = { "25", "50", "100" };

	private static final int paginetsPerBucket = 5; // The number of page
													// numbers that user will
													// see. Our default is 5

	private PaginationDaoImpl paginationDao;

	private int pageSize; // The number of resultant records that user will
							// see. Our default is 25

	private ArrayList<PaginetVO> pageSizeBuckets; // The hot link numbers that
													// are available for the
													// user to set the page size

	private int totalRecords; // The total number of records available for the
								// given condition

	private int totalPaginets; // The total number of page numbers that is
								// available for the user

	private ArrayList<PaginetVO> currentResultSetBuckets; // The paginets that
															// are available for
															// the user to
															// select in the
															// current bucket.

	boolean previousIndicator; // This will let the front end know whether to
								// print the Previous hyperlink or not

	boolean nextIndicator; // This will let the front end know whether to print
							// the next hyperlink or not

	private PaginetVO previousPaginet; // 

	private PaginetVO nextPaginet;

	private ArrayList resultSetObjects; // This is the list that will hold all
										// the objects that will

	// comprise the current result set and will have the details of each record
	private int startIndex; // This is set by the client

	private int endIndex;// This is set by the client

	private PaginetVO currentPaginet;
	
	private PaginetVO lastPaginet;

	private ArrayList<PaginetVO> getPageSizeBuckets() {
		return pageSizeBuckets;
	}

	private void setPageSizeBuckets(ArrayList<PaginetVO> pageSizeBuckets) {
		this.pageSizeBuckets = pageSizeBuckets;
	}

	private int getTotalRecords() {
		return totalRecords;
	}

	private void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	private int getTotalPaginets() {
		return totalPaginets;
	}

	private void setTotalPaginets(int totalPaginets) {
		this.totalPaginets = totalPaginets;
	}

	private ArrayList<PaginetVO> getCurrentResultSetBucket() {
		return currentResultSetBuckets;
	}

	private void setCurrentResultSetBucketList(
			ArrayList<PaginetVO> currentResultSetBuckets) {
		this.currentResultSetBuckets = currentResultSetBuckets;
	}

	private boolean isPreviousIndicator() {
		return previousIndicator;
	}

	private void setPreviousIndicator(boolean previousIndicator) {
		this.previousIndicator = previousIndicator;
	}

	private boolean isNextIndicator() {
		return nextIndicator;
	}

	private void setNextIndicator(boolean nextIndicator) {
		this.nextIndicator = nextIndicator;
	}

	private PaginetVO getPreviousPaginet() {
		return previousPaginet;
	}

	private void setPreviousPaginet(PaginetVO previousPaginet) {
		this.previousPaginet = previousPaginet;
	}

	private PaginetVO getNextPaginet() {
		return nextPaginet;
	}

	private void setNextPaginet(PaginetVO nextPaginet) {
		this.nextPaginet = nextPaginet;
	}

	private ArrayList getResultSetObjects() {
		return resultSetObjects;
	}

	private void setResultSetObjects(ArrayList resultSetObjects) {
		this.resultSetObjects = resultSetObjects;
	}

	private int getStartIndex() {
		return startIndex;
	}

	private void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	private int getEndIndex() {
		return endIndex;
	}

	private void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	private PaginationDaoImpl getPaginationDao() {
		return paginationDao;
	}

	private void setPaginationDao(PaginationDaoImpl paginationDao) {
		this.paginationDao = paginationDao;
	}

	private int getPageSize() {
		return pageSize;
	}

	private void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	private ArrayList<PaginetVO> getCurrentResultSetBuckets() {
		return currentResultSetBuckets;
	}

	private String[] getPageSizeConfiguration() {
		return pageSizeConfiguration;
	}

	private void setPageSizeConfiguration(String[] pageSizeConfiguration) {
		this.pageSizeConfiguration = pageSizeConfiguration;
	}

	private PaginetVO getCurrentPaginet() {
		return currentPaginet;
	}

	private void setCurrentPaginet(PaginetVO currentPaginet) {
		this.currentPaginet = currentPaginet;
	}

	private void setCurrentResultSetBuckets(
			ArrayList<PaginetVO> currentResultSetBuckets) {
		this.currentResultSetBuckets = currentResultSetBuckets;
	}

	//NOT USED
	/*
	public int getResultSetCount(String countQueryName, Integer statusIds[], CriteriaMap criteriaMap) {
		int resultCount = 0;
		try {
			String str = (String) PaginationHash.getInstance().get(
					countQueryName);
			ContextValue
					.setContextFile("resources/spring/applicationContext.xml");
			System.out.println("loaded applicationContext.xml");
			paginationDao = (PaginationDaoImpl) BeanFactory
					.getBean("paginationDao");
			resultCount = paginationDao.getResultSetCount(str, statusIds, criteriaMap);

		} catch (DataServiceException dse) {
			dse.printStackTrace();
		}
		return resultCount;
	}
	*/
	
	/*
	 * This method finds out the first and last paginet in the current bucket
	 * given the start and end index @return HashMap first and last paginet in
	 * the bucket
	 */
	private HashMap<String, PaginetVO> findFirstLastPaginetInBucket() {
		HashMap<String, PaginetVO> map = new HashMap<String, PaginetVO>();
		int displayNumber = 0;
		// If the start index is 1, then that is the first paginet.
		if (startIndex == 1) {
			displayNumber = 1;
		} else {
			// Find the Page Number that is clicked by the end user using the
			// start index
			displayNumber = (startIndex - 1) / pageSize;
			boolean flag = false;
			while (flag == false) {
				// Divide the display number by the paginets per bucket. The mod
				// of the first paginet in the bucket is always 0
				int mod = displayNumber % paginetsPerBucket;
				if (mod == 0) {
					flag = true;
					displayNumber--;
					break;
				} else {
					displayNumber--;
				}

			}
			// Add 2 to the display number to offset the loop processing
			displayNumber = displayNumber + 2;
		}
		// Construct the first paginet in the bucket
		PaginetVO firstPaginet = new PaginetVO();
		firstPaginet.setDisplayName(new Integer(displayNumber).toString());
		firstPaginet.setStartIndex(((displayNumber - 1) * pageSize) + 1);
		firstPaginet.setEndIndex(firstPaginet.getStartIndex() + pageSize - 1);

		// Construct the last paginet in the bucket
		PaginetVO lastPaginet = new PaginetVO();
		int lastPaginetDisplayNumber = displayNumber + paginetsPerBucket - 1;
		lastPaginet.setDisplayName(new Integer(lastPaginetDisplayNumber)
				.toString());
		lastPaginet.setEndIndex((lastPaginetDisplayNumber * pageSize));
		lastPaginet.setStartIndex(lastPaginet.getEndIndex() - pageSize + 1);

		// Add them to the map
		map.put("FIRST_PAGINET_IN_BUCKET", firstPaginet);
		map.put("LAST_PAGINET_IN_BUCKET", lastPaginet);
		return map;
	}

	/*
	 * This method calculates the number of paginets in a bucket if the total
	 * records don't fill in the paginets to complete the bucket. For example,
	 * if the number of paginets per bucket is 5, the records per paginet is 25
	 * and the total number of records is 75, then the bucket has only 4
	 * paginets and not 5 as defined for a bucket. Thus method finds out how
	 * many paginets are required to create this bucket given as the number of
	 * records is less than the bucket record size (which is 125) @param int
	 * number of records @return int number of paginets
	 */
	private int calculateLastFewPaginets(int mySize) {

		int mod = mySize % pageSize;
		int temp = mySize / pageSize;
		int fewPaginets = 0;
		if (mod > 0) {
			fewPaginets = temp + 1;
		} else {
			fewPaginets = temp;
		}
		if( fewPaginets==0)
			fewPaginets=1;
		return fewPaginets;
	}

	/*
	 * This method calculates the total number of paginets given the total
	 * records @return void
	 */

	private void calculateTotalPaginets() {
		int mod = totalRecords % pageSize;
		int temp = totalRecords / pageSize;
		if (mod > 0) {
			totalPaginets = temp + 1;
		} else {
			totalPaginets = temp;
		}
		
		//Create last paginet 
		lastPaginet = new PaginetVO();
		lastPaginet.setStartIndex(((totalPaginets-1)*pageSize)+1);
		lastPaginet.setEndIndex(totalRecords);
		lastPaginet.setDisplayName(new Integer(totalPaginets).toString());

		
	}

	/*
	 * This method formulates the paginets for the current bucket that the user
	 * has requested for @return void
	 */
	private void formulateCurrentResultSetBucketList() {

		// 1. Instantiate the currentResultSet Buckets
		currentResultSetBuckets = new ArrayList<PaginetVO>();
		PaginetVO paginet = null;
		boolean isFirstPaginet = false;
		int paginetNumber = 1;
		int myPaginetsPerBucket = 0;
		int tempStartIndex = 0;

		// 2. Find the First and Last Paginets with the start and end index
		// provided by the client( Consumer )
		HashMap<String, PaginetVO> map = findFirstLastPaginetInBucket();
		PaginetVO firstPaginet = map.get("FIRST_PAGINET_IN_BUCKET");
		PaginetVO lastPaginet = map.get("LAST_PAGINET_IN_BUCKET");

		// 3. This flag is initialized to detect if the total records will not
		// fulfill the complete bucket size ( referred by paginetsPerBucket
		// variable)
		boolean unevenFlag = false;
		// 4. Find out the number of paginets in the bucket when the total
		// records is less than the end index of the paginet of the current
		// bucket
		if (totalRecords < (lastPaginet.getEndIndex())) {
			if (firstPaginet.getStartIndex()!=1){ 
				int myInt = totalRecords- firstPaginet.getStartIndex() + 1;
			myPaginetsPerBucket = calculateLastFewPaginets(myInt);
			}else
			{
				myPaginetsPerBucket = calculateLastFewPaginets(totalRecords);
			}
			// 4.1 Set the uneven flag to true as the number of records will not
			// fulfill the complete bucket size
			unevenFlag = true;
		} else {
			myPaginetsPerBucket = paginetsPerBucket;
		}
		// 5. Find out the startIndex of the first paginet in the bucket.
		if (startIndex > 1) {
			tempStartIndex = firstPaginet.getStartIndex();
			paginetNumber = new Integer(firstPaginet.getDisplayName())
					.intValue();
		} else {
			// This is the default initialization
			tempStartIndex = 1;
			isFirstPaginet = true;
		}
		// 6. Loop thru to form the paginets for the current bucket
		for (int r = 0; r < myPaginetsPerBucket; r++) {
			paginet = new PaginetVO();
			paginet.setCurrentPaginet(true);
			paginet.setDisplayName(new Integer(paginetNumber).toString());
			paginet.setStartIndex(tempStartIndex);

			// 6.1 It is here we set the end Index as the total number of
			// records. This is the last paginet in the bucket
			if (unevenFlag == true && (r == myPaginetsPerBucket - 1)) {
				paginet.setEndIndex(totalRecords);
			} else {
				paginet.setEndIndex(tempStartIndex + pageSize - 1);
			}

			// 6.2Set the current paginet attribute.
			if ((paginet.getStartIndex() == startIndex && paginet.getEndIndex() == endIndex)
					|| (isFirstPaginet == true))
			{
				// 6.3 Set the paginet attribute to true.
				paginet.setCurrentPaginet(true);
				// 6.4 Set the currentPaginet to the current paginet in the loop
				currentPaginet = paginet;
			} else {
				paginet.setCurrentPaginet(false);
			}
			// 6.5 reset the isFirstPaginet to false

			if (isFirstPaginet == true)
				isFirstPaginet = false;

			//
			/*
			 * if ( paginet.getStartIndex() == startIndex &&
			 * paginet.getEndIndex()== endIndex){
			 * paginet.setCurrentPaginet(true); currentPaginet = paginet; }
			 */
			// 6.6 Increment the start index and the paginet
			tempStartIndex = tempStartIndex + pageSize;
			paginetNumber++;
			currentResultSetBuckets.add(paginet);
		}

	}

	/*
	 * This method formulates the "Next" Paginet
	 */
	private void formulateNextPaginet() {
		PaginetVO lastPaginetInCurrentBucket = currentPaginet;
		if (lastPaginetInCurrentBucket!=null ) 
		{
			nextPaginet = new PaginetVO();
			nextPaginet.setCurrentPaginet(false);
			nextPaginet.setDisplayName("Next");
			nextPaginet.setStartIndex(lastPaginetInCurrentBucket.getEndIndex() + 1);
			nextPaginet.setEndIndex(lastPaginetInCurrentBucket.getEndIndex()
					+ pageSize);
		}
	}

	/*
	 * This method formulates the "Previous" Paginet
	 */
	private void formulatePreviousPaginet() {
		PaginetVO firstPaginetInCurrentBucket = currentPaginet;
		previousPaginet = new PaginetVO();
		previousPaginet.setCurrentPaginet(false);
		previousPaginet.setDisplayName("Previous");
		previousPaginet.setStartIndex(firstPaginetInCurrentBucket
				.getStartIndex()
				- pageSize);
		previousPaginet
				.setEndIndex(firstPaginetInCurrentBucket.getStartIndex() - 1);
	}

	/*
	 * This method checks if the user requested paginet is the last paginet in
	 * the bucket
	 */
	private boolean isLastPaginet() {
		int m = (startIndex - 1) / pageSize;
		int mod = m % paginetsPerBucket;
		if (mod == 0) {
			return true;
		} else
			return false;

	}

	/*
	 * This method formulates the paginets for the Page Size bucket, the one
	 * that displays 25 | 50 | 100
	 */
	private void formulatePageSizeBucketList() {
		pageSizeBuckets = new ArrayList<PaginetVO>();
		int size = pageSizeConfiguration.length;
		PaginetVO paginet = null;
		for (int i = 0; i < size; i++) {
			paginet = new PaginetVO();
			paginet.setDisplayName(pageSizeConfiguration[i]);
			paginet.setStartIndex(1);
			paginet.setEndIndex(new Integer(paginet.getDisplayName())
					.intValue());
			if (pageSize == paginet.getEndIndex()) {
				paginet.setCurrentPaginet(true);
			}
			pageSizeBuckets.add(paginet);

		}

	}

	private int findMyStartIndex(int myEndIndex) {
		boolean flag = false;
		if ((myEndIndex % pageSize == 0)) {
			myEndIndex--;
		}
		while (flag == false) {
			int mod = myEndIndex % pageSize;
			if (mod == 0) {
				flag = true;
				break;
			}
			myEndIndex--;

		}
		return myEndIndex + 1;

	}

	private boolean isEven(int index) {
		int mod = index % pageSize;
		if (mod == 0)
			return true;
		else
			return false;

	}

	/*
	 * This method tries to retain the user on the page that makes the most
	 * sense. The underlying Service Order records can change after the user is
	 * on the SOM page and sees the pagination. Here are the possible scenarios:
	 * 1. User clicks the paginet(page 7) that stands to retrieve records from
	 * 151 to 175 when the SOM has gone down to 150. Now the user will be shown
	 * page 6 as page 7 doesnt exist. 2. User clicks on the paginet(page 5) that
	 * stands to retrieve records from 101 to 110 when the SOM has gone down to
	 * 105. Now the user will be shown page 5 with records 101 to 105 3. User
	 * clicks on the paginet(page 5) that stands to retrieve records from 101 to
	 * 110 when the SOM has increased to 115. Now the user will be shown page 5
	 * with records 101 to 115 4. User clicks on the paginet(page 5) that stands
	 * to retrieve records from 101 to 110 when the SOM has increased to 160.
	 * Now the user will be shown page 5 with records 101 to 125 5. User clicks
	 * on the paginet(page 5) that stands to retrieve records from 101 to 110
	 * when the SOM has gone down to 100 or 80. Now the user will be shown page
	 * 4 with records 76 to 100 or 80 6. User clicks the paginet(page 7) that
	 * stands to retrieve records from 151 to 175 when the SOM has increased to
	 * 350. Now the user will be shown page 6. This method addresses scenarios
	 * #1,2,3,4 & 5. 6 is automatically taken care of.
	 */
	private void validateStartEndIndex(int startIndex, int endIndex) {

		int newStartIndex = startIndex;
		int newEndIndex = endIndex;
		if (startIndex != 0 || endIndex != 0) {
			boolean evenFlag = isEven(endIndex);
			if (evenFlag == true && endIndex > totalRecords
					&& startIndex > totalRecords) {
				// This happens when the user is on the last paginet and the
				// records have decreased on SOM DB. We will need to reset the
				// start and end index for the last paginet. We will show the
				// last paginet in this case. In this case, the user clicked
				// paginet will
				// not be the same as the new paginet
				newEndIndex = totalRecords;
				newStartIndex = findMyStartIndex(newEndIndex);
			}
			if (evenFlag == true && endIndex > totalRecords
					&& startIndex < totalRecords) {
				newEndIndex = totalRecords;
				newStartIndex = findMyStartIndex(newEndIndex);
			}
			int correctEndIndex = startIndex + pageSize - 1;

			if (evenFlag == false && endIndex > totalRecords
					&& startIndex < totalRecords) {
				// This happens when the user is on the last paginet that has
				// 105 records for example with 110 as end index.
				// find the new end Index of this page
				newStartIndex = startIndex;
				newEndIndex = totalRecords;
			}
			if (evenFlag == false && endIndex < totalRecords
					&& startIndex < totalRecords
					&& correctEndIndex > totalRecords) {
				// goes here for 115 and 160
				newEndIndex = totalRecords;
				newStartIndex = startIndex;
			}
			if (evenFlag == false && endIndex < totalRecords
					&& correctEndIndex < totalRecords) {
				// To deal 160- goes here for 160
				newStartIndex = startIndex;
				newEndIndex = newStartIndex + pageSize - 1;
			}

			if (evenFlag == false && totalRecords < startIndex) {
				// 100 record testing with 110 as endIndex
				newEndIndex = totalRecords;
				newStartIndex = findMyStartIndex(newEndIndex);

			}
		} else {
			this.endIndex = 1;
			this.startIndex = pageSize;

		}
		this.endIndex = newEndIndex;
		this.startIndex = newStartIndex;
	}

	/*
	 * This method gets the required pagination detail and is exposed to the
	 * client. The client needs to make this method call passing the total
	 * record count, page size ( ie 25, 50 or 100), the start index and the end
	 * index @param int totalRecordCount @param int pageSize @param int
	 * startIndex @param int endIndex @return ArrayList currentResultSetBuckets
	 */
	public PaginationVO getPaginationDetail(int totalRecordCount, int pageSize,
			int startIndex, int endIndex) {
		PaginationVO paginationVO = new PaginationVO();
	
		if ( totalRecordCount>0 ){
		if (totalRecordCount!=0 || pageSize !=0){
			this.totalRecords = totalRecordCount;
			this.pageSize = pageSize;

		// If the end index of the requested paginet is less than the total
		// records, give the user the exact paginet ( with revised end index and
		// start index)
		// If the end index of the requested paginet is greater than the total
		// records, give the user the last paginet ( with revised end index and
		// start index)
		validateStartEndIndex(startIndex, endIndex);

		calculateTotalPaginets();
		formulateCurrentResultSetBucketList();
		// We can calculate this if the next and previous indicators are true
		if(totalRecordCount>pageSize){
			formulateNextPaginet();
			formulatePreviousPaginet();
			calculatePreviousIndicator();
			calculateNextIndicator();
		}
		formulatePageSizeBucketList();

		paginationVO.setCurrentPaginet(currentPaginet);
		paginationVO.setCurrentResultSetBuckets(currentResultSetBuckets);
		paginationVO.setEndIndex(endIndex);
		paginationVO.setNextIndicator(nextIndicator);
		paginationVO.setNextPaginet(nextPaginet);
		paginationVO.setPageSize(pageSize);
		paginationVO.setPageSizeBuckets(pageSizeBuckets);
		paginationVO.setPreviousIndicator(previousIndicator);
		paginationVO.setPreviousPaginet(previousPaginet);
		paginationVO.setResultSetObjects(resultSetObjects);
		paginationVO.setStartIndex(startIndex);
		paginationVO.setTotalPaginets(totalPaginets);
		paginationVO.setTotalRecords(totalRecords);
		paginationVO.setLastPaginet(lastPaginet);

		}
		}
		return paginationVO;
	}

	/*
	 * This method calculates the availability of previous indicator based on
	 * the paginet and record set position
	 */
	private void calculatePreviousIndicator() {
		if (startIndex == 1) {
			previousIndicator = false;
		} else
			previousIndicator = true;
	}

	/*
	 * This method calculates the availability of next indicator based on the
	 * paginet and record set position
	 */
	private void calculateNextIndicator() {

		// if (endIndex < totalRecords || endIndex<=
		// ((pageSize*paginetsPerBucket)+1 ) ){
		if (endIndex < totalRecords) {
			nextIndicator = true;
		} else {
			nextIndicator = false;
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		ArrayList<PaginetVO> al = getCurrentResultSetBucket();

		sb.append("-------------------Current Paginet Bucket--------------\n");
		for (int e = 0; e < al.size(); e++) {
			PaginetVO p = al.get(e);
			sb.append(p.toString());
			sb.append("\n");
		}
		sb.append("-------------------------------------------------------");
		sb.append("\n");
		sb.append("Previous Indicator: " + isPreviousIndicator() + "\n");
		sb.append("Next Indicator: " + isNextIndicator() + "\n");
		ArrayList<PaginetVO> pg = getPageSizeBuckets();
		sb.append("--------Page Size Buckets------------------------------\n");
		for (int r = 0; r < pg.size(); r++) {
			PaginetVO pp = pg.get(r);
			sb.append(pp.toString());
			sb.append("\n");
		}
		sb.append("\n");
		sb.append("-------------------------------------------------------");
		sb.append("\n");
		PaginetVO nextPaginet = getNextPaginet();
		if(nextPaginet!=null) {
			sb.append("Next Paginet: " + nextPaginet.toString() + "\n");
		}
		PaginetVO previousPaginet = getPreviousPaginet();
		if(previousPaginet!=null) {
			sb.append("Previous Paginet: " + previousPaginet.toString() + "\n");
		}

		return sb.toString();

	}

	/*
	 * //temporary method private List<ArrayList<MyUserObject>>
	 * getMyUserObjects(){ ArrayList<MyUserObject> al = new ArrayList<MyUserObject>();
	 * int size = 25; MyUserObject myObject; for (int r=0;r<size;r++){ myObject =
	 * new MyUserObject(); myObject.setName("S "+r); al.add(myObject);
	 *  }
	 * 
	 * return (ArrayList)al; }
	 */

	public static void main(String g[]) {
		PaginationFacility pagination = new PaginationFacility();
		int myTotalRecords = 851; // Will be set one time on the session;
		int myStartIndex = 801;
		int myEndIndex = 825; // This could be derived based on the Page Size,
							// right?
		int pageSize = 25; // This is set by the client ( in the session and
							// Pagination Component retrieves from the session)

		pagination.getPaginationDetail(myTotalRecords, pageSize, myStartIndex,
				myEndIndex);
		System.out.println(pagination.toString());
		
	}

	public PaginetVO getLastPaginet() {
		return lastPaginet;
	}

	public void setLastPaginet(PaginetVO lastPaginet) {
		this.lastPaginet = lastPaginet;
	}

}
