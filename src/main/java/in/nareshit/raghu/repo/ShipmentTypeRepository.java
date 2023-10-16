package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.ShipmentType;

public interface ShipmentTypeRepository 
        extends JpaRepository<ShipmentType, String> 
{
   
	@Query("SELECT COUNT(shipmentCode) FROM ShipmentType WHERE shipmentCode=:shipmentCode")
	Integer getShipmentTypeCodeCount(String shipmentCode);
	
	
	//shipment mode,count(shipmentMode)
	@Query("SELECT shipmentMode, COUNT(shipmentMode) FROM ShipmentType GROUP BY shipmentMode")
	List<Object []> getShipmentModeAndCount();
	
	
	@Query("SELECT id,shipmentCode FROM ShipmentType WHERE enableShipment=:enabled")
	List<Object[]> getShipmentIdAndCodeByEnabled(String enabled);
}
