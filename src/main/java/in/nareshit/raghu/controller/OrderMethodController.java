package in.nareshit.raghu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.nareshit.raghu.model.OrderMethod;
import in.nareshit.raghu.service.IOrderMethodService;

@Controller
@RequestMapping("/om")
public class OrderMethodController {
	
	@Autowired
	private IOrderMethodService service;
	
	//1.show register page
	@GetMapping("/register")
	public String showReg(Model model) {
		//Form backing Object
		model.addAttribute("orderMethod",new OrderMethod());
		return "OrderMethodRegister";
	}
	
	//2.save
	@PostMapping("/save")
	public String save(
			@ModelAttribute OrderMethod orderMethod,
			   Model model) 
	{
		String id=service.saveOrderMethod(orderMethod);
		String message = "OrderMethod '"+id+"' save";
		model.addAttribute("message", message);
		model.addAttribute("orderMethod",new OrderMethod());
		return "OrderMethodRegister";
	}
	
	//3.display all
	@GetMapping("/all")
	public String showAll(Model model)
	{
		model.addAttribute("list",service.getAllOrderMethod());
		return "OrderMethodData";
	}
	
	//4.delete by id
	@GetMapping("/delete")
	public String deleteOne(
			@RequestParam String id,
			     Model model) 
	{
		service.deleteOrderMethod(id);
		model.addAttribute("message","OrderMethod '"+id+"' deleted");
		model.addAttribute("list",service.getAllOrderMethod());
	    return "OrderMethodData";	
	}
	
	//5.show edit
	@GetMapping("/edit")
	public String showEdit(
			@RequestParam String id,
			       Model model)
	{
		model.addAttribute("orderMethod",service.getOneOrderMethod(id));
		return "OrderMethodEdit";
	}
	
	
	//6.update
	@PostMapping("/update")
	public String doUpdate(
			@ModelAttribute OrderMethod orderMethod, 
			         Model model)
	{
		service.updateOrderMethod(orderMethod);
		model.addAttribute("message", "OrderMethod '"+orderMethod.getId()+"' updated");
		model.addAttribute("list",service.getAllOrderMethod());
		return "OrderMethodData";
	}

}
