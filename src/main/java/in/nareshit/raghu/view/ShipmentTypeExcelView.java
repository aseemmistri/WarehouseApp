package in.nareshit.raghu.view;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import in.nareshit.raghu.model.ShipmentType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShipmentTypeExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception 
	{
		//set file name
		response.addHeader("Content-Disposition", "attachment;filename=shipmentType.xlsx");
		@SuppressWarnings("unchecked")
		List<ShipmentType> list=(List<ShipmentType>) model.get("list");
		
		//create sheet
		Sheet sheet=workbook.createSheet("SHIPMENTTYPE");
		setHeader(sheet);
		setBody(sheet,list);

	}

	private void setBody(Sheet sheet, List<ShipmentType> list) {
		int rowNom=1;
		for(ShipmentType shipmentType : list) {
			Row row=sheet.createRow(rowNom++);
			row.createCell(0).setCellValue(shipmentType.getId());
			row.createCell(1).setCellValue(shipmentType.getShipmentMode());
			row.createCell(2).setCellValue(shipmentType.getShipmentCode());
			row.createCell(3).setCellValue(shipmentType.getEnableShipment());
			row.createCell(4).setCellValue(shipmentType.getShipmentGrade());
			row.createCell(5).setCellValue(shipmentType.getDescription());
		}
		
	}

	private void setHeader(Sheet sheet) {
		Row row=sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("MODE");
		row.createCell(2).setCellValue("CODE");
		row.createCell(3).setCellValue("ENABLE");
		row.createCell(4).setCellValue("GRADE");
		row.createCell(5).setCellValue("DESCRIPTION");
	}

}
