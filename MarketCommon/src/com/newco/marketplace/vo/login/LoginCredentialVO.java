package com.newco.marketplace.vo.login;

import com.newco.marketplace.constants.Constants;
import com.sears.os.vo.ABaseVO;

public class LoginCredentialVO extends ABaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 496741862391760975L;
	public String username;
	public String password;
	public Integer roleId = null;
    public String roleName = null;
    public Integer companyId = -1; //Provider or Buyer Admin
    public Integer vendBuyerResId = -1; //Provider or Buyer Resource
    private String firstName;
    private String lastName;
    private Integer contactId;
    private String email;
    private String phoneNo;
    private Integer roleInCompany;
    private boolean passwordResetForSLAdmin;
    private boolean passwordResetForAllExternalUsers;
    private boolean permissionForProviderNameChange;
    private boolean permissionForAdminNameChange; // Provider Admin Name Chane
    
  //SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000
    private boolean permissionForForcefulCarActivation; // CAR Force Activation
    
    //SL-20461 starts
    private boolean permissionForBuyerAdminNameChange;
    //SL02461 changes ends
    
    
	public Integer getContactId() {
		return contactId;
	}
	public boolean isPermissionForBuyerAdminNameChange() {
		return permissionForBuyerAdminNameChange;
	}
	public void setPermissionForBuyerAdminNameChange(
			boolean permissionForBuyerAdminNameChange) {
		this.permissionForBuyerAdminNameChange = permissionForBuyerAdminNameChange;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public LoginCredentialVO (String username, String password) {
		this.username = username;
		this.password = password;
	}
	public LoginCredentialVO() {
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
		public Integer getRoleId() {
            return roleId;
        }
        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }
        
        public String getRoleName() {
            return roleName;
        }
        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String toString() {
                return ("LoginCredential: " + this.getUsername() + "    " +this.getPassword() );
        }//toString()
		public Integer getCompanyId() {
			return companyId;
		}
		public void setCompanyId(Integer companyId) {
			this.companyId = companyId;
		}
		public Integer getVendBuyerResId() {
			return vendBuyerResId;
		}
		public void setVendBuyerResId(Integer vendBuyerResId) {
			this.vendBuyerResId = vendBuyerResId;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public Integer getRoleInCompany() {
			return roleInCompany;
		}
		public void setRoleInCompany(Integer roleInCompany) {
			this.roleInCompany = roleInCompany;
		}
		public boolean isPasswordResetForSLAdmin() {
			return passwordResetForSLAdmin;
		}
		public void setPasswordResetForSLAdmin(boolean passwordResetForSLAdmin) {
			this.passwordResetForSLAdmin = passwordResetForSLAdmin;
		}
		public boolean isPasswordResetForAllExternalUsers() {
			return passwordResetForAllExternalUsers;
		}
		public void setPasswordResetForAllExternalUsers(
				boolean passwordResetForAllExternalUsers) {
			this.passwordResetForAllExternalUsers = passwordResetForAllExternalUsers;
		}
		
		public boolean isSuperAdmin() {
			if (Constants.COMPANY_ROLE.SUPER_ADMIN_ID == this.roleInCompany) {
				return true;
			}	
			return false;
		}
		public boolean isPermissionForProviderNameChange() {
			return permissionForProviderNameChange;
		}
		public void setPermissionForProviderNameChange(boolean permissionForProviderNameChange) {
			this.permissionForProviderNameChange = permissionForProviderNameChange;
		}
		public boolean isPermissionForAdminNameChange() {
			return permissionForAdminNameChange;
		}
		public void setPermissionForAdminNameChange(boolean permissionForAdminNameChange) {
			this.permissionForAdminNameChange = permissionForAdminNameChange;
		}
		public boolean isPermissionForForcefulCarActivation() {
			return permissionForForcefulCarActivation;
		}
		public void setPermissionForForcefulCarActivation(
				boolean permissionForForcefulCarActivation) {
			this.permissionForForcefulCarActivation = permissionForForcefulCarActivation;
		}
		
		
}