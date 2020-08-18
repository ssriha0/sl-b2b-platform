package com.newco.marketplace.gwt.providersearch.client;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProviderNoSearchResultsPanel extends Composite {

	static SimpleProviderSearchActions myActions = new SimpleProviderSearchActions();
	
	public ProviderNoSearchResultsPanel() {

		final VerticalPanel rootPanel = new VerticalPanel();
		final FlowPanel changeZipDiv = new FlowPanel();
		final Hyperlink changeZipCodeLink = new Hyperlink("Search Again", "#");
		initWidget(rootPanel);
		
		
		String str = "<div style='text-align: center; font-weight: bold; font-size: 14px; width: 570px;' class='gwt-Label'>Sorry</div>" +
					"<div style='text-align: center;' class='gwt-Label'>We were unable to find any Providers with your search criteria.</div>";
					
		final HTMLPanel lable_1 = new HTMLPanel(str);
		rootPanel.add(lable_1);
		
		changeZipCodeLink.setStyleName("gwt-try-another-zip");
		changeZipDiv.add(changeZipCodeLink);
		rootPanel.add(changeZipDiv);
		
		changeZipCodeLink.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				myActions.showZipModal();
			}
		});
		
		/*lable_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		final Label label_2 = new Label("We were unable to find any Providers with your search criteria.");
		rootPanel.add(label_2);
		label_2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		final Label label_3 = new Label("You can try your search again, or leave us your email and we will send you a note when providers become available in your area.");
		rootPanel.add(label_3);
		label_3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);*/
	}

}
