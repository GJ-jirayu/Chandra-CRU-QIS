package th.ac.chandra.eduqa.portlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import th.ac.chandra.eduqa.form.CdsForm;
import th.ac.chandra.eduqa.mapper.CustomObjectMapper;
import th.ac.chandra.eduqa.mapper.ResultService;
import th.ac.chandra.eduqa.model.CdsModel;
import th.ac.chandra.eduqa.model.DbConnModel;
import th.ac.chandra.eduqa.model.DbQueryModel;
import th.ac.chandra.eduqa.model.SysYearModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.Paging;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

@Controller("cdsController")
@RequestMapping("VIEW")
public class CdsController {
	private static final Logger logger = Logger.getLogger(CdsController.class);
	@Autowired
	@Qualifier("eduqaServiceWSImpl")
	private EduqaService service;
	
	private Integer getCurrentYear(){
		SysYearModel sysYearModel = new SysYearModel();
		List<SysYearModel> sysYears = service.searchSysYear(sysYearModel);
		try{
			sysYearModel = sysYears.get(0);
			return sysYearModel.getMasterAcademicYear();
		}catch(Exception err){
			return 9999;
		}
	}
	
	@Autowired
	private CustomObjectMapper customObjectMapper;

	private String msg = "start";
	@InitBinder
	public void initBinder(PortletRequestDataBinder binder, PortletPreferences preferences) {
		logger.debug("initBinder");
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
/*
		final String[] ALLOWED_FIELDS={"researchGroupM.researchGroupId","researchGroupM.createdBy","researchGroupM.createdDate",
				"researchGroupM.groupCode","researchGroupM.permissions","researchGroupM.updatedBy",
				"researchGroupM.updatedDate","researchGroupM.groupTh","researchGroupM.groupEng","mode", "command","keySearch"};
			*/
		//	binder.setAllowedFields(ALLOWED_FIELDS);		
	}
	 @RenderMapping()
	public String listRows(PortletRequest request,Model model){
		CdsForm cdsForm=null;
		if (!model.containsAttribute("cdsForm")) {
			cdsForm = new CdsForm();
			model.addAttribute("cdsForm",	cdsForm);
		}else{
			cdsForm = (CdsForm)model.asMap().get("cdsForm");
		}
		CdsModel cdsModel = new CdsModel();
		String keySearch=cdsForm.getKeySearch();
		cdsModel.setKeySearch(keySearch);
		Paging page = new Paging();
		page.setPageNo(1);
		cdsModel.setPaging(page);
		ResultService rs = service.searchCds(cdsModel);
		List<CdsModel> cdsList = (List<CdsModel>) rs.getResultObjList();
		model.addAttribute("listCds", cdsList );
		model.addAttribute("pageNo",1);
		model.addAttribute("PageCur",1);
		model.addAttribute("lastPage",rs.getResultPage());
		return "master/cds";
	}
	
	@RenderMapping( params="render=backToList")
	public String showList(PortletRequest request,
			@RequestParam("messageDesc") String messageDesc,
			@RequestParam("messageCode") String messageCode, 
			Model model){
		CdsForm cdsForm=null;
		if (!model.containsAttribute("cdsForm")) {
			cdsForm = new CdsForm();
			model.addAttribute("cdsForm",	cdsForm);
		}else{
			cdsForm = (CdsForm)model.asMap().get("cdsForm");
		}
		CdsModel cdsModel = new CdsModel();
		String keySearch=cdsForm.getKeySearch();
		cdsModel.setKeySearch(keySearch);
		Paging page = new Paging();
		page.setPageNo(1);
		cdsModel.setPaging(page);
		ResultService rs = service.searchCds(cdsModel);
		List<CdsModel> cdsList = (List<CdsModel>) rs.getResultObjList();
		model.addAttribute("listCds",cdsList);
		model.addAttribute("pageNo",1);
		model.addAttribute("PageCur",1);
		model.addAttribute("lastPage",rs.getResultPage());
		model.addAttribute("messageCode", messageCode);
		model.addAttribute("messageDesc", messageDesc);
		return "master/cds";
	}
	
	@RequestMapping(params = "action=doPageSize")
	public void actionPageSize(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("cdsForm") CdsForm CdsForm,
			BindingResult result, Model model) {
		String pageSize = CdsForm.getPageSize();
		String keySearch = CdsForm.getKeySearch();
		response.setRenderParameter("render", "listPage_2");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", "1");
		response.setRenderParameter("pageSize", pageSize);
		response.setRenderParameter("messageDesc", "");
		response.setRenderParameter("messageCode", "");
	}
	@RequestMapping("VIEW")
	@RenderMapping(params = "render=listPage_2")
	public String RenderListPage(@RequestParam("keySearch") String keySearch,
			@RequestParam("pageNoStr") String pageNoStr,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("messageDesc") String messageDesc,
			@RequestParam("messageCode") String messageCode, 
			Model model) {		
		Paging page = new Paging(Integer.parseInt(pageNoStr), 10, "ASC");
		CdsModel CdsModel =new CdsModel ();
		CdsModel.setPaging(page);
		CdsModel.getPaging().setPageSize(pageSize);
		ResultService rs = service.searchCds(CdsModel);
		List<CdsModel> cdsList = (List<CdsModel>) rs.getResultObjList();
		model.addAttribute("listCds", cdsList);
		model.addAttribute("pageNo",pageNoStr);
		model.addAttribute("PageCur", pageNoStr);
		model.addAttribute("lastPage", rs.getResultPage());
		model.addAttribute("keySearch", keySearch);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("messageCode", messageCode);
		model.addAttribute("messageDesc", messageDesc);
		return "master/cds";
	}
	
	
	@RequestMapping(params="action=doNew") 
	public void newDetail(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("cdsForm") CdsForm cdsForm,BindingResult result,Model model){
		//CdsModel cds = service.findCdsById(1);
		response.setRenderParameter("render", "displayDetail");
		response.setRenderParameter("pageAction", "new");
	}
	@RequestMapping(params="action=doEdit") 
	public void editDetail(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("cdsForm") CdsForm cdsForm,BindingResult result,Model model){
		response.setRenderParameter("render", "displayDetail");
		response.setRenderParameter("cdsId",String.valueOf( cdsForm.getCdsModel().getCdsId() ) ) ;
		response.setRenderParameter("pageAction", "edit");
	}
	@RequestMapping("VIEW")
	@RenderMapping(params="render=displayDetail")
	public String displayDetail(PortletRequest request,Model model){
		// level
		Map<Integer,String> levelList = new HashMap<Integer,String>();
		levelList.put(1,"สถาบัน");
		levelList.put(2,"คณะ");
		levelList.put(3,"หลักสูตร");
		model.addAttribute("levelList", levelList);
		
		//con dbType
		Map<String,String> dbTypeList = new HashMap<String,String>();
		dbTypeList.put("mysql","mysql");
		dbTypeList.put("oracle","oracle");
		model.addAttribute("dbTypeList", dbTypeList);
		// conn
		DbConnModel connModel = new DbConnModel();
		List<DbConnModel> conns = service.searchConn(connModel);
		model.addAttribute("listConn", conns);
		
		// fieldList
		HashMap<String,String> qValueList =  new HashMap<String,String>();
		HashMap<String,String> qUniList =  new HashMap<String,String>();
		HashMap<String,String> qFacultyList =  new HashMap<String,String>();
		HashMap<String,String> qCourseList =  new HashMap<String,String>();
		HashMap<String,String> qDetailList =  new HashMap<String,String>();
		HashMap<String,String> qYearList =  new HashMap<String,String>();
		HashMap<String,String> qMonthList =  new HashMap<String,String>();
		//add default
		qValueList.put("", "none");
		qUniList.put("", "none");
		qFacultyList.put("", "none");
		qCourseList.put("", "none");
		qDetailList.put("", "none");
		qYearList.put("", "none");
		qMonthList.put("", "none");
		
		//CdsForm
		CdsForm cdsForm=null;
		if (!model.containsAttribute("cdsForm")) {
			cdsForm = new CdsForm();
		}else{
			cdsForm = (CdsForm)model.asMap().get("cdsForm");
		}
		// check mode
		if(request.getParameter("cdsId")!=null && !request.getParameter("cdsId").equals("")){ // edit mode
			Integer cdsId = Integer.parseInt(request.getParameter("cdsId"));
			ResultService rs = service.findCdsById(cdsId);
			CdsModel cds = (CdsModel) rs.getResultObj(); 
			cdsForm.setCdsModel(cds);
			
			//set select fieldname to fieldList
			if(cds.getValueField()!=null && !cds.getValueField().equals("")){ 
				qValueList.put(cds.getValueField(),cds.getValueField() );
			}
			if(cds.getUniversityField()!=null && !cds.getUniversityField().equals("")){ 	
				qUniList.put(cds.getUniversityField(),cds.getUniversityField());
			}
			if(cds.getFacultyField()!=null && !cds.getFacultyField().equals("")){ 
				qFacultyList.put(cds.getFacultyField(),cds.getFacultyField());
			}
			if(cds.getCourseField()!=null && !cds.getCourseField().equals("")){ 
				qCourseList.put(cds.getCourseField(),cds.getCourseField());
			}
			if(cds.getDetailField()!=null && !cds.getDetailField().equals("")){ 
				qDetailList.put(cds.getDetailField(),cds.getDetailField());
			}
			if(cds.getYearField()!=null && !cds.getYearField().equals("")){ 
				qYearList.put(cds.getYearField(),cds.getYearField());
			}
			if(cds.getMonthField()!=null && !cds.getMonthField().equals("")){ 
				qMonthList.put(cds.getMonthField(), cds.getMonthField());
			}
		}
		model.addAttribute("cdsForm",	cdsForm);
		// field list 
		model.addAttribute("qValueList",qValueList);
		model.addAttribute("qUniList",qUniList);
		model.addAttribute("qFacultyList",qFacultyList);
		model.addAttribute("qCourseList",qCourseList);
		model.addAttribute("qDetailList",qDetailList);
		model.addAttribute("qYearList",qYearList);
		model.addAttribute("qMonthList",qMonthList);
		model.addAttribute("pageAction", request.getParameter("pageAction"));
		
		return "master/cdsDetail";
	}
	
	@RequestMapping(params = "action=doDelete")
	public void actionDelete(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("cdsForm") CdsForm cdsForm,BindingResult result,Model model) { 
			ResultService rs = service.deleteCds(cdsForm.getCdsModel() );
			response.setRenderParameter("render", "backToList");
			response.setRenderParameter("messageCode", rs.getMsgCode());
			response.setRenderParameter("messageDesc", rs.getMsgDesc());
	}
	@RequestMapping(params = "action=doSearch") 
	public void actionSearch(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("cdsForm") CdsForm cdsForm,BindingResult result,Model model) { 
		
		String keySearch=cdsForm.getKeySearch();
		response.setRenderParameter("render", "listSearch");
		response.setRenderParameter("keySearch", keySearch);
	}
	@RequestMapping("VIEW")
	@RenderMapping(params = "render=listSearch")
	public String RenderSearch(@RequestParam("keySearch") String keySearch,Model model){
			CdsModel CdsModel =new CdsModel ();
			CdsModel.setKeySearch(keySearch);
			Paging page = new Paging();  // default pageNo = 1
			CdsModel.setPaging(page);
			// convert Model -> form 
			ResultService rs = service.searchCds(CdsModel);
			List<CdsModel> cdsList = (List<CdsModel>) rs.getResultObjList();
			model.addAttribute("listCds",cdsList);
			model.addAttribute("pageNo",1);
			model.addAttribute("lastPage",rs.getResultPage());
			return "master/cds";
	}

	@RequestMapping(params = "action=doListPage") 
	public void actionListPage(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("cdsForm") CdsForm CdsForm,BindingResult result,Model model) { 
		/*String pageNo = CdsForm.getPageNo().toString();
		String keySearch = CdsForm.getKeySearch();
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("pageNoStr",pageNo);*/
		
		String pageNo = CdsForm.getPageNo().toString();
		String pageSize = CdsForm.getPageSize();
		String keySearch = CdsForm.getKeySearch();
		response.setRenderParameter("render", "listPage_2");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", pageNo);
		response.setRenderParameter("pageSize", pageSize);
		response.setRenderParameter("messageDesc", "");
		response.setRenderParameter("messageCode", "");
	}
	@RenderMapping(params = "render=listPage")
	public String RenderListPage(@RequestParam("pageNoStr") String pageNoStr,Model model){
		CdsModel CdsModel =new CdsModel ();
		Paging page = new Paging(Integer.parseInt(pageNoStr),10,"ASC");
		CdsModel.setPaging(page);
		ResultService rs = service.searchCds(CdsModel);
		List<CdsModel> cdsList = (List<CdsModel>) rs.getResultObjList();
		model.addAttribute("listCds",cdsList);
		model.addAttribute("pageNo",pageNoStr);
		model.addAttribute("PageCur", pageNoStr);
		model.addAttribute("lastPage",rs.getResultPage());
		return "master/cds";
	}
	
	// detai
	@RequestMapping(params = "action=doSave") 
	public void SaveCds(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("cdsForm") CdsForm CdsForm,BindingResult result,Model model) { 
		User user = (User) request.getAttribute(WebKeys.USER);
		// require checkMode
		CdsModel cds = CdsForm.getCdsModel();
		cds.setAcademicYear(getCurrentYear());		
		
		ResultService rs = new ResultService();
		if(cds.getCdsId()==null  ){
			cds.setCreatedBy(user.getFullName());
			cds.setUpdatedBy(user.getFullName());
			rs = service.saveCds(cds);
		}
		else{
			cds.setUpdatedBy(user.getFullName());
			rs = service.updateCds(cds);
		}
		response.setRenderParameter("render", "backToList");
		response.setRenderParameter("messageCode", rs.getMsgCode());
		response.setRenderParameter("messageDesc", rs.getMsgDesc());
	}
	
	@RequestMapping(params = "action=doShowList") 
	public void doShowList(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("cdsForm") CdsForm CdsForm,BindingResult result,Model model) { 
		//response.setRenderParameter("render", "backToList");
	}
	
	// ####################### conn ajax #######################
	@ResourceMapping(value="connGetList")
	@ResponseBody 
	public void connectionList(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		//String id=request.getParameter("p1");
		JSONObject json = JSONFactoryUtil.createJSONObject();
		DbConnModel conn = new DbConnModel();
		conn.setKeySearch(null);
		//WARNING** return al least 1 if null not return any thing
		List<DbConnModel> results =service.searchConn(conn);
                JSONArray connList = 	JSONFactoryUtil.createJSONArray();
                for (DbConnModel result : results) {
                	JSONObject connJSON = JSONFactoryUtil.createJSONObject();
                	connJSON.put("id", result.getConnId());
                	connJSON.put("name", result.getConnName() );
                	connJSON.put("dbName", result.getDbName());
                	connJSON.put("userName", result.getUsername());
                	connList.put(connJSON);
                }
		json.put("lists", connList);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="connGetDetail")
	@ResponseBody 
	public void connectionDetail(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		//WARNING convert liferay object to normal
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		String connId = PortalUtil.getOriginalServletRequest(httpReq).getParameter("id");
		
		//WARNING** must return at least 1 record if not  return null 
		JSONObject json = JSONFactoryUtil.createJSONObject();
		
		DbConnModel conn =service.findConnById( Integer.parseInt(connId));
     	JSONObject connJSON = JSONFactoryUtil.createJSONObject();
         connJSON.put("id", conn.getConnId());
         connJSON.put("name", conn.getConnName() );
         connJSON.put("dbName", conn.getDbName() );
         connJSON.put("dbType", conn.getDbType() );
         connJSON.put("host", conn.getHostName());
         connJSON.put("port", conn.getPort() );
         connJSON.put("username", conn.getUsername());
         connJSON.put("password", conn.getPassword());
         connJSON.put("createBy", conn.getCreatedBy());
         connJSON.put("createDate", conn.getCreatedDate());
		json.put("conn", connJSON);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="connSave")
	@ResponseBody 
	public void conectionSave(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		User user = (User) request.getAttribute(WebKeys.USER);
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		//WARNING** return al least 1 if null not return any thing
		JSONObject json = JSONFactoryUtil.createJSONObject();
		DbConnModel conn = new DbConnModel();
		conn.setConnName( normalRequest.getParameter("name"));
		conn.setDbType( normalRequest.getParameter("dbType"));
		conn.setDbName( normalRequest.getParameter("dbName"));
		conn.setHostName(  normalRequest.getParameter("host") );		
		if(normalRequest.getParameter("academicYear")!=null && normalRequest.getParameter("academicYear")!=""){
			conn.setAcademicYear( Integer.parseInt(normalRequest.getParameter("academicYear")));			
		}else{ 
			conn.setAcademicYear(getCurrentYear()); 
		}		
		if( normalRequest.getParameter("port")!=null && normalRequest.getParameter("port")!=""){
			conn.setPort( Integer.parseInt(normalRequest.getParameter("port") )) ;
		}
		conn.setUsername(normalRequest.getParameter("username"));
		conn.setPassword(normalRequest.getParameter("password"));
		
		Integer status = null;
		if( normalRequest.getParameter("id")!=null && normalRequest.getParameter("id")!=""){
			String createStr = normalRequest.getParameter("createDate");
			Timestamp timestamp = Timestamp.valueOf(createStr.substring(0, 19));
			conn.setConnId( Integer.parseInt(normalRequest.getParameter("id")) );
			conn.setCreatedBy(normalRequest.getParameter("createBy"));
			conn.setCreatedDate(timestamp);
			conn.setUpdatedBy(user.getFullName());
			status = service.updateConn(conn);
		}
		else{			
			conn.setCreatedBy(user.getFullName());
			conn.setUpdatedBy(user.getFullName());
			status = service.saveConn(conn);
		}
		json.put("status",status);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="connDelete")
	@ResponseBody 
	public void conectionDelete(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		//WARNING** return al least 1 if null not return any thing
		JSONObject json = JSONFactoryUtil.createJSONObject();
		DbConnModel conn = new DbConnModel();
		Integer status = null;
		if( normalRequest.getParameter("id")!=null && normalRequest.getParameter("id")!=""){
			conn.setConnId( Integer.parseInt(normalRequest.getParameter("id")) );
			status = service.deleteConn(conn);
		}
		json.put("status",status);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="exDataQuery")
	@ResponseBody 
	public void exQuery(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		//WARNING** return al least 1 if null not return any thing
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		 JSONArray connList = 	JSONFactoryUtil.createJSONArray();
		String status = "";
		Integer size = 0;
		if( normalRequest.getParameter("connId")!=null && normalRequest.getParameter("connId")!=""){
			
			DbQueryModel q = new DbQueryModel();
			DbConnModel conn = new DbConnModel();
			conn.setConnId( Integer.parseInt(normalRequest.getParameter("connId")));
			q.setConn(conn);
			q.setQuery( normalRequest.getParameter("query") );
			List<List<String>> results = service.resultPreviewQuery(q);
			status = service.getResultMessage();
			if(results!=null){
				size = results.size();
				if(size>0){
		             for (List<String> result : results) {
		            	 JSONArray rec = 	JSONFactoryUtil.createJSONArray();
		            	 for(String col : result){
		            		 rec.put(col);
		            	 }
		             	connList.put(rec);
		             }
				}else{
				}
			}//end result!=null
		}else{
			status = "invalid parameters";
		}
		content.put("lists",connList);
		header.put("status", status);
		header.put("size", size);
		json.put("header",header);
		json.put("content", content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
}
