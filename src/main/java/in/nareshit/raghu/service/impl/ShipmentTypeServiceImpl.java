package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.exception.ShipmentTypeNotFoundException;
import in.nareshit.raghu.model.ShipmentType;
import in.nareshit.raghu.repo.ShipmentTypeRepository;
import in.nareshit.raghu.service.IShipmentTypeService;
import in.nareshit.raghu.util.MyCollectionUtil;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {
	
	@Autowired
	private ShipmentTypeRepository repo;

	@Override
	public String saveShipmentType(ShipmentType st) {
		return repo.save(st).getId();
	}

	@Override
	public void updateShipmentType(ShipmentType st) {
        repo.save(st);
	}

	@Override
	public void deleteshipmentType(String id) {
      ShipmentType st= getOneShipmentType(id);
      repo.delete(st);
	}

	@Override
	public ShipmentType getOneShipmentType(String id) {
		ShipmentType st= repo.findById(id)
		         .orElseThrow(
				  ()-> new ShipmentTypeNotFoundException(
						  "ShipmentType '"+id+"' not exist"));
		return st;
	}

	@Override
	public List<ShipmentType> getAllShipmentType() 
	{
		return repo.findAll();
	}

	@Override
	public boolean isShipmentTypeCodeExist(String code) {
		
		return repo.getShipmentTypeCodeCount(code) > 0 ? true : false;
	}

	@Override
	public List<Object[]> getShipmentModeAndCount() {
		return repo.getShipmentModeAndCount();
	}

	@Override
	public Map<String, String> getShipmentIdAndCodeByEnabled(String enabled) {
		List<Object[]> list=repo.getShipmentIdAndCodeByEnabled(enabled);
		Map<String,String> map=MyCollectionUtil.corvertListToMap(list);
		return map;
	}

	@Override
	public Page<ShipmentType> getAllShipmentType(Pageable p) {
		return repo.findAll(p);
	}

}
