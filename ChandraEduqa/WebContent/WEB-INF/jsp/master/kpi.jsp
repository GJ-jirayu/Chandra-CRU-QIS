<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %> 
<%@page import="javax.portlet.PortletURL"%>

<portlet:actionURL var="formActionInsert">
	<portlet:param name="action" value="doInsertKpi"/>
</portlet:actionURL> 
<portlet:actionURL var="formActionEdit">
	<portlet:param name="action" value="doUpdateKpi"/>
</portlet:actionURL>
<portlet:actionURL var="formActionBack">
	<portlet:param name="action" value="doBack2List"/>
</portlet:actionURL>
<portlet:resourceURL var="doSearchKpiName" id="doSearchKpiName" ></portlet:resourceURL>
<portlet:resourceURL var="listStandardCriteria" id="listStandardCriteria" ></portlet:resourceURL>
<portlet:resourceURL var="doSaveCriteria" id="doSaveCriteria" ></portlet:resourceURL>
<portlet:resourceURL var="doDeleteCriteria" id="doDeleteCriteria" ></portlet:resourceURL>
<portlet:resourceURL var="listBaseline" id="listBaseline" ></portlet:resourceURL>
<portlet:resourceURL var="doDeleteBaseline" id="doDeleteBaseline" ></portlet:resourceURL>
<portlet:resourceURL var="doSaveQuanBaseline" id="doSaveQuanBaseline" ></portlet:resourceURL>
<portlet:resourceURL var="doSaveRangeBaseline" id="doSaveRangeBaseline" ></portlet:resourceURL>
<portlet:resourceURL var="doSaveSpecBaseline" id="doSaveSpecBaseline" ></portlet:resourceURL>
<portlet:resourceURL var="doSaveFormula" id="doSaveFormula" ></portlet:resourceURL>
<portlet:resourceURL var="requestCriteriaGroupDetail" id="requestCriteriaGroupDetail" ></portlet:resourceURL>
<portlet:resourceURL var="listCds" id="listCds" ></portlet:resourceURL>

<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>ตัวบ่งชี้</title>
    <!-- Bootstrap core CSS --> 
 <!--     <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" type="text/css"/>
    -->
    <script src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script> 
    <script  src="<c:url value="/resources/js/smoothness/jquery-ui-1.9.1.custom.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/smoothness/jquery-ui-1.9.1.custom.min.css"/>"/>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>  
	<script  src="<c:url value="/resources/bootstrap/js/bootstrap-typeahead.min.js"/>"></script> 
	<script src="<c:url value="/resources/js/confirm-master/jquery.confirm.min.js"/>"></script> 
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/override-portlet-aui.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/override-jqueryui.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/common-element.css"/>" type="text/css"/>
    <script type="text/javascript"> 
		var portletBoxName = "kpiDetail";
		var standardCriteriaId = 2;
	    $(function() {
	    	 $(".actionCnt div").hide();
	    	 $( "#accordion" ).accordion({ active:false,collapsible:true,autoHeight: false ,
		       	 beforeActivate: function( event, ui ) {
	 				 var msgCnt = $("#"+portletBoxName+" #accordionStateMessage");
	        		 msgCnt.html("");	
	        		 if(ui.newHeader.text()=="เกณฑ์มาตราฐาน/เกณฑ์ประเมิน"){
	        			if(!hasSaveKpi()){ msgCnt.append(" บันทึกตัวบ่งชี้ก่อน ") }
	        			var setMsg = hasSetCriteria();	 
	        			msgCnt.append(setMsg);
	        			if(!hasSaveKpi() || msgCnt.html()!=''){
	        				return false;  
	        			}else{
	        				toggleStandardLayout();
	        				toggleBaselineLayout();
	        			}
	        		 }
	        		 else if(ui.newHeader.text()=="สูตรการคำนวน"){
	        			 if(!hasSaveKpi()){ msgCnt.append(" บันทึกตัวบ่งชี้ก่อน ");return false;  }
	        			 else{
	        				 actCdsSearch();
	        			 }
	        		 }
	        	 } //end beforeAtive
	        });
		    var sctabs = $( "#StandardCriteriaTab" ).tabs();
		    var rctabs = $( "#quanBaselineTab" ).tabs();
		    var sptabs = $("#specBaselineTab").tabs();
		    
		    $("#rangeBaselineTab").tabs();
	        tabCriteriaCounter = $( "#quanBaselineTab" ).find("ul li").length;
	        $("#Text1").html(" ( 20 - [sum:cds1] ) / 100 ");
	        var sctabsCurrentStateLineCnt;
	        var rctabsCurrentLine;
	        var baselineEditItem;
	        validateNumber();
	        validateInteger();
	        pageMessage();
	   /*     sctabs.delegate( "span.ui-icon-close", "click", function() {
	            if(sctabs.find("ul li").length > 1 ){
		            var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
		            $( "#" + panelId ).remove();
		            sctabs.tabs( "refresh" );
	            }
	          });
	        rctabs.delegate( "span.ui-icon-close", "click", function() {
	            if(rctabs.find("ul li").length > 1 ){
		            var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
		            $( "#" + panelId ).remove();
		            rctabs.tabs( "refresh" );
	            }
	          });*/
	          //blind criterieType // quan or qual
	          //bind event
	       	 scoreNumChangeEvent();
	    });
	    function pageMessage(){
	    	if($('#pageMessage').val()==0){ //ok
	    		$('#kpi-msg').removeClass().addClass('alert');
	    	}else{
	    		$('#kpi-msg').removeClass().addClass('alert alert-danger');
	    	}
	    	$('#kpi-msg').focus();
	 		$('#kpi-msg').css( "display", "block" ).fadeOut( 30000 );
	    }
	    function validateNumber(){
	    	var specialKeys = new Array();
	        specialKeys.push(8); //Backspace
	        specialKeys.push(46); //period (.)
	    	 $(".numbersOnly").bind("keypress", function (e) {
	                var keyCode = e.which ? e.which : e.keyCode
	                var ret = ((keyCode >= 48 && keyCode <= 57) || specialKeys.indexOf(keyCode) != -1);
	                
	                if(!ret){
	                	$(this).css({"box-shadow" : "inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(253, 31, 31, 1)","border-color":"rgba(255, 0, 0, 1)"});
		                $(this).next('.error').css("display", ret ? "none" : "inline");	
	                }else{
	                	$(this).css({"box-shadow" : "","border-color":""});
		                $(this).next('.error').css("display","none");	
	                }
	                $(this).focusout(function(){
	                	$(this).css({"box-shadow" : "","border-color":""});
		                $(this).next('.error').css("display","none");	
	                })
	                return ret;
	       	});
	        $(".numbersOnly").bind("paste", function (e) {
	            return false;
	         });
	        $(".numbersOnly").bind("drop", function (e) {
	            return false;
	         });
	    }
	    function validateInteger(){
	    	var specialKeys = new Array();
	        specialKeys.push(8); //Backspace
	    	 $(".intOnly").bind("keypress", function (e) {
	                var keyCode = e.which ? e.which : e.keyCode
	                var ret = ((keyCode >= 48 && keyCode <= 57) || specialKeys.indexOf(keyCode) != -1);
	                
	                if(!ret){
	                	$(this).css({"box-shadow" : "inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(253, 31, 31, 1)","border-color":"rgba(255, 0, 0, 1)"});
		                $(this).next('.error').css("display", ret ? "none" : "inline");	
	                }else{
	                	$(this).css({"box-shadow" : "","border-color":""});
		                $(this).next('.error').css("display","none");	
	                }
	                $(this).focusout(function(){
	                	$(this).css({"box-shadow" : "","border-color":""});
		                $(this).next('.error').css("display","none");	
	                })
	                return ret;
	       	});
	        $(".intOnly").bind("paste", function (e) {
	            return false;
	         });
	        $(".intOnly").bind("drop", function (e) {
	            return false;
	         });
	    }
	    //
	    function hasSaveKpi(){
	    	if(	 $("#"+portletBoxName+" #kpiId").val()!='' ){
	    		return true;	
	    	}
	    		return false;
	    }
		function getKpiParentList(el){
				$(el).hide();
				$(el).next("img").show();
				var levelId = $("#detailLevel").val();
				var groupId = $("#detailGroup").val();
				var strId = $("#detailStructure").val();
				var target = $('#detailParent').empty();
				$.ajax({
	    			dataType:'json',
	    			url: "<%=doSearchKpiName%>" ,
	    			data: { 'level':levelId,'group': groupId, 'structure':strId  } ,
	    			success: function(data){
	    				if(data["header"]["size"]>0){
	    					var dat = data["content"]["lists"];
	    					var opt = $("<option>none</option>");
	    		    		target.append(opt);
		    				for(var i=0;i<dat.length;i++){
		    		    		var opt = $("<option value=\""+dat[i]["id"]+"\"></option>").html(dat[i]["name"]);
		    		    		target.append(opt);
		    		    	} 
		    				$(el).show();
		    				$(el).next("img").hide();
		    			}
	    			}
	    		});
		}
    	function doSubmitDetail(){
    		if($('#kpiDetail #kpiId').val()==""){
    			$('#kpiDetail #kpiFormDetail').attr("action","<%=formActionInsert%>");
    		}else{
    			$('#kpiDetail #kpiFormDetail').attr("action","<%=formActionEdit%>");
    		}
    		$('#kpiDetail #kpiFormDetail').submit();
    	}
    	function doBack2List(){
    		$('#kpiDetail #kpiFormDetail').attr("action","<%=formActionBack%>");
			$('#kpiDetail #kpiFormDetail').submit();
    	}
	    function showStandardCr(elName,editMode,headNo){
	    	// editMode 0,1
	    	var cnt = $("#standard").children("div.tabAction");
	    	var target = cnt.children("div#"+elName);
	    	cnt.children('div.actionCnt').hide(); //hide all
	    	target.show();
	    	target.find('input[type="text"]').each(function(){
	    		$(this).val("");
	    	});
	    	target.find('textarea').each(function(){
	    		$(this).val("");
	    	});
	    	target.find("table>tbody>tr:nth-child(1)").children("td:nth-child(2)").html(headNo);
	    	if(editMode==1 && elName=="actCrDetail"){
	    		target.find(".btnAdd").hide();
	    		target.find(".btnEdit").show();
	    	}
	    	else if(editMode==0 && elName=="actCrDetail"){
	    		target.find(".btnAdd").show();
	    		target.find(".btnEdit").hide();
	    	}
	    }
	    function hideCriteriaGroup(elName){
	    	$("#"+elName).hide();
	    }
	    function crGroupChange(el,detailElName){
	    	var target = $( el ).next("select"); //mean group detail
	    	target.empty();
	    	target.prop( "disabled", true );
	    	var value = parseInt($(el).val());
	    	$.ajax({
    			dataType:'json',
    			url: "<%=requestCriteriaGroupDetail%>" ,
    			data: { 'groupId': value  } ,
    			success: function(data){
    				target.prop( "disabled", false );
    				if(data["header"]["size"]>0){
    					var dat = data["content"]["lists"];
	    				for(var i=0;i<dat.length;i++){
	    		    		var opt = $("<option value=\""+dat[i]["id"]+"\"></option>").html(dat[i]["name"]);
	    		    		target.append(opt);
	    		    	} 
    				}
    			}
    		});
	    }
	    function getActiveTabId(elName,word){
	    	var id = 0;
	    	//StandardCriteriaTab
	    	//var activeDiv = $("div#"+elName+".ui-tabs>div.ui-tabs-panel[aria-expanded=\"true\"]");
	    	var activeDiv = $("div#"+elName+" ul.tabsList>li.ui-state-active");
	    	var strDivId = activeDiv.attr("aria-controls");
	    	if( typeof activeDiv !="undefined"){
	    		if( typeof strDivId!="undefined"){
	    			strDivId = strDivId.replace(word,"")//remove word
	    			id =parseInt(strDivId);
	    		}
	    	}
	    	return id;
	    }
	    function getActiveTab(elName){
	    	var activeDiv = $("div#"+elName+".ui-tabs>div.ui-tabs-panel[aria-expanded=\"true\"]");
	    	var str = activeDiv.attr("id");
	    	return str;
	    }
	    function removeTabs(tabsId,tabDivId){ 
	    	var headTab = $('div#'+tabsId+'>ul.tabsList>li[aria-controls="'+tabDivId+'"]');
	    	headTab.remove();
	    	$('div#'+tabsId+' #'+tabDivId).remove();
	    	//refresh
	    	$('div#'+tabsId).tabs( "refresh" );
	    }
	    function posOnTab(tabName,name){
	    	//return id number sequecen
	    	var ret = 0;
	    	var running = 1;
	    	var flag = 0;
	    	$("#"+tabName+" ul.tabsList>li>a").each(function(){
	    		if(flag == 0 && name==$(this).html() ){
	    			ret = running;
	    			flag == 1;
	    		}
	    		running++;
	    	});
	    	return ret;
	    }
	    function listStandardCriteria(){
	    	var kpiId = $("#"+portletBoxName+" #kpiId").val();
	    	$.ajax({
    			dataType:'json',
    			url: "<%=listStandardCriteria%>" ,
    			data: { kpiId : kpiId},
    			success: function(data){
    				var hsize = data["header"]["size"];
    				var dat = data["content"]["data"];
    				if(hsize>0){
    					for(var i=0;i<dat.length;i++){
    						var pos = posOnTab("StandardCriteriaTab",dat[i]["groupName"]); //fail
    				    	var tabCnt = $("#"+portletBoxName+" #StandardCriteriaTab");
    						if(!pos){
    	    					createStandardTab(tabCnt,dat[i]["groupId"],dat[i]["groupName"]);
    	    				}
    						var contentCnt = $("#"+portletBoxName+" #StandardCriteriaTab"+" #std-tab-"+dat[i]["groupId"]);
    	    				createStandardDesc(contentCnt,dat[i]["stdId"],dat[i]["runNo"],dat[i]["stdName"],dat[i]["cds"]);
    					}// end loop
    				}// end hsize
    			}
    		});
	    }
	    function createStandardTab(tab,tabId,tabName){
		    	tab.tabs("destroy")
		    	var newId = "std-tab-"+tabId;
	    		// tabs list
	    		var a = $("<a></a>").html(tabName);
	    		a.attr("href","#"+newId);
	    		var li = $("<li></li>").append(a);
	    		tab.find("ul.tabsList").append(li);
	    		//content
	    		var tabContentTemplate = $("#"+portletBoxName+" #criteriaTabContentTemplate #stdCrTabContentTemplate");
	    		var newTab = tabContentTemplate.clone();
	    		newTab.attr("id",newId);
	    		tab.append(newTab);
	    		tab.tabs();
	    }
	    function getLastSeqOfTable(jqTable){
	    	// if null = 1;
	    	var runNo = jqTable.find("tbody tr:last-child").children("td:nth-child(1)").html();
	    	if(typeof runNo == "undefined" || runNo ==""){
	    		runNo = 0;
	    	}
	    	return runNo;
	    }
	    function createStandardDesc(tabContent,stdId,runNo,crName,crCds){
	    	//get value in input div
	    	//var descCnt = $("div#StandardCriteriaTab.ui-tabs>div.ui-tabs-panel[aria-expanded=\"true\"]");
	    	var newLine = $("<tr></tr>");
	    	/*var lastSeq =  getLastSeqOfTable(tabContent.find("table"));
	    	newLine.append($("<td></td>").html(++lastSeq));*/
	    	newLine.append($("<td></td>").html(runNo));
	    	newLine.append($('<td style="display:none"></td>').html(stdId));
	    	newLine.append($("<td></td>").html(crName));
	    	newLine.append($("<td></td>").html(crCds+"NNNNNNN"));
	    	var td = $('<td class="col-action"></td>');
	    	var actEditBtn = $('<img height="24" width="24" style="cursor: pointer" src="<c:url value="/resources/images/edited.png"/>"   onClick="actStdCrEdit(this)" />'); 
	    	td.append(actEditBtn);
	    	newLine.append(td);
	    	var actDelBtn = $('<img height="24" width="24" style="cursor: pointer" src="<c:url value="/resources/images/delete.png"/>" onClick="actStdCrDel(this)" />');
	    	var tdd = $('<td class="col-action"></td>');
	    	tdd.append(actDelBtn);
	    	newLine.append(tdd);
	    	tabContent.find("tbody").append(newLine);
	    }
	    function editStandardDesc(){
	    	var srcCrName = $("#actCrDetail>table").find("tr:nth-child(2) textarea").val();
	    	var srcCrCds =  $("#actCrDetail>table").find("tr:nth-child(4) input:nth-child(1)").val();
	    	sctabsCurrentStateLineCnt.find("td:nth-child(3)").html(srcCrName);
	    	sctabsCurrentStateLineCnt.find("td:nth-child(4)").html(srcCrCds);
	    	hideCriteriaGroup('actCrDetail');
	    }
	    function addStdCr(){
	    	//swapEnabledTab('StandardCriteriaTab');
	    	var id;
	    	var desc = $("#actCrDetail>table").find("tr:nth-child(2) textarea").val();
	      	var cdsId = $("#actCrDetail>table").find("tr:nth-child(4) input:nth-child(1)").val();
	    	var kpiId = $("#kpiId").val();
	    	var gId = $("select#crGroupItem").val();
	    	var gName = $("#"+portletBoxName+" #crGroupItem option:selected").text();
	    	var lastRunNo = getLastSeqOfTable($("#StandardCriteriaTab #std-tab-"+gId));
	    	var runNo = parseInt(lastRunNo) +1;
    		$.ajax({
    			dataType:'json',
    			url: "<%=doSaveCriteria%>" ,
    			data: { 'stdId': id , 'runNo':runNo, 'desc': desc
    				, 'kpiId':kpiId , 'cdsId':cdsId , 'groupId': gId  } ,
    			success: function(data){
    				if(data["header"]["type"]=="insert"&&data["content"]["stdId"]>0){
    					var tabCnt = $("#"+portletBoxName+" #StandardCriteriaTab");
    					var pos = posOnTab('StandardCriteriaTab',gName);
    					if(!pos){
        					createStandardTab(tabCnt,gId,gName);	
    					}
    			    	var tabContentCnt = tabCnt.find("div#std-tab-"+gId);
    			    	createStandardDesc(tabContentCnt,data["content"]["stdId"],runNo,desc,cdsId);
    		    		tabCnt.tabs("option","active", parseInt(pos)-1);
    				}
    			},
    			complete: function(data){
    				hideCriteriaGroup('actCrDetail');
    			}
    		});
	    }
	    function  editStdCr(){
	    	//swapEnabledTab('StandardCriteriaTab');
	    	var stdId = $("#actCrDetail>table").find("tr:nth-child(5) td:nth-child(2)").html();
	    	var desc = $("#actCrDetail>table").find("tr:nth-child(2) textarea").val();
	      	var cdsId = $("#actCrDetail>table").find("tr:nth-child(4) input:nth-child(1)").val();
	    	var kpiId = $("#kpiId").val();
	    	var gId = $("select#crGroupItem").val();
	    	var runNo = $("#actCrDetail>table").find("tr:nth-child(1) td:nth-child(2)").html();
	    	$.ajax({
    			dataType:'json',
    			url: "<%=doSaveCriteria%>" ,
    			data: { 'stdId': stdId , 'runNo':runNo, 'desc': desc
    				, 'kpiId':kpiId , 'cdsId':cdsId , 'groupId': gId  } ,
    			success: function(data){
    				if(data["header"]["type"]=="update"){
    					editStandardDesc();
    				}
    			},
    			error: function(data){
    				alert('บันทึกผิดพลาด');
    			},
    			complete: function(data){
    				hideCriteriaGroup('actCrDetail');
    			}
    		});
	    }
	    function swapEnabledTab(divId){
	    	//swap enabled , disabled (all tab)
	    	if($( "#"+divId ).tabs( "option", "disabled")==true){
	    		$( "#"+divId ).tabs( "option", "disabled",false );
	    	}else{
	    		$( "#"+divId ).tabs( "option", "disabled",true );
	    	}
	    }
	    function actStdCrAdd(el){
	    	//swapEnabledTab('StandardCriteriaTab');
	    	$("#actCrDetail>table").find("tr:nth-child(3) select").each(function(){
	    		$(this).removeAttr('disabled');
	    	});
	    	/*var activeTab = getActiveTabId('StandardCriteriaTab','std-tab-');
	    	var runNo = 0;
	    	if(activeTab==0){
	    		runNo = getLastSeqOfTable($('#std-tab-'+activeTab).find("table"));
	    	}*/
	    	$("#actCrDetail>table tr:nth-child(1)").hide();
	    	showStandardCr('actCrDetail',0,0);
	    }
	    function actStdCrEdit(el){
	    	//swapEnabledTab('StandardCriteriaTab');
	    	$("#actCrDetail>table tr:nth-child(1)").show();
	    	var runNo = $(el).parent("td").parent("tr").children("td:nth-child(1)").html();
	    	var stdId = $(el).parent("td").parent("tr").children("td:nth-child(2)").html();
	    	var datName = $(el).parent("td").parent("tr").children("td:nth-child(3)").html();
	    	var datCds = $(el).parent("td").parent("tr").children("td:nth-child(4)").html();

	    	showStandardCr('actCrDetail',1,runNo);
	    	var inputCnt = $("#actCrDetail>table");
	    	inputCnt.find("tr:nth-child(2) textarea").val(datName);
	    	inputCnt.find("tr:nth-child(3) select").each(function(){
	    		$(this).attr('disabled', 'disabled');
	    	});
	    	inputCnt.find("tr:nth-child(4) input:nth-child(1)").val(datCds);	
	    	inputCnt.find("tr:nth-child(5) td:nth-child(2)").html(stdId);
	    	sctabsCurrentStateLineCnt = $(el).parent("td").parent("tr");
	    }
	    function actStdCrDel(el){
	    	var stdId = $(el).parent("td").parent("tr").children("td:nth-child(2)").html();
	    	if (confirm('ลบเกณฑ์มาตราฐาน')) {
	    		$.ajax({
	    			dataType:'json',
	    			url: "<%=doDeleteCriteria%>" ,
	    			data: { 'stdId': stdId   } ,
	    			success: function(data){
	    				if(data["header"]["type"]=="delete"){
	    					var tbody = $(el).parent("td").parent("tr").parent("tbody");
	    					$(el).parent("td").parent("tr").remove();
	    					if(tbody.find("tr").size()==0){
	    						removeTabs('StandardCriteriaTab',getActiveTab('StandardCriteriaTab'));
	    					}
	    				}
	    			}
	    		});
	    	} else {
	    	}
	    }
	    function findActiveTabByNameGetIndex(cnt,name){
	    	// return number of tab
	    	var startIndex=0;
	    	var index=-1;
	    	$("#"+portletBoxName).find("#"+cnt).find("ul li a").each(function(){
	    		if($(this).html()==name){
	    			index=startIndex;
	    		}
	    		startIndex++;
	    	});	
	    	return index;
	    }
	    // result criteria tabs
	     function showCriteriaGroup(current,elName,editMode){
	    	//hide , show
	    	var cnt = $(current).closest('.tabAction');
		    var target = cnt.children("div#"+elName);
		    cnt.children('div.actionCnt').hide(); //hide all
	    	target.show();
	    	target.find('input[type="text"]').each(function(){
	    		$(this).val("");
	    	});
	    	target.find('textarea').each(function(){
	    		$(this).val("");
	    	});
		    if(editMode==1 ){
	    		target.find(".btnAdd").hide();
	    		target.find(".btnEdit").show();
	    	}
	    	else if(editMode==0 ){
	    		target.find(".btnAdd").show();
	    		target.find(".btnEdit").hide();
	    	}
	    }
    	function hasSetCriteria(){
    		var listError = [];
    		if( $("#"+portletBoxName+" #criteriaTypeId").val() ==''){
    			listError.push("เกณฑ์ประเมิน");
    		}
    		if ( $("#"+portletBoxName+" input[name='radioCriteriaScore']:checked").val() == ''){
    			listError.push("ประเภทคะแนน");
    		}
    		if ($("#"+portletBoxName+" #criteriaScore").val() == '' ){
    			listError.push("คะแนนเต็ม");
    		}
    		if ($("#"+portletBoxName+" #criteriaMethod").val()== ''){
    			listError.push("เกณฑ์แปลงคะแนน");
    		}
    		var msg = "";
    		if(listError.length>0){
    			msg=" ยังไม่ได้ระบุ "+listError.join(",")+" ";
    		}
    		return msg
    	}
    	function scoreNumChangeEvent(){
    		$('input[type=radio][name=radioCriteriaScore]').change(function() {
    			scoreNumChange();
    	    });
    		$('#criteriaScore').change(function(){
    			scoreNumChange();
    		});
    	}
    	function toggleStandardLayout(){ 
    		var ctype = $("#"+portletBoxName+" #criteriaTypeId").val();
    		var qualValue = standardCriteriaId;
    		var cnt = $("#standardCriteriaCnt").children("td:nth-child(2)");
    		clearCnt(cnt);
    		var template = $("#criteriaTemplate #standard");
    		if(ctype==qualValue){
    			template.detach().appendTo(cnt);
   		 		listStandardCriteria();
    		}else{
    		}
    	}
    	function toggleBaselineLayout(){
    		var method = $("#"+portletBoxName+" #criteriaMethod").val();
    		var cnt = $("#resultCriteriaCnt").children("td:nth-child(2)");
    		clearCnt(cnt);
    		if(method==1){
    			var template = $("#criteriaTemplate #baseline");
    			cnt.html(template);
    			listQuanBaseline();
    		}else if(method==3){
    			var template = $("#criteriaTemplate #baselineRange");
    			cnt.html(template);
    			scoreNumChange();
    			listRangeBaseline();
    		}
    		else if(method==4){
    			specTabScoreList();
    			var template = $("#criteriaTemplate #baselineSpec");
    			cnt.html(template);
    			listSpecBaselineGroup();
    			chooseSpecScore();
    		}
    	}
    	function criteriaTypeChange(el){
    		toggleStandardLayout();
    		//change crteria score
    		$('select#criteriaMethod').val("");
    		if($('#criteriaTypeId').val()==1) // 1= ปริมาณ 2=คุณภาพ
    		{
        		$('input#radioCriteriaScore1').attr('checked', true);
        		$('#radioCriteriaScore2').prop("disabled",true);
        		//$('select#criteriaMethod option').prop("disabled",false);
        		//$('select#criteriaMethod option[value="1"]').prop("disabled",true);
        		//$('select#criteriaMethod option[value="2"]').prop("disabled",true);
    		}else{
    			$('#radioCriteriaScore2').prop("disabled",false);
    			//$('select#criteriaMethod option').prop("disabled",false);
    			//$('select#criteriaMethod option[value="3"]').prop("disabled",true);
        		//$('select#criteriaMethod option[value="4"]').prop("disabled",true);
    		}
    	}
    	function clearCnt(jCnt){
    		if(typeof jCnt != 'undefined'){
    			var cnt = $("#criteriaTemplate");
    			jCnt.children("div.templateItem").children("div.tabContainer").children("ul").empty();
    			jCnt.children("div.templateItem").children("div.tabContainer").children("div").remove();
    			jCnt.children("div.templateItem").detach().appendTo(cnt);
    		}
    	}
	    function formulaAppendCds(method,el){
	    	var cdsString = "["+method+":cds(Id)]";
	    	cdsString = cdsString.replace("(Id)",$(el).parent("td").parent("tr").children("td:nth-child(1)").html());
		    var el = $("#"+portletBoxName+" #formula");
		    var start = el.prop("selectionStart");
		    var end = el.prop("selectionEnd");
		    var text = el.val();
		    var before = text.substring(0, start);
		    var after  = text.substring(end, text.length);
		    el.val(before + cdsString + after);
		    el[0].selectionStart = el[0].selectionEnd = start + cdsString.length;
		    el.focus();
	    }
    	function doSubmitFormula(){
    		var formulaString = $("#"+portletBoxName+" #formula").val();
    		var formulaDesc = $("#"+portletBoxName+" #formulaDesc").val();
    		var percentFlag = "1"; //'1','0'
    		var kpiId = $("#kpiId").val();
    		if (confirm('บันทึกสูตรการคำนวน')) {
    			$.ajax({
        			dataType:'json',
        			url: "<%=doSaveFormula%>" ,
        			data: { 'kpiId':kpiId,'formula': formulaString,'desc':formulaDesc,'percent':percentFlag  } ,
        			success: function(data){
        			//	alert(JSON.stringify(data));
        			}
        		});
    		}else{
    			
    		}
    	}
    	// qual baseline
    	function addQuanGroup(el){
    		//check tab Exist
    		var cnt = $("#"+portletBoxName+" #quanBaselineTab");
    		var groupName = $("#"+portletBoxName+" #rcGroupItem option:selected").text();
    		var groupId = $("#"+portletBoxName+" #rcGroupItem option:selected").val();
    		var pos=posOnTab('quanBaselineTab',groupName);
    		if(typeof groupId != "undefined"){
	    		if(pos){
	    			alert('ซ้ำ');
	    			cnt.tabs( "option", "active", parseInt(pos)-1 );
	    		}
	    		else{
	    			createQuanTab(cnt,groupId,groupName);
	    		}
    		}
    	}
    	function saveQuan(current){
    		var cnt = $(current).closest("table");
    		var baselineId = cnt.find("tr:nth-child(1)").find("td:nth-child(2)").html();
    		var kpiId  = $("#"+portletBoxName+" #kpiId").val();
    		var groupId = getActiveTabId('quanBaselineTab','quan-tab-');
    		var subVal = cnt.find("tr:nth-child(2)").find("td:nth-child(2)").find("input").val();
    		var percentVal = cnt.find("tr:nth-child(3)").find("td:nth-child(2)").find("input").val();
    		$.ajax({
    			dataType:'json',
    			url: "<%=doSaveQuanBaseline%>" ,
    			data: { 'type':'quan','baselineId':baselineId,'kpiId':kpiId,'groupId': groupId,'subVal':subVal,'percentVal':percentVal  } ,
    			success: function(data){
    				//alert(JSON.stringify(data));
    				if(data["content"]["baselineId"]!=0){
    					alert("บันทึกสำเร็จ");
    					cnt.find("tr:nth-child(1)").find("td:nth-child(2)").html(data["content"]["baselineId"]);
    				}else{
    					alert("บันทึกผิดพลาด");
    				}
    			}
    		});
    	}
    	function deleteQuan(current){
    		var cnt = $(current).closest("table");
    		var baselineId = cnt.find("tr:nth-child(1)").find("td:nth-child(2)").html();
    		var baselineName = "test";
    		if(confirm('ยินยันการลบ "'+baselineName+'"')){
	    		$.ajax({
	    			dataType:'json',
	    			url: "<%=doDeleteBaseline%>" ,
	    			data: { 'type':'quan','baselineId':baselineId } ,
	    			success: function(data){
	    	    		if(data["header"]["success"] == 1){
	    	    			removeTabs('quanBaselineTab',getActiveTab('quanBaselineTab'));
	    	    		}
	    			}
	    		});
    		}
    	}
    	function listQuanBaseline(){
    		var kpiId  = $("#"+portletBoxName+" #kpiId").val();
    		var type = 'quan' // quan/range/spec
    		$.ajax({
    			dataType:'json',
    			url: "<%=listBaseline%>" ,
    			data: { 'type':'quan','kpiId':kpiId } ,
    			success: function(data){
    				//alert(JSON.stringify(data));
    				if(data["header"]["size"]>0){
    					var dat = data["content"]["lists"];
    					var cnt = $("#"+portletBoxName+" #quanBaselineTab");
    					for(var i=0;i<dat.length;i++){
    						createQuanTab(cnt,dat[i]["groupId"],dat[i]["groupName"])
    						var tabContent = cnt.children("div#quan-tab-"+dat[i]["groupId"])
    						createQuanDesc(tabContent,dat[i]["baselineId"],dat[i]["subVal"],dat[i]["conVal"]);
    					}
    				}
    			}
    		});
    	}
	    function createQuanTab(tab,tabId,tabName){
		    	tab.tabs("destroy")
		    	var newId = "quan-tab-"+tabId;
	    		// tabs list
	    		var a = $("<a></a>").html(tabName);
	    		a.attr("href","#"+newId);
	    		var li = $("<li></li>").append(a);
	    		tab.find("ul.tabsList").append(li);
	    		//content
	    		var tabContentTemplate = $("#"+portletBoxName+" #criteriaTabContentTemplate #baselineQuanTabContentTemplate");
	    		var newTab = tabContentTemplate.clone();
	    		newTab.attr("id",newId);
	    		tab.append(newTab);
	    		tab.tabs();
	    		//validateNumber();
	    }
	    function createQuanDesc(tabContent,quanId,subVal,conVal){
	    	tabContent.find("table").find("tr:nth-child(1)").children("td:nth-child(2)").html(quanId);
	    	tabContent.find("table").find("tr:nth-child(2)").children("td:nth-child(2)").children("input").val(subVal);
	    	tabContent.find("table").find("tr:nth-child(3)").children("td:nth-child(2)").children("input").val(conVal);
	    }
	    function scoreNumChange(){
	    	var sel = $("#actRangeDetail").children("table").find("select");
	    	sel.empty();
	    	var scoreType = $('input[name=radioCriteriaScore]:checked').val(); // integer,pass
	    	if( scoreType=="integer" ){
	    		var score = $('#criteriaScore').val();
	    		for(var i = 1;i<=score;i++){
	    			var opt = $('<option value="'+i+'">').html(i);
	    			sel.append(opt);
	    		}
	    	}else if(scoreType=="pass"){
	    		sel.append( $('<option value="0">').html("ไม่ผ่าน") );
	    		sel.append( $('<option value="1">').html("ผ่าน") );
	    	}else{
	    	}
	    }
		function listRangeBaseline(){
			// render data
			var kpiId  = $("#"+portletBoxName+" #kpiId").val();
    		var type = 'range' // quan/range/spec
    		$.ajax({
    			dataType:'json',
    			url: "<%=listBaseline%>" ,
    			data: { 'type':type,'kpiId':kpiId } ,
    			success: function(data){
    				//alert(JSON.stringify(data));
    				var list = data["content"]["lists"];
    				for(var i=0;i<list.length;i++){
    					renderRangeGroup(0,list[i]["groupId"],list[i]["groupName"]);
    					renderRangeDetail(list[i]["groupId"],list[i]["baselineId"],list[i]["score"],list[i]["begin"],list[i]["end"],list[i]["desc"].replace(/\r\n|\n|\r/g, '<br/>'));
    				}
    			}
    		});
		}
    	function newRangeTr(id){
    		var tr  = $("<tr></tr>");
    		var cnt = $("#actRangeDetail table tbody")
    		var score = cnt.children("tr:nth-child(3)").children("input").val();
    		var beginV = cnt.children("tr:nth-child(4)").children("input").val();
    		var endV = cnt.children("tr:nth-child(5)").children("input").val();
    		var desc =  cnt.children("tr:nth-child(6)").children("input").val();
			tr.append( $("<td></td>").html(id) );
			tr.append( $("<td></td>").html( $('<input type="text">').val( score ) ) );
			tr.append( $("<td></td>").html( $('<input type="text">').val( beginV ) ) );
			tr.append( $("<td></td>").html( $('<input type="text">').val( endV ) ) );
			tr.append( $("<td></td>").html( $('<input type="text">').val( desc ) ) );
			return tr;
    	}
    	function renderRangeBaseline(dat){
			// check tab exist
    		var headName = "range-tab-";
    		var cnt = $("#"+portletBoxName+" #rangeBaselineTab");
    		var tabName = $("#"+portletBoxName+" #rangeGroupItem option:selected").text();
    		var groupId = $("#"+portletBoxName+" #rangeGroupItem option:selected").val();
    		//var index=findActiveTabByNameGetIndex('rangeBaselineTab',tabName);
    		var pos = posOnTab("rangeBaselineTab",tabName);
    		if(pos>0){ // exist add tr
    			var work = cnt.children("#"+headName+dat["groupId"]);
    			work.append( newRangeTr(dat["baselineId"] ) );
    		}
    		else{
	    		cnt.tabs("destroy");
	    		var newId = headName+dat["groupId"];
	    		// tabs list
	    		var newEl = cnt.find("ul.tabsList>li:nth-child(1)").clone();
	    		newEl.find("a:nth-child(1)").html(tabName); // text
	    		newEl.find("a:nth-child(1)").attr("href","#"+newId); // link tab el
	    		cnt.find("ul.tabsList").append(newEl);
	    		//content
	    		newEl = cnt.find("div.tabsContent").first().clone();
	    		newEl.attr("id",newId);
	    		newEl.attr("class","tabsContent");
	    		newEl.find("table").find("tr:nth-child(1)").find("td:nth-child(2)").html(groupId);
	    		newEl.find("input[type=\"text\"]").val(""); // value reset
	    		cnt.append(newEl);
	    		cnt.tabs({ active:parseInt(num)-1});
    		}
    	}
    	function renderBaselineRangeScore(){
    		var cnt = $("#baselineRange tbody");
    		cnt.empty();
    		var radio = $('input[name=radioCriteriaScore]:checked').val(); //integer or pass
    		var score = 0;
    		score = $("#criteriaScore").val();
    		if( radio="pass"){
    			score = 2;
    		}
    		if(score>0){
    			var percent = 100/score;
    			var start = 0; // ((score-1)*percent)+1
    			var end = 100; // score*percent;
    			for(var i=0;i<score;i++){
    				var tr  = $("<tr></tr>");
    				tr.append( $("<td></td>").html() );
    				tr.append( $("<td></td>").html( $('<input type="text">').val(i) ) );
    				var s = i>0? ((i-1)*percent)+1 : 1 ;
    				tr.append( $("<td></td>").html( $('<input type="text">').val( s ) ) );
    				tr.append( $("<td></td>").html( $('<input type="text">').val( i*percent ) ) );
    				tr.append( $("<td></td>").html( $('<input type="text">').val( i*percent ) ) );
    			}
    		}
    	}
    	function renderBaselineSpec(){
    		
    	}
    	function addRangeScore(){
    		var tabId = getActiveTabId("rangeBaselineTab","range-tab-");
    		var cnt = $("#actRangeDetail table tbody");
    		//cnt.children("tr:nth-child(2)").children("td:nth-child(2)").html("");
    		cnt.children("tr:nth-child(2)").children("td:nth-child(2)").html(tabId);
    		saveRangeScore('insert');
    	}
    	function editRangeScore(current){
    		saveRangeScore('edit');
    	}
    	function saveRangeScore(actionName){
    		//actionName insert,edit
    		var cnt = $("#actRangeDetail table tbody");
    		var kpiId = $("#kpiId").val();
    		var baselineId = cnt.children("tr:nth-child(1)").children("td:nth-child(2)").html();
    		var tabId = cnt.children("tr:nth-child(2)").children("td:nth-child(2)").html();
    		var score = cnt.children("tr:nth-child(3)").children("td:nth-child(2)").children("select").val();
    		var begin = cnt.children("tr:nth-child(4)").children("td:nth-child(2)").children("input").val();
    		var end = cnt.children("tr:nth-child(5)").children("td:nth-child(2)").children("input").val();
    		var desc = cnt.children("tr:nth-child(6)").children("td:nth-child(2)").children("input").val();
    	//	alert(score+":"+begin+":"+end+":"+desc);
    		$.ajax({
    			dataType:'json',
    			url: "<%=doSaveRangeBaseline%>" ,
    			data: { 'type':'range','baselineId':baselineId,'kpiId':kpiId,'groupId':tabId
    				,'score':score,'begin':begin,'end':end ,'desc':desc } ,
    			success: function(data){
    				//alert(JSON.stringify(data));
    				if(actionName=="insert"){
    					renderRangeDetail(tabId,data["content"]["data"]["baselineId"],score,begin,end,desc.replace(/\r\n|\n|\r/g, '<br/>'));
    				}
    				else if(actionName=="edit"){
    					renderRangeDetailEdit(tabId,baselineId,score,begin,end,desc.replace(/\r\n|\n|\r/g, '<br/>'));
    				}
    			}
    		});
    	}
    	function actAddRangeGroup(){
    		var tabname = $("#"+portletBoxName+" #rangeGroupItem option:selected").text();
    		var tabid = $("#"+portletBoxName+" #rangeGroupItem option:selected").val();
    		renderRangeGroup(1,tabid,tabname);
    	}
    	function renderRangeGroup(enableAlert,tabid,tabname){
    		var cnt = $("#rangeBaselineTab");
    		var pos = posOnTab("rangeBaselineTab",tabname);
    		if(pos>0){ //exist
    			if(enableAlert){	 alert('ซ้ำ');	}
    		}else{
    			//var nextId = parseInt(cnt.find("ul li").length)+1;
    			cnt.tabs("destroy");
				var newId = "range-tab-"+tabid;
				// add list
				var newA = $("<a></a>");
				newA.attr("href","#"+newId);
				newA.html(tabname);
				var newListItem = $("<li></li>").append(newA);
				cnt.find("ul").append(newListItem);
				//content only table head
				var template = tabContentTemplate = $("#"+portletBoxName+" #criteriaTabContentTemplate #baselineRangeTabContentTemplate");
	    		var newTab = template.clone();
				newTab.attr("id",newId);
				cnt.append(newTab.clone());
				cnt.tabs({ active: parseInt(pos)-1 });
    		}
    	}
    	function renderRangeDetail(tabId,rangeId,score,begin,end,desc){
    		var cnt = $("#range-tab-"+tabId+" table tbody");
    		var tr = $("<tr></tr>");
    		tr.append( $("<td></td>").html(rangeId) );
    		tr.append( $("<td></td>").html(score) );
    		tr.append( $("<td></td>").html(begin) );
    		tr.append( $("<td></td>").html(end) );
    		tr.append( $("<td></td>").html(desc) );
			var action_td_edt = $('<td class="col-action"></td>').html('<img height="24" width="24" style="cursor: pointer" src="<c:url value="/resources/images/edited.png"/>" onclick="actEditRangeDetail(this)" />');
			tr.append(action_td_edt);
			var action_td_del = $('<td class="col-action"></td>').html('<img height="24" width="24" style="cursor: pointer" src="<c:url value="/resources/images/delete.png"/>"   onClick="actDelRangeDetail(this)" />');
    		tr.append( action_td_del);
    		cnt.append(tr);
    	}
    	function renderRangeDetailEdit(tabId,baselineId,score,begin,end,desc){
    		var objTr = getCurrentEditItem("range-tab-"+tabId,baselineId);
    		objTr.children("td:nth-child(1)").html(baselineId);
    		objTr.children("td:nth-child(2)").html(score) ;
    		objTr.children("td:nth-child(3)").html(begin) ;
    		objTr.children("td:nth-child(4)").html(end) ;
    		objTr.children("td:nth-child(5)").html(desc) ;
    	}
    	function actEditRangeDetail(current){
    		showCriteriaGroup($("#baselineRange .tabAction input").get(0),'actRangeDetail',1);
    		var cntDivId = $(current).closest("table").parent("div").attr("id");
    		var tabId = cntDivId.replace("range-tab-","");
    		tabId =parseInt(tabId);
    		var cntVal = $(current).closest("tr");
    		var detail = $("#actRangeDetail table tbody");
    		var baselineId = cntVal.children("td:nth-child(1)").html();
    		detail.children("tr:nth-child(1)").children("td:nth-child(2)").html( baselineId );
    		detail.children("tr:nth-child(2)").children("td:nth-child(2)").html( tabId );
    		detail.children("tr:nth-child(3)").children("td:nth-child(2)").children("select").val( cntVal.children("td:nth-child(2)").html() );
    		detail.children("tr:nth-child(4)").children("td:nth-child(2)").children("input").val( cntVal.children("td:nth-child(3)").html() );
    		detail.children("tr:nth-child(5)").children("td:nth-child(2)").children("input").val( cntVal.children("td:nth-child(4)").html());
    		detail.children("tr:nth-child(6)").children("td:nth-child(2)").children("input").val( cntVal.children("td:nth-child(5)").html());
    	}
    	function actDelRangeDetail(current){
    		var lineObj = $(current).closest("tr");
    		var baselineId = lineObj.children("td:nth-child(1)").html();
    		var baselineName = lineObj.children("td:nth-child(5)").html();
    		if(confirm('ยินยันการลบ "'+baselineName+'"')){
	    		$.ajax({
	    			dataType:'json',
	    			url: "<%=doDeleteBaseline%>" ,
	    			data: { 'type':'range','baselineId':baselineId } ,
	    			success: function(data){
	    			//	alert(JSON.stringify(data));
	    	    		if(typeof lineObj != "undefined"){
	    	    			var tbody = lineObj.closest("tbody");
	    	    			lineObj.remove();
	    					if(tbody.find("tr").size()==0){
	    						removeTabs('rangeBaselineTab',getActiveTab('rangeBaselineTab'));
	    					}
	    	    		}
	    			}
	    		});
    		}
    	}
    	function getCurrentEditItem(divTabId,baselineId){
    		//return tr jquery object
    		var cnt = $("#"+divTabId);
    		var juiObj;
    		cnt.children("table").children("tbody").children("tr").each(function(){
    			var tableBaselineId = $(this).children("td:nth-child(1)").html();
    			if(tableBaselineId==baselineId){
    				juiObj = $(this);
    			}
    		});
    		return juiObj;
    	}
    	//  spec baseline
    	function actAddSpecGroup(){
    		var tabname = $("#"+portletBoxName+" #specGroupItem option:selected").text();
    		var tabid = $("#"+portletBoxName+" #specGroupItem option:selected").val();
    		createSpecGroup(1,tabid,tabname);
    		hideCriteriaGroup('actSpecGroup');
    	}
    	function createSpecGroup(enableAlert,tabid,tabname){
    		var cnt = $("#specBaselineTab");
    		var pos = posOnTab("specBaselineTab",tabname);
    		if(pos>0){ //exist
    			if(enableAlert){ 	alert('ซ้ำ');  }
    		}else{
    			//var nextId = parseInt(cnt.find("ul li").length)+1;
    			cnt.tabs("destroy");
				var newId = "spec-tab-"+tabid;
				// add list
				var newA = $("<a></a>");
				newA.attr("href","#"+newId);
				newA.html(tabname);
				var newListItem = $("<li></li>").append(newA);
				cnt.children("ul").append(newListItem);
				//content only table head
				var tabContentTemplate = $("#"+portletBoxName+" #criteriaTabContentTemplate #baselineSpecTabContentTemplate");
	    		var newTab = tabContentTemplate.clone();
				newTab.attr("id",newId);
				newTab.children("ul").html(""); //clean ul
				newTab.children("table").children("tbody").html(""); //clean tbody
				cnt.append(newTab.clone());
				cnt.tabs({ active: parseInt(pos)-1 });
    		}
    	}
    	function specGroupEvent(){
    		$("#specBaselineTab").off( "tabsactivate");
    		$("#specBaselineTab").on( "tabsactivate", function( event, ui ) {
    			listSpecBaselineDetail(1); // 1 default
    		} );
    	}
		function actAddSpecCriteria(current){
			var score = $(current).prev("select").val();
			$('#actSpecCriteria').find("span.scoreNo").html(score);
			chooseSpecScore();
			$('#specBaselineTab').tabs( "disable" );
			showCriteriaGroup($('#baselineSpec').find("#actSpecCriteria").get(0),'actSpecCriteria',0);
		}
		function actSpecScoreDelCr(current){
			var lineObj = $(current).closest("tr");
			var baselineId = lineObj.children("td:nth-child(1)").html();
			var baselineName = lineObj.children("td:nth-child(2)").html();
			//del request
			if(confirm('ยินยันการลบ "'+baselineName+'"')){
	    		$.ajax({
	    			dataType:'json',
	    			url: "<%=doDeleteBaseline%>" ,
	    			data: { 'type':'spec','baselineId':baselineId } ,
	    			success: function(data){
	    			//	alert(JSON.stringify(data));
	    	    		if(typeof lineObj != "undefined" && data["content"]["total"]>0){
	    	    			lineObj.remove();
	    	    		}
	    			}
	    		});
    		}
		}
		function chooseSpecScore(){
			// get Stndard Items to component select
			var target = $("#"+portletBoxName+" #baselineSpec #specCriteriaList");
			var standardItems =  $("#"+portletBoxName+" #StandardCriteriaTab>div.tabsContent>table>tbody>tr");
			target.empty();
			standardItems.each(function(){
				var tr = $(this);
				var opt = $("<option></option>");
				opt.attr("value",tr.children("td:nth-child(2)").html());
				opt.html(tr.children("td:nth-child(3)").html());
				target.append(opt);
			});
		}
		function saveSpec(current){
			var criteriaId = $("#"+portletBoxName+" #baselineSpec #specCriteriaList").val();
			var criteriaDesc = $("#"+portletBoxName+" #baselineSpec #specCriteriaList option:selected").text();
			var groupId = getActiveTabId('specBaselineTab','spec-tab-');
			var kpiId  = $("#"+portletBoxName+" #kpiId").val();
			var score = $(current).parent().find('span.scoreNo').html();
			$.ajax({
    			dataType:'json',
    			url: "<%=doSaveSpecBaseline%>" ,
    			data: { 'type':'spec','kpiId':kpiId,'groupId':groupId,'score':score,'criteriaId':criteriaId } ,
    			success: function(data){
    				if(data["content"]["baselineId"]>0){
    					alert("บันทึกสำเร็จ");
    					var target = $("#"+portletBoxName+" #specBaselineTab #spec-tab-"+groupId).find("table>tbody");
    					renderSpecLine(target,data["content"]["baselineId"],criteriaDesc);
    				}else{
    					alert("บันทึกผิดพลาด");
    				}
    			}
    		});
		}
		function saveSpecCancel(current){
			$('#actSpecCriteria').hide();
			$('#specBaselineTab').tabs( "enable" );
		}
		function renderSpecLine(cnt,specId,desc){
			var tr = $("<tr></tr>");
			tr.append($("<td></td>").html(specId));
			tr.append($("<td></td>").html(desc));
			var btn = $('<img height="24" width="24" style="cursor: pointer" src="<c:url value="/resources/images/delete.png"/>" onClick="actSpecScoreDelCr(this)" />')
			//var btn = 	$('<input type="button" onclick="actSpecScoreDelCr(this)" value="ลบ"/>');
			tr.append($('<td class="col-action"></td>').append(btn));
			cnt.append(tr);
		}
		function specTabScoreList(){
			var sel = $("#"+portletBoxName+" #criteriaTabContentTemplate #baselineSpecTabContentTemplate>select.specScores");
			sel.empty();
			var score  = 0;
			if( $("#"+portletBoxName+" input[name='radioCriteriaScore']:checked").val() == "integer" ){
				score = $("#"+portletBoxName+" #criteriaScore").val();
			}else if( $("#"+portletBoxName+" input[name='radioCriteriaScore']:checked").val() == "pass" ){
				score = 2;
			}
			for(var i=1;i<=score;i++){
				var opt = $("<option></option>").html(i);
				opt.attr("value",i);
				sel.append(opt.clone());
			}
		}
		function listSpecBaselineGroup(){
			var kpiId  = $("#"+portletBoxName+" #kpiId").val();
			$.ajax({
    			dataType:'json',
    			url: "<%=listBaseline%>" ,
    			data: { 'type':'specGroup','kpiId':kpiId , 'score': "1"} ,
    			success: function(data){
    				if(data["header"]["size"]>0){
    					var dat = data["content"]["lists"];
    					for(var i=0;i<dat.length;i++){
    						createSpecGroup(0,dat[i]["groupId"],dat[i]["groupDesc"]);
    					//	var target = $("#"+portletBoxName+" #specBaselineTab #spec-tab-"+dat[i]["groupId"]).find("table>tbody");
    					//	renderSpecLine(target,dat[i]["baselineId"],dat[i]["desc"]);
    					}
    				}
    				specGroupEvent();
    			}
    		});
		}
		function listSpecBaselineDetail(score){
			var kpiId  = $("#"+portletBoxName+" #kpiId").val();
			var groupId = getActiveTabId('specBaselineTab','spec-tab-');
			var target = $("#"+portletBoxName+" #specBaselineTab #spec-tab-"+groupId).find("table>tbody");
			target.empty();
    		$.ajax({
    			dataType:'json',
    			url: "<%=listBaseline%>" ,
    			data: { 'type':'specDetail','kpiId':kpiId,'groupId':groupId,'score':score } ,
    			success: function(data){
    				//alert(JSON.stringify(data));
    				if(data["header"]["size"]>0){
    					var dat = data["content"]["lists"];
    					for(var i=0;i<dat.length;i++){
    						renderSpecLine(target,dat[i]["baselineId"],dat[i]["desc"]);
    					}
    				}
    			}
    		});
		}
		function specScoreChange(current){
			listSpecBaselineDetail($(current).val());
		}
		/*cds function*/
		function actCdsSearch(){
			var searchStr = $("#"+portletBoxName+" #btn-cds-search").prev("input").val();
		//	alert(searchStr);
			var pageNo = 1;
			cdsListRequest(searchStr,pageNo);
		}
		function cdsGotoPage(el){
			var pageNo = $(el).val();
			var searchStr = $("#"+portletBoxName+" #btn-cds-search").prev("input").val();
			fireRunPage(pageNo); //warning v1
			cdsListRequest(searchStr,pageNo);
		}
		function cdsGoNext(){
			var searchStr = $("#"+portletBoxName+" #btn-cds-search").prev("input").val();
			var pagingCnt = $("#"+portletBoxName+" #cds-pagging>ul");
			var pageNo = pagingCnt.children("li.currentpage").children("input").val();
			var lastPage = pagingCnt.children("li:last-child").children("input").val();
			//alert(pageNo+":"+lastPage);
			if( parseInt(pageNo)+1 <= lastPage){
				fireRunPage(pageNo); //warning v1
				cdsListRequest(searchStr,parseInt(pageNo)+1);
			}
		}
		function cdsGoPrev(){
			var searchStr = $("#"+portletBoxName+" #btn-cds-search").prev("input").val();
			var pageNo = $("#"+portletBoxName+" #cds-pagging>ul").children("li.currentpage").children("input").val();
			if( parseInt(pageNo)-1 > 0){
				fireRunPage(pageNo); //warning v1
				cdsListRequest(searchStr,parseInt(pageNo)-1);
			}
		}
		function fireRunPage(seq){ //currentpage
			var cnt = $("#"+portletBoxName+" #cds-pagging>ul");
			cnt.children("li.currentpage").removeClass("currentpage");
			cnt.find("li:nth-child("+seq+")").addClass("currentpage");
		}
		function cdsListRequest(searchStr,pageNo){
			$.ajax({
    			dataType:'json',
    			url: "<%=listCds%>" ,
    			data: {  'searchStr':searchStr,'pageNo':pageNo } ,
    			success: function(data){
    				//alert(JSON.stringify(data));
    				if(data["header"]["size"]>0){
    					var dat = data["content"]["lists"];
    					cdsPagging(data["header"]["pageTotal"],data["header"]["currentPage"]);
    					cdsListCreateList(dat);
    				}
    			}
    		});
		}
		function cdsListCreateList(dataAr){
			// 4 column
			var target = $("#cds-list>table>tbody");
			target.empty();
			for(var i=0;i<dataAr.length;i++){
				var tr = $("<tr></tr>");
				tr.append( $("<td></td>").html(dataAr[i]["cdsId"]) );
				tr.append( $("<td></td>").html(dataAr[i]["cdsName"]) );
				tr.append( $("<td></td>").html(dataAr[i]["cdsLevel"]) );
				var btnSum = $('<input type="button" onClick="formulaAppendCds(\'sum\',this)" value="ผลรวม"></input>');
				var btnMax = $('<input type="button" onClick="formulaAppendCds(\'max\',this)" value="เดือนล่าสุด"></input>');
				var tdAct = $("<td></td>");
				tdAct.append(btnSum);
				tdAct.append(btnMax);
				tr.append(tdAct);
				target.append(tr);
			}
		}
		function cdsPagging(noOfPage,currentNo){
			var cnt = $("#"+portletBoxName+" #cds-pagging>ul");
			// clear
			cnt.empty();
			// new
			for(var i = 1;i<=noOfPage;i++){
				var li = $("<li></li>");  
				var btn = $('<input type="button" class="btnPag" onclick="cdsGotoPage(this)"/>').val(i);
				if(i==currentNo){
					li.addClass("currentpage");
				}
				li.append(btn);
				cnt.append(li);
			}
		}
    </script>
    <style type="text/css">
    	table.tableKpiDetail,table.tableKpiCriteria{ width:100%;}
    	table.tableKpiDetail td,table.tableKpiCriteria td{	width:50%;	}
    	table.tableKpiCriteria td,table.tableKpiDetail td{
    		vertical-align:middle;
    		padding:1px 1px 2px 1px;
    	}
    	table.tableKpiDetail label,table.tableKpiCriteria label{
    		display:inline-block;
    		min-width:150px;
    	}
    	table.tableKpiCriteria2 td{ vertical-align:text-top;}
    	table.tableKpiCriteria2 td:nth-child(1){ width:150px }
    	div#StandardCriteriaTab table td:nth-child(1){ text-align:center; width:100px !important;}
    	div#StandardCriteriaTab table td:nth-child(4){ text-align:center; width:100px !important;}
    	table.table-std td.col-action , table.table-spec td.col-action , table.table-range td.col-action{ width:90px; text-align:center;}
    	.kpi-formula{
    		vertical-align:text-top;
    	}
    	div.formula-calc{
    		border:2px solid #bcbcbc;
    	}
    	.formula-calc{
    		padding:10px 10px 20px 10px;
    	}
    	.tabContainer{ margin-top:10px;
    	}
    	.tabsContent table>tbody>td:nth-child(1){
    		width:30px;
    	}
    	.actionCnt{
    		border:1px outset #cdcdcd;
    		padding:10px 10px 10px 10px;
    	}
    	#baselineRange th:nth-child(1),#baselineRange .tabsContent td:nth-child(1){
    		display:none;
    	}
    	li.currentpage>input{
    		color:blue;
    	}
    	#kpiFormFormula{
    		padding:20px 20px 20px 20px;
    	}
    	#kpiFormFormula>div{
    		padding:
    	}
    	#kpiFormFormula>div>label,#cds-search>label{
    		display:inline-block;
    		width:120px;
    	}
    	#kpiFormFormula textarea{ width:auto;
    	}
    	#cds-search{float:left;}
    	#cds-pagging{ float:right;}
    	#cds-pagging *{display:inline;}
    	#cds-pagging ul{ list-style:none;}
    	#cds-list{ clear:both; }
    	.actionMessage{ background-color:#CCCCFF; border:1px solid red;padding:15px 15px 15px 15px; display:inline; }
    	body *{color:black;}
    	#kpi-Detail select{ min-width:150px;max-width:300px;}
    	.aui select{
			color:black;
			width:auto;
		}
		input[type="radio"]{
   			margin: 0px!important;
   		}
   		.btnPag {
		    background-color: Transparent;
		    background-repeat:no-repeat;
		    border: none;
		    cursor:pointer;
		    overflow: hidden;
		    outline:none;
		}
    </style>
</head>
<body>
	<div id="kpiDetail" class="box">
		<input type="hidden" id="pageMessage" value="${actionMessageCode}"/>
		<c:if test="${not empty actionMessage}"> 
			<div id="kpi-msg" class="alert">${actionMessage}</div>
       	</c:if>
		<div id="kpi-Detail" class="boxPadding">
		   	<form:form  id="kpiFormDetail" modelAttribute="kpiForm" method="post"  name="kpiForm" action="${formActionNew}" enctype="multipart/form-data">
			<form:input type="text" id="kpiId" path="kpiModel.kpiId" style="display:none"/>
			<table class="tableKpiDetail">
				<tr><td colspan="2"><label>ชื่อตัวบ่งชี้</label><form:textarea id="detailKpiName" style="width:80%;" path="kpiModel.kpiName" rows="2" />
				</tr>
				<tr><td><label>ระดับตัวบ่งชี้</label><form:select id="detailLevel"  path="kpiModel.levelId" items="${levelList}" /></td>
					<td><label>องค์ประกอบ</label> <form:select id="detailStructure"  path="kpiModel.structureId" items="${structureList}" /></td>
				</tr>
				<tr><td><label>กลุ่มตัวบ่งชี้</label><form:select id="detailGroup"  path="kpiModel.groupId" items="${groupList}" /></td>
					<td><label>ชนิดตัวบ่งชี้</label><form:select id="detailType" path="kpiModel.typeId" items="${typeList}" /></td>
				</tr>
				<tr><td><label>ประเภทปฏิทิน</label><form:select id="detailCalendarType"  path="kpiModel.calendarTypeId" items="${calendarTypeList}" /></td>
					<td> <label>ช่วงเวลา </label> <form:select id="detailPeriod"  path="kpiModel.periodId" items="${periodList}" /> </td>
				</tr>
				<tr><td><label>หน่วยวัด </label><form:select id="detailUom" path="kpiModel.uomId" items="${uomList}" /></td>	
					<td><label>ภายใต้ตัวบ่งชี้</label> <form:select id="detailParent"  path="kpiModel.parentId" items="${ parentList}" />
					<img height="24" width="24" style="cursor: pointer" src="<c:url value="/resources/images/refresh-rect-bw.png"/>" onclick="getKpiParentList()" />
					<img height="24" width="24" style="display:none;" src="<c:url value="/resources/images/loading_blue_32.gif"/>" />
				</tr>
				<tr><td> <label>ค่าเปรียบเทียบ</label> <form:input type="text" class="numbersOnly" id="detailBenchmark" path="kpiModel.benchmark" /> (ตามหน่วยวัด)</td>
					<td> <label>ค่าคะแนนต่ำสุด</label> <form:input type="text" class="numbersOnly" id="detailMinScore" path="kpiModel.minScore" style="width:30px" /></td>
				</tr>				
			</table>
			<table class="tableKpiCriteria"> 
				<tr><td><label>ประเภทเกณฑ์ประเมิน</label> <form:select id="criteriaTypeId" onchange="criteriaTypeChange()" path="kpiModel.criteriaTypeId" items="${criteriaTypeList}" /></td>
				</tr>
				<tr><td><label>เกณฑ์คะแนน</label>
					<form:radiobutton id="radioCriteriaScore1" name="radioCriteriaScore" path="radioCriteriaScore" value="integer" />  เทียบคะแนนเต็มเท่ากับร้อยละ <form:input id="criteriaScore" path="kpiModel.criteriaScore" type="text" style="width:30px" class="numbersOnly"/>
						&nbsp<form:radiobutton id="radioCriteriaScore2" name="radioCriteriaScore" path="radioCriteriaScore" value="pass"/> ผ่าน/ไม่ผ่าน</td>
				</tr>
				<tr><td><label>วิธีการประเมิน</label><form:select id="criteriaMethod"  onchange="toggleBaselineLayout()"   path="kpiModel.criteriaMethodId" items="${criteriaMethodList}" /></td>
				</tr>
				<tr style="display:none;"> <td> <form:input type="text" path="kpiModel.createdBy" /> </td> </tr>
				<tr style="display:none;"> <td> <form:input type="text" path="kpiModel.createdDate" /> </td> </tr>
			</table>			
			<div style="text-align:center;"><input onClick="doSubmitDetail()" type="button" class="save" value="บันทึก" style="{margin-right:10px;}" /> <input type="button" class="cancel" onClick="doBack2List()" value="ยกเลิก" /> 
			</div>
			</form:form>
		</div> <!--  end detail box --> 
		<div id="accordionStateMessage" style="{text-align:center;color:red}"></div>
		<div id="accordion" class="eduqa-kpi-calc">
			<h3>เกณฑ์มาตราฐาน/เกณฑ์ประเมิน</h3>
			<div class="kpi-criteria">
				<form:form  id="kpiFormCriteria" modelAttribute="kpiForm" method="post"  name="kpiForm" action="${formActionNew}" enctype="multipart/form-data">
				 <table class="tableKpiCriteria2"> 
					<tr id="standardCriteriaCnt" >
						<td>เกณฑ์มาตราฐาน</td><td></td>
					</tr>	
					<tr id="resultCriteriaCnt">
						<td>เกณฑ์แปลงคะแนน</td><td></td>
					</tr>
				</table>
				</form:form>
				<div id="criteriaTemplate" class="template" style="display:none">
					<div id="standard" class="templateItem">
						<div class="tabAction">
							<input type="button" class="btn_clearType" onClick="actStdCrAdd(this)" value="เพิ่มเกณฑ์"/>
							<div id="actCrDetail" class="actionCnt">
								<table>
									<tr><td>ข้อ</td><td></td></tr>
									<tr><td>เกณฑ์มาตราฐาน</td><td><textarea style="width:90%"  rows="3"></textarea></td></tr>
									<tr><td>กลุ่ม </td><td><select id="crGroup" onchange="crGroupChange(this,'crGroupItem')">
														<c:if test="${not empty criteriaGroupList}"> 
       														<c:forEach items="${criteriaGroupList}" var="cgroup" varStatus="loop">
       															<option value="${cgroup.key}">${cgroup.value}</option>
															</c:forEach>
														</c:if></select>
														<select id="crGroupItem">
														<c:if test="${not empty criteriaGroupDetailList}"> 
       														<c:forEach items="${criteriaGroupDetailList}" var="detail" varStatus="loop">
       															<option value="${detail.key}">${detail.value}</option>
															</c:forEach>
														</c:if></select></td>
									</tr>
									<tr><td>ข้อมูลพื้นฐาน </td><td><input type="text"/></td></tr>
									<tr style="display:none;"><td>รหัส</td><td></td></tr>
									<tr><td> </td><td><input type="button" class="btnAdd save" onClick="addStdCr()" value="เพิ่ม"/>
										<input type="button" class="btnEdit save" onClick="editStdCr()" value="แก้ไข" />
										<input type="button" class="cancel" onClick="hideCriteriaGroup('actCrDetail')" value="ยกเลิก"/></td></tr>
								</table>
							</div>
						</div>
						<div id="StandardCriteriaTab" class="tabContainer">
							<ul class="tabsList"></ul>
						</div>
					</div>
					<div id="baseline"  class="templateItem">
						<div class="tabAction">
							<input type="button" class="btn_clearType" onClick="showCriteriaGroup(this,'actQuanGroup',0)" value="เพิ่มกลุ่ม.."/>
							<div id="actQuanGroup" class="actionCnt">
								<table>
									<tr><td>เพิ่มกลุ่ม </td><td><select id="rcGroup" onchange="crGroupChange(this,'rcGroupItem')"><c:if test="${not empty criteriaGroupList}"> 
       												<c:forEach items="${criteriaGroupList}" var="cgroup" varStatus="loop">
       														<option value="${cgroup.key}">${cgroup.value}</option>
													</c:forEach>
													</c:if></select>
													<select id="rcGroupItem">
													<c:if test="${not empty criteriaGroupDetailList}"> 
       													<c:forEach items="${criteriaGroupDetailList}" var="detail" varStatus="loop">
       														<option value="${detail.key}">${detail.value}</option>
														</c:forEach>
													</c:if></select></td>
									</tr>
									<tr><td></td><td><input type="button" class="save" onClick="addQuanGroup(this)" value="เพิ่ม"/>
										<input type="button" class="cancel" onClick="hideCriteriaGroup('actQuanGroup')" value="ยกเลิก"/></td>
									</tr>
								</table>
							</div>
						</div>
						<div id="quanBaselineTab" class="tabContainer">
							<ul class="tabsList"></ul>
						</div>
					</div>
					<div id="baselineRange" class="templateItem">
						<div class="tabAction">
							<input type="button" class="btn_clearType" onClick="showCriteriaGroup(this,'actRangeGroup',0)" value="แบ่งกลุ่มเพิ่ม"/>
							<input type="button" class="btn_clearType" onClick="showCriteriaGroup(this,'actRangeDetail',0)" value="เพิ่มคะแนน"/>
							<div id="actRangeGroup" class="actionCnt">
								<table>
									<tr><td>เพิ่มกลุ่ม </td><td><select id="rangeGroup" onchange="crGroupChange(this,'rangeGroupItem')"><c:if test="${not empty criteriaGroupList}"> 
       														<c:forEach items="${criteriaGroupList}" var="cgroup" varStatus="loop">
       															<option value="${cgroup.key}">${cgroup.value}</option>
															</c:forEach>
														</c:if></select>
														<select id="rangeGroupItem">
														<c:if test="${not empty criteriaGroupDetailList}"> 
       														<c:forEach items="${criteriaGroupDetailList}" var="detail" varStatus="loop">
       															<option value="${detail.key}">${detail.value}</option>
															</c:forEach>
														</c:if></select></td>
									</tr>
									<tr><td> </td><td><input type="button" class="btnAdd save" value="เพิ่ม" onClick="actAddRangeGroup()"/>
										<input type="button" class="cancel" onClick="hideCriteriaGroup('actRangeGroup')" value="ยกเลิก"/></td>
									</tr>
								</table>
							</div>
							<div id="actRangeDetail" class="actionCnt">
								<table>
									<tbody>
									<tr style="display:none"><td>รหัส</td><td></td></tr>
									<tr style="display:none"><td>รหัสกลุ่ม</td><td></td></tr>
									<tr><td>คะแนน</td><td><select></select></td></tr>
									<tr><td>เริ่มต้น </td><td><input type="text" class="numbersOnly" /></td></tr>
									<tr><td>สิ้นสุด </td><td><input type="text" class="numbersOnly" /></td></tr>
									<tr><td>คำอธิบาย </td><td><input type="text" /></td></tr>
									<tr><td> </td><td><input type="button" class="btnAdd save" value="เพิ่ม" onClick="addRangeScore()"/>
										<input type="button" value="แก้ไข" class="btnEdit save" onClick="editRangeScore()"/>
										<input type="button" class="cancel" onClick="hideCriteriaGroup('actRangeDetail')" value="ยกเลิก"/></td>
									</tr>
									</tbody>
								</table>
							</div>
						</div>
						<div id="rangeBaselineTab" class="tabContainer">
							<ul class="tabsList"></ul>
						</div>
					</div> <!--  end rangeBaseLine -->
					<div id="baselineSpec" class="templateItem">
						<div class="tabAction">
							<input type="button" class="btn_clearType" onClick="showCriteriaGroup(this,'actSpecGroup',0)" value="แบ่งกลุ่มเพิ่ม"/>
							<div id="actSpecGroup" class="actionCnt">
								<table>
									<tr><td>เพิ่มกลุ่ม </td><td><select id="specGroup" onchange="crGroupChange(this,'specGroupItem')"><c:if test="${not empty criteriaGroupList}"> 
       														<c:forEach items="${criteriaGroupList}" var="cgroup" varStatus="loop">
       															<option value="${cgroup.key}">${cgroup.value}</option>
															</c:forEach>
														</c:if></select>
														<select id="specGroupItem">
														<c:if test="${not empty criteriaGroupDetailList}"> 
       														<c:forEach items="${criteriaGroupDetailList}" var="detail" varStatus="loop">
       															<option value="${detail.key}">${detail.value}</option>
															</c:forEach>
														</c:if></select></td>
									</tr>
									<tr><td> </td><td><input type="button" class="btnAdd save" value="เพิ่ม" onClick="actAddSpecGroup()"/>
										<input type="button" class="cancel" onClick="hideCriteriaGroup('actSpecGroup')" value="ยกเลิก"/></td>
									</tr>
								</table>
							</div>
							<div id="actSpecCriteria" class="actionCnt"> 
								<span>คะแนน</span>&nbsp&nbsp<span class="scoreNo"></span>&nbsp&nbsp
								<select id="specCriteriaList"></select>
								<input type="button" class="save" onclick="saveSpec(this)" value="ตกลง"/>
								<input type="button" class="cancel" onclick="saveSpecCancel(this)" value="ยกเลิก"/>
							</div>
						</div>
						<div id="specBaselineTab" class="tabContainer">
							<ul class="tabsList"></ul>
						</div>
					</div>
				</div>
				<div id="criteriaTabContentTemplate" class="template" style="display:none">
					<div id="stdCrTabContentTemplate" class="tabsContent">
						<table class="darkTable table-std">	
						 	<thead> 
						  	<tr><th>ข้อ</th><th style="display:none">รหัส</th><th>เกณฑ์มาตราฐาน</th><th>ข้อมูลพื้นฐาน</th><th>แก้ไข</th><th>ลบ</th></tr>
						  	</thead>
						  	<tbody> 
						  	</tbody>
						</table>
					</div>
					<div id="baselineQuanTabContentTemplate" class="tabsContent">
						<table>
							<tr style="display:none"><td>รหัส</td><td></td></tr>
						  	<tr><td>ค่าผลต่าง</td><td><input type="text" class="smallText numbersOnly"/></td></tr>
						  	<tr><td>คะแนนเต็มคิดเป็นร้อยละ</td><td><input type="text" class="smallText numbersOnly"/></td></tr> 
						  	<tr><td></td><td><input type="button" class="save" onclick="saveQuan(this)" value="บันทึก" style="margin=right:10px"/><input type="button" class="cancel" onclick="deleteQuan(this)" value="ลบ"/></td></tr>
						</table>
					</div>
					<div id="baselineRangeTabContentTemplate" class="tabsContent">
						<table class="darkTable table-range">	
							<thead> 
							<tr><th>รหัส</th><th>คะแนน</th><th>เริ่มต้น</th><th>สิ้นสุด</th><th>คำอธิบาย</th><th>แก้ไข</th><th>ลบ</th></tr>
							</thead>
							<tbody> 
							</tbody>
						</table>
					</div>
					<div id="baselineSpecTabContentTemplate" class="tabsContent">
						<span>คะแนน</span>
						<select class="specScores" style="margin-left:10px;margin-right:10px" onchange="specScoreChange(this)"></select>
						<a href="javascript: void(0)" onclick="actAddSpecCriteria(this)">เพิ่มเกณฑ์</a>
						<br/>
						<table class="darkTable  table-spec">
							<thead>
								<tr>
									<th>รหัส</th><th>เกณฑ์มาตรฐาน</th><th>ลบ</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div> <!-- end tabContentTemplate -->
			</div>
			<h3>สูตรการคำนวน</h3>
			<div class="kpi-formula">
				<form:form  id="kpiFormFormula" modelAttribute="kpiForm" method="post"  name="kpiForm" action="${formActionNew}" enctype="multipart/form-data">
				<div class="formula-desc">
					<label>คำอธิบายสูตร</label><form:textarea id="formulaDesc" name="Text1" path="kpiModel.formulaDesc" cols="70" rows="3" ></form:textarea>
				</div>
				<div class="formula-cds">
					<div id="cds-search"><label>ค้นหาข้อมูลพื้นฐาน</label><input type="text"/><img id="btn-cds-search" style="cursor: pointer" width="24" height="24" onClick="actCdsSearch()" src="<c:url value="/resources/images/search.png"/>"/>
					</div>
					<div id="cds-pagging" style="float:right">
						<span><input type="button" class="btnPag" onclick="cdsGoPrev()" value="<"/></span>
						<ul>
						</ul>
						<span><input type="button" class="btnPag" onclick="cdsGoNext()" value=">"/></span>
					</div>
					<div id="cds-list" style="clear:both">
						<table id="kpi-list-cds" class="cdsTable detail darkTable">
							<thead>
								<tr><th width="5%">รหัส</th><th width="60%">ข้อมูลพื้นฐาน</th><th width="10%">ระดับ</th><th width="15%">คำสั่ง</th></tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
				<div class="formula-calc">
					<label>สูตรการคำนวน</label><form:textarea id="formula" name="formula" path="kpiModel.formula" cols="70" rows="4" ></form:textarea>
				</div>
				<div style="text-align:center;"><input onclick="doSubmitFormula()" type="button" value="บันทึก" style="{margin-right:10px;}" class="save" />
					<!-- <input type="button" onclick="doCloseAccordFormula" value="ปิด" /> -->
				</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>