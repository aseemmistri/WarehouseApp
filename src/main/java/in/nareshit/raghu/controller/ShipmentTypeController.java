package in.nareshit.raghu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.nareshit.raghu.model.ShipmentType;
import in.nareshit.raghu.service.IShipmentTypeService;
import in.nareshit.raghu.util.ShipmentTypeUtil;
import in.nareshit.raghu.view.ShipmentTypeExcelView;
import in.nareshit.raghu.view.ShipmentTypePdfView;
import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/st")
public class ShipmentTypeController {
	
	@Autowired
	private IShipmentTypeService service;
	
	@Autowired
	private ShipmentTypeUtil util;
	
	@Autowired
	private ServletContext context;

	
	//1.show register page
	@GetMapping("/register")
	public String showReg(Model model) {
		//from backing object
		model.addAttribute("shipmentType", new ShipmentType());
		return "ShipmentTypeRegister";
	}
	
	//2.save
	@PostMapping("/save")
	public String save(
			@ModelAttribute ShipmentType shipmentType,
			Model model) 
	{
		String id=service.saveShipmentType(shipmentType);
		String message="ShipmentType '"+id+"' saved";
		model.addAttribute("message", message);
		//from backing object
		model.addAttribute("shipmentType", new ShipmentType());
		return "ShipmentTypeRegister";
	}
	
	//3.display all
	@GetMapping("/all")
	public String showAll(@PageableDefault(page=0,size=3)Pageable p,
			Model model) 
	{
		//model.addAttribute("list",service.getAllShipmentType());
		Page<ShipmentType> page=service.getAllShipmentType(p);
		model.addAttribute("list", page.getContent());
		model.addAttribute("page",page);
		return "ShipmentTypeData";
	}
	
	//4.delete by id
	@GetMapping("/delete")
	public String deleteOne(
			@RequestParam String id,
			@PageableDefault(page=0,size=3)Pageable p,
			  Model model) 
	{
		service.deleteshipmentType(id);
		model.addAttribute("message","shipmentType '"+id+"' deleted");
		//model.addAttribute("list",service.getAllShipmentType());
		Page<ShipmentType> page=service.getAllShipmentType(p);
		model.addAttribute("list", page.getContent());
		model.addAttribute("page",page);
		return "ShipmentTypeData";
	}
	
	//5.show edit
	@GetMapping("/edit")
	public String showEdit( 
			@RequestParam String id, 
			Model model) 
	{
		model.addAttribute("shipmentType",service.getOneShipmentType(id));
		return "ShipmentTypeEdit";
	}
	
	//6.update
	@PostMapping("/update")
	public String doUpdate(
			@ModelAttribute ShipmentType shipmentType,
			@PageableDefault(page=0,size=3)Pageable p,
			Model model) 
	{
		service.updateShipmentType(shipmentType);
		model.addAttribute("message","ShipmentType '"+shipmentType.getId()+"' Updated");
		//model.addAttribute("list",service.getAllShipmentType());
		Page<ShipmentType> page=service.getAllShipmentType(p);
		model.addAttribute("list", page.getContent());
		model.addAttribute("page",page);
		return "ShipmentTypeData";
	}
	
	//7.excel export
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView m=new ModelAndView();
		
		//service call
		List<ShipmentType> list=service.getAllShipmentType();
		m.addObject("list", list);
		
		if(list==null || list.isEmpty()) {
			m.addObject("message","NO DATA FOR EXCEL EXPORT");
			m.setViewName("ShipmentTypeData");
		}else {//data exist then export
			m.setView(new ShipmentTypeExcelView());
		}
		
		return m;
	}
	
	//8.Export data to PDF
	@GetMapping("/pdf")
	public ModelAndView exportToPdf() {
		ModelAndView m=new ModelAndView();
		
		//service call
		List<ShipmentType> list=service.getAllShipmentType();
		m.addObject("list", list);
		
		if(list==null || list.isEmpty()) {
			m.addObject("message","NO DATA FOR PDF EXPORT");
			m.setViewName("ShipmentTypeData");
		}else {//data exist then export
			m.setView(new ShipmentTypePdfView());
		}
		
		return m;
	}
	
	//9. validate code using ajax
	@GetMapping("/validate")
	public @ResponseBody String validateCode(
			@RequestParam String code,
			@RequestParam String id) 
	{
		String message = "";
		if(id==null && service.isShipmentTypeCodeExist(code)) {
			message = "Shipment Code "+code+" already exist";
		}
		return message;
	}
	
	//10. charts
	@GetMapping("/charts")
	public String generateCharts() {
		List<Object[]> data = service.getShipmentModeAndCount();
		String path = context.getRealPath("/");
		util.generatePie(path, data);
		util.generateBar(path, data);
		return "ShipmentTypeCharts";
	}
}
