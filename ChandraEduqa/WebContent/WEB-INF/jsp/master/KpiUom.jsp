<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %> 
<%@ page import="javax.portlet.PortletURL" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.sql.*" %>
<%@ taglib prefix="chandraFn" uri="http://localhost:8080/web/function" %>

<portlet:actionURL var="formActionInsert"> <portlet:param name="action" value="doInsert"/> </portlet:actionURL> 
<portlet:actionURL var="formActionEdit"> <portlet:param name="action" value="doEdit"/> </portlet:actionURL> 
<portlet:actionURL var="formActionDelete"> <portlet:param name="action" value="doDelete"/> </portlet:actionURL> 
<portlet:actionURL var="formActionSearch"> <portlet:param name="action" value="doSearch"/> </portlet:actionURL> 
<portlet:actionURL var="formActionListPage"> <portlet:param name="action" value="doListPage"/> </portlet:actionURL> 
<portlet:actionURL var="formActionPageSize"> <portlet:param name="action" value="doPageSize"/> </portlet:actionURL>
<portlet:resourceURL var="getPlan" id="getPlan" ></portlet:resourceURL>
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
   	  	var dialog,dialog2;
    	$( document ).ready(function() {
    		paging();
    		$('.numPage').val(${PageCur});
    		$('.pageSize').val(${pageSize});
    		$('div.paging button:contains('+ ${PageCur} +')').css({'color':'#009ae5','text-decoration':'underline','border':'0.5px solid #009ae5'});
    		pageMessage();
    	});
    	function pageMessage(){
    		if($("#messageMsg").val()){
    			if($("#messageMsg").val() == 100){ //ok
    				$("#msgAlert").removeClass().addClass("alert");
    				$("span#headMsg").html("");
    				$("#msgAlert").fadeTo(10000, 500).slideUp(1000, function(){
                	    $("#msgAlert").alert('close');
                	});
    			}else{
    				$("#msgAlert").removeClass().addClass("alert alert-danger");
    				$("span#headMsg").append("<strong> ผิดพลาด! </strong>");    				
    				$("#msgAlert").fadeTo(10000, 500).slideUp(1000, function(){
                	    $("#msgAlert").alert('close');
                	});
    			}
            }
    	}
    	/* bind element event*/
    	function actSearch(el){
    		$('#kpiUomForm').attr("action","<%=formActionSearch%>");
    		$('#keySearch').val($('#textSearch').val());
    		$('#kpiUomForm').submit();
    	}
    	function actChangePageSize(el){
    		var numPage = $('.numPage').val();
    		var sizePage = $(el).val();
    		$('#kpiUomForm '+'#pageNo').val(numPage);
    		$('#kpiUomForm '+'#PageSize').val(sizePage);
    		$('#kpiUomForm').attr("action","<%=formActionPageSize%>");
    		$('#kpiUomForm').submit();
    	}
   	 	function actSelectPage(el){
   	 		var numPage = el.innerHTML;
   	 		var sizePage = $('.pageSize').val();
	   	 	$('#kpiUomForm '+'#pageNo').val(numPage);	   	 	
	   	 	$('#kpiUomForm '+'#PageSize').val(sizePage);
			$('#kpiUomForm').attr("action","<%=formActionListPage%>");
			$('#kpiUomForm').submit();
   	 	}
   	 	function actAdd(el){
   	 		renderDialog('#formActUom',1,'','');   	 		
   	 	}
   	 	function actEdit(el){
   	 		var dataId = parseInt($(el).parent('td').parent('tr').children('tbody tr td:nth-child(5)').html());
   	 		var dataDesc = [];
   	 		dataDesc["name"] = $.trim($(el).parent('td').parent('tr').children('tbody tr td:nth-child(2)').html());
   	 		dataDesc["createDate"] = $(el).parent('td').parent('tr').children('tbody tr td:nth-child(7)').html();
   	 		dataDesc["createBy"] = $(el).parent('td').parent('tr').children('tbody tr td:nth-child(8)').html();
   	 		renderDialog('#formActUom',2,dataId,dataDesc);
   	 	}
   	 	function actDelete(el){   	
   	 		var dataId = parseInt($(el).parent('td').parent('tr').children('tbody tr td:nth-child(5)').html());
	    	var dataName = $(el).parent('td').parent('tr').children('tbody tr td:nth-child(2)').text();
	   	 	$.confirm({
		   	     text: "ยืนยันการลบหน่วยวัด\"".concat(dataName, "\""),
		   	     title: "ลบหน่วยวัด",
		   	     confirm: function(button) {		   	    	
		   	 		$('#kpiUomForm').attr("action","<%=formActionDelete%>");
			 		$('#kpiUomForm '+'#fUomId').val(dataId);
			 		$('#kpiUomForm').submit();
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
   	 	function actSaveInsert(){
   	 		if($.trim($('#fUomName').val()) == ""){
   	 			$('label#ckInputText').css( "display", "block" ).fadeOut( 5000 );
   	 		}else{
	   	 		$('#kpiUomForm').attr('action',"<%=formActionInsert%>");   	 		
	   	 		$('#kpiUomForm').submit().trigger('reset');
	   	 		$('#fUomName').val("");
   	 		}
   	 	}
   	 	function actSaveEdit(){
   	 		if($.trim($('#fUomName').val()) == ""){
	 			$('label#ckInputText').css( "display", "block" ).fadeOut( 5000 );
	 		}else{
		 		$('#kpiUomForm').attr("action","<%=formActionEdit%>");
		 		$('#kpiUomForm').submit();
		 		$('#fUomName').val("");
	 		}
	 	}
	 	function actCancel(el){
	  		//dialog.dialog( "close" );
	  		$('#fUomName').val("");
	  		$('#formActUom').slideToggle('slow');     	  		
	  	}
   	 	function renderDialog(d1,mode,dataId,dataDesc){
   	 		/*mode 1:insert 2:edit*/
   	 		var head,event;
   	 		if(mode==1){
	 			head = 'เพิ่ม';
	 			event= 'actSaveInsert()';
	 			$(d1).trigger( "reset" );
	 			$(d1).find('input[type=text]#fUomName').val("");
	 			
	 		}else if(mode==2){
	 			head = 'แก้ไข';
	 			event='actSaveEdit()';
	 			$(d1).find('input[type=text]#fUomName').val(dataDesc["name"]);
	 		}
   	 		$(d1).find('span').html(head);
   	 		$(d1).find('input[type=hidden]#fUomId').val(dataId);
   	 		$(d1).find('input[type=hidden]#fUomCreateBy').val(dataDesc["createBy"]);
   			$(d1).find('input[type=hidden]#fUomCreateDate').val(dataDesc["createDate"]);	
   			$(d1).find('button.save').attr('onClick',event);

		   	if ( $(d1).is(':visible')) {

		   		return false ;
		   	}else{
		   		$(d1).slideToggle("slow");
		   	} 
   	 	} 
   	 	function paging(){
	   	 	if(${not empty uoms}){
	   	 		var totalPage = parseInt(${lastPage});
	   	 		$('div.buttonPage').empty();
	   	 		for(var i=1;i<=totalPage;i++){
	   	 			$('div.buttonPage').append($('<button class="btnPag" onClick="actSelectPage(this)">'+i+'</button>'));
	   	 		}
	 		}
   	 	}
   	 	function goPrev(){
        	if(${PageCur}!=1){
        		var numPage = parseInt($('.numPage').val())-1;
        		var sizePage = $('.pageSize').val();
        		$('#kpiUomForm '+'#pageNo').val(numPage);
        		$('#kpiUomForm '+'#PageSize').val(sizePage);
        		$('#kpiUomForm').attr("action","<%=formActionListPage%>");
        		$('#kpiUomForm').submit();
        	}
   	 	}
        function goNext(){
        	if(${PageCur} < ${lastPage}){
        		var numPage = parseInt($('.numPage').val())+1;
        		var sizePage = $('.pageSize').val();
        		$('#kpiUomForm '+'#pageNo').val(numPage);
        		$('#kpiUomForm '+'#PageSize').val(sizePage);
        		$('#kpiUomForm').attr("action","<%=formActionListPage%>");
        		$('#kpiUomForm').submit();
        	}
        }
   	</script>  
  
   	<style type="text/css">
   		div.boxAct{
			padding: 20px 20px 20px 20px;
			border: thin solid #CDCDCD;
			border-radius: 10px;
			display: block; 
		}
		
   		table.tableGridTp{
   			background-color:#FFFFFF;
    		border:1px solid #999999;
    		overflow:hidden;
    		width:100%;
   			padding-top:10px;
   			font-size:14px;
   		}   		
   		table.tableGridTp th:nth-child(1){ width:10%; }
   		table.tableGridTp th:nth-child(2){ width:60%; }
   		table.tableGridTp th:nth-child(3){ width:15%; }
   		table.tableGridTp th:nth-child(4){ width:15%; }
   		table.tableGridTp th:nth-child(5), table.tableGridTp td:nth-child(5){ width:0%; display:none;}
   		table.tableGridTp th:nth-child(6), table.tableGridTp td:nth-child(6){ width:0%; display:none;}
   		table.tableGridTp th:nth-child(7), table.tableGridTp td:nth-child(7){ width:0%; display:none;}
   		table.tableGridTp th:nth-child(8), table.tableGridTp td:nth-child(8){ width:0%; display:none;}
   		/* table.tableGridTp tbody td:nth-child(1){
   			text-align:center;$("#success-alert").alert('close');
   			border-color:#acacac;
   			border-width:0px 0px 0px 0px;
   			border-style:inset;
   			background: linear-gradient(white, #efefef,#e2e2e2,#f7f7f7);
   		} */
   		table.tableGridTp thead th{
   			height: 30px;
   			text-align:center;
   			border-color:#acacac;
   			border-width:0px 1px 1px 1px;
   			border-style:inset;
   			background: linear-gradient(white, #efefef,#e2e2e2,#f7f7f7);
   		}
   		table.tableGridTp tbody td {
			border: none;
			padding: 1px 2px 2px 2px;
		}
   		.tableGridTp tr:nth-child(2n){ background-color:rgba(244,244,244,1); }				
		.numPage{width:40px; margin-bottom: 0px;}
	
   	</style>
  </head>
  
<body> 
	<input type="hidden" id="messageMsg" value="${messageCode}"> 
	<div id="msgAlert" style="display:none">
	    <button type="button" class="close" data-dismiss="alert">x</button>
	    <span id="headMsg"> </span> ${messageDesc} 
	</div>
	
	<div class="box">
		<div id="formActUom" class="boxAct" style="display:none">
			<form:form id="kpiUomForm" modelAttribute="kpiUomForm" action="${formAction}" method="POST" enctype="multipart/form-data">
				<fieldset>
					<legend style="font:16px bold;">
						<span></span>หน่วยวัด
					</legend>
					<div style="text-align: center;">
						<form:input type="hidden" id="pageNo" path="pageNo" value="${PageCur}"/>
						<form:input type="hidden" id="PageSize" path="pageSize" />
						<form:input type="hidden" id="keySearch" path="keySearch" />
						<form:input type="hidden" id="fUomId" path="kpiUomModel.uomId" />
						<form:input type="hidden" id="fUomCreateBy" path="kpiUomModel.createdBy" />
						<form:input type="hidden" id="fUomCreateDate" path="createDate" />
						
						<table style="margin:auto"> 
							<tr>
								<td style="text-align:right">ชื่อหน่วยวัดตัวบ่งชี้: </td>
								<td><form:input id="fUomName" path="kpiUomModel.uomName" maxlength="255"/></td>
							</tr>
						</table>
						
						<label id="ckInputText" style="color:red; display:none;">*กรุณากรอกข้อมูลให้ครบถ้วน</label> <br/>				
						<button class="save btn btn-primary" type="button" onClick="actSaveInsert()">บันทึก</button>
						<button class="cancel btn btn-danger" type="button" onClick="actCancel()">ยกเลิก</button>
					</div>
				</fieldset>
			</form:form>
		</div><br/>
		
		<div class="row-fluid">
			<div class="span6">
				<span>ค้นหาหน่วยวัด : </span>
				<input type="text" id="textSearch" value="${keySearch}"  placeholder="ค้นหาจากชื่อ" style="margin-bottom: 0px;"/>
				<img src="<c:url value="/resources/images/search.png"/>" width="20" height="20" onClick="actSearch(this)" style="cursor: pointer;">
				<img src="<c:url value="/resources/images/add.png"/>" width="18" height="18" onClick="actAdd(this)" style="cursor: pointer;">
			</div>
		
			<div class="paging span6" align="right">
				<li style="display: inline-block;" onclick='goPrev()'>
					<a style="cursor: pointer;"><u>&lt;&nbsp;</u></a>
				</li>
				<div class="buttonPage"> 
					<!-- Generate from paging(). -->
					<button class="btnPag btnPagDummy" onClick="actSelectPage(this)"> 1 </button> 
				</div> 
				<li style="display: inline-block;" onclick='goNext()'>
					<a style="cursor: pointer;"><u>&nbsp&gt;</u></a>
				</li>
				&nbsp&nbsp&nbsp&nbsp
				<input type="hidden" class="numPage" style="width:60px"/>
				<span>จำนวนแถว: </span> 
				<select class="pageSize" onchange="actChangePageSize(this)">
	    			<option>10</option>
	    			<option>20</option>
	    			<option>30</option>
	    			<option>40</option>
	    			<option>50</option>
	  			</select>
			</div>
		</div>
		
		<div class="boxTable table-responsive">
			<table class="tableGridTp hoverTable">
				<thead>
					<tr>
						<th>ลำดับ</th>						
						<th>ชื่อหน่วยวัด</th>					
						<th>แก้ไข</th>
						<th>ลบ</th>
						<th>รหัส(ซ้อน)</th>
						<th>ปี (ซ้อน)</th>
						<th>createDate (ซ้อน)</th>
						<th>createBy (ซ้อน)</th>
					</tr>
				</thead>
				<tbody> 
					<c:if test="${not empty uoms}">
						<c:forEach items="${uoms}" var="uom" varStatus="loop">
							<tr>
								<td class="padL">${(loop.count+((PageCur-1)*pageSize))}</td>
								<td>${chandraFn:nl2br(uom.uomName)} </td>
								<td align="center">
									<img src="<c:url value="/resources/images/edited.png"/>" width="22" height="22" onClick="actEdit(this)" style="cursor: pointer;">
								</td>
								<td align="center">
									<img src="<c:url value="/resources/images/delete.png"/>" width="22" height="22" onClick="actDelete(this)" style="cursor: pointer;">
								</td>
								<td>${uom.uomId}</td>
								<td>${uom.academicYear}</td>
								<td> <fmt:formatDate value="${uom.createdDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td> ${uom.createdBy} </td>
							</tr> 
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
		
		<div class="row-fluid" style="margin-top: 10px">		
			<div class="paging span12" align="right">
				<li style="display: inline-block;" onclick='goPrev()'>
					<a style="cursor: pointer;"><u>&lt;&nbsp;</u></a>
				</li>
				<div class="buttonPage"> 
					<!-- Generate from paging(). --> 
					<button class="btnPag btnPagDummy" onClick="actSelectPage(this)"> 1 </button>
				</div> 
				<li style="display: inline-block;" onclick='goNext()'>
					<a style="cursor: pointer;"><u>&nbsp;&gt;</u></a>
				</li>
				&nbsp&nbsp&nbsp&nbsp
				<input type="hidden" class="numPage" style="width:60px"/>
				<span>จำนวนแถว: </span> 
				<select class="pageSize" onchange="actChangePageSize(this)">
	    			<option>10</option>
	    			<option>20</option>
	    			<option>30</option>
	    			<option>40</option>
	    			<option>50</option>
	  			</select>
			</div>
		</div>
		
	</div>
</body>
</html>	
   