package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eurovending.dao.LocationDAO;
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
public class HallController {
//double dayContr;
//double contPerm;
//int signLvl ;
//static String macAdrrs;
	Location locClass = new Location();
	Location newLoc = new Location();
	 
		 @RequestMapping(value ="getTcpData.htm", method = RequestMethod.GET)
		    public void getTcpData
	(HttpServletRequest request,HttpServletRequest response,@RequestParam String dayCont,@RequestParam String signalLevel,@RequestParam String serialnr,
		    @RequestParam String macAddress,@RequestParam String permanentCont, RedirectAttributes redirectAttributes,Model model)
		    
		    		throws SQLException ,ServletException, IOException
		    {
			 
		        
		        double contorPerm = Integer.parseInt(permanentCont);
		    	double dayContor = Double.parseDouble(dayCont);
		    	int levelSignal = Integer.parseInt(signalLevel);
		    	levelSignal = Math.abs(levelSignal);
		    	locClass  = new Location(serialnr,macAddress,contorPerm,dayContor,levelSignal);
		    	HallUtils hallsUtl = new HallUtils();
		    	hallsUtl.insertUpdateLoc(locClass);
		    	System.out.println("Client Connected"+" MacAdrres "+macAddress+" TotCont "+contorPerm);
		    	    getParam( model);
		   
		    }
		 
		 @RequestMapping(value ="getDataHall.htm", method = RequestMethod.GET)
		    public void getDataHall    
	(HttpServletRequest request,HttpServletResponse response,@RequestParam String dayCont,
	@RequestParam String macAddress,@RequestParam String permanentCont,RedirectAttributes redirectAttributes,Model model)
		    		throws SQLException ,ServletException, IOException
		    {
			 
			     HallUtils hallsUtl = new HallUtils();
		    	double contorPer = Double.parseDouble(permanentCont);
		    	double dayContor = Double.parseDouble(dayCont);	 
		    	//int levelSignal = Integer.parseInt(signalLevel);
		    	//levelSignal = Math.abs(levelSignal);
		    	//newLoc = new Location(macAddress,contorPer,dayContor,levelSignal);
		    	newLoc = new Location(macAddress,contorPer,dayContor); 
		    	System.out.println("Client ConnectedgetDataHall"+" MacAdrres "+macAddress);
		    	//System.out.println("wifi signal"+levelSignal);
		    	 getParam( model);
		        hallsUtl.updateContorHall(newLoc);   	
		    	
		    }

	 @RequestMapping(value ="getParam.htm")
	    public ModelAndView getParam(Model model) {
		 System.out.println("getParamnewLoc"+newLoc.getDayContor()+" get "+newLoc.getLocationMacAddress());
		 System.out.println("getParamlocClass"+newLoc.getDayContor()+" get "+newLoc.getLocationMacAddress());
		 model.addAttribute("newLoc",newLoc);
		 model.addAttribute("loc",locClass);
		        return new ModelAndView("getParam.jsp","model",model);
	    }
	 
	 /*
    @RequestMapping(value="home.htm")
	public ModelAndView homeSuperAdmin(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("myUser") User myUser,Model model) {
    	
    	 model.addAttribute("loc",locClass);
 		return new ModelAndView("index.jsp","model",model);
    }
    */
}
