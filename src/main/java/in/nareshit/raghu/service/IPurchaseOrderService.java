package in.nareshit.raghu.service;

import java.util.List;
import java.util.Map;

import in.nareshit.raghu.model.PurchaseDtl;
import in.nareshit.raghu.model.PurchaseOrder;

public interface IPurchaseOrderService {
	
	//Screen#1
	public String savePurchaseOrder(PurchaseOrder po);
	public PurchaseOrder getOnePurchaseOrder(String id);
	public List<PurchaseOrder> getAllPurchaseOrders();
	
	//screen#2
	public String savePurchaseDtl(PurchaseDtl dtl);
	public List<PurchaseDtl> getPurchaseDtlsByOrderId(String orderId);
	public void removePurchaseDtl(String id);
	public void updateStatus(String orderId,String status);
	public Integer getDetailsCountByOrderId(String orderId);
	
	
	//for Grn
	public Map<String,String> getPurchaseOrderIdAndCodeByStatus(String status);


}
