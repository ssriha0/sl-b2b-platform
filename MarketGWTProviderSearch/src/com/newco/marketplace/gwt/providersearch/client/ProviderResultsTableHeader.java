package com.newco.marketplace.gwt.providersearch.client;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ProviderResultsTableHeader extends Composite {
	final HorizontalPanel horizontalPanel = new HorizontalPanel();
	//final CheckBox checkBox = new CheckBox();
	//final Hyperlink matchHyperlink = new Hyperlink("% Match", "#");
	final Hyperlink providerLink = new Hyperlink("Provider", "#");
	final Hyperlink serviceliveratingHyperlink = new Hyperlink("ServiceLive\nRating", "#");
	//final Hyperlink orderscompletedHyperlink = new Hyperlink("Orders\nCompleted", "#");
	//final Hyperlink distanceLocationHyperlink = new Hyperlink("Distance\n& Location", "#");
	  SimpleProviderSearchActions myActions = null;
	 final Hidden hidden_selectedColumn = new Hidden();
	 final Hidden hidden_selectedOrder = new Hidden();

	public ProviderResultsTableHeader(SimpleProviderSearchActions myAction) {


		setMyActions(myAction);
		//horizontalPanel.addStyleName("tablehead");
		initWidget(horizontalPanel);

		addStyleName("disabledResults");




	/*	matchHyperlink.addStyleName("column2");
		horizontalPanel.add(matchHyperlink);
		matchHyperlink.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				toggleSortOrder(GWTProviderSearchConstants.PERCENTMTACH_COLUMN);
				getMyActions().sortColumn(hidden_selectedColumn.getValue(),hidden_selectedOrder.getValue());
			}
		});*/




		horizontalPanel.add(providerLink);
		horizontalPanel.setCellVerticalAlignment(providerLink, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(providerLink, HasHorizontalAlignment.ALIGN_LEFT);
		providerLink.addStyleName("gwt-provider");
		providerLink.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				toggleSortOrder(GWTProviderSearchConstants.PROVIDER_COLUMN);
				getMyActions().sortColumn(hidden_selectedColumn.getValue(),hidden_selectedOrder.getValue());
			}
		});



		horizontalPanel.add(serviceliveratingHyperlink);
		horizontalPanel.setCellVerticalAlignment(providerLink, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(providerLink, HasHorizontalAlignment.ALIGN_CENTER);
		serviceliveratingHyperlink.addStyleName("gwt-slrating");
		serviceliveratingHyperlink.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				toggleSortOrder(GWTProviderSearchConstants.RATINGS_COLUMN);
				getMyActions().sortColumn(hidden_selectedColumn.getValue(),hidden_selectedOrder.getValue());
			}
		});
		serviceliveratingHyperlink.setTitle("Sort ServiceLive Rating");


		/*orderscompletedHyperlink.addStyleName("column5");
		horizontalPanel.add(orderscompletedHyperlink);
		orderscompletedHyperlink.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				toggleSortOrder(GWTProviderSearchConstants.ORDERS_COLUMN);
				getMyActions().sortColumn(hidden_selectedColumn.getValue(),hidden_selectedOrder.getValue());
			}
		});*/


		/*distanceLocationHyperlink.addStyleName("column6");
		horizontalPanel.add(distanceLocationHyperlink);
		distanceLocationHyperlink.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				toggleSortOrder(GWTProviderSearchConstants.DISTANCE_COLUMN);
				getMyActions().sortColumn(hidden_selectedColumn.getValue(),hidden_selectedOrder.getValue());
			}
		});
		horizontalPanel.add(checkBox);
		checkBox.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				CheckBox cb = (CheckBox)sender;
				boolean ischecked = cb.isChecked();
				getMyActions().selectAlltheProviders(ischecked);

			}
		});

		 */

	/*	horizontalPanel.add(hidden_selectedColumn);


		horizontalPanel.add(hidden_selectedOrder);*/
		hidden_selectedColumn.setValue("X");
		hidden_selectedOrder.setValue("X");
	}


	private void toggleSortOrder(String columnSelected) {
		String currentValue = hidden_selectedColumn.getValue();
		if(currentValue != null && currentValue.trim().length() > 0 ){
			String sortOrder = hidden_selectedOrder.getValue();
			//user clicks on the same column sorted currently
			if(currentValue.equals(columnSelected)){
				if(sortOrder != null && sortOrder.trim().length() > 0){
					if("ASC".equals(sortOrder)) {
						//then set to DESC
						hidden_selectedOrder.setValue("DESC");
					}
					else {
						hidden_selectedOrder.setValue("ASC");
					}
				}
				else{
					hidden_selectedOrder.setValue("ASC");
				}
			}
			//user clicks on the different column
			else {
				hidden_selectedColumn.setValue(columnSelected);
				hidden_selectedOrder.setValue("ASC");
			}
		}
		//nothing has been set yet
		else {
			hidden_selectedColumn.setValue(columnSelected);
			hidden_selectedOrder.setValue("ASC");
		}
	}





	public final SimpleProviderSearchActions getMyActions() {
		return myActions;
	}


	public void setMyActions(SimpleProviderSearchActions myAction) {
		myActions = myAction;
	}
}
