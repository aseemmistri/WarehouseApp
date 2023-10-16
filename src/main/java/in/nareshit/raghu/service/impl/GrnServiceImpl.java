package in.nareshit.raghu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.model.Grn;
import in.nareshit.raghu.model.GrnDtl;
import in.nareshit.raghu.repo.GrnDtlRepository;
import in.nareshit.raghu.repo.GrnRepository;
import in.nareshit.raghu.service.IGrnService;

@Component
public class GrnServiceImpl implements IGrnService {
	
	@Autowired
	private GrnRepository repo;
	
	@Autowired
	private GrnDtlRepository dtlRepo;

	@Override
	public String saveGrn(Grn grn) {
		return repo.save(grn).getId();
	}

	@Override
	public List<Grn> getAllGrn() {
		return repo.findAll();
	}

	@Override
	public String saveGrnDtl(GrnDtl grnDtl) {
		return dtlRepo.save(grnDtl).getId();
	}

	@Override
	public List<GrnDtl> getAllGrnDtlsByGrnId(String grnId) {
		return dtlRepo.getAllGrnDtlsByGrnId(grnId);
	}

	@Override
	public Grn getOneGrnbyId(String id) {
		return repo.findById(id).get();
	}

	@Transactional
	@Override
	public Integer updateGrnDtlsStatus(String grnDtlId, String grnDtlStatus) {
		return dtlRepo.updateGrnDtlsStatus(grnDtlId, grnDtlStatus);
	}

}
