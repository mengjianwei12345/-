package application;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ShowWithGraph {
	String absPath = null;
	PieChart chart = null;
	/*
	 * �½��Ĺ���
	 */
	ComponentFactory factory = new ComponentFactory();
	EventFactory eventFactory = new EventFactory();
	Dialog dialog = new Dialog();
	SetGraph setGraph = new SetGraph();
	GetData getData = new GetData();
	//SetGraph sg = new SetGraph();
	/*
	 * �û�ѡ��ͼ����ʾ����ô˺������˺������Ը����û�ѡ����ļ�����ʾ��Ӧ��ͼ��
	 */
	public void graphShow(){
		
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setHeight(800);
		stage.setWidth(800);
		Group root = new Group();
		Scene scene = new Scene(root, 800, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("ͼ����ʾ��");
		stage.show();
		
		//���UI�ӱ���
		ImageView ivBackground = factory.graphFactory(
				800, 800, 0, 0, new Image("image/48.jpg"));
		root.getChildren().add(ivBackground);
		ImageView ivLogoBackground = factory.graphFactory(
				600, 600, 150, 150, new Image("image/logo1.png"));
		ivLogoBackground.setOpacity(0.5);
		root.getChildren().add(ivLogoBackground);
		//���Ļ����������ʱ�ڱα�ͼ
//		Rectangle graphBackground1 = factory.rectangleFactory(
//				700, 700, 100, 100, Style.gray, 0.3);
//		root.getChildren().add(graphBackground1);
//		Rectangle graphBackground2 = factory.rectangleFactory(
//				100, 600, 0, 100, Style.gray, 0.3);
//		root.getChildren().add(graphBackground2);
		//Ϊ��������logo
		ImageView ivLogo = new ImageView(new Image("image/logo.png"));
		ivLogo.setFitHeight(100);
		ivLogo.setFitWidth(100);
		ivLogo.setLayoutX(0);
		ivLogo.setLayoutY(0);
		root.getChildren().add(ivLogo);
		/*
		 * ����һ���ı���������ʾ����ִ�е��ļ�����
		 */
		Text currentFile = factory.textFactory(
				"�ȴ��ļ�����...", Style.font, Style.gray, 315, 40);
		Font currentFont = new Font("΢���ź�", 26);
		currentFile.setFont(currentFont);
		root.getChildren().add(currentFile);
		//�ṩ�û�ѡ���ļ��İ�ť----�ļ�ѡ����
		Rectangle choiseFile = factory.rectangleFactory(
				100, 100, 200, 0, Style.gray, 0);
		root.getChildren().add(choiseFile);
		ImageView ivChoise = factory.graphFactory(
				80, 60, 215, 22, new Image("image/fileopen1.png"));
		root.getChildren().add(ivChoise);
		Rectangle updataFileRec = factory.rectangleFactory(
				100, 100, 0, 300, Style.gray, 0);
		root.getChildren().add(updataFileRec);
		ImageView ivFileUpdate = factory.graphFactory(
				75, 75, 10, 315, new Image("image/fileupdate1.png"));
		root.getChildren().add(ivFileUpdate);
		
//		Text choiseText = factory.textFactory(
//				"�����ļ�", Style.font, Style.gray, 115, 35);
//		root.getChildren().add(choiseText);
		/*
		 * ʵ�����ļ�ѡ����
		 */
		FileChooser fc = new FileChooser();
		File file = new File("D:/0SaveUploadFile");
		/*
		 * �����ļ�ѡ�����ı���
		 */
		fc.setTitle("ѡ���ļ�");
		/*
		 * �����ļ�ѡ������Ĭ��ѡ��·��
		 */
		fc.setInitialDirectory(file);
		
		choiseFile.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc.showOpenDialog(stage);
			if (null != f) {
				currentFile.setText(f.getName());
				absPath = f.getAbsolutePath();
//				System.out.println(absPath);
			}
			
		});
		ivChoise.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc.showOpenDialog(stage);
			if (null != f) {
				currentFile.setText(f.getName());
				absPath = f.getAbsolutePath();
//				System.out.println(absPath);
			}
		});
		eventFactory.setRectangleMouseEnter(
				choiseFile, Style.gray, 0.3, ivChoise, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				choiseFile, Style.gray, 0, ivChoise, 
				new Image("image/fileopen1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				choiseFile, Style.gray, 0.3, ivChoise, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				choiseFile, Style.gray, 0, ivChoise, 
				new Image("image/fileopen1.png"), null, Style.gray);
		eventFactory.setRectangleMouseEnter(
				updataFileRec, Style.gray, 0.3, ivFileUpdate, 
				new Image("image/fileupdate2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				updataFileRec, Style.gray, 0, ivFileUpdate, 
				new Image("image/fileupdate1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				updataFileRec, Style.gray, 0.3, ivFileUpdate, 
				new Image("image/fileupdate2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				updataFileRec, Style.gray, 0, ivFileUpdate, 
				new Image("image/fileupdate1.png"), null, Style.gray);
		updataFileRec.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if(null == absPath){
				dialog.showDialog("û��ѡ���ļ���");
			} else {
				String path = absPath;
				path = absPath.substring(0,absPath.length() - 10);
//				System.out.println(path);
				setGraph.updataFile(path);
				dialog.showDialog("�ļ��Ѿ����£�");
			}
		});
		
		//�û�ѡ��ʼ���Եİ�ť
		Rectangle start = factory.rectangleFactory(
				100, 100, 0, 400, Style.gray, 0);
		root.getChildren().add(start);
		ImageView ivStart = factory.graphFactory(
				60, 60, 20, 420, new Image("image/start1.png"));
		root.getChildren().add(ivStart);
		eventFactory.setRectangleMouseEnter(
				start, Style.gray, 0.3, ivStart, 
				new Image("image/start2.png"), null, null);
		eventFactory.setRectangleMouseExit(
				start, Style.gray, 0, ivStart, 
				new Image("image/start1.png"), null, null);
		
		start.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if(null == absPath){
				dialog.showDialog("û��ѡ���ļ���");
			} else {
				chart = setGraph.dynaGraph(absPath);
				if(null != chart){
					root.getChildren().add(chart);
					root.getChildren().remove(ivLogoBackground);
				}
			}
		});
		eventFactory.setIVMouseEnter(
				start, Style.gray, 0.3, ivStart, 
				new Image("image/start2.png"), null, null);
		eventFactory.setIVMouseExit(
				start, Style.gray, 0, ivStart, 
				new Image("image/start1.png"), null, null);
		ivStart.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if(null == absPath){
				dialog.showDialog("û��ѡ���ļ���");
			} else {
				chart = setGraph.dynaGraph(absPath);
				if(null != chart){
					root.getChildren().add(chart);
					root.getChildren().remove(ivLogoBackground);
				}
			}
		});
		/*
		 * ������ϸ��Ϣ��ʾ
		 * ���û�ѡ����ʾ��ͼ����Ӧ����ϸ��Ϣ����ʾ������
		 */
		Rectangle showDetail1 = factory.rectangleFactory(
				100, 100, 0, 100, Style.gray, 0);
		root.getChildren().add(showDetail1);
		ImageView ivShowDetail1 = factory.graphFactory(
				80, 80, 10, 110, new Image("image/detail1.png"));
		root.getChildren().add(ivShowDetail1);
		eventFactory.setRectangleMouseEnter(
				showDetail1, Style.gray, 0.3, ivShowDetail1, 
				new Image("image/detail2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				showDetail1, Style.gray, 0, ivShowDetail1, 
				new Image("image/detail1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				showDetail1, Style.gray, 0.3, ivShowDetail1, 
				new Image("image/detail2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				showDetail1, Style.gray, 0, ivShowDetail1, 
				new Image("image/detail1.png"), null, Style.gray);
		showDetail1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			getData.showDetialInfo(absPath);
		});
		ivShowDetail1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			getData.showDetialInfo(absPath);
		});
		
		/*
		 * ���û��ṩһ������ķ�Χ����ʾ��ֵ
		 */
		Rectangle showRange = factory.rectangleFactory(
				100, 100, 0, 200, Style.gray, 0);
		root.getChildren().add(showRange);
		ImageView ivShowRange = factory.graphFactory(
				80, 80, 10, 210, new Image("image/range1.png"));
		root.getChildren().add(ivShowRange);
		eventFactory.setRectangleMouseEnter(
				showRange, Style.gray, 0.3, ivShowRange, 
				new Image("image/range2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				showRange, Style.gray, 0, ivShowRange, 
				new Image("image/range1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				showRange, Style.gray, 0.3, ivShowRange, 
				new Image("image/range2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				showRange, Style.gray, 0, ivShowRange, 
				new Image("image/range1.png"), null, Style.gray);
		showRange.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			//getData.showDetialInfo(absPath);
		});
		ivShowRange.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			//getData.showDetialInfo(absPath);
		});
		/*
		 * Ϊ�����ۣ������رգ���С��ť��
		 */
		factory.component(stage, root, 800, 800, 100, 100);
		//�����˳���
		Rectangle quit = factory.rectangleFactory(
				100, 100, 0, 700, Style.gray, 0);
		root.getChildren().add(quit);
		ImageView ivQuit = factory.graphFactory(
				60, 60, 15, 720, new Image("image/quit3.png"));
		root.getChildren().add(ivQuit);
		eventFactory.setRectangleMouseEnter(
				quit, Style.gray, 0.3, ivQuit, 
				new Image("image/quit4.png"), null, null);
		eventFactory.setRectangleMouseExit(
				quit, Style.gray, 0, ivQuit, 
				new Image("image/quit3.png"), null, null);
		quit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
		eventFactory.setIVMouseEnter(
				quit, Style.gray, 0.3, ivQuit, 
				new Image("image/quit4.png"), null, null);
		eventFactory.setIVMouseExit(
				quit, Style.gray, 0, ivQuit, 
				new Image("image/quit3.png"), null, null);
		ivQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
	}
}
