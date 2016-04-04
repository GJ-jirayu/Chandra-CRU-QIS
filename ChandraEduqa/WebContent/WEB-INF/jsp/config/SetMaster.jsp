<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %> 
<%@ page import="javax.portlet.PortletURL" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.sql.*" %>

<portlet:actionURL var="formActionSave">
	<portlet:param name="action" value="doSave"/>
</portlet:actionURL> 
<portlet:resourceURL var="getPlan" id="getPlan" ></portlet:resourceURL>
<portlet:resourceURL var="requestSaveSysYear" id="requestSaveSysYear" ></portlet:resourceURL>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap core CSS --> 
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap-responsive.min.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/common-element.css"/>" type="text/css"/>
    <script src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script> 
    <script  src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.min.css"/>"/>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
	<script  src="<c:url value="/resources/bootstrap/js/bootstrap-typeahead.min.js"/>"></script>
	<script src="<c:url value="/resources/js/confirm-master/jquery.confirm.min.js"/>"></script>
	
    <script type="text/javascript"> 
    	$( document ).ready(function() {
    		if($("#messageMsg").val()){
    			if($("#messageMsg").val()==""){
    				$("#msgAlert").removeClass().addClass("alert alert-danger");
    				$("#headMsg").append("<strong> ผิดพลาด! </strong>");  
    			}
    			else if($("#messageMsg").val() == 201){ // save ok
    				$("#msgAlert").removeClass().addClass("alert");
					$("#headMsg").empty().append("<b>บันทึกสำเร็จ</b>"); 				
    				$("#msgAlert").fadeTo(4000, 500).slideUp(1000, function(){
                	 	$("#msgAlert").alert('close');
                	});
    			}
    			else if($("#messageMsg").val() == 301){ // error code 1
    				$("#msgAlert").removeClass().addClass("alert alert-error");
					$("#headMsg").empty().append("<b>มีข้อผิดพลาด!</b>: พบข้อผิดพลาดในการบันทึกข้อมูล"); 				
    				$("#msgAlert").fadeTo(4000, 500).slideUp(1000, function(){
                	 	$("#msgAlert").alert('close');
                	});
    			}else{
    				$("#msgAlert").removeClass("alert");
    				$("#msgAlert").removeClass("alert-error");
    			}         	
            }
    	});
    	/* bind element event*/
   	 	function actSave(){
    		var masterYear = $("#fMasterAcademicYear").val(); 
    		var resultYear = $("#fAppraisalAcademicYear").val();
    		var academicMonth = $("#fFirstMonthAcademic").val();
    		var fiscalMonth = $("#fFirstMonthFiscal").val();
    		//var academicMonth = $("#fFirstMonthAcademic")[0].selectedIndex + 1;
    		//var fiscalMonth = $("#fFirstMonthFiscal")[0].selectedIndex + 1; 
    		console.log('masterYear:'+masterYear+', resultYear:'+resultYear+', academicMonth:'+academicMonth+', fiscalMonth:'+fiscalMonth);
    		
	   	 	$.ajax({
				dataType:'json',
				url: '<%=requestSaveSysYear%>',
				data: { 'masterYear':academicYear,'resultYear':resultYear,'academicMonth':academicMonth, 'fiscalMonth':fiscalMonth } ,
				success: function(data){
					//Status: 0=exist, 1=sucess, -9=procedure error //alert-error, alert-success, alert-block
					console.log("Save Status "+ data['saveStatus']);
					if(data["saveStatus"] == "0"){
						$("div#alertMsg").removeClass().addClass("alert alert-block");
						$("div#alertMsg span").empty().append("<b>แจ้งเตือน!</b>: มีข้อมูลอยู่ในระบบแล้ว จึงไม่สามารถแก้ไขเดือนเริ่มต้นได้ ");
						
					}else if(data["saveStatus"] == "-9"){
						$("div#alertMsg").removeClass().addClass("alert alert-error");
						$("div#alertMsg span").empty().append("<b>มีข้อผิดพลาด!</b>: พบข้อผิดพลาดในการบันทึกข้อมูล");
						
					}else if(data["saveStatus"] == "1"){
						//alert( $("#fFirstMonthAcademic").val() );
						//$('#sysYearForm').attr('action',"<%=formActionSave%>");
						//$('#sysYearForm').submit();
					}
					
					/* $("div#alertMsg").fadeTo(5000, 500).slideUp(500, function(){
					    $("div#alertMsg").alert('close');
					}); */
					$("div#alertMsg").fadeTo(5000, 500).slideUp(500);
				}
	   	 	});
   	 	
   	 	}
    	function actFormSubmit(){
    		$('#sysYearForm').submit();
    	}
	 	function actCancel(el){
	 		$('#sysYearForm').trigger('reset');   	  		
	  	} 
   	</script>  
  
   	<style type="text/css">
   		div.boxAct{
			padding: 20px 20px 20px 20px;
			border: thin solid #CDCDCD;
			border-radius: 10px;
			display: block; 
		}
		button.btnPag {
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
	<input id="messageMsg" type="hidden" value="${messageCode}" />
	<c:if test="${not empty messageCode}">
	<div id="msgAlert" style="display:block">
	    <button type="button" class="close" data-dismiss="alert">x</button>
	    <span id="headMsg"> </span> 
	</div>
	</c:if>
	
    
	<div class="box">
		<div id="formActLv" class="boxAct">
			<form:form id="sysYearForm" modelAttribute="sysYearForm" action="${formActionSave}" method="POST" enctype="multipart/form-data">
				<fieldset>
					<div style="text-align: center;">
						
						
						<!-- Set Month for <Select>  -->
						<form:input id="fYearId" type="hidden" value="${yearId}" path="sysYearModel.yearId"/>
						<form:input id="fCreateDate" type="hidden" value="${createdDate}" path="createDate"/>
						<form:input id="fCreatedBy" type="hidden" value="${createdBy}" path="sysYearModel.createdBy"/>
						<table style="margin:auto">
							<tr>
								<td style="text-align:right"> <label> ปีการศึกษา เริ่มเดือน:</label></td>
								<td>
									<form:select id="fFirstMonthAcademic" path="sysYearModel.firstMonthAcademic" items="${months}" disabled="true"/>
									
								</td>
							</tr>
							<tr>
								<td style="text-align:right">ปีงบประมาณ เริ่มเดือน :</td>
								<td>
									<form:select id="fFirstMonthFiscal" path="sysYearModel.firstMonthFiscal" items="${months}" disabled="true"/>
								</td>
							</tr>
							<tr>
								<td style="text-align:right">กำหนดปีสร้างข้อมูลหลัก(ปีการศึกษา) :</td>
								<td>
									<form:select id="fMasterAcademicYear" path="sysYearModel.masterAcademicYear" items="${setupYears}"/>
								</td>
							</tr>
							<tr>
								<td style="text-align:right">กำหนดปีการประเมินผล(ปีการศึกษา) :</td>
								<td>
									<form:select id="fAppraisalAcademicYear" path="sysYearModel.appraisalAcademicYear" items="${appraisalYears}"/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<label id="ckInputText" style="color:red; display:none;">*กรุณากรอกข้อมูลให้ครบถ้วน</label> <br/>								
									<input type="button" class="save" value="บันทึก" onClick="actFormSubmit()" /> 
									<input type="button" class="cancel" value="ยกเลิก" onClick="actCancel()" />
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
			</form:form>
		</div><br/>
	</div>

</body>
</html>	
   