package com.newco.marketplace.dto.vo;

import com.sears.os.vo.ABaseVO;

public class RoleVO extends ABaseVO {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1085068292229691040L;
	private Long roleId;
    private String roleName;
    private String userName;
    private Integer roleIdInt;
    
	public RoleVO () {
    }
    
    public Integer getRoleIdInt() {
		return roleIdInt;
	}

	public void setRoleIdInt(Integer roleIdInt) {
		this.roleIdInt = roleIdInt;
	}

    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("roleid= ");
        sb.append(getRoleId());
        sb.append("\n");
        sb.append("roleName= ");
        sb.append(getRoleName());
        return sb.toString();
    }//toString()
}