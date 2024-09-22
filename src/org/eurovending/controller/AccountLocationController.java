package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eurovending.dao.AccountLocationDAO;
import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.pojo.Location;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@MultipartConfig(maxFileSize = 16177215)
public class AccountLocationController {
public int idLoc = 0 ;
	 @RequestMapping(value ="view_account.htm")
	 public ModelAndView viewAccount(HttpServletRequest request,@ModelAttribute("id") int id,Model model,BindingResult result)
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
					return new ModelAndView("WEB-INF/superadmin/location/view_account.jsp","model",model);
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
	
	 @RequestMapping(value ="generate_account.htm")
	 public ModelAndView generateAccount(HttpServletRequest request,@ModelAttribute("id") int id,Model model,BindingResult result)
	    		throws SQLException ,ServletException, IOException
	    {
		 idLoc = id;
		 Location loc = new Location();
		 SuperAdminController sadm = new SuperAdminController();
		 ArrayList<Location> newLocationList = new ArrayList<Location>();
		 AccountLocationDAO locDao = new AccountLocationDAO();
		 LocationDAO lDao = new LocationDAO();
		 loc = lDao.getLocation(id);
		 
	      	String bucDate = sadm.dateTime();   
	      	 HttpSession session = request.getSession();
	      	Location accountLoc = new Location(loc.getLocationMacAddress(),loc.getPermanentContor(),0,0,loc.getPasContor()
	      			,loc.getNumberOfMachine(),LoginController.getMyUserName(),bucDate);
	      	boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if(getMyUserNameIsOk==true) {
			if((LoginController.getMyUserName().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
				locDao.createTableAccountLocation(id);
				locDao.insertAccountLocation(accountLoc, id);
					return new ModelAndView("redirect:/view_account.htm");
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
