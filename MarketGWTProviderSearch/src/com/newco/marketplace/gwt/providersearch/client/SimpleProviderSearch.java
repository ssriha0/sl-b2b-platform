package com.newco.marketplace.gwt.providersearch.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleProviderSearch implements EntryPoint, EventListener {

	final FlowPanel SearchCriteriaPanel = new FlowPanel();
	//final HTML label_chooseServices = new HTML("<h3><span class='alt'>Step 1 of 3</span> Select the Category of Service you need.</h3>");
	//final HTML mainCategoryListBox = new HTML();
	final ListBox catagoryListBox = new ListBox();
	final ListBox subCategoryListbox = new ListBox();
	final HTML chooseJobsLabel = new HTML("<h3><span class='alt'>Step 2 of 3</span> Select Type of Service(s) you need.</h3>");
	final HTML chooseProviderLabel = new HTML("<h3><span class='alt'>Step 3 of 3</span> Select providers from the list below and continue to build your service order. </h3>");
	final CheckBox skilltypeBox1 = new CheckBox();
	final CheckBox skilltypeBox2 = new CheckBox();
	final CheckBox skilltypeBox3 = new CheckBox();
	final CheckBox skilltypeBox4 = new CheckBox();
	final CheckBox skilltypeBox5 = new CheckBox();
	//final CheckBox skilltypeBox6 = new CheckBox();
	final Label label_AddFilters = new Label("Filter by : ");
	final Label totalProvidersFound = new Label("Please wait while we find providers in your area..");
	final HTML noOfProvSelected = new HTML("<input type='hidden' id='countProv' value='0'/>");
	final HTML totalNoOfProv = new HTML("<input type='hidden' id='totalProv' value='0'/>");
	final Label label_2 = new Label("We've found ");
	final Label label_3 = new Label(" professionals to help you...");
	final Label lbl_zip = new Label("your location");
	//final Label label_5 = new Label("You've Selected");*/
	final Label selectedProviders = new Label();
	/*final Label label_6 = new Label("Service Providers");*/
	final Label lbl_showing = new Label("");
	final ScrollPanel ProvidersScrollPanel = new ScrollPanel();
	final Grid resultGrid = new Grid();
	final Label sortByLabel = new Label("Sort By");
	final ListBox sortByListBox = new ListBox();

	final ListBox DistanceComboBox = new ListBox();
	final ListBox ratingComboBox = new ListBox();
	final ListBox languagesComboBox = new ListBox();
	final ListBox percentMatchComboBox = new ListBox();
	final CheckBox selectedCheckBox = new CheckBox();
	final Label matchLabel = new Label("%Match");
	final Label providerLabel = new Label("Provider");
	final Label distanceLocationLabel = new Label("Distance & Location");
	final Label ordersCompletedLabel = new Label("Orders Completed");
	final Label servicceliveRatingLabel = new Label("ServiceLive Rating");
	final Hidden so_id = new Hidden();
	final Hidden zip = new Hidden();
	final Hidden state_cd = new Hidden();
	final Hidden selected_verticle = new Hidden("selected_verticle");
	final Hidden isLockedProvider = new Hidden();
	final Hidden viewAll = new Hidden("view_all");
	//final Label searchAgainLabel = new Label("Search Again");

	//final FlowPanel firstBoxDiv = new FlowPanel();
	final FlowPanel secondBoxDiv = new FlowPanel();
	final FlowPanel thirdBoxDiv = new FlowPanel();
	final FlowPanel headerBoxDiv = new FlowPanel();
	final HorizontalPanel fifthHorizontalDiv = new HorizontalPanel();
	final Hyperlink changeZipCodeLink = new Hyperlink("change this zipcode", "#");
	final Hyperlink viewALLLink = new Hyperlink("View All", "#");
	final SimplePanel  loadingImagePanel = new SimplePanel();
	final int MAX_RESULT_TO_DISPLAY = 30;


	static SimpleProviderSearchActions myActions = new SimpleProviderSearchActions();
	static ProviderResultsTableHeader providerResultHeader = new ProviderResultsTableHeader(myActions);
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get("gwtProviderSearchTile");
		RootPanel resultsHeadertPanel = RootPanel.get("gwtProviderResultHeader");
		RootPanel resultsPanel = RootPanel.get("gwtProviderResultTile");
		RootPanel headerDivPanel  = RootPanel.get("gwtProviderSearchTileHeader");
		Log.debug("I am here in onLoad Module...startup");
		myActions.setMyObjects(this);
		final HorizontalPanel leftPanel = new HorizontalPanel();
		//rootPanel.add(headerBoxDiv);
		headerBoxDiv.setStyleName("altTitle");
		totalProvidersFound.addStyleName("gwt-totalProviderslbl");
		//headerBoxDiv.add(label_2);
		headerBoxDiv.add(totalProvidersFound);
		totalProvidersFound.setStyleName("alt label");
		//headerBoxDiv.add(label_3);
		//label_3.setStylePrimaryName("gwt-Label");
		//headerBoxDivLoad.add(label_load);
		lbl_showing.setVisible(false);
		//label_chooseServices.addStyleName("gwt-Title");
		/*firstBoxDiv.setStyleName("gwtsearch step1 clearfix");
		firstBoxDiv.add(label_chooseServices);
		firstBoxDiv.add(mainCategoryListBox);
		rootPanel.add(firstBoxDiv);


		firstBoxDiv.add(mainCategoryListBox);*/

		secondBoxDiv.addStyleName("gwtsearch");
		secondBoxDiv.addStyleName("step2");
		secondBoxDiv.addStyleName("clearfix");
		secondBoxDiv.addStyleName("hide");
		thirdBoxDiv.addStyleName("gwtsearch");
		thirdBoxDiv.addStyleName("step3");
		thirdBoxDiv.addStyleName("clearfix");
		thirdBoxDiv.addStyleName("hide");

		rootPanel.add(secondBoxDiv);
		rootPanel.add(thirdBoxDiv);
		secondBoxDiv.add(chooseJobsLabel);
		secondBoxDiv.add(noOfProvSelected);
		secondBoxDiv.add(totalNoOfProv);

		secondBoxDiv.add(skilltypeBox1);
		skilltypeBox1.setText("Delivery");
		skilltypeBox1.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				myActions.getProviders();
				enableDisableStuff();

			}
		});

		secondBoxDiv.add(skilltypeBox2);
		skilltypeBox2.setText("Repair");
		skilltypeBox2.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				myActions.getProviders();
				enableDisableStuff();
			}
		});

		secondBoxDiv.add(skilltypeBox3);
		skilltypeBox3.setText("Training");
		skilltypeBox3.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				myActions.getProviders();
				enableDisableStuff();
			}
		});

		secondBoxDiv.add(skilltypeBox4);
		skilltypeBox4.setText("Installation");
		skilltypeBox4.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				myActions.getProviders();
				enableDisableStuff();
			}
		});

		secondBoxDiv.add(skilltypeBox5);
		skilltypeBox5.setText("Maintenance");
		skilltypeBox5.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				myActions.getProviders();
				enableDisableStuff();
			}
		});


		label_AddFilters.setStyleName("gwt-Title");

		thirdBoxDiv.add(chooseProviderLabel);
		thirdBoxDiv.add(label_AddFilters);
		thirdBoxDiv.add(catagoryListBox);
		catagoryListBox.addChangeListener(new ChangeListener() {
			public void onChange(final Widget sender) {
				myActions.getSubcatagories(catagoryListBox.getValue(catagoryListBox
						.getSelectedIndex()), -1);
				myActions.getProviders();
			}
		});
		thirdBoxDiv.add(subCategoryListbox);
		subCategoryListbox.addChangeListener(new ChangeListener() {
			public void onChange(final Widget sender) {
				myActions.getProviders();
			}
		});




		// label_1.setSize("141px", "18px");
		thirdBoxDiv.add(ratingComboBox);
		ratingComboBox.setStyleName("gwt-Listbox-c3");
		ratingComboBox.addChangeListener(new ChangeListener() {
			public void onChange(final Widget sender) {
				Log.debug("In the Change listner");
				myActions.repaintFilteredGrid(buildFilterSearch());
			}
		});
		ratingComboBox.addItem("Rating", "-1");
		ratingComboBox.addItem("5 Star Only", "5");
		ratingComboBox.addItem("4 to 5  Star", "4");
		ratingComboBox.addItem("3 to 5 Star", "3");

		//thirdBoxDiv.add(percentMatchComboBox);
		percentMatchComboBox.addItem("%Match to Services", "-1");
		percentMatchComboBox.addItem("100% Match", "100");
		percentMatchComboBox.addItem("80 to 100% Match", "80");
		percentMatchComboBox.addChangeListener(new ChangeListener() {
			public void onChange(final Widget sender) {
				Log.debug("In the Change listner of Distance");
				myActions.repaintFilteredGrid(buildFilterSearch());
			}
		});

		/*thirdBoxDiv.add(DistanceComboBox);
		DistanceComboBox.setStyleName("gwt-Listbox-c3");
		DistanceComboBox.addChangeListener(new ChangeListener() {
			public void onChange(final Widget sender) {
				Log.debug("In the Change listner of Distance");
				myActions.repaintFilteredGrid(buildFilterSearch());
			}
		});
		DistanceComboBox.addItem("Distance", "-1");
		DistanceComboBox.addItem("10 Miles", "10");
		DistanceComboBox.addItem("20 Miles", "20");
		DistanceComboBox.addItem("50 Miles", "30");
		DistanceComboBox.addItem("100 Miles", "40");
		DistanceComboBox.addItem("200 Miles", "50");*/

		thirdBoxDiv.add(languagesComboBox);
		languagesComboBox.setStyleName("gwt-Listbox-c3");
		languagesComboBox.addChangeListener(new ChangeListener() {
			public void onChange(final Widget sender) {
				myActions.repaintFilteredGrid(buildFilterSearch());
			}
		});
	//	rootPanel.add(SearchCriteriaPanel);
		resultsHeadertPanel.add(providerResultHeader);
		/*SearchCriteriaPanel.addStyleName("gwt-controlPanel");

		SearchCriteriaPanel.addStyleName("gwt-controlPanel");*/
		//firstBoxDiv.setWidth("240px");

		fifthHorizontalDiv.add(leftPanel);
		rootPanel.add(fifthHorizontalDiv);
		fifthHorizontalDiv.setCellHorizontalAlignment(leftPanel, HasHorizontalAlignment.ALIGN_LEFT);

		//searchAgainLabel.setVisible(true);
		//leftPanel.add(searchAgainLabel);

		lbl_showing.setStyleName("gwt-lbl-showing");
		changeZipCodeLink.setStyleName("gwt-chg-zip");
		leftPanel.add(lbl_showing);
		leftPanel.add(changeZipCodeLink);
		leftPanel.setWidth("400px");

		changeZipCodeLink.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				myActions.showZipModal();
			}
		});


		final HorizontalPanel rightPanel = new HorizontalPanel();
		fifthHorizontalDiv.add(rightPanel);
		rightPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		fifthHorizontalDiv.setCellHorizontalAlignment(rightPanel,
				HasHorizontalAlignment.ALIGN_RIGHT);

		viewALLLink.setStyleName("gwt-view-all");
		
		viewALLLink.setVisible(false);
		rightPanel.add(viewALLLink);
		viewALLLink.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				toggleViewAll();
				myActions.repaintFilteredGrid(buildFilterSearch());
				myActions.viewAllContinue(viewAll.getValue());
			}
		});
		sortByLabel.setVisible(false);
		sortByListBox.setVisible(false);

		sortByListBox.addItem("% Match", GWTProviderSearchConstants.PERCENTMTACH_COLUMN);
		//rightPanel.add(sortByListBox);
		sortByListBox.addItem("Provider", GWTProviderSearchConstants.PROVIDER_COLUMN);
		sortByListBox.addItem("ServiceLive Rating", GWTProviderSearchConstants.RATINGS_COLUMN);
		sortByListBox.addItem("Orders Completed", GWTProviderSearchConstants.ORDERS_COLUMN);
		sortByListBox.addItem("Distance", GWTProviderSearchConstants.DISTANCE_COLUMN);

		rootPanel.add(so_id);

		rootPanel.add(zip);
		viewAll.setValue("0");
		selected_verticle.setID("selected_verticle");

		rootPanel.add(selected_verticle);

		rootPanel.add(state_cd);
		resultGrid.setStyleName("disabledResults");
		resultsPanel.add(resultGrid);
		headerDivPanel.add(headerBoxDiv);
		
		

		// ProvidersScrollPanel.setSize("812px", "200px");



		//resultGrid.resize(1, 1);
		resultGrid.setSize("100%", "100%");
		initRedirectCriteriaJs(this);
		loadingImagePanel.setStyleName("gwtImageLoadingDiv");
		loadingImagePanel.setWidget(getLoadingImage(getLoadingImageUrl()));

		//ProvidersScrollPanel.setWidget(resultGrid);
		resultsPanel.add(loadingImagePanel);

		myActions.init();
		// myActions.getVerticals();
		isLockedProvider.setValue("FALSE");
		myActions.loadLanguages();
		myActions.getDTOfromSession();

		// myActions.getVerticals();
		Log.debug("I am here in onLoad Module...end");

	}
	private void toggleViewAll() {
		 if("0".equalsIgnoreCase( viewAll.getValue())){
			 viewAll.setValue("1");
		 }
		 else {
			 viewAll.setValue("0");
		 }
	}
	public void enableDisableStuff() {
		resultGrid.setStyleName("enabledResults");
		providerResultHeader.setStyleName("enabledResults");
		thirdBoxDiv.removeStyleName("hide");
		enableSidebarGWT();
	}

	public  void  doRefreshVerticle(String selectedverticle) {
		int skillNodeId = (selectedverticle != null && selectedverticle.length() > 0 ) ? Integer.parseInt(selectedverticle) : -1;
		String sVal = selected_verticle.getValue();
		selected_verticle.setValue(String.valueOf(skillNodeId));
		boolean isChange = false;
		if(sVal != null && sVal.length() > 0 ) {
			isChange = !sVal.equalsIgnoreCase(String.valueOf(skillNodeId));
		}
		if(skillNodeId == -1 && !isChange) return ;
		SimpleProviderSearchSkillNodeVO vo  = myActions.getVeticleVOforSelectedId(skillNodeId);
		myActions.getCatagories(vo, -1);
		myActions.getSkillTypes(String.valueOf(skillNodeId));
		myActions.getProviders();
		//searchAgainLabel.setText(selected_verticle.getValue());
		secondBoxDiv.removeStyleName("hide");
	}
	private native void initRedirectCriteriaJs(SimpleProviderSearch mywidget) /*-{
	   $wnd.redirectGWT = function () {
	    	mywidget.@com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearch::doRedirect()();
	   };
	   $wnd.refreshVerticle = function (s) {
	   	mywidget.@com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearch::doRefreshVerticle(Ljava/lang/String;)(s);
	   }
	}-*/;

	/*
	 * to redirect the pagew you need to set wnd.redirectUrl then call
	 * doRedirect();
	 *
	 */
	public native String getTheRedirectUrl()/*-{
				return $wnd.redirectUrl;
				}-*/;

	public void doRedirect() {
		String url = getTheRedirectUrl();
		myActions.saveDTOToSession();

	}

	public native String getLoadingImageUrl()/*-{
		return $wnd.loadingImgUrl;
	}-*/;

	public native void enableSidebarGWT()/*-{
	return $wnd.enableSidebar();
}-*/;


	protected SimpleProviderSearchCriteraVO buildFilterSearch() {
		SimpleProviderSearchCriteraVO vo = new SimpleProviderSearchCriteraVO();
		int selectedDistance = -1;
		int selectedSearchLanguageid = -1;
		float selectedStartRating = -1;
		String percentMatch = "-1";

		if (DistanceComboBox.getSelectedIndex() >= 0) {
			String distance = DistanceComboBox.getValue(DistanceComboBox.getSelectedIndex());
			selectedDistance = Integer.parseInt(distance);
		}
		if (percentMatchComboBox.getSelectedIndex() >= 0) {
			 percentMatch = percentMatchComboBox.getValue(percentMatchComboBox.getSelectedIndex());

		}

		if (ratingComboBox.getSelectedIndex() >= 0) {
			String rating = ratingComboBox.getValue(ratingComboBox.getSelectedIndex());
			selectedStartRating = Integer.parseInt(rating);
		}
		if (languagesComboBox.getSelectedIndex() >= 0) {
			String languageId = languagesComboBox.getValue(languagesComboBox.getSelectedIndex());
			selectedSearchLanguageid = Integer.parseInt(languageId);
		}
		vo.setDistance(selectedDistance);
		vo.setStarRating(selectedStartRating);
		vo.setLanguageId(selectedSearchLanguageid);
		vo.setPercentMatch(percentMatch);

		 if("0".equalsIgnoreCase( viewAll.getValue())){
			 vo.setViewAll(false);
		 }
		 else {
			 vo.setViewAll(true);
		 }
		return vo;

	}

	public Image getLoadingImage(String imgSrc) {
		Image extImage  = new Image();
		extImage.setUrl(imgSrc);
		extImage.setStyleName("gwt-LoadingImage");
		return extImage;
	}

	public void onBrowserEvent(Event event) {

	}

	public void showLoading() {

		loadingImagePanel.setVisible(true);
		resultGrid.setVisible(false);
	}
	public void hideLoading() {
		loadingImagePanel.setVisible(false);
		resultGrid.setVisible(true);

	}
}
