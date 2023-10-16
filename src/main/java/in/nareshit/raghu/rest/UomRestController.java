package in.nareshit.raghu.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nareshit.raghu.exception.UomNotFoundException;
import in.nareshit.raghu.model.Uom;
import in.nareshit.raghu.service.IUomService;
import in.nareshit.raghu.util.UomUtil;

@RestController
@RequestMapping("/rest/uom")
public class UomRestController {
	
	private Logger log = LoggerFactory.getLogger(UomRestController.class);
 
	@Autowired
	private IUomService service;
	
	@Autowired
	private UomUtil uomUtil;
	
	//1. Fetch All rows
	@GetMapping("/all")
	public ResponseEntity<?> getAllUom(@PageableDefault(page=0,size=3) Pageable p)
	{
		log.info("ENTERED INTO GET ALL METHOD");
		ResponseEntity<?> resp=null;
		try {
			Page<Uom> list=service.getAllUoms(p);
			log.info("SERVICE METHOD CALLED FOR ALL DATA FETCH");
			resp = new ResponseEntity<Page<Uom>>(list,HttpStatus.OK);
			log.info("SUCCESS PESPONSE CREATED");
		} catch (Exception e) {
			log.error("PROBLEM IN FETCHING DATA {}",e.getMessage());
			resp = new ResponseEntity<String>("Unable to Fetch Uoms",HttpStatus.OK);
			e.printStackTrace();
		}
		log.info("ABOUT TO RETURN FROM METHOD");
		return resp;
	}
	
	//2. Insert Data
	@PostMapping("/save")
	public ResponseEntity<String> saveUom(
			@RequestBody Uom uom)
	{
		log.info("ENTERED INTO SAVE METHOD");
		ResponseEntity<String> resp=null;
		try {
			log.info("SAVE OPERATION IS CALLING");
			String id=service.saveUom(uom);
			log.debug("SAVE WITH ID {}",id);
			String message="UOM "+id+" SAVED";
			resp= new ResponseEntity<String>(message,HttpStatus.CREATED);
			log.info("SUCCESS RESPONSE CONSTRUCT");
		} catch (Exception e) {
			log.error("UNABLE TO SAVE UOM {}",e.getMessage());
			resp= new ResponseEntity<String>("Unable to save Uom",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}
	
	//3. Fetch one row by Id
	@GetMapping("/one/{id}")
	public ResponseEntity<?> fetchUomById(
			@PathVariable String id)
	{
		ResponseEntity<?> resp = null;
		try {
			Uom uom = service.getOneUom(id);
            //resp = new ResponseEntity<Uom>(uom,HttpStatus.OK);
			resp = ResponseEntity.ok(uom);
		}catch (UomNotFoundException unfe) {
			throw unfe;
		}
		catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to fetch Uom",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
				
		return resp;
	}
	
	//4. Delete one row by id
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> removeUomById(
			@PathVariable String id)
	{
		ResponseEntity<String> resp = null;
		try {
			service.deleteUom(id);
			resp = ResponseEntity.ok("UOM DELETED SUCCESSFULLY");
		}catch (UomNotFoundException unfe) {
			throw unfe;
		}catch (DataIntegrityViolationException cvef) {
			throw new DataIntegrityViolationException("We can not delete uom! used by other entity");
		}
		catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to delete Uom",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}
	
	//5. update data
	@PutMapping("/modify/{id}")
	public ResponseEntity<String> updateUom(
			@PathVariable String id,
			@RequestBody Uom uom)
	{
		ResponseEntity<String> resp = null;
	    try {
			Uom dbUom = service.getOneUom(id);
			uomUtil.copyNonNullValues(dbUom, uom);
			service.updateUom(dbUom);
			resp = new ResponseEntity<String>("Uom Update",HttpStatus.OK);
	    }catch (UomNotFoundException unfe) {
			throw unfe;
		}
	    catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to update Uom",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		
		return resp;
	}
	
}
