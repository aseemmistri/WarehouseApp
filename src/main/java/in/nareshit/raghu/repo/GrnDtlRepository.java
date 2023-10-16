package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.GrnDtl;

public interface GrnDtlRepository extends JpaRepository<GrnDtl, String> {
	
	
	@Query("SELECT dtl FROM GrnDtl dtl JOIN dtl.grn as grn WHERE grn.id=:grnId")
	public List<GrnDtl> getAllGrnDtlsByGrnId(String grnId);
	
	@Modifying
	@Query("UPDATE GrnDtl SET status=:grnDtlStatus WHERE id=:grnDtlId")
	public Integer updateGrnDtlsStatus(String grnDtlId,String grnDtlStatus);

}
