package application;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ComponentFactory {
	
	/*
	 * ������������
	 * ���ܲ����������ε�width��hight��position��x,y��,��ɫ����͸����
	 * �������ɺ�Ľ��
	 */
	public Rectangle rectangleFactory(double width, double hight,
			double layoutX, double layoutY, Color color, double opacity){
		Rectangle retangle = new Rectangle();
		retangle.setHeight(hight);
		retangle.setWidth(width);
		retangle.setLayoutX(layoutX);
		retangle.setLayoutY(layoutY);
		retangle.setFill(color);
		retangle.setOpacity(opacity);
		return retangle;
	}
	/*
	 * ���ɾ��κ���Ҫ�����任Ч������˽���Ӧ�ľ�����Ϊ�������룬Ȼ��ı���ֵ���Ӷ���������Ч
	 * ���ܲ��������ε����ã��ı����ɫ����͸����
	 */
	public void changeRectangle(Rectangle rectangle, Color color, double opacity){
		rectangle.setFill(color);
		rectangle.setOpacity(opacity);
	}

	/*
	 * ����ͼƬ���ɹ���
	 * ���ܲ�����ͼƬ��width��hight��position��x,y��
	 */
	public ImageView graphFactory(double width, double hight,
			double layoutX, double layoutY, Image image){
		ImageView iv = new ImageView(image);
		iv.setFitHeight(hight);
		iv.setFitWidth(width);
		iv.setLayoutX(layoutX);
		iv.setLayoutY(layoutY);
		return iv;
	}
	/*
	 * ��ʱ��Ҫ����ͼƬ����ʽ����ʱ������ĵ�ͼƬ����
	 */
	public void changeImageView(ImageView iv, Image image){
		iv.setImage(image);
	}
	/*
	 * �ı������ɹ���
	 */
	public Text textFactory(String content, Font font, Color color,
			double layoutX, double layoutY){
		Text text = new Text(content);
		text.setFont(font);
		text.setFill(color);
		text.setLayoutX(layoutX);
		text.setLayoutY(layoutY);
		text.setTextOrigin(VPos.TOP);
		return text;
	}
	
	/*
	 * �����������ɣ��رգ���С
	 * ���ܲ�������ƽ̨�������¼������رգ��������ӵ���壬���Ĵ�С������Ĵ�С
	 * Ĭ������λ��Ϊ���Ͻ�
	 */
	EventFactory eventFactory = new EventFactory();
	public void component(Stage primaryStage, Group root,
			double width, double hight, double widthSize, double hightSize){
		/*
		 * Ϊ�����ۣ�������С��ť
		 */
		Rectangle suoxiaoBackground = this.rectangleFactory(widthSize, hightSize,
				width - 2*widthSize, 0, Style.blue, 0);
		root.getChildren().add(suoxiaoBackground);
		ImageView ivSuoXiao = this.graphFactory(40, 40,
				width - 2*widthSize +30, 30,
				new Image("image/suoxiao1.png"));
		root.getChildren().add(ivSuoXiao);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(suoxiaoBackground, Style.gray, 0.3,
				ivSuoXiao, new Image("image/suoxiao2.png"), null, null);
		eventFactory.setRectangleMouseExit(suoxiaoBackground, Style.gray, 0,
				ivSuoXiao, new Image("image/suoxiao1.png"), null, null);
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(suoxiaoBackground, Style.gray, 0.3,
				ivSuoXiao, new Image("image/suoxiao2.png"), null, null);
		eventFactory.setIVMouseExit(suoxiaoBackground, Style.gray, 0,
				ivSuoXiao, new Image("image/suoxiao1.png"), null, null);
		
		/*
		 * ���ϽǵĹرհ�ť
		 * �ɾ�����Ϊ������һ��ͼƬ��Ϊ��ʾͼ��
		 */
		Rectangle closeBackground = this.rectangleFactory(widthSize, hightSize,
				width - widthSize, 0, Style.blue, 0);
		closeBackground.setArcWidth(0);
		closeBackground.setArcHeight(0);
		
		root.getChildren().add(closeBackground);
		ImageView ivClose = this.graphFactory(40, 40, 
				width - widthSize + 30, 30,
				new Image("image/close1.png"));
		root.getChildren().add(ivClose);
		/*
		 * Ϊ����������괦���¼�
		 */
		eventFactory.setRectangleMouseEnter(closeBackground, Style.gray, 0.3,
				ivClose, new Image("image/close2.png"), null, null);
		eventFactory.setRectangleMouseExit(closeBackground, Style.gray, 0,
				ivClose, new Image("image/close1.png"), null, null);
		closeBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		/*
		 * ΪͼƬ������괦���¼�
		 */
		eventFactory.setIVMouseEnter(closeBackground, Style.gray, 0.3,ivClose, 
				new Image("image/close2.png"), null, null);
		eventFactory.setIVMouseExit(closeBackground, Style.gray, 0.3,ivClose, 
				new Image("image/close1.png"), null, null);
		ivClose.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
	}
	
}