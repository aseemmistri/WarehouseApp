package in.nareshit.raghu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.nareshit.raghu.model.WhUserType;
import in.nareshit.raghu.service.IWhUserTypeService;
import in.nareshit.raghu.util.EmailUtil;

@Controller
@RequestMapping("/wh")
public class WhUserTypeController {
	
	@Autowired
	private IWhUserTypeService service;
	
	@Autowired
	private EmailUtil emailUtil;
	
	//1.show register page
	@GetMapping("/register")
	public String showReg(Model model)
	{
		//Form Backing Object
		model.addAttribute("whUserType",new WhUserType());
		return "WhUserTypeRegister";
	}
	
	//2.save
	@PostMapping("/save")
	public String save(
			@ModelAttribute WhUserType whUserType,//read from data as object
			   Model model)//send data to UI
	{
		String id=service.saveWhUserType(whUserType);
		String message="WhUserType '"+id+"' Saved";
		if(id!=null) {
		new Thread(new Runnable() {
			public void run() {
				    emailUtil.sendEmail(
				        whUserType.getUserEmail(),
						"Welcome to whUser","HELLO:"+
				        whUserType.getUserCode());	
					
			}
		}).start();
		
		}
		
		
		//sending data to UI
		model.addAttribute("message", message);
		//Form Backing Object
		model.addAttribute("whUserType",new WhUserType());
		return "WhUserTypeRegister";
	}
	
	//3.display all
	@GetMapping("/all")
	public String showAll(Model model)
	{
		model.addAttribute("list",service.getAllWhUserType() );
		return "WhUserTypeData";
	}
	
	//4.delete by Id
	@GetMapping("/delete")
	public String deleteOne(
			@RequestParam String id,
			 Model model)
	{
		service.deleteWhUserType(id);
		model.addAttribute("message","WhUserType '"+id+"' deleted");
		model.addAttribute("list",service.getAllWhUserType() );
		return "WhUserTypeData";
	}
	
	//5.show edit
	@GetMapping("/edit")
	public String shoeEdit(
			@RequestParam String id, 
			  Model model)
	{
		model.addAttribute("whUserType",service.getOneWhUserType(id));
		return "WhUserTypeEdit";
	}
	
	//6.update
	@PostMapping("/update")
	public String doUpdate(
			@ModelAttribute WhUserType whUserType,
			  Model model)
	{
		service.updateWhUserType(whUserType);
		model.addAttribute("message","WhUserType '"+whUserType.getId()+"' Updated");
		model.addAttribute("list",service.getAllWhUserType() );
		return "WhUserTypeData";
	}
	
	//7. AJAX VALIDATETION
		@GetMapping("/checkMail")
		public @ResponseBody String validateEmailExist(
				@RequestParam String mail) 
		{
			String message = "";
			
			if(service.isWhUserEmailExist(mail)) {
				message = mail+" already exist";
			}
			return message;
		}

}
