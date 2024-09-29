package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eurovending.dao.CompanyDataDAO;
import org.eurovending.dao.LocationCountersListDAO;
import org.eurovending.dao.LocationCountersRegistersDAO;
import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.LocationListRegisterDAO;
import org.eurovending.dao.LocationMonthYearDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.dao.UserLocationListDAO;
import org.eurovending.pojo.CompanyData;
import org.eurovending.pojo.Location;
import org.eurovending.pojo.User;
import org.eurovending.pojo.UsersLocationList;
import org.eurovending.utils.HallUtils;
import org.eurovending.utils.PasswordUtils;
import org.eurovending.utils.ServiceLoger;
import org.eurovending.utils.ServiceUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
	SuperAdminController sAdminControll = new SuperAdminController();
	public int idLoc = 0 ;
	//listare  homeAdmin
			@RequestMapping(value="home-admin.htm")
			public ModelAndView homeAdmin(HttpServletRequest request,
					HttpServletResponse response, @ModelAttribute("myUser") User myUser,Model model,BindingResult result)
					throws SQLException ,ServletException, IOException, ParseException
					 {
				 ArrayList<User> userList = new ArrayList<User>();
				 ArrayList<User> newUserList = new ArrayList<User>();
				 ArrayList<Location> locationList = new ArrayList<Location>();
				 ArrayList<Location> newLocationList = new ArrayList<Location>();
				 HallUtils hutls = new HallUtils();
				 LocationDAO loc = new LocationDAO();
				 CompanyDataDAO compdao = new CompanyDataDAO();
				 HttpSession session = request.getSession();
				 UserDAO udao = new UserDAO();
			      	String bucDate = sAdminControll.dateTime();
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
				if(getMyUserNameIsOk==true) {
				if((LoginController.getMyUserName().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
					 
			String buttonAddUser = "NotNull";	
			
			String nameUser = udao.getEmailUser(LoginController.getMyUserName()).getFullName();
			locationList = loc.getAllLocation();
			newLocationList = sAdminControll.isLoginTest(locationList);
			String buttonAdd = null;
			if(compdao.verifyCompanyData() == 0) {
				buttonAdd = "Add";
			}
			userList = udao.getUserList();	
			for(User u:userList) {
				
					if(u.getRole().equalsIgnoreCase("ADMIN"))
					newUserList.add(u);	
				
			}
			double totalDayCount = hutls.totalDayContor(newLocationList);
			double totlalMonthCount = hutls.totalMonthContor(newLocationList);
			int totalMachine = hutls.totalMachine(newLocationList);
			 model.addAttribute("buttonAdd",buttonAdd);	
			 model.addAttribute("buttonAddUser",buttonAddUser);	
			 model.addAttribute("nameUser",nameUser);
			 model.addAttribute("europeDateTime",bucDate);
			 model.addAttribute("locationList",newLocationList);
			 model.addAttribute("totalDayCount",totalDayCount);
			 model.addAttribute("totlalMonthCount",totlalMonthCount);
			 model.addAttribute("totalMachine",totalMachine);
				return new ModelAndView("WEB-INF/admin/homeadmin.jsp","model",model);
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
			@RequestMapping(value="admin-user-list.htm")
			public ModelAndView homeAdminUser(Model model)
					throws SQLException ,ServletException, IOException
					 {
				 ArrayList<User> userList = new ArrayList<User>();
				 ArrayList<User> newUserList = new ArrayList<User>();
				 UserDAO udao = new UserDAO();
				 
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
				if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
					
			String buttonAddUser = "NotNull";	
			String buttonAdd = null;
			String nameUser = udao.getEmailUser(LoginController.getMyUserName()).getFullName();
			userList = udao.getUserList();	
			for(User u:userList) {
				if(u.getId()>1) {
					if(u.getRole().equalsIgnoreCase("USER"))
					newUserList.add(u);	
				}
			}
			 
			 model.addAttribute("userList",newUserList);
			 model.addAttribute("nameUser",nameUser);
				return new ModelAndView("WEB-INF/admin/user/userlist.jsp","model",model);
			}
			else {
				LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}
			
			@RequestMapping(value="adduseradmin.htm")
			public ModelAndView addUserAdmin(@ModelAttribute("idUser") User user, 
					Model model,BindingResult result) throws SQLException {
				
				PasswordUtils utilPassword = new PasswordUtils();
				ServiceLoger svl = new ServiceLoger();
				 ArrayList<User> userList = new ArrayList<User>();
				UserDAO udao = new UserDAO();
				 String salt = null ;
		         salt = utilPassword.getSalt(30);
		         String newPassword = utilPassword.generateSecurePassword(user.getPassword(), salt);
		         user.setOperator(LoginController.getMyUserName());
		         user.setPassword(newPassword);
		         user.setSalt(salt);
				 boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
					if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
						userList = udao.getUserList();
						for(User u: userList) {
							if((u.getFullName().equalsIgnoreCase(user.getFullName()))||(u.getEmail().equals(user.getEmail()))){
							String msgError =" Eroare UserName utilizat sau email utilizat!! Schimbati!!! ";	
							model.addAttribute("msgError",msgError);
							return new ModelAndView("WEB-INF/admin/user/usermodal.jsp","model",model);
							}
						}			
						udao.insertUser(user);
						user = udao.getLastUserInsert();						
						model.addAttribute("myUser",user);
			        		return new ModelAndView("redirect:/admin-user-list.htm");
					}
					else {
						 LoginController.isLoginSuperAdmin = false;
						 return new ModelAndView("redirect:/LoginOut.htm");
					}
			}

			@RequestMapping(value="userlocationadmin.htm")
			public ModelAndView userLocationAdmin(@ModelAttribute("idUser") User user, 
					Model model,BindingResult result) throws SQLException {
				ArrayList<Location> locationList = new ArrayList<Location>();
				UserLocationListDAO usrLocListDao = new UserLocationListDAO();
				LocationDAO ldao = new LocationDAO();
				locationList = ldao.getAllLocation();
				UserDAO udao = new UserDAO();
				ArrayList<UsersLocationList> newUserLocList = new ArrayList<UsersLocationList>();
				user = udao.getIdUser(user.getId()); 					
		         user.setOperator(LoginController.getMyUserName());		       
				 boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
					if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
						usrLocListDao.createTableLocationUserLocationList(user.getId());
						newUserLocList = usrLocListDao.getUserLocationList(user.getId());
						ArrayList<UsersLocationList> finalUserLocList = new ArrayList<UsersLocationList>();
						Location location = new Location();
						for(UsersLocationList locl:newUserLocList) {
							location = ldao.getLocation(locl.getIdLocation());
							locl.setId(location.getId());
							locl.setLocName(location.getLocationName());
							finalUserLocList.add(locl);
						}
						
						model.addAttribute("finalUserLocList",finalUserLocList);
						model.addAttribute("locationList",locationList);						
						model.addAttribute("user",user);
			        		return new ModelAndView("WEB-INF/admin/user/userlocationlist.jsp","model",model);
					}
					else {
						 LoginController.isLoginSuperAdmin = false;
						 return new ModelAndView("redirect:/LoginOut.htm");
					}
			}
			@RequestMapping(value="createlocationlistadmin.htm")
			public ModelAndView createLocationListAdmin(@ModelAttribute("userLocList") UsersLocationList userLocList, 
					Model model,BindingResult result) throws SQLException {												
				ServiceUser servUser = new ServiceUser();
				ArrayList<Location> locationList = new ArrayList<Location>();
				UserLocationListDAO usrLocListDao = new UserLocationListDAO();
				LocationDAO ldao = new LocationDAO();
				locationList = ldao.getAllLocation();
				UserDAO udao = new UserDAO();
				User user = new User();
				ArrayList<UsersLocationList> newUserLocList = new ArrayList<UsersLocationList>();
				user = udao.getIdUser(userLocList.getIdUser()); 					
		         user.setOperator(LoginController.getMyUserName());		      
				 boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
					if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
						servUser.insertUserLocation( userLocList);
						newUserLocList = usrLocListDao.getUserLocationList(user.getId());
						ArrayList<UsersLocationList> finalUserLocList = new ArrayList<UsersLocationList>();
						Location location = new Location();
						for(UsersLocationList locl:newUserLocList) {
							location = ldao.getLocation(locl.getIdLocation());
							locl.setId(location.getId());
							locl.setLocName(location.getLocationName());
							finalUserLocList.add(locl);
						}
						model.addAttribute("finalUserLocList",finalUserLocList);
						model.addAttribute("locationList",locationList);						
						model.addAttribute("user",user);
			        		return new ModelAndView("WEB-INF/admin/user/userlocationlist.jsp","model",model);
					}
					else {
						 LoginController.isLoginSuperAdmin = false;
						 return new ModelAndView("redirect:/LoginOut.htm");
					}
			}
			
			//listare  homeAdmin
			@RequestMapping(value="admin-view-location.htm")
			public ModelAndView viewAdminLocation(HttpServletRequest request,
					HttpServletResponse response, @ModelAttribute("id") int idLocation,Model model,BindingResult result)
					throws SQLException ,ServletException, IOException, ParseException
					 {
				String bucDate = sAdminControll.dateTime();
			
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
					return new ModelAndView("WEB-INF/admin/view-location.jsp","model",model);
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
			@RequestMapping(value="admin-list-countLocation.htm")
			public ModelAndView listCountLocation(Model model,@ModelAttribute("id") int idLoc) 
					throws SQLException, ServletException, IOException {
				LocationCountersRegistersDAO locCountRegDao = new LocationCountersRegistersDAO();
				ArrayList<Location> locationList = new ArrayList<Location>();
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
				if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
					locationList = locCountRegDao.getAllLocationListRegisters(idLoc);
					 model.addAttribute("locationList",locationList);
					return new ModelAndView("WEB-INF/admin/location/list-countLocation.jsp","model",model);
				}
				else {
					 LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}
			//view month list
			@RequestMapping(value="admin-view-countList.htm")
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
					return new ModelAndView("WEB-INF/admin/location/view-countList.jsp","model",model);
				}
				else {
					 LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}	
			
			//View company data
			@RequestMapping(value="admin-view-company.htm")
			public ModelAndView viewCompany(Model model) throws SQLException {
				 
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
				if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk=true)) {
					CompanyDataDAO compdao = new CompanyDataDAO();
					ArrayList <CompanyData> myCompanyData = new ArrayList<CompanyData>();
						myCompanyData = compdao.getCompanyDataList();
					
				 model.addAttribute("myCompanyData",myCompanyData);
	
					return new ModelAndView("WEB-INF/admin/viewcompany.jsp","model",model);
				}
				else {
					LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}
			
			//view month list
			@RequestMapping(value="admin-month-list.htm")
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
					return new ModelAndView("WEB-INF/admin/location/monthlist.jsp","model",model);
				}
				else {
					 LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}
			//view month list
			@RequestMapping(value="admin-view-monthAccount.htm")
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
					return new ModelAndView("WEB-INF/admin/location/view-monthAccount.jsp","model",model);
				}
				else {
					 LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}	
}
