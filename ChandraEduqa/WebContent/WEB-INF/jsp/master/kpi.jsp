<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %> 
<%@ page import="javax.portlet.PortletURL"%>

<portlet:actionURL var="formActionNew">
	<portlet:param name="action" value="doNew"/>
</portlet:actionURL>
<portlet:actionURL var="formActionEdit">
	<portlet:param name="action" value="doEdit"/>
</portlet:actionURL>
<portlet:actionURL var="formActionDelete">
	<portlet:param name="action" value="doDelete"/>
</portlet:actionURL> 
<portlet:actionURL var="formActionFilter">
	<portlet:param name="action" value="doFilter"/>
</portlet:actionURL> 
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <!-- Jquery -->
    <script src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script> 
    <script  src="<c:url value="/resources/js/smoothness/jquery-ui-1.9.1.custom.min.js"/>"></script>
    <script src="<c:url value="/resources/js/confirm-master/jquery.confirm.min.js"/>"></script> 
    <link rel="stylesheet" href="<c:url value="/resources/css/smoothness/jquery-ui-1.9.1.custom.min.css"/>"/>
    
    <!-- Bootstrap core CSS --> 
	<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script> 
	<script  src="<c:url value="/resources/bootstrap/js/bootstrap-typeahead.min.js"/>"></script>   
    
    <!-- General Css -->
    <link rel="stylesheet" href="<c:url value="/resources/css/common-element.css"/>" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/override-portlet-aui.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/override-jqueryui.css"/>"/>
    
    <script type="text/javascript"> 
   	  	var dialog,dialog2;
    	$( document ).ready(function() {
    		$("#kpi_accordion").accordion({ autoHeight: false });
    		togglePageMessage();
   	  	});
    	/* bind element event*/
    	function togglePageMessage(){
    		if($("#pageMessage").html()!=""){
   	 			$('#pageMessage').css( "display", "block" ).fadeOut( 10000 );
    			$('#pageMessage').focus();
    		}else{
    			$("#pageMessage").hide();
    		}
    	}
    	function actFilter(){
        	$('#kpiListForm').attr("action","<%=formActionFilter%>");
    		$('#kpiListForm').submit();
    	}
   	 	function actAdd(el){
	   	 	$('#kpiListForm').attr("action","<%=formActionNew%>");
			$('#kpiListForm').submit();
   	 	}
   	 	function actEdit(el){
   	 		var dataId = parseInt($(el).parent('td').parent('tr').children('tbody tr td:nth-child(1)').html());
		//	alert(dataId);
   	 		$('#kpiListForm #kpiId').val(dataId);
	   	 	$('#kpiListForm').attr("action","<%=formActionEdit%>");
			$('#kpiListForm').submit();
   	 		
   	 	}
   	 	function actDelete(el){
   	 		var dataId = $(el).parent('td').parent('tr').children('td:nth-child(1)').html();
	 		var dataName = $(el).parent('td').parent('tr').children('td:nth-child(3)').html();
	   	 	$.confirm({
		   	     text: "ยืนยันการลบข้อมูลตัวบ่งชี้ \"".concat(dataName, "\""),
		   	     title: "ลบข้อมูลตัวบ่งชี้",
		   	     confirm: function(button) {		 
		   	    	 deleteKpi(dataId);
		   	     },
		   	     cancel: function(button) {
		   	         // nothing to do
		   	     },
		   	     confirmButton: "ตกลง",
		   	     cancelButton: "ยกเลืก",
		   	     post: true,
		   	     confirmButtonClass: "btn-primary",
		   	     cancelButtonClass: "btn-danger",
		   	     dialogClass: "modal-dialog modal-lg" // Bootstrap classes for large modal
		   	 });
   	 	}
   	 	function deleteKpi(kpiId){
   	 		$('#kpiListForm').attr("action","<%=formActionDelete%>");
	 		$('#kpiListForm '+' #kpiId').val(kpiId);
	 		$('#kpiListForm').submit();
   	 	}
   	</script>  
   	<style type="text/css">
   		/* (1)+5 td*/
   		#kpiList table.datagrid td:nth-child(1),#kpiList table.datagrid th:nth-child(1){
   			display:none
   		}
   		#kpiList table.datagrid td:nth-child(2),#kpiList table.datagrid th:nth-child(2){
   			width:12%;
   		}
   		#kpiList table.datagrid td:nth-child(3),#kpiList table.datagrid th:nth-child(3){
   			width:36%
   		}
   		#kpiList table.datagrid td:nth-child(4),#kpiList table.datagrid th:nth-child(4){
   			width:24%;
   		}
   		#kpiList table.tablegrid td:nth-child(5),#kpiList table.datagrid th:nth-child(5){
   			width:12%;
   		}
   		#kpiList table.tablegrid td:nth-child(6),#kpiList table.datagrid th:nth-child(6){ width:6%; text-align: center;}
   		#kpiList table.tablegrid td:nth-child(7),#kpiList table.datagrid th:nth-child(7){ width:6%; text-align: center;}
   		.aui body{
			font-family: "Liberation Sans"!important;
		}		
		div#kpiList {
			background: linear-gradient(#FFFFFF, rgba(222, 222, 222, .5), rgba(233, 233, 233, .8));
			border: thin solid #CDCDCD;
		}
   	</style>
  </head>
  <body>
       	<div id="kpiList" class="box">
       		<div id="pageMessage" class="alert alert-danger">${pageMessage}</div>
        	<div class="boxHeader">
       		<form:form  id="kpiListForm" modelAttribute="kpiListForm" method="post"  name="kpiListForm" action="${formActionNew}" enctype="multipart/form-data">
       			<form:input type="hidden" id="pageNo" path="pageNo" />
				<form:input type="hidden" id="kpiId" path="kpiId" />
       			<span>ระดับตัวบ่งชี้</span>
       			<form:select path="level" onchange="actFilter()" items="${levels}"/> 
       			<span>ค้นหาตัวบ่งชี้</span>
       			<form:input type="text" id="keySearch" placeholder="ค้นหาจากชื่อ" path="keySearch"/>
       			<img height="20" width="20" onClick="actFilter()"  src="<c:url value="/resources/images/search.png"/>">
       			<img height="18" width="18" onClick="actAdd(this)" src="<c:url value="/resources/images/add.png"/>">
       		</form:form>
       		</div>
       		<div id="kpi_accordion" class="">
       		<c:if test="${not empty accordions}"> 
	       	<c:forEach items="${accordions}" var="accordion" varStatus="acLoop"> 
	       		<h3>${accordion.structureName}</h3>
	       		<div>
		       		<table  class="datagrid tableGridLv hoverTable">
		       			<thead>
		       				<tr>
		       					<th>รหัสตัวบ่งชี้</th>
			       				<th>กลุ่มตัวบ่งชี้</th>
			       				<th>ชื่อตัวบ่งชี้</th>
			       				<th>ประเภทเกณฑ์การประเมิน</th>
			       				<th>ชนิดตัวบ่งชี้</th>
			       				<th>แก้ไข</th>
			       				<th>ลบ</th>
		       				</tr>
		       			</thead>
		       			<tbody>
		       				<c:forEach items="${accordion.kpis}" var="kpi" varStatus="loop"> 
		                	<tr> 
		                		<td>${kpi.kpiId}</td>
		                		<td>${kpi.groupName}</td>  
			               	 	<td>${kpi.kpiName}</td>  
			               	 	<td>${kpi.criteriaTypeName}</td> 
			               	 	<td>${kpi.typeName}</td>  
			                	<td style="text-align: center;">
			                		<img height="24" width="24"  onClick="actEdit(this)"  src="<c:url value="/resources/images/edited.png"/>">
		       					</td>	
		       					<td style="text-align: center;"><img height="24" width="24" onClick="actDelete(this)" src="<c:url value="/resources/images/delete.png"/>">
		       					</td>
			 				</tr>
			 				</c:forEach>
	       				</tbody>
	       			</table>
	       		</div> <!-- end 1 accordion -->
       		
	              		</c:forEach>      		
              		</c:if>
        	</div>
       	</div>
  </body>
</html>	
   