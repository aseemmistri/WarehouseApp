package in.nareshit.raghu.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Modifying
	@Query("UPDATE in.nareshit.raghu.model.User SET active=:active WHERE id=:id")
	public void updateStatus(String id,boolean active);
	
	@Modifying
	@Query("UPDATE in.nareshit.raghu.model.User SET pwd=:encPwd WHERE id=:id")
	public void updatePwd(String id,String encPwd);
	
	Optional<User> findByEmail(String email);

	@Modifying
	@Query("UPDATE in.nareshit.raghu.model.User SET otp=:otpNew WHERE id=:id")
	public void updateNewOtpById(String id, String otpNew);

}
