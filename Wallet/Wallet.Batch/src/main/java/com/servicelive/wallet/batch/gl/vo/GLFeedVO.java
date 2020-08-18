package com.servicelive.wallet.batch.gl.vo;

import java.io.Serializable;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class GLFeedVO.
 */
public class GLFeedVO implements Serializable {

	/*
	 * @author spate05
	 * This VO is for one row in the GL Feed. This VO maps to the cobol file format
	 * provided by accounting
	 * 
	 * Note: - has been replaced with _
	 * 
	 * ******************************************************************
	 * SHC TEXT FILE FORMAT 11/2/2007 *
	 * USAGE NOTES: *
	 * 1. RECORD LENGTH IS 300; RECORD FORMAT IS FIXED. *
	 * 2. SIMPLE NUMERIC FIELDS *
	 * (LOCN, DIV, ACCT, CATEGORY, YEAR, WEEK, REV-YR, REV-WEEK *
	 * AND RECORD-TYPE; ENTRY-TYPE SUPPLIED BY GL, ENTER A 0) *
	 * - LEADING ZEROS NOT REQUIRED *
	 * - LEADING AND TRAILING BLANKS ACCEPTABLE *
	 * - MAY ONLY CONTAIN DIGITS AND BLANKS *
	 * - A BLANK FIELD IS TREATED AS ZERO (ERROR MESSAGE PRINTED) *
	 * 3. COST, SELL-VALUE, STAT-AMT *
	 * - LEADING ZEROS NOT REQUIRED *
	 * - LEADING AND TRAILING BLANKS ACCEPTABLE *
	 * - SIGN MAY BE LEADING OR TRAILING *
	 * - NO SIGN IMPLIES POSITIVE VALUE *
	 * - MAY ONLY CONTAIN DIGITS, SIGN, DECIMAL POINT, AND BLANKS.*
	 * - A BLANK FIELD IS TREATED AS ZERO (ERROR MESSAGE PRINTED) *
	 * *****************************************************************
	 * 01 SHC-IPTF-TEXT.
	 * 03 IPTX-LOCN PIC X(05). 1-5
	 * 03 IPTX-DIV PIC X(04). 6-9
	 * 03 IPTX-ACCT PIC X(05). 10-14
	 * 03 IPTX-ENTRY-SOURCE PIC X(03). 15-17
	 * 03 IPTX-CATEGORY PIC X(04). 18-21
	 * 03 IPTX-YEAR PIC X(04). 22-25
	 * 03 IPTX-WEEK PIC X(02). 26-27
	 * 03 IPTX-COST PIC X(17). 28-44
	 * 03 IPTX-CURR-CODE PIC X(03). 45-47
	 * 03 IPTX-SELL-VALUE PIC X(17). 48-64
	 * 03 IPTX-STAT-AMT PIC X(17). 65-81
	 * 03 IPTX-STAT-CODE PIC X(03). 82-84
	 * 03 IPTX-REV-FLAG PIC X(01). 85-85
	 * 03 IPTX-REV-YEAR PIC X(04). 86-89
	 * 03 IPTX-REV-WEEK PIC X(02). 90-91
	 * 03 IPTX-BACKTFLAG PIC X(01). 92-92
	 * 03 IPTX-SYSTEM-SW PIC X(02). 93-94
	 * 03 IPTX-DESCRIP PIC X(30). 95-124
	 * 03 IPTX-ENTRY-TYPE PIC X(03). 125-127
	 * 03 IPTX-RECORD-TYPE PIC X(02). 128-129
	 * 03 IPTX-REF-NBR-1 PIC X(10). 130-139
	 * 03 IPTX-DOC-NBR PIC X(15). 140-154
	 * 03 IPTX-REF-NBR-2 PIC X(10). 155-164
	 * 03 IPTX-MISC-1 PIC X(20). 165-184
	 * 03 IPTX-MISC-2 PIC X(20). 185-204
	 * 03 IPTX-MISC-3 PIC X(20). 205-224
	 * 03 IPTX-TO-FROM PIC X(06). 225-230
	 * 03 IPTX-DOC-DATE PIC X(08). 231-238
	 * 03 IPTX-EXP-CODE PIC X(03). 239-241
	 * 03 IPTX-EMP-NBR PIC X(07). 242-248
	 * 03 IPTX-DET-TRAN-DATE PIC X(06). 249-254
	 * 03 IPTX-ORIG-ENTRY PIC X(15). 255-269
	 * 03 IPTX-REP-FLG PIC X(01). 270-270
	 * 03 IPTX-ORU PIC X(06). 271-276
	 * 03 FILLER PIC X(24). 277-300
	 */
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(GLFeedVO.class.getName());

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5746317659590828347L;

	/** The ACCT. */
	private String ACCT;// PIC X(05). 10_14

	/** The BACKTFLAG. */
	private String BACKTFLAG;// PIC X(01). 92_92

	/** The CATEGORY. */
	private String CATEGORY;// PIC X(04). 18_21

	/** The COST. */
	private String COST;// PIC X(17). 28_44

	/** The CUR r_ code. */
	private String CURR_CODE;// PIC X(03). 45_47

	/** The DESCRIP. */
	private String DESCRIP;// PIC X(30). 95_124

	/** The DE t_ tra n_ date. */
	private String DET_TRAN_DATE;// PIC X(06). 249_254

	/** The DIV. */
	private String DIV;// PIC X(04). 6_9

	/** The DO c_ date. */
	private String DOC_DATE;// PIC X(08). 231_238

	/** The DO c_ nbr. */
	private String DOC_NBR;// PIC X(15). 140_154

	/** The EM p_ nbr. */
	private String EMP_NBR;// PIC X(07). 242_248

	/** The ENTR y_ source. */
	private String ENTRY_SOURCE;// PIC X(03). 15_17

	/** The ENTR y_ type. */
	private String ENTRY_TYPE;// PIC X(03). 125_127

	/** The EX p_ code. */
	private String EXP_CODE;// PIC X(03). 239_241

	/** The FILLER. */
	private String FILLER;// PIC X(24). 277_300

	/** The LOCN. */
	private String LOCN;// PIC X(05). 1-5

	/** The MIS c_1. */
	private String MISC_1;// PIC X(20). 165_184

	/** The MIS c_2. */
	private String MISC_2;// PIC X(20). 185_204

	/** The MIS c_3. */
	private String MISC_3;// PIC X(20). 205_224

	/** The ORI g_ entry. */
	private String ORIG_ENTRY;// PIC X(15). 255_269

	/** The ORU. */
	private String ORU;// PIC X(06). 271_276

	/** The RECOR d_ type. */
	private String RECORD_TYPE;// PIC X(02). 128_129

	/** The RE f_ nb r_1. */
	private String REF_NBR_1;// PIC X(10). 130_139

	/** The RE f_ nb r_2. */
	private String REF_NBR_2;// PIC X(10). 155_164

	/** The RE p_ flg. */
	private String REP_FLG;// PIC X(01). 270_270

	/** The RE v_ flag. */
	private String REV_FLAG;// PIC X(01). 85_85

	/** The RE v_ week. */
	private String REV_WEEK;// PIC X(02). 90_91

	/** The RE v_ year. */
	private String REV_YEAR;// PIC X(04). 86_89

	/** The SEL l_ value. */
	private String SELL_VALUE;// PIC X(17). 48_64

	/** The STA t_ amt. */
	private String STAT_AMT;// PIC X(17). 65_81

	/** The STA t_ code. */
	private String STAT_CODE;// PIC X(03). 82_84

	/** The SYSTE m_ sw. */
	private String SYSTEM_SW;// PIC X(02). 93_94

	/** The T o_ from. */
	private String TO_FROM;// PIC X(06). 225_230

	/** The WEEK. */
	private String WEEK;// PIC X(02). 26_27

	/** The YEAR. */
	private String YEAR;// PIC X(04). 22_25

	/**
	 * Gets the aCCT.
	 * 
	 * @return the aCCT
	 */
	public String getACCT() {

		return ACCT;
	}

	/**
	 * Gets the bACKTFLAG.
	 * 
	 * @return the bACKTFLAG
	 */
	public String getBACKTFLAG() {

		return BACKTFLAG;
	}

	/**
	 * Gets the cATEGORY.
	 * 
	 * @return the cATEGORY
	 */
	public String getCATEGORY() {

		return CATEGORY;
	}

	/**
	 * Gets the cOST.
	 * 
	 * @return the cOST
	 */
	public String getCOST() {

		return COST;
	}

	/**
	 * Gets the cUR r_ code.
	 * 
	 * @return the cUR r_ code
	 */
	public String getCURR_CODE() {

		return CURR_CODE;
	}

	/**
	 * Gets the dESCRIP.
	 * 
	 * @return the dESCRIP
	 */
	public String getDESCRIP() {

		return DESCRIP;
	}

	/**
	 * Gets the dE t_ tra n_ date.
	 * 
	 * @return the dE t_ tra n_ date
	 */
	public String getDET_TRAN_DATE() {

		return DET_TRAN_DATE;
	}

	/**
	 * Gets the dIV.
	 * 
	 * @return the dIV
	 */
	public String getDIV() {

		return DIV;
	}

	/**
	 * Gets the dO c_ date.
	 * 
	 * @return the dO c_ date
	 */
	public String getDOC_DATE() {

		return DOC_DATE;
	}

	/**
	 * Gets the dO c_ nbr.
	 * 
	 * @return the dO c_ nbr
	 */
	public String getDOC_NBR() {

		return DOC_NBR;
	}

	/**
	 * Gets the eM p_ nbr.
	 * 
	 * @return the eM p_ nbr
	 */
	public String getEMP_NBR() {

		return EMP_NBR;
	}

	/**
	 * Gets the eNTR y_ source.
	 * 
	 * @return the eNTR y_ source
	 */
	public String getENTRY_SOURCE() {

		return ENTRY_SOURCE;
	}

	/**
	 * Gets the eNTR y_ type.
	 * 
	 * @return the eNTR y_ type
	 */
	public String getENTRY_TYPE() {

		return ENTRY_TYPE;
	}

	/**
	 * Gets the eX p_ code.
	 * 
	 * @return the eX p_ code
	 */
	public String getEXP_CODE() {

		return EXP_CODE;
	}

	/**
	 * Gets the fILLER.
	 * 
	 * @return the fILLER
	 */
	public String getFILLER() {

		return FILLER;
	}

	/**
	 * Gets the lOCN.
	 * 
	 * @return the lOCN
	 */
	public String getLOCN() {

		return LOCN;
	}

	/**
	 * Gets the mIS c_1.
	 * 
	 * @return the mIS c_1
	 */
	public String getMISC_1() {

		return MISC_1;
	}

	/**
	 * Gets the mIS c_2.
	 * 
	 * @return the mIS c_2
	 */
	public String getMISC_2() {

		return MISC_2;
	}

	/**
	 * Gets the mIS c_3.
	 * 
	 * @return the mIS c_3
	 */
	public String getMISC_3() {

		return MISC_3;
	}

	/**
	 * Gets the oRI g_ entry.
	 * 
	 * @return the oRI g_ entry
	 */
	public String getORIG_ENTRY() {

		return ORIG_ENTRY;
	}

	/**
	 * Gets the oRU.
	 * 
	 * @return the oRU
	 */
	public String getORU() {

		return ORU;
	}

	/**
	 * Gets the rECOR d_ type.
	 * 
	 * @return the rECOR d_ type
	 */
	public String getRECORD_TYPE() {

		return RECORD_TYPE;
	}

	/**
	 * Gets the rE f_ nb r_1.
	 * 
	 * @return the rE f_ nb r_1
	 */
	public String getREF_NBR_1() {

		return REF_NBR_1;
	}

	/**
	 * Gets the rE f_ nb r_2.
	 * 
	 * @return the rE f_ nb r_2
	 */
	public String getREF_NBR_2() {

		return REF_NBR_2;
	}

	/**
	 * Gets the rE p_ flg.
	 * 
	 * @return the rE p_ flg
	 */
	public String getREP_FLG() {

		return REP_FLG;
	}

	/**
	 * Gets the rE v_ flag.
	 * 
	 * @return the rE v_ flag
	 */
	public String getREV_FLAG() {

		return REV_FLAG;
	}

	/**
	 * Gets the rE v_ week.
	 * 
	 * @return the rE v_ week
	 */
	public String getREV_WEEK() {

		return REV_WEEK;
	}

	/**
	 * Gets the rE v_ year.
	 * 
	 * @return the rE v_ year
	 */
	public String getREV_YEAR() {

		return REV_YEAR;
	}

	/**
	 * Gets the sEL l_ value.
	 * 
	 * @return the sEL l_ value
	 */
	public String getSELL_VALUE() {

		return SELL_VALUE;
	}

	/**
	 * Gets the sTA t_ amt.
	 * 
	 * @return the sTA t_ amt
	 */
	public String getSTAT_AMT() {

		return STAT_AMT;
	}

	/**
	 * Gets the sTA t_ code.
	 * 
	 * @return the sTA t_ code
	 */
	public String getSTAT_CODE() {

		return STAT_CODE;
	}

	/**
	 * Gets the sYSTE m_ sw.
	 * 
	 * @return the sYSTE m_ sw
	 */
	public String getSYSTEM_SW() {

		return SYSTEM_SW;
	}

	/**
	 * Gets the t o_ from.
	 * 
	 * @return the t o_ from
	 */
	public String getTO_FROM() {

		return TO_FROM;
	}

	/**
	 * Gets the wEEK.
	 * 
	 * @return the wEEK
	 */
	public String getWEEK() {

		return WEEK;
	}

	/**
	 * Gets the yEAR.
	 * 
	 * @return the yEAR
	 */
	public String getYEAR() {

		return YEAR;
	}

	/**
	 * Sets the aCCT.
	 * 
	 * @param acct the new aCCT
	 */
	public void setACCT(String acct) {

		ACCT = acct;
	}

	/**
	 * Sets the bACKTFLAG.
	 * 
	 * @param backtflag the new bACKTFLAG
	 */
	public void setBACKTFLAG(String backtflag) {

		BACKTFLAG = backtflag;
	}

	/**
	 * Sets the cATEGORY.
	 * 
	 * @param category the new cATEGORY
	 */
	public void setCATEGORY(String category) {

		CATEGORY = category;
	}

	/**
	 * Sets the cOST.
	 * 
	 * @param cost the new cOST
	 */
	public void setCOST(String cost) {

		COST = cost;
	}

	/**
	 * Sets the cUR r_ code.
	 * 
	 * @param curr_code the new cUR r_ code
	 */
	public void setCURR_CODE(String curr_code) {

		CURR_CODE = curr_code;
	}

	/**
	 * Sets the dESCRIP.
	 * 
	 * @param descrip the new dESCRIP
	 */
	public void setDESCRIP(String descrip) {

		DESCRIP = descrip;
	}

	/**
	 * Sets the dE t_ tra n_ date.
	 * 
	 * @param det_tran_date the new dE t_ tra n_ date
	 */
	public void setDET_TRAN_DATE(String det_tran_date) {

		DET_TRAN_DATE = det_tran_date;
	}

	/**
	 * Sets the dIV.
	 * 
	 * @param div the new dIV
	 */
	public void setDIV(String div) {
		if( div == null ) {
			logger.warn("DIV came back null. Defaulting to 0000");
			DIV = "0000";
		}
		DIV = div;
	}

	/**
	 * Sets the dO c_ date.
	 * 
	 * @param doc_date the new dO c_ date
	 */
	public void setDOC_DATE(String doc_date) {

		DOC_DATE = doc_date;
	}

	/**
	 * Sets the dO c_ nbr.
	 * 
	 * @param doc_nbr the new dO c_ nbr
	 */
	public void setDOC_NBR(String doc_nbr) {

		DOC_NBR = doc_nbr;
	}

	/**
	 * Sets the eM p_ nbr.
	 * 
	 * @param emp_nbr the new eM p_ nbr
	 */
	public void setEMP_NBR(String emp_nbr) {

		EMP_NBR = emp_nbr;
	}

	/**
	 * Sets the eNTR y_ source.
	 * 
	 * @param entry_source the new eNTR y_ source
	 */
	public void setENTRY_SOURCE(String entry_source) {

		ENTRY_SOURCE = entry_source;
	}

	/**
	 * Sets the eNTR y_ type.
	 * 
	 * @param entry_type the new eNTR y_ type
	 */
	public void setENTRY_TYPE(String entry_type) {

		ENTRY_TYPE = entry_type;
	}

	/**
	 * Sets the eX p_ code.
	 * 
	 * @param exp_code the new eX p_ code
	 */
	public void setEXP_CODE(String exp_code) {

		EXP_CODE = exp_code;
	}

	/**
	 * Sets the fILLER.
	 * 
	 * @param filler the new fILLER
	 */
	public void setFILLER(String filler) {

		FILLER = filler;
	}

	/**
	 * Sets the lOCN.
	 * 
	 * @param locn the new lOCN
	 */
	public void setLOCN(String locn) {

		LOCN = locn;
	}

	/**
	 * Sets the mIS c_1.
	 * 
	 * @param misc_1 the new mIS c_1
	 */
	public void setMISC_1(String misc_1) {

		MISC_1 = misc_1;
	}

	/**
	 * Sets the mIS c_2.
	 * 
	 * @param misc_2 the new mIS c_2
	 */
	public void setMISC_2(String misc_2) {

		MISC_2 = misc_2;
	}

	/**
	 * Sets the mIS c_3.
	 * 
	 * @param misc_3 the new mIS c_3
	 */
	public void setMISC_3(String misc_3) {

		MISC_3 = misc_3;
	}

	/**
	 * Sets the oRI g_ entry.
	 * 
	 * @param orig_entry the new oRI g_ entry
	 */
	public void setORIG_ENTRY(String orig_entry) {

		ORIG_ENTRY = orig_entry;
	}

	/**
	 * Sets the oRU.
	 * 
	 * @param oru the new oRU
	 */
	public void setORU(String oru) {

		ORU = oru;
	}

	/**
	 * Sets the rECOR d_ type.
	 * 
	 * @param record_type the new rECOR d_ type
	 */
	public void setRECORD_TYPE(String record_type) {

		RECORD_TYPE = record_type;
	}

	/**
	 * Sets the rE f_ nb r_1.
	 * 
	 * @param ref_nbr_1 the new rE f_ nb r_1
	 */
	public void setREF_NBR_1(String ref_nbr_1) {

		REF_NBR_1 = ref_nbr_1;
	}

	/**
	 * Sets the rE f_ nb r_2.
	 * 
	 * @param ref_nbr_2 the new rE f_ nb r_2
	 */
	public void setREF_NBR_2(String ref_nbr_2) {

		REF_NBR_2 = ref_nbr_2;
	}

	/**
	 * Sets the rE p_ flg.
	 * 
	 * @param rep_flg the new rE p_ flg
	 */
	public void setREP_FLG(String rep_flg) {

		REP_FLG = rep_flg;
	}

	/**
	 * Sets the rE v_ flag.
	 * 
	 * @param rev_flag the new rE v_ flag
	 */
	public void setREV_FLAG(String rev_flag) {

		REV_FLAG = rev_flag;
	}

	/**
	 * Sets the rE v_ week.
	 * 
	 * @param rev_week the new rE v_ week
	 */
	public void setREV_WEEK(String rev_week) {

		REV_WEEK = rev_week;
	}

	/**
	 * Sets the rE v_ year.
	 * 
	 * @param rev_year the new rE v_ year
	 */
	public void setREV_YEAR(String rev_year) {

		REV_YEAR = rev_year;
	}

	/**
	 * Sets the sEL l_ value.
	 * 
	 * @param sell_value the new sEL l_ value
	 */
	public void setSELL_VALUE(String sell_value) {

		SELL_VALUE = sell_value;
	}

	/**
	 * Sets the sTA t_ amt.
	 * 
	 * @param stat_amt the new sTA t_ amt
	 */
	public void setSTAT_AMT(String stat_amt) {

		STAT_AMT = stat_amt;
	}

	/**
	 * Sets the sTA t_ code.
	 * 
	 * @param stat_code the new sTA t_ code
	 */
	public void setSTAT_CODE(String stat_code) {

		STAT_CODE = stat_code;
	}

	/**
	 * Sets the sYSTE m_ sw.
	 * 
	 * @param system_sw the new sYSTE m_ sw
	 */
	public void setSYSTEM_SW(String system_sw) {

		SYSTEM_SW = system_sw;
	}

	/**
	 * Sets the t o_ from.
	 * 
	 * @param to_from the new t o_ from
	 */
	public void setTO_FROM(String to_from) {

		TO_FROM = to_from;
	}

	/**
	 * Sets the wEEK.
	 * 
	 * @param week the new wEEK
	 */
	public void setWEEK(String week) {

		WEEK = week;
	}

	/**
	 * Sets the yEAR.
	 * 
	 * @param year the new yEAR
	 */
	public void setYEAR(String year) {

		YEAR = year;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * This function was put in so that there are less chances of error when
	 * formatting the file
	 */
	public String toString() {

		return getLOCN() + getDIV() + getACCT() + getENTRY_SOURCE() + getCATEGORY() + getYEAR() + getWEEK() + getCOST() + getCURR_CODE() + getSELL_VALUE() + getSTAT_AMT()
			+ getSTAT_CODE() + getREV_FLAG() + getREV_YEAR() + getREV_WEEK() + getBACKTFLAG() + getSYSTEM_SW() + getDESCRIP() + getENTRY_TYPE() + getRECORD_TYPE() + getREF_NBR_1()
			+ getDOC_NBR() + getREF_NBR_2() + getMISC_1() + getMISC_2() + getMISC_3() + getTO_FROM() + getDOC_DATE() + getEXP_CODE() + getEMP_NBR() + getDET_TRAN_DATE()
			+ getORIG_ENTRY() + getREP_FLG() + getORU() + getFILLER();

	}
}
