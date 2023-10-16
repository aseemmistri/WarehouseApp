package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.Uom;

public interface UomRepository 
	extends JpaRepository<Uom, String>
{
	//@Query("SELECT U FROM Uom WHERE U.uomModel like %?1%")
	Page<Uom> findByUomModelContaining(String uomModel,Pageable pageable);
	
	
    // Reg Check
	@Query("SELECT COUNT(uomModel) FROM Uom WHERE uomModel=:uomModel")
	Integer getUomModelCount(String uomModel);
	
	
	// Edit Check
		@Query("SELECT COUNT(uomModel) FROM Uom WHERE uomModel=:uomModel and id!=:id")
		Integer getUomModelCountForNotId(String uomModel,String id);
	
	@Query("SELECT uomType, COUNT(uomType) FROM Uom GROUP BY uomType")
	List<Object[]> getUomTypeAndCount();
	
	@Query("SELECT id,uomModel FROM Uom")
	List<Object[]> getUomIdAndModel();
}
