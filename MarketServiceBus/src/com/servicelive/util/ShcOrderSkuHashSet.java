package com.servicelive.util;

import java.util.Iterator;
import java.util.LinkedHashSet;

import org.apache.log4j.Logger;

import com.newco.marketplace.webservices.dao.ShcOrderSku;

public class ShcOrderSkuHashSet<T extends ShcOrderSku> 
	extends LinkedHashSet<ShcOrderSku> 
{
	/** generated serialVersionUID */
	private static final long serialVersionUID = -250704807090642759L;
	private Logger logger = Logger.getLogger(this.getClass());
	public boolean add( ShcOrderSku o )
	{
		Iterator<ShcOrderSku> i = super.iterator();
		while( i.hasNext() )
		{
			ShcOrderSku sku = i.next();
			if( sku.getSku().equals(o.getSku()) )
			{
				logger.warn( "Duplicate ShcOrderSku found: " + sku.getSku() );
				return false;
			}
		}
		return super.add( o );
	}
}
