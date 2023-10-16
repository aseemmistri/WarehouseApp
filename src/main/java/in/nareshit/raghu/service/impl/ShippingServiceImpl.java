package in.nareshit.raghu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.model.Shipping;
import in.nareshit.raghu.model.ShippingDtl;
import in.nareshit.raghu.repo.ShippingDtlRepository;
import in.nareshit.raghu.repo.ShippingRepository;
import in.nareshit.raghu.service.IShippingService;

@Component
public class ShippingServiceImpl implements IShippingService {
	
	@Autowired
	private ShippingRepository repo;
	
	@Autowired
	private ShippingDtlRepository dtlRepo;

	@Override
	public String saveShipping(Shipping shipping) {
		return repo.save(shipping).getId();
	}

	@Override
	public List<Shipping> allShipping() {
		return repo.findAll();
	}

	@Override
	public String saveShippingDtl(ShippingDtl shippingDtl) {
		return dtlRepo.save(shippingDtl).getId();
	}

	@Override
	public Shipping getOneShippingById(String id) {
		return repo.findById(id).get();
	}

	@Override
	public List<ShippingDtl> getAllShippingDtlByShippingId(String shippingId) {
		return dtlRepo.getAllShippingDtlsByShippingId(shippingId);
	}

	@Transactional
	@Override
	public Integer updateShippingDtlStatus(String shippingDtlId, String shippingDtlStatus) {
		return dtlRepo.updateShippingDtlStatus(shippingDtlId, shippingDtlStatus);
	}

}
