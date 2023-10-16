package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.PurchaseDtl;

public interface PurchaseDtlRepository extends JpaRepository<PurchaseDtl, String> {
	
	@Query("SELECT dtl FROM PurchaseDtl dtl JOIN dtl.order as order WHERE order.id=:orderId")
	public List<PurchaseDtl> getPurchaseDtlsByOrderId(String orderId);

	
	@Query("SELECT count(dtl.id) FROM PurchaseDtl dtl JOIN dtl.order as order WHERE order.id=:orderId")
	public Integer getPurchaseDtlsCountByOrderId(String orderId);
}
