package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eurovending.dao.UserDAO;
import org.eurovending.pojo.User;
import org.eurovending.pojo.UserRole;
import org.eurovending.pojo.UserStatut;
import org.eurovending.utils.PasswordUtils;
import org.eurovending.utils.ServiceLoger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@MultipartConfig(maxFileSize = 16177215)
public class LoginController {
	static int idUser;
	static boolean isLoginSuperAdmin = false ;
	static String myUserName=null;
	
	public static int getIdUser() {
		return idUser;
	}


	public static void setIdUser(int idUser) {
		LoginController.idUser = idUser;
	}


	public static boolean isLoginSuperAdmin() {
			return isLoginSuperAdmin;
		}
		

		public static  void setLoginSuperAdmin(boolean isLoginSuperAdmin) {
			LoginController.isLoginSuperAdmin = isLoginSuperAdmin;
		}


		public static String getMyUserName() {
			return myUserName;
		}



		public static  void setMyUserName(String myUserName) {
			LoginController.myUserName = myUserName;
		} 

		//verificare string 
		public static boolean isNotNullNotEmptyNotWhiteSpace( String string)
		{
			boolean stringOk =false;
			if((string!=null)&&(!string.equals(" "))&&(!string.trim().isEmpty()))
			{
				stringOk=true;
			}
		   return stringOk;
		}
		
		//Login Admin & User(Employ)
		@RequestMapping(value="loginAdmin.htm")
		public ModelAndView doPost(HttpServletRequest request,
				HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) throws ServletException, IOException, SQLException {
			 ArrayList<User> userList = new ArrayList<User>();
			 UserDAO udao = new UserDAO();
             ServiceLoger svl = new ServiceLoger();
           //  HallUtils hlsutl = new HallUtils();
			// get request parameters for userID and password
			String email = request.getParameter("email");
			String pwd = request.getParameter("password");
			 //Generate table if not exist
			svl.generateTable();
			
			//test user&password is not empty
			boolean isOkUser = svl.testLogin(email, pwd); 
			
			//verifi user&password
			userList = udao.getUserList();
			 boolean adminPasswordMatch=false;
			
				if(isOkUser==true) {
			for(User myUser:userList) {
				// String verifyPassword = myUser.getPassword();
				// String adminSalt = myUser.getSalt();
				
					adminPasswordMatch = PasswordUtils.verifyUserPassword( pwd,myUser.getPassword(), myUser.getSalt());
					
		        if((adminPasswordMatch==true)&&(myUser.getEmail().equalsIgnoreCase(email))) 
		        {	
		        	//hlsutl.createInsertLocationList();
		        	LoginController.setIdUser(myUser.getId());
		        	LoginController.setMyUserName(myUser.getEmail());
		        	
		        	HttpSession session = request.getSession();
					session.setAttribute("email", email);
					//setting session to expiry in 60 mins
					session.setMaxInactiveInterval(5*60);//5 minute
					
					Cookie userName = new Cookie("email", email);
					userName.setMaxAge(60*60*60*60);
					response.addCookie(userName);				
					setIdUser(myUser.getId());
					//setMyUserName(myUser.getFullName());
		        	if((myUser.getRole().equals(UserRole.SUPERADMIN.toString()))&&(myUser.getStatute().equalsIgnoreCase(UserStatut.ACTIVE.toString()))) {
		        		setLoginSuperAdmin(true);	        		
		        		return new ModelAndView("redirect:/home-superadmin.htm");
		        	}
		        	if((myUser.getRole().equals(UserRole.ADMIN.toString()))&&(myUser.getStatute().equals(UserStatut.ACTIVE.toString()))) {
		        		setLoginSuperAdmin(true);
		        		return new ModelAndView("redirect:/home-admin.htm");
		        	}
		        	if((myUser.getRole().equals(UserRole.USER.toString()))&&(myUser.getStatute().equals(UserStatut.ACTIVE.toString()))) {
		        		setLoginSuperAdmin(true);
		        		
		        		return new ModelAndView("redirect:/home-user.htm");
		            	}
		        }
		        
		        
			}
			
			
				}
		                     
				//invalidate the session if exists
		    	HttpSession session = request.getSession(false);
		    	
		    	if(session != null){
		    		session.invalidate();
		    	}
				return new ModelAndView("index.jsp");
		        }
		
		//Login Out Admin & User(Employ)
		@RequestMapping(value="LoginOut.htm")
		 protected void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    	response.setContentType("text/jsp");
		    
		    	Cookie[] cookies = request.getCookies();
		    	if(cookies != null){
		    	for(Cookie cookie : cookies){
		    		if(cookie.getName().equals("JSESSIONID")){
		    			//System.out.println("JSESSIONID="+cookie.getValue());
		    		}
		    		cookie.setMaxAge(0);
		    		response.addCookie(cookie);
		    	}
		    	}
		    	//invalidate the session if exists
		    	HttpSession session = request.getSession(false);
		    	
		    	if(session != null){
		    		session.invalidate();
		    	}
		    	
		    	//no encoding because we have invalidated the session
		    	response.sendRedirect("index.jsp");
		    }

}
