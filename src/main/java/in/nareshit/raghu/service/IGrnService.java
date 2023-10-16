package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.model.Grn;
import in.nareshit.raghu.model.GrnDtl;

public interface IGrnService {
	
	public String saveGrn(Grn grn);
	public List<Grn> getAllGrn();
	public Grn getOneGrnbyId(String id);
	
	
	public String saveGrnDtl(GrnDtl grnDtl);
	
	
	//Screen#2
	public List<GrnDtl> getAllGrnDtlsByGrnId(String grnId);
	public Integer updateGrnDtlsStatus(String grnDtlId,String grnDtlStatus);

}
