package application;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransitionBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

@SuppressWarnings("deprecation")
public class SetGraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SetGraph sg = new SetGraph();
		sg.dynaGraph("D:/0SaveUploadFile/111.pd");
		sg.setPieChart("D:/0SaveUploadFile/111.pd");
	}
	GetData getData = new GetData();
	List<PieChart.Data> pieData = new ArrayList<PieChart.Data>();
	
	/*
	 * ���ñ�ͼ��ʾ����Ϣ
	 * ���ܲ���path���ļ��ľ���·��
	 */
	public void setPieChart(String path){
//		List<PieChart.Data> pieData = new ArrayList<PieChart.Data>();
		PieChart.Data num1 = new PieChart.Data(null, 0);
		PieChart.Data num2 = new PieChart.Data(null, 0);
		PieChart.Data num3 = new PieChart.Data(null, 0);
		PieChart.Data num4 = new PieChart.Data(null, 0);
		PieChart.Data num5 = new PieChart.Data(null, 0);
		PieChart.Data num6 = new PieChart.Data(null, 0);
		//���ñ�ͼ����ʾ��Ϣ
		num1.setName("1 - " + getData.range[0]);
		num2.setName("" + getData.range[0] + " - " + getData.range[1]);
		num3.setName("" + getData.range[1] + " - " + getData.range[2]);
		num4.setName("" + getData.range[2] + " - " + getData.range[3]);
		num5.setName("" + getData.range[3] + " - " + getData.range[4]);
		num6.setName("" + getData.range[4] + " - 0");	
		//���ñ�ͼ��ʾ��Ϣ----��Ӧ��������Ŀ��ͳ����Ŀ
		getData.readFile(path);
		getData.simpleSimList();
		getData.showSimpleList();
		getData.initSimpleList();
		/*
		 * ����ɰٷֱ���ʾ����
		 */
//		int result = 0;
//		for(int i = 0; i < getData.simList.size(); i++){
//			System.out.println(getData.simList.get(i));
//			result = result + getData.simList.get(i);
//		}
//		System.out.println(result);
//		System.out.println(getData.simList.get(5));
//		System.out.println(getData.simList.get(5)*1.0 / result);
//		num1.setPieValue(getData.simList.get(0)*1.0 / result);
//		num2.setPieValue(getData.simList.get(1)*1.0 / result);
//		num3.setPieValue(getData.simList.get(2)*1.0 / result);
//		num4.setPieValue(getData.simList.get(3)*1.0 / result);
//		num5.setPieValue(getData.simList.get(4)*1.0 / result);
//		num6.setPieValue(getData.simList.get(5)*1.0 / result);
		int result = 0;
		for(int i = 0; i < getData.simpleSimList.size(); i++){
			System.out.println(getData.simpleSimList.get(i));
			result = result + getData.simpleSimList.get(i);
		}
		System.out.println(result);
		System.out.println(getData.simpleSimList.get(5));
		System.out.println(getData.simpleSimList.get(5));
		num1.setPieValue(getData.simpleSimList.get(0));
		num2.setPieValue(getData.simpleSimList.get(1));
		num3.setPieValue(getData.simpleSimList.get(2));
		num4.setPieValue(getData.simpleSimList.get(3));
		num5.setPieValue(getData.simpleSimList.get(4));
		num6.setPieValue(getData.simpleSimList.get(5));
		//�����úõı�ͼ��Ϣ��������ͼ��
		pieData.add(num1);
		pieData.add(num2);
		pieData.add(num3);
		pieData.add(num4);
		pieData.add(num5);
		pieData.add(num6);
//		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
//				num1, num2,num3,num4,num5,num6);
	}
	/*
	 * ��Ҫ��ȡ��ָ���ļ���û�����ɣ���ʱ��Ҫ���¶�ȡһϵ�����ݣ������ɻ��Ǹ����ļ�
	 */
	public void updataFile(String path){
		getData.launch.setDefaultPath(path);
		getData.launch.pd.setTHRESHOLD(-1);
		getData.launch.readDirctoriesWithAll();
		getData.launch.pd.launchSimiMeasWithAll();
		getData.writeFile();
//		System.out.println(path);
		getData.readFile(path + "\\111.pd");
		//System.out.println(getData.simInfo);
//		getData.simpleSimList();
//		getData.showSimpleList();
	}
	/*
	 * ����һ����̬��ͼ����
	 * ��������һ����ͼ���ظ��û�����
	 */
	public PieChart dynaGraph(String path){
		/*
		 * �ж��Ƿ�Ϊ�գ����Ϊ�գ���ֱ�ӷ��ؿ�ֵ
		 */
		if(null == path){
			return null;
		}
		/*
		 * ���ɶ�̬��ͼ֮ǰ��Ҫ���ݵĳ�ʼ��
		 * Ϊ�˱������ݵĸ��ţ��˴��������������
		 */
		getData.simList.clear();
		getData.simInfo.clear();
		pieData.clear();
		getData.initSimList();
		System.out.println(path);
		this.setPieChart(path);
		ObservableList<PieChart.Data> pieChartData = 
				FXCollections.observableArrayList();
		/*
		 * ���Ե�ʱ�������bug����ε���󣬱�ͼ���ݿ�ʼ��������
		 * �˴��жϱ�ͼ�������Ƿ�Ϊ�գ������Ϊ�������������
		 */
		pieChartData.clear();
		for(int i = 0 ;i < pieData.size(); i++){
			//pieData.get(i).setName(pieData.get(i).getName() + " " + pieData.get(i).getPieValue() + "%");
			pieChartData.add(pieData.get(i));
		}
		PieChart chart = new PieChart(pieChartData);
		
		chart.setMinSize(650, 650);
		chart.setLayoutX(130);
		chart.setLayoutY(150);
		chart.setLabelLineLength(20);
		chart.setLegendSide(Side.RIGHT);
		chart.setTitle("�������ƶȷֲ���ͼ");
//		chart.setTitleSide(Side.BOTTOM);
		/*
		 * ���������Ӧ�¼�
		 */
		
		final Label caption = new Label("");
		caption.setTextFill(Color.BLACK);
		caption.setStyle("-fx-font: 30 arial;");
		chart.getData().stream().forEach((data) -> {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
				caption.setTranslateX(e.getSceneX());
				caption.setTranslateY(e.getSceneY());
				caption.setText(String.valueOf(data.getPieValue()) + "%");
			});
		});
		/*
		 * ���ñ�ͼ�����ƶ�
		 */
		for (PieChart.Data d : pieChartData) {
			d.getNode().setOnMouseEntered(new MouseHoverAnimation(d, chart));
			d.getNode().setOnMouseExited(new MouseExitAnimation());
		}
		chart.setClockwise(false);
		
		return chart;
	}
	
	static class MouseHoverAnimation implements EventHandler<MouseEvent> {
		static final Duration ANIMATION_DURATION = new Duration(500);
		static final double ANIMATION_DISTANCE = 0.15;
		private double cos;
		private double sin;
		private PieChart chart;

		public MouseHoverAnimation(PieChart.Data d, PieChart chart) {
			this.chart = chart;
			double start = 0;
			double angle = calcAngle(d);
			for (PieChart.Data tmp : chart.getData()) {
				if (tmp == d) {
					break;
				}
				start += calcAngle(tmp);
			}

			cos = Math.cos(Math.toRadians(start + angle / 2));
			sin = Math.sin(Math.toRadians(start + angle / 2));
		}

		@Override
		public void handle(MouseEvent arg0) {
			Node n = (Node) arg0.getSource();
			double minX = Double.MAX_VALUE;
			double maxX = Double.MAX_VALUE * -1;

			for (PieChart.Data d : chart.getData()) {
				minX = Math.min(minX, d.getNode().getBoundsInParent().getMinX());
				maxX = Math.max(maxX, d.getNode().getBoundsInParent().getMaxX());
			}
			double radius = maxX - minX;
			TranslateTransitionBuilder.create().toX((radius * ANIMATION_DISTANCE) * cos)
					.toY((radius * ANIMATION_DISTANCE) * (-sin)).duration(ANIMATION_DURATION).node(n).build().play();
		}

		private static double calcAngle(PieChart.Data d) {
			double total = 0;
			for (PieChart.Data tmp : d.getChart().getData()) {
				total += tmp.getPieValue();
			}
			return 360 * (d.getPieValue() / total);
		}
	}

	static class MouseExitAnimation implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			TranslateTransitionBuilder.create().toX(0).toY(0).duration(new Duration(500)).node((Node) event.getSource())
					.build().play();
		}
	}
	

	final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BarChart<Number,String> barChart(){
		final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number,String> bc = 
            new BarChart<>(xAxis,yAxis);
        bc.setTitle("Country Summary");
        xAxis.setLabel("Value");  
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Country");        
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");       
        series1.getData().add(new XYChart.Data(25601.34, austria));
        series1.getData().add(new XYChart.Data(20148.82, brazil));
        series1.getData().add(new XYChart.Data(10000, france));
        series1.getData().add(new XYChart.Data(35407.15, italy));
        series1.getData().add(new XYChart.Data(12000, usa));      
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data(57401.85, austria));
        series2.getData().add(new XYChart.Data(41941.19, brazil));
        series2.getData().add(new XYChart.Data(45263.37, france));
        series2.getData().add(new XYChart.Data(117320.16, italy));
        series2.getData().add(new XYChart.Data(14845.27, usa));  
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data(45000.65, austria));
        series3.getData().add(new XYChart.Data(44835.76, brazil));
        series3.getData().add(new XYChart.Data(18722.18, france));
        series3.getData().add(new XYChart.Data(17557.31, italy));
        series3.getData().add(new XYChart.Data(92633.68, usa));
        bc.getData().addAll(series1, series2, series3);
        
		return bc;
	}
	
}
