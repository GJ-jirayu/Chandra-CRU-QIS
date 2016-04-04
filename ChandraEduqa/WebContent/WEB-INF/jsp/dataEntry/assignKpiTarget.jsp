<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %> 
<portlet:resourceURL var="doSaveTarget" id="doSaveTarget" ></portlet:resourceURL>
<portlet:actionURL var="formActionSaveTarget">
	<portlet:param name="action" value="doSaveTarget2"/>
</portlet:actionURL> 

<portlet:actionURL var="formActionBack">
	<portlet:param name="action" value="doBack2List"/>
</portlet:actionURL> 

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
    <link rel="stylesheet"href="<c:url value="/resources/css/common-element.css"/>"type="text/css" />
   <script type="text/javascript"> 
    	var portlexBoxName = "assignkpi_target";
		var vars = {};
		vars['name'+1] = "something";
		var monthNameList = ["มกราคม","กุมภาพันธ์","มีนาคม","เมษายน","พฤษภาคม","มิถุนายน","กรกฎาคม","สิงหาคม","กันยายน","ตุลาคม","พฤศจิกายน","ธันวาคม"];
		$( document ).ready(function() {
			if($('#pageMessage').html()==""){
    			$('#pageMessage').hide();
    		}
	    	var frequency = $("#frequency").val();
			var startMonthNo = $("#startMonthNo").val();
			
			var newMonthList = [];
			var monthPos=startMonthNo-1;
			for(var i=1;i<=12;i++){
				newMonthList.push(monthNameList[monthPos++]); 
				monthPos==12?  monthPos=0: monthPos=monthPos;
			}
			sortMonthByCalendarType(startMonthNo);
			activeBoxByPeriod(frequency);
			/*start check number input*/
// 				$('.numbersOnly').keyup(function () {
// 				    if (this.value != this.value.replace(/[^0-9\.]/g, '0.0')) {
// 				       this.value = this.value.replace(/[^0-9\.]/g, '0.0');
// 				    }
// 				});
			/*end check number input*/
			var specialKeys = new Array();
		        specialKeys.push(8); //Backspace
		        specialKeys.push(46); //190 onkeyup
		        $(function () {
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
		        });
		        
		        /* $('div[class="numbersOnly"]').each(function(index,item){
		        	var keyCode = e.which ? e.which : e.keyCode
	                var ret = ((keyCode >= 48 && keyCode <= 57) || specialKeys.indexOf(keyCode) != -1);
		        	$(".numbersOnly").next().css("display", ret ? "none" : "inline");
		        }); */
			/***********************/
	  	});
		function actAssignAllMonth(el){
			var targetValue = $(el).prev("input[type=\"text\"]").val();
			var cnt = $("#"+portlexBoxName+" #eachMonth0 input[type=\"text\"]");
			cnt.each(function(){
				if($(this).is(':disabled')){
				}
				else{
					$(this).val(targetValue);	
				}
			});
		}
		function sortMonthByCalendarType(startMonth){
			var cnt = $("#"+portlexBoxName+" #eachMonth0");
			var monthBox = $("#"+portlexBoxName+" #eachMonth0>div");
			for(var i=1;i<startMonth;i++){
				$(monthBox[i-1]).detach().appendTo(cnt);
			}//for
		}
		function activeBoxByPeriod(period){
			var inputs = $("#"+portlexBoxName+" #eachMonth0>div input");	
			inputs.each(function(index){
				if((index+1)%period==0){
					$(this).prop('disabled', false);
				}else{
					$(this).prop('disabled', true);
				}
			});
		}
		function saveTarget2(){
			var cnt = $("#"+portlexBoxName+" #eachMonth>table>tbody");	
			 $.ajax({ 
	                url:urlName,    
	                type:"POST", 
	                contentType: "application/json; charset=utf-8",
	                data: jsonString, //Stringified Json Object
	                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
	                cache: false,    //This will force requested pages not to be cached by the browser          
	                processData:false, //To avoid making query String instead of JSON
	                success: function(resposeJsonObject){                           
	        }});
		}
		function saveTarget(){
			var kpiId = $("#"+portlexBoxName+" #kpiId").val();
			var orgId = $("#"+portlexBoxName+" #orgId").val();
			var orgName ="test";
			$.ajax({
    			dataType:'json',
    			url: "<%=doSaveTarget%>" ,
    			data: { "kpiId":kpiId , "orgId":orgId , "orgName":orgName,
    					"m1":$("#m1").val(),"m2":$("#m2").val(),"m3":$("#m3").val(),
    					"m4":$("#m4").val(),"m5":$("#m5").val(),"m6":$("#m6").val(),
    					"m7":$("#m7").val(),"m8":$("#m8").val(),"m9":$("#m9").val(),
    					"m10":$("#m10").val(),"m11":$("#m11").val(),"m12":$("#m12").val(),},
    			success: function(data){
    				if(data["header"]["success"]>0){
    					$('div#pageMessage').html("บันทึกสำเร็จ");
    					$('div#pageMessage').removeClass().addClass("alert");
    				}else{
    					$('div#pageMessage').html("บันทึกไม่สำเร็จ");
    					$('div#pageMessage').removeClass().addClass("alert alert-danger");
    				}
	   	 			$('div#pageMessage').focus();
	   	 			$('div#pageMessage').css( "display", "block" ).fadeOut( 30000 );
    			}
    		});
		}
		function saveAssignTarget(){
			$("#"+portlexBoxName+" #assignTargetForm").attr("action","<%=formActionSaveTarget%>");
			$("#"+portlexBoxName+" #assignTargetForm").submit();
		}
		function back(){
			$("#"+portlexBoxName+" #assignTargetForm").attr("action","<%=formActionBack%>");
			$("#"+portlexBoxName+" #assignTargetForm").submit();
		}
		
   	</script>  
   	<style type="text/css">
   		#assignkpi_target>div{
   			margin-bottom:16px;
   		}
   		#eachMonth>table>tbody span{
   			display:inline-block;
   			width:100px;
   		}
   		#eachMonth{
   		}
   		#eachMonth0>div{
   			display:block;
   			float:left;
   			margin-right:12px;
   		}
   		#eachMonth0>div>span{
   			display:inline-block;
   			min-width:200px;
   		}
   		#eachMonth0>div:nth-child(2n+1){
   			clear:both;
   		}
   		#eachMonth0>div:nth-child(2n){
   			float:left;
   		}
   		#eachMonth0 input[type="text"]{
   			width:80px;
   		}
   		#baselinePanel{
   			margin-left: 7%;
   			min-height:150px;
   			max-width: 1000px;
   			background-color:#dfdddf;
   			border:1px solid #acacac;
   		}
   		#headerPanel>*{ vertical-align:text-top; }
   		#headerPanel>span{display:inline-block;padding:5px 15px 0px 0px;}
   		#actionMenu {padding: 2% 0 2% 24%;}
		.marTaget {margin:3% 0% 0% 7%;}
		.marB7 {margin-bottom:7px;}
		.marL-35 {margin-left:-35%;}
   	</style>
  </head>

  <body>
       	<div id="assignkpi_target" class="box bg">
       		<div id="pageMessage" class="alert"></div>
       		<div id="headerPanel">
		       	<form:form id="kpiForm" modelAttribute="kpiForm" method="post"  name="kpiForm" action="" enctype="multipart/form-data">
		       		<h3><span>ตัวบ่งชี้</span></h3><form:input type="text" id="kpiId" path="kpiModel.kpiId" style="display:none" />
		       		 <!--  <form:textarea id="kpiName" path="kpiModel.kpiName" rows="2" style="width:80%;"/> -->
		       		
		       		<span style="padding-left:5px;">${kpiForm.kpiModel.kpiName} &nbsp; ${kpiForm.kpiModel.calendarTypeName} &nbsp; ${kpiForm.kpiModel.periodName} &nbsp; ${kpiForm.kpiModel.uomName}</span>
		      	</form:form>
         	</div>
         	<form:form id="assignTargetForm" modelAttribute="assignTargetForm" method="post"  name="assignTargetForm" action="" enctype="multipart/form-data">
		    
		     	<form:input type="hidden" id="frequency" path="frequency"/>
		       	<form:input type="hidden" id="startMonthNo" path="startMonthNo"/>
		       	<form:input type="hidden" id="kpiId" path="kpiId"/>
		       	<form:input type="hidden" id="orgId" path="orgId"/>
 			<div id="baselinePanel" style="display:none"></div>
 			<div id="targetPanel" style="margin:3% 0% 0% 7%;">
				<c:out value="${amsg}" />
 				<form:errors path="rmonth1" cssClass="error"/>
 					<form:errors path="rmonth2" cssClass="error"/>
 				<div id="allMonth" style="margin-bottom:2%">
 					<span>กำหนดเป็นหมายทุกเดือน: </span>
 					<input type="text" id="m0" maxlength="15" class="smallText numbersOnly" />
 					<input type="button" class="btn btn-inverse" onclick="actAssignAllMonth(this)" value="ตั้งค่า" style="margin-bottom: 7px;"/>
 					<span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span>
 				</div>
 				<div id="eachMonth0">
 					<div><span>มกราคม</span><form:input type="text" maxlength="15" id="m1" path="rmonth1" class="numbersOnly" style="margin-left:-35%;" /><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>กุมภาพันธ์</span><form:input type="text" maxlength="15" id="m2" path="rmonth2" class="numbersOnly" style="margin-left:-35%;" /><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>มีนาคม</span><form:input type="text" maxlength="15" id="m3" path="rmonth3" class="numbersOnly" style="margin-left:-35%;" /><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>เมษายน</span><form:input type="text" maxlength="15" id="m4" path="rmonth4" class="numbersOnly" style="margin-left:-35%;"/><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>พฤษภาคม</span><form:input type="text" maxlength="15" id="m5" path="rmonth5" class="numbersOnly" style="margin-left:-35%;" /><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>มิถุนายน</span><form:input type="text" maxlength="15" id="m6" path="rmonth6" class="numbersOnly" style="margin-left:-35%;" /><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>กรกฎาคม</span><form:input type="text" maxlength="15" id="m7" path="rmonth7" class="numbersOnly" style="margin-left:-35%;" /><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>สิงหาคม</span><form:input type="text" maxlength="15" id="m8" path="rmonth8" class="numbersOnly" style="margin-left:-35%;" /><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>กันยายน</span><form:input type="text" maxlength="15" id="m9" path="rmonth9" class="numbersOnly" style="margin-left:-35%;"/><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>ตุลาคม</span><form:input type="text" maxlength="15" id="m10" path="rmonth10" class="numbersOnly" style="margin-left:-35%;" /><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>พฤศจิกายน</span><form:input type="text" maxlength="15" id="m11" path="rmonth11" class="numbersOnly" style="margin-left:-35%;" /><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 					<div><span>ธันวาคม</span><form:input type="text" maxlength="15" id="m12" path="rmonth12" class="numbersOnly" style="margin-left:-35%;" /><span class="error" style="color: Red; display: none;font-size:0.8em; position: absolute;">* Input digits (0 - 9)</span></div>
 				</div>
 				<div style="clear:both;"></div>
 				<div id="eachMonth">
 					<table>
 						<tbody>
 						</tbody>
 					</table>
 				</div>
 			</div>
 			
 			</form:form>
 			
       		<div id="actionMenu" style="">
 				<input type="button" class="save btn btn-success" onclick="saveTarget()" value="บันทึก"/>
 				<input type="button" class="cancel btn btn-default" onclick="back()" value="ยกเลิก"/>
 			</div>
       	</div>
  </body>
</html>	
