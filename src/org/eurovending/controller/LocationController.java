package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eurovending.dao.LocationCountersListDAO;
import org.eurovending.dao.LocationCountersRegistersDAO;
import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.LocationListRegisterDAO;
import org.eurovending.dao.LocationMonthYearDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.pojo.Location;
import org.eurovending.pojo.User;
import org.eurovending.utils.HallUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@MultipartConfig(maxFileSize = 16177215)
public class LocationController {
	
	
	
	 @RequestMapping(value ="editlocation.htm")
	 public ModelAndView editLocation(HttpServletRequest request,@ModelAttribute("id") Location location,Model model,BindingResult result)
	    		throws SQLException ,ServletException, IOException
	    {
		 SuperAdminController sadm = new SuperAdminController();
		 ArrayList<Location> newLocationList = new ArrayList<Location>();
		 LocationDAO locDao = new LocationDAO();
		 UserDAO udao = new UserDAO();
		 Location getLocation = new Location();
	      	String bucDate = sadm.dateTime();   
	      	 HttpSession session = request.getSession();
	      	 
	      	boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if(getMyUserNameIsOk==true) {
			if((LoginController.getMyUserName().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
				locDao.updateLocationName(location);
				getLocation = locDao.getLocation(location.getId());
				String buttonAddUser = "NotNull";
				int id = location.getId();
				 model.addAttribute("id",id);	
				return new ModelAndView("redirect:/view-location.htm","model",model);
			}
			else {
				LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
				}
				else {
					LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}

	    	}
	 

	 @RequestMapping(value ="contorcorection.htm")
	 public ModelAndView contorCorection(HttpServletRequest request,@ModelAttribute("locationMacAddress") Location location,Model model,BindingResult result)
	    		throws SQLException ,ServletException, IOException
	    {
		 SuperAdminController sadm = new SuperAdminController();
		 ArrayList<Location> newLocationList = new ArrayList<Location>();
		 LocationDAO locDao = new LocationDAO();
		 UserDAO udao = new UserDAO();
		 Location getLocation = new Location();
	      	String bucDate = sadm.dateTime();   
	      	 HttpSession session = request.getSession();
	      	boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if(getMyUserNameIsOk==true) {
			if((LoginController.getMyUserName().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
				locDao.updateLocationContor(location);
				getLocation = locDao.getLocationByMacAddr(location.getLocationMacAddress());
				String buttonAddUser = "NotNull";
				int id = getLocation.getId();
				 model.addAttribute("id",id);	
				return new ModelAndView("redirect:/view-location.htm","model",model);
			}
			else {
				LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
				}
				else {
					LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}

	    	}
	 
	//view month list
		@RequestMapping(value="month-list.htm")
		public ModelAndView monthList(Model model) 
				throws SQLException, ServletException, IOException {
			
			
			boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
				
				LocalDate currentdate = LocalDate.now();
				Month currentMonth = currentdate.getMonth();	
			   String actualYear =Integer.toString(currentdate.getYear());
			  String actualMonth =currentMonth.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro") );
				LocationListRegisterDAO usrLocListDao = new LocationListRegisterDAO();
				ArrayList<Location> locationList = new ArrayList<Location>();
				ArrayList<Location> newLocationList = new ArrayList<Location>();
				locationList = usrLocListDao.getAllLocationListRegisters();
				for(Location l : locationList) {
					if(!l.getMonth().equalsIgnoreCase(actualMonth)||(!l.getYear().equalsIgnoreCase(actualYear))) {
						newLocationList.add(l);
					}
				}
				 model.addAttribute("locationList",newLocationList);
				return new ModelAndView("WEB-INF/superadmin/location/monthlist.jsp","model",model);
			}
			else {
				 LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}
		
		//view month list
		@RequestMapping(value="view-monthAccount.htm")
		public ModelAndView viewMonthAccount(Model model,@ModelAttribute("month") String month,@ModelAttribute("year") String year) 
				throws SQLException, ServletException, IOException {
			LocationMonthYearDAO locCountListDao = new LocationMonthYearDAO();
			ArrayList<Location> locationList = new ArrayList<Location>();
			boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
				locationList = locCountListDao.getAllLocation(month, year);
				SuperAdminController sc = new SuperAdminController();
				 HallUtils hutls = new HallUtils();
				double totlalMonthCount = hutls.totalMonthContor(locationList);
				 model.addAttribute("month",month);
				 model.addAttribute("year",year);
				 model.addAttribute("totlalMonthCount",totlalMonthCount);				
				model.addAttribute("locationList",locationList);
				return new ModelAndView("WEB-INF/superadmin/location/view-monthAccount.jsp","model",model);
			}
			else {
				 LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}	
		
		
		
		//view month list
				@RequestMapping(value="list-countLocation.htm")
				public ModelAndView listCountLocation(Model model,@ModelAttribute("id") int idLoc) 
						throws SQLException, ServletException, IOException {
					LocationCountersRegistersDAO locCountRegDao = new LocationCountersRegistersDAO();
					ArrayList<Location> locationList = new ArrayList<Location>();
					boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
					if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
						locationList = locCountRegDao.getAllLocationListRegisters(idLoc);
						 model.addAttribute("locationList",locationList);
						return new ModelAndView("WEB-INF/superadmin/location/list-countLocation.jsp","model",model);
					}
					else {
						 LoginController.isLoginSuperAdmin = false;
						 return new ModelAndView("redirect:/LoginOut.htm");
					}
					}
				//view month list
				@RequestMapping(value="view-countList.htm")
				public ModelAndView viewCountList(Model model,@ModelAttribute("id") int idLoc,@ModelAttribute("month") String month,@ModelAttribute("year") String year) 
						throws SQLException, ServletException, IOException {
					LocationCountersListDAO locCountListDao = new LocationCountersListDAO();
					ArrayList<Location> locationList = new ArrayList<Location>();
					boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
					if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
						locationList = locCountListDao.getAllLocationCountersList(idLoc, month, year);
						SuperAdminController sc = new SuperAdminController();
						String bucDate = sc.dateTime();
						 model.addAttribute("europeDateTime",bucDate);
						model.addAttribute("locationList",locationList);
						return new ModelAndView("WEB-INF/superadmin/location/view-countList.jsp","model",model);
					}
					else {
						 LoginController.isLoginSuperAdmin = false;
						 return new ModelAndView("redirect:/LoginOut.htm");
					}
					}	
	 @RequestMapping(value ="deletelocation.htm")
	 public ModelAndView deleteLocation(HttpServletRequest request,@ModelAttribute("id") Location location,Model model,BindingResult result)
	    		throws SQLException ,ServletException, IOException
	    {
		 SuperAdminController sadm = new SuperAdminController();
		 ArrayList<Location> newLocationList = new ArrayList<Location>();
		 LocationDAO locDao = new LocationDAO();
		 UserDAO udao = new UserDAO();
		 Location getLocation = new Location();
	      	String bucDate = sadm.dateTime();   
	      	 HttpSession session = request.getSession();
	      
	      	boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if(getMyUserNameIsOk==true) {
			if((LoginController.getMyUserName().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
				locDao.deleteRowLocation(location.getId());;
				getLocation = locDao.getLocation(location.getId());
				String buttonAddUser = "NotNull";
				int id = location.getId();
				 model.addAttribute("id",id);	
				return new ModelAndView("redirect:/home-superadmin.htm","model",model);
			}
			else {
				LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
				}
				else {
					LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}

	    	}
	 
}