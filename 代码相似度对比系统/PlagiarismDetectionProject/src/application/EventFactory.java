package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class EventFactory {
	/*
	 * �����ܲ�������ͳһ����Ӧ�¼���ͨ����������ֵ��Ȼ��Ϊ��������Ӧ�¼�
	 * �������¼����ı���ε���ɫ��͸����
	 */
	public void setRectangleMouseEnter(Rectangle rectangle, Color color, 
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		rectangle.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}			
		});
	}
	public void setRectangleMouseExit(Rectangle rectangle, Color color,
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		rectangle.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}
		});
	}
	
	/*
	 * ͼƬ���¼�������
	 * ��Ҫ���Ǹı�ͼƬ����ʽ
	 */
	public void setIVMouseEnter(Rectangle rectangle, Color color,
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		iv.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}
		});
	}
	public void setIVMouseExit(Rectangle rectangle, Color color,
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		iv.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}
		});
	}
	
	/*
	 * �ı���������Ӧ�¼�
	 */
	public void setTextMouseEnter(Rectangle rectangle, Color color,
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		text.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}
		});
	}
	public void setTextMouseExit(Rectangle rectangle, Color color,
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		text.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}
		});
	}
}
