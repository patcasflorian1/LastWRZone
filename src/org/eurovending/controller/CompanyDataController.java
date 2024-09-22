package org.eurovending.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eurovending.dao.CompanyDataDAO;
import org.eurovending.pojo.CompanyData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompanyDataController {

	//View company data
		@RequestMapping(value="view-company.htm")
		public ModelAndView viewCompany(Model model) throws SQLException {
			 
			boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk=true)) {
				CompanyDataDAO compdao = new CompanyDataDAO();
				ArrayList <CompanyData> myCompanyData = new ArrayList<CompanyData>();
					myCompanyData = compdao.getCompanyDataList();
				String msg = "NotNull";
				String buttonAdd = null;
				if(compdao.verifyCompanyData() == 0) {
					buttonAdd = "Add";
				}
			 model.addAttribute("myCompanyData",myCompanyData);
			 model.addAttribute("msg",msg);
			 model.addAttribute("buttonAdd",buttonAdd);
				return new ModelAndView("WEB-INF/superadmin/viewcompany.jsp","model",model);
			}
			else {
				LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}
		
		//Add company data
		@RequestMapping(value="addcompany.htm")
		public ModelAndView addCompany( @ModelAttribute("myCompany") CompanyData myComp,BindingResult result, Model model ) throws SQLException {
			
			boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
			if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk=true)) {
				CompanyDataDAO compdao = new CompanyDataDAO();
				String buttonAdd = null;
				if(compdao.verifyCompanyData() == 0) {
				compdao.insertCompanyData(myComp);
				
				}
				ArrayList <CompanyData> myCompanyData = new ArrayList<CompanyData>();
					myCompanyData = compdao.getCompanyDataList();
					String msg = "NotNull";
				
			 model.addAttribute("myCompanyData",myCompanyData);
			 model.addAttribute("msg",msg);
			 model.addAttribute("buttonAdd",buttonAdd);
				return new ModelAndView("WEB-INF/superadmin/viewcompany.jsp","model",model);
			}
			else {
				LoginController.isLoginSuperAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}
		
		//Edit company data
			@RequestMapping(value="editcompany.htm")
			public ModelAndView EditCompany( @ModelAttribute("myCompany") CompanyData myComp,BindingResult result, Model model ) throws SQLException {
				
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyUserName());
				if((LoginController.isLoginSuperAdmin() == true)&&(getMyUserNameIsOk=true)) {
					CompanyDataDAO compdao = new CompanyDataDAO();
					String buttonAdd = null;
					
					compdao.updateCompanyData(myComp);
					
				
					ArrayList <CompanyData> myCompanyData = new ArrayList<CompanyData>();
						myCompanyData = compdao.getCompanyDataList();
						String msg = "NotNull";
					
				 model.addAttribute("myCompanyData",myCompanyData);
				 model.addAttribute("msg",msg);
				 model.addAttribute("buttonAdd",buttonAdd);
					return new ModelAndView("WEB-INF/superadmin/viewcompany.jsp","model",model);
				}
				else {
					LoginController.isLoginSuperAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}
	
	
  }
