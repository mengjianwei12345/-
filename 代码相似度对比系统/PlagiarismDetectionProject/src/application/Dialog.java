package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Dialog {

	/*
	 * �½��Ĺ���
	 */
	ComponentFactory factory = new ComponentFactory();
	EventFactory eventFactory = new EventFactory();
	/*
	 * ��ʾ�û��鿴�ĸ�����ϸ��Ϣ�ĵ���
	 */
	public void showMoreDetail(List<String> list){
		
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setWidth(300);
		stage.setHeight(700);
		Group root = new Group();
		Scene scene = new Scene(root, 300, 700);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("DetailDialog");
		stage.show();
		// ���UI�ӱ���
		ImageView ivBackground = factory.graphFactory(300, 700, 0, 0, new Image("image/50.png"));
		root.getChildren().add(ivBackground);
		ImageView ivLogoBackground = factory.graphFactory(100, 100, 0, 0, new Image("image/logo.png"));
		ivLogoBackground.setOpacity(1);
		root.getChildren().add(ivLogoBackground);
		
		/*
		 * �˴������õ�����
		 */
		String info = "";
		for(int i = 0; i < list.size(); i+=3){
			info = info + list.get(i) + "\n" + list.get(i+1) +
					"\n���ƶ�->" + list.get(i+2) + "\n";
		}
		System.out.println(info);
		System.out.println(list);
		Text infoText = factory.textFactory(info, Style.font, Style.red, 20, 120);
		infoText.setFont(new Font("΢���ź�", 22));
//		root.getChildren().add(infoText);
		ScrollPane sp = new ScrollPane();
		sp.setLayoutX(0);
		sp.setLayoutY(100);
		sp.setMinSize(300, 500);
		sp.setMaxSize(300, 500);
		sp.setOpacity(0.5);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		root.getChildren().add(sp);
		sp.setContent(infoText);
		/*
		 * ��ʾ�����Ϣ���ڵײ����ӹرհ�ť
		 */
		Rectangle closeRecB = factory.rectangleFactory(
				300, 100, 0, 600, Style.gray, 0);
		root.getChildren().add(closeRecB);
		ImageView ivCloseB = factory.graphFactory(
				80, 80, 110, 610, new Image("image/close3.png"));
		root.getChildren().add(ivCloseB);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(closeRecB, Style.gray, 0.3, ivCloseB,
				new Image("image/close4.png"), null, null);
		eventFactory.setRectangleMouseExit(closeRecB, Style.gray, 0, ivCloseB, new Image("image/close3.png"),
				null, null);
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(closeRecB, Style.gray, 0.3, ivCloseB, new Image("image/close4.png"),
				null, null);
		eventFactory.setIVMouseExit(closeRecB, Style.gray, 0, ivCloseB, new Image("image/close3.png"), null,
				null);
		closeRecB.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
		ivCloseB.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
		/*
		 * Ϊ�����ۣ�������С��ť
		 */
		Rectangle suoxiaoBackground = factory.rectangleFactory(60, 60, 180, 0, Style.blue, 0);
		root.getChildren().add(suoxiaoBackground);
		ImageView ivSuoXiao = factory.graphFactory(30, 30, 195, 10, new Image("image/suoxiao1.png"));
		root.getChildren().add(ivSuoXiao);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(suoxiaoBackground, Style.gray, 0.3, ivSuoXiao,
				new Image("image/suoxiao2.png"), null, null);
		eventFactory.setRectangleMouseExit(suoxiaoBackground, Style.gray, 0, ivSuoXiao, new Image("image/suoxiao1.png"),
				null, null);
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(suoxiaoBackground, Style.gray, 0.3, ivSuoXiao, new Image("image/suoxiao2.png"),
				null, null);
		eventFactory.setIVMouseExit(suoxiaoBackground, Style.gray, 0, ivSuoXiao, new Image("image/suoxiao1.png"), null,
				null);

		/*
		 * ���ϽǵĹرհ�ť �ɾ�����Ϊ������һ��ͼƬ��Ϊ��ʾͼ��
		 */
		Rectangle closeBackground = factory.rectangleFactory(60, 60, 240, 0, Style.blue, 0);
		closeBackground.setArcWidth(0);
		closeBackground.setArcHeight(0);

		root.getChildren().add(closeBackground);
		ImageView ivClose = factory.graphFactory(30, 30, 255, 10, new Image("image/close1.png"));
		root.getChildren().add(ivClose);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(closeBackground, Style.gray, 0.3, ivClose, new Image("image/close2.png"),
				null, null);
		eventFactory.setRectangleMouseExit(closeBackground, Style.gray, 0, ivClose, new Image("image/close1.png"), null,
				null);
		closeBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(closeBackground, Style.gray, 0.3, ivClose, new Image("image/close2.png"), null,
				null);
		eventFactory.setIVMouseExit(closeBackground, Style.gray, 0.3, ivClose, new Image("image/close1.png"), null,
				null);
		ivClose.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
		
		
	}
	/*
	 * ���û���Ҫ�鿴������ϸ����Ϣ��ʱ�򣬽���õ�����ת����Ϊ��Ϣ���
	 */
	List<String> list = new ArrayList<String>();
	public void showDetailDialog(GetData getData, String info1, String info2, String info3
			,String info4, String info5, String info6) {

		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setWidth(300);
		stage.setHeight(700);
		Group root = new Group();
		Scene scene = new Scene(root, 300, 700);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("DetailDialog");
		stage.show();
		// ���UI�ӱ���
		ImageView ivBackground = factory.graphFactory(300, 700, 0, 0, new Image("image/50.png"));
		root.getChildren().add(ivBackground);
		ImageView ivLogoBackground = factory.graphFactory(100, 100, 0, 0, new Image("image/logo.png"));
		ivLogoBackground.setOpacity(1);
		root.getChildren().add(ivLogoBackground);
		/*
		 * ��ʾ��6����Χ�ڵ�ֵ
		 */
		Text infoText1 = factory.textFactory(info1, Style.font, Style.blue, 50, 140);
		root.getChildren().add(infoText1);
		Rectangle infoRec1 = factory.rectangleFactory(
				300, 80, 0, 120, Style.gray, 0.3);
		root.getChildren().add(infoRec1);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(infoRec1, Style.blue, 0.3, null,
				null, infoText1, Style.red);
		eventFactory.setRectangleMouseExit(infoRec1, Style.gray, 0.3, null,
				null, infoText1, Style.blue);
		infoRec1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			list = getData.getMore(1);
			this.showMoreDetail(list);
		});
		
		Text infoText2 = factory.textFactory(info2, Style.font, Style.blue, 50, 220);
		root.getChildren().add(infoText2);
		Rectangle infoRec2 = factory.rectangleFactory(
				300, 80, 0, 200, Style.gray, 0);
		root.getChildren().add(infoRec2);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(infoRec2, Style.blue, 0.3, null,
				null, infoText2, Style.red);
		eventFactory.setRectangleMouseExit(infoRec2, Style.gray, 0, null,
				null, infoText2, Style.blue);
		infoRec2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			list = getData.getMore(2);
			this.showMoreDetail(list);
		});
		
		Text infoText3 = factory.textFactory(info3, Style.font, Style.blue, 50, 300);
		root.getChildren().add(infoText3);
		Rectangle infoRec3 = factory.rectangleFactory(
				300, 80, 0, 280, Style.gray, 0.3);
		root.getChildren().add(infoRec3);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(infoRec3, Style.blue, 0.3, null,
				null, infoText3, Style.red);
		eventFactory.setRectangleMouseExit(infoRec3, Style.gray, 0.3, null,
				null, infoText3, Style.blue);
		infoRec3.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			list = getData.getMore(3);
			this.showMoreDetail(list);
		});
		
		Text infoText4 = factory.textFactory(info4, Style.font, Style.blue, 50, 380);
		root.getChildren().add(infoText4);
		Rectangle infoRec4= factory.rectangleFactory(
				300, 80, 0, 360, Style.gray, 0);
		root.getChildren().add(infoRec4);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(infoRec4, Style.blue, 0.3, null,
				null, infoText4, Style.red);
		eventFactory.setRectangleMouseExit(infoRec4, Style.gray, 0, null,
				null, infoText4, Style.blue);
		infoRec4.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			list = getData.getMore(4);
			this.showMoreDetail(list);
		});
		
		Text infoText5 = factory.textFactory(info5, Style.font, Style.blue, 50, 460);
		root.getChildren().add(infoText5);
		Rectangle infoRec5 = factory.rectangleFactory(
				300, 80, 0, 440, Style.gray, 0.3);
		root.getChildren().add(infoRec5);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(infoRec5, Style.blue, 0.3, null,
				null, infoText5, Style.red);
		eventFactory.setRectangleMouseExit(infoRec5, Style.gray, 0.3, null,
				null, infoText5, Style.blue);
		infoRec5.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			list = getData.getMore(5);
			this.showMoreDetail(list);
		});
		
		Text infoText6 = factory.textFactory(info6, Style.font, Style.blue, 50, 540);
		root.getChildren().add(infoText6);
		Rectangle infoRec6 = factory.rectangleFactory(
				300, 80, 0, 520, Style.gray, 0);
		root.getChildren().add(infoRec6);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(infoRec6, Style.blue, 0.3, null,
				null, infoText6, Style.red);
		eventFactory.setRectangleMouseExit(infoRec6, Style.gray, 0, null,
				null, infoText6, Style.blue);
		infoRec6.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			list = getData.getMore(6);
			this.showMoreDetail(list);
		});
		
		/*
		 * ��ʾ�����Ϣ���ڵײ����ӹرհ�ť
		 */
		Rectangle closeRecB = factory.rectangleFactory(
				300, 100, 0, 600, Style.gray, 0);
		root.getChildren().add(closeRecB);
		ImageView ivCloseB = factory.graphFactory(
				80, 80, 110, 610, new Image("image/close3.png"));
		root.getChildren().add(ivCloseB);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(closeRecB, Style.gray, 0.3, ivCloseB,
				new Image("image/close4.png"), null, null);
		eventFactory.setRectangleMouseExit(closeRecB, Style.gray, 0, ivCloseB, new Image("image/close3.png"),
				null, null);
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(closeRecB, Style.gray, 0.3, ivCloseB, new Image("image/close4.png"),
				null, null);
		eventFactory.setIVMouseExit(closeRecB, Style.gray, 0, ivCloseB, new Image("image/close3.png"), null,
				null);
		closeRecB.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
		ivCloseB.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
		/*
		 * Ϊ�����ۣ�������С��ť
		 */
		Rectangle suoxiaoBackground = factory.rectangleFactory(60, 60, 180, 0, Style.blue, 0);
		root.getChildren().add(suoxiaoBackground);
		ImageView ivSuoXiao = factory.graphFactory(30, 30, 195, 10, new Image("image/suoxiao1.png"));
		root.getChildren().add(ivSuoXiao);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(suoxiaoBackground, Style.gray, 0.3, ivSuoXiao,
				new Image("image/suoxiao2.png"), null, null);
		eventFactory.setRectangleMouseExit(suoxiaoBackground, Style.gray, 0, ivSuoXiao, new Image("image/suoxiao1.png"),
				null, null);
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(suoxiaoBackground, Style.gray, 0.3, ivSuoXiao, new Image("image/suoxiao2.png"),
				null, null);
		eventFactory.setIVMouseExit(suoxiaoBackground, Style.gray, 0, ivSuoXiao, new Image("image/suoxiao1.png"), null,
				null);

		/*
		 * ���ϽǵĹرհ�ť �ɾ�����Ϊ������һ��ͼƬ��Ϊ��ʾͼ��
		 */
		Rectangle closeBackground = factory.rectangleFactory(60, 60, 240, 0, Style.blue, 0);
		closeBackground.setArcWidth(0);
		closeBackground.setArcHeight(0);

		root.getChildren().add(closeBackground);
		ImageView ivClose = factory.graphFactory(30, 30, 255, 10, new Image("image/close1.png"));
		root.getChildren().add(ivClose);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(closeBackground, Style.gray, 0.3, ivClose, new Image("image/close2.png"),
				null, null);
		eventFactory.setRectangleMouseExit(closeBackground, Style.gray, 0, ivClose, new Image("image/close1.png"), null,
				null);
		closeBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(closeBackground, Style.gray, 0.3, ivClose, new Image("image/close2.png"), null,
				null);
		eventFactory.setIVMouseExit(closeBackground, Style.gray, 0.3, ivClose, new Image("image/close1.png"), null,
				null);
		ivClose.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});

	}

	public void showDialog(String info) {

		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setWidth(300);
		stage.setHeight(150);
		Group root = new Group();
		Scene scene = new Scene(root, 300, 150);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Dialog");
		stage.show();
		// ���UI�ӱ���
		ImageView ivBackground = factory.graphFactory(300, 150, 0, 0, new Image("image/49.png"));
		root.getChildren().add(ivBackground);
		ImageView ivLogoBackground = factory.graphFactory(60, 60, 0, 0, new Image("image/logo.png"));
		ivLogoBackground.setOpacity(1);
		root.getChildren().add(ivLogoBackground);

		Text choiseText = factory.textFactory(info, Style.font, Style.gray, 55, 70);
		root.getChildren().add(choiseText);

		/*
		 * Ϊ�����ۣ�������С��ť
		 */
		Rectangle suoxiaoBackground = factory.rectangleFactory(60, 60, 180, 0, Style.blue, 0);
		root.getChildren().add(suoxiaoBackground);
		ImageView ivSuoXiao = factory.graphFactory(30, 30, 195, 10, new Image("image/suoxiao1.png"));
		root.getChildren().add(ivSuoXiao);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(suoxiaoBackground, Style.gray, 0.3, ivSuoXiao,
				new Image("image/suoxiao2.png"), null, null);
		eventFactory.setRectangleMouseExit(suoxiaoBackground, Style.gray, 0, ivSuoXiao, new Image("image/suoxiao1.png"),
				null, null);
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(suoxiaoBackground, Style.gray, 0.3, ivSuoXiao, new Image("image/suoxiao2.png"),
				null, null);
		eventFactory.setIVMouseExit(suoxiaoBackground, Style.gray, 0, ivSuoXiao, new Image("image/suoxiao1.png"), null,
				null);

		/*
		 * ���ϽǵĹرհ�ť �ɾ�����Ϊ������һ��ͼƬ��Ϊ��ʾͼ��
		 */
		Rectangle closeBackground = factory.rectangleFactory(60, 60, 240, 0, Style.blue, 0);
		closeBackground.setArcWidth(0);
		closeBackground.setArcHeight(0);

		root.getChildren().add(closeBackground);
		ImageView ivClose = factory.graphFactory(30, 30, 255, 10, new Image("image/close1.png"));
		root.getChildren().add(ivClose);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(closeBackground, Style.gray, 0.3, ivClose, new Image("image/close2.png"),
				null, null);
		eventFactory.setRectangleMouseExit(closeBackground, Style.gray, 0, ivClose, new Image("image/close1.png"), null,
				null);
		closeBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(closeBackground, Style.gray, 0.3, ivClose, new Image("image/close2.png"), null,
				null);
		eventFactory.setIVMouseExit(closeBackground, Style.gray, 0.3, ivClose, new Image("image/close1.png"), null,
				null);
		ivClose.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});

	}

}
