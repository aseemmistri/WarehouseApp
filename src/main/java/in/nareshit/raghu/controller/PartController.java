package in.nareshit.raghu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.nareshit.raghu.model.Part;
import in.nareshit.raghu.service.IOrderMethodService;
import in.nareshit.raghu.service.IPartService;
import in.nareshit.raghu.service.IUomService;

@Controller
@RequestMapping("/part")
public class PartController {
	@Autowired
	private IPartService service;
	
	@Autowired
	private IUomService uomService;
	
	@Autowired
	private IOrderMethodService orderMethodService;
	
	private void addDynamicUiComponents(Model model) {
		model.addAttribute("uoms", uomService.getUomIdAndModel());
		model.addAttribute("sales",orderMethodService.getOrderMethodIdAndCodeByMode("Sale"));
	}
	
	//1. show Parts Register Page
	@GetMapping("/register")
	public String showReg(Model model ) {
		model.addAttribute("part", new Part());
		addDynamicUiComponents(model);
		return "PartRegister";
	}
	
	//2. save Parts
	@PostMapping("/save")
	public String savePart(@ModelAttribute Part part, Model model) {
		String id = service.savePart(part);
		model.addAttribute("message", "Part '"+id+"' saved");
		model.addAttribute("part", new Part());
		addDynamicUiComponents(model);
		return "PartRegister";
	}
	
	//3. Display Parts
	@GetMapping("/all")
	public String getAllParts(Model model) {
		List<Part> list=service.getAllParts();
		model.addAttribute("list", list);
		return "PartData";
	}
	
	//4. Show Edit
	
	//5. DoUpdate
}
