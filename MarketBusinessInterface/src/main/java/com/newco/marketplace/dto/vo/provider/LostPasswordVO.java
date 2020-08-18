package com.newco.marketplace.dto.vo.provider;

import com.sears.os.vo.ABaseVO;

public class LostPasswordVO extends ABaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3651561926177569522L;
	private String userName;
    private String secretAnswer;
    private String password;

    public String getPassword() {
    
        return password;
    }

    public void setPassword(String password) {
    
        this.password = password;
    }

    public String getSecretAnswer() {

        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {

        this.secretAnswer = secretAnswer;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }
    
    @Override
	public String toString() {
        return ("<LostPasswordVO>" + getUserName() + getSecretAnswer() + getPassword() + "</LostPasswordVO");
    }//toString()

    public LostPasswordVO(String userName, String password) {

        super();
        this.userName = userName;
        this.password = password;
    }

    public LostPasswordVO() {

        super();
    }

}// LostPasswordVO
