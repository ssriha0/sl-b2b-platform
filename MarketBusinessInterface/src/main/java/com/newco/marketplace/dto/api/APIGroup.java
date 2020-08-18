package com.newco.marketplace.dto.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIGroup {
	private String groupName;
	private Map<Integer, APIUrlPermission> permissionsMap;
	private List<APIUrlPermission> permissions;

	public APIGroup(String groupName) {
		this.groupName = groupName;
		permissionsMap = new HashMap<Integer, APIUrlPermission>();
	}


	public void addPermission(APIUrlPermission permission) {
		//if (this.permissions == null) {
		//	this.permissions = new ArrayList<APIUrlPermission> ();
		//}
		this.permissionsMap.put(permission.getId(), permission);
	}

	public APIUrlPermission getPermission(Integer id) {
		return permissionsMap.get(id);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<APIUrlPermission> getPermissions() {
		if (permissions == null) {
			permissions = new ArrayList<APIUrlPermission>();
			for (Integer key: permissionsMap.keySet()) {
				permissions.add(permissionsMap.get(key));
			}
		}
		return permissions;
	}

	public void setPermissions(List<APIUrlPermission> permissions) {
		this.permissions = permissions;
	}


	public Map<Integer, APIUrlPermission> getPermissionsMap() {
		return permissionsMap;
	}


	public void setPermissionsMap(Map<Integer, APIUrlPermission> permissionsMap) {
		this.permissionsMap = permissionsMap;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((groupName == null) ? 0 : groupName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		APIGroup other = (APIGroup) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		return true;
	}
}
