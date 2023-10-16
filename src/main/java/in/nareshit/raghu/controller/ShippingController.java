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

import in.nareshit.raghu.model.SaleDtl;
import in.nareshit.raghu.model.Shipping;
import in.nareshit.raghu.model.ShippingDtl;
import in.nareshit.raghu.service.ISaleOrderService;
import in.nareshit.raghu.service.IShippingService;

@Controller
@RequestMapping("/shipping")
public class ShippingController {
	
	@Autowired
	private IShippingService service;
	
	@Autowired
	private ISaleOrderService soService;
	
	//Register page drop down
	private void addDynamicUiComponent(Model model) {
		Map<String,String> sos=soService.getSaleOrderIdAndCodeByStatus("SALE-INVOICED");
		model.addAttribute("sos", sos);
	}
	
	//1.show shipping reg page
	@GetMapping("/register")
	public String showShippingReg(Model model) {
		model.addAttribute("shipping", new Shipping());
		addDynamicUiComponent( model);
		return "ShippingRegister";
	}
	
	
	//2.save shipping
	@PostMapping("/save")
	public String saveShipping(
			@ModelAttribute Shipping shipping,
			Model model) 
	{
		String id=service.saveShipping(shipping);
		model.addAttribute("message","Shipping "+id+" saved");
		model.addAttribute("shipping", new Shipping());
		
		//change so status on shipping Create Successful
		soService.updateSaleOrderStatusById(shipping.getSo().getId(), "SALE-SHIPPED");
		
		addDynamicUiComponent( model);
		
		createShippingDtl(shipping);
		return "ShippingRegister";
	}

	private void createShippingDtl(Shipping shipping) {
		//a# Read Sale Order id Using Shipping Linked So
		String orderId=shipping.getSo().getId();
		
		//b# Read All SaleDtl Data using SaleOrder id
		List<SaleDtl> soDtls=soService.getSaleDtlsByOrderId(orderId);
		
		for(SaleDtl soDtl:soDtls) {
			//d# create one shippingDtl object using one SaleDtl object
			ShippingDtl shippingDtl =new ShippingDtl();
			shippingDtl.setItemCode(soDtl.getPart().getPartCode());
			shippingDtl.setBaseCost(soDtl.getPart().getPartCost());
			shippingDtl.setQty(soDtl.getQty());
			shippingDtl.setGstSlob(soDtl.getPart().getGstSlob());
			shippingDtl.setItemValue((shippingDtl.getBaseCost()+(shippingDtl.getBaseCost()*shippingDtl.getGstSlob()/100))*shippingDtl.getQty());
			
			//e# Link shippingDtl object to Shipping object 
			shippingDtl.setShipping(shipping);
			
			//f# Save shippingDtl object
			service.saveShippingDtl(shippingDtl);
		}
		
	}
	
	// 3. View All Shipping
	@GetMapping("/all")
	public String showShipping(Model model)
	{
		List<Shipping> list=service.allShipping();
		model.addAttribute("list", list);
		return "ShippingData";
	}
	
	// 4.show GrnDtlsView (Screen#2) based on GrnId
	@GetMapping("/viewParts")
	public String showShippingDtl(
			@RequestParam String shippingId,
			Model model) 
	{
		Shipping shipping=service.getOneShippingById(shippingId);
		model.addAttribute("shipping", shipping);
		
		//grn Dtl
		List<ShippingDtl> list=service.getAllShippingDtlByShippingId(shippingId);
		model.addAttribute("list", list);
		
		
		
		return "ShippingDtlView";
	}
	
	//5.Update shipping Dtl status
	@GetMapping("/updateStatus")
	public  String updateStatus(
			@RequestParam String shippingId,
			@RequestParam String shippingDtlId,
			@RequestParam String status) 
	{
		service.updateShippingDtlStatus(shippingDtlId, status);
		return "redirect:viewParts?shippingId="+shippingId;
	}

}
