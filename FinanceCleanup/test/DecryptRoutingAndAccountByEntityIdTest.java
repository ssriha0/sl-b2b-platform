import junit.framework.Assert;

import org.junit.Test;

import com.newco.marketplace.dto.vo.CryptographyVO;

public class DecryptRoutingAndAccountByEntityIdTest {
	
	private DecryptRoutingAndAccountByEntityId decrypt;
	
	@Test
	public void testEncryptKey(){
		decrypt = new DecryptRoutingAndAccountByEntityId();
		CryptographyVO vo = new CryptographyVO();
		vo.setInput("1123645454");
		
		CryptographyVO voResult = new CryptographyVO();
		voResult = decrypt.encryptKey(vo);
		
		Assert.assertEquals("apd/NymSlOuUqFMy8fi3Ww==", voResult.getResponse());
	}

}
