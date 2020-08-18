package com.servicelive.orderfulfillment.group;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.common.ServiceOrderNoteUtil;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Sep 24, 2010
 * Time: 5:50:53 PM
 */
public class GroupPricingUtil {
    
    private IMarketPlatformBuyerBO marketPlatformBuyerBO;
    private ServiceOrderNoteUtil serviceOrderNoteUtil;
    protected IServiceOrderDao serviceOrderDao;

    public boolean orderIsEligibleForGrouping(List<ServiceOrderProcess> soProcesses, ServiceOrder serviceOrder, boolean saveNotGroupNote){
        List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
        for (ServiceOrderProcess sop : soProcesses){
            serviceOrders.add(serviceOrderDao.getServiceOrder(sop.getSoId()));
        }
        int soCount = serviceOrders.size() + 1;
        //get trip charges based on the buyer and skill category
        BigDecimal tripCharge = marketPlatformBuyerBO.getTripChargeByBuyerAndSkillCategory(serviceOrder.getBuyerId(), serviceOrder.getPrimarySkillCatId());
        BigDecimal groupSpendLimit = PricingUtil.ZERO;
        //Calculate the amount that will be applied to all the orders for trip charges
        //Basically the idea is to allow one trip charge and rest of the trip charges gets distribute
        //equally among service order
        BigDecimal multipleOrderDiscount = tripCharge.divide(new BigDecimal(soCount), RoundingMode.HALF_EVEN).multiply(new BigDecimal(soCount - 1));

        StringBuffer soIds = new StringBuffer();
        for (ServiceOrder so : serviceOrders){
            soIds.append(so.getSoId()).append(", ");
            groupSpendLimit = groupSpendLimit.add(so.getPrice().getOrigSpendLimitLabor());
        }
        groupSpendLimit = groupSpendLimit.add(serviceOrder.getPrice().getOrigSpendLimitLabor());

        if(groupSpendLimit.compareTo(multipleOrderDiscount.multiply(new BigDecimal(soCount))) >= 0){
            return true;
        } else {
            if(saveNotGroupNote){
                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("SO_IDS", serviceOrder.getSoId());
                SONote note = serviceOrderNoteUtil.getNewNote("UnableToGroup", dataMap);
                for (ServiceOrder so : serviceOrders){
                    SONote sNote = note.copy();
                    sNote.setServiceOrder(so);
                    so.addNote(sNote);
                    serviceOrderDao.save(sNote);
                }
                String soId = soIds.toString();
                dataMap.put("SO_IDS", soId.substring(0, soId.length() - 2));
                note = serviceOrderNoteUtil.getNewNote("UnableToGroup", dataMap);
                note.setServiceOrder(serviceOrder);
                serviceOrderDao.save(note);
            }
            return false;
        }
    }

    public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
        this.marketPlatformBuyerBO = marketPlatformBuyerBO;
    }

    public void setServiceOrderNoteUtil(ServiceOrderNoteUtil serviceOrderNoteUtil) {
        this.serviceOrderNoteUtil = serviceOrderNoteUtil;
    }

    public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
        this.serviceOrderDao = serviceOrderDao;
    }
}
