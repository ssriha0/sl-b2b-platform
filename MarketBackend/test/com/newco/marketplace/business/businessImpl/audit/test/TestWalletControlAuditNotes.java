package com.newco.marketplace.business.businessImpl.audit.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.audit.AuditProfileBOImpl;
import com.newco.marketplace.business.businessImpl.audit.WalletControlAuditNotesBOImpl;
import com.newco.marketplace.business.iBusiness.audit.IAuditProfileBO;
import com.newco.marketplace.persistence.daoImpl.audit.WalletControlAuditNotesDaoImpl;
import com.newco.marketplace.persistence.iDao.audit.IWalletControlAuditNotesDao;
import com.newco.marketplace.vo.audit.TransferReasonCodeVO;
import com.newco.marketplace.vo.audit.WalletControlAuditVO;
import com.newco.marketplace.vo.provider.VendorNotesVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
@Ignore
public class TestWalletControlAuditNotes {
	WalletControlAuditNotesBOImpl walletControlAuditNotesBO;
	IAuditProfileBO bo;
	IWalletControlAuditNotesDao dao;

	@Before
	public void setUp() {

		walletControlAuditNotesBO = new WalletControlAuditNotesBOImpl();
		bo = mock(AuditProfileBOImpl.class);
		walletControlAuditNotesBO.setAuditProfileBoTarget(bo);
		dao=mock(WalletControlAuditNotesDaoImpl.class);
		walletControlAuditNotesBO.setWalletControlAuditNotesDao(dao);
		
	}

	@Test
	public void walletControlAuditNotes() {
		String result;
		ProcessResponse response = new ProcessResponse();
		response.setCode(ServiceConstants.VALID_RC);
		response.setMessage("The insertion done successfully");
		WalletControlAuditVO walletControlAuditVO = new WalletControlAuditVO();
		walletControlAuditVO.setVendorId(101);
		walletControlAuditVO.setReviewedBy("SLDEV");
		walletControlAuditVO.setWalletControlType("IRS Levy");
		walletControlAuditVO.setWalletOnHold(true);
		walletControlAuditVO.setSendEmailNotice(true);
		VendorNotesVO notes = new VendorNotesVO();
		notes.setModifiedBy("SLDEV");
		notes.setNote("Account is under IRS Levy Email sent to provider");
		notes.setVendorId(101);
		try {
			when(bo.addVendorNote(notes)).thenReturn(response);
			result = walletControlAuditNotesBO.walletControlAuditNotes(walletControlAuditVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertTrue("success", true);
	}
	
	@Test
	public void walletControlAuditNotesSLBucks() {
		String result;
		TransferReasonCodeVO vo=new TransferReasonCodeVO();
		vo.setDescription("Escheatment (Debit) -IRS Levy");
		vo.setTransferReasonNote("Funds Escheated as mandated by IRS Levy");
		ProcessResponse response = new ProcessResponse();
		response.setCode(ServiceConstants.VALID_RC);
		response.setMessage("The insertion done successfully");
		WalletControlAuditVO walletControlAuditVO = new WalletControlAuditVO();
		walletControlAuditVO.setVendorId(101);
		walletControlAuditVO.setReviewedBy("SLDEV");
		walletControlAuditVO.setWalletControlType("IRS Levy");
		walletControlAuditVO.setWalletOnHold(true);
		walletControlAuditVO.setSendEmailNotice(true);
		walletControlAuditVO.setEscheatmentTransferReasonCodeId(70);
		walletControlAuditVO.setAmount(100);
		VendorNotesVO notes = new VendorNotesVO();
		notes.setModifiedBy("SLDEV");
		notes.setNote("Account is under IRS Levy Email sent to provider");
		notes.setVendorId(101);
		try {
			when(dao.getTransferReasonCodeDetails(walletControlAuditVO.getEscheatmentTransferReasonCodeId())).thenReturn(vo);
			result = walletControlAuditNotesBO.addWalletControlAuditNotesSLBucks(walletControlAuditVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertTrue("success", true);
	}
}
