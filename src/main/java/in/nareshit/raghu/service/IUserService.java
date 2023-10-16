package in.nareshit.raghu.service;

import java.util.List;
import java.util.Optional;

import in.nareshit.raghu.model.User;

public interface IUserService {

	public String saveUser(User user);
	public User getOneUser(String id);
	public List<User> getAllUsers();
	public void modifyStatus(String id,boolean active);
	public void updatePwd(String id,String encPwd);
	
	public Optional<User> findByEmail(String email);
	public void updateNewOtpById(String id, String otpNew);
	
	
}
