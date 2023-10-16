package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.exception.PurchaseOrderNotFound;
import in.nareshit.raghu.model.PurchaseDtl;
import in.nareshit.raghu.model.PurchaseOrder;
import in.nareshit.raghu.repo.PurchaseDtlRepository;
import in.nareshit.raghu.repo.PurchaseOrderRepository;
import in.nareshit.raghu.service.IPurchaseOrderService;
import in.nareshit.raghu.util.MyCollectionUtil;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {
	
	@Autowired
	private PurchaseOrderRepository repo;
	@Autowired
	private PurchaseDtlRepository dtlrepo;

	@Override
	public String savePurchaseOrder(PurchaseOrder po) {
		return repo.save(po).getId();
	}

	@Override
	public List<PurchaseOrder> getAllPurchaseOrders() {
		return repo.findAll();
	}

	@Override
	public PurchaseOrder getOnePurchaseOrder(String id) {
		return repo.findById(id).orElseThrow(
				()->new PurchaseOrderNotFound("Not Found"));
	}
	
	//------Screen#2---------

	@Override
	public String savePurchaseDtl(PurchaseDtl dtl) {
		
		return dtlrepo.save(dtl).getId();
	}

	@Override
	public List<PurchaseDtl> getPurchaseDtlsByOrderId(String orderId) {
		return dtlrepo.getPurchaseDtlsByOrderId(orderId);
	}

	@Override
	public void removePurchaseDtl(String id) {
		dtlrepo.deleteById(id);
		
	}

	@Override
	@Transactional
	public void updateStatus(String orderId, String status) {
		repo.updatePurchaseOrderStatusById(orderId, status);
		
	}

	@Override
	public Integer getDetailsCountByOrderId(String orderId) {
		return dtlrepo.getPurchaseDtlsCountByOrderId(orderId);
	}

	@Override
	public Map<String, String> getPurchaseOrderIdAndCodeByStatus(String status) {
		List<Object[]> list=repo.getPurchaseOrderIdAndCodeByStatus(status);
		return MyCollectionUtil.corvertListToMap(list);
	}

	

}
