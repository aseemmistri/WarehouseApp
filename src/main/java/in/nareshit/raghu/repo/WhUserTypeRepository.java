package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.WhUserType;

public interface WhUserTypeRepository 
extends JpaRepository<WhUserType, String> 
{
	
	@Query("SELECT COUNT(userEmail) FROM WhUserType WHERE userEmail=:email")
	Integer getUserEmailCount(String email);
	
	@Query("SELECT id,userCode FROM WhUserType WHERE userType=:userType")
	List<Object[]> getWhUserTypeByUserType(String userType);
}
