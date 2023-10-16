
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

import in.nareshit.raghu.model.PurchaseDtl;
import in.nareshit.raghu.model.PurchaseOrder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VendorInvoicePdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model,
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception 
	{
	  //  Header for file name
		response.addHeader("Content-Disposition", "attachment;filename=VendorInvoice.pdf");
		
		PurchaseOrder po=(PurchaseOrder)model.get("po");
		@SuppressWarnings("unchecked")
		List<PurchaseDtl> dtls=(List<PurchaseDtl>)model.get("dtls");
		
		//create the element
				Font font=new Font(Font.TIMES_ROMAN, 22, Font.BOLD,Color.BLUE);
				Paragraph p= new Paragraph("VENDOR INVOICE PDF",font);
				p.setSpacingAfter(10.0f);
				p.setAlignment(Element.ALIGN_CENTER);
				//add element to document
				document.add(p);
		
		double finalCost=0.0;
		for(PurchaseDtl dtl:dtls) {
			finalCost += dtl.getPart().getPartCost() * dtl.getQty();
		}
		
		PdfPTable header=new PdfPTable(4);
		header.setSpacingAfter(15.0f);
		       
		header.addCell("VENDOR CODE");
		header.addCell(po.getVendor().getUserCode());
		
		header.addCell("ORDER CODE");
		header.addCell(po.getOrderCode());
		
		header.addCell("FINAL COST");
		header.addCell(String.valueOf(finalCost ));
		
		header.addCell("SHIPMENT CODE");
		header.addCell(po.getShipmentType().getShipmentCode());
		
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
		for(PurchaseDtl dtl:dtls) {
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
