package org.eurovending.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.LocationListRegisterDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.dao.UserLocationListDAO;
import org.eurovending.pojo.Location;
import org.eurovending.pojo.LocationStatus;
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
public class SuperAdminController {

	public ArrayList<Location> isLoginTest(ArrayList<Location> locationList){
		ArrayList<Location> newLocationList = new ArrayList<Location>();
		// ZoneDateTime ett = ZoneDateTime.now(ZoneId.of("Europe/Bucharest"));
		 LocalDateTime now = LocalDateTime.now();
		 // convert LocalDateTime to ZonedDateTime, with default system zone id
	      ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
	      String paymentDate =zonedDateTime.toString();			
			 String regex = "[T.]";
			String myArray[] = paymentDate.split(regex); 
			 paymentDate = myArray[0]+" "+myArray[1];
			 String hour[] = myArray[1].split(":");
			 int minute = Integer.parseInt(hour[1]);
			 LocationStatus online = LocationStatus.OnLine;
			 LocationStatus offline = LocationStatus.OffLine;
			
			 for(Location l:locationList) {
					String str = l.getPaymentDate();
			        String[] arrOfStr = str.split(" ");
			        l.setOnlineDate(arrOfStr[0]);
			        l.setPaymentDate(arrOfStr[1]);
			        String locationHour[] = arrOfStr[1].split(":");
			        String hourLoc = locationHour[0];
			        int minuteLoc = Integer.parseInt(locationHour[1]);
			        if((arrOfStr[0].equalsIgnoreCase(myArray[0]))&&(hourLoc.equalsIgnoreCase(hour[0]))&&((minute-minuteLoc)<5)) {
			        	l.setIsLogin(online.toString());
			        }else {
			        	l.setIsLogin(offline.toString());
			        	l.setSignalLevel(0);
			        }
					newLocationList.add(l);
			 }
	      
		return newLocationList;
	}
	
	public String dateTime() {
		 DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
			// ZoneDateTime ett = ZoneDateTime.now(ZoneId.of("Europe/Bucharest"));
			 LocalDateTime now = LocalDateTime.now();
			 // convert LocalDateTime to ZonedDateTime, with default system zone id
		      ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
		      
				String paymentDate =zonedDateTime.toString();
		      // convert LocalDateTime to ZonedDateTime, with specified zoneId
		      ZonedDateTime europeDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Bucharest"));
		      
		      	String bucDate = (format.format(europeDateTime)).toString();
		return bucDate;
	}
	//listare  homeSuperAdmin
		@RequestMapping(value="home-superadmin.htm")
		public ModelAndView homeSuperAdmin(HttpServletRequest request,
				HttpServletResponse response, @ModelAttribute("myUser") User myUser,Model model,BindingResult result)
				throws SQLException ,ServletException, IOException, ParseException
				 {
			 ArrayList<User> userList = new ArrayList<User>();
			 ArrayList<User> newUserList = new ArrayList<User>();
			 ArrayList<Location> locationList = new ArrayList<Location>();
			 ArrayList<Location> newLocationList = new ArrayList<Location>();
			 HallUtils hutls = new HallUtils();
			 LocationDAO loc = new LocationDAO();
			 HttpSession session = request.getSession();
			 UserDAO udao = new UserDAO();
		      	String bucDate = dateTime();
			boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if(getMyUserNameIsOk==true) {
			if((LoginController.getMyUserName().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
				 
		String buttonAddUser = "NotNull";	
		String buttonAdd = null;
		String nameUser = udao.getEmailUser(LoginController.getMyUserName()).getFullName();
		locationList = loc.getAllLocation();
		newLocationList = isLoginTest(locationList);
		
		userList = udao.getUserList();	
		for(User u:userList) {
			if(u.getId()>1) {
				if(u.getRole().equalsIgnoreCase("ADMIN"))
				newUserList.add(u);	
			}
		}
		double totalDayCount = hutls.totalDayContor(newLocationList);
		double totlalMonthCount = hutls.totalMonthContor(newLocationList);
		int totalMachine = hutls.totalMachine(newLocationList);
		 model.addAttribute("buttonAddUser",buttonAddUser);	
		 model.addAttribute("nameUser",nameUser);
		 model.addAttribute("europeDateTime",bucDate);
		 model.addAttribute("locationList",newLocationList);
		 model.addAttribute("totalDayCount",totalDayCount);
		 model.addAttribute("totlalMonthCount",totlalMonthCount);
		 model.addAttribute("totalMachine",totalMachine);
			return new ModelAndView("WEB-INF/superadmin/homesuperadmin.jsp","model",model);
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
				@RequestMapping(value="view-location.htm")
				public ModelAndView viewLocation(HttpServletRequest request,
						HttpServletResponse response, @ModelAttribute("id") int idLocation,Model model,BindingResult result)
						throws SQLException ,ServletException, IOException, ParseException
						 {
					String bucDate = dateTime();
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
						return new ModelAndView("WEB-INF/superadmin/view-location.jsp","model",model);
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
				@RequestMapping(value="super-admin-list.htm")
				public ModelAndView homeSuperAdmin(Model model)
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
						if(u.getRole().equalsIgnoreCase("SUPERADMIN"))
						newUserList.add(u);	
					}
				}
				 
				 model.addAttribute("userList",newUserList);
				 model.addAttribute("nameUser",nameUser);
					return new ModelAndView("WEB-INF/superadmin/user/superadminlist.jsp","model",model);
				}
				else {
					LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}
				
				//listare  homeSuperAdmin
				@RequestMapping(value="user-list.htm")
				public ModelAndView homeUser(Model model)
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
					return new ModelAndView("WEB-INF/superadmin/user/userlist.jsp","model",model);
				}
				else {
					LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}
				
				//listare  homeSuperAdmin
				@RequestMapping(value="admin-list.htm")
				public ModelAndView homeAdmin(Model model)
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
						if(u.getRole().equalsIgnoreCase("ADMIN"))
						newUserList.add(u);	
					}
				}
				 
				 model.addAttribute("userList",newUserList);
				 model.addAttribute("nameUser",nameUser);
					return new ModelAndView("WEB-INF/superadmin/user/adminlist.jsp","model",model);
				}
				else {
					LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}
				
				
				@RequestMapping(value="adduser.htm")
				public ModelAndView addUser(@ModelAttribute("idUser") User user, 
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
								return new ModelAndView("WEB-INF/superadmin/user/usermodal.jsp","model",model);
								}
							}			
							udao.insertUser(user);
							user = udao.getLastUserInsert();						
							model.addAttribute("myUser",user);
				        		return new ModelAndView("redirect:/user-list.htm");
						}
						else {
							 LoginController.isLoginSuperAdmin = false;
							 return new ModelAndView("redirect:/LoginOut.htm");
						}
				}
				
				//Update User
				@RequestMapping(value="updateUser.htm")
				public ModelAndView editProduct(@ModelAttribute("idUser") User user , 
						Model model,BindingResult result) throws SQLException, ServletException, IOException {
					PasswordUtils utilPassword = new PasswordUtils();
					User newUser = new User();
						UserDAO udao = new UserDAO();
						newUser = udao.getIdUser(user.getId());
						boolean getMyUserNameIsOk =LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
						boolean getMyPassword = LoginController.isNotNullNotEmptyNotWhiteSpace(user.getPassword());
						if((LoginController.isLoginSuperAdmin == true)&&(getMyUserNameIsOk == true)) {
							if(getMyPassword == true) {
								 String salt = null ;
						          salt = utilPassword.getSalt(30);
						          String newPassword = utilPassword.generateSecurePassword(user.getPassword(), salt);
						          user.setPassword(newPassword);
						          user.setSalt(salt);
							}
							else {
								user.setSalt(newUser.getSalt());
								user.setPassword(newUser.getPassword());
							}
							udao.updateUser(user);
							return new ModelAndView("redirect:/user-list.htm");
						    }
						else {
							 LoginController.isLoginSuperAdmin = false;
							 return new ModelAndView("redirect:/LoginOut.htm");
						}
				     }
				
				@RequestMapping(value="userlocation.htm")
				public ModelAndView userLocation(@ModelAttribute("idUser") User user, 
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
				        		return new ModelAndView("WEB-INF/superadmin/user/userlocationlist.jsp","model",model);
						}
						else {
							 LoginController.isLoginSuperAdmin = false;
							 return new ModelAndView("redirect:/LoginOut.htm");
						}
				}
				
				
				
				
				
				
				@RequestMapping(value="createlocationlist.htm")
				public ModelAndView createLocationList(@ModelAttribute("userLocList") UsersLocationList userLocList, 
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
				        		return new ModelAndView("WEB-INF/superadmin/user/userlocationlist.jsp","model",model);
						}
						else {
							 LoginController.isLoginSuperAdmin = false;
							 return new ModelAndView("redirect:/LoginOut.htm");
						}
				}
				
				
				
				//delete User
				@RequestMapping(value="delete-user.htm")
				public ModelAndView deleteUser(@ModelAttribute("id") String idUser ,Model model,BindingResult result) 
						throws SQLException, ServletException, IOException {
					
					int id= Integer.parseInt(idUser);
					boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
					if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
						UserLocationListDAO usrLocListDao = new UserLocationListDAO();
						UserDAO udao = new UserDAO();						
						udao.deleteUser(id);
						usrLocListDao.dropUserLocationList(id);
						return new ModelAndView("redirect:/user-list.htm");
					}
					else {
						 LoginController.isLoginSuperAdmin = false;
						 return new ModelAndView("redirect:/LoginOut.htm");
					}
					}
}
