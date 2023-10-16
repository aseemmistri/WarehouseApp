package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.exception.UomNotFoundException;
import in.nareshit.raghu.model.Uom;
import in.nareshit.raghu.repo.UomRepository;
import in.nareshit.raghu.service.IUomService;
import in.nareshit.raghu.util.MyCollectionUtil;

@Service
public class UomServiceImpl implements IUomService {

	@Autowired
	private UomRepository repo;

	@Override
	public String saveUom(Uom uom) {
		return repo.save(uom).getId();
	}

	@Override
	public List<Uom> getAllUoms() {
		return repo.findAll();
	}

	@Override
	public void deleteUom(String id) {
		Uom uom  = getOneUom(id);
		repo.delete(uom);
	}

	@Override
	public Uom getOneUom(String id) {
		Uom uom = repo.findById(id)
				.orElseThrow(()-> new UomNotFoundException("Uom '"+id+"' Not exist"));
		return uom;
	}

	@Override
	public void updateUom(Uom uom) {
		repo.save(uom);
	}


	@Override
	public boolean isUomModelExist(String uomModel) {
		return repo.getUomModelCount(uomModel) > 0 ? true : false;
	}

	@Override
	public List<Object[]> getUomTypeAndCount() {
		return repo.getUomTypeAndCount();
	}

	@Override
	public Map<String, String> getUomIdAndModel() {
		List<Object[]> list = repo.getUomIdAndModel();
        Map<String,String> map=MyCollectionUtil.corvertListToMap(list);
		return map;
	}

	@Override
	public boolean isUomModelExistForEdit(String uomModel, String id) {
		//return repo.getUomModelCountForNotId(uomModel, id) > 0 ? true : false;
		return repo.getUomModelCountForNotId(uomModel, id) > 0 ;
	}

	@Override
	public Page<Uom> getAllUoms(Pageable p) {
		return repo.findAll(p);
	}

	@Override
	public Page<Uom> findByUomModelContaining(String uomModel, Pageable pageable) {
		return repo.findByUomModelContaining(uomModel, pageable);
	}

}