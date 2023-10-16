package in.nareshit.raghu.service;

import java.util.List;
import java.util.Map;

import in.nareshit.raghu.model.Part;

public interface IPartService {

	public String savePart(Part part);
	public List<Part> getAllParts();
	
	public void updatePart(Part part);
	public void deletePart(String id);
	public Part getOnePart(String id);
	
	Map<String,String> getPartIdAndCode();
	
}
