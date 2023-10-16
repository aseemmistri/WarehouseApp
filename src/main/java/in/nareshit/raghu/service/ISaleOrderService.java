package in.nareshit.raghu.service;

import java.util.List;
import java.util.Map;

import in.nareshit.raghu.model.SaleDtl;
import in.nareshit.raghu.model.SaleOrder;

public interface ISaleOrderService {
	
	//--Screen#1-
public String saveSaleOrder(SaleOrder so);
public SaleOrder getOneSaleOrder(String id);
public List<SaleOrder> getAllSaleOrders();
public boolean isSaleOrderExist(String id);


//---Screen#2
public String saveSaleDtl(SaleDtl dtl);
public List<SaleDtl> getSaleDtlsByOrderId(String orderId);
public void removeSaleDtl(String id);
public void updateSaleOrderStatusById(String orderId,String status);
public Integer getSaleDtlsCountByOrderId(String orderId);



//for sale
public Map<String,String> getSaleOrderIdAndCodeByStatus(String status);
public void deleteSaleOrder(String id);




}
