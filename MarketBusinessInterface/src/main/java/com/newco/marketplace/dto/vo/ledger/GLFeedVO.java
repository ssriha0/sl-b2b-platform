package com.newco.marketplace.dto.vo.ledger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sears.os.vo.SerializableBaseVO;

public class GLFeedVO extends SerializableBaseVO{

/**
	 * 
	 */
	private static final long serialVersionUID = -5746317659590828347L;
	/* 
 * @author spate05
 * This VO is for one row in the GL Feed. This VO maps to the cobol file format
 * provided by accounting
 * 
 * Note: - has been replaced with _ 
 * 
 *       ******************************************************************
      *                     SHC TEXT FILE FORMAT            11/2/2007  *
      *  USAGE NOTES:                                                  *
      *  1. RECORD LENGTH IS 300; RECORD FORMAT IS FIXED.              *
      *  2. SIMPLE NUMERIC FIELDS                                      *
      *     (LOCN, DIV, ACCT, CATEGORY, YEAR, WEEK, REV-YR, REV-WEEK   *
      *      AND RECORD-TYPE; ENTRY-TYPE SUPPLIED BY GL, ENTER A 0)    *
      *     - LEADING ZEROS NOT REQUIRED                               *
      *     - LEADING AND TRAILING BLANKS ACCEPTABLE                   *
      *     - MAY ONLY CONTAIN DIGITS AND BLANKS                       *
      *     - A BLANK FIELD IS TREATED AS ZERO (ERROR MESSAGE PRINTED) *
      *  3. COST, SELL-VALUE, STAT-AMT                                 *
      *     - LEADING ZEROS NOT REQUIRED                               *
      *     - LEADING AND TRAILING BLANKS ACCEPTABLE                   *
      *     - SIGN MAY BE LEADING OR TRAILING                          *
      *     - NO SIGN IMPLIES POSITIVE VALUE                           *
      *     - MAY ONLY CONTAIN DIGITS, SIGN, DECIMAL POINT, AND BLANKS.*
      *     - A BLANK FIELD IS TREATED AS ZERO (ERROR MESSAGE PRINTED) *
      ******************************************************************
       01  SHC-IPTF-TEXT.
           03  IPTX-LOCN               PIC  X(05).                      1-5
           03  IPTX-DIV                PIC  X(04).                      6-9
           03  IPTX-ACCT               PIC  X(05).                      10-14
           03  IPTX-ENTRY-SOURCE       PIC  X(03).                      15-17
           03  IPTX-CATEGORY           PIC  X(04).                      18-21
           03  IPTX-YEAR               PIC  X(04).                      22-25
           03  IPTX-WEEK               PIC  X(02).                      26-27
           03  IPTX-COST               PIC  X(17).                      28-44
           03  IPTX-CURR-CODE          PIC  X(03).                      45-47
           03  IPTX-SELL-VALUE         PIC  X(17).                      48-64
           03  IPTX-STAT-AMT           PIC  X(17).                      65-81
           03  IPTX-STAT-CODE          PIC  X(03).                      82-84
           03  IPTX-REV-FLAG           PIC  X(01).                      85-85
           03  IPTX-REV-YEAR           PIC  X(04).                      86-89
           03  IPTX-REV-WEEK           PIC  X(02).                      90-91
           03  IPTX-BACKTFLAG          PIC  X(01).                      92-92
           03  IPTX-SYSTEM-SW          PIC  X(02).                      93-94
           03  IPTX-DESCRIP            PIC  X(30).                      95-124
           03  IPTX-ENTRY-TYPE         PIC  X(03).                      125-127
           03  IPTX-RECORD-TYPE        PIC  X(02).                      128-129
           03  IPTX-REF-NBR-1          PIC  X(10).                      130-139
           03  IPTX-DOC-NBR            PIC  X(15).                      140-154
           03  IPTX-REF-NBR-2          PIC  X(10).                      155-164
           03  IPTX-MISC-1             PIC  X(20).                      165-184
           03  IPTX-MISC-2             PIC  X(20).                      185-204
           03  IPTX-MISC-3             PIC  X(20).                      205-224
           03  IPTX-TO-FROM            PIC  X(06).                      225-230
           03  IPTX-DOC-DATE           PIC  X(08).                      231-238
           03  IPTX-EXP-CODE           PIC  X(03).                      239-241
           03  IPTX-EMP-NBR            PIC  X(07).                      242-248
           03  IPTX-DET-TRAN-DATE      PIC  X(06).                      249-254
           03  IPTX-ORIG-ENTRY         PIC  X(15).                      255-269
           03  IPTX-REP-FLG            PIC  X(01).                      270-270
           03  IPTX-ORU                PIC  X(06).                      271-276
           03  FILLER                  PIC  X(24).                      277-300
 */	
	private final Log logger = LogFactory.getLog(getClass());
	private String LOCN;//           	  PIC  X(05).                      1-5
	private String DIV;//                PIC  X(04).                      6_9
	private String ACCT;//               PIC  X(05).                      10_14
	private String ENTRY_SOURCE;//       PIC  X(03).                      15_17
	private String CATEGORY;//           PIC  X(04).                      18_21
	private String YEAR;//               PIC  X(04).                      22_25
	private String WEEK;//               PIC  X(02).                      26_27
	private String COST;//               PIC  X(17).                      28_44
	private String CURR_CODE;//          PIC  X(03).                      45_47
	private String SELL_VALUE;//         PIC  X(17).                      48_64
	private String STAT_AMT;//           PIC  X(17).                      65_81
	private String STAT_CODE;//          PIC  X(03).                      82_84
	private String REV_FLAG;//           PIC  X(01).                      85_85
	private String REV_YEAR;//           PIC  X(04).                      86_89
	private String REV_WEEK;//           PIC  X(02).                      90_91
	private String BACKTFLAG;//          PIC  X(01).                      92_92
	private String SYSTEM_SW;//          PIC  X(02).                      93_94
	private String DESCRIP;//            PIC  X(30).                      95_124
	private String ENTRY_TYPE;//         PIC  X(03).                      125_127
	private String RECORD_TYPE;//        PIC  X(02).                      128_129
	private String REF_NBR_1;//          PIC  X(10).                      130_139
	private String DOC_NBR;//            PIC  X(15).                      140_154
	private String REF_NBR_2;//          PIC  X(10).                      155_164
	private String MISC_1;//             PIC  X(20).                      165_184
	private String MISC_2;//             PIC  X(20).                      185_204
	private String MISC_3;//             PIC  X(20).                      205_224
	private String TO_FROM;//            PIC  X(06).                      225_230
	private String DOC_DATE;//           PIC  X(08).                      231_238
	private String EXP_CODE;//           PIC  X(03).                      239_241
	private String EMP_NBR;//            PIC  X(07).                      242_248
	private String DET_TRAN_DATE;//      PIC  X(06).                      249_254
	private String ORIG_ENTRY;//         PIC  X(15).                      255_269
	private String REP_FLG;//            PIC  X(01).                      270_270
	private String ORU;//                PIC  X(06).                      271_276
	private String FILLER;//                  PIC  X(24).                      277_300

	public String getFILLER() {
		return FILLER;
	}
	public void setFILLER(String filler) {
		FILLER = filler;
	}
	public String getACCT() {
		return ACCT;
	}
	public void setACCT(String acct) {
		ACCT = acct;
	}
	public String getBACKTFLAG() {
		return BACKTFLAG;
	}
	public void setBACKTFLAG(String backtflag) {
		BACKTFLAG = backtflag;
	}
	public String getCATEGORY() {
		return CATEGORY;
	}
	public void setCATEGORY(String category) {
		CATEGORY = category;
	}
	public String getCOST() {
		return COST;
	}
	public void setCOST(String cost) {
		COST = cost;
	}
	public String getCURR_CODE() {
		return CURR_CODE;
	}
	public void setCURR_CODE(String curr_code) {
		CURR_CODE = curr_code;
	}
	public String getDESCRIP() {
		return DESCRIP;
	}
	public void setDESCRIP(String descrip) {
		DESCRIP = descrip;
	}
	public String getDET_TRAN_DATE() {
		return DET_TRAN_DATE;
	}
	public void setDET_TRAN_DATE(String det_tran_date) {
		DET_TRAN_DATE = det_tran_date;
	}
	public String getDIV() {
		return DIV;
	}
	public void setDIV(String div) {
		try {
			DIV = div;
		} catch (NullPointerException npe) {
			logger.warn("DIV came back null. Defaulting to 0000");
			DIV = "0000";
		}
	}
	public String getDOC_DATE() {
		return DOC_DATE;
	}
	public void setDOC_DATE(String doc_date) {
		DOC_DATE = doc_date;
	}
	public String getDOC_NBR() {
		return DOC_NBR;
	}
	public void setDOC_NBR(String doc_nbr) {
		DOC_NBR = doc_nbr;
	}
	public String getEMP_NBR() {
		return EMP_NBR;
	}
	public void setEMP_NBR(String emp_nbr) {
		EMP_NBR = emp_nbr;
	}
	public String getENTRY_SOURCE() {
		return ENTRY_SOURCE;
	}
	public void setENTRY_SOURCE(String entry_source) {
		ENTRY_SOURCE = entry_source;
	}
	public String getENTRY_TYPE() {
		return ENTRY_TYPE;
	}
	public void setENTRY_TYPE(String entry_type) {
		ENTRY_TYPE = entry_type;
	}
	public String getEXP_CODE() {
		return EXP_CODE;
	}
	public void setEXP_CODE(String exp_code) {
		EXP_CODE = exp_code;
	}
	public String getLOCN() {
		return LOCN;
	}
	public void setLOCN(String locn) {
		LOCN = locn;
	}
	public String getMISC_1() {
		return MISC_1;
	}
	public void setMISC_1(String misc_1) {
		MISC_1 = misc_1;
	}
	public String getMISC_2() {
		return MISC_2;
	}
	public void setMISC_2(String misc_2) {
		MISC_2 = misc_2;
	}
	public String getMISC_3() {
		return MISC_3;
	}
	public void setMISC_3(String misc_3) {
		MISC_3 = misc_3;
	}
	public String getORIG_ENTRY() {
		return ORIG_ENTRY;
	}
	public void setORIG_ENTRY(String orig_entry) {
		ORIG_ENTRY = orig_entry;
	}
	public String getORU() {
		return ORU;
	}
	public void setORU(String oru) {
		ORU = oru;
	}
	public String getRECORD_TYPE() {
		return RECORD_TYPE;
	}
	public void setRECORD_TYPE(String record_type) {
		RECORD_TYPE = record_type;
	}
	public String getREF_NBR_1() {
		return REF_NBR_1;
	}
	public void setREF_NBR_1(String ref_nbr_1) {
		REF_NBR_1 = ref_nbr_1;
	}
	public String getREF_NBR_2() {
		return REF_NBR_2;
	}
	public void setREF_NBR_2(String ref_nbr_2) {
		REF_NBR_2 = ref_nbr_2;
	}
	public String getREP_FLG() {
		return REP_FLG;
	}
	public void setREP_FLG(String rep_flg) {
		REP_FLG = rep_flg;
	}
	public String getREV_FLAG() {
		return REV_FLAG;
	}
	public void setREV_FLAG(String rev_flag) {
		REV_FLAG = rev_flag;
	}
	public String getREV_WEEK() {
		return REV_WEEK;
	}
	public void setREV_WEEK(String rev_week) {
		REV_WEEK = rev_week;
	}
	public String getREV_YEAR() {
		return REV_YEAR;
	}
	public void setREV_YEAR(String rev_year) {
		REV_YEAR = rev_year;
	}
	public String getSELL_VALUE() {
		return SELL_VALUE;
	}
	public void setSELL_VALUE(String sell_value) {
		SELL_VALUE = sell_value;
	}
	public String getSTAT_AMT() {
		return STAT_AMT;
	}
	public void setSTAT_AMT(String stat_amt) {
		STAT_AMT = stat_amt;
	}
	public String getSTAT_CODE() {
		return STAT_CODE;
	}
	public void setSTAT_CODE(String stat_code) {
		STAT_CODE = stat_code;
	}
	public String getSYSTEM_SW() {
		return SYSTEM_SW;
	}
	public void setSYSTEM_SW(String system_sw) {
		SYSTEM_SW = system_sw;
	}
	public String getTO_FROM() {
		return TO_FROM;
	}
	public void setTO_FROM(String to_from) {
		TO_FROM = to_from;
	}
	public String getWEEK() {
		return WEEK;
	}
	public void setWEEK(String week) {
		WEEK = week;
	}
	public String getYEAR() {
		return YEAR;
	}
	public void setYEAR(String year) {
		YEAR = year;
	}

/*
 * (non-Javadoc)
 * @see java.lang.Object#toString()
 * This function was put in so that there are less chances of error when 
 * formatting the file
 */
	@Override
	public String toString(){
		
		return 
			getLOCN() +
			getDIV() +
			getACCT() +
			getENTRY_SOURCE() +
			getCATEGORY() +
			getYEAR() +
			getWEEK() +
			getCOST() +
			getCURR_CODE() +
			getSELL_VALUE()+
			getSTAT_AMT() +
			getSTAT_CODE() +
			getREV_FLAG() +
			getREV_YEAR() +
			getREV_WEEK() +
			getBACKTFLAG() +
			getSYSTEM_SW() +
			getDESCRIP() + 
			getENTRY_TYPE() +
			getRECORD_TYPE() +
			getREF_NBR_1() +
			getDOC_NBR() +
			getREF_NBR_2() +
			getMISC_1() +
			getMISC_2() +
			getMISC_3() +
			getTO_FROM() +
			getDOC_DATE() +
			getEXP_CODE() +
			getEMP_NBR() +
			getDET_TRAN_DATE() +
			getORIG_ENTRY() +
			getREP_FLG() +
			getORU() +
			getFILLER();
		
	}
}
