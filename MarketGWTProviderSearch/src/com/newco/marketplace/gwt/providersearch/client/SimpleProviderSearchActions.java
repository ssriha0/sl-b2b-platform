package com.newco.marketplace.gwt.providersearch.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class SimpleProviderSearchActions {
	SimpleProviderSearch myObjects = null;

	// Some state Stuff
	final List verticals = new ArrayList();
	final List catagories = new ArrayList();
	final List subcatagories = new ArrayList();
	final List skillTypes = new ArrayList();
	final List returnedProviders = new ArrayList();
	final Map currentProvidersMap = new HashMap();
	final List providerSortRetentionList = new ArrayList();
	final List skillTypeCkeckboxes = new ArrayList();
	final List providerSelectionCheckboxes = new ArrayList();

	int totalSelectedProvider;
	final GwtFindProvidersDTO gwtFindProvidersDto = new GwtFindProvidersDTO();
	boolean running = false;
	boolean waiting = false;
	boolean repaintrunning = false;
	boolean isPopupRunning = false;

	public SimpleProviderSearch getMyObjects() {
		return myObjects;
	}

	public void setMyObjects(SimpleProviderSearch myObjects) {
		this.myObjects = myObjects;
	}

	public void init() {
		skillTypeCkeckboxes.add(myObjects.skilltypeBox1);
		skillTypeCkeckboxes.add(myObjects.skilltypeBox2);
		skillTypeCkeckboxes.add(myObjects.skilltypeBox3);
		skillTypeCkeckboxes.add(myObjects.skilltypeBox4);
		skillTypeCkeckboxes.add(myObjects.skilltypeBox5);
		//skillTypeCkeckboxes.add(myObjects.skilltypeBox6);
		for (int i = 0; i < skillTypeCkeckboxes.size(); i++) {
			((CheckBox) skillTypeCkeckboxes.get(i)).setVisible(false);
		}
	}

	protected void getSkillTypes(String sSelectedVertical) {
		// get SkillTypes for a given skill set

		// I suppose that if this fails clear all things.
		int selectedVertical = Integer.parseInt(sSelectedVertical);
		if(selectedVertical == -1){
			clearSkillTyes();
		}
		SimpleProviderSearchSkillNodeVO selectedSkillNode = null;
		for (int i = 0; i < verticals.size(); ++i) {
			if (((SimpleProviderSearchSkillNodeVO) verticals.get(i)).skillNodeId == selectedVertical) {
				selectedSkillNode = (SimpleProviderSearchSkillNodeVO) verticals.get(i);
				break;
			}
		}
		ProviderSearchCriteriaService.Util.getInstance().getSkillTypes(selectedSkillNode,
				new AsyncCallback() {
					public void onSuccess(Object result) {
						GwtRemoteServiceResponse response  = (GwtRemoteServiceResponse)result;
						 if(response.isValidSession()){
							List skilList = response.getValueList();

							if (skilList != null) {
								skillTypes.clear();
								for (int i = 0; i < skilList.size(); ++i) {
									// type checking?
									skillTypes.add(skilList.get(i));
								}

								repaintSkills();
							}
						 }else {
							 //TODO.. popup crap
							 showZipModal();

						 }

					}

					public void onFailure(Throwable caught) {
						skillTypes.clear();

					}
				});

	}

	protected void getSkillTypes(String sSelectedVertical, final List checkedJobs) {
		// get SkillTypes for a given skill set

		// I suppose that if this fails clear all things.
		int selectedVertical = -1;
		try {
			selectedVertical = Integer.parseInt(sSelectedVertical);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(selectedVertical == -1){
			clearSkillTyes();
		}
		SimpleProviderSearchSkillNodeVO selectedSkillNode = null;
		for (int i = 0; i < verticals.size(); ++i) {
			if (((SimpleProviderSearchSkillNodeVO) verticals.get(i)).skillNodeId == selectedVertical) {
				selectedSkillNode = (SimpleProviderSearchSkillNodeVO) verticals.get(i);
				break;
			}
		}
		ProviderSearchCriteriaService.Util.getInstance().getSkillTypes(selectedSkillNode,
				new AsyncCallback() {
					public void onSuccess(Object result) {
						GwtRemoteServiceResponse response  = (GwtRemoteServiceResponse)result;
						 if(response.isValidSession()){
							List skilList = response.getValueList();
							if (skilList != null) {
								skillTypes.clear();
								for (int i = 0; i < skilList.size(); ++i) {
									// type checking?
									skillTypes.add(skilList.get(i));
								}

								repaintSkills(checkedJobs);
							}
						 }else {
							 showZipModal();
						 }

					}

					public void onFailure(Throwable caught) {
						skillTypes.clear();

					}
				});

	}



	protected void getVerticals(final int selectedVerticleNode) {
		// get the vericals
		//myObjects.mainCategoryListBox.setHTML("");
		Log.debug("Calling get Verticals...to select the  ID = " + selectedVerticleNode);
		ProviderSearchCriteriaService.Util.getInstance().getVerticals(new AsyncCallback() {
			public void onSuccess(Object result) {
				GwtRemoteServiceResponse response  = (GwtRemoteServiceResponse)result;
				 if(response.isValidSession()){

					List verticalVOs =  response.getValueList();
					if (verticalVOs != null) {
						verticals.clear();
						for (int i = 0; i < verticalVOs.size(); ++i) {
							verticals.add(verticalVOs.get(i));
						}
						repaintVerticls(selectedVerticleNode);

					}
				 }
				 else {
					 //TODO put popup code here
					 showZipModal();
				 }
			}

			public void onFailure(Throwable caught) {
				Log.debug("Failed  verticals");
				Log.debug("exception occurred...", caught);
				repaintrunning = false;
			}
		});

	}



	protected void getProviders() {
		Log.debug("I am in getProvider ");
		// get the vericals
		if (running == true || repaintrunning == true) {
			Log.info(" Waiting ");
			waiting = true;
		} else {
			Log.info(" Running ");
			running = true;
			if(isPopupRunning == false ) {

				//getMyObjects().loadingPopup.createAndShow(getMyObjects().getLoadingImageUrl()); //,getMyObjects().getLeftForPopup(),getMyObjects().getTopForPopup());
				getMyObjects().showLoading();
				isPopupRunning = true;
			}
			getMyObjects().resultGrid.clear();
			SimpleProviderSearchCriteraVO inSearchVO = new SimpleProviderSearchCriteraVO();
			inSearchVO.setZip(gwtFindProvidersDto.getZip());
			inSearchVO.setTheSkillNode(getTheSkillNode());
			inSearchVO.setLockedResourceId(gwtFindProvidersDto.getResourceID());
			inSearchVO.setProviderLocked(gwtFindProvidersDto.isLockedProviderList());
			List skillTypes = new ArrayList();
			for (int i = 0; i < skillTypeCkeckboxes.size(); i++) {
				CheckBox aCheckBox = (CheckBox) skillTypeCkeckboxes.get(i);
				if (aCheckBox.isEnabled() && aCheckBox.isChecked()) {
					skillTypes.add(new Integer(((SimpleProviderSearchSkillTypeVO) this.skillTypes
							.get(i)).skillTypeId));
					getMyObjects().enableDisableStuff();
				}
			}

			inSearchVO.setSkillTypes(skillTypes);

			ProviderSearchCriteriaService.Util.getInstance().getProviderResults(inSearchVO,
					new AsyncCallback() {
						public void onSuccess(Object result) {
							returnedProviders.clear();
							GwtRemoteServiceResponse response  = (GwtRemoteServiceResponse)result;
							 if(response.isValidSession()){
								List providerVos = response.getValueList();
								if (providerVos != null) {
									for (int i = 0; i < providerVos.size(); ++i) {
										returnedProviders.add(providerVos.get(i));
									}
									setProviderResults(returnedProviders);
									running = false;
									if (waiting) {
										Log.info("rerunning");
										waiting = false;
										getProviders();
									}
								}
							 }else {
								 showZipModal();
							 }

						}

						public void onFailure(Throwable caught) {
							running = false;
							if (waiting) {
								Log.info("rerunning");
								waiting = false;
								getProviders();
							}
						}
					});

		}
	}

	public SimpleProviderSearchSkillNodeVO getTheSkillNode() {
		SimpleProviderSearchSkillNodeVO theSkillNode = new SimpleProviderSearchSkillNodeVO();
		theSkillNode.setSkillNodeId(-999);
		//The subcategory drop down is now out of scope so removing this code
		if (myObjects.subCategoryListbox.getSelectedIndex() > 0) {
			for (int i = 0; i < subcatagories.size(); i++) {
				theSkillNode = (SimpleProviderSearchSkillNodeVO) subcatagories.get(i);
				if (theSkillNode.skillNodeId == Integer.parseInt(myObjects.subCategoryListbox
						.getValue(myObjects.subCategoryListbox.getSelectedIndex()))) {
					return theSkillNode;
				}

			}
		}
		if (myObjects.catagoryListBox.getSelectedIndex() > 0) {
			for (int i = 0; i < catagories.size(); i++) {
				theSkillNode = (SimpleProviderSearchSkillNodeVO) catagories.get(i);
				if (theSkillNode.skillNodeId == Integer.parseInt(myObjects.catagoryListBox
						.getValue(myObjects.catagoryListBox.getSelectedIndex()))) {
					return theSkillNode;
				}

			}
		}
		//TODO update this to get the current selection of the Vertical
		/*if (myObjects.mainCategoryListBox.getSelectedIndex() > 0) {
			for (int i = 0; i < verticals.size(); i++) {
				theSkillNode = (SimpleProviderSearchSkillNodeVO) verticals.get(i);
				if (theSkillNode.skillNodeId == Integer.parseInt(myObjects.mainCategoryListBox
						.getValue(myObjects.mainCategoryListBox.getSelectedIndex()))) {
					return theSkillNode;
				}

			}
		}*/
		String sVal = myObjects.selected_verticle.getValue();
		if(sVal != null && sVal.length() > 0 ){
			for (int i = 0; i < verticals.size(); i++) {
				theSkillNode = (SimpleProviderSearchSkillNodeVO) verticals.get(i);
				if (theSkillNode.skillNodeId == Integer.parseInt(sVal)) {
					return theSkillNode;
				}

			}
		}
		return theSkillNode;

	}


	protected SimpleProviderSearchSkillNodeVO getVerticalForSelectedString(String sSelectedVertical) {
		int selectedVertical = Integer.parseInt(sSelectedVertical);
		SimpleProviderSearchSkillNodeVO selectedSkillNode = new SimpleProviderSearchSkillNodeVO();
		selectedSkillNode.setSkillNodeId(-1);
		for (int i = 0; i < verticals.size(); ++i) {
			if (((SimpleProviderSearchSkillNodeVO) verticals.get(i)).skillNodeId == selectedVertical) {
				selectedSkillNode = (SimpleProviderSearchSkillNodeVO) verticals.get(i);
				break;
			}
		}
		return selectedSkillNode;

	}

	protected void getCatagories(SimpleProviderSearchSkillNodeVO selectedSkillNode,
			final int catgorytoSelect) {
		// get the vericals
		clearSubcatagories();
		clearCatagories();
		ProviderSearchCriteriaService.Util.getInstance().getCatagories(selectedSkillNode,
				new AsyncCallback() {
					public void onSuccess(Object result) {
						GwtRemoteServiceResponse response  = (GwtRemoteServiceResponse)result;
						 if(response.isValidSession()){
							List catagoryVOs =  response.getValueList();
							if (catagoryVOs != null) {
								catagories.clear();
								for (int i = 0; i < catagoryVOs.size(); ++i) {
									catagories.add(catagoryVOs.get(i));
								}
								repaintCatagories(catgorytoSelect);
							}
						 }else {
							 showZipModal();
						 }
					}
					public void onFailure(Throwable caught) {	}
				});
	}

	protected void getSubcatagories(String sSelectedCatagory, final int subcategorytoSelect) {
		// get the vericals
		clearSubcatagories();
		// I suppose that if this fails clear all things.
		int selectedCatagory = Integer.parseInt(sSelectedCatagory);
		SimpleProviderSearchSkillNodeVO selectedSkillNode = null;
		for (int i = 0; i < catagories.size(); ++i) {
			if (((SimpleProviderSearchSkillNodeVO) catagories.get(i)).skillNodeId == selectedCatagory) {
				selectedSkillNode = (SimpleProviderSearchSkillNodeVO) catagories.get(i);
				break;
			}
		}

		ProviderSearchCriteriaService.Util.getInstance().getSubCatagories(selectedSkillNode,
				new AsyncCallback() {
					public void onSuccess(Object result) {
						GwtRemoteServiceResponse response  = (GwtRemoteServiceResponse)result;
						 if(response.isValidSession()){
							List subCatagoryVOs = response.getValueList();
							if (subCatagoryVOs != null) {
								subcatagories.clear();
								for (int i = 0; i < subCatagoryVOs.size(); ++i) {
									subcatagories.add(subCatagoryVOs.get(i));
								}
								repaintSubCatagories(subcategorytoSelect);
							}
						 }else {
							 showZipModal();
						 }
					}

					public void onFailure(Throwable caught) {
						repaintrunning = false;
					}
				});

	}

	private String getVerticalHTMLHeader(String currentGuy) {
		//return "<li class='current'><a id='CatID' href='#a'>" + currentGuy + "</a>";
		return "";

	}

	private String getVerticalHTMLFooter() {
		//return 	"</li>";
		return " ";
	}

	public SimpleProviderSearchSkillNodeVO getVeticleVOforSelectedId(int id) {
		for (int i = 0; i < verticals.size(); ++i) {
			SimpleProviderSearchSkillNodeVO aSkillNodeVo = (SimpleProviderSearchSkillNodeVO) verticals.get(i);
			if (id == aSkillNodeVo.getSkillNodeId()) {
				aSkillNodeVo.setSelected(true);
				return aSkillNodeVo;
			}
		}
		SimpleProviderSearchSkillNodeVO vo = new SimpleProviderSearchSkillNodeVO();
		vo.setSkillNodeId(-1);
		vo.setDecriptionContent("<span>Nothing Selected</span>");
		vo.setSkillNodeDesc("Category");
		return vo;
	}

	protected void repaintVerticls(int selectedNode) {

		clearCatagories();
		myObjects.catagoryListBox.setEnabled(false);
		/*clearSubcatagories();
		myObjects.subCategoryListbox.setEnabled(false);*/
		//myObjects.mainCategoryListBox.addItem("Type of Service", "-1");
		boolean found = false;

			for (int i = 0; i < verticals.size(); ++i) {
				SimpleProviderSearchSkillNodeVO aSkillNodeVo = (SimpleProviderSearchSkillNodeVO) verticals.get(i);
				if (selectedNode == aSkillNodeVo.getSkillNodeId()) {
					aSkillNodeVo.setSelected(true);
					found = true;
					doVerticleClick(""+aSkillNodeVo.getSkillNodeId(),aSkillNodeVo.getSkillNodeDesc());
				}
			}


		if (!found) {
			repaintrunning = false;
			getProviders();
		}
		// get categories here..
		Map map = gwtFindProvidersDto.getSelectedCategorys();
		SimpleProviderSearchSkillNodeVO verticlenode = (SimpleProviderSearchSkillNodeVO) map
				.get(GwtFindProvidersDTO.MAIN_VERTICLES);
		// Now we need to populate the providers only when we got some category

		if (verticlenode != null && verticlenode.getSkillNodeId() > 0) {

			Log.debug("getting categories for  = " + verticlenode.getSkillNodeId());
			List checkedJobs = new ArrayList();
			if (gwtFindProvidersDto.getCheckedJobs() != null) {
				checkedJobs = gwtFindProvidersDto.getCheckedJobs();
			}
			if(checkedJobs.size() > 0) {
				myObjects.secondBoxDiv.removeStyleName("hide");
				myObjects.enableDisableStuff();
			}
			getSkillTypes(verticlenode.getSkillNodeId() + "", checkedJobs);
			SimpleProviderSearchSkillNodeVO categorynode = null;
			if (map.get(GwtFindProvidersDTO.CATEGORY) != null) {
				categorynode = (SimpleProviderSearchSkillNodeVO) map
						.get(GwtFindProvidersDTO.CATEGORY);
			}

			if (categorynode != null && categorynode.getSkillNodeId() > 0) {
				getCatagories(verticlenode, categorynode.getSkillNodeId());
			} else {
				getCatagories(verticlenode, -1);
			}
		}

	}


	protected void repaintCatagories(int selectedNode) {
		clearCatagories();
		myObjects.catagoryListBox.setEnabled(true);
		clearSubcatagories();
		myObjects.subCategoryListbox.setEnabled(false);
		Log.debug("repainting the Category  for selctected Node = " + selectedNode);
		myObjects.catagoryListBox.addItem("Category", "-1");
		for (int i = 0; i < catagories.size(); ++i) {
			SimpleProviderSearchSkillNodeVO aSkillNodeVo = (SimpleProviderSearchSkillNodeVO) catagories
					.get(i);
			// this is for gordon
			myObjects.catagoryListBox.addItem(aSkillNodeVo.getSkillNodeDesc(), aSkillNodeVo
					.getSkillNodeId()
					+ "");

			// Select the guy..
			if (selectedNode == aSkillNodeVo.getSkillNodeId()) {
				Log.debug("Selecyiong the Category  for selctected Node = " + selectedNode);
				myObjects.catagoryListBox.setItemSelected(i + 1, true);
			}

		}
		Map map = gwtFindProvidersDto.getSelectedCategorys();
		SimpleProviderSearchSkillNodeVO categorynode = (SimpleProviderSearchSkillNodeVO) map
				.get(GwtFindProvidersDTO.CATEGORY);
		SimpleProviderSearchSkillNodeVO subcategorynode = (SimpleProviderSearchSkillNodeVO) map
				.get(GwtFindProvidersDTO.SUB_SUB_CATEGORY);
		if (subcategorynode != null && subcategorynode.getSkillNodeId() > 0) {
			getSubcatagories(categorynode.getSkillNodeId() + "", subcategorynode.getSkillNodeId());
		} else {
			getSubcatagories(categorynode.getSkillNodeId() + "", -1);
			repaintrunning = false;
			getProviders();
		}
	}


	protected void repaintSubCatagories(int subcategorytoSelect) {
		myObjects.subCategoryListbox.clear();
		myObjects.subCategoryListbox.setEnabled(true);
		myObjects.subCategoryListbox.addItem("Sub Category", "-1");
		for (int i = 0; i < subcatagories.size(); ++i) {
			SimpleProviderSearchSkillNodeVO aSkillNodeVo = (SimpleProviderSearchSkillNodeVO) subcatagories
					.get(i);
			// this is for gordon
			myObjects.subCategoryListbox.addItem(aSkillNodeVo.getSkillNodeDesc(), aSkillNodeVo
					.getSkillNodeId()
					+ "");
			// Select the guy..
			if (subcategorytoSelect == aSkillNodeVo.getSkillNodeId()) {
				Log.debug("Selecyiong the Category  for selctected Node = " + subcategorytoSelect);
				myObjects.subCategoryListbox.setItemSelected(i + 1, true);
				// myObjects.catagoryListBox.setItemSelected(i+1, true);
			}

		}
		repaintrunning = false;
		getProviders();
	}

	// Repaint the checkboxes here
	protected void repaintSkills() {
		/*
		 * This will preserve checkedness across one call unless the skill was
		 * checked and not present in new set
		 */

		List checkedStrings = new ArrayList();

		// get the labels of all of the ckecked boxes
		for (int k = 0; k < skillTypeCkeckboxes.size(); ++k) {
			if (((CheckBox) skillTypeCkeckboxes.get(k)).isChecked()) {
				checkedStrings.add(((CheckBox) skillTypeCkeckboxes.get(k)).getText());
			}
		}
		// set up boxes again
		for (int i = 0; i < skillTypes.size(); ++i) {
			// java 4 junk
			SimpleProviderSearchSkillTypeVO aSkillTypeVO = (SimpleProviderSearchSkillTypeVO) skillTypes
					.get(i);
			// set the text
			((CheckBox) skillTypeCkeckboxes.get(i)).setText(aSkillTypeVO.getSkillTypeDescr());
			((CheckBox) skillTypeCkeckboxes.get(i)).setVisible(true);
			// look in previos array for checkedness
			if (checkedStrings.contains(aSkillTypeVO.getSkillTypeDescr())) {
				((CheckBox) skillTypeCkeckboxes.get(i)).setChecked(true);
			} else {
				((CheckBox) skillTypeCkeckboxes.get(i)).setChecked(false);

			}
		}
		// disable the rest
		for (int j = skillTypes.size(); j < 5; ++j) {
			((CheckBox) skillTypeCkeckboxes.get(j)).setVisible(false);
		}

	}

	protected void repaintSkills(List checkedJobs) {

		List checkedStrings = new ArrayList();

		// get the labels of all of the ckecked boxes
		for (int k = 0; k < checkedJobs.size(); ++k) {
			SimpleProviderSearchSkillTypeVO aSkillTypeVO = (SimpleProviderSearchSkillTypeVO) checkedJobs
					.get(k);
			checkedStrings.add(aSkillTypeVO.getSkillTypeDescr().trim());
		}

		for (int i = 0; i < skillTypes.size(); ++i) {
			// java 4 junk
			SimpleProviderSearchSkillTypeVO aSkillTypeVO = (SimpleProviderSearchSkillTypeVO) skillTypes
					.get(i);
			// set the text
			((CheckBox) skillTypeCkeckboxes.get(i)).setText(aSkillTypeVO.getSkillTypeDescr());
			((CheckBox) skillTypeCkeckboxes.get(i)).setVisible(true);
			// look in previos array for checkedness
			if (checkedStrings.contains(aSkillTypeVO.getSkillTypeDescr())) {
				((CheckBox) skillTypeCkeckboxes.get(i)).setChecked(true);
			} else {
				((CheckBox) skillTypeCkeckboxes.get(i)).setChecked(false);

			}
		}
		// disable the rest
		for (int j = skillTypes.size(); j < 5; ++j) {
			((CheckBox) skillTypeCkeckboxes.get(j)).setVisible(false);
		}

	}

	protected CheckBox getSkillBoxforDesc(String drsc) {
		for (int k = 0; k < skillTypeCkeckboxes.size(); ++k) {
			if (((CheckBox) skillTypeCkeckboxes.get(k)).getText().equals(drsc)) {
				return (CheckBox) skillTypeCkeckboxes.get(k);

			}
		}
		return null;
	}

	protected void clearCatagories() {
		myObjects.catagoryListBox.clear();
	}

	protected void clearSubcatagories() {
		myObjects.subCategoryListbox.clear();
	}

	protected void clearSkillTyes() {
		skillTypes.clear();
		for (int i = 0; i < skillTypeCkeckboxes.size(); i++) {
			CheckBox cb = (CheckBox) skillTypeCkeckboxes.get(i);
			cb.setVisible(false);
			cb.setChecked(false);
		}

	}

	protected void clearResultWidgetToDefault() {
		currentProvidersMap.clear();
		// clear the checkboxarray
		providerSelectionCheckboxes.clear();
		myObjects.resultGrid.clear();
		getProviders();
	}

	protected void setProviderResults(List providerResults) {
		// working on it;
		myObjects.resultGrid.clear();

		// sets the checkness in the current provider array
		applySelection();

		// stick them on
		for (int i = 0; i < providerResults.size(); ++i) {
			SimpleProviderSearchProviderResultVO aProvResult = (SimpleProviderSearchProviderResultVO) providerResults
					.get(i);
			if(aProvResult.isLockedProvider()){aProvResult.setSelected(true);}

			if (currentProvidersMap.containsKey(new Integer(aProvResult.getResourceId()))) {
				if (((SimpleProviderSearchProviderResultVO) currentProvidersMap.get(new Integer(
						aProvResult.getResourceId()))).getSelected()) {
					aProvResult.setSelected(true);
				}
			}
		} // end reestablishing the checked in the new array

		// because of final we need to reestablish the existing current array.
		currentProvidersMap.clear();
		// clear the checkboxarray
		providerSelectionCheckboxes.clear();
		// clear the Sort Retention Array
		providerSortRetentionList.clear();
		for (int j = 0; j < providerResults.size(); ++j) {
			SimpleProviderSearchProviderResultVO aProvResult = (SimpleProviderSearchProviderResultVO) providerResults
					.get(j);
			currentProvidersMap.put(new Integer(aProvResult.getResourceId()), aProvResult);

			// save the order
			providerSortRetentionList.add(new Integer(aProvResult.getResourceId()));

			// loop through the selected guys from the DTO
			if (gwtFindProvidersDto.getSelectedProviders() != null) {
				if (gwtFindProvidersDto.getSelectedProviders().size() > 0) {
					for (int i = 0; i < gwtFindProvidersDto.getSelectedProviders().size(); i++) {
						SimpleProviderSearchProviderResultVO anotherProvResult = (SimpleProviderSearchProviderResultVO) gwtFindProvidersDto
								.getSelectedProviders().get(i);
						if (anotherProvResult.getResourceId() == aProvResult.getResourceId()) {
							aProvResult.setSelected(true);
						}
					}
				}
			}

			// Whilst I am here lets make some new checkboxes.
			CheckBox aNewCheckBox = new CheckBox();
			aNewCheckBox.setName(aProvResult.getResourceId() + "");
			aNewCheckBox.setChecked(aProvResult.getSelected());
			aNewCheckBox.setEnabled(true);
			aNewCheckBox.setVisible(true);
			aNewCheckBox.addClickListener(new ClickListener() {
				public void onClick(final Widget sender) {
					if (((CheckBox) sender).isChecked()) {
						selectProvider();
					} else {
						unSelectProvider();
					}
				}
			});
			// sort order should equal the checkbox order
			providerSelectionCheckboxes.add(aNewCheckBox);
		}

		// repaint the grid
		// repaintGrid();
		repaintFilteredGrid(getMyObjects().buildFilterSearch());

	} // end setup provider results

	protected void selectProvider() {
		// aProvResult.setSelected(true);
		totalSelectedProvider++;
		myObjects.selectedProviders.setText("" + totalSelectedProvider);
		myObjects.noOfProvSelected.setHTML("<input type='hidden' id='countProv' value='"+totalSelectedProvider+"'>");
		
	}

	protected void unSelectProvider() {
		// aProvResult.setSelected(true);
		totalSelectedProvider--;
		myObjects.selectedProviders.setText("" + totalSelectedProvider);
		myObjects.noOfProvSelected.setHTML("<input type='hidden' id='countProv' value='"+totalSelectedProvider+"'>");
	}

	/*
	 * This sets up the currentProvidersMap to reflect the "Checkedness" of the
	 * checkboxes.
	 */
	protected void applySelection() {
		// Assumption, these are the same index but to be safe let us name the
		// checkboxes for the providerids
		totalSelectedProvider = 0;
		for (int i = 0; i < providerSelectionCheckboxes.size(); i++) {
			CheckBox acheckbox = (CheckBox) providerSelectionCheckboxes.get(i);
			if (acheckbox.isChecked())
				totalSelectedProvider++;

			SimpleProviderSearchProviderResultVO aProvResult = (SimpleProviderSearchProviderResultVO) currentProvidersMap
					.get(new Integer(Integer.parseInt(acheckbox.getName())));
			if(aProvResult.isLockedProvider()){aProvResult.setSelected(true);}
			aProvResult.setSelected(acheckbox.isChecked());
		}

	}


	protected void repaintFilteredGrid(SimpleProviderSearchCriteraVO searchCriteria) {
		myObjects.selectedProviders.setText("" + totalSelectedProvider);
		myObjects.noOfProvSelected.setHTML("<input type='hidden' id='countProv' value='"+totalSelectedProvider+"'>");
		List filteredListwithRetention = getFilteredProvider(searchCriteria);
		myObjects.resultGrid.clear();
		myObjects.resultGrid.resizeColumns(1);
		int listSize = getMyObjects().MAX_RESULT_TO_DISPLAY;

		if(searchCriteria.isViewAll()){
			listSize = filteredListwithRetention.size();
			getMyObjects().viewALLLink.setText("View First "+ getMyObjects().MAX_RESULT_TO_DISPLAY);
		}
		else {
			getMyObjects().viewALLLink.setText("View All");
		}
		if(filteredListwithRetention.size() <= getMyObjects().MAX_RESULT_TO_DISPLAY){
			getMyObjects().viewALLLink.setVisible(false);
		}
		/*else {
			getMyObjects().viewALLLink.setVisible(true);
		}*/
		if(filteredListwithRetention.size() <= getMyObjects().MAX_RESULT_TO_DISPLAY && !searchCriteria.isViewAll()){
			listSize = filteredListwithRetention.size();
			myObjects.resultGrid.resizeRows(listSize);
			myObjects.lbl_showing.setText("Showing "+ listSize+ " out of " + listSize + " for " + myObjects.lbl_zip.getText());
			myObjects.lbl_showing.setVisible(true);
		}
		else {
			myObjects.resultGrid.resizeRows(listSize);
			myObjects.lbl_showing.setText("Showing "+  listSize + " out of " + filteredListwithRetention.size()+ " for " + myObjects.lbl_zip.getText());
			myObjects.lbl_showing.setVisible(true);
		}

		myObjects.totalProvidersFound.setText("We have found " + filteredListwithRetention.size()+" providers in your area");
		myObjects.selectedProviders.setText("" + totalSelectedProvider);
		myObjects.noOfProvSelected.setHTML("<input type='hidden' id='countProv' value='"+totalSelectedProvider+"'>");
		myObjects.totalNoOfProv.setHTML("<input type='hidden' id='totalProv' value='"+filteredListwithRetention.size()+"'>");
		Log.debug("Try to repaint the grid  with size = " + filteredListwithRetention.size());

		if (filteredListwithRetention.size() <= 0) {
			myObjects.resultGrid.resizeRows(1);
			ProviderNoSearchResultsPanel nosearchPnl = new ProviderNoSearchResultsPanel();
			myObjects.resultGrid.setWidget(0, 0, nosearchPnl);

		}



		for (int i = 0; i < listSize ; i++) {

			SimpleProviderSearchProviderResultVO vo = (SimpleProviderSearchProviderResultVO) filteredListwithRetention
					.get(i);
			CheckBox chbox = getCheckBox(vo.getResourceId());
			ProviderInfoPanel panel = new ProviderInfoPanel(vo, chbox);
			myObjects.resultGrid.setWidget(i, 0, panel);
		}

		if(isPopupRunning == true ) {
			//getMyObjects().loadingPopup.hide();
			 getMyObjects().hideLoading();
			isPopupRunning = false;
		}

	}



	protected void repaintSortedList(List sortedList) {
		myObjects.resultGrid.clear();
		myObjects.resultGrid.resizeColumns(1);
		myObjects.resultGrid.resizeRows(sortedList.size());
		for (int i = 0; i < sortedList.size(); i++) {

			SimpleProviderSearchProviderResultVO vo = (SimpleProviderSearchProviderResultVO) sortedList
					.get(i);
			CheckBox chbox = getCheckBox(vo.getResourceId());
			ProviderInfoPanel panel = new ProviderInfoPanel(vo, chbox);
			myObjects.resultGrid.setWidget(i, 0, panel);

		}
	}

	protected CheckBox getCheckBox(int resourceId) {

		for (int i = 0; i < providerSelectionCheckboxes.size(); i++) {
			CheckBox acheckbox = (CheckBox) providerSelectionCheckboxes.get(i);
			if (Integer.parseInt(acheckbox.getName()) == resourceId)
				return acheckbox;

		}
		CheckBox aNewCheckBox = new CheckBox();
		aNewCheckBox.setName(resourceId + "");
		aNewCheckBox.setEnabled(true);
		aNewCheckBox.setVisible(true);
		return aNewCheckBox;
	}

	/*
	 * //one more idiotic stuff.. I want to make it work.. dont care abt
	 * elegance at this time protected List
	 * getFilteredProviderForSort(SimpleProviderSearchCriteraVO searchCriteria){ }
	 */

	protected List getFilteredProvider(SimpleProviderSearchCriteraVO searchCriteria) {
		Log.debug("In the filtered provider with criteria of rating  "
				+ searchCriteria.getStarRating());
		// this is list of SimpleProviderSearchProviderResultVO with all
		// retention above the filtered one
		List filteredListwithRetention = new ArrayList();
		List filteredListwithoutRetention = new ArrayList();

		applySelection();
		// Get all the retention list prior to do anything
		for (int i = 0; i < providerSortRetentionList.size(); i++) {
			SimpleProviderSearchProviderResultVO vo = (SimpleProviderSearchProviderResultVO) currentProvidersMap
					.get(providerSortRetentionList.get(i));
			if (vo.getSelected()) {
				filteredListwithRetention.add(vo);
			}
		}

		Iterator itr = providerSortRetentionList.iterator();
		while (itr.hasNext()) {

			SimpleProviderSearchProviderResultVO vo = (SimpleProviderSearchProviderResultVO) currentProvidersMap
					.get((Integer) itr.next());
			if (filteredListwithRetention.contains(vo))
				continue;
			else
				filteredListwithoutRetention.add(vo);
		}

		// now while sorthing check if this vo is not available in the retention
		// list .. if it is skip to the next one.

		// Look in the searchVO what is being asked...
		// 1. Filter must happen frist....
		// 2. Sort should happen last

		// 1. Rating filtering
		// 2. Distance filtering

		float searchRating = searchCriteria.getStarRating();
		int searchDistance = searchCriteria.getDistance();
		int searchlanaguageId = searchCriteria.getLanguageId();
		String searchPercentMatch = searchCriteria.getPercentMatch();
		int pmatch  = Integer.parseInt(searchPercentMatch);
		if (searchRating > 0) {
			// now we got to filter based on the rating above the search rating.
			Log.debug(" Search Rating filtering starts now");
			filterByRating(searchRating, filteredListwithoutRetention);
			Log.debug(" Filtered size = " + filteredListwithoutRetention.size());
		}

		if(pmatch > 0 ) {
			filterByPMatch(pmatch, filteredListwithoutRetention);
		}
		if (searchDistance > 0) {
			// now we got to filter the list already created by the first one.
			Log.debug(" Search Rating filtering starts now with searchdistincae = "
					+ searchDistance);
			filterByRDistance(searchDistance, filteredListwithoutRetention);
		}
		if (searchRating > 0) {
			// now we got to filter based on the rating above the search rating.
			Log.debug(" Search Rating filtering starts now");
			filterByRating(searchRating, filteredListwithoutRetention);
			Log.debug(" Filtered size = " + filteredListwithoutRetention.size());
		}
		if ( searchlanaguageId > 0){
			filterByLangauageKnown(searchlanaguageId,filteredListwithoutRetention);
		}


		/*if (searchCriteria.isPercentMatchSort()) {
			sortFilteredListForPercentMatch(filteredListwithoutRetention);
		}
		if (searchCriteria.isDistanceSort()) {
			sortFilteredListForDistance(filteredListwithoutRetention);
		}*/

		filteredListwithRetention.addAll(filteredListwithoutRetention);

		return filteredListwithRetention;
	}

	// This would filtered out all the unwanted stuff from the list passsed to
	// it
	private void filterByRating(float rating, List filteredListwithoutRetention) {
		Iterator itr = filteredListwithoutRetention.iterator();
		while (itr.hasNext()) {
			SimpleProviderSearchProviderResultVO vo = (SimpleProviderSearchProviderResultVO) itr
					.next();
			if (vo.getProviderStarRating() >= rating)
				continue;
			else
				itr.remove();
		}
	}

	private void filterByRDistance(int distance, List filteredListwithoutRetention) {
		Iterator itr = filteredListwithoutRetention.iterator();
		while (itr.hasNext()) {
			SimpleProviderSearchProviderResultVO vo = (SimpleProviderSearchProviderResultVO) itr
					.next();
			if (vo.getDistance().floatValue() <= distance)
				continue;
			else
				itr.remove();
		}
	}
	private void filterByPMatch(int pmatch, List filteredListwithoutRetention) {
		Iterator itr = filteredListwithoutRetention.iterator();
		while (itr.hasNext()) {
			SimpleProviderSearchProviderResultVO vo = (SimpleProviderSearchProviderResultVO) itr
					.next();
			if (vo.getPercentageMatch().intValue() >= pmatch)
				continue;
			else
				itr.remove();
		}
	}


	private void filterByLangauageKnown(int langauageId, List filteredListwithoutRetention) {
		Iterator itr = filteredListwithoutRetention.iterator();
		while (itr.hasNext()) {
			boolean isToKeep = false;
			SimpleProviderSearchProviderResultVO vo = (SimpleProviderSearchProviderResultVO) itr
					.next();
			Iterator itr1 = vo.getLanguagesKnownByMe().iterator();
		     while(itr1.hasNext()){
		    	 LanguageVO lvo =  (LanguageVO)itr1.next();
		    	 if(lvo.getId().intValue() == langauageId) {
		    		 isToKeep = true;
		    		 break;
		    	 }
		     }
			if (isToKeep)
				continue;
			else
				itr.remove();
		}
	}


	// This method builds the list for selected
	protected List getProviderSelection() {

		// Assumption, these are the same index but to be safe let us name the

		// checkboxes for the providerids
		// list of selected ResouceID in Integer
		List selectedList = new ArrayList();
		Log.debug("Getting list of Selected provider...start..");

		for (int i = 0; i < providerSelectionCheckboxes.size(); i++) {

			CheckBox acheckbox = (CheckBox) providerSelectionCheckboxes.get(i);

			if (acheckbox.isChecked()) {

				SimpleProviderSearchProviderResultVO aProvResult = (SimpleProviderSearchProviderResultVO) currentProvidersMap
						.get(new Integer(Integer.parseInt(acheckbox.getName())));
				selectedList.add(aProvResult);
			}

		}
		Log.debug("Getting list of Selected provider...done..");
		return selectedList;

	}

	protected Map getCategorySelection() {
		// List of SimpleProviderSearchSkillNodeVO
		// First get the first level catrogy
		Map map = new HashMap();

		// init the map..
		map.put(GwtFindProvidersDTO.MAIN_VERTICLES, new SimpleProviderSearchSkillNodeVO());
		map.put(GwtFindProvidersDTO.CATEGORY, new SimpleProviderSearchSkillNodeVO());
		map.put(GwtFindProvidersDTO.SUB_SUB_CATEGORY, new SimpleProviderSearchSkillNodeVO());

		String selectedMainCategory = "0";
		String selectedCategory = "0";
		String selectedSubcategroy = "0";

		if(myObjects.selected_verticle.getValue() != null && !"".equals(myObjects.selected_verticle.getValue())){
			selectedMainCategory = myObjects.selected_verticle.getValue();
		}
		if (myObjects.catagoryListBox.getSelectedIndex() > 0) {
			selectedCategory = myObjects.catagoryListBox.getValue(myObjects.catagoryListBox
					.getSelectedIndex());
		}
		if (myObjects.subCategoryListbox.getSelectedIndex() > 0) {
			selectedSubcategroy = myObjects.subCategoryListbox
					.getValue(myObjects.subCategoryListbox.getSelectedIndex());
		}

		int selectedVertical = Integer.parseInt(selectedMainCategory);
		for (int i = 0; i < verticals.size(); ++i) {
			if (((SimpleProviderSearchSkillNodeVO) verticals.get(i)).skillNodeId == selectedVertical) {
				map.put(GwtFindProvidersDTO.MAIN_VERTICLES,
						(SimpleProviderSearchSkillNodeVO) verticals.get(i));
				break;
			}
		}

		int selectedCatagory = Integer.parseInt(selectedCategory);
		for (int i = 0; i < catagories.size(); ++i) {
			if (((SimpleProviderSearchSkillNodeVO) catagories.get(i)).skillNodeId == selectedCatagory) {
				map.put(GwtFindProvidersDTO.CATEGORY, (SimpleProviderSearchSkillNodeVO) catagories
						.get(i));
				break;
			}
		}

		int selectedsubCatagory = Integer.parseInt(selectedSubcategroy);
		for (int i = 0; i < subcatagories.size(); ++i) {
			if (((SimpleProviderSearchSkillNodeVO) subcatagories.get(i)).skillNodeId == selectedsubCatagory) {
				map.put(GwtFindProvidersDTO.SUB_SUB_CATEGORY,
						(SimpleProviderSearchSkillNodeVO) subcatagories.get(i));
				break;
			}

		}

		return map;

	}

	// List of SimpleProviderSearchSkillTypeVO vos...
	public List getSkillTypesChecboxSelected() {
		List jobsChecked = new ArrayList();
		for (int k = 0; k < skillTypeCkeckboxes.size(); ++k) {
			if (((CheckBox) skillTypeCkeckboxes.get(k)).isChecked()) {

				for (int i = 0; i < skillTypes.size(); i++) {
					SimpleProviderSearchSkillTypeVO aSkillTypeVO = (SimpleProviderSearchSkillTypeVO) skillTypes
							.get(i);
					if (aSkillTypeVO.getSkillTypeDescr().equals(
							((CheckBox) skillTypeCkeckboxes.get(k)).getText())) {
						jobsChecked.add(aSkillTypeVO);
					}
				}
			}
		}
		return jobsChecked;
	}

	public void saveDTOToSession() {

		// Save all the widget stuff to the DTO
		Log.debug("Calling save DTO .. building DTO...");
		gwtFindProvidersDto.setSelectedProviders(getProviderSelection());
		gwtFindProvidersDto.setSelectedCategorys(getCategorySelection());
		/*gwtFindProvidersDto.setSelectedFilterDistance(getMyObjects().DistanceComboBox
				.getValue(getMyObjects().DistanceComboBox.getSelectedIndex()));*/
		/*gwtFindProvidersDto.setSelectedFilterRating(getMyObjects().ratingComboBox
				.getValue(getMyObjects().ratingComboBox.getSelectedIndex()));*/
		/*gwtFindProvidersDto.setSelectedSortLable(getMyObjects().sortByListBox
				.getValue(getMyObjects().sortByListBox.getSelectedIndex()));*/
		gwtFindProvidersDto.setSo_id(getMyObjects().so_id.getValue());
		gwtFindProvidersDto.setState(getMyObjects().state_cd.getValue());
		gwtFindProvidersDto.setZip(getMyObjects().zip.getValue());
		gwtFindProvidersDto.setRedirectUrl(getMyObjects().getTheRedirectUrl());
		gwtFindProvidersDto.setCheckedJobs(getSkillTypesChecboxSelected());

		Log.debug("Calling get The DTO. to back end..");
		ProviderSearchCriteriaService.Util.getInstance().setDTOinSession(gwtFindProvidersDto,
				new AsyncCallback() {
					public void onSuccess(Object result) {
						// all is good
					GwtRemoteServiceResponse response  = (GwtRemoteServiceResponse)result;
						if(response.isValidSession()){
							redirect(gwtFindProvidersDto.getRedirectUrl());
						}else {
							showZipModal();
						}
					}

					public void onFailure(Throwable caught) {
						Log.debug("Failed  gwtFindProvidersSTO");
						Log.debug("exception occurred...", caught);
					}
				});

	}

	public void getDTOfromSession() {

		Log.debug("Calling get The DTO...");
		repaintrunning = true;
		ProviderSearchCriteriaService.Util.getInstance().getDTOFromSession(new AsyncCallback() {
			public void onSuccess(Object result) {
				GwtRemoteServiceResponse response  = (GwtRemoteServiceResponse)result;
				 if(response.isValidSession()){
					GwtFindProvidersDTO myGwtFindProvidersDTO = response.getGwtDto();
					if (myGwtFindProvidersDTO != null) {
						setStuffInGWTDTO(myGwtFindProvidersDTO, gwtFindProvidersDto);
						repaintFromSession();
						getMyObjects().lbl_zip.setText(myGwtFindProvidersDTO.getZip());
					}
				 }else {

				 }
			}

			public void onFailure(Throwable caught) {
				Log.debug("Failed  gwtFindProvidersSTO");
			}
		});

	}


	public void loadLanguages() {
		Log.debug("Calling get lanaguages...");
		ProviderSearchCriteriaService.Util.getInstance().getAllLanguages(new AsyncCallback() {
			public void onSuccess(Object result) {
				GwtRemoteServiceResponse response  = (GwtRemoteServiceResponse)result;
				 if(response.isValidSession()){
					List languages = response.getValueList();
					getMyObjects().languagesComboBox.addItem("Language", "-1");
					if (languages != null) {
						for(int i = 0; i < languages.size(); i++){
							LanguageVO vo = (LanguageVO) languages.get(i);
							getMyObjects().languagesComboBox.addItem(vo.getDescr(), vo.getId()+"");
						}
					}
				 }else {

				 }
			}

			public void onFailure(Throwable caught) {
				Log.debug("Failed  gwtFindProvidersSTO");
			}
		});
	}



	public void repaintFromSession() {

		if (this.gwtFindProvidersDto != null) {
			this.repaintrunning = true;
			repaintCategoriesfromSession();
			/*
			 * if(this.repaintrunning = false){
			 * getProviders(getInSearchVOFromDto()); }
			 */
		}
	}

	private SimpleProviderSearchCriteraVO getInSearchVOFromDto() {
		SimpleProviderSearchCriteraVO inSearchVO = new SimpleProviderSearchCriteraVO();
		// Set zip
		inSearchVO.setZip(this.gwtFindProvidersDto.getZip());
		// set the skillNode
		Map map = gwtFindProvidersDto.getSelectedCategorys();
		SimpleProviderSearchSkillNodeVO subcategorynode = (SimpleProviderSearchSkillNodeVO) map
				.get(GwtFindProvidersDTO.SUB_SUB_CATEGORY);
		SimpleProviderSearchSkillNodeVO categorynode = (SimpleProviderSearchSkillNodeVO) map
				.get(GwtFindProvidersDTO.CATEGORY);
		SimpleProviderSearchSkillNodeVO verticlenode = (SimpleProviderSearchSkillNodeVO) map
				.get(GwtFindProvidersDTO.MAIN_VERTICLES);
		if (subcategorynode != null && subcategorynode.getSkillNodeId() > 0) {
			inSearchVO.setTheSkillNode(subcategorynode);
		} else if (categorynode != null && categorynode.getSkillNodeId() > 0) {
			inSearchVO.setTheSkillNode(categorynode);
		} else if (verticlenode != null && verticlenode.getSkillNodeId() > 0) {
			inSearchVO.setTheSkillNode(verticlenode);
		}
		inSearchVO.setSkillTypes(gwtFindProvidersDto.getCheckedJobs());

		return inSearchVO;

	}

	protected void repaintCategoriesfromSession() {
		/*LoadProgressPopup pop = new LoadProgressPopup();
		pop.show();*/
		Map map = gwtFindProvidersDto.getSelectedCategorys();
		SimpleProviderSearchSkillNodeVO verticlenode = new SimpleProviderSearchSkillNodeVO();
		verticlenode = (SimpleProviderSearchSkillNodeVO) map
				.get(GwtFindProvidersDTO.MAIN_VERTICLES);
		Log.debug("Repaintrunnig = " + this.repaintrunning);
		// Log.debug(map.toString());
		Log.debug("getting and Repainting  main verticles with node it = "
				+ verticlenode.getSkillNodeId());
		getVerticals(verticlenode.getSkillNodeId());
		/*if(repaintrunning == false){
			pop.hide();
		}*/
	}

	public void setStuffInGWTDTO(GwtFindProvidersDTO from, GwtFindProvidersDTO to) {

		// set all of the stuff
		to.setSelectedCategorys(from.getSelectedCategorys());
		to.setCheckedJobs(from.getCheckedJobs());
		to.setRedirectUrl(from.getRedirectUrl());
		to.setSelectedFilterDistance(from.getSelectedFilterDistance());
		to.setSelectedFilterRating(from.getSelectedFilterRating());
		to.setSelectedProviders(from.getSelectedProviders());
		to.setSelectedSortLable(from.getSelectedSortLable());
		to.setZip(from.getZip());
		to.setState(from.getState());

		getMyObjects().state_cd.setValue(from.getState());
		getMyObjects().zip.setValue(from.getZip());
		to.setLockedProviderList(from.isLockedProviderList());
		to.setResourceID(from.getResourceID());

	}

	// redirect the browser to the given url
	public static native void redirect(String url)/*-{
				     $doc.getElementById("csoFindProviders").submit();
				  }-*/;

	public void sortColumn(String sortKey, String sortOrder) {

		List sortedlist = sortProvider(providerSortRetentionList, sortKey, sortOrder);
		this.providerSortRetentionList.clear();
		this.providerSortRetentionList.addAll(sortedlist);
		repaintFilteredGrid(getMyObjects().buildFilterSearch());

	}

	// List of the resource IDs from the provideretentionlist
	private List sortProvider(List nonSortedList, String sortKey, String sortOrder) {
		List sorted = new ArrayList(nonSortedList);
		Collections.sort(sorted, new HeaderFieldComparator(currentProvidersMap,sortKey,sortOrder));
		return sorted;
	}


	/**
	 * This method is called on the click checkbox on the
	 */
	public void selectAlltheProviders(boolean isAllselectOrNot) {
		SimpleProviderSearchCriteraVO searchcriteria = getMyObjects().buildFilterSearch();
		List filteredListwithRetention = getFilteredProvider(searchcriteria);
		int sizetoCheck = filteredListwithRetention.size() <= getMyObjects().MAX_RESULT_TO_DISPLAY ? filteredListwithRetention.size() : getMyObjects().MAX_RESULT_TO_DISPLAY;
		if(searchcriteria.isViewAll()){
			 sizetoCheck = filteredListwithRetention.size();
		}

		for (int j = 0; j < sizetoCheck ; ++j) {
			/*SimpleProviderSearchProviderResultVO aProvResult = (SimpleProviderSearchProviderResultVO) currentProvidersMap
					.get(filteredListwithRetention.get(j));*/

			SimpleProviderSearchProviderResultVO vo = (SimpleProviderSearchProviderResultVO) filteredListwithRetention.get(j);
			CheckBox chbox = getCheckBox(vo.getResourceId());
			if (isAllselectOrNot) {
				// Select all
				vo.setSelected(true);
				chbox.setChecked(true);

			} else {
				vo.setSelected(false);
				chbox.setChecked(false);
			}
		}

		repaintFilteredGrid(getMyObjects().buildFilterSearch());

	}

	public native void doVerticleClick(String id, String text)/*-{
		$wnd.doVerticleClick(id,text);
	}-*/;

	public native String showZipModal()/*-{
		//setHiddenFieldsAndShowModal('<s:property value='name'/>', '<s:property value='mainCategoryId'/>', '<s:property value='categoryId'/>', '<s:property value='subCategoryId'/>', '<s:property value='serviceTypeTemplateId'/>', '<s:property value='buyerTypeId'/>')"
		//href="#"><s:property value="name" />
		   $wnd.showModal();
	}-*/;
	
	public native String viewAllContinue(String isViewAll)/*-{
		$wnd.viewAllContinue(isViewAll);
	}-*/;
}
