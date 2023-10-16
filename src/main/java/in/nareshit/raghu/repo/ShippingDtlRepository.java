package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.ShippingDtl;

public interface ShippingDtlRepository extends JpaRepository<ShippingDtl, String> {

	
	@Query("SELECT dtl FROM ShippingDtl dtl JOIN dtl.shipping as shipping WHERE shipping.id=:shippingId")
	public List<ShippingDtl> getAllShippingDtlsByShippingId(String shippingId);
	
	
	@Modifying
	@Query("UPDATE ShippingDtl SET status=:shippingDtlStatus WHERE id=:shippingDtlId")
	public Integer updateShippingDtlStatus(String shippingDtlId,String shippingDtlStatus);
}
