package in.nareshit.raghu.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.nareshit.raghu.model.ShipmentType;

public interface IShipmentTypeService {
	
	String saveShipmentType(ShipmentType st);
	void updateShipmentType(ShipmentType st);
	void deleteshipmentType(String id);
	ShipmentType getOneShipmentType(String id);
	List<ShipmentType> getAllShipmentType();

	boolean isShipmentTypeCodeExist(String code);
	
	List<Object []> getShipmentModeAndCount();
	
	Map<String,String> getShipmentIdAndCodeByEnabled(String enabled);
	
	Page<ShipmentType> getAllShipmentType(Pageable p);
}
