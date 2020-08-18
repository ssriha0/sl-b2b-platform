package com.servicelive.orderfulfillment.notification;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTaskAddresses;
import com.servicelive.orderfulfillment.notification.address.AddrFetcherForAcceptedVendor;
import com.servicelive.orderfulfillment.notification.address.AddrFetcherForAssurant;
import com.servicelive.orderfulfillment.notification.address.AddrFetcherForAssurantFtp;
import com.servicelive.orderfulfillment.notification.address.AddrFetcherForBuyer;
import com.servicelive.orderfulfillment.notification.address.AddrFetcherForBuyerAdmin;
import com.servicelive.orderfulfillment.notification.address.AddrFetcherForNonAcceptedProviders;
import com.servicelive.orderfulfillment.notification.address.AddrFetcherForProviderAdmin;
import com.servicelive.orderfulfillment.notification.address.AddrFetcherForRoutedProviders;
import com.servicelive.orderfulfillment.notification.address.AddrFetcherForSOCreator;
import com.servicelive.orderfulfillment.notification.address.AddrFetcherForServiceLive;
import com.servicelive.orderfulfillment.notification.address.AddressCode;
import com.servicelive.orderfulfillment.notification.address.AddressCodeSet;
import com.servicelive.orderfulfillment.notification.address.AddressResolverTaskCode;

public class NotificationAddressResolver implements INotificationTaskAddressesResolver {

	private Logger logger = Logger.getLogger(getClass());
	
    private AddrFetcherForBuyer addrFetcherForBuyer;
    private AddrFetcherForSOCreator addrFetcherForSOCreator;
    private AddrFetcherForRoutedProviders addrFetcherForRoutedProviders;
    private AddrFetcherForAcceptedVendor addrFetcherForAcceptedVendor;
    private AddrFetcherForNonAcceptedProviders addrFetcherForNonAcceptedProviders;
    private AddrFetcherForBuyerAdmin addrFetcherForBuyerAdmin;
    private AddrFetcherForProviderAdmin addrFetcherForProviderAdmin;
    private AddrFetcherForAssurantFtp addrFetcherForAssurantFtp;
    private AddrFetcherForAssurant addrFetcherForAssurant;
    private AddrFetcherForServiceLive addrFetcherForServiceLive;
    private AddrFetcherForServiceLive addrFetcherForServiceLiveSupport;
    private String addressDelimiter;

    public ServiceOrderNotificationTaskAddresses resolveAddresses(AddressCodeSet recipientCodeSet, NotificationInfo notificationInfo) {
        Set<AddressResolverTaskCode> resolverTasks = getResolverCollectTasks(recipientCodeSet);
        runResolverCollectionTasks(resolverTasks, notificationInfo);
        return createAndPopulateTaskRecipients(recipientCodeSet, notificationInfo);
    }

    private ServiceOrderNotificationTaskAddresses createAndPopulateTaskRecipients(AddressCodeSet recipientCodeSet, NotificationInfo notificationInfo) {
        ServiceOrderNotificationTaskAddresses taskAddresses = new ServiceOrderNotificationTaskAddresses();
        logger.info("setting from address");
        taskAddresses.setFrom(resolveTargetAddress(recipientCodeSet.getAlertFromCode(), notificationInfo));
        logger.info("setting to address");
        taskAddresses.setTo(resolveTargetAddress(recipientCodeSet.getAlertToCode(), notificationInfo));
        logger.info("setting cc address");
        taskAddresses.setCc(resolveTargetAddress(recipientCodeSet.getAlertCcCode(), notificationInfo));
        logger.info("setting bcc address");
        taskAddresses.setBcc(resolveTargetAddress(recipientCodeSet.getAlertBccCode(), notificationInfo));

        return taskAddresses;
    }

    private String resolveTargetAddress(AddressCode addressCode, NotificationInfo notificationInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        if (addressCode == null) return StringUtils.EMPTY;
        for (AddressResolverTaskCode resolverCollectTask : addressCode.getRecipientResolverTaskCodes()) {
        	logger.info(" resolveTargetAddress : " + resolverCollectTask);
            switch (resolverCollectTask) {
                case FetchBuyerEmail:
                    if (notificationInfo.getBuyerDestAddr() != null)
                        stringBuilder.append(notificationInfo.getBuyerDestAddr()).append(addressDelimiter);
                    break;
                case FetchSOCreatorEmail:
                    if (notificationInfo.getSoCreatorDestAddr() != null)
                        stringBuilder.append(notificationInfo.getSoCreatorDestAddr()).append(addressDelimiter);
                    break;
                case FetchRoutedProviderContacts:
                case FetchRoutedNonAcceptedProvider:
                    for (String destAddr : notificationInfo.getRoutedProviderDestAddr()) {
                        stringBuilder.append(destAddr).append(addressDelimiter);
                    }
                    break;
                case FetchAcceptedVendorContact:
                    if (notificationInfo.getAcceptedVendorDestAddr() != null) {
                        stringBuilder.append(notificationInfo.getAcceptedVendorDestAddr()).append(addressDelimiter);
                        logger.info(" getAcceptedVendorDestAddr : " + notificationInfo.getAcceptedVendorDestAddr());
                    }
                    break;
                case FetchBuyerAdmin:
                    if (notificationInfo.getBuyerAdminDestAddr() != null) {
                        stringBuilder.append(notificationInfo.getBuyerAdminDestAddr()).append(addressDelimiter);
                    }
                    break;
                case FetchProviderAdmin:
                    if (notificationInfo.getProviderAdminDestAddr() != null) {
                        stringBuilder.append(notificationInfo.getProviderAdminDestAddr()).append(addressDelimiter);
                    }
                    break;
                case FetchAssurantFtpDestination:
                    if (notificationInfo.getAssurantFtpDest() != null) {
                        stringBuilder.append(notificationInfo.getAssurantFtpDest()).append(addressDelimiter);
                    }
                    break;
                case FetchAssurantEmail:
                    if (notificationInfo.getAssurantDestAddr() != null) {
                        stringBuilder.append(notificationInfo.getAssurantDestAddr()).append(addressDelimiter);
                    }
                    break;
                case FetchServiceLiveEmail:
                    if (notificationInfo.getServiceLiveDestAddr() != null) {
                        stringBuilder.append(notificationInfo.getServiceLiveDestAddr()).append(addressDelimiter);
                    }
                    break;
                case FetchServiceLiveSupportEmail:
                    if (notificationInfo.getServiceLiveDestAddr() != null) {
                        stringBuilder.append(notificationInfo.getServiceLiveSupportAddr()).append(addressDelimiter);
                    }
                    break;
                default:
                    break;
            }
        }
        if (stringBuilder.length() == 0) return StringUtils.EMPTY;

        logger.info(" target address : " + stringBuilder);
        return StringUtils.removeEnd(stringBuilder.toString(), addressDelimiter);
    }

    private void runResolverCollectionTasks(Set<AddressResolverTaskCode> resolverTasks, NotificationInfo notificationInfo) {
        for (AddressResolverTaskCode resolverCollectTask : resolverTasks) {
        	logger.info("resolverTask : " + resolverCollectTask);
            switch (resolverCollectTask) {
                case FetchBuyerEmail:
                    notificationInfo.setBuyerDestAddr(
                            addrFetcherForBuyer.fetchDestAddr(notificationInfo.getNotificationType(), notificationInfo.getServiceOrder())
                    );
                    break;
                case FetchSOCreatorEmail:
                    notificationInfo.setSoCreatorDestAddr(
                            addrFetcherForSOCreator.fetchDestAddr(notificationInfo.getNotificationType(), notificationInfo.getServiceOrder())
                    );
                case FetchRoutedProviderContacts:
                    notificationInfo.setRoutedProviderDestAddr(
                            addrFetcherForRoutedProviders.fetchDestAddrList(notificationInfo.getNotificationType(), notificationInfo.getServiceOrder())
                    );
                    break;
                case FetchRoutedNonAcceptedProvider:
                    notificationInfo.setRoutedProviderDestAddr(
                    		addrFetcherForNonAcceptedProviders.fetchDestAddrList(notificationInfo.getNotificationType(), notificationInfo.getServiceOrder())
                    );
                    break;
                case FetchAcceptedVendorContact:
                    notificationInfo.setAcceptedVendorDestAddr(
                            addrFetcherForAcceptedVendor.fetchDestAddr(notificationInfo.getNotificationType(), notificationInfo.getServiceOrder())
                    );
                    break;
                case FetchBuyerAdmin:
                    notificationInfo.setBuyerDestAddr(
                            addrFetcherForBuyerAdmin.fetchDestAddr(notificationInfo.getNotificationType(), notificationInfo.getServiceOrder())
                    );
                    break;
                case FetchProviderAdmin:
                    notificationInfo.setProviderAdminDestAddr(
                            addrFetcherForProviderAdmin.fetchDestAddr(notificationInfo.getNotificationType(), notificationInfo.getServiceOrder())
                    );
                    break;
                case FetchAssurantFtpDestination:
                    notificationInfo.setAssurantFtpDest(
                            addrFetcherForAssurantFtp.fetchDestAddr()
                    );
                    break;
                case FetchAssurantEmail:
                    notificationInfo.setAssurantDestAddr(
                            addrFetcherForAssurant.fetchDestAddr()
                    );
                    break;
                case FetchServiceLiveEmail:
                    notificationInfo.setServiceLiveDestAddr(
                            addrFetcherForServiceLive.fetchDestAddr()
                    );
                    break;
                case FetchServiceLiveSupportEmail:
                    notificationInfo.setServiceLiveSupportAddr(
                            addrFetcherForServiceLiveSupport.fetchDestAddr()
                    );
                    break;
                default:
                    break;
            }
        }
    }

    private Set<AddressResolverTaskCode> getResolverCollectTasks(AddressCodeSet addressCodeSet) {
        Set<AddressResolverTaskCode> resolverTasks = new HashSet<AddressResolverTaskCode>();

        addResolverTasksToSet(addressCodeSet.getAlertFromCode(), resolverTasks);
        addResolverTasksToSet(addressCodeSet.getAlertToCode(), resolverTasks);
        addResolverTasksToSet(addressCodeSet.getAlertBccCode(), resolverTasks);
        addResolverTasksToSet(addressCodeSet.getAlertCcCode(), resolverTasks);

        return resolverTasks;
    }

    private void addResolverTasksToSet(AddressCode addressCode, Set<AddressResolverTaskCode> resolverTasks) {
        if (addressCode != null) {
            for (AddressResolverTaskCode taskCode : addressCode.getRecipientResolverTaskCodes()) {
                if (taskCode != AddressResolverTaskCode.NONE) {
                    resolverTasks.add(taskCode);
                }
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setAddrFetcherForBuyer(AddrFetcherForBuyer addrFetcherForBuyer) {
        this.addrFetcherForBuyer = addrFetcherForBuyer;
    }

    public void setAddrFetcherForSOCreator(AddrFetcherForSOCreator addrFetcherForSOCreator) {
        this.addrFetcherForSOCreator = addrFetcherForSOCreator;
    }

    public void setAddrFetcherForRoutedProviders(AddrFetcherForRoutedProviders addrFetcherForRoutedProviders) {
        this.addrFetcherForRoutedProviders = addrFetcherForRoutedProviders;
    }

    public void setAddrFetcherForAcceptedVendor(AddrFetcherForAcceptedVendor addrFetcherForAcceptedVendor) {
        this.addrFetcherForAcceptedVendor = addrFetcherForAcceptedVendor;
    }

    public void setAddrFetcherForBuyerAdmin(AddrFetcherForBuyerAdmin addrFetcherForBuyerAdmin) {
        this.addrFetcherForBuyerAdmin = addrFetcherForBuyerAdmin;
    }

    public void setAddrFetcherForProviderAdmin(AddrFetcherForProviderAdmin addrFetcherForProviderAdmin) {
        this.addrFetcherForProviderAdmin = addrFetcherForProviderAdmin;
    }

    public void setAddrFetcherForAssurantFtp(AddrFetcherForAssurantFtp addrFetcherForAssurantFtp) {
        this.addrFetcherForAssurantFtp = addrFetcherForAssurantFtp;
    }

    public void setAddrFetcherForAssurant(AddrFetcherForAssurant addrFetcherForAssurant) {
        this.addrFetcherForAssurant = addrFetcherForAssurant;
    }

    public void setAddrFetcherForServiceLive(AddrFetcherForServiceLive addrFetcherForServiceLive) {
        this.addrFetcherForServiceLive = addrFetcherForServiceLive;
    }

    public void setAddrFetcherForServiceLiveSupport(AddrFetcherForServiceLive addrFetcherForServiceLiveSupport) {
        this.addrFetcherForServiceLiveSupport = addrFetcherForServiceLiveSupport;
    }

    public void setAddressDelimiter(String addressDelimiter) {
        this.addressDelimiter = addressDelimiter;
    }

	public void setAddrFetcherForNonAcceptedProviders(
			AddrFetcherForNonAcceptedProviders addrFetcherForNonAcceptedProviders) {
		this.addrFetcherForNonAcceptedProviders = addrFetcherForNonAcceptedProviders;
	}

}
