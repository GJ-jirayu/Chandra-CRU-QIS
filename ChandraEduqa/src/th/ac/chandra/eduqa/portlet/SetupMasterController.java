package th.ac.chandra.eduqa.portlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import th.ac.chandra.eduqa.form.SysYearForm;
import th.ac.chandra.eduqa.mapper.CustomObjectMapper;
import th.ac.chandra.eduqa.model.SysMonthModel;
import th.ac.chandra.eduqa.model.SysYearModel;
import th.ac.chandra.eduqa.service.EduqaService;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

@Controller("setupMasterController")
@RequestMapping("VIEW")
@SessionAttributes({ "sysYearForm" })
public class SetupMasterController {
	private static final Logger logger = Logger
			.getLogger(SetupMasterController.class);
	@Autowired
	@Qualifier("eduqaServiceWSImpl")
	private EduqaService service;
	
	@Autowired
	private CustomObjectMapper customObjectMapper;

	@InitBinder
	public void initBinder(PortletRequestDataBinder binder,
			PortletPreferences preferences) {
		logger.debug("initBinder");
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("VIEW")
	public String listDetail(PortletRequest request, Model model) {
		String messageCode  = request.getParameter("messageCode");
		
		SysYearForm sysYearForm = null;
		SysYearModel sysYearModel = new SysYearModel();
		SysMonthModel sysMonthModel = new SysMonthModel();
		List<SysMonthModel> sysMonths = service.searchSysMonth(sysMonthModel);
		List<Integer> findMax = new ArrayList<Integer>();
		Map<Integer,Integer> appraisalYears = new HashMap<Integer,Integer>();
		Map<Integer,Integer> appraisalYearsReverse = new TreeMap(Collections.reverseOrder());
		Map<Integer,Integer> setupYears = new HashMap<Integer,Integer>();
		Map<Integer,Integer> setupYearsReverse = new TreeMap(Collections.reverseOrder());
		
		if(sysMonths.size()>0){
			for(SysMonthModel sysMonth : sysMonths){
				appraisalYears.put(sysMonth.getAcademicYear()-1, sysMonth.getAcademicYear()-1);
				appraisalYears.put(sysMonth.getAcademicYear(), sysMonth.getAcademicYear());
				appraisalYears.put(sysMonth.getAcademicYear()+1, sysMonth.getAcademicYear()+1);
				setupYears.put(sysMonth.getAcademicYear(), sysMonth.getAcademicYear());
				findMax.add(sysMonth.getAcademicYear());
			}
			for(Integer i=1;i<=4;i++){
				setupYears.put(Collections.max(findMax)+i,Collections.max(findMax)+i);
			}
			appraisalYearsReverse.putAll(appraisalYears);
			setupYearsReverse.putAll(setupYears);
		}else{
			 Integer year = Calendar.getInstance().get(Calendar.YEAR);
			 year=year+543;
			 appraisalYears.put(year-1, year-1);
			 appraisalYears.put(year, year);
			 appraisalYears.put(year+1, year+1);
			 setupYears.put(year-1,year-1);
			 setupYears.put(year,year);
			 setupYears.put(year+1,year+1);
			 appraisalYearsReverse.putAll(appraisalYears);
			setupYearsReverse.putAll(setupYears);
		}
		List<SysYearModel> configs = service.searchSysYear(sysYearModel);
		SysYearModel config = configs.get(0);
		model.addAttribute("appraisalYears",appraisalYearsReverse);
		model.addAttribute("setupYears", setupYearsReverse);
		
		Map<String,String> months = new LinkedHashMap<String,String>();
		List<String> monthList = Arrays.asList("มกราคม","กุมภาพันธ์","มีนาคม","เมษายน","พฤษภาคม","มิถุนายน","กรกฎาคม","สิงหาคม","กันยายน","ตุลาคม","พฤศจิกายน","ธันวาคม");
		for(String month : monthList){
			months.put(month,month);
		}
		model.addAttribute("months", months);
		
		if (!model.containsAttribute("sysYearForm")) {
			sysYearForm = new SysYearForm();
		} else {
			sysYearForm = (SysYearForm) model.asMap().get("sysYearForm");
		}
		sysYearForm.setSysYearModel(config);
		model.addAttribute("sysYearForm", sysYearForm);
		if(messageCode!=null && !messageCode.equals("")){
			model.addAttribute("messageCode",messageCode);
		}
		return "config/SetMaster";
	}

	@RequestMapping(params="action=doSave")
	public void actionInsert(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("sysYearForm") SysYearForm sysYearForm,
			BindingResult result, Model model) {
		User user = (User) request.getAttribute(WebKeys.USER);
		SysYearModel sysYearM = new SysYearModel();
		sysYearM = sysYearForm.getSysYearModel();
		sysYearForm.getSysYearModel().setCreatedBy(user.getFullName());
		sysYearForm.getSysYearModel().setUpdatedBy(user.getFullName());
		sysYearForm.getSysYearModel().setFirstMonthAcademic(sysYearForm.getSysYearModel().getFirstMonthAcademic());
		sysYearForm.getSysYearModel().setFirstMonthFiscal(sysYearForm.getSysYearModel().getFirstMonthFiscal());
		
		Integer success = 0;
		if(sysYearForm.getSysYearModel().getYearId()==null || sysYearForm.getSysYearModel().getYearId().equals("") ){
			sysYearM.setYearId(null);
			success = service.saveSysYear(sysYearForm.getSysYearModel());
		}else{
			success = service.updateSysYear(sysYearForm.getSysYearModel());
		}
		String msgCode  = "100";
		if(success>0){
			msgCode = "201";
		}else{
			msgCode = "301";
		}
		response.setRenderParameter("messageCode",msgCode);
	}
	
	@ResourceMapping(value="requestSaveSysYear")
	@ResponseBody 
	public void submitConfiguration(ResourceRequest request,ResourceResponse response) throws IOException{
		//User user = (User) request.getAttribute(WebKeys.USER);
		JSONObject json = JSONFactoryUtil.createJSONObject();		
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest =PortalUtil.getOriginalServletRequest(httpReq);
		
		Integer masterYear = Integer.parseInt(normalRequest.getParameter("masterYear"));
		Integer resultYear = Integer.parseInt(normalRequest.getParameter("resultYear"));
		String academicMonth = normalRequest.getParameter("academicMonth");
		String fiscalMonth = normalRequest.getParameter("fiscalMonth");
		
		SysYearModel SysYearModel = new SysYearModel();
		SysYearModel.setAppraisalAcademicYear(masterYear);
		SysYearModel.setMasterAcademicYear(resultYear);
		SysYearModel.setFirstMonthAcademic(academicMonth);
		SysYearModel.setFirstMonthFiscal(fiscalMonth);
		
		Integer status = service.saveSysYear(SysYearModel);
		
		json.put("saveStatus", status.toString());
		response.getWriter().write(json.toString());
	}
}
