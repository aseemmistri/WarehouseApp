package in.nareshit.raghu.view;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import in.nareshit.raghu.model.Uom;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UomExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook,
			HttpServletRequest request,
			HttpServletResponse response)
					throws Exception 
	{
		//set file name
		response.addHeader("Content-Disposition","attachment;filename=uoms.xlsx");
		
		//read data from Model
		@SuppressWarnings("unchecked")
		List<Uom> list=(List<Uom>) model.get("list");
		
		Sheet sheet=workbook.createSheet("UOMS");
		setHeader(sheet);
		setBody(sheet,list);
	
	}
	
	private void setBody(Sheet sheet, List<Uom> list) {
		int rowNom =1;
		for(Uom uom : list) {
		Row row =sheet.createRow(rowNom++);
		row.createCell(0).setCellValue(uom.getId());
		row.createCell(1).setCellValue(uom.getUomType());
		row.createCell(2).setCellValue(uom.getUomModel());
		row.createCell(3).setCellValue(uom.getDescription());
		}
	}

	private void setHeader(Sheet sheet) {
		Row row =sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("TYPE");
		row.createCell(2).setCellValue("MODEL");
		row.createCell(3).setCellValue("DESCRIPTION");
	}
}
