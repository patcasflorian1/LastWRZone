package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eurovending.dao.AccountLocationDAO;
import org.eurovending.dao.CompanyDataDAO;
import org.eurovending.dao.LocationCountersListDAO;
import org.eurovending.dao.LocationCountersRegistersDAO;
import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.dao.UserLocationListDAO;
import org.eurovending.pojo.CompanyData;
import org.eurovending.pojo.Location;
import org.eurovending.pojo.User;
import org.eurovending.pojo.UsersLocationList;
import org.eurovending.utils.HallUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsersController {
	SuperAdminController sAdminControll = new SuperAdminController();
	public int idLoc = 0 ;
	 @RequestMapping(value ="view_account_user.htm")
	 public ModelAndView viewAccountUser(HttpServletRequest request,@ModelAttribute("id") int id,Model model,BindingResult result)
	    		throws SQLException ,ServletException, IOException
	    {
		 idLoc = id;
		 Location location = new Location();
		 SuperAdminController sadm = new SuperAdminController();
		 ArrayList<Location> newLocationList = new ArrayList<Location>();
		 AccountLocationDAO locDao = new AccountLocationDAO();
		
	      	String bucDate = sadm.dateTime();   
	      	 HttpSession session = request.getSession();
	      	 
	      	boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if(getMyUserNameIsOk==true) {
			if((LoginController.getMyUserName().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
				locDao.createTableAccountLocation(id);
				newLocationList = locDao.getAllAccountLocation(idLoc); 
					
				 model.addAttribute("id",idLoc);	
				 model.addAttribute("locationList",newLocationList);
					return new ModelAndView("WEB-INF/user/view_account_user.jsp","model",model);
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
		@RequestMapping(value="list-countLocation-user.htm")
		public ModelAndView listCountLocationUser(Model model,@ModelAttribute("id") int idLoc) 
				throws SQLException, ServletException, IOException {
			LocationCountersRegistersDAO locCountRegDao = new LocationCountersRegistersDAO();
			ArrayList<Location> locationList = new ArrayList<Location>();
			boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
				locationList = locCountRegDao.getAllLocationListRegisters(idLoc);
				 model.addAttribute("locationList",locationList);
				return new ModelAndView("WEB-INF/user/list-countLocation-user.jsp","model",model);
			}
			else {
				 LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}
		//view month list
		@RequestMapping(value="view-countList-user.htm")
		public ModelAndView viewCountListUser(Model model,@ModelAttribute("id") int idLoc,@ModelAttribute("month") String month,@ModelAttribute("year") String year) 
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
				return new ModelAndView("WEB-INF/user/view-countList-user.jsp","model",model);
			}
			else {
				 LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}	
		
	//listare  homeSuperAdmin
			@RequestMapping(value="home-user.htm")
			public ModelAndView homeUser(HttpServletRequest request,
					HttpServletResponse response, @ModelAttribute("myUser") User myUser,Model model,BindingResult result)
					throws SQLException ,ServletException, IOException, ParseException
					 {
				
				UserLocationListDAO userLocationListDAO =new UserLocationListDAO();
				 ArrayList<UsersLocationList> userLocationList = new ArrayList<UsersLocationList>();
				 ArrayList<Location> locationList = new ArrayList<Location>();
				 ArrayList<Location> newLocationList = new ArrayList<Location>();
				 ArrayList<Location> finalLocationList = new ArrayList<Location>();
				 HallUtils hutls = new HallUtils();
				 LocationDAO locDao = new LocationDAO();
				 HttpSession session = request.getSession();
				 UserDAO udao = new UserDAO();
			      	String bucDate = sAdminControll.dateTime();
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
				if(getMyUserNameIsOk==true) {
				if((LoginController.getMyUserName().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
					myUser = udao.getEmailUser(LoginController.getMyUserName());
					userLocationList = userLocationListDAO.getUserLocationList(myUser.getId());	
					locationList = locDao.getAllLocation() ;
					for(int i=0;i<userLocationList.size();i++) {
						for(Location l: locationList) {
							if(userLocationList.get(i).getIdLocation()==l.getId()) {
								newLocationList.add(l);
							}
						}
					}
			String buttonAddUser = "NotNull";	
			String buttonAdd = null;
			String nameUser = udao.getEmailUser(LoginController.getMyUserName()).getFullName();
			finalLocationList = sAdminControll.isLoginTest(newLocationList);
			
			
			double totalDayCount = hutls.totalDayContor(newLocationList);
			double totlalMonthCount = hutls.totalMonthContor(newLocationList);
			int totalMachine = hutls.totalMachine(newLocationList);
			 model.addAttribute("buttonAddUser",buttonAddUser);	
			 model.addAttribute("nameUser",nameUser);
			 model.addAttribute("europeDateTime",bucDate);
			 model.addAttribute("locationList",finalLocationList);
			 model.addAttribute("totalDayCount",totalDayCount);
			 model.addAttribute("totlalMonthCount",totlalMonthCount);
			 model.addAttribute("totalMachine",totalMachine);
				return new ModelAndView("WEB-INF/user/homeuser.jsp","model",model);
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

			
			//listare  homeSuperAdmin
			@RequestMapping(value="view-user-location.htm")
			public ModelAndView viewUserLocation(HttpServletRequest request,
					HttpServletResponse response, @ModelAttribute("id") int idLocation,Model model,BindingResult result)
					throws SQLException ,ServletException, IOException, ParseException
					 {
				String bucDate = sAdminControll.dateTime();
						// int idLoc = 0;
				//System.out.println("Id Loc "+idLocation);
				// idLoc = Integer.parseInt(idLocation);
				LocationDAO ldao = new LocationDAO();
				Location getLocation = new Location();
				getLocation = ldao.getLocation(idLocation);
				 HttpSession session = request.getSession();		
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
				if(getMyUserNameIsOk==true) {
				if((LoginController.getMyUserName().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
					getLocation = ldao.getLocation(idLocation);
					String buttonAddUser = "NotNull";
					     model.addAttribute("buttonAddUser",buttonAddUser);	
						 model.addAttribute("nameUser",LoginController.getMyUserName());
						 model.addAttribute("europeDateTime",bucDate);
						 model.addAttribute("getLocation",getLocation);
					return new ModelAndView("WEB-INF/user/view-user-location.jsp","model",model);
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
 
			//View company data
			@RequestMapping(value="user-view-company.htm")
			public ModelAndView viewCompany(Model model) throws SQLException {
				 
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
				if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk=true)) {
					CompanyDataDAO compdao = new CompanyDataDAO();
					ArrayList <CompanyData> myCompanyData = new ArrayList<CompanyData>();
						myCompanyData = compdao.getCompanyDataList();
					
				 model.addAttribute("myCompanyData",myCompanyData);
	
					return new ModelAndView("WEB-INF/user/viewcompany.jsp","model",model);
				}
				else {
					LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}

	
}
