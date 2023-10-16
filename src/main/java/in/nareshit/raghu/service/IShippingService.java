package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.model.Shipping;
import in.nareshit.raghu.model.ShippingDtl;

public interface IShippingService {
	
	public String saveShipping(Shipping shipping);
	public List<Shipping> allShipping();
	public Shipping getOneShippingById(String id);
	
	
	public String saveShippingDtl(ShippingDtl shippingDtl);
	
	//Screen#2
	public List<ShippingDtl> getAllShippingDtlByShippingId(String shippingId);
	public Integer updateShippingDtlStatus(String shippingDtlId,String shippingDtlStatus);
}
