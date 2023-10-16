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

import in.nareshit.raghu.model.SaleDtl;
import in.nareshit.raghu.model.SaleOrder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomerInvoicePdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception 
	{
		//Header for file name
		response.addHeader("Content-Disposition","attachment;filename=CustomerInvoice.pdf");
		
		SaleOrder so=(SaleOrder)model.get("so");
		
		@SuppressWarnings("unchecked")
		List<SaleDtl> dtls=(List<SaleDtl>)model.get("dtls");
		
		//create the element 
		Font font=new Font(Font.TIMES_ROMAN, 22, Font.BOLD, Color.BLUE);
		Paragraph p=new Paragraph("CUSTOMER INVOICE PDF",font);
		p.setSpacingAfter(10.0f);
		p.setAlignment(Element.ALIGN_CENTER);
		//add element to document
		document.add(p);
		
		double finalCost=0.0f;
		for(SaleDtl dtl:dtls) {
			finalCost=dtl.getPart().getPartCost()*dtl.getQty();
		}
	
		PdfPTable header =new PdfPTable(4);
		header.setSpacingAfter(15.0f);
		
		header.addCell("Customer Code");
		header.addCell(so.getCustomer().getUserCode());
		
		header.addCell("Order Code");
		header.addCell(so.getOrderCode());
		
		header.addCell("Final Cost");
		header.addCell(String.valueOf(finalCost));
		
		header.addCell("Shipment Code");
		header.addCell(so.getShipmentType().getShipmentCode());
		
		document.add(header);
		
		PdfPTable partsTab=new PdfPTable(6);
		partsTab.setWidths(new float[]{3.5f, 2.0f, 1.5f, 1.5f, 2.5f, 2.0f});
		header.setSpacingAfter(15.0f);
		Font headingFont = new Font(Font.HELVETICA,10,Font.BOLD, Color.BLACK);
		partsTab.addCell(new Phrase("CODE",headingFont));
		partsTab.addCell(new Phrase("COST",headingFont));
		partsTab.addCell(new Phrase("QTY",headingFont));
		partsTab.addCell(new Phrase("GST%",headingFont));
		partsTab.addCell(new Phrase("GSTAMOUNT",headingFont));
		partsTab.addCell(new Phrase("LINEVALUE",headingFont));
		
		
		Font dataFont = new Font(Font.HELVETICA,10,Font.NORMAL,Color.BLACK);
		for(SaleDtl dtl:dtls) {
			partsTab.addCell(new Phrase(dtl.getPart().getPartCode(),dataFont));
			partsTab.addCell(new Phrase(String.valueOf(dtl.getPart().getPartCost()),dataFont));
			partsTab.addCell(new Phrase(String.valueOf(dtl.getQty()),dataFont));
			partsTab.addCell(new Phrase(String.valueOf(dtl.getPart().getGstSlob()),dataFont));
			partsTab.addCell(new Phrase(String.valueOf((dtl.getPart().getPartCost()*dtl.getPart().getGstSlob()/100)*dtl.getQty()),dataFont));
			partsTab.addCell(new Phrase(String.valueOf((dtl.getPart().getPartCost()*dtl.getQty())+(dtl.getPart().getPartCost()*dtl.getPart().getGstSlob()/100)*dtl.getQty()),dataFont));
		}
		
		document.add(partsTab);
		
		document.add(new Paragraph(new Date().toString()));

	}

}
