package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eurovending.dao.ChasRegisterByUserDAO;
import org.eurovending.dao.ChasRegisterDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.pojo.ChasRegister;
import org.eurovending.pojo.ChasRegisterByUser;
import org.eurovending.pojo.User;
import org.eurovending.utils.ServiceRegister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SuperadminRegiserCash {

	//listare User
	@RequestMapping(value="superadmin-register-list.htm")
	public ModelAndView listSuperdminRegisterChas(Model model) 
			throws SQLException, ServletException, IOException {
		 ArrayList<ChasRegister> chasRegisters = new ArrayList<ChasRegister>();
		 final DecimalFormat df = new DecimalFormat("0.00");

		 ChasRegisterDAO chrdao = new ChasRegisterDAO();
		 ServiceRegister svreg = new ServiceRegister();
		boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());	
		if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
			chrdao.createTableRegister();
			svreg.createChasRegisters();
			svreg.insertAutomaticChasRegister();
			chasRegisters = chrdao.getAllChasRegister();
			model.addAttribute("chasRegisters",chasRegisters);				
			 model.addAttribute("nameUser",LoginController.getMyUserName());
			return new ModelAndView("WEB-INF/superadmin/register/listregister.jsp","model",model);
		}
		else {
			 LoginController.isLoginSuperAdmin = false;
			 return new ModelAndView("redirect:/LoginOut.htm");
		}
		}
	
	public ServiceRegister svReg = new ServiceRegister();
	public ChasRegister chReg = new ChasRegister();
	public User user = new User();
	public UserDAO udao = new UserDAO();

	//public TotalPaymentUserDAO pdao = new TotalPaymentUserDAO();
	public ChasRegisterByUserDAO chrdao = new ChasRegisterByUserDAO();
		
	//Add encashment or payments
			@RequestMapping(value="addPayment.htm")
			public ModelAndView doPost(HttpServletRequest request,RedirectAttributes redirectAttributes,
					HttpServletResponse response,Model model) throws ServletException, IOException, SQLException {
				 ChasRegisterDAO chrdao = new ChasRegisterDAO();
				 ChasRegister chasReg =  new ChasRegister();
				 
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
				 
				ArrayList<ChasRegisterByUser> paymentList=new ArrayList<ChasRegisterByUser>();
				if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
					double encashment = 0;
					double payment = 0;	
					String explanation = request.getParameter("explanation");
				// int id =Integer.parseInt(request.getParameter("id"));
				 if(request.getParameter("encashment")!=null) 
				  encashment =   Double.parseDouble(request.getParameter("encashment"));
				 if(request.getParameter("payment")!=null)
				  payment = Double.parseDouble(request.getParameter("payment"));
				 
				 String msg = svReg.getPayUser(encashment,payment,explanation); 
				    
					return new ModelAndView("redirect:/superadmin-register-list.htm");
				}
				else {
					 LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
			}
			
			
			@RequestMapping(value="addnewregister.htm")
			public ModelAndView addNewRegister(@ModelAttribute("idUser")  ChasRegister chasReg,
					Model model,BindingResult result) throws SQLException {
			
				 final DecimalFormat df = new DecimalFormat("0.00");
				 ChasRegisterDAO chrdao = new ChasRegisterDAO();
				 ArrayList<ChasRegister> chasRegisters = new ArrayList<ChasRegister>();
				boolean notExist = true;
				 
				 UserDAO udao = new UserDAO();
				 User user = new User();
		
				 ServiceRegister svreg = new ServiceRegister();
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());				
				if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
					//user = udao.getIdUser(chasReg.getIdUser());
					String mesage = null ;
					notExist = svreg.createChasRegisters();
					chasRegisters = chrdao.getAllChasRegister();
					if(notExist == false) {	
						mesage = " - Pentru aceasta luna exisa un Registru !!!";
					}				
					model.addAttribute("mesage",mesage);
					model.addAttribute("chasRegisters",chasRegisters);
				
					model.addAttribute("user",user);
					model.addAttribute("nameUser",LoginController.getMyUserName());
				return new ModelAndView("WEB-INF/superadmin/register/chasregister.jsp","model",model);
			}
			else {
				 LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}
			
			@RequestMapping(value="viewregister.htm")
			public ModelAndView superAdminViewRegister(@ModelAttribute("idUser")  ChasRegister chasReg,
					Model model,BindingResult result) throws SQLException {
				 ChasRegisterDAO chrdao = new ChasRegisterDAO();
				 ChasRegisterByUserDAO chRegByUserDAO = new ChasRegisterByUserDAO();
				 ArrayList<ChasRegisterByUser> chRegByUserList= new  ArrayList<ChasRegisterByUser>();
				 ChasRegisterByUser chRegByUser= new  ChasRegisterByUser();
				 UserDAO udao = new UserDAO();
				 User user = new User();
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());				
				if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {
					chasReg = chrdao.getChasRegister(chasReg.getId());
					chRegByUserList= chRegByUserDAO.getAllPay( chasReg.getMonth(), chasReg.getYear());
					chRegByUser= chRegByUserDAO.getLastPay(chasReg.getMonth(), chasReg.getYear());
					//user = udao.getIdUser(chasReg.getIdUser());
					
					model.addAttribute("chasReg",chasReg);
					model.addAttribute("chRegByUserList",chRegByUserList);
					model.addAttribute("chRegByUser",chRegByUser);
					model.addAttribute("user",user);
					model.addAttribute("nameUser",LoginController.getMyUserName());
					return new ModelAndView("WEB-INF/superadmin/register/chasregister.jsp","model",model);
				}
				else {
					 LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
			}
			
				//DeleteChasReg
				@RequestMapping(value="deleteChasReg.htm")
				public ModelAndView deleteChasReg(HttpServletRequest request,RedirectAttributes redirectAttributes,
						HttpServletResponse response,Model model) throws ServletException, IOException, SQLException {
					boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
					 ArrayList<ChasRegister> paymentList=new ArrayList<ChasRegister>();
					 ChasRegisterDAO chdao = new ChasRegisterDAO();		
					 ServiceRegister svreg = new ServiceRegister();
					 if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {					
						int id =Integer.parseInt(request.getParameter("id"));					  
					    svreg.deleteChasRegister( id);					  
						return new ModelAndView("redirect:/superadmin-register-list.htm");
					}
					else {
						 LoginController.isLoginSuperAdmin = false;
						 return new ModelAndView("redirect:/LoginOut.htm");
					}
	  }
		
				//DeleteRowChasReg
				@RequestMapping(value="deleteRowChasReg.htm")
				public ModelAndView deleteRowChasReg(HttpServletRequest request,RedirectAttributes redirectAttributes,
						HttpServletResponse response,Model model) throws ServletException, IOException, SQLException {
					boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
					 ArrayList<ChasRegister> paymentList=new ArrayList<ChasRegister>();
					 ChasRegisterDAO chdao = new ChasRegisterDAO();		
					 ServiceRegister svreg = new ServiceRegister();
					 if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk==true)) {					
						int id =Integer.parseInt(request.getParameter("id"));
					    svreg.deleteRowChasRegister(id);
					    //paymentList = chrdao.getAllPay(user.getId());				
						 redirectAttributes.addFlashAttribute("id",user);
						return new ModelAndView("redirect:/superadmin-register-list.htm");
					}
					else {
						 LoginController.isLoginSuperAdmin = false;
						 return new ModelAndView("redirect:/LoginOut.htm");
					}
	  }



}
