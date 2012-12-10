/**
 * 
 */
package com.credit.http.user;

import com.credit.org.bo.Company;
import com.credit.org.service.OrganizationService;
import com.credit.rbac.bo.User;
import com.credit.rbac.service.AuthService;
import com.credit.rbac.service.UserService;
import com.credit.util.CreditConstants;

/**
 * @author Hanlu
 * 
 */
public class OnlineUser implements IUser{

	private String remoteAddr;
	private User user;

	private Company org;
	private IOrgUser orgUser;
	
	private UserService userService;
	private AuthService authService;
	private OrganizationService orgService;

	protected boolean isLogon = false;

	public OnlineUser() {
	}

	/**
	 * @param req
	 * @throws Exception
	 * 
	 */
	public OnlineUser(String userIp, UserService userService, AuthService authService,OrganizationService orgService) throws Exception {
		this.remoteAddr = userIp;
		this.userService = userService;
		this.authService = authService;
		this.orgService=orgService;
		this.logon(CreditConstants.GUEST, CreditConstants.GUEST);
		this.isLogon = false;
	}

	/**
	 * 
	 * @return
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	/**
	 * @return the orgUser
	 */
	public IOrgUser getOrgUser() {
		return orgUser;
	}

	/**
	 * @return the org
	 */
	public Company getOrg() {
		return org;
	}

	@Override
	public boolean isLogon() {
		return this.isLogon;
	}

	@Override
	public boolean logon(String username, String password) throws Exception {
		if (this.isLogon) {
			return true;
		}
		User newUser = userService.getUser(username);
		if (newUser != null) {
			if (newUser.getUsername().equals(CreditConstants.GUEST)) {
				this.user = newUser;
				return false;
			}
		}
		this.isLogon = authService.checkPassword(password, newUser);
		if (this.isLogon) {
			this.user = newUser;
			this.org=orgService.getOrgByUser(user);
			if(this.org!=null){
				String orgID=this.org.getParent().trim();
				if(orgID.equals("1")){
					orgUser=new SupervisorUser(org);
				}else if(orgID.equals("2")){
					orgUser=new AgcUser(org);
				}else if(orgID.equals("3")){
					orgUser=new FinancialUser(org);
				}else if(orgID.equals("4")){
					orgUser=new FirmUser(org);
				}
			}
		}
		return isLogon;
	}
}
