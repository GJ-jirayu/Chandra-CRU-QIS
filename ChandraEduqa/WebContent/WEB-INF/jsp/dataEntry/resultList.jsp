<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %> 
<%@ page import="javax.portlet.PortletURL" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*" %>
<%@ taglib prefix="chandraFn" uri="http://localhost:8080/web/function" %>

<%-- <portlet:actionURL var="listTableInsert"> 
  <portlet:param name="action" value="doInsert"/>
</portlet:actionURL> 
<portlet:actionURL var="listTableEdit">
  <portlet:param name="action" value="doEdit"/>
</portlet:actionURL> 
<portlet:actionURL var="listTableDelete">
  <portlet:param name="action" value="doDelete"/>
</portlet:actionURL> 
<portlet:actionURL var="listTableSearch">
  <portlet:param name="action" value="doSearch"/>
</portlet:actionURL> 
<portlet:actionURL var="listTableListPage">
  <portlet:param name="action" value="doListPage"/>
</portlet:actionURL>  --%>
<portlet:actionURL var="doShowResultQuantity">
  <portlet:param name="action" value="doShowResultQuantity"/>
</portlet:actionURL> 
<portlet:actionURL var="doShowResultQuality">
  <portlet:param name="action" value="doShowResultQuality"/>
</portlet:actionURL>
<portlet:actionURL var="doSubmitFilter">
  <portlet:param name="action" value="doSubmitFilter"/>
</portlet:actionURL> 
<portlet:resourceURL var="doSearchOrg" id="doSearchOrg" ></portlet:resourceURL>
<portlet:resourceURL var="requestSearchOrgId" id="requestSearchOrgId" ></portlet:resourceURL>
<portlet:resourceURL var="requestOrgFaculty" id="requestOrgFaculty" ></portlet:resourceURL>
<portlet:resourceURL var="requestOrgCourse" id="requestOrgCourse" ></portlet:resourceURL>
<portlet:resourceURL var="dofindOrgByOrgId" id="dofindOrgByOrgId" ></portlet:resourceURL>
<portlet:resourceURL var="dofindOrgByUserName" id="dofindOrgByUserName" ></portlet:resourceURL>

<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="<c:url value="/resources/css/common-element.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.min.css"/>"/>
    <script src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script> 
    <script  src="<c:url value="/resources/js/smoothness/jquery-ui-1.9.1.custom.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/smoothness/jquery-ui-1.9.1.custom.min.css"/>"/>
  <script src="<c:url value="/resources/js/confirm-master/jquery.confirm.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/override-portlet-aui.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/override-jqueryui.css"/>"/>

    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
  <script src="<c:url value="/resources/bootstrap/js/bootstrap-typeahead.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" type="text/css" />
   
   
    <script type="text/javascript"> 

      $( document ).ready(function() {
        pageMessage();        
        $("#accordionList").accordion({
          heightStyle: "content",
          collapsible: true
        });

        ToggleEnableSelection();
        renderParameterCtrl(getUserRoleLevelID());
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

      function getUserRoleLevelID(){
        var userRoleLevel;
        $.ajax({ 
          dataType:'json',
          url: "<%=dofindOrgByUserName%>",
          async: false,
          data: { },
          success: function(data){
            userRoleLevel = data["userRoleId"][0]["levelId"];
          }
        });
        return userRoleLevel;
      }

      function renderParameterCtrl(userLevelId){
        var paramLevel = $('#paramLevel').val();
        var paramUniversity  =  $("#paramUniversity");
        var paramFaculty  =  $("#paramFaculty");
        var paramCourse = $("#paramCourse");

        //ตรวจสอบสิทธิ์ของผู้ใชงานและทำการส้ราง Parameter ตามสิทธิ์
        if(userLevelId == 1){
          if(paramLevel == 1){
            //Do not thing

          }else if(paramLevel == 2){
            //Generate paramFaculty and set defalut value
            ParamChange(paramUniversity, 'university');
            if(${currentFaculty} != 0){
              paramFaculty.val(${currentFaculty});
            }

          }else if(paramLevel == 3){
            //Generate paramFaculty and set defalut value
            ParamChange(paramUniversity, 'university');
            if(${currentFaculty} != 0){
              paramFaculty.val(${currentFaculty});
            }

            //Generate paramCoures and set defalut value
            ParamChange($('select#paramFaculty'), 'faculty');
            if(${currentCourse} != 0){
              paramCourse.val(${currentCourse});
            }
          }

        }else if(userLevelId == 2){ 
          $('select#paramLevel option[value=1]').prop("disabled", true);
          if(paramLevel == 2){
            //Generate paramFaculty and set defalut value
            ParamChange(paramUniversity, 'university');
            if(${currentFaculty} != 0){
              paramFaculty.val(${currentFaculty});
            }

          }else if(paramLevel == 3){
            //Generate paramFaculty and set defalut value
            ParamChange(paramUniversity, 'university');
            if(${currentFaculty} != 0){
              paramFaculty.val(${currentFaculty});
            }

            //Generate paramCoures and set defalut value
            ParamChange($('select#paramFaculty'), 'faculty');
            if(${currentCourse} != 0){
              paramCourse.val(${currentCourse});
            }
          }

        }else if(userLevelId==3){
          $('select#paramLevel option[value=1]').prop("disabled", true);
          $('select#paramLevel option[value=2]').prop("disabled", true);         
          $.ajax({ 
              dataType:'json',
              url: "<%=dofindOrgByOrgId%>",
              data: { 'orgId': $('input#idenOrgId').val() },
              success: function(data){ 
                //Generate paramFaculty             
                facultyOpt = $("<option value=\""+data["facultyCourseList"][0]["facultyCode"]
                  +"\"></option>").html(data["facultyCourseList"][0]["facultyName"]);
                paramFaculty.empty().append(facultyOpt);

                //Generate paramCourse
                courseOpt = $("<option value=\""+data["facultyCourseList"][0]["courseCode"]
                  +"\"></option>").html(data["facultyCourseList"][0]["courseName"]);
                paramCourse.empty().append(courseOpt);
              }
            });
        }
      }

      function ToggleEnableSelection(){
        var uni  =  $("#paramUniversity");
        var fac  =  $("#paramFaculty");
        var cou = $("#paramCourse");
        var lv = $('#paramLevel').val();
        if(lv==1){ 
          uni.prop("disabled",false); 
          fac.prop("disabled",true); 
          cou.prop("disabled",true); 
        }else if(lv==2){ 
          uni.prop("disabled",false);
          fac.prop("disabled",false); 
          cou.prop("disabled",true); 
        }else if(lv==3){ 
          uni.prop("disabled",false); 
          fac.prop("disabled",false); 
          cou.prop("disabled",false); 
        }
      }

      function ParamLevelChange(el){
        ToggleEnableSelection();

        var lv = $('#paramLevel').val();
        if(lv==1){
          $("#paramFaculty").val(0);
          $("#paramCourse").val(0);
          $('select#paramFaculty option').attr('disabled', 'disabled');
          $('select#paramCourse option').attr('disabled', 'disabled');
        }else if(lv==2){          
          $('select#paramCourse option').attr('disabled', 'disabled');
          ParamChange($('select#paramUniversity'), 'university');
        }else if(lv==3){ 
          ParamChange($('select#paramUniversity'), 'university');
          ParamChange($('select#paramFaculty'), 'faculty');
        }
      }

      function ParamChange(el, changeType){

        var sups = ["university","faculty","course"];
        var value = $(el).val();
        var elUniversity  = $("#paramUniversity");
        var elFaculty  = $("#paramFaculty");
        var elCouse = $("#paramCourse");

        if(changeType == sups[0]){ // university change
          $.ajax({ 
            dataType:'json',
            url: "<%=requestOrgFaculty%>",
            async: false,
            data: { 'levelId': value , 'university':elUniversity.val()  } ,
            success: function(data){
              GenParamFacultyList(elFaculty,data["lists"]);
              var x = [];
              GenParamCourseList(elCouse,x);
            }
          });
        }else if(changeType == sups[1]){ // faculty change
          $.ajax({ 
            dataType:'json',
            url: "<%=requestOrgCourse%>",
            data: { 'levelId': value , 'university':elUniversity.val(), 'faculty': elFaculty.val()  } ,
            async: false,            
            success: function(data){
            //  alert(JSON.stringify(data));
              if($('#paramLevel').val() == "3"){
                GenParamCourseList(elCouse,data["lists"]);

                //Remove class redBorder if not null value    
                if(elCouse.val() != null){
                  elCouse.removeClass("redBorder");
                } 
              }
            }
          });
        }else if(changeType == sups[2]){ 
          //Remove class redBorder if not null value  
          if(elCouse.val() != null){
            elCouse.removeClass("redBorder");
          }
        }
      } 

    function GenParamFacultyList(target,data){
      //  var target = $('#'+elFaculty);
        target.empty();
        var opt;
        for(var i=0; i<data.length; i++){
          opt = $("<option value=\'"+data[i]["id"]+"\'></option>").html(data[i]['name']);
          target.append(opt);
        }
      }     
    function GenParamCourseList(target,data){
      //  var target = $('#'+elCourse);
        target.empty();
        var opt; //= $("<option value='0'></option>").html("");
        target.append(opt);
        for(var i=0;i<data.length;i++){
          if(!(jQuery.isEmptyObject(data[i]))){
            opt = $("<option value=\""+data[i]['id']+"\"></option>").html(data[i]['name']);
            target.append(opt);
          }         
        } 
      }     
    
    function SearchOrgId(levelMode){
      /* levelMode = U:สถาบัน, F:คณะ, C:หลักสูตร */
      var levelVal = $('#paramLevel').val() || '';
      var facultyVal = $('#paramFaculty').val() || '';
        var couseVal = $('#paramCourse').val() || '';
        var orgId;
        //console.log('valLevel:'+levelVal+', valFaculty:'+facultyVal+', valCouse:'+couseVal);
      $.ajax({ 
          dataType:'json',
          url: '<%=requestSearchOrgId%>',
          data: { 'levelMode': levelMode,'levelId': levelVal, 'facultyCode': facultyVal, 'couseCode': couseVal} ,
          success: function(data){
            for(var i=0; i<1;i++){
              orgId = "<input type='text' id='paramOrg' value='"+data['orgIdLists'][i]['id']+"' style='display:none;'/>" ;
              $('span#appanOrgId').empty().append(orgId);
              }
          }
        });
    }
    
    function actSearchOrg(el,level){
        //var types = ["univerisity","faculty","course"];
        //var orgType = types[parseInt(level)-1 ];
        var uniId  = $("#paramUniversity").val();
        var facId  = $("#paramFaculty").val();
        var couId = $("#paramCourse").val()
        $.ajax({
            dataType: "json",
            url:"<%=doSearchOrg%>",
            data: { "level":level , "university":uniId,"faculty":facId,"course":couId },
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
    function actResult(el){
      var kpiId = parseInt($(el).parent('td').parent('tr').children('td:nth-child(1)').html());
        var criType = parseInt($(el).parent('td').parent('tr').children('td:nth-child(2)').html());
      $('#kpiResultForm #selectKpiId').val(kpiId);
        $('#kpiResultForm #selectCriteriaType').val(criType);
        if(criType==1){
          $('#kpiResultForm').attr("action","<%=doShowResultQuantity%>");
        }else{
          $('#kpiResultForm').attr("action","<%=doShowResultQuality%>");
        }
      $('#kpiResultForm').submit();
    }
    function actionSubmitFilter(){
      var paramLevel = $('select#paramLevel');
      var paramUniversity = $('select#paramUniversity');
      var paramFaculty = $('select#paramFaculty');
      var paramCourse = $('select#paramCourse');

      //ตรวจสอบค่าว่างของ parameter 
      if(paramLevel.val() == 3){
        if(paramCourse.val() != null){
          $('#kpiResultForm').attr("action","<%=doSubmitFilter%>");
          $('#kpiResultForm').submit();
        }else{
          paramCourse.addClass("redBorder");
        }
      }else{
        $('#kpiResultForm').attr("action","<%=doSubmitFilter%>");
        $('#kpiResultForm').submit();
      }

        //$('#kpiResultForm').attr("action","<%=doSubmitFilter%>");
      //$('#kpiResultForm').submit();
    }

    </script>
 
    <style type="text/css">
    /*fieldset.fieldsets{
        border: thin solid #CDCDCD; 
      } */
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
            
      table.listTable th:nth-child(1){ width:10%; } table.listTable td:nth-child(1){ text-align: center;}
      table.listTable th:nth-child(2){ width:10%; }
      table.listTable th:nth-child(3){ width:30%; }
      table.listTable th:nth-child(4){ width:10%; }
      table.listTable th:nth-child(5){ width:10%; }
      table.listTable th:nth-child(6){ width:10%; }
      table.listTable th:nth-child(7){ width:10%; }
      table.listTable th:nth-child(8){ width:10%; } table.listTable td:nth-child(8){ text-align: center;}
      table.listTable th:nth-child(9), table.listTable td:nth-child(9){ width:0%; display:none;}
      table.listTable th:nth-child(10), table.listTable td:nth-child(10){ width:0%; display:none;}
      
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
    .cds-detail-box{
      padding-right: 5px;
      min-height: 250px;
      float: left;
    } 
    .cds-detail-box-sub{
      border: thin solid #C0C0C0; 
      padding: 5px;
      min-height: 250px;
    }
    .bs-example{ margin:0; }
  
    #cdmCdsValue{ width: 50px; }
    #filePath{ width: 150px; }
    .rectred{ background-color:#FF0000;}
    .rectgreen{ background-color:#00FF00;}
    #paramMain .boxFilter>span{ font-size:105%;display:inline-block; text-align:right;}

    .redBorder{
      border-color: #66afe9;
        box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(255, 44, 44, 0.7);
        outline: 0 none;
    }
    </style>
  </head>

<body>

  <input type="hidden" id="messageMsg" value="${messageCode}" > 
  <div id="msgAlert" style="display:none">
      <button type="button" class="close" data-dismiss="alert">x</button>
      <span id="headMsg"> </span> ${messageDesc} 
  </div>

  <div class="boxPage">
    <!-- <input id="facultyCodeAfterSubmit" value=""></input> -->
    
    <form:form  id="kpiResultForm" modelAttribute="kpiResultForm" method="post"  name="kpiResultForm" action="${formActionSubmitFilter}" enctype="multipart/form-data">
        
    <!-- Parameter -->
    <div id="paramMain" style="display:inline-block; border:1px solid #C0C0C0; border-radius:5px;margin:5px 5px 5px 5px;padding:10px 6px 10px 6px;width:97%;">
      <div  style="">
        <form:input type="hidden" id="idenOrgId" path="identify.orgId" />
        <form:input type="hidden" id="monthId" path="monthId" />
        <div class="boxFilter span3" >
          <span>ปี พ.ศ.:</span>
          <form:select id="paramYear"  path="calendarYear" class="filterYear input-small">
            <form:options items="${years}" />         
          </form:select>
        </div>
        <div class="boxFilter span2">
          <span>เดือน:</span>
          <form:select id="paramMonth" class="filterMonth input-small"  path="monthNo" >
              <form:options items="${months}" />
          </form:select>
        </div>
        <div class="boxFilter span2" >
          <span>กลุ่มตัวบ่งชี้:</span>
          <form:select id="paramGroup"  class="filterGroup input-small" path="groupId">
            <form:options items="${groups}" />
          </form:select>
        </div>
        <div class="boxFilter span3">
          <span>ระดับตัวบ่งชี้:</span>
          <form:select id="paramLevel" class="filterLevel input-small" path="identify.level" onchange="ParamLevelChange(this)">
                <form:options items="${levels}" />
            </form:select>
        </div>
        <div class="boxFilter span3" >
          <span>สถาบัน:</span>
          <form:select id="paramUniversity" class="filterUniversity input-large"  path="identify.university" onchange="ParamChange(this,'university')">
                <!-- <form:option value="0" label=""/> -->
                <form:options items="${universitys}" />
            </form:select>
        </div>
        <div class="boxFilter span4">
          <span>คณะ:</span>
          <form:select id="paramFaculty" class="filterFaculty input-xlarge"  path="identify.faculty" onchange="ParamChange(this,'faculty')">
                <!-- <form:option value="" label=""/> -->
                <!-- <form:options items="${facultys}" /> -->
            </form:select>
        </div>
        <div class="boxFilter span4">
          <span>หลักสูตร:</span>
          <form:select id="paramCourse" path="identify.course"  class="filterCourse input-xlarge" onchange="ParamChange(this,'course')">
                <!-- <form:option value="" label=""/> -->
                <!-- <form:options items="${courses}" /> -->
            </form:select>
        </div>
      </div>
      <div style="float:right;">
          <input type="button" id="searchData" class="save"value="เรียกดูข้อมูล" onClick="actionSubmitFilter()" />
      </div>
    </div> <!-- end paramMain -->
     
     <br/>
     
    <!-- Container -->
    <div class="bs-example">
      <form:input type="hidden" id="selectKpiId" path="kpiId" />
      <form:input type="hidden" id="selectOrgId" path="orgId" />
      <form:input type="hidden" id="selectCriteriaType" path="criteriaType" />
      
      <div id="accordionList" class="panel-group">
        <c:if test="${not empty kpiResultForm.resultList}"> 
            <c:forEach items="${kpiResultForm.resultList}" var="accordion" varStatus="acLoop"> 
            <h3>${accordion.structureName}</h3>
            <div>
              <table  class="tableGridLv hoverTable">
                <thead>
                  <tr>
                    <th style="display:none">รหัสตัวบ่งชี้</tืh>
                    <th style="display:none">รหัสตัวบ่งชี้</th>
                    <th>สถานะตัวบ่งชี้</th>
                    <th>กลุ่มตัวบ่งชี้</th>
                    <th>ชื่อตัวบ่งชี้</th>
                    <th>ประเภทปฏิทิน</th>
                    <th>ช่วงเวลา</th>
                    <th>หน่วยวัด</th>
                    <th>เป้าหมาย</th>
                    <th class="column-center">ผลการดำเนินงาน</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${accordion.kpiResults}" var="kpiResult" varStatus="loop"> 
                      <tr> 
                        <td style="display:none">${kpiResult.kpiId}</td>
                        <td style="display:none">${kpiResult.criteriaTypeId}</td>
                        <td class="column-center">
                          <c:choose>
                      <c:when test="${kpiResult.hasResult=='1'}">
                        <span class="rectgreen" >&nbsp;&nbsp;&nbsp;&nbsp;</span>
                      </c:when>    
                      <c:otherwise>
                        <span class="rectred" >&nbsp;&nbsp;&nbsp;&nbsp;</span>
                      </c:otherwise>
                  </c:choose>
                        </td>
                        <td>${chandraFn:nl2br(kpiResult.kpiGroupName)}</td>  
                        <td>${chandraFn:nl2br(kpiResult.kpiName)}</td>  
                        <td>${chandraFn:nl2br(kpiResult.calendarTypeName)}</td> 
                        <td>${chandraFn:nl2br(kpiResult.periodName)}</td>
                        <td>${chandraFn:nl2br(kpiResult.kpiUomName)}</td>  
                        <td>${kpiResult.targetValue}</td>
                        <td class="column-center"><button class="icon" onClick="actResult(this)"><img  src="<c:url value="/resources/images/edited.png"/>"></button>
                    </td>
              </tr>
              </c:forEach>
            </tbody>
              </table>
            </div> <!-- end 1 accordion -->
            </c:forEach>          
                </c:if>
      </div> <!-- end accordian list -->
     
    </div>
    </form:form>
  </div> <!-- End div box -->
  
</body>
</html> 
   