package in.nareshit.raghu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import in.nareshit.raghu.model.PurchaseDtl;
import in.nareshit.raghu.model.PurchaseOrder;
import in.nareshit.raghu.service.IPartService;
import in.nareshit.raghu.service.IPurchaseOrderService;
import in.nareshit.raghu.service.IShipmentTypeService;
import in.nareshit.raghu.service.IWhUserTypeService;
import in.nareshit.raghu.view.VendorInvoicePdfView;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {
	
	@Autowired
	private IPurchaseOrderService service;
	
	@Autowired
	private IShipmentTypeService shipmentService;
	
	@Autowired
	private IWhUserTypeService whservice;
	
	@Autowired
	private IPartService partService;
	
	//Register page DropDown
	private void addDynamicUiComponents(Model model) {
		model.addAttribute("shipmenttypes", shipmentService.getShipmentIdAndCodeByEnabled("YES"));
		model.addAttribute("vendors", whservice.getWhUserTypeByUserType("Vendor"));
	}
	
	//1. show register page
	@GetMapping("/register")
	public String showReg(Model model) {
		PurchaseOrder po = new PurchaseOrder();
		po.setStatus("OPEN");
		model.addAttribute("purchaseOrder",po);
		addDynamicUiComponents(model);
		return "PurchaseOrderRegister";
	}
	
	//2. save data
	@PostMapping("/save")
	public String save(
			@ModelAttribute PurchaseOrder purchseOrder,
			Model model)
	{
		String id = service.savePurchaseOrder(purchseOrder);
		model.addAttribute("message","Purchase Order "+id+" saved");
		PurchaseOrder po = new PurchaseOrder();
		po.setStatus("OPEN");
		model.addAttribute("purchaseOrder",po);
		addDynamicUiComponents(model);
		return "PurchaseOrderRegister";
	}
	
	//3. display POs
	@GetMapping("/all")
	public String showAll(Model model) {
		List<PurchaseOrder> list =service.getAllPurchaseOrders();
		model.addAttribute("list", list);
		return "PurchaseOrderData";
	}
	
	//---------------Methods for Screen#2 Purchase Order Parts--------------------//
	//--section#2---
	private void addDynamicUiComponentsForParts(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}
	
	/**
	 * This method is showing output of Scrreen#2
	 * It is displayed when we click on ADD PARTS Button From Screen#1
	 * and even after adding new Part/Remove added part same page is loaded
	 * @param id
	 * @param model
	 * @return
	 */
	
	@GetMapping("/parts")
	public String showPartsPage(
			@RequestParam String id,
			Model model) 
	{
		//--section#1---
		//get purchase order by id
		PurchaseOrder po=service.getOnePurchaseOrder(id);
		model.addAttribute("po", po);
		
		//---section#2---
		//dynamic DropDown 
		addDynamicUiComponentsForParts(model);
		
		//send Form Backing Object
		model.addAttribute("purchaseDtl",new PurchaseDtl());
		
		//----section#4--------
		//show parts added to Order based on orderid
		List<PurchaseDtl> dtls=service.getPurchaseDtlsByOrderId(id);//OrderId
		//send data to ui
		model.addAttribute("dtls", dtls);
		
		return "PurchaseOrderParts";
	}
	
	/**
	 * On click add part button
	 * Read Form data as PurchaseDtl
	 * save into Db using service method
	 * redirect to same UI with /parts?id=<OrderId>
	 */
	//-----Section#3--------
	@PostMapping("/add")
	public String addPart(@ModelAttribute PurchaseDtl purchaseDtl) 
	{
		String orderId=purchaseDtl.getOrder().getId();
		
		service.savePurchaseDtl(purchaseDtl);
		
		//updata order status
		service.updateStatus(orderId,"PICKING");
		//service.updateStatus(orderId,OrderStatus.PICKING.name());
		
		
		
		//From PurchaseDtl-> get Order --> from Order get Id (ie order id)
		return "redirect:parts?id="+orderId;//order id (PO-ID)
	}
	
	
	/**
	 * On click Remove Button/Link, this method is called
	 * with 2 inputs  @param dtlId  and  @param orderId
	 * It will remove one part using DtlId and then redirect to
	 * same page using order id
	 * 
	 */
	@GetMapping("/remove")
	public String removePart(
			@RequestParam String dtlId,
			@RequestParam String orderId) 
	{
		service.removePurchaseDtl(dtlId);
		if(service.getDetailsCountByOrderId(orderId)==0) {
			service.updateStatus(orderId, "OPEN");
			//service.updateStatus(orderId, OrderStatus.OPEN.name());
		}
		return "redirect:parts?id="+orderId;
	}
	
	/**
	 * Read OrderId and update status of PO to ORDERED
	 * Finally redirect to screen#2 /part?id=<orderId>
	 */
	 
	@GetMapping("/confirmOrder")
     public String placeOrder(
    		 @RequestParam String orderId) 
	{
		service.updateStatus(orderId, "ORDERED");
		//service.updateStatus(orderId, OrderStatus.ORDERED.name());
		//back to screen#2
    	 return "redirect:parts?id="+orderId;
     }
	
	/**
	 * Generate Invoice Status Change
	 * From ORDERED TO INVOICED
	 */
	@GetMapping("/genInv")
	public String generateInvoice(@RequestParam String id)//order id 
	{
		service.updateStatus(id, "INVOICED");
		//service.updateStatus(id, OrderStatus.INVOICED.name());
		return "redirect:all";//screen#1
	}
	
	/**
	 * print Vendor Invoice PDF based on OrderId
	 */
	@GetMapping("/printInv")
	public ModelAndView showInvoice(@RequestParam String id)//order id
	{
		ModelAndView m=new ModelAndView();
		m.setView(new VendorInvoicePdfView());
		
		m.addObject("dtls", service.getPurchaseDtlsByOrderId(id));
		m.addObject("po", service.getOnePurchaseOrder(id));
		
		return m;
	}
	
}
