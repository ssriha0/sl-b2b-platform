<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="OVERVIEW_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH%>" />
<c:set var="BUYER_TERM_COND_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_BUYER_TERM_COND_LENGTH%>" />
<c:set var="SPECIAL_INSTRUCTION_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH%>" />
<c:set var="TASK_COMMENTS_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_SCOPE_OF_WORK_TASK_COMMENTS_LENGTH%>" />
<!-- SL-20728 Enable Rich text editing -->
<script type="text/javascript">
  var max_chars_spl_instructions = 5000; //max characters
  var chars_without_html_spl_instructions = 0;
  tinymce.init({ 
	 	  selector:'#specialInstructions',
	 	  menubar: false,statusbar: false,
	 	  plugins: ["autolink autoresize link paste"],
		  toolbar1: '| undo redo | bold italic underline | formatselect | fontsizeselect |',
		  toolbar2: '| alignleft aligncenter alignright alignjustify | outdent indent | bullist numlist | link unlink | removeformat |',	
		  block_formats:'Paragraph=p;Heading 1=h1;Heading 2=h2;Heading 3=h3;Heading 4=h4;Heading 5=h5;Heading 6=h6',
		  autoresize_max_height: 100,
  		  setup: function (editor) {
		        editor.on('change', function () {
		            editor.save();
		        });
		        editor.on('keyup', function (editor, evt) {
		        	chars_without_html_spl_instructions = (tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
		        	var key = editor.keyCode;
		        	document.getElementById("specialInstructions_leftChars").value = max_chars_spl_instructions - chars_without_html_spl_instructions;
                    if (chars_without_html_spl_instructions >= max_chars_spl_instructions && key != 8 && key != 46) {
                    	editor.stopPropagation();
                    	editor.preventDefault();
                    }
		        });
		         editor.on('keydown', function (editor, evt) {
		        	 chars_without_html_spl_instructions = (tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
		        	var key = editor.keyCode;
		        	document.getElementById("specialInstructions_leftChars").value = max_chars_spl_instructions - chars_without_html_spl_instructions;
                    if (chars_without_html_spl_instructions >= max_chars_spl_instructions && key != 8 && key != 46) {
                    	editor.stopPropagation();
                    	editor.preventDefault();
                    }
		       }); 
		  }
	  });
  
  var max_chars_overview = 2500; //max characters
  var chars_without_html_overview = 0;
  tinymce.init({ 
	 	  selector:'#overview',
	 	  menubar: false,statusbar: false,
	 	  plugins: ["autolink autoresize link paste"],
		  toolbar1: '| undo redo | bold italic underline | formatselect | fontsizeselect |',
		  toolbar2: '| alignleft aligncenter alignright alignjustify | outdent indent | bullist numlist | link unlink | removeformat |',
		  block_formats:'Paragraph=p;Heading 1=h1;Heading 2=h2;Heading 3=h3;Heading 4=h4;Heading 5=h5;Heading 6=h6',
		  autoresize_max_height: 100,
  		  setup: function (editor) {
		        editor.on('change', function () {
		            editor.save();
		        });
		        editor.on('keyup', function (editor, evt) {
		        	chars_without_html_overview = (tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
		        	var key = editor.keyCode;
		        	document.getElementById("overview_leftChars").value = max_chars_overview - chars_without_html_overview;
                    if (chars_without_html_overview >= max_chars_overview && key != 8 && key != 46) {
                    	editor.stopPropagation();
                    	editor.preventDefault();
                    }
		        });
		         editor.on('keydown', function (editor, evt) {
		        	 chars_without_html_overview = (tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
		        	var key = editor.keyCode;
		        	document.getElementById("overview_leftChars").value = max_chars_overview - chars_without_html_overview;
                    if (chars_without_html_overview >= max_chars_overview && key != 8 && key != 46) {
                    	editor.stopPropagation();
                    	editor.preventDefault();
                    }
		       }); 
		  }
	  });
  
  var max_chars_buyerTandC = 5000; //max characters
  var chars_without_html_buyerTandC = 0;
  tinymce.init({ 
	 	  selector:'#buyerTandC',
	 	  menubar: false,statusbar: false,
	 	  plugins: ["autolink autoresize link paste"],
		  toolbar1: '| undo redo | bold italic underline | formatselect | fontsizeselect |',
		  toolbar2: '| alignleft aligncenter alignright alignjustify | outdent indent | bullist numlist | link unlink | removeformat |',
		  block_formats:'Paragraph=p;Heading 1=h1;Heading 2=h2;Heading 3=h3;Heading 4=h4;Heading 5=h5;Heading 6=h6',
		  autoresize_max_height: 100,
  		  setup: function (editor) {
		        editor.on('change', function () {
		            editor.save();
		        });
		        editor.on('keyup', function (editor, evt) {
		        	chars_without_html_buyerTandC = (tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
		        	var key = editor.keyCode;
		        	document.getElementById("buyerTandC_leftChars").value = max_chars_buyerTandC - chars_without_html_buyerTandC;
                    if (chars_without_html_buyerTandC >= max_chars_buyerTandC && key != 8 && key != 46) {
                    	editor.stopPropagation();
                    	editor.preventDefault();
                    }
		        });
		         editor.on('keydown', function (editor, evt) {
		        	 chars_without_html_buyerTandC = (tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
		        	var key = editor.keyCode;
		        	document.getElementById("buyerTandC_leftChars").value = max_chars_buyerTandC - chars_without_html_buyerTandC;
                    if (chars_without_html_buyerTandC >= max_chars_buyerTandC && key != 8 && key != 46) {
                    	editor.stopPropagation();
                    	editor.preventDefault();
                    }
		       }); 
		  }
	  });
  </script>

<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="General Information" id="" class="contentWellPane">
  <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.geninfo.description"/>
  
  
  <tags:fieldError id="Title" >
	  	<strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.title"/></strong>
	  	<font color="red">*</font>
	  	<br>
	  	<s:textfield theme="simple" name="title" id="title" cssStyle="width: 500px" cssClass="shadowBox" maxlength="255" value="%{title}"/>
  </tags:fieldError>
  
  <br/>
  <br/>
  <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.geninfo.overview.public"/> <font color="red">*</font>
  <s:textarea cssStyle="width: 657px" 
		name="overview"
	    id="overview" 
	    cssClass="shadowBox" value="%{overview}"/>
	    <input type="text" id="overview_leftChars" name="overview_leftChars" readonly size="4" maxlength="4" value="2500"> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>	    
  <br/>
  <br/>
  <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.geninfo.buyer.terms.public"/> <font color="red">*</font>
  <s:textarea cssStyle="width: 657px" 
	    name="buyerTandC"
	    id="buyerTandC" 
	    cssClass="shadowBox" value="%{buyerTandC}"/>
	    <input type="text" id="buyerTandC_leftChars" name="buyerTandC_leftChars" readonly size="4" maxlength="4" value="5000"> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>
  <br/>
  <br/>
  <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.geninfo.special.inst.private"/>
  <s:textarea cssStyle="width: 657px" 
	    name="specialInstructions"
	    id="specialInstructions" 
	    cssClass="shadowBox" value="%{specialInstructions}"/>
	    <input type="text" id="specialInstructions_leftChars" name="specialInstructions_leftChars" readonly size="4" maxlength="4" value="5000"> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>
  
</div>
