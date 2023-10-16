package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.SaleOrder;

public interface SaleOrderReposityory extends JpaRepository<SaleOrder,String > {
	
	@Modifying
	@Query("UPDATE SaleOrder SET status=:status WHERE id=:orderId")
	public void updateSaleOrderStatusById(String orderId,String status);
	
	@Query("SELECT id,orderCode FROM SaleOrder WHERE status=:status")
	public List<Object[]> getSaleOrderIdAndCodeByStatus(String status);

}
