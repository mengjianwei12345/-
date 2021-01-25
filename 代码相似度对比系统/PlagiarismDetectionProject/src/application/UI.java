package application;
import javafx.application.Application;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;


public class UI extends Application {

	ShowWithGraph swg = new ShowWithGraph();
	/*
	 * �½��Ĺ���
	 */
	ComponentFactory factory = new ComponentFactory();
	EventFactory eventFactory = new EventFactory();
	SetGraph setGraph = new SetGraph();
	ShowWithDetail swd = new ShowWithDetail();
	private String absPath;
	private PieChart chart;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setHeight(500);
		primaryStage.setWidth(500);
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("����Ƚϲ鿴��");
		primaryStage.show();
		/*
		 * ����ı�����ʽ
		 */
		ImageView ivBackground = new ImageView(new Image("image/48.jpg"));
		ivBackground.setFitWidth(500);
		ivBackground.setFitHeight(500);
		ivBackground.setLayoutX(0);
		ivBackground.setLayoutY(0);
		root.getChildren().add(ivBackground);
		//Ϊ��������logo
		ImageView ivLogo = factory.graphFactory(100, 100, 50, 50, (new Image("image/logo1.png")));
		root.getChildren().add(ivLogo);
		/*
		 * ��������--����
		 */
		Text title = factory.textFactory(
				"���ƶȲ鿴��", Style.font, Style.red, 180, 80);
		root.getChildren().add(title);
		Rectangle titleRec = factory.rectangleFactory(500, 100,
				0, 200, Style.blue, 0);
		root.getChildren().add(titleRec);
		/*
		 * ���������û�ѡ��
		 * ��ϸ����Ƚϲ鿴ѡ��
		 * ͼ��Ƚϲ鿴
		 * �����˳���ť
		 */
		Rectangle detailBackground = factory.rectangleFactory(500, 100,
				0, 200, Style.blue, 0);
		root.getChildren().add(detailBackground);
		ImageView ivDetail = factory.graphFactory(60, 60, 80, 
				220, new Image("image/bijiao1.png"));
		root.getChildren().add(ivDetail);
		Text detailInfo = factory.textFactory("������ϸ�Ƚ�", Style.font,
				Style.gray, 160, 235);
		root.getChildren().add(detailInfo);
		/*
		 * Ϊ�����������������Ӧ�¼�
		 */
		eventFactory.setRectangleMouseEnter(detailBackground, Style.blue, 0.3, ivDetail, 
				new Image("image/bijiao2.png"), detailInfo, Style.blue);
		eventFactory.setRectangleMouseExit(detailBackground, Style.blue, 0, ivDetail, 
				new Image("image/bijiao1.png"), detailInfo, Style.gray);
		/*
		 * ΪͼƬ���������Ӧ�¼�
		 */
		eventFactory.setIVMouseEnter(detailBackground, Style.blue, 0.3, ivDetail, 
				new Image("image/bijiao2.png"), detailInfo, Style.blue);
		eventFactory.setIVMouseExit(detailBackground, Style.blue, 0, ivDetail, 
				new Image("image/bijiao1.png"), detailInfo, Style.gray);
		/*
		 * Ϊ�ı������������Ӧ�¼�
		 */
		eventFactory.setTextMouseEnter(detailBackground, Style.blue, 0.3, ivDetail, 
				new Image("image/bijiao2.png"), detailInfo, Style.blue);
		eventFactory.setTextMouseExit(detailBackground, Style.blue, 0, ivDetail, 
				new Image("image/bijiao1.png"), detailInfo, Style.gray);
		detailBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swd.showWithDetail1();
		});
		detailInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swd.showWithDetail1();
		});
		ivDetail.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swd.showWithDetail1();
		});
		/*
		 * �û�ѡ��ͼ��鿴
		 */
		Rectangle graphShowBackground = factory.rectangleFactory(
				500, 100, 0, 300, Style.blue, 0);
		root.getChildren().add(graphShowBackground);
		ImageView ivGraph = factory.graphFactory(
				60, 60, 80, 320, new Image("image/graph1.png"));
		root.getChildren().add(ivGraph);
		Text graphShow = factory.textFactory(
				"ͼ��鿴���", Style.font, Style.gray, 160, 330);
		root.getChildren().add(graphShow);
		/*
		 * �����Ӧ�¼�
		 */
		eventFactory.setRectangleMouseEnter(graphShowBackground, Style.blue, 0.3, ivGraph, 
				new Image("image/graph2.png"), graphShow, Style.blue);
		eventFactory.setRectangleMouseExit(graphShowBackground, Style.blue, 0, ivGraph, 
				new Image("image/graph1.png"), graphShow, Style.gray);
		eventFactory.setIVMouseEnter(graphShowBackground, Style.blue, 0.3, ivGraph, 
				new Image("image/graph2.png"), graphShow, Style.blue);
		eventFactory.setIVMouseExit(graphShowBackground, Style.blue, 0, ivGraph, 
				new Image("image/graph1.png"), graphShow, Style.gray);
		eventFactory.setTextMouseEnter(graphShowBackground, Style.blue, 0.3, ivGraph, 
				new Image("image/graph2.png"), graphShow, Style.blue);
		eventFactory.setTextMouseExit(graphShowBackground, Style.blue, 0, ivGraph, 
				new Image("image/graph1.png"), graphShow, Style.gray);
		graphShowBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swg.graphShow();
		});
		ivGraph.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swg.graphShow();
		});
		graphShow.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swg.graphShow();
		});
		/*
		 * ѡ��뿪���û��˳�����İ�ť
		 */
		Rectangle quitBackground = factory.rectangleFactory(
				500, 100, 0, 400, Style.blue, 0);
		root.getChildren().add(quitBackground);
		Text quit = factory.textFactory(
				"�˳�����", Style.font, Style.gray, 190, 430);
		root.getChildren().add(quit);
		ImageView ivQuit = factory.graphFactory(
				60, 60, 75, 420, new Image("image/quit3.png"));
		root.getChildren().add(ivQuit);
		/*
		 * ��������Ӧ�¼�
		 */
		eventFactory.setRectangleMouseEnter(quitBackground, Style.blue, 0.6, ivQuit, 
				new Image("image/quit4.png"), quit, Style.red);
		eventFactory.setRectangleMouseExit(quitBackground, Style.red, 0, ivQuit, 
				new Image("image/quit3.png"), quit, Style.gray);
		eventFactory.setIVMouseEnter(quitBackground, Style.blue, 0.6, ivQuit, 
				new Image("image/quit4.png"), quit, Style.red);
		eventFactory.setIVMouseExit(quitBackground, Style.red, 0, ivQuit, 
				new Image("image/quit3.png"), quit, Style.gray);
		eventFactory.setTextMouseEnter(quitBackground, Style.blue, 0.6, ivQuit, 
				new Image("image/quit4.png"), quit, Style.red);
		eventFactory.setTextMouseExit(quitBackground, Style.red, 0, ivQuit, 
				new Image("image/quit3.png"), quit, Style.gray);
		quitBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		quit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		ivQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		
		/*
		 * Ϊ�����ۣ�������С��ť
		 */
		Rectangle suoxiaoBackground = factory.rectangleFactory(60, 60,
				380, 0, Style.blue, 0);
		root.getChildren().add(suoxiaoBackground);
		ImageView ivSuoXiao = factory.graphFactory(30, 30, 395, 15,
				new Image("image/suoxiao1.png"));
		root.getChildren().add(ivSuoXiao);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(suoxiaoBackground, Style.blue, 0.3,
				ivSuoXiao, new Image("image/suoxiao2.png"), null, null);
		eventFactory.setRectangleMouseExit(suoxiaoBackground, Style.blue, 0,
				ivSuoXiao, new Image("image/suoxiao1.png"), null, null);
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(suoxiaoBackground, Style.blue, 0.3,
				ivSuoXiao, new Image("image/suoxiao2.png"), null, null);
		eventFactory.setIVMouseExit(suoxiaoBackground, Style.blue, 0,
				ivSuoXiao, new Image("image/suoxiao1.png"), null, null);
		/*
		 * ���ϽǵĹرհ�ť
		 * �ɾ�����Ϊ������һ��ͼƬ��Ϊ��ʾͼ��
		 */
		Rectangle closeBackground = factory.rectangleFactory(60, 60,
				440, 0, Style.blue, 0);
		closeBackground.setArcWidth(0);
		closeBackground.setArcHeight(0);
		
		root.getChildren().add(closeBackground);
		ImageView ivClose = factory.graphFactory(30, 30, 455, 15,
				new Image("image/close1.png"));
		root.getChildren().add(ivClose);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(closeBackground, Style.blue, 0.3,
				ivClose, new Image("image/close2.png"), null, null);
		eventFactory.setRectangleMouseExit(closeBackground, Style.blue, 0,
				ivClose, new Image("image/close1.png"), null, null);
		closeBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(closeBackground, Style.blue, 0.3,ivClose, 
				new Image("image/close2.png"), null, null);
		eventFactory.setIVMouseExit(closeBackground, Style.blue, 0.3,ivClose, 
				new Image("image/close1.png"), null, null);
		ivClose.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		
	}
	
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
				600, 600, 150, 150, new Image("image/logo.png"));
		ivLogoBackground.setOpacity(1);
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
				"�ȴ��ļ�����...", Style.font, Style.gray, 345, 40);
		Font currentFont = new Font("΢���ź�", 20);
		currentFile.setFont(currentFont);
		root.getChildren().add(currentFile);
		//�ṩ�û�ѡ���ļ��İ�ť----�ļ�ѡ����
		Rectangle choiseFile = factory.rectangleFactory(
				100, 100, 240, 0, Style.gray, 0);
		root.getChildren().add(choiseFile);
		ImageView ivChoise = factory.graphFactory(
				80, 60, 255, 22, new Image("image/fileopen1.png"));
		root.getChildren().add(ivChoise);
		Rectangle choiseTextRec = factory.rectangleFactory(
				140, 100, 100, 0, Style.gray, 0);
		root.getChildren().add(choiseTextRec);
		Text choiseText = factory.textFactory(
				"�����ļ�", Style.font, Style.gray, 115, 35);
		root.getChildren().add(choiseText);
		/*
		 * ʵ�����ļ�ѡ����
		 */
		FileChooser fc = new FileChooser();
		File file = new File("D:\\");
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
			}

		});
		ivChoise.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc.showOpenDialog(stage);
			if (null != f) {
				currentFile.setText(f.getName());
				absPath = f.getAbsolutePath();
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
		//�û�ѡ��ʼ���Եİ�ť
		Rectangle start = factory.rectangleFactory(
				100, 100, 500, 0, Style.gray, 0);
		root.getChildren().add(start);
		ImageView ivStart = factory.graphFactory(
				60, 60, 520, 20, new Image("image/start1.png"));
		root.getChildren().add(ivStart);
		eventFactory.setRectangleMouseEnter(
				start, Style.gray, 0.3, ivStart, 
				new Image("image/start2.png"), null, null);
		eventFactory.setRectangleMouseExit(
				start, Style.gray, 0, ivStart, 
				new Image("image/start1.png"), null, null);


		start.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			chart = setGraph.dynaGraph(absPath);
			if(null != chart){
				root.getChildren().add(chart);
				root.getChildren().remove(ivLogoBackground);
			}
			/*
			 * ʹ����Ϻ�����ָ��գ�������������ͼ
			 */
			absPath = null;
		});
		eventFactory.setIVMouseEnter(
				start, Style.gray, 0.3, ivStart, 
				new Image("image/start2.png"), null, null);
		eventFactory.setIVMouseExit(
				start, Style.gray, 0, ivStart, 
				new Image("image/start1.png"), null, null);
		ivStart.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			chart = setGraph.dynaGraph(absPath);
			if(null != chart){
				root.getChildren().add(chart);
				root.getChildren().remove(ivLogoBackground);
			}
			absPath = null;
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
