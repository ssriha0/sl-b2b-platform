package com.newco.marketplace.gwt.providersearch.client;



import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ProviderInfoPanel extends Composite {
	private SimpleProviderSearchProviderResultVO provResultVO ;
	private CheckBox selectCheckBox;
	private static final double MAXRATING = 5.0;

	StarImageBundle starImageBundle = (StarImageBundle) GWT.create(StarImageBundle.class);


	public ProviderInfoPanel(SimpleProviderSearchProviderResultVO vo,CheckBox cb) {
		this.provResultVO = vo;
		this.selectCheckBox = cb;

	//	final FocusPanel focusPanel = new FocusPanel();



		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		//focusPanel.setWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");

		//final CheckBox selectCheckBox = new CheckBox();


		//horizontalPanel.setCellHeight(selectCheckBox, COMMON_MAX_HEIGHT);
		//horizontalPanel.setCellWidth(selectCheckBox, "20Px");


		/*final Label matchLbl = new Label(this.provResultVO.getPercentageMatch().toString()+ "%");
		horizontalPanel.add(matchLbl);
		matchLbl.setStyleName("gwt-result-column2");
		horizontalPanel.setCellVerticalAlignment(matchLbl, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(matchLbl, HasHorizontalAlignment.ALIGN_CENTER);
*/
		//horizontalPanel.setCellHeight(matchLbl, COMMON_MAX_HEIGHT);
		//horizontalPanel.setCellWidth(matchLbl, "100Px");

		final FlowPanel verticalPanelColumn3_1 = new FlowPanel();
		horizontalPanel.add(verticalPanelColumn3_1);
		horizontalPanel.setCellVerticalAlignment(verticalPanelColumn3_1, HasVerticalAlignment.ALIGN_TOP);
		horizontalPanel.setCellHorizontalAlignment(verticalPanelColumn3_1, HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanelColumn3_1.addStyleName("gwt-provider");
		//horizontalPanel.setCellHeight(verticalPanel, COMMON_MAX_HEIGHT);
		//horizontalPanel.setCellWidth(verticalPanel, "250Px");
		String nameLink = this.provResultVO.getProviderFirstName()+" "+ (this.provResultVO.getProviderLastName()).substring(0,1) + ".";
		nameLink = nameLink + " (ID# "+this.provResultVO.getResourceId() + ")";
		nameLink = nameLink + " Company Id # " + this.provResultVO.getVendorID();
		final Hyperlink providerNameLbl = new Hyperlink(nameLink,"#");
		providerNameLbl.addStyleName("gwt-nameLink-nextline");
		final FlowPanel imageHyperlinkDiv = new FlowPanel();



		//imageHyperlinkDiv.add(img);
		//providerNameLbl.setHTML()
		providerNameLbl.setTitle(""+this.provResultVO.getResourceId());
		providerNameLbl.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				Hyperlink lnk = (Hyperlink)sender;
				openProviderProfilePage(lnk.getTitle());
			}
		});

		imageHyperlinkDiv.add(providerNameLbl);
		if(this.provResultVO.isBackgroundCheckClear()){
			Image img = starImageBundle.greycheckImage().createImage();
			img.setStyleName("gwt-result-column3_top");
			img.setTitle("This provider has passed the ServiceLive-approved background check");
			imageHyperlinkDiv.add(img);
		}
		verticalPanelColumn3_1.add(imageHyperlinkDiv);

		/*final Label companyInfoLbl = new Label("Company Id # " + this.provResultVO.getVendorID());
		verticalPanelColumn3_1.add(companyInfoLbl);
		companyInfoLbl.setStyleName("textLeft");*/
		String distanceInfo = "";
		String completedOrdersInfo = "";
		Integer tsoCompleted = this.provResultVO.getTotalSOCompleted();
		if(tsoCompleted == null) {
			tsoCompleted = new Integer(0);
		}
		distanceInfo = this.provResultVO.getDistance() + " miles";
		
		final Label distanceTitleLbl = new Label("Distance : ");
		final Label distanceInfoLbl = new Label(distanceInfo);
		distanceTitleLbl.addStyleName("gwt-distance-title");
		distanceTitleLbl.removeStyleName("gwt-Label");
		distanceInfoLbl.addStyleName("gwt-distance-info");
		distanceInfoLbl.removeStyleName("gwt-Label");
		verticalPanelColumn3_1.add(distanceTitleLbl);
		verticalPanelColumn3_1.add(distanceInfoLbl);
		
		if(tsoCompleted.intValue() > 0 ){
			completedOrdersInfo = "" + tsoCompleted;
			
			final Label completedOrdersTitleLbl = new Label(" | Completed Orders : ");
			final Label completedOrdersLbl = new Label(completedOrdersInfo);
			completedOrdersTitleLbl.addStyleName("gwt-completed-orders-title");
			completedOrdersTitleLbl.removeStyleName("gwt-Label");
			completedOrdersLbl.addStyleName("gwt-completed-orders");
			completedOrdersLbl.removeStyleName("gwt-Label");
			verticalPanelColumn3_1.add(completedOrdersTitleLbl);
			verticalPanelColumn3_1.add(completedOrdersLbl);
		}
		
		
		

		String matchinfo = "";
		if(this.provResultVO.getPercentageMatch().intValue() < 100) {
			matchinfo = this.provResultVO.getPercentageMatch().toString() +  "% Match Search -- Provider offers similar services";
		}
		final Label matchLbl = new Label(matchinfo);
		verticalPanelColumn3_1.add(matchLbl);


		final FlowPanel verticalPanelForRating = new FlowPanel();




		final FlowPanel imgPanel = new FlowPanel();
		imgPanel.add(getImagePanel(this.provResultVO.getProviderStarRatingImage(),this.provResultVO.getTotalSOCompleted().intValue()));
		verticalPanelForRating.add(imgPanel);


		String ratingInfo = "";

		if(this.provResultVO.getProviderStarRating() > 0 ){
			ratingInfo = this.provResultVO.getProviderStarRating()+"/" + MAXRATING;
		}
		final Label providerRatingLbl = new Label(ratingInfo );
		verticalPanelForRating.add(providerRatingLbl);
		horizontalPanel.add(verticalPanelForRating);
		horizontalPanel.setCellVerticalAlignment(verticalPanelForRating, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(verticalPanelForRating, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanelForRating.addStyleName("gwt-slrating");

		/*
		 * horizontalPanel.add(selectCheckBox);
		horizontalPanel.setCellVerticalAlignment(selectCheckBox, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(selectCheckBox, HasHorizontalAlignment.ALIGN_CENTER);
		 */




		/*final Label distanceLocation = new Label(this.provResultVO.getCity() + ", " + this.provResultVO.getState() );
		verticalPanelForDistance.add(distanceLocation);
		distanceLocation.addStyleName("gwt-result-column6_bottom");*/
	}
	public SimpleProviderSearchProviderResultVO getProvResultVO() {
		return provResultVO;
	}
	public void setProvResultVO(SimpleProviderSearchProviderResultVO provResultVO) {
		this.provResultVO = provResultVO;
	}



	public FlowPanel getImagePanel(int rating,int totalOrdersCompleted) {
		//StarImageBundle starImageBundle = (StarImageBundle) GWT.create(StarImageBundle.class);
		FlowPanel tbPanel = new FlowPanel();

		 if(rating >= 0  && rating <= 20) {
			 switch(rating) {
			 //use the brutal force here rather than smartness...
			 case 0 : tbPanel.add(starImageBundle.stars_notRatedImage().createImage());return tbPanel;
			 /* Commented per CQ #58037
				 if(totalOrdersCompleted == 0 ){tbPanel.add(starImageBundle.stars_notRatedImage().createImage());return tbPanel; }
				 else {tbPanel.add(starImageBundle.stars_0Image().createImage());return tbPanel; }
				 */

			 case 1 : tbPanel.add(starImageBundle.stars_1Image().createImage());return tbPanel;
			 case 2 : tbPanel.add(starImageBundle.stars_2Image().createImage());return tbPanel;
			 case 3 : tbPanel.add(starImageBundle.stars_3Image().createImage());return tbPanel;
			 case 4 : tbPanel.add(starImageBundle.stars_4Image().createImage());return tbPanel;
			 case 5 : tbPanel.add(starImageBundle.stars_5Image().createImage());return tbPanel;
			 case 6 : tbPanel.add(starImageBundle.stars_6Image().createImage());return tbPanel;
			 case 7 : tbPanel.add(starImageBundle.stars_7Image().createImage());return tbPanel;
			 case 8 : tbPanel.add(starImageBundle.stars_8Image().createImage());return tbPanel;
			 case 9 : tbPanel.add(starImageBundle.stars_9Image().createImage());return tbPanel;
			 case 10 : tbPanel.add(starImageBundle.stars_10Image().createImage());return tbPanel;
			 case 11 : tbPanel.add(starImageBundle.stars_11Image().createImage());return tbPanel;
			 case 12 : tbPanel.add(starImageBundle.stars_12Image().createImage());return tbPanel;
			 case 13 : tbPanel.add(starImageBundle.stars_13Image().createImage());return tbPanel;
			 case 14 : tbPanel.add(starImageBundle.stars_14Image().createImage());return tbPanel;
			 case 15 : tbPanel.add(starImageBundle.stars_15Image().createImage());return tbPanel;
			 case 16 : tbPanel.add(starImageBundle.stars_16Image().createImage());return tbPanel;
			 case 17 : tbPanel.add(starImageBundle.stars_17Image().createImage());return tbPanel;
			 case 18 : tbPanel.add(starImageBundle.stars_18Image().createImage());return tbPanel;
			 case 19 : tbPanel.add(starImageBundle.stars_19Image().createImage());return tbPanel;
			 case 20 : tbPanel.add(starImageBundle.stars_20Image().createImage());return tbPanel;


			 }
		 }


		  return tbPanel;
	}

	//redirect the browser to the given url
	public static native void redirectOutside(String url)/*-{
		    $wnd.location = url ;
	  }-*/;

	public native String getContextPath()/*-{

	return $wnd.contextUrl;
	}-*/;

	public native void openProviderProfilePage(String resourceId)/*-{
		return $wnd.openProviderprofile(resourceId);
	}-*/;

}
