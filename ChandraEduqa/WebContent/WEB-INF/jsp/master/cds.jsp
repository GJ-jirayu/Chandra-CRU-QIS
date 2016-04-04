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
<portlet:actionURL var="formActionSearch">
	<portlet:param name="action" value="doSearch"/>
</portlet:actionURL> 
<portlet:actionURL var="formActionListPage">
	<portlet:param name="action" value="doListPage"/>
</portlet:actionURL> 
<portlet:actionURL var="formActionPageSize"> <portlet:param name="action" value="doPageSize"/> </portlet:actionURL>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <%-- <!-- Jquery -->
    <script src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script> 
    <script  src="<c:url value="/resources/js/smoothness/jquery-ui-1.9.1.custom.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/smoothness/jquery-ui-1.9.1.custom.min.css"/>"/>
    
    <!-- Master Confirm -->
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>  
    <script src="<c:url value="/resources/js/confirm-master/jquery.confirm.min.js"/>"></script>   
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common-element.css"/>"/>  --%>
    
    <script src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script> 
    <script  src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>    
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>	
	<script src="<c:url value="/resources/js/confirm-master/jquery.confirm.min.js"/>"></script>	
	<link rel="stylesheet" href="<c:url value="/resources/css/common-element.css"/>" type="text/css"/>
    
    <script type="text/javascript"> 
    var portletBoxName = "cdsList";
    	$(function() {
    		paging();
    		$('.numPage').val(${PageCur});
    		$('.pageSize').val(${pageSize});
    		/* Highlighter curent page */
    		$('div.paging button:contains('+ ${PageCur} +')').css({'color':'#009ae5','text-decoration':'underline','border':'0.5px solid #009ae5'});
    		message();
    });
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
		function actAdd(el){
   	 		$('#cdsForm').attr("action","<%=formActionNew%>");
			$('#cdsForm').submit();
	 	}
	 	function actEdit(el){
	 		var dataId = parseInt($(el).parent('td').parent('tr').children('td:nth-child(1)').html());
	 		$('#cdsForm #cdsId').val(dataId);
   	 		$('#cdsForm').attr("action","<%=formActionEdit%>");
			$('#cdsForm').submit();	
	 	}
	 	function actDelete(el){
	 		var dataId = $(el).parent('td').parent('tr').children('td:nth-child(1)').html();
	   	 	var dataName = $(el).parent('td').parent('tr').children('td:nth-child(2)').html();
	 		$.confirm({
		   	     text: "ยืนยันการลบข้อมูลพื้นฐาน \""+dataName+"\" !?",
		   	     title: "ลบข้อมูลพื้นฐาน",
		   	     confirm: function(button) {		 
		 	 		$('#cdsForm').attr("action","<%=formActionDelete%>");
		  			$('#cdsForm '+'#cdsId').val(dataId);
		  			$('#cdsForm').submit();
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
	 	function actSearch(el){
	 		var word = $("#"+portletBoxName+" #textSearch").val();
	 		$("#"+portletBoxName+" form#cdsForm #keySearch").val(word);
	 		$("#"+portletBoxName+" form#cdsForm").attr("action","<%=formActionSearch%>");
	 		$("#"+portletBoxName+" form#cdsForm").submit();
	 	}
   	 	function paging(){
	   	 	if(${not empty listCds}){
	   	 		var totalPage = parseInt(${lastPage});
	   	 		$('div.buttonPage').empty();
	   	 		for(var i=1;i<=totalPage;i++){
	   	 			$('div.buttonPage').append($('<button class="btnPag" onClick="actSelectPage(this)">'+i+'</button> &nbsp;'));
	   	 		}
	 		}
   	 	}
   	 	function actSelectPage(el){
   	 		var page = $(el).html();
	   	 	$('#cdsForm'+' #pageNo').val(page);
			$('#cdsForm').attr("action","<%=formActionListPage%>");
			$('#cdsForm').submit();
   	 	}
   	 	function goPrev(){
    		var prevPage = parseInt($('#pageNo').val())-1;
        	if(prevPage>0){
        		var sizePage = $('.pageSize').val();
        		$('#cdsForm'+' #PageSize').val(sizePage);
        		$('#cdsForm'+' #pageNo').val(prevPage);
        		$('#cdsForm').attr("action","<%=formActionListPage%>");
        		$('#cdsForm').submit();
        	}
   	 	}
        function goNext(){
    		var nextPage = parseInt($('#pageNo').val())+1;
        	if(nextPage <= ${lastPage}){
        		var sizePage = $('.pageSize').val();
        		$('#cdsForm'+' #pageNo').val(nextPage);
        		$('#cdsForm'+' #PageSize').val(sizePage);
        		$('#cdsForm').attr("action","<%=formActionListPage%>");
        		$('#cdsForm').submit();
        	}
        }
        function actChangePageSize(el){
    		var numPage = $('.numPage').val();
    		var sizePage = $(el).val();
    		$('#cdsForm '+'#pageNo').val(numPage);
    		$('#cdsForm '+'#PageSize').val(sizePage);
    		$('#cdsForm').attr("action","<%=formActionPageSize%>");
    		$('#cdsForm').submit();
    	}
   	</script>  
   	<style type="text/css">
   		#cdsList div.row{
   			margin-left: 0px;
   		}
	   	input[type=text]#textSearch {
			vertical-align: middle;
			margin-bottom: 0px;
		}
   		/* DataGrid */
   		#cdsList table.datagrid{
   			background-color:#FFFFFF;
    		border:1px solid #999999;
    		margin-bottom: 10px;
    		margin-top: 10px;
    		overflow:hidden;
    		width:100%;
   			font-size:14px;
   		}
   		#cdsList table.datagrid thead th{
   			height: 30px;
   			text-align:center;
   			border-color:thin solid #CCCCCC;
   			background: linear-gradient(white, #efefef,#e2e2e2,#f7f7f7);
   		}
   		#cdsList table.datagrid tbody td {
			border: none;
			padding: 1px 2px 2px 2px;
		}
		#cdsList table.datagrid tbody tr:nth-child(2n) td{ background-color:rgba(244,244,244,1); }
		#cdsList table.datagrid th:nth-child(1), #cdsList table.datagrid td:nth-child(1){ width:0%; display: none; }
		#cdsList table.datagrid th:nth-child(2){ width:40%; }
		#cdsList table.datagrid th:nth-child(3){ width:30%; }
		#cdsList table.datagrid th:nth-child(4){ width:10%; } #cdsList table.datagrid td:nth-child(4){ text-align: center; }
		#cdsList table.datagrid th:nth-child(5){ width:10%; } #cdsList table.datagrid td:nth-child(5){ text-align: center; }
   		
   		#cdsList #dataPaging>div, #cdsList #dataPagingBottom>div{
   			display:inline-block;
   			margin: 0px;
   		}
   		#pageList, #pageListBottom{list-style:none;}
   		
   		li.currentpage>input{	color:blue; 	}
   		#dataSearch{ float:left;}
   		.currentpage{ color:blue;}
   		select.pageSize{
			width:60px;
			height:25px;
			vertical-align: middle;
			margin-bottom: 0px;
		}
   		button.btnPag {
		    background-color: Transparent;
		    background-repeat:no-repeat;
		    /* border:0.5px solid; */
		    border: none;
		    margin-right: 2px;
		    cursor:pointer;
		    overflow: hidden;
		    outline:none;
		}
   	</style>
  </head>

  <body>
  	<input id="messageMsg" type="hidden" value="${messageCode}" />
	<c:if test="${not empty messageCode}">
	<div id="msgAlert" style="display:none">
	    <button type="button" class="close" data-dismiss="alert">x</button>
	    <span id="headMsg"> </span> ${messageDesc} 
	</div>
	</c:if>

	<div id="cdsList" class="box">
		<div style="display: none;">
			<form:form id="cdsForm" modelAttribute="cdsForm" method="post"
				name="cdsForm" action=""
				enctype="multipart/form-data">
				<form:input type="hidden" id="pageNo" path="pageNo"
					value="${pageNo}" />
				<form:input type="hidden" id="PageSize" path="pageSize" />
				<form:input type="hidden" id="keySearch" path="keySearch" />
				<form:input type="hidden" id="cdsId" path="cdsModel.cdsId" />
			</form:form>
		</div>
		<div class="row" style="margin-bottom:6px;">
			<div id="dataSearch" class="span6">
				<!-- class="boxHeader" -->
				<span>ค้นหาข้อมูลพื้นฐาน</span> <input type="text" id="textSearch"
					placeholder="ค้นหาจากชื่อ" />
					<img height="20" width="20" src="<c:url value="/resources/images/search.png"/>" onClick="actSearch(this)">
					<img height="18" width="18" src="<c:url value="/resources/images/add.png"/>"  onClick="actAdd(this)">
			</div>

			<%-- <div id="dataPaging" class="span6" align="right">
	       			<input type="button" onclick="goPrev()" value="<<"/>
	       			<ul id="pageList" class="pageList" style="display: inline-block; margin: 0px">
	       			</ul>
	       			<input type="button" onclick="goNext();" value=">>"/>
	       		</div> --%>
			<div class="paging span6" align="right">
				<a onclick='goPrev()'
					style="display: inline-block; cursor: pointer;"> &lt;&nbsp; </a>
				<div class="buttonPage">
					<!-- Generate from paging(). -->
					<button class="btnPag btnPagDummy" onClick="actSelectPage(this)">
						1</button>
				</div>
				<a onclick='goNext()'
					style="display: inline-block; cursor: pointer;"> &nbsp;&gt; </a>
				&nbsp&nbsp&nbsp&nbsp <input type="hidden" class="numPage"
					style="width: 60px" /> <span>จำนวนแถว: </span> <select
					class="pageSize" onchange="actChangePageSize(this)">
					<option>10</option>
					<option>20</option>
					<option>30</option>
					<option>40</option>
					<option>50</option>
				</select>
			</div>
		</div>

		<table class="tableGridLv hoverTable">
			<thead>
				<tr>
					<th>รหัสข้อมูลพื้นฐาน</th>
					<th>ชื่อข้อมูลพื้นฐาน</th>
					<th>ระดับข้อมูลพื้นฐาน</th>
					<th>แก้ไข</th>
					<th>ลบ</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty listCds}">
					<c:forEach items="${listCds}" var="cds" varStatus="loop">
						<tr>
							<td>${cds.cdsId}</td>
							<td>${cds.cdsName}</td>
							<td>${cds.levelDesc}</td>
							<td style="text-align:center">
								<img height="24" width="24" src="<c:url value="/resources/images/edited.png"/>" onClick="actEdit(this)" >
							</td>
							<td style="text-align:center">
								<img height="24" width="24" src="<c:url value="/resources/images/delete.png"/>"  onClick="actDelete(this)" >
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>

		<%-- <div class="row">
	       		<div id="dataPagingBottom" class="span12" align="right">
	       			<input type="button" onclick="goPrev()" value="<<"/>
	       			<ul id="pageListBottom" class="pageList" style="display: inline-block; margin: 0px">
	       			</ul>
	       			<input type="button" onclick="goNext();" value=">>"/>
	       		</div>
       		</div> --%>
		<div class="row-fluid" style="margin-top: 10px">
			<div class="paging span12" align="right">
				<li style="display: inline-block;" onclick='goPrev()'><a
					style="cursor: pointer;"><u>&lt;&nbsp;</u></a></li>
				<div class="buttonPage">
					<!-- Generate from paging(). -->
					<button class="btnPag btnPagDummy" onClick="actSelectPage(this)">
						1</button>
				</div>
				<li style="display: inline-block;" onclick='goNext()'><a
					style="cursor: pointer;"><u>&nbsp;&gt;</u></a></li> &nbsp&nbsp&nbsp&nbsp
				<input type="hidden" class="numPage" style="width: 60px" /> <span>จำนวนแถว:
				</span> <select class="pageSize" onchange="actChangePageSize(this)">
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
