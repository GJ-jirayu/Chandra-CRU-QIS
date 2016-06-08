<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %> 
<%@ page import="javax.portlet.PortletURL"%>

<portlet:actionURL var="formActionSave">
	<portlet:param name="action" value="doSave"/>
</portlet:actionURL> 
<portlet:actionURL var="formActionBack">
	<portlet:param name="action" value="doShowList"/>
</portlet:actionURL> 
<portlet:resourceURL var="connGetList" id="connGetList" ></portlet:resourceURL>
<portlet:resourceURL var="connGetDetail" id="connGetDetail" ></portlet:resourceURL>
<portlet:resourceURL var="connSave" id="connSave" ></portlet:resourceURL>
<portlet:resourceURL var="connDelete" id="connDelete" ></portlet:resourceURL>
<portlet:resourceURL var="exDataQuery" id="exDataQuery" ></portlet:resourceURL>
<html lang="en">
  <head>
   <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap core CSS --> 
 <!--     <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" type="text/css"/>
    -->
    <script src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script> 
    <script  src="<c:url value="/resources/js/smoothness/jquery-ui-1.9.1.custom.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/smoothness/jquery-ui-1.9.1.custom.min.css"/>"/>
   	<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>  
	<script  src="<c:url value="/resources/bootstrap/js/bootstrap-typeahead.min.js"/>"></script>   
   	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/portlet-content.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/override-portlet-aui.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/override-jqueryui.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/common-element.css"/>" type="text/css"/>
   	<script src="<c:url value="/resources/js/confirm-master/jquery.confirm.min.js"/>"></script>
   	
    <script type="text/javascript"> 
	var portletBoxName = "cdsDetail";
	var connModeNS = ["edit","save"];
	var connMode = connModeNS[0];
    $(function() {
    	// initial state
    	$("#accordion").accordion({ active: 0 , autoHeight: false  });
    	$("#dvQuery").show();
    	$("#dvExData").hide();
    	$("input[type=\"button\"].queryCnt").addClass("buttonEnabled");
    	$("input[type=\"button\"].exDataCnt").addClass("buttonClearType");
    	fireSqlFlag();
    	toggleSqlInput();
    	doConnListRequest();
    	message();
    	$('#cdsDetail').on('keydown', '.numberOnly', 
    		function(e){
    			-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()
    		});
    	
    	// Set default cds level //
    	if("${pageAction}" == "new"){
    		elRadioCdsLevel = $("input.cdsLevel");
    		elRadioCdsLevel.each(function( index ) {    			
    			if(index == 0){
    				$(this).prop("checked", true);
    			}
			});
    	}
    });
    //bind event
    function message(){
    		if($("#messageMsg").val()){
    			if($("#messageMsg").val() > 100){ // error
    				$("#msgAlert").removeClass().addClass("alert alert-danger");
    				$("span#headMsg").append("<strong> ผิดพลาด! </strong>");    				
    				$("#msgAlert").fadeTo(10000, 500).slideUp(1000, function(){
                	    $("#msgAlert").alert('close');
                	});
    			}
    			else{ // 100 ok
    				$("#msgAlert").removeClass().addClass("alert");
    				$("span#headMsg").html("");
    				$("#msgAlert").fadeTo(10000, 500).slideUp(1000, function(){
                	    $("#msgAlert").alert('close');
                	});
    			}
            }
    }
    function bindConnSelectionEvent(){
    	$("#"+portletBoxName+" input[type=\"radio\"][name=\"connId\"]").click(function(){
    		var useClass="activeline";
    		$(this).closest("table").find("tr").removeClass(useClass);
    		$(this).closest("tr").addClass(useClass);
    		$("#"+portletBoxName+" input[type=\"hidden\"]#connId").val( $(this).val() );
    	});
    }
    function fireConnById(){
    	var id = $("#"+portletBoxName+" input[type=\"hidden\"]#connId").val();
    	if(typeof id !="undefined" && id!=null &&id!=""){
    		var input = $("#"+portletBoxName+" table#connectionList>tbody").find("input[type=\"radio\"]#"+id);
    		if(typeof input !="undefined"){
    			input.click();
    		}
    	}
    }
    // ui event
    function actPageSave(){
    	var cdsName = $('#cdsName').val()||"";
    	var levelId = $('input[name="cdsModel.levelId"]').val()||"";
    	if(cdsName==""||levelId==""){
    		$('#menuSave span#pageSubmitValidate').html("*กรุณาระบุชื่อและระดับของข้อมูลพื้นฐาน");
    		$('#menuSave span#pageSubmitValidate').css( "display", "block" ).fadeOut( 10000 );
    	}else{
    		$("#"+portletBoxName+" form#cdsForm").attr("action","<%=formActionSave%>");
        	$("#"+portletBoxName+" form#cdsForm").submit();
    	}    	
    }
    function actPageCancel(){
    	$("#"+portletBoxName+" form#cdsForm").attr("action","<%=formActionBack%>");
    	$("#"+portletBoxName+" form#cdsForm").submit();
    }

    function actConnShowDetail(el){
    	$("#connDetailPanel>h3").html("เพิ่มการเชื่อมต่อ");
    	var cnt = $(el).next("div.connDetailPanel");
    	if(cnt.is(":visible")){
    		$("#connDetailPanel>table").find("input[type=\"text\"]").val("");
    		$("#connDetailPanel>table").find("input[type=\"password\"]").val("");
    		$("#connDetailPanel").hide();
    	}else{
        	cnt.show();
    	}
    	$("#"+portletBoxName+" #connIdAjax").val('');
    }
    function actConnEdit(el){
    	$("#connDetailPanel").show();
    	$("#connDetailPanel>h3").html("แก้ไขการเชื่อมต่อ");
    	
    	var connId = $(el).closest("tr").find("input[type=\"radio\"][name=\"connId\"]").val();
    	$("#"+portletBoxName+" #connIdAjax").val(connId);
    	doConnDetailRequest(connId);    	
    }
    function actConnDelete(el){
    	var connId = $(el).closest("tr").find("input[type=\"radio\"][name=\"connId\"]").val();
    	var dataName = $(el).parent('td').parent('tr').children('td:nth-child(3)').html();
    	/* var ans = confirm("ยืนยันการลบ");
    	if(ans){
    		doConnDeleteRequest(connId);
    	}else{
    		
    	} */
    	$.confirm({
	   	     text: "ยืนยันการลบข้อมูลการเชื่อมต่อ \""+dataName+"\" !?",
	   	     title: "ลบข้อมูลการเชื่อมต่อ",
	   	     confirm: function(button) {
	   	    	doConnDeleteRequest(connId);
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
    function actConnSave(el){ // now in td
    	var conName = $("#connName").val();
    	var connHost = $("#connHost").val();
    	var connPort = $("#connPort").val();
    	var connDbName = $("#connDbName").val();
    	var connUsername = $("#connUsername").val();
    	var connPassword = $("#connPassword").val();
    	if(conName==""||connHost==""||connPort==""||connDbName==""||connUsername==""||connPassword==""){
    		$('label#ckInputText').css( "display", "block" ).fadeOut( 5000 );
    	}else{
    		doConnSaveRequest();
    	}         
    }
    function actConnClose(el){ // now in td
		$("#connDetailPanel>table").find("input[type=\"text\"]").val("");
		$("#connDetailPanel>table").find("input[type=\"password\"]").val("");
		$("#connDetailPanel").hide();
    	//$(el).closest("table").find("input[type=\"text\"]").val("");
    	//$(el).closest("table").find("input[type=\"password\"]").val("");
    	//$(el).closest("div.connDetailPanel").hide();
    }
    // press
    function fireSqlFlag(){
    	var state = $("#"+portletBoxName+" #sqlFlag").val(); //1,0
    	if(state==1){
    		$("#"+portletBoxName+" #useQuery").prop('checked', true);	
    	}
    	else{
    		$("#"+portletBoxName+" #useQuery").prop('checked', false);
    	}
    }
    function toggleSqlInput(){
    	/* deprecate
    	var state = $("#"+portletBoxName+" #sqlFlag").val(); //1,0
    	if(state==1){
    		$(".ckSqlFlag").prop('disabled', false);
    	}
    	else{
    		$(".ckSqlFlag").prop('disabled', true);
    	}*/
    }
    function checkQuery(el){
    	if(el.checked){
    		$("#"+portletBoxName+" #sqlFlag").val(1);
    	}else{
        	$("#"+portletBoxName+" #sqlFlag").val(0);
        }
    	toggleSqlInput();
    }
    function renderQueryAttr(){
    	// render from exData header output
		var headAr = [];
    	var table = $("#"+portletBoxName+" #dvExData>#displayExData>table");
		if(typeof table != "undefined"){
			var metaTr = table.find("tr:nth-child(1)");
			metaTr.find("td").each(function(){
				headAr.push($(this).html());
			});
			renderSelectAttr(headAr);
		}
    }
    function showExampleData(){
    	$("#"+portletBoxName+" #dvQuery").hide();
    	$("#"+portletBoxName+" #dvExData").show();
    	$("#"+portletBoxName+" #dvExData>#displayExData").html("กำลังโหลด......");
    	$("input[type=\"button\"].queryCnt").removeClass("buttonEnabled");
    	$("input[type=\"button\"].queryCnt").addClass("buttonClearType");
    	$("input[type=\"button\"].exDataCnt").removeClass("buttonClearType");
    	$("input[type=\"button\"].exDataCnt").addClass("buttonEnabled");
    	$("input[type=\"button\"].queryCnt").prop('disabled', true);
    	$("input[type=\"button\"].exDataCnt").prop('disabled', true);
    	setTimeout(function(){ doPreviewQuery(); }, 1500); 
    }
    function showQuery(){
    	$("#"+portletBoxName+" #dvQuery").show();
    	$("#"+portletBoxName+" #dvExData").hide();
    	$("#"+portletBoxName+" #dvExData>#displayExData").empty();
    	$("input[type=\"button\"].queryCnt").removeClass("buttonClearType");
    	$("input[type=\"button\"].queryCnt").addClass("buttonEnabled");
    	$("input[type=\"button\"].exDataCnt").removeClass("buttonEnabled");
    	$("input[type=\"button\"].exDataCnt").addClass("buttonClearType");
    }
    // request ###
    function doConnListRequest(){
    	$.ajax({
   	 		dataType: "json",
   	 		url:"<%=connGetList%>",
   	 		success:function(data){
   	 			renderConnList(data["lists"]);
   	 		} 
   	 	});
    }
    function doConnDetailRequest(connId){
    	$.ajax({
   	 		dataType: "json",
   	 		url:"<%=connGetDetail%>",
   	 		data: { id: $("#"+portletBoxName+" #connIdAjax").val()  },
   	 		success:function(data){
   	 			//alert(JSON.stringify(data));
   	 			if(data["conn"]["id"]>0){
   	 				renderConnDetail(data["conn"]);
   	 			}
   	 		} 
   	 	});
    }
	function doConnDeleteRequest(connId){
		$.ajax({
   	 		dataType: "json",
   	 		data: { "id": connId},
   	 		url:"<%=connDelete%>",
   	 		success:function(data){
   	 			if(data["status"]>0){
   	 				alert("ลบสำเร็จ");
   	 				doConnListRequest();
   	 			}else{
   	 				alert("ลบผิดพลาด");
   	 			}
   	 		} 
   	 	});
    }
    function doConnSaveRequest(){
    	$.ajax({
   	 		dataType: "json",
   	 		data:{"mode":connMode,"id":$("#"+portletBoxName+" #connIdAjax").val() , "name": $("#"+portletBoxName+" #connName").val()
   	 			,"dbType": $("#"+portletBoxName+" #connDbType").val() ,"dbName": $("#"+portletBoxName+" #connDbName").val() 
   	 			,"host": $("#"+portletBoxName+" #connHost").val() ,"port": $("#"+portletBoxName+" #connPort").val() 
   	 			,"username": $("#"+portletBoxName+" #connUsername").val(),"password":  $("#"+portletBoxName+" #connPassword").val()
   	 			,"academicYear":$('input#academicYear').val()
   	 			,"createBy": $('input#connCreateBy').val()
   	 			,"createDate": $.trim($('input#connCreateDate').val())
   	 			},
   	 		url:"<%=connSave%>",
   	 		success:function(data){
   	 			//alert(JSON.stringify(data));
   	 			if(data["status"]>0){
   	 				alert("บันทึกสำเร็จ");
   	     			$("#connDetailPanel").hide();
   	   	 			doConnListRequest();
   	   	 			
   	   	 			/* Input tag data clear. */
   	   	 			$('table#connTable tbody input:text').val('');
   	   	 			$('table#connTable tbody input:password').val('');
   	   	 			
   	 			}else if(data["status"] == 0){
   	 				alert("ชื่อการเชื่อมต่อมีอยู่ในระบบเรียบร้อยแล้ว!!");
   	 				
   	 			}else{
   	 				alert("บึกทึกผิดพลาด");
   	 			}
   	 		} 
   	 	});
    }
    function doPreviewQuery(){
    	//var ret = [[["id"],["name"]],[[1,"ทดสอบ"],[2,"ทดสอบ"]]]; // meta/dat
    	var connId = $("#"+portletBoxName+" #connId").val();
    	var query = $("#"+portletBoxName+" #queryTextArea").val();
    	var msgCnt = $("#"+portletBoxName+" #displayExData");
    	msgCnt.empty();
    	$.ajax({
   	 		dataType: "json",
   	 		data: { "connId": connId,"query":query},
   	 		url:"<%=exDataQuery%>",
   	 		success:function(data){
   	     		if(data["header"]["size"]>0){
   	     			renderExampleData(data["content"]["lists"]);
   	     			renderQueryAttr();
   	     		}
   	     		else{
   	     			msgCnt.html(data["header"]["status"]);
   	     		}
   	 		}, 
   	 		error:function(){
   	 			msgCnt.html("เซิร์ฟเวอร์เซอร์วิสทำงานผิดพลาด");
   	 		},
   	 		complete:function(){
		    	$("input[type=\"button\"].queryCnt").prop('disabled', false);
		    	$("input[type=\"button\"].exDataCnt").prop('disabled', false);
   	 		}
   	 	});
    }
    // renderFunction 
    function renderSelectAttr(cols){
    	var cnt = $("#cdsDetail #queryAttribute select"); // multi element
    	var sel = $("<select></select");
    	sel.append("<option value=\"\">none</option>"); // default with none
	    	for(var i=0;i<cols.length;i++){
	    		var opt = $("<option></option>");
	    		opt.attr("value",cols[i]);
	    		opt.append(cols[i]);
	    		sel.append(opt);
	    	}
	    // bind option to selects
	    cnt.each(function(){
	    	$(this).empty();
	    	$(this).html(sel.html());
	    });
    }
    function renderExampleData(dataAr){
    	var cnt = $("#cdsDetail #displayExData"); //append
    	var table = $("<table></table>");
    	for(var r=0;r<dataAr.length;r++){
    		var tr = $("<tr></tr>");
    		for(var i=0;i<dataAr[r].length;i++){
    			var td = $("<td></td>");
    			td.append(dataAr[r][i]);
    			tr.append(td);
    		}
    		table.append(tr);
    	}
    	cnt.append(table);
    }
    function setAttrSelectionValue(){
    	
    }
    function renderConnList(list){
    	//ex[1,"ทดสอบ","mysql","user1"]
    	var tbody = $("#"+portletBoxName+" table#connectionList tbody");

    	var selectionTd = tbody.find("tr:first-child>td:first-child").clone();
    	var actionEdtTd = tbody.find("tr:first-child>td:nth-child(5)").clone();
    	var actionDelTd = tbody.find("tr:first-child>td:nth-child(6)").clone();
    	tbody.empty();
    	for(var r=0;r<list.length;r++){
        	var tr = $("<tr></tr>")
        	var newSelectTd = selectionTd.clone();
        	newSelectTd.children("input[type=\"radio\"]").attr("value",list[r]["id"])
        	newSelectTd.children("input[type=\"radio\"]").attr("id",list[r]["id"])
        	tr.append( newSelectTd );
        	tr.append( $("<td></td>").html(list[r]["dbName"]) );
        	tr.append( $("<td></td>").html(list[r]["name"]) );
        	tr.append( $("<td></td>").html(list[r]["userName"]) );
        	tr.append( actionEdtTd.clone() );
        	tr.append( actionDelTd.clone() );
        	tbody.append(tr);
    	} // end loop
    	//bindEvent click hilight
    	bindConnSelectionEvent();
    	fireConnById();
    }
    function renderConnDetail(conn){
    	$("#"+portletBoxName+" #connId").val(conn["id"]);
    	$("#"+portletBoxName+" #connName").val(conn["name"]);
    	$("#"+portletBoxName+" #connDbType").val(conn["dbType"]);
    	$("#"+portletBoxName+" #connDbName").val(conn["dbName"]);
    	$("#"+portletBoxName+" #connHost").val(conn["host"]);
    	$("#"+portletBoxName+" #connPort").val(conn["port"]);
    	$("#"+portletBoxName+" #connUsername").val(conn["username"]);
    	$("#"+portletBoxName+" #connPassword").val(conn["password"]);
    	$("#"+portletBoxName+" #connCreateBy").val(conn["createBy"]);
    	$("#"+portletBoxName+" #connCreateDate").val(conn["createDate"]);
    }
   	</script>
   	<style type="text/css">
   		table td:nth-child(1){
   			width:120px;
   		}
   		table td:nth-child(2){
   			width: auto;
   		}
   		div.queryCnt{
   			padding:3px 3px 3px 3px;
   			border:1px solid #acacac;
    		background: linear-gradient(#dfdfdf,#ffffff, #dfdfdf); 
   		}
   		div.exDataCnt{
   			border:1px solid #bdbdbd;
   		}
   		#cdsDetail.box table#connectionList td:nth-child(1){
   			width:30px;
   		}
   		#cdsDetail.box table#connectionList td:nth-child(2){
   			width:100px;
   		}
   		
   		#cdsDetail.box table#connectionList td:nth-child(3){
   			width:200px;
   		}
   		
   		#cdsDetail.box table#connectionList td:nth-child(4){
   			width:100px;
   		}
   		
   		#cdsDetail.box table#connectionList td:nth-child(5){
   			width:80px;
   		}
   		#cdsDetail #dvExData{
   			overflow: scroll;
   		}
   		#cdsDetail #dvExData #displayExData{
   			background-color:white;
   			width:100%;
   			min-width:300px;
   			min-height:100px;
   		}
   		#displayExData table{
   			border-spacing:0px;
   		}
   		#displayExData table td{
   			min-width:120px;
   			background-color:white;
   			border:1px solid #efefef;
  			display: inline-block;
   		}
   		.connDetailPanel{padding:10px 10px 10px 10px;border-radius:4px;
   			box-shadow: inset 0 0 2px black, 0 0 1px black; 
   			margin:10px 0px 10px 0px;
   			display:none;}
   			button:actived{ border:10px solid black; }
   		table.datagrid tr.activeline td{ background-color:#5EFB6E; }
   		#cdsDetail #cdsDetailDesc textarea{ width:auto; }
   		.pointer { cursor: pointer; }
   		table#cdsDataTable td:first-child{
   			width: 15%;
   			/* text-align: right; */
   			vertical-align: top;
   			padding-left: 20px
   		}
   		.ui-widget *{
   			font-family: "Liberation Sans"!important;
			font-size: 15px!important;
   		}
   		.ui-widget-content {
   			background: none!important;
   		}
   		div#cdsDetail input[type="radio"]{
   			margin: 0px;
   		}
   	</style>
  </head>
  
  <body>
  	<div id="cdsDetail" class="box">
  		<input id="messageMsg" type="hidden" value="${messageCode}" />
		<c:if test="${not empty messageCode}">
		<div id="msgAlert" style="display:none">
	    	<button type="button" class="close" data-dismiss="alert">x</button>
	    	<span id="headMsg"> </span> ${messageDesc} 
		</div>
		</c:if>
  		<form:form  id="cdsForm" modelAttribute="cdsForm" method="post" class="form" name="cdsForm" action="" enctype="multipart/form-data">
	       		<div id="accordion">
					<h3>ข้อมูลพื้นฐาน</h3>
					<div id="cdsDetailDesc" class="accord_content">
						<table id="cdsDataTable">
							<thead>
								<tr>
									<!-- <td> <span style="font-weight:bold"> ข้อมูลพื้นฐาน </span> </td> -->
									<td><form:input type="hidden" path="cdsModel.cdsId" /></td>
									<td><form:input type="hidden" path="cdsModel.createdBy" /></td>
									<td><form:input type="hidden" path="cdsModel.createdDate" /></td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td> <label>รายการ :</label> </td>
									<td> <form:textarea id="cdsName" path="cdsModel.cdsName" cols="70"  rows="2"></form:textarea></td>
								</tr>
								<tr>
									<td>ระดับข้อมูลพื้นฐาน :</td>
									<td> <form:radiobuttons class="cdsLevel" path="cdsModel.levelId" items="${levelList}"/> </td>
								</tr>
								<tr>
									<td>คำอธิบาย :</td>
									<td><form:textarea path="cdsModel.cdsDesc" cols="70" rows="2"></form:textarea></td>
								</tr>
								<tr><td></td><td></td></tr>
								<tr>
									<td>รหัสข้อมูลพื้นฐาน che :</td>
									<td><form:input type="text" class="tinyText" path="cdsModel.cheCode" readonly="true"/></td>
								</tr>
								
								<!-- Hidden -->
								<tr style="display:none;"><td>Academic_Year</td><td><form:input id="academicYear" type="text" class="tinyText" path="cdsModel.academicYear"/></td></tr>
							</tbody>
						</table>
					</div> 
					<h3>ตั้งค่าข้อมูลพื้นฐาน</h3>
					<div class="accord_content">
						<table>
							<!-- <thead>
								<tr><td>ข้อมูลพื้นฐาน</td><td></td></tr>
							</thead> -->
							<tbody>
								<tr><td>การเชื่อมต่อ</td>
								<td>
									<input type="hidden" id="connIdAjax" />
									<button type="button" class="icon buttonClearType" onClick="actConnShowDetail(this)"><img  src="<c:url value="/resources/images/add.png"/>"></button>
									<div id="connDetailPanel" class="connDetailPanel">
										<h3>เพิ่ม/แก้ไข การเชื่อมต่อ</h3>
										<table id="connTable">
											<tbody>
												<tr><td>ชื่อการเชื่อมต่อ</td>
													<td><input id="connName" type="text" class="longText" value=""/></td></tr>
												<tr><td>ชนิดฐานข้อมูล</td>
													<td><select id="connDbType">
														<c:if test="${not empty dbTypeList}"> 
       														<c:forEach items="${dbTypeList}" var="dbType" varStatus="loop">
       															<option value="${dbType.value}">${dbType.key}</option>
															</c:forEach>
														</c:if>
													</select></td>
												</tr>
												<tr><td>หมายเลขไอพี</td>
													<td><input type="text" id="connHost" class="midText" style="margin-right:10px;" value=""/></td> </tr>
												<tr><td>พอร์ต</td> 
													<td><input type="text" id="connPort" class="tinyText numberOnly" maxlength="5" value=""/></td></tr>
												<tr><td>ชื่อฐานข้อมูล</td>
													<td><input type="text" id="connDbName" class="smallText" value=""/></td></tr>
												<tr><td>ชื่อผู้ใช้</td>
													<td><input type="text" id="connUsername" class="smallText" value=""/></td></tr>
												<tr><td>รหัสผ่าน</td>
													<td><input type="password" id="connPassword" class="smallText" value=""/></td></tr>
												<tr style="display:none;">
													<td>Conn Create By (hide)</td>
													<td><input type="text" id="connCreateBy" class="smallText" value=""/></td></tr>
												<tr style="display:none;">
													<td>Conn Create Date (hide)</td>
													<td><input type="text" id="connCreateDate" class="smallText" value=""/></td></tr>
												<tr><td></td>
													<td>
													<!-- <input type="button" class="save" style="margin-right:10px;" onClick="actConnSave(this)" value="บันทึก"/>
													<input type="button" class="cancel" onClick="actConnClose(this)" value="ยกเลิก"/></td></tr> -->
													<label id="ckInputText" style="color:red; display:none;">*กรุณากรอกข้อมูลให้ครบถ้วน</label> <br/>
													<button class="save btn btn-primary" type="button" onClick="actConnSave(this)">บันทึก</button>
													<button class="cancel btn btn-danger" type="button" onClick="actConnClose(this)">ยกเลิก</button>
											</tbody>
										</table>
									</div>
									<form:input type="hidden" id="connId" path="cdsModel.connId" />
									<table id="connectionList" class="datagrid">
										<thead>
											<tr><th></th><th>ฐานข้อมูล</th><th>ชื่อการเชื่อมต่อ</th><th>ชื่อผู้ใช้</th><th>แก้ไข</th><th>ลบ</th></tr>
										</thead>
										<tbody>
											<tr><td><input type="radio" id="" name="connId" value="1" /></td><td>ทดสอบ 1</td><td>mssql</td><td>name1</td>
												<td>
													<img onClick="actConnEdit(this)" class="editRow pointer" src="<c:url value="/resources/images/edited.png"/>" width="25" height="25"/>
												</td>
												<td><img onClick="actConnDelete(this)" class="deleteRow pointer" src="<c:url value="/resources/images/delete.png"/>" width="25" height="25"/>
												</td></tr>
											<tr><td><input type="radio" id="" name="connId" value="1" /></td><td>ทดสอบ 2</td><td>oracle</td><td>name1</td>		
												<td></td></tr>
										</tbody>
									</table>
								</td>
								</tr>
								
								<tr><td>ระบุ Query</td>
									<td>
										<div>
											<input type="button" class="queryCnt ckSqlFlag" onClick="showQuery()" value="SqlQuery"/>
											<input type="button" class="exDataCnt ckSqlFlag" onClick="showExampleData()" value="ตัวอย่าง"/>
											<form:input type="hidden" id="sqlFlag" path="cdsModel.sqlFlag" />
											<div style="float: right;">
												<input type="checkbox" id="useQuery" name="useQuery" onclick="checkQuery(this)" value="checked"/>ใช้ query
											</div>
										</div>
										<div id="dvQuery" class="queryCnt cnt"><form:textarea id="queryTextArea" path="cdsModel.query" rows="7" class="boxWide ckSqlFlag"></form:textarea></div>
										<div id="dvExData" class="exDataCnt cnt"><div id="displayExData"></div></div>
									</td>
								</tr>
								
								<tr><td>คุณลักษณะ</td>
									<td>
										<table id="queryAttribute">
											<tr><td>คอลัมน์ผลลัพธ์ข้อมูลพื้นฐาน	</td><td><form:select id="valueField" class="ckSqlFlag" path="cdsModel.valueField"> <form:options items="${qValueList}" /></form:select></td></tr>
											<tr><td>คอลัมน์สถาบัน </td><td><form:select id="uniField" class="ckSqlFlag" path="cdsModel.universityField"><form:options items="${qUniList}" /></form:select></td></tr>
									/		<tr><td>คอลัมน์คณะ </td><td><form:select id="facultyField" class="ckSqlFlag" path="cdsModel.facultyField"><form:options items="${qFacultyList}" /></form:select></td></tr>
											<tr><td>คอลัมน์หลักสูตร	</td><td><form:select id="courseField" class="ckSqlFlag" path="cdsModel.courseField"><form:options items="${qCourseList}" /></form:select></td></tr>
											<tr><td>คอลัมน์ข้อมูลสนับสนุน </td><td><form:select id="detailField" class="ckSqlFlag" path="cdsModel.detailField"><form:options items="${qDetailList}" /></form:select></td></tr>
											<tr><td>คอลัมน์ปีปฏิทิน	</td><td><form:select id="yearField" class="ckSqlFlag" path="cdsModel.yearField"><form:options items="${qYearList}" /></form:select></td></tr>
											<tr><td>คอลัมน์เดือนปฏิทิน</td><td><form:select id="monthField" class="ckSqlFlag" path="cdsModel.monthField"><form:options items="${qMonthList}" /></form:select></td></tr>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				<!-- end accordion -->
				</div>
				<div id="menuSave" class="savePanel">
					<!-- <input type="button" class="save" onclick="actPageSave()" value="บันทึก"/>
					<input type="button" class="cancel" onclick="actPageCancel()" value="ยกเลิก"/> -->
					<span id="pageSubmitValidate" style="color:red; display:none;"></span> <br/>
					<button class="save btn btn-primary" type="button" onClick="actPageSave()">บันทึก</button>
					<button class="cancel btn btn-danger" type="button" onClick="actPageCancel()">ยกเลิก</button>
				</div>
		</form:form>
       </div> <!-- end box -->
  </body>
</html>	
