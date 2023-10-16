package in.nareshit.raghu.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.nareshit.raghu.model.Uom;

public interface IUomService {

	public String saveUom(Uom uom);
	public List<Uom> getAllUoms();
	
	public void updateUom(Uom uom);
	public void deleteUom(String id);
	public Uom getOneUom(String id);
	
	public boolean isUomModelExist(String uomModel);
	public boolean isUomModelExistForEdit(String uomModel,String id);
	public List<Object[]> getUomTypeAndCount();
	
	public Map<String,String> getUomIdAndModel();
	
	public Page<Uom> getAllUoms(Pageable p);
	
	public Page<Uom> findByUomModelContaining(String uomModel,Pageable pageable);
}
