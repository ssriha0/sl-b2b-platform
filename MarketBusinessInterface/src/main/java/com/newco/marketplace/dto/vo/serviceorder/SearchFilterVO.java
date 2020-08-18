package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;

public class SearchFilterVO extends SerializableBaseVO {

/**

*

*/

private static final long serialVersionUID = -2997759648844837984L;

private int searchFilterId;

private int entityId;

private String filterName;

private String templateHtmlContent;

private int roleId;

private String templateValue;

public int getSearchFilterId() {

return searchFilterId;

}

public void setSearchFilterId(int searchFilterId) {

this.searchFilterId = searchFilterId;

}

public int getEntityId() {

return entityId;

}

public void setEntityId(int entityId) {

this.entityId = entityId;

}

public String getFilterName() {

return filterName;

}

public void setFilterName(String filterName) {

this.filterName = filterName;

}

public String getTemplateHtmlContent() {

return templateHtmlContent;

}

public void setTemplateHtmlContent(String templateHtmlContent) {

this.templateHtmlContent = templateHtmlContent;

}

public int getRoleId() {

return roleId;

}

public void setRoleId(int roleId) {

this.roleId = roleId;

}

public String getTemplateValue() {

return templateValue;

}

public void setTemplateValue(String templateValue) {

this.templateValue = templateValue;

}

}