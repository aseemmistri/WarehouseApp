package in.nareshit.raghu.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.nareshit.raghu.model.ShipmentType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShipmentTypePdfView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response)
					throws Exception 
	{
		//file name
	response.addHeader("Content-Disposition","attachment;filename=SHIPMENTTYPE.pdf");
	
	//read data from Controller using Model
	@SuppressWarnings("unchecked")
	List<ShipmentType> list=(List<ShipmentType>) model.get("list");
	//--TITLE----
	Font titleFont = new Font(Font.HELVETICA,30,Font.BOLD,Color.BLUE);
	Paragraph title=new Paragraph("SHIPMENT TYPES",titleFont);
	title.setAlignment(Element.ALIGN_CENTER);
	
	//-----DATE AND TIME----
	Font dateFont = new Font(Font.HELVETICA,13,Font.NORMAL,Color.BLACK);
	// paragraph(String,font)
	Paragraph date=new Paragraph(new Date().toString(),dateFont);
	date.setAlignment(Element.ALIGN_RIGHT);
	
	//---table---
	PdfPTable table =new PdfPTable(6);
	table.setSpacingBefore(40.0f);
	table.setSpacingAfter(35.0f);
	table.setTotalWidth(new float[] {1.0f,1.5f,2.5f,2.0f,2.0f,3.5f});
	
	Font headingFont = new Font(Font.HELVETICA,15,Font.BOLD, new Color(140,6,251));
	//addCell (phrase(String,font))	
	table.addCell(new Phrase("ID",headingFont));
	table.addCell(new Phrase("MODE",headingFont));
	table.addCell(new Phrase("CODE",headingFont));
	table.addCell(new Phrase("ENABLE",headingFont));
	table.addCell(new Phrase("GRADE",headingFont));
	table.addCell(new Phrase("DESCRIPTION",headingFont));
	
	
	Font dataFont = new Font(Font.HELVETICA,13,Font.NORMAL,Color.BLACK);
	for(ShipmentType st : list) {
		table.addCell(new Phrase(String.valueOf(st.getId()),dataFont));
		table.addCell(new Phrase(st.getShipmentMode(),dataFont));
		table.addCell(new Phrase(st.getShipmentCode(),dataFont));
		table.addCell(new Phrase(st.getEnableShipment(),dataFont));
		table.addCell(new Phrase(st.getShipmentGrade(),dataFont));
		table.addCell(new Phrase(st.getDescription(),dataFont));
	}
	document.add(title);
	document.add(table);
	document.add(date);
	}

}
