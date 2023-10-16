package in.nareshit.raghu.util;

import java.awt.Color;
import java.io.File;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class ShipmentTypeUtil {
	
	//generate Pie chart
	@SuppressWarnings("unchecked")
	public void generatePie(String path,List<Object[]> data) {
		//a.create Data Set
		@SuppressWarnings("rawtypes")
		DefaultPieDataset dataset=new DefaultPieDataset();
		//b. add data to dataset Ket(String) value(Double)
		for(Object[] ob:data) {
			dataset.setValue(String.valueOf(ob[0]),Double.valueOf(ob[1].toString()));
		}
		
		//b. create JFreeChart object using ChartFactory
		JFreeChart chart = ChartFactory.createPieChart("SHIPMENT TYPE MODE COUNT", dataset);
		
		@SuppressWarnings("rawtypes")
		PiePlot plot = (PiePlot) chart.getPlot();
		
		//plot.setSectionPaint("AIR",Color.ORANGE);
		plot.setSectionPaint("AIR", new  Color(182, 147, 250));
		//plot.setSectionPaint("TRUCK",Color.BLUE);
		plot.setSectionPaint("TRUCK",new Color(115,204,251));
		plot.setSectionPaint("SHIP",Color.PINK);
		plot.setSectionPaint("TRAIN",Color.MAGENTA);
		
		
		//c. create JFreeChart into impate
		try{
			ChartUtils.saveChartAsJPEG(new File(path+"/shipmntA.jpg"), chart, 500, 400);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	//generate Bar chart
	public void generateBar(String path,List<Object[]> data) {
		//a.create data set
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		//b.add data to dataset value(Double), key(String), label(String)
		for(Object[] ob:data) {
			dataset.setValue(Double.valueOf(ob[1].toString()),
					String.valueOf(ob[0]),
					"");
		}
		//b. create JFreeChart object using ChartFactory
		JFreeChart chart = ChartFactory.createBarChart("SHIPMENT TYPE MODE COUNT", "TYPE","COUNT",dataset);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.LIGHT_GRAY);
		//c. convert JFreeChart into image
		try{
			ChartUtils.saveChartAsJPEG(new File(path+"/shipmntB.jpg"), chart, 500, 400);
		}catch(Exception e) {
			e.printStackTrace();
		}
		

	}
}
