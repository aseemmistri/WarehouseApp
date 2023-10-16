package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.SaleDtl;

public interface SaleDtlRepository extends JpaRepository<SaleDtl, String> {
	
	@Query("SELECT dtl FROM SaleDtl dtl JOIN dtl.order as order WHERE order.id=:orderId")
	public List<SaleDtl> getSaleDtlsByOrderId(String orderId);
	
	@Query("SELECT count(dtl.id) FROM SaleDtl dtl JOIN dtl.order as order WHERE order.id=:orderId")
	public Integer getSaleDtlsCountByOrderId(String orderId);

}
