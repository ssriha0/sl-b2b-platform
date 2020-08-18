<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div>
<tiles:importAttribute name="headerTitleImage"/>
<tiles:importAttribute name="headerTitleAlt"/>
<tiles:importAttribute name="headerTitleId" ignore="true"/>
<img src="${headerTitleImage}" alt="${headerTitleAlt}" id="${headerTitleId}" />
</div>
