package in.nareshit.raghu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.nareshit.raghu.model.Grn;
import in.nareshit.raghu.model.GrnDtl;
import in.nareshit.raghu.model.PurchaseDtl;
import in.nareshit.raghu.service.IGrnService;
import in.nareshit.raghu.service.IPurchaseOrderService;

@Controller
@RequestMapping("/grn")
public class GrnController {
	
	@Autowired
	private IGrnService service;
	
	@Autowired
	private IPurchaseOrderService poService;
	
	//Register Page DropDown
	private void addDynamicUiComponents(Model model) {
	Map<String,String> pos=	poService.getPurchaseOrderIdAndCodeByStatus("INVOICED");
	model.addAttribute("pos", pos);
	}
	
	//1.show Grn Register page
	@GetMapping("/register")
	public String showGrnReg(Model model) 
	{
		model.addAttribute("grn", new Grn());
		addDynamicUiComponents(model);
		return "GrnRegister";
	}
	
	
	//2.save Grn
	@PostMapping("/save")
	public String saveGrn(@ModelAttribute Grn grn,Model model) 
	{
		String id=service.saveGrn(grn);
		model.addAttribute("message","Grn '"+id+"' saved");
		model.addAttribute("grn", new Grn());
		
		
		//change po status on GRN Create Successful
		poService.updateStatus(grn.getPo().getId(),"RECEIVED");
		
		
		addDynamicUiComponents(model);
		
		
		createGrnDtls(grn);
		return "GrnRegister";
	}

	private void createGrnDtls(Grn grn) {
		// a# Read Purchase Order Id Using Grn Linked Po
		String orderId= grn.getPo().getId();
		//b# Read All PurchaseDtls data using PurchaseOrdeer Id
		List<PurchaseDtl> poDtls= poService.getPurchaseDtlsByOrderId(orderId);
		
		for(PurchaseDtl poDtl : poDtls) {
			//d# create one GrnDtl object using one PurchaseDtl object
		    GrnDtl grnDtl = new GrnDtl();
		    grnDtl.setItemCode(poDtl.getPart().getPartCode());
		    grnDtl.setBaseCost(poDtl.getPart().getPartCost());
		    grnDtl.setQty(poDtl.getQty());
		    grnDtl.setItemVal(grnDtl.getBaseCost()*grnDtl.getQty());
		    
		    //e# Link GrnDtl object to Grn object 
		    grnDtl.setGrn(grn);
		    
		    //f# Save GrnDtl object
		    service.saveGrnDtl(grnDtl);
		}
		
	}
	
	//3.View All Grns
	@GetMapping("/all")
	public String showGrns(Model model) {
	    List<Grn> list=service.getAllGrn();
	    model.addAttribute("list", list);
		return "GrnData";
	}
	
	//4.show GrnDtlsView (Screen#2) based on GrnId
	@GetMapping("/viewParts")
	public String showGrnDtls(@RequestParam String grnId,Model model) 
	{
		// grn object
		Grn grn=service.getOneGrnbyId(grnId);
		model.addAttribute("grn", grn);
		//grn Dtls
		List<GrnDtl> list=service.getAllGrnDtlsByGrnId(grnId);
		model.addAttribute("list", list);
		return "GrnDtlView";
	}
	
	//5. update grnDtl status
	@GetMapping("/updateStatus")
	public String updateGrnDtlStatus(
			@RequestParam String grnId,
			@RequestParam String grnDtlId,
			@RequestParam String status) 
	{
		service.updateGrnDtlsStatus(grnDtlId, status);
		return "redirect:viewParts?grnId="+grnId;
	}

}
