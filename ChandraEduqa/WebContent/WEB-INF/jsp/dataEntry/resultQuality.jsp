<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %> 
<%@ page import="javax.portlet.PortletURL" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*" %>
<portlet:actionURL var="doAssignResultQuality">
  <portlet:param name="action" value="doAssignResultQuality"/>
</portlet:actionURL> 
<portlet:actionURL var="doBackToList">
  <portlet:param name="action" value="doBackFromQuality"/>
</portlet:actionURL> 
<portlet:actionURL var="doSaveResultQuality">
  <portlet:param name="action" value="doSaveResultQuality"/>
</portlet:actionURL> 
<portlet:actionURL var="doResultQualityBackToList">
  <portlet:param name="action" value="doResultQualityBackToList"/>
</portlet:actionURL> 
<portlet:actionURL var="doViewEvidence">
  <portlet:param name="action" value="doViewEvidenceQuality"/>
</portlet:actionURL> 
<portlet:actionURL var="doCloseEvidence">
  <portlet:param name="action" value="doCloseEvidenceQuality"/>
</portlet:actionURL> 
<portlet:actionURL var="doSaveEvidence">
    <portlet:param name="action" value="doSaveEvidenceQuality" />      
</portlet:actionURL>
<portlet:resourceURL var="fileDownload" id="fileDownload"></portlet:resourceURL>    
<portlet:resourceURL var="kpiEvidenceFileDelete" id="kpiEvidenceFileDelete" ></portlet:resourceURL>
<portlet:resourceURL var="doSaveAutoSaveResult" id="doSaveAutoSaveResult" ></portlet:resourceURL>

<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap core CSS --> 
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap-select.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap-responsive.min.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/common-element.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.min.css"/>"/>
    <script src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script> 
    <script  src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
    <script  src="<c:url value="/resources/bootstrap/js/bootstrap-select.min.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
  <script  src="<c:url value="/resources/bootstrap/js/bootstrap-typeahead.min.js"/>"></script>
  <script src="<c:url value="/resources/js/confirm-master/jquery.confirm.min.js"/>"></script>

    <script type="text/javascript"> 
        <%-- var dialog,dialog2; --%>
      $( document ).ready(function() {
        $("#evidenceContent").prop('disabled',true);
        //event display evidence content
        validateNumber()
      });
      function validateNumber(){
        var specialKeys = new Array();
          specialKeys.push(8); //Backspace  
          specialKeys.push(46); // period (.)
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
      function actAssignResult(el){
        var value = $(el).parent("td").parent("tr").children("td:nth-child(1)").html();
        $("#resultQualityForm").attr("action","<%=doAssignResultQuality%>");
        $("#resultQualityForm #selectStdId").val(value);
        $("#resultQualityForm").submit();
      }
      function actBack2List(){
        $("#resultQualityForm").attr("action","<%=doBackToList%>");
        $("#resultQualityForm").submit();
      }
      function viewEvidences(el){
        var value = $(el).parent("td").parent("tr").children("td:nth-child(1)").html();
        $("#resultQualityForm #selectStdId").val(value);
        $("#resultQualityForm").attr("action","<%=doViewEvidence%>");
        $("#resultQualityForm").submit();
      }
      function resultSave(){
        $("#assignResultQualityForm").attr("action","<%=doSaveResultQuality%>");
        $("#assignResultQualityForm").submit();
        /* if($('#cdmCdsValue').text().trim() != ""){
          $("#assignResultQualityForm").submit();
        }else{
          $('#resultDetailMessage').html('ไม่ใส่ผลดำเนินงาน');
        } */
      }
      function resultBack(){
        $("#assignResultQualityForm").attr("action","<%=doResultQualityBackToList%>");
        $("#assignResultQualityForm").submit();
      }
      function evidenceSave(){        
        if($('input[name=evidenceType]').is(":checked")){
          if($('input[name="evidenceType"]:checked').val()!=$("#evidenceCheck").val()
              && $("table#evidenceList>tbody>tr").size()>0){
            $("#evidenceMessage").html("ไม่สามารถเปลี่ยนประเภทหลักฐาน ต้องลบรายการหลักฐานออกก่อน");
          }else{
            if($('input[name="evidenceType"]:checked').val()=="L" && $("#urlPath").val()==""){
              $("#evidenceMessage").html("ยังไม่ได้ระบุ URL");
            }else if($('input[name="evidenceType"]:checked').val()=="F" && $("#attachFile").val()==""){
              $("#evidenceMessage").html("ยังไม่ได้ระบุไฟล์");
            }else{
                $("#evidenceQualityForm").attr("action","<%=doSaveEvidence%>");
                $("#evidenceQualityForm").submit();
            }
          }
        }else{
          $("#evidenceMessage").html("ยังไม่ได้ระบุประเภทหลักฐาน");
        }
      }
      function evidenceDel(el){
          var lineObj = $(el).parent("td").parent("tr");
        var evidenceId = lineObj.children("td:nth-child(2)").html();
        var evidenceDisplayName = lineObj.children("td:nth-child(3)").html();
        $.confirm({
             text: "ยืนยันการลบหลักฐาน \"".concat(evidenceDisplayName, "\""),
             title: "ลบรายการหลักฐาน",
             confirm: function(button) {
              evidenceDeleteRequest(lineObj,evidenceId);
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
      function evidenceDeleteRequest(obj,evidenceId){
        var orgId = $("#evidenceOrgId").val();
        var monthId = $("#evidenceMonthId").val();
        var criteriaId = $("#evidenceCriteriaId").val();
        var kpiId = $("#evidenceKpiId").val();
        $.ajax({
            dataType: "json",
            url:"<%=kpiEvidenceFileDelete%>",
            data: { evidenceId: evidenceId , kpiId:kpiId ,orgId:orgId, monthId:monthId, criteriaId:criteriaId },
            success:function(data){
              if(data["header"]["success"]>0){
                obj.remove();
                $("#evidenceMessage").html("ลบหลักฐานสำเร็จ");
              }
              else{
                $("#evidenceMessage").html(data["header"]["errorMessage"]);
              }
            } 
          });
      }
      function evidenceUpload(){
        var filename = $('input[type=file]#fileupload').val().split('\\').pop();
        //alert("name="+filename+"//size="+document.getElementById("fileupload").files[0].size);
        if(filename != "" && filename != null){
          $("#evidenceQualityForm").submit();
        }
      }
      function evidenceClose(){
        $("#evidenceQualityForm").attr("action","<%=doCloseEvidence%>");
        $("#evidenceQualityForm").submit();
      }
      
      function doSaveAutoSaveResult(el){
        var value = $(el).parent("td").parent("tr").children("td:nth-child(1)").html();
        $("#resultQualityForm #selectStdId").val(value);

        var orgId = $("div#cdsModal input#resultOrgId").val();
        var kpiId = $("div#cdsModal input#resultKpiId").val();
        var monthId = $("div#cdsModal input#resultMonthId").val();
        var selectStdId = $("div#cdsModal input#selectStdId").val()
        var actionFlag;

        if($(el).is(":checked")){
          actionFlag = "true";
        }else{
          actionFlag = "false";
        }       
        
        $.ajax({
            dataType: "json",
            url:"<%=doSaveAutoSaveResult%>",
            data: { orgId:orgId, kpiId:kpiId, monthId:monthId, selectStdId:selectStdId, actionFlag:actionFlag },
            success:function(data){
              if(data["msgCode"] == "1"){
                $("#resultQualityForm").attr("action","<%=doAssignResultQuality%>");
                $("#resultQualityForm").submit();
              }
            } 
          });
      }
      
    </script>
 
    <style type="text/css">
      input[type="text"]{ margin-bottom: 0px; }
      .widthCustom { width:95% }
      legend.legends{
        font-size: 16px; 
        /* width: auto;
        margin-bottom: 0px;
        font-size: 16px;
        border-style: none; */
      }
      div.boxAct{
      padding: 20px 20px 20px 20px;
      border: thin solid #CDCDCD;
      border-radius: 10px;
      display: block;
    }
    table.listTable, table#cdsListDetail{
        background-color:#FFFFFF;
        border:1px solid #999999;
        overflow:hidden;
        width:100%;
        padding-top:10px;
        font-size:14px;
      }
      table.listTable tbody td, table#cdsListDetail tbody td {
      border: none;
      padding: 1px 2px 2px 2px;
    }
    table.listTable thead th, 
    table#cdsListDetail thead th, 
    table#evidenceList thead th {
        text-align:center;
        border-color:#acacac;
        border-width:0px 1px 1px 1px;
        border-style:inset;
        background: linear-gradient(white, #efefef,#e2e2e2,#f7f7f7);
      }
      table.listTable tbody tr:nth-child(2n) td,
      table#cdsListDetail tbody tr:nth-child(2n) td,
      table#evidenceList tbody tr:nth-child(2n) td { 
        background-color:rgba(244,244,244,1); 
      }
      table.tbKpiResultModal td:nth-child(1){ width:30%; }
      table.tbKpiResultModal td:nth-child(2){ width:70%; }
      
      table.tbEvidence_Flag{ width:100% }
      table.tbEvidence_Flag td:nth-child(1){ display:none; }
      table.tbEvidence_Flag td:nth-child(2){ display:none; }
      table.tbEvidence_Flag td:nth-child(3){ width: 90% }
      table.tbEvidence_Flag td:nth-child(4){ width: 10% }
      
      table#evidenceList tbody{ overflow-y: auto; overflow-x: hidden; }
      table#evidenceList th:nth-child(1), table#evidenceList td:nth-child(1){ display: none; }
      table#evidenceList th:nth-child(2), table#evidenceList td:nth-child(2){ display: none; }
      table#evidenceList th:nth-child(3), table#evidenceList td:nth-child(3){ width: 85%; }
      table#evidenceList th:nth-child(4), table#evidenceList td:nth-child(4){ width: 15%; }
      
    button.btnPag {
        background-color: Transparent;
        background-repeat:no-repeat;
        border: none;
        cursor:pointer;
        overflow: hidden;
        outline:none;
    }
    .bs-example{ margin: 20px; }
      .panel{ border-color:#1C1C1C; }
      .boxParam {
      float: left;
      margin: 5px;
    }
    .cds-detail-box{
      padding-right: 5px;
      min-height: 250px;
      border: thin solid #C0C0C0; 
      padding: 10px 10px 10px 10px;
      min-height: 150px;
    } 
    .bs-example{ margin:0; }
    .panel.panel-default{ border: 1px solid #C0C0C0; margin-top: 5px; }
    .panel-heading{ margin: 5px; }
    .modal-content{
      width: 900px;
      margin-left: -150px;
      background-color: white;
    }
    #cdmCdsValue{ width: 50px; }
    #filePath{ width: 150px; }
    #cdsListDetail tr>td:nth-child(1),#cdsListDetail tr>th:nth-child(1){ display:none;}
    .responseMessage{color:red;}
    </style>
  </head>

<body>
  <div class="box">
  
  <!-- CDS RESULT Modal -->
  <form:form  id="resultQualityForm" modelAttribute="resultQualityForm" method="post"  name="resultQualityForm" action="${doAssignResultQuality}" enctype="multipart/form-data">
  <div id="cdsModal" class="quantity"> 
    <form:input type="hidden" id="resultOrgId" path="orgId"/>
    <form:input type="hidden" id="resultMonthId" path="monthId"/>
    <form:input type="hidden" id="resultKpiId" path="kpiId"/>
    <form:input type="hidden" id="selectCdsId" path="selectCdsId"/>
    <form:input type="hidden" id="selectResultId" path="selectResultId"/>
    <form:input type="hidden" id="selectStdId" path="selectStdId"/>
    <fieldset class="fieldsets">
      <legend class="legends"> บันทึกผลการดำเนินงานและรายการหลักฐาน </legend>
      ชื่อตัวบ่งชี้: &nbsp<span id="diaKpiName">${resultQualityForm.kpiName}</span><br />
      <c:choose>
        <c:when test="${resultQualityForm.uom=='ร้อยละ'}">
          เป้าหมายตัวบ่งชี้: &nbsp<span>${resultQualityForm.uom} &nbsp ${resultQualityForm.targetValue}</span><br />  
        </c:when>
        <c:otherwise>
          เป้าหมายตัวบ่งชี้: &nbsp<span>${resultQualityForm.targetValue} &nbsp ${resultQualityForm.uom}</span><br />
        </c:otherwise>
      </c:choose>
      <table id="cdsListDetail" style="width:100%">
        <thead>
          <tr>
            <th width="5%">รหัส</th>
            <!-- <th width="10%">รายงานผล</th> -->
            <th width="5%">มี</th>
            <th width="50%">เกณฑ์การประเมิน</th>
            <th width="20%">ผลการดำเนินงาน</th>
            <th width="10%">รายการหลักฐาน</th>
          </tr>
        </thead>
        <tbody>
          <c:if test="${not empty resultQualityForm.resultList}"> 
          <c:forEach items="${resultQualityForm.resultList}" var="result" varStatus="loop"> 
                      <tr> 
                        <td>${result.standardId}</td>
                        <!-- <td style="text-align:center;"><button class="icon" onClick="actAssignResult(this)"><img  src="<c:url value="/resources/images/edited.png"/>"></button></td> -->
                        <td style="text-align:center;">
                        <c:choose>
                    <c:when test="${result.hasResult=='1'}">
                      <input type="checkbox" onclick='doSaveAutoSaveResult(this)' checked />
                    </c:when>    
                    <c:otherwise>
                      <input type="checkbox" onclick='doSaveAutoSaveResult(this)' />
                   </c:otherwise>
                </c:choose>
                        </td>
                        <td>${result.standardName}</td>  
                        <td>${result.cdsValue}</td>
                        <td style="text-align:center;"> 
                        <c:choose>
                    <c:when test="${result.hasResult=='1'}">
                      <button class="icon" onClick="viewEvidences(this)"><img  src="<c:url value="/resources/images/attach.png"/>"></button>
                    </c:when>    
                    <c:otherwise>
                <!--   <button class="icon" onClick="viewEvidences(this)"><img  src="<c:url value="/resources/images/attach.png"/>"></button> -->
                    </c:otherwise>
                </c:choose>
                        </td>  
              </tr>
          </c:forEach>
          </c:if>
        </tbody>
      </table>
      <br/>
      <div style="text-align:center"><input type="button" class="back" onclick="actBack2List()" value="ย้อนกลับ"/></div>
    </fieldset>
  </div>
  </form:form>

  <!-- หน้านี้ถูกตัดออกเพราะความไม่สะดวกของผู้ใช้งาน โดยจะไปเรียกใช้งาน doSaveAutoSaveResult() แทน-->
  <!-- <c:if test="${not empty assignResultQualityForm}"> 
  <div id="cdsDetailModal" style="min-height:200px;width:80%;border:1px solid #bcbcbc;padding:10px 10px 10px 10px" >
    <form:form  id="assignResultQualityForm" modelAttribute="assignResultQualityForm" method="post"  name="assignResultQualityForm" action="${doAddResult}" enctype="multipart/form-data">
      <form:input type="hidden" id="detailKpiId" path="kpiId" />
        <form:input type="hidden" id="detailOrgId" path="orgId" />
        <form:input type="hidden" id="detailCdsId" path="cdsId"/>
        <form:input type="hidden" id="detailMonthId" path="monthId"/>
        <form:input type="hidden" id="detailCriteriaId" path="criteriaId"/> 
    ชื่อตัวบ่งชี้: <span id="cdmKpiName">${assignResultQualityForm.kpiName}</span> <br/>
    <span id="resultDetailMessage" class="responseMessage">${assignResultQualityForm.message}</span>
      <fieldset>
        <legend class="legends"> รายงานผล </legend>
        <div> <form:checkbox path="actionFlag"/> <span id="cdmCdsName">${assignResultQualityForm.criteriaDesc}</span> </div>
      </fieldset>
      <c:if test="${not empty assignResultQualityForm.cdsId }">
      <fieldset>
        <legend class="legends"> ข้อมูลพื้นฐาน </legend>
        <span id="cdmCdsName">${assignResultQualityForm.cdsName}</span> 
        <br/>
        <span id="cdmDate">${assignResultQualityForm.yearName} ${assignResultQualityForm.yearNo}</span>
        <span>เดือน ${assignResultQualityForm.monthName} :</span>
        <form:input id="cdmCdsValue" type="text" path="cdsValue" class="numbersOnly" disabled="true" /> 
      </fieldset>
      </c:if>
      <br/> 
      <div style="width:100%; text-align:center; padding-top: 5px">
        <input type="button" id="resultSavebtn" class="save"  value="บันทึก" onclick="resultSave()" />
        <input type="button" id="resultCancelbtn" class="cancel" value="ยกเลิก" onclick="resultBack()" />
      </div>
    </form:form>
  </div>
  </c:if> -->
  
  <c:if test="${not empty evidenceQualityForm}"> 
  
    <div id="evidenceContent" > 
      <form:form name="evidenceQualityForm" id="evidenceQualityForm" modelAttribute="evidenceQualityForm" action="" enctype="multipart/form-data" method="post"> 
        <form:input type="hidden" id="evidenceOrgId" path="orgId"/>
        <form:input type="hidden" id="evidenceKpiId" path="kpiId"/>
        <form:input type="hidden" id="evidenceCriteriaId" path="criteriaId"/>
        <form:input type="hidden" id="evidenceMonthId" path="monthId"/>
        <div class="cds-detail-box">
          <fieldset>
            <legend class="legends"> รายการหลักฐาน </legend>
          </fieldset>
          <span id="evidenceMessage" class="responseMessage">${evidenceQualityForm.message}</span>
          <div>
            <span>ชื่อตัวบ่งชี้:</span><span >${evidenceQualityForm.kpiName}</span>
          </div>
          <div style="width:10%;float:left;padding-top:10px;">
            <span>ประเภทหลักฐาน</span>
          </div>
          <div style="width:40%;float:left;padding-top:10px;">  
            <form:input type="hidden" id="evidenceCheck" path="evidenceCheck" />    
            <div>
              <form:radiobutton id="evidenceR" path="evidenceType" name="evidenceType" value="R" disabled="true"/>
              Standard Report: 
            </div>        
            <div style="padding-top: 5px">
              <form:radiobutton id="evidenceL" path="evidenceType" name="evidenceType" value="L" /> 
              URL:
              <form:input type="text" id="urlPath" path="urlPath" placeholder="https://"/> 
            </div>      
            <div style="padding-top: 5px">
              <form:radiobutton  id="evidenceF" path="evidenceType" name="evidenceType" value="F"  /> 
              Attached File
              <form:input id="attachFile" path="fileData" type="file" placeholder="Location Path."/>
            </div>
          </div>
          <div style="clear:both;float:left;width:10%;">
           <span>รายการหลักฐาน</span></div>
           <table id="evidenceList" style="width: 80%;"> 
            <thead>
              <tr>
                <th> cds result detail id (hide) </th>
                <th>evidence id (hide)</th>
                <th>รายการหลักฐาน</th>
                <th>ลบ</th>
              </tr>
            </thead>
            <tbody>
              <c:if test="${not empty evidenceQualityForm.evidences}"> 
              <c:forEach items="${evidenceQualityForm.evidences}" var="evidence" varStatus="loop"> 
                      <tr>
                        <td>${evidence.resultDetailId}</td>
                        <td>${evidence.evidenceId}</td>
                        <td>
                        <c:choose>
                          <c:when test="${evidenceQualityForm.evidenceType=='F'}">
                            <a href="${evidence.evidencePath}">${evidence.evidenceFileName}</a>
                          </c:when>
                          <c:when test="${evidenceQualityForm.evidenceType=='L'}">
                            <a href="${evidence.evidencePath}">${evidence.evidenceFileName}</a>
                          </c:when>
                          <c:otherwise>
                            ${evidence.evidencePath}
                          </c:otherwise>
                        </c:choose>
                        </td>
                        <td style="text-align:center"><img src="<c:url value="/resources/images/delete.png"/>" width="22" height="22" onClick="evidenceDel(this)" style="cursor: pointer;"></td>
                      </tr>
                    </c:forEach>
                    </c:if>
            </tbody>
          </table>
          <div style="text-align:center;padding-top:10px;">
            <input type="button" class="save" onclick="evidenceSave()" value="บันทึก" />
            <input type="button" class="cancel" onclick="evidenceClose()" value="ยกเลิก"/>
          </div>
        </div>
      </form:form>
    </div> <!-- end right -->   
  </c:if>
</div>
</body>
</html> 