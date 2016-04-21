<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="javax.portlet.PortletURL"%>

<portlet:actionURL var="formActionSubmitFilter">
	<portlet:param name="action" value="doSubmitFilter" />
</portlet:actionURL>
<portlet:actionURL var="formActionTarget">
	<portlet:param name="action" value="doAssignTarget" />
</portlet:actionURL>
<portlet:resourceURL var="doSearchOrg" id="doSearchOrg"></portlet:resourceURL>
<portlet:resourceURL var="doInsertResult" id="doInsertResult"></portlet:resourceURL>
<portlet:resourceURL var="doReloadResult" id="doReloadResult"></portlet:resourceURL>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" type="text/css" />
<script src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script>
<script
	src="<c:url value="/resources/js/smoothness/jquery-ui-1.9.1.custom.min.js"/>"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/css/smoothness/jquery-ui-1.9.1.custom.min.css"/>" />
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<script
	src="<c:url value="/resources/bootstrap/js/bootstrap-typeahead.min.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/override-portlet-aui.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/override-jqueryui.css"/>" />
<link rel="stylesheet"href="<c:url value="/resources/css/common-element.css"/>"type="text/css" />
<link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" type="text/css" />
<script type="text/javascript"> 
    	$( document ).ready(function() {
    		if($('#pageMessage').html()==""){
    			$('#pageMessage').hide();
    		}
    		$("#assignKpi_accordion").accordion({
    			 heightStyle: "content",
    			 collapsible: true
    		});
    		// initial active select
    		toggleEnableSelection();

    	});
    	function toggleEnableSelection(){
    		var uni  = $("#filterUni");
    		var fac  = $("#filterFac");
    		var cou = $("#filterCou");
    		var lv = $('#assignKpi_filter select#level').val();
    		if(lv==1){ uni.prop("disabled",false); fac.prop("disabled",true); cou.prop("disabled",true); }
    		else if(lv==2){ uni.prop("disabled",false); fac.prop("disabled",false); cou.prop("disabled",true); }
    		else if(lv==3){ uni.prop("disabled",false); fac.prop("disabled",false); cou.prop("disabled",false); }
    	}

    	/* bind element event*/
    	function actSearchOrg(el,level){
    	
    		//var types = ["univerisity","faculty","course"];
    		//var orgType = types[parseInt(level)-1 ];
    		var uni  = $("#filterUni");
    		var fac  = $("#filterFac");
    		var cou = $("#filterCou");
    		toggleEnableSelection();
    		$.ajax({
       	 		dataType: "json",
       	 		url:"<%=doSearchOrg%>",
       	 		data: { "level":level , "university":uni.val(),"faculty":fac.val(),"course":cou.val() },
       	 		success:function(data){
       	 			//alert(JSON.stringify(data));
	       	 		if(level==0){
	       	 			createOption('filterUni',data["content"]["lists"]);
	       	 			clearOption("filterFac");   
	       	 			clearOption("filterCou");	
	       	 		}else if(level==1){
	       	 			createOption('filterFac',data["content"]["lists"]);
	       	 			clearOption("filterCou");
	       	 		}else if(level==2){
	       	 			createOption('filterCou',data["content"]["lists"]);
	       	 		}
       	 		} 
       	 	});
    	}
   	 	function actTarget(el){
   	 		var kpiId = parseInt($(el).parent('td').parent('tr').children('tbody tr td:nth-child(1)').html());
   	 		$('#kpiListForm #kpiId').val(kpiId);
	   	 	$('#kpiListForm').attr("action","<%=formActionTarget%>");
			$('#kpiListForm').submit();
   	 	}
   	 	function submitFilter(){
	   	 	$('#hierarchyAuthorityForm').attr("action","<%=formActionSubmitFilter%>");
			$('#hierarchyAuthorityForm').submit(); 	 			
   	 	}
   	 	function clearOption(elName){
   	 		var defaultOpt = $("<option></option>").html("");
   	 		var target = $("#"+elName);
   	 		target.empty();
   	 		target.append(defaultOpt);
   	 	}
   	 	function createOption(targetId,lists){
   	 		var target = $("select#"+targetId);
   	 		target.empty();
   	 		var opt = $("<option></option>").html("");
   	 		target.append(opt);
   	 		for(var i=0;i<lists.length;i++){
   	 			var opt = $("<option></option>");
   	 			opt.attr("value",lists[i]["orgCode"]);
   	 			opt.html(lists[i]["orgDesc"]);
   	 			target.append(opt);
   	 		}
   	 	}
   	 	function insertResult(){
   	 		var arId = [];
   	 		$("#assignKpi_accordion table.tableGridLv>tbody>tr").each(function(){
   	 			if($(this).children("td:nth-child(2)").children('input[type="checkbox"]').is(':checked')){
   	   	 			arId.push($(this).children("td:nth-child(1)").html());	
   	 			}
   	 		});
   	 		var orgId = $("#kpiListForm #orgId").val();
	   	 	$.ajax({
	   	 		dataType: "json",
	   	 		url:"<%=doInsertResult%>",
	   	 		data: { "orgId":orgId ,"kpis":arId.join('-') },
	   	 		success:function(data){
	   	 			//alert(JSON.stringify(data));
	   	 			if(data['header']['success']>0){
		   	 			$('#pageMessage').html('บันทึกสำเร็จ');
		   	 			$('#pageMessage').removeClass();
		   	 			$('#pageMessage').addClass("alert");
		   	 			toggleSetTargetBtn();
	   	 			}else{
		   	 			$('#pageMessage').html('บันทึกผิดพลาด '+data['header']['status']);
		   	 			$('#pageMessage').removeClass();
		   	 			$('#pageMessage').addClass("alert alert-danger");
	   	 			}
	   	 			$('div#pageMessage').focus();
	   	 			$('div#pageMessage').css( "display", "block" ).fadeOut( 30000 );
	   	 		}
	   	 	});
   	 	}
   	 	function reloadResult(){
   	 		var orgId = $("#kpiListForm #orgId").val();
	   	 	$.ajax({
	   	 		dataType: "json",
	   	 		url:"<%=doReloadResult%>",
					data : {
						"orgId" : orgId
					},
					success : function(data) {
						if (data['header']['success'] > 0) {
							$(
									'#assignKpi_accordion table tbody tr input[type="checkbox"]')
									.prop('checked', false);
							var kpiIds = data['content']['lists'].split('-');
							for (var i = 0; i < kpiIds.length; i++) {
								$(
										'#assignKpi_accordion table tbody tr#r'
												+ kpiIds[i]).children(
										'td:nth-child(2)').children(
										'input[type="checkbox"]').prop(
										'checked', true);
							}
							$('#pageMessage').html('โหลดค่าเริ่มต้นสำเร็จ');
							$('#pageMessage').removeClass()
							$('#pageMessage').addClass("alert");
							toggleSetTargetBtn();
						} else {
							$('#pageMessage').html('โหลดค่าเริ่มต้นผิดพลาด');
							$('#pageMessage').removeClass();
							$('#pageMessage').addClass("alert alert-danger");
						}
						$('div#pageMessage').focus();
						$('div#pageMessage').css("display", "block").fadeOut( 30000 );

					}
				});
	}
	function toggleSetTargetBtn() {
		$('#assignKpi_accordion table tbody tr').each(
				function() {
					if ($(this).children('td:nth-child(2)').children(
							'input[type="checkbox"]').is(':checked')) {
						$(this).find('a').show();
					} else {
						$(this).find('a').hide();
					}
				});
	}
	
</script>
<style type="text/css">

/* (1)+5 td*/
#assignKpi_accordion table.tableGridLv td:nth-child(1) {
	display: none
}

#assignKpi_accordion table.tableGridLv td:nth-child(2) {
	width: 10%;
}

#assignKpi_accordion table.tableGridLv td:nth-child(3) {
	width: 10%
}

#assignKpi_accordion table.tableGridLv td:nth-child(4) {
	width: 40%;
}

#assignKpi_accordion table.tableGridLv td:nth-child(5) {
	width: 16%;
}

#assignKpi_accordion table.tableGridLv td:nth-child(6) {
	width: 13%;
}

#assignKpi_accordion table.tableGridLv td:nth-child(6) {
	width: 10%;
}   
select.filterOrg{  min-width:220px !important;}
    .center {text-align: center;}
    </style>
</head>
<body>
	<div id="assignKpiList" class="box bg">
		<div id="pageMessage" class="">${pageMessage}</div>
		<div id="assignKpi_filter" class="boxHeader">
			<form:form id="hierarchyAuthorityForm"
				modelAttribute="hierarchyAuthorityForm" method="post"
				name="hierarchyAuthorityForm" action="${formActionSubmitFilter}"
				enctype="multipart/form-data">
				<span>ระดับตัวบ่งชี้: </span>
				<form:select path="level" class="smallText wid"
					onchange="actSearchOrg(this,0)">
					<form:options items="${levelList}" />
				</form:select>
				<br/>
				<span>สถาบัน/มหาวิทยาลัย: </span>
				<form:select class="wid filterOrg" id="filterUni" path="university"
					onchange="actSearchOrg(this,1)">
					<!-- <form:option value="" label="" /> -->
					<form:options items="${uniList}" />
				</form:select>
				<span>คณะ: </span>
				<form:select class="wid filterOrg" id="filterFac" path="faculty"
					onchange="actSearchOrg(this,2)">
					<!-- <form:option value="" label="" /> -->
					<!-- <form:options items="${facList}" /> -->
				</form:select>
				<span>หลักสูตร: </span>
				<form:select class="wid filterOrg" id="filterCou" path="course">
					<!-- <form:option value="" label="" /> -->
					<!-- <form:options items="${corsList}" /> -->
				</form:select>
				<input type="button" value="เรียกดู" onclick="submitFilter()" class="btn btn-primary" style="margin-bottom: 10px;" />
			</form:form>
		</div>
		
		<form:form id="kpiListForm" modelAttribute="kpiListForm" method="post"
			name="kpiListForm" action="${formActionNew}"
			enctype="multipart/form-data">
			<form:input type="hidden" id="pageNo" path="pageNo" />
			<form:input type="hidden" id="keySearch" path="keySearch" />
			<form:input type="hidden" id="kpiId" path="kpiId" />
			<form:input type="hidden" id="orgId" path="orgId" />
		</form:form>

		<div id="assignKpi_accordion" class="">
			<c:if test="${not empty accordions}">
				<c:forEach items="${accordions}" var="accordion" varStatus="acLoop">
					<h3>${accordion.structureName}</h3>
					<div class="table-responsive">
						<table class="tableGridLv hoverTable">
							<thead>
								<tr>
									<th style="display: none;">รหัสตัวบ่งชี้</th>
									<th style="text-align: center">เลือกตัวบ่งชี้</th>
									<th>กลุ่มตัวบ่งชี้</td>
									<th>ชื่อตัวบ่งชี้</td>
									<th>ประเภทปฏิทิน</td>
									<th class="center">ช่วงเวลา</th>
									<th class="center">หน่วยวัด</th>
									<th class="center">เป้าหมาย</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${accordion.resultKpis}" var="kpi"
									varStatus="loop">
									<tr id="r${kpi.kpiId}">
										<td style="display: none;">${kpi.kpiId}</td>
										<td style="text-align: center"><c:choose>
												<c:when test="${kpi.resultId>0}">
													<input type="checkbox" name="isUsed" value="1" checked>
												</c:when>
												<c:otherwise>
													<input type="checkbox" name="isUsed" value="1">
												</c:otherwise>
											</c:choose></td>
										<td>${kpi.kpiGroupShortName}</td>
										<td>${kpi.kpiName}</td>
										<td>${kpi.calendarTypeName}</td>
										<td class="center">${kpi.periodName}</td>
										<td class="center">${kpi.kpiUomName}</td>
										<td class="center"><c:choose>
												<c:when test="${kpi.resultId>0}">												
													<c:choose>
														<c:when test="${not empty kpi.targetValue && kpi.targetValue > 0}">
															<a href="#" class="icon" onClick="actTarget(this)">
																<img src="<c:url value="/resources/images/edited-assign.png"/>" width="25" height="25">
															</a>
														</c:when>
														<c:otherwise>
															<a href="#" class="icon" onClick="actTarget(this)">
																<img src="<c:url value="/resources/images/edited.png"/>" width="25" height="25">
															</a>
														</c:otherwise>
													</c:choose>

												</c:when>
												<c:otherwise>
													<a href="#" class="icon" onClick="actTarget(this)"
														style="display: none">
														<img src="<c:url value="/resources/images/edited.png"/>" width="22" height="22">
													</a>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- end 1 accordion -->

				</c:forEach>
			</c:if>
		</div>
		<div style="text-align:center;padding:25px 10px 25px 10px;">
		<input type="button" class="btn btn-success" onclick="insertResult()" value="บันทึก" style="margin-right:10px"/>
		<input type="button" class="btn btn-inverse" onclick="reloadResult()" value="กลับสู่ค่าเริ่มต้น">  
<!-- 		<div class="span12">  -->
<!-- 			<div style="text-align: center; padding-top: 2%">  -->
<!-- 				<input type="button" class="btn btn-success" onclick="insertResult()" value="บันทึก" style="margin-right: 10px">  -->
<!-- 				<input type="button" class="btn btn-inverse" onclick="reloadResult()" value="กลับสู่ค่าเริ่มต้น">  -->
<!-- 			</div>  -->
<!-- 		</div> -->
		</div>
	</div>
</body>
</html>