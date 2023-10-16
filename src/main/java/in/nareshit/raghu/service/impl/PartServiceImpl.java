package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.exception.PartNotFoundException;
import in.nareshit.raghu.model.Part;
import in.nareshit.raghu.repo.PartRepository;
import in.nareshit.raghu.service.IPartService;
import in.nareshit.raghu.util.MyCollectionUtil;

@Service
public class PartServiceImpl implements IPartService {
	
	@Autowired
	private PartRepository repo;
	
	@Override
	public String savePart(Part part) {
		return repo.save(part).getId();
	}

	@Override
	public List<Part> getAllParts() {
		return repo.findAll();
	}

	@Override
	public void deletePart(String id) {
		Part part  = getOnePart(id);
		repo.delete(part);
	}
	
	@Override
	public Part getOnePart(String id) {
		Part part = repo.findById(id)
				.orElseThrow(()-> new PartNotFoundException("Part '"+id+"' Not exist"));
		return part;
	}
	
	@Override
	public void updatePart(Part part) {
		repo.save(part);
	}

	@Override
	public Map<String, String> getPartIdAndCode() {
		List<Object[]> list =repo.getPartIdAndCode();
		return MyCollectionUtil.corvertListToMap(list);
	}
	
	
	
	
}