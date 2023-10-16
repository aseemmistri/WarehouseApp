package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.exception.OrderMethodNotFoundException;
import in.nareshit.raghu.model.OrderMethod;
import in.nareshit.raghu.repo.OrderMethodRepository;
import in.nareshit.raghu.service.IOrderMethodService;
import in.nareshit.raghu.util.MyCollectionUtil;
@Service
public class OrderMethodServiceImpl implements IOrderMethodService {
	
	@Autowired
	private OrderMethodRepository repo;

	@Override
	public String saveOrderMethod(OrderMethod om) {
		return repo.save(om).getId();
	}

	@Override
	public void updateOrderMethod(OrderMethod om) {
		repo.save(om);
		
	}

	@Override
	public void deleteOrderMethod(String id) {
		OrderMethod om=getOneOrderMethod(id);
		repo.delete(om);
	}

	@Override
	public OrderMethod getOneOrderMethod(String id) {
		OrderMethod om = repo.findById(id)
		         .orElseThrow(
				  ()-> new OrderMethodNotFoundException(
						  "ShipmentType '"+id+"' not exist"));
		return om;
	}
	@Override
	public List<OrderMethod> getAllOrderMethod() {
		
		return repo.findAll();
	}

	 @Override
	public Map<String, String> getOrderMethodIdAndCodeByMode(String orderMode) {
		List<Object[]> list=repo.getOrderMethodIdAndCodeByMode(orderMode);
		Map<String,String> map=MyCollectionUtil.corvertListToMap(list);
		return map;
	}

}
