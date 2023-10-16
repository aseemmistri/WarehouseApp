package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.exception.SaleOrderNotFoundException;
import in.nareshit.raghu.model.SaleDtl;
import in.nareshit.raghu.model.SaleOrder;
import in.nareshit.raghu.repo.SaleDtlRepository;
import in.nareshit.raghu.repo.SaleOrderReposityory;
import in.nareshit.raghu.service.ISaleOrderService;
import in.nareshit.raghu.util.MyCollectionUtil;
@Component
public class SaleOrderServiceImpl implements ISaleOrderService {
	
	@Autowired
	private SaleOrderReposityory repo;
	
	@Autowired
	private SaleDtlRepository dtlrepo;

	@Override
	public String saveSaleOrder(SaleOrder so) {
		return repo.save(so).getId();
	}

	@Override
	public List<SaleOrder> getAllSaleOrders() {
		return repo.findAll();
	}

	@Override
	public SaleOrder getOneSaleOrder(String id) {
		return repo.findById(id).orElseThrow(
				()->new SaleOrderNotFoundException("Sale Order Not Found"));
	}
	
	//---------Screen#2------------------

	@Override
	public String saveSaleDtl(SaleDtl dtl) {
		return dtlrepo.save(dtl).getId();
	}

	@Override
	public List<SaleDtl> getSaleDtlsByOrderId(String orderId) {
		return dtlrepo.getSaleDtlsByOrderId(orderId);
	}

	@Override
	public void removeSaleDtl(String id) {
		dtlrepo.deleteById(id);
		
	}

	@Transactional
	@Override
	public void updateSaleOrderStatusById(String orderId, String status) {
		repo.updateSaleOrderStatusById(orderId, status);
		
	}

	@Override
	public Integer getSaleDtlsCountByOrderId(String orderId) {
		return dtlrepo.getSaleDtlsCountByOrderId(orderId);
	}

	@Override
	public Map<String, String> getSaleOrderIdAndCodeByStatus(String status) {
		List<Object[]> list=repo.getSaleOrderIdAndCodeByStatus(status); 
		return MyCollectionUtil.corvertListToMap(list);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isSaleOrderExist(String id) {
		return repo.existsById(id);
	}

	@Override
	@Transactional()
	public void deleteSaleOrder(String id) {
		Optional<SaleOrder> opt = repo.findById(id);
		if (!opt.isPresent())
			throw new SaleOrderNotFoundException("Sale Order '" + id + "'Not Found");
		repo.deleteById(id);
		
	}

	
	

}
