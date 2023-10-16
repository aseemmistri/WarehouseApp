package in.nareshit.raghu.service;

import java.util.List;
import java.util.Optional;

import in.nareshit.raghu.model.Document;

public interface IDocumentService {

	void saveDocument(Document document);
	List<Object[]> getDocumentIdAndNames();
	Optional<Document> getOneDocument(Integer id);
}
