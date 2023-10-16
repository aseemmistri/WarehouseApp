package in.nareshit.raghu.service;

import java.util.List;
import java.util.Map;

import in.nareshit.raghu.model.WhUserType;

public interface IWhUserTypeService {

	String saveWhUserType(WhUserType wh);
	void updateWhUserType(WhUserType wh);
	void deleteWhUserType(String id);
	WhUserType getOneWhUserType(String id);
	List<WhUserType> getAllWhUserType();
	
	public boolean isWhUserEmailExist(String email);
	
	public Map<String,String> getWhUserTypeByUserType(String userType);
}
