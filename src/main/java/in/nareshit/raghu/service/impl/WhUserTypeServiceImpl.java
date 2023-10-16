package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.exception.WhUserTypeNotFoundException;
import in.nareshit.raghu.model.WhUserType;
import in.nareshit.raghu.repo.WhUserTypeRepository;
import in.nareshit.raghu.service.IWhUserTypeService;
import in.nareshit.raghu.util.MyCollectionUtil;
@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService {
	
	@Autowired
	private WhUserTypeRepository repo;

	@Override
	public String saveWhUserType(WhUserType wh) {
		return repo.save(wh).getId();
	}

	@Override
	public void updateWhUserType(WhUserType wh) {
		repo.save(wh);

	}

	@Override
	public void deleteWhUserType(String id) {
       WhUserType wh=getOneWhUserType(id);
       repo.delete(wh);
	}

	@Override
	public WhUserType getOneWhUserType(String id) {
		WhUserType wh=repo.findById(id).orElseThrow(
				()->new WhUserTypeNotFoundException("Wh User "+id+" not exist"));
		return wh;
	}
	
	@Override
	public List<WhUserType> getAllWhUserType() {
		return repo.findAll();
	}

	@Override
	public boolean isWhUserEmailExist(String email) {
		
		//return repo.getUserEmailCount(email) > 0 ? true : false;
		return repo.getUserEmailCount(email) > 0;
	}

	@Override
	public Map<String, String> getWhUserTypeByUserType(String userType) {
		List<Object[]> list=repo.getWhUserTypeByUserType(userType);
		Map<String,String> map=MyCollectionUtil.corvertListToMap(list);
		return map;
	}

}
