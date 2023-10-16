package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.OrderMethod;

public interface OrderMethodRepository 
      extends JpaRepository<OrderMethod, String>  
{
	@Query("SELECT id,orderCode FROM OrderMethod WHERE orderMode=:orderMode")
	List<Object[]> getOrderMethodIdAndCodeByMode(String orderMode);

}
