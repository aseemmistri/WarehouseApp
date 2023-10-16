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

import in.nareshit.raghu.model.SaleDtl;
import in.nareshit.raghu.model.SaleOrder;
import in.nareshit.raghu.service.IPartService;
import in.nareshit.raghu.service.ISaleOrderService;
import in.nareshit.raghu.service.IShipmentTypeService;
import in.nareshit.raghu.service.IWhUserTypeService;
import in.nareshit.raghu.view.CustomerInvoicePdfView;

@Controller
@RequestMapping("/so")
public class SaleOrderController {
	
	@Autowired
	private ISaleOrderService service;
	
	@Autowired
	private IShipmentTypeService shipmentService;
	
	@Autowired
	private IWhUserTypeService whservice;
	
	@Autowired
	private IPartService partService;
	
	//register page dropdown
	private void addDynamicUiComponents(Model model) {
		model.addAttribute("shipmenttypes", shipmentService.getShipmentIdAndCodeByEnabled("YES"));
		model.addAttribute("customers", whservice.getWhUserTypeByUserType("Customer"));
	}
	
	//1. show register page
		@GetMapping("/register")
		public String showReg(Model model) {
			SaleOrder so =new SaleOrder();
			so.setStatus("SALE-OPEN");
			model.addAttribute("saleOrder",so);
			addDynamicUiComponents(model);
			return "SaleOrderRegister";
		}
	
		
		//2. save data
		@PostMapping("/save")
		public String save(
				@ModelAttribute SaleOrder saleOrder,
				Model model)
		{
			String id = service.saveSaleOrder(saleOrder);
			model.addAttribute("message","Sale Order "+id+" saved");
			SaleOrder so=new SaleOrder();
			so.setStatus("SALE-OPEN");
			model.addAttribute("SaleOrder",so);
			addDynamicUiComponents(model);
			return "SaleOrderRegister";
		} 
		
		//3. display POs
		@GetMapping("/all")
		public String showAll(Model model) {
			List<SaleOrder> list =service.getAllSaleOrders();
			model.addAttribute("list", list);
			return "SaleOrderData";
		}
		
		// 4.delete record
		@GetMapping("/delete/{id}")
		public String remove(@RequestParam String id, Model model) {
			String msg = null;
			// invoke service
			if (service.isSaleOrderExist(id)) {
				service.deleteSaleOrder(id);
				msg = "Sale Order '" + id + "' Type deleted !";
			} else {

				msg = "Sale Order'" + id + "' Not Existed !";
			}
			// display other records
			List<SaleOrder> list = service.getAllSaleOrders();
			model.addAttribute("list", list);

			// send confirmation to UI
			model.addAttribute("message", msg);
			return "SaleOrderData";
		}
		
		//---------------Method for Screen#2 sale order-------------------//
		//--section#2---
		private void addDynamicUiComponentsForParts(Model model) {
			model.addAttribute("parts", partService.getPartIdAndCode());
		}
		
		
		
		@GetMapping("/parts")
		public String showPartsPage(
				@RequestParam String id,
				Model model) 
		{
			//--section#1---
			//get purchase order by id
			SaleOrder so=service.getOneSaleOrder(id);
			model.addAttribute("so", so);
			
			
			
			//---section#2---
			//dynamic DropDown 
			addDynamicUiComponentsForParts(model);
			
			//send Form Backing Object
			model.addAttribute("saleDtl",new SaleDtl());
			
			//----section#4--------
			//show parts added to Order based on orderid
			List<SaleDtl> dtls=service.getSaleDtlsByOrderId(id);//OrderId
			//send data to ui
			model.addAttribute("dtls", dtls);
			
			return "SaleOrderParts";
		}
		
		/**
		 * On click add part button
		 * Read Form data as SaleDtl
		 * save into Db using service method
		 * redirect to same UI with /parts?id=<OrderId>
		 */
		
		@PostMapping("/add")
		public String addPart(@ModelAttribute SaleDtl saleDtl) 
		{
			String orderId=saleDtl.getOrder().getId();
			service.saveSaleDtl(saleDtl);
			
			//updata order status
			service.updateSaleOrderStatusById(orderId,"SALE-READY");
			//service.updateSaleOrderStatusById(orderId, SaleStatus.SALE_READY.name());
			
			
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
			service.removeSaleDtl(dtlId);
			if(service.getSaleDtlsCountByOrderId(orderId)==0) {
				service.updateSaleOrderStatusById(orderId, "SALE-OPEN");
				//service.updateSaleOrderStatusById(orderId, SaleStatus.SALE_OPEN.name());
			}
			return "redirect:parts?id="+orderId;
		}
		
		/**
		 * Read OrderId and update status of PO to ORDERED
		 * Finally redirect to screen#2 /part?id=<orderId>
		 */
		@GetMapping("/confirmOrder")
		public String plaseOrder(
				@RequestParam String orderId)
		{
			service.updateSaleOrderStatusById(orderId, "SALE-CONFIRM");
			//service.updateSaleOrderStatusById(orderId, SaleStatus.SALE_CONFIRM.name());
			return "redirect:parts?id="+orderId;
		}
		
		/**
		 * Generate Invoice Status Change
		 * From ORDERED TO INVOICED
		 */
		@GetMapping("/genInv")
		public String generateInvoice(@RequestParam String id)//order id 
		{
			service.updateSaleOrderStatusById(id, "SALE-INVOICED");
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
			m.setView(new CustomerInvoicePdfView());
			
			m.addObject("dtls", service.getSaleDtlsByOrderId(id));
			m.addObject("so", service.getOneSaleOrder(id));
			
			return m;
		}
}
