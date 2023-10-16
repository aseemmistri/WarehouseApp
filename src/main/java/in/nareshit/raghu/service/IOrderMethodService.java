package in.nareshit.raghu.service;

import java.util.List;
import java.util.Map;

import in.nareshit.raghu.model.OrderMethod;

public interface IOrderMethodService {

	String saveOrderMethod(OrderMethod om);
	void updateOrderMethod(OrderMethod om);
	void deleteOrderMethod(String id);
	OrderMethod getOneOrderMethod(String id);
	List<OrderMethod> getAllOrderMethod();
	
	public Map<String,String> getOrderMethodIdAndCodeByMode(String orderMode);
	
}
