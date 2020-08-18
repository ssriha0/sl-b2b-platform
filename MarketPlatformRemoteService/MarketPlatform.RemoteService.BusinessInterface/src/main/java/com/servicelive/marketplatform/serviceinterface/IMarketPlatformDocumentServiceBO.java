package com.servicelive.marketplatform.serviceinterface;

public interface IMarketPlatformDocumentServiceBO {
	Integer retrieveBuyerDocumentIdByTitleAndOwnerId(String title, Integer ownerId);
}
