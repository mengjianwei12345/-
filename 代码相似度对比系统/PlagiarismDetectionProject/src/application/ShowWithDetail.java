package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Detection.Launch;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ShowWithDetail {
	/*
	 * ����һ���ı��Ƚ��������
	 */
	CompareForApp cfa = new CompareForApp();
	/*
	 * Ϊ�ļ�ѡ��������Ĭ�ϵ�ѡ��·��
	 */
	File defaultFilePath = new File("D:");
	/*
	 * ����һ���������������ã�����֮��ĺ�������
	 */
	Launch launch = new Launch();

	/*
	 * ��������Ӧ�õĳ��Ϳ�,���е�������ϵͳ�ı���ı�
	 */
	int applicationWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 900;
	int applicationHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 200;
	/*
	 * ����GridPane�ĸ���ģ��ı߾�
	 */
	int gridPaneHgap = 20;
	int gridPaneVgap = 20;
	/*
	 * ��������
	 */
	Font font = new Font("Arial", 22);
	/*
	 * ���������ַ��������ڱ���ѡ���ļ��ľ���·�� ����֮ǰ������Ӧ�¼�Ϊ�ڲ��࣬���ò�������·���洢��ȫ��������
	 */
	String file1Path = "";
	String file2Path = "";
	/*
	 * �½��Ĺ���
	 */
	ComponentFactory factory = new ComponentFactory();
	EventFactory eventFactory = new EventFactory();
	Dialog dialog = new Dialog();
	SetGraph setGraph = new SetGraph();
	GetData getData = new GetData();
	
	public void showWithDetail1(){
		
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setHeight(800);
		stage.setWidth(900);
		Group root = new Group();
		Scene scene = new Scene(root, 900, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("������ϸ��ʾ");
		stage.show();		
		//���UI�ӱ���
		ImageView ivBackground = factory.graphFactory(
				900, 800, 0, 0, new Image("image/48.jpg"));
		root.getChildren().add(ivBackground);
		ImageView ivLogoBackground = factory.graphFactory(
				600, 600, 180, 150, new Image("image/logo2.png"));
		ivLogoBackground.setOpacity(0.5);
		root.getChildren().add(ivLogoBackground);
		//Ϊ��������logo
		ImageView ivLogo = new ImageView(new Image("image/logo.png"));
		ivLogo.setFitHeight(100);
		ivLogo.setFitWidth(100);
		ivLogo.setLayoutX(0);
		ivLogo.setLayoutY(0);
		root.getChildren().add(ivLogo);
		//�ṩ�û�ѡ���ļ��İ�ť----�ļ�ѡ����
		Rectangle choiseFile1 = factory.rectangleFactory(
				100, 100, 0, 100, Style.gray, 0);
		root.getChildren().add(choiseFile1);
		ImageView ivChoise1 = factory.graphFactory(
				80, 60, 15, 122, new Image("image/fileopen1.png"));
		root.getChildren().add(ivChoise1);
		/*
		 * ����һ���ı���������ʾ����ִ�е��ļ�����
		 */
		Text currentFile1 = factory.textFactory(
				"�ȴ��ļ�\n  ����...", Style.font, Style.gray, 3, 230);
		Font currentFont1 = new Font("΢���ź�", 20);
		currentFile1.setFont(currentFont1);
		root.getChildren().add(currentFile1);
		/*
		 * ʵ�����ļ�ѡ����
		 */
		FileChooser fc1 = new FileChooser();
		File file1 = new File("D:\\");
		/*
		 * �����ļ�ѡ�����ı���
		 */
		fc1.setTitle("ѡ���ļ�");
		/*
		 * �����ļ�ѡ������Ĭ��ѡ��·��
		 */
		fc1.setInitialDirectory(file1);
		String str = "dsjfhdsjkf";
		str.substring(0, 8);
		choiseFile1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc1.showOpenDialog(stage);
			if (null != f) {
				currentFile1.setText(f.getName().substring(0,6)
						+"\n"+f.getName().substring(6,f.getName().length()));
				this.file1Path = f.getAbsolutePath();
			}
			
		});
		ivChoise1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc1.showOpenDialog(stage);
			if (null != f) {
				currentFile1.setText(f.getName().substring(0,6)
						+"\n"+f.getName().substring(6,f.getName().length()));
				this.file1Path = f.getAbsolutePath();
			}
		});
		eventFactory.setRectangleMouseEnter(
				choiseFile1, Style.gray, 0.3, ivChoise1, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				choiseFile1, Style.gray, 0, ivChoise1, 
				new Image("image/fileopen1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				choiseFile1, Style.gray, 0.3, ivChoise1, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				choiseFile1, Style.gray, 0, ivChoise1, 
				new Image("image/fileopen1.png"), null, Style.gray);
		/*
		 * �ڶ����ļ�ѡ����
		 */
		//�ṩ�û�ѡ���ļ��İ�ť----�ļ�ѡ����
		Rectangle choiseFile2 = factory.rectangleFactory(
				100, 100, 0, 300, Style.gray, 0);
		root.getChildren().add(choiseFile2);
		ImageView ivChoise2 = factory.graphFactory(
				80, 60, 15, 322, new Image("image/fileopen1.png"));
		root.getChildren().add(ivChoise2);
		eventFactory.setRectangleMouseEnter(
				choiseFile2, Style.gray, 0.3, ivChoise2, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				choiseFile2, Style.gray, 0, ivChoise2, 
				new Image("image/fileopen1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				choiseFile2, Style.gray, 0.3, ivChoise2, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				choiseFile2, Style.gray, 0, ivChoise2, 
				new Image("image/fileopen1.png"), null, Style.gray);
		/*
		 * ����һ���ı���������ʾ����ִ�е��ļ�����
		 */
		Text currentFile2 = factory.textFactory(
				"�ȴ��ļ�\n  ����...", Style.font, Style.gray, 3, 430);
		Font currentFont2 = new Font("΢���ź�", 20);
		currentFile2.setFont(currentFont2);
		root.getChildren().add(currentFile2);
		/*
		 * ʵ�����ļ�ѡ����
		 */
		FileChooser fc2 = new FileChooser();
		File file2 = new File("D:\\");
		/*
		 * �����ļ�ѡ�����ı���
		 */
		fc2.setTitle("ѡ���ļ�");
		/*
		 * �����ļ�ѡ������Ĭ��ѡ��·��
		 */
		fc2.setInitialDirectory(file2);
		/*
		 * ������Ӧ�¼����������ļ�ѡ��buttonʱ���ᵯ��ѡ��ҳ�湩�û�ѡ�� ���û�ѡ����Ӧ���ļ��󣬻����ļ��ľ���·������ʾ�ļ�����
		 */
		choiseFile2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc2.showOpenDialog(stage);
			if (null != f) {
				currentFile2.setText(f.getName().substring(0,6)
						+"\n"+f.getName().substring(6,f.getName().length()));
				this.file2Path = f.getAbsolutePath();
			}	
		});
		ivChoise2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc1.showOpenDialog(stage);
			if (null != f) {
				currentFile2.setText(f.getName().substring(0,6)
						+"\n"+f.getName().substring(6,f.getName().length()));
				this.file2Path = f.getAbsolutePath();
			}
		});
		
		/*
		 * Ϊ����ʾ���ݴ�������ݣ��˴���������sp
		 * ���ù�����������̫�࣬һ��ҳ��治��
		 */
		ScrollPane sp1 = new ScrollPane();
		ScrollPane sp2 = new ScrollPane();
		/*
		 * ���ù�������ֻ���������ƶ�
		 */
		sp1.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp1.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp1.setMinSize(400, 800);
		sp1.setMaxSize(400, 800);
		sp1.setLayoutX(100);
		sp1.setLayoutY(0);
		sp1.setOpacity(0.7);
		sp2.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp2.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp2.setMinSize(400, 800);
		sp2.setMaxSize(400, 800);
		sp2.setLayoutX(500);
		sp2.setLayoutY(0);
		sp2.setOpacity(0.7);
		root.getChildren().add(sp1);
		root.getChildren().add(sp2);
		/*
		 * ��������������ͬʱ����
		 * Ϊ��������Ӽ����¼������һ����������ͬʱ����������鿴
		 */
		sp1.vvalueProperty().addListener((ObservableValue<? extends Number> ov, 
			    Number old_val, Number new_val) -> {
			    	/*
			    	 * ����sp1���ʱ��ͬʱ��ֵ��ֵ�����2��ʵ��ͬ������
			    	 */
			    	sp2.setHvalue(sp1.getHvalue());
			    	sp2.setVvalue(sp1.getVvalue());
			});
		sp2.vvalueProperty().addListener((ObservableValue<? extends Number> ov, 
			    Number old_val, Number new_val) -> {
			    	sp1.setHvalue(sp2.getHvalue());
			    	sp1.setVvalue(sp2.getVvalue());
			});
		
		//�û�ѡ��ʼ���Եİ�ť
		Rectangle start = factory.rectangleFactory(
				100, 100, 0, 500, Style.gray, 0);
		root.getChildren().add(start);
		ImageView ivStart = factory.graphFactory(
				60, 60, 20, 520, new Image("image/start1.png"));
		root.getChildren().add(ivStart);
		eventFactory.setRectangleMouseEnter(
				start, Style.gray, 0.3, ivStart, 
				new Image("image/start2.png"), null, null);
		eventFactory.setRectangleMouseExit(
				start, Style.gray, 0, ivStart, 
				new Image("image/start1.png"), null, null);
		
		start.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			/*
			 * ����ֵ�ĳ�ʼ��
			 */
			this.exchangeInt = 0;
			this.exchangeBoolean = true;
			/*
			 * �ٴΰ��µ�ʱ��û�б仯���������һЩMap
			 */
			this.cfa.programeInfo1.clear();
			this.cfa.programeInfo2.clear();
			/*
			 * ���°�ťʱ���������ļ��ľ���·�������ȥ����ȡ��������Դ������Ϣ ����ʼ��֮ǰ��Map����
			 */
//			getAndSetProgrameInfo(this.file1Path, this.file2Path);
			if(
				getAndSetProgrameInfo(this.file1Path, this.file2Path).equals("file1")
					){
				dialog.showDialog("�ļ�-1��δѡ��");
			} else if(
				getAndSetProgrameInfo(this.file1Path, this.file2Path).equals("file2")){
				dialog.showDialog("�ļ�-2��δѡ��");
			} else {
				this.file1Path = "";
				this.file2Path = "";
				ivLogoBackground.setImage(new Image("image/logo.png"));
				/*
				 * ��ʼ������֮����Ҫ������壬�������Ϊ���������ȥ �ٶ���������Ӧ���޸�
				 */
				compareString(sp1, sp2);
			}
		});
		eventFactory.setIVMouseEnter(
				start, Style.gray, 0.3, ivStart, 
				new Image("image/start2.png"), null, null);
		eventFactory.setIVMouseExit(
				start, Style.gray, 0, ivStart, 
				new Image("image/start1.png"), null, null);
		ivStart.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			ivLogoBackground.setImage(new Image("image/logo4.png"));
			/*
			 * ����ֵ�ĳ�ʼ��
			 */
			this.exchangeInt = 0;
			this.exchangeBoolean = true;
			/*
			 * �ٴΰ��µ�ʱ��û�б仯���������һЩMap
			 */
			this.cfa.programeInfo1.clear();
			this.cfa.programeInfo2.clear();
			/*
			 * ���°�ťʱ���������ļ��ľ���·�������ȥ����ȡ��������Դ������Ϣ ����ʼ��֮ǰ��Map����
			 */
//			getAndSetProgrameInfo(this.file1Path, this.file2Path);
			if(
					getAndSetProgrameInfo(this.file1Path, this.file2Path).equals("file1")
						){
					dialog.showDialog("�ļ�-1��δѡ��");
				} else if(
					getAndSetProgrameInfo(this.file1Path, this.file2Path).equals("file2")){
					dialog.showDialog("�ļ�-2��δѡ��");
				} else {
					this.file1Path = "";
					this.file2Path = "";
					ivLogoBackground.setImage(new Image("image/logo.png"));
					/*
					 * ��ʼ������֮����Ҫ������壬�������Ϊ���������ȥ �ٶ���������Ӧ���޸�
					 */
					compareString(sp1, sp2);
				}
		});
		/*
		 * ������ϸ��Ϣ��ʾ
		 */
		Rectangle showDetail1 = factory.rectangleFactory(
				100, 100, 0, 600, Style.gray, 0);
		root.getChildren().add(showDetail1);
		ImageView ivShowDetail1 = factory.graphFactory(
				80, 80, 10, 610, new Image("image/detail1.png"));
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
			//getData.showDetialInfo(absPath);
		});
		ivShowDetail1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			//getData.showDetialInfo(absPath);
		});
		
		/*
		 * Ϊ�����ۣ������رգ���С��ť��
		 */
		//factory.component(stage, root, 900, 800, 100, 100);
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
	/*
	 * �������ļ�ѡ��������ɺ����ȡ�������ļ������ݣ����һ�ô�����Դ���� �ֱ����������� ���ܲ����������ļ��ľ���·��
	 */
	public String getAndSetProgrameInfo(String fileAbPath1, String fileAbPath2) {
		this.launch.setDefaultPath("");
		// �жϴ���Ĳ�������·���Ƿ�Ϊ�գ����Ϊ�����ʾû������ֵ����
		if (!fileAbPath1.equals("")) {
			this.launch.readFile(fileAbPath1);
			this.cfa.programeInfo1 = this.launch.getProgrameInfo();
		} else {
//			System.out.println("file1 is empty!");
			return "file1";
		}
		if (!fileAbPath2.equals("")) {
			this.launch.readFile(fileAbPath2);
			this.cfa.programeInfo2 = this.launch.getProgrameInfo();
		} else {
//			System.out.println("file2 is empty!");
			return "file2";
		}
		return "";
	}
	
	/*
	 * ���ݶ�UC����ϸ�۲�������ɾ���Ƚϣ��������·�����
	 * 		�����������ı���һ��ȡ������--��ʱ���ѽ�����������д��룬�����׿����е�Ӱ��
	 * 			���Ҫ̸Ӱ��Ļ����Ǿ��Ǵ������ˣ�����Щͬѧ�ı������д��ʽ���������ŵ�������
	 * 		Ȼ���ȡ�������д���һ�Զ�ıȽϣ����鿴�Ƿ���������������д����к���
	 * 		ͬʱΪ�˱���㼶�Ļ��ң��ҹ涨�ַ���ֻ�ܴӶ�Ӧ�㼶���±Ƚϣ�ֱ���ҵ���ͬ���ַ���λ��
	 * 		���û���ҵ����������Ӧ�㼶���ַ���Ҳû���ҵ���ȵ��ַ�����������ͬʱ��λdifferent
	 * 		���ͬ���������ַ���֮�����ҽ���һ���ַ����ҵ���ͬ�ַ���������һ�ߵ��ַ�����Ӧ�հױ�ǩ
	 * 		�����������ߵ��ַ�������λ��ȱ�ǩ
	 * ʵ�ֲ��裺
	 * 	1.����Map����ȡ�����д���
	 * 	2.�����д��봦�����ȽϺ�����
	 * 		���������������ַ����������ʽ������ӣ�Ȼ����һ���ַ�����ά����
	 * 		���ն�ά�ַ�������ķ�������һϵ�е��жϴ������󷵻�һ��һά����
	 * 		��������Ƿ���һ����һ�׵Ķ�ά���飬���ǿ��ǵ������ԣ����Ƿ���һ��һά���飩
	 * 		��ֵ���壺0,����������ȣ�1������������ȣ�2�������������У�3�����ұ��������
	 * 		
	 * 		����������ʵ���뿼�ǣ����Ƿ���һ���޸Ĺ��Ķ�ά�ַ�������
	 * 	3.���ݱȽϺ������صĽ�����к����ڲ��������Ӳ���
	 * 		�����ݶ�ά�ַ���������±��ҵ���Ӧ��һά������±��ֵ��Ȼ�������Ӧ��ֵ
	 * 		�������ͬ�ı�ǩ�����������������ڡ�
	 */
	/*
	 * getStringNum��ֵΪ������ַ��������ά��
	 */
	final static int getStringNum = 60;
	public void compareString(ScrollPane sp1, ScrollPane sp2){
		/*
		 * ���ڽ�����label����textarea �����ı���ʾ�Ĵ�С�������ã���������\n�س���������
		 */
		VBox vboxBig1 = new VBox();
		VBox vboxBig2 = new VBox();
		/*
		 * ����VBox�ڲ���������¼��
		 */
		vboxBig1.setSpacing(0);
		vboxBig2.setSpacing(0);
		
		/*
		 * ��ʽ�ıȽϴ���
		 * ������ȡ�����д���
		 * 
		 * ���������α����ڱ���Map
		 */
		Iterator<Map.Entry<Integer, String>> it1 = this.cfa.programeInfo1.entrySet().iterator();
		Iterator<Map.Entry<Integer, String>> it2 = this.cfa.programeInfo2.entrySet().iterator();
		/*
		 * �ȴ���ȡ��������ֵ���бȽ�
		 */
		Map.Entry<Integer, String> entry1 = null;
		Map.Entry<Integer, String> entry2 = null;

		while(it1.hasNext() && it2.hasNext()){
			/*
			 * ��������ArrayList�������ά����
			 */
			List<Integer> numInfo1 = new ArrayList<Integer>();
			List<Integer> numInfo2 = new ArrayList<Integer>();
			List<String> proInfo1 = new ArrayList<String>();
			List<String> proInfo2 = new ArrayList<String>();
			/*
			 * Ϊ�˴�����ǩ������Ҫ�к�
			 */
			//Integer[][] numInfo = new Integer[DesktopApplication.getStringNum][2];
			/*
			 * �ȴ���ȡ��������ֵ���бȽ�
			 * ͬʱ������ά�������ڴ���ַ���
			 */
			//String[][] proInfo = new String[DesktopApplication.getStringNum][2];
			/*
			 * ����ȡֵn�Σ�ÿ��ȡֵ��Ϻ�Ҫ�����ж��Ƿ���ֵ
			 */
			for(int i = 0; i < ShowWithDetail.getStringNum; i++){
				/*
				 * ÿ��ȡֵ֮ǰ��Ҫ�ж���û��ֵ
				 */
				if(it1.hasNext() && it2.hasNext()){
					entry1 = it1.next();
					entry2 = it2.next();
				/*
				 * ���û��ֵ����ֱ���˳�
				 */
				} else {
					break;
				}
				/*
				 * ����ֵ��֮�󣬽��հ�ȥ������ʵ���Բ�Ҫ����Ϊ֮ǰ�ѽ��������
				 */
				proInfo1.add(entry1.getValue().replace(" ", ""));
				proInfo2.add(entry2.getValue().replace(" ", ""));
				/*
				 * �������±걣�����飬���ڱ�ǩ������
				 */
				numInfo1.add(entry1.getKey());
				numInfo2.add(entry2.getKey());
			}
//			for(int j = 0; j < proInfo1.size(); j++){
//				System.out.print(numInfo1.get(j) + " : " + proInfo1.get(j));
//				System.out.println("          "
//						+ "           " + numInfo2.get(j) + " : " + proInfo2.get(j));
//			}
			/*
			 * ��ֱ���ӣ�����װ�����еĺ��� 
			 */
			VBox[] vbox = createHBox(
					this.cfa.compareStringWithList(proInfo1, proInfo2), proInfo1, proInfo2,
					numInfo1, numInfo2);
			vboxBig1.getChildren().add(vbox[0]);
			vboxBig2.getChildren().add(vbox[1]);
		}
		/*
		 * ���Ǵ��볤�Ȳ�һ��ʣ�µĴ���ֱ�ӱ�־λ2-1����1-2
		 */
		while(it1.hasNext()) {
			entry1 = it1.next();
			vboxBig1.getChildren().add(
					differentHBoxFactory(entry1.getKey().toString(), entry1.getValue())
				);
			vboxBig2.getChildren().add(
					emptyHBoxFactory()
				);
		}
		while(it2.hasNext()){
			entry2 = it2.next();
			vboxBig2.getChildren().add(
					differentHBoxFactory(entry2.getKey().toString(), entry2.getValue())
				);
			vboxBig1.getChildren().add(
					emptyHBoxFactory()
				);
		}
		/*
		 * ������뵽�������������ʾ
		 */
		sp1.setContent(vboxBig1);
		sp2.setContent(vboxBig2);
		/*
		 * ȡֵ��ɺ󣬿����γ�һ����ά�ַ�������
		 */
		//HBox[] hbox = createHBox(this.cfa.compareString(proInfo));
		/*
		 * ���������Ӽ��뵽��������
		 */
//		sp1.setContent(hbox[0]);
//		sp2.setContent(hbox[1]);
	}
	
	/*
	 * ���ݷ��صĽ�������ɲ�ͬ�ı�ǩ�������֮��
	 */
	public VBox[] createHBox(List<Integer> list,
			List<String> proInfo1, List<String> proInfo2
			,List<Integer> numInfo1, List<Integer> numInfo2){
		/*
		 * ���ڽ�����label����textarea �����ı���ʾ�Ĵ�С�������ã���������\n�س���������
		 */
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		/*
		 * ����VBox�ڲ���������¼��
		 */
		vbox1.setSpacing(10);
		vbox2.setSpacing(10);
		/*
		 * �������е�ֵ��˳��ȡ��������������Ӧ�ı�ǩ�����ɹ������£�
		 * 		0�����ɴ���ɫ�ı�ǩ
		 * 		1�����ɿհױ�ǩ
		 * 		2�����ɲ�����ɫ�ı�ǩ
		 */
//		for(int i = 0; i < list.size(); i++){
//			System.out.print(list.get(i) + " ");
//		}
//		System.out.println("\naccording to this number create label!");
		/*
		 * �����±�ֵ�ֱ��ʾ������ȡֵ��ʲôλ����
		 */
		int index1 = 0;
		int index2 = 0;
		/*
		 * �˴�ѭ������2�����Ҵ�0��ʼ����ʾֵ���ӻ��Ǹı��һ�е�ֵ
		 */
		for(int i = 0; i < list.size(); i+=2){
			if(null == list.get(i)){
				continue;
			}
			switch(list.get(i)){
			case 0:
				/*
				 * ֵΪ�㣬��ʾΪ��Ҫ���һ����ɫ��ǩ
				 */
//				vbox1.getChildren().add(
//					sameHBoxFactory(numInfo[index1][0].toString(), proInfo[index1][0])
//				);
				vbox1.getChildren().add(
						sameHBoxFactory(numInfo1.get(index1).toString(), proInfo1.get(index1))		
				);
				/*
				 * index++��˵���ѽ�����ȡֵ����ָ��Ӧ���ƶ�
				 */
				index1++;
				break;
			case 1:
				//System.out.println("index1 = " + index1 + " i = " + i);
				/*
				 * ֵΪ1����ʾ��Ҫ���һ���հױ�ǩ
				 */
//				vbox1.getChildren().add(
//					emptyHBoxFactory()
//				);
				vbox1.getChildren().add(emptyHBoxFactory());
				break;
			case 2:
				//System.out.println("index1 = " + index1 + " i = " + i);
				/*
				 * ֵΪ2����ʾ��Ҫ����һ����ͬ�ı�ǩ��������ֵ�����
				 */
//				vbox1.getChildren().add(
//					differentHBoxFactory(numInfo[index1][0].toString(), proInfo[index1][0])
//				);
				vbox1.getChildren().add(
						differentHBoxFactory(numInfo1.get(index1).toString(), proInfo1.get(index1))
					);
				index1++;
				break;
			default:
				System.out.println("System Account Error!"+list.get(i));
				break;
				//System.exit(0);
			}
		}
		/*
		 * �˴���ѭ����1��ʼ��ÿ�ε���2����ʾ�ǲ����ڶ���
		 */
		for(int i = 1; i < list.size(); i+=2){
			switch(list.get(i)){
			case 0:
				//System.out.println("index2 = " + index2 + " i = " + i);
				/*
				 * ֵΪ�㣬��ʾΪ��Ҫ���һ����ɫ��ǩ
				 */
//				vbox2.getChildren().add(
//					sameHBoxFactory(numInfo[index2][1].toString(), proInfo[index2][1])
//				);
				vbox2.getChildren().add(
					sameHBoxFactory(numInfo2.get(index2).toString(), proInfo2.get(index2))
				);
				/*
				 * index++��˵���ѽ�����ȡֵ����ָ��Ӧ���ƶ�
				 */
				index2++;
				break;
			case 1:
				//System.out.println("index2 = " + index2 + " i = " + i);
				/*
				 * ֵΪ1����ʾ��Ҫ���һ���հױ�ǩ
				 */
				vbox2.getChildren().add(
					emptyHBoxFactory()
				);
				break;
			case 2:
				//System.out.println("index2 = " + index2 + " i = " + i);
				/*
				 * ֵΪ2����ʾ��Ҫ����һ����ͬ�ı�ǩ��������ֵ�����
				 */
//				vbox2.getChildren().add(
//					differentHBoxFactory(numInfo[index2][1].toString(), proInfo[index2][1])
//				);
				vbox2.getChildren().add(
						differentHBoxFactory(numInfo2.get(index2).toString(), proInfo2.get(index2))
					);
				index2++;
				break;
			default:
				System.out.println("System Account Error!"+list.get(i));
				break;
				//System.exit(0);
			}
		}
		VBox[] vbox = new VBox[2];
		vbox[0] = vbox1;
		vbox[1] = vbox2;
		return vbox;
	}
	
	
	/*
	 * ����һ���µķ�����Ҳ����򵥵ķ�����ֱ�ӽ�������ǩͬ���ƶ�
	 * �ڷ׷����ӵı����У�������򵥵��Ǹ���������
	 * 		����һ��ĳ�Ϯ���ԣ��Ķ��Ŀ�����һ��ֻ���������ӣ��򲻴�����ɾ��
	 * ���ڵļ򵥷�ʽΪͬ���ƶ��������Ǿ�������Ĵ��룬���ԱȽϵ�ֵ�����ڿհ��С�
	 */
	public void compareString1(ScrollPane sp1, ScrollPane sp2) {
		
		/*
		 * ���ڽ�����label����textarea �����ı���ʾ�Ĵ�С�������ã���������\n�س���������
		 */
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		/*
		 * ����VBox�ڲ���������¼��
		 */
		vbox1.setSpacing(10);
		vbox2.setSpacing(10);
		/*
		 * ���������α����ڱ���Map
		 */
		Iterator<Map.Entry<Integer, String>> it1 = this.cfa.programeInfo1.entrySet().iterator();
		Iterator<Map.Entry<Integer, String>> it2 = this.cfa.programeInfo2.entrySet().iterator();
		/*
		 * �ȴ���ȡ��������ֵ���бȽ�
		 */
		Map.Entry<Integer, String> entry1 = null;
		Map.Entry<Integer, String> entry2 = null;
		/*
		 * �ȴ���ȡ��������ֵ���бȽ�
		 */
		while(it1.hasNext() && it2.hasNext()){
			entry1 = it1.next();
			entry2 = it2.next();
			/*
			 * �������ֵΪ0����˵�������ַ������
			 * ��ǩȾɫ��Ϳ��Խ����Ǽ��뵽������ȥ
			 */
			if(0 == this.cfa.compareEqual(entry1.getValue(), entry2.getValue())){
				vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
				vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
			/*
			 * ��������ַ��������
			 * ��ǩ����Ⱦɫ��ֱ�Ӽ��������
			 */
			} else {
				vbox1.getChildren().add(differentHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
				vbox2.getChildren().add(differentHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
			}
		}
		/*
		 * ���������Ӽ��뵽��������
		 */
		sp1.setContent(vbox1);
		sp2.setContent(vbox2);
		
	}
	
	/*
	 * ���ñ�ǩ��ʾ������� ����programe�����ֵ���б�ǩ�Ĵ�����Ȼ�����������
	 * ͬʱ������ַ����ȽϺ����������ж�������ǩ�Ƿ���Ҫ�ı���ɫ
	 * ���ⲿ����һ�����������ж��ǵڼ������ߵı���-�ַ��������
	 * ÿ���ξͽ����ƶ���ֱ������������ȫƥ��
	 */
	/*
	 * ���ҽ���ƥ��Ľ��������equalsʱ�Ż��1
	 * ͬʱBoolean��ֵ�����int��ȡģֵ�ĸı���ı�
	 * true������ߣ�false�����ұ� 
	 */
	int exchangeInt = 0;
	boolean exchangeBoolean = true;
	public void compareString0(ScrollPane sp1, ScrollPane sp2) {
		/*
		 * ���ڽ�����label����textarea �����ı���ʾ�Ĵ�С�������ã���������\n�س���������
		 */
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		/*
		 * ����VBox�ڲ���������¼��
		 */
		vbox1.setSpacing(10);
		vbox2.setSpacing(10);
		/*
		 * ���������α����ڱ���Map
		 */
		Iterator<Map.Entry<Integer, String>> it1 = this.cfa.programeInfo1.entrySet().iterator();
		Iterator<Map.Entry<Integer, String>> it2 = this.cfa.programeInfo2.entrySet().iterator();
		/*
		 * �ȴ���ȡ��������ֵ���бȽ�
		 */
		Map.Entry<Integer, String> entry1 = null;
		Map.Entry<Integer, String> entry2 = null;
		if(it1.hasNext() && it2.hasNext()){
			entry1 = it1.next();
			entry2 = it2.next();
			/*
			 * ��ȡ����ֵ��Ȼ����ñȽϷ������бȽ�
			 */
			
			int result = this.cfa.compareEqual(entry1.getValue(), entry2.getValue());
			/*
			 * ����ֵΪ0��˵�������ַ������
			 * ����Ҫ����־λ����1,ͬʱ��Ҫ����ͬʱ���ô���ɫ�ı�ǩ
			 * ͬʱ�����ƶ�λ�������´αȽϣ����ߵ��ĵ�����Ҫ�ƶ�
			 */
			if(0 == result){
				this.exchangeInt++;
				vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
				vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
				this.exchangeBoolean = true;
			/*
			 * �������ȣ���Ĭ���Ǵ���ߣ���һ��λ�ÿ�ʼ���в�ѯ��
			 * ��һ��λ������һ����ͨ�ı�ǩ������λ������һ���հױ�ǩ����ʾ��ͬ
			 * ͬʱ���ñ�־λ�������´��ƶ��Ƚ�ʱ������һ�ߵ��ĵ���Ҫ�ƶ�������һ�߲���
			 */
			} else {
				this.exchangeInt++;
				vbox1.getChildren().add(differentHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
				vbox2.getChildren().add(emptyHBoxFactory());
				this.exchangeBoolean = false;
			}
		}
		/*
		 * ���ݼ�ֵ�����ж��Ƿ���Ҫ�������ǩȾ��
		 * �����α�ͬʱ�ƶ���ͬʱ����label
		 */
		while (it1.hasNext() && it2.hasNext()) {
			/*
			 * �ж�exchange��ֵ�����С�������������Ҫ�ƶ����Ծ�����ߵ��ĵ�
			 */
			if(3 > this.exchangeInt){
				/*
				 * �����жϣ���һ���жϵ�ʱ���Ƿ���ֹ���ȫƥ�伴������Ƿ���Ҫ�ƶ�
				 */
				if(this.exchangeBoolean){
					/*
					 * ����������Ϊ�棬���ʾ�˴αȽ���Ҫ����ͬʱ�ƶ�
					 */
					entry1 = it1.next();
					entry2 = it2.next();
					/*
					 * ��ȡ����ֵ��Ȼ����ñȽϷ������бȽ�
					 */
					int result = this.cfa.compareEqual(entry1.getValue(), entry2.getValue());
					/*
					 * ����ֵΪ0��˵�������ַ������
					 * ����Ҫ����־λ����1,ͬʱ��Ҫ����ͬʱ���ô���ɫ�ı�ǩ
					 * ͬʱ�����ƶ�λ�������´αȽϣ����ߵ��ĵ�����Ҫ�ƶ�
					 */
					if(0 == result){
						this.exchangeInt++;
						vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						this.exchangeBoolean = true;
					/*
					 * �������ȣ���Ĭ���Ǵ���ߣ���һ��λ�ÿ�ʼ���в�ѯ��
					 * ��һ��λ������һ����ͨ�ı�ǩ������λ������һ���հױ�ǩ����ʾ��ͬ
					 * ͬʱ���ñ�־λ�������´��ƶ��Ƚ�ʱ������һ�ߵ��ĵ���Ҫ�ƶ�������һ�߲���
					 */
					} else {
						vbox1.getChildren().add(differentHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(emptyHBoxFactory());
						this.exchangeBoolean = false;
						this.exchangeInt++;
					}
				/*
				 * ��־λΪfalse���������Ҫͬ���ƶ�����Ȼֻ��Ҫ�ƶ�һ��
				 * ��ʱѭ���Ĵ���С�����Σ������ƶ����������
				 */
				} else {
					entry1 = it1.next();
					int result = this.cfa.compareEqual(entry1.getValue(), entry2.getValue());
					if(0 == result){
						this.exchangeInt++;
						vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						this.exchangeBoolean = true;
					} else {
						vbox1.getChildren().add(differentHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(emptyHBoxFactory());
						this.exchangeBoolean = false;
						this.exchangeInt++;
					}
					//entry1 = it1.next();
				}
			/*
			 * �ж�exchange��ֵ��������������������Ҫ�ƶ������ұߵ��ĵ�
			 */
			} else {
				/*
				 * �����жϱ�־λ�Ƿ�Ϊ6������ﵽ��6������Ҫ������Ϊ0
				 */
				if(6 == this.exchangeInt){
					this.exchangeInt = 0;
				}
				/*
				 * �����жϣ���һ���жϵ�ʱ���Ƿ���ֹ���ȫƥ�伴������Ƿ���Ҫ�ƶ�
				 */
				if(this.exchangeBoolean){
					/*
					 * ����������Ϊ�棬���ʾ�˴αȽ���Ҫ����ͬʱ�ƶ�
					 */
					entry1 = it1.next();
					entry2 = it2.next();
					/*
					 * ��ȡ����ֵ��Ȼ����ñȽϷ������бȽ�
					 */
					int result = this.cfa.compareEqual(entry1.getValue(), entry2.getValue());
					/*
					 * ����ֵΪ0��˵�������ַ������
					 * ����Ҫ����־λ����1,ͬʱ��Ҫ����ͬʱ���ô���ɫ�ı�ǩ
					 * ͬʱ�����ƶ�λ�������´αȽϣ����ߵ��ĵ�����Ҫ�ƶ�
					 */
					if(0 == result){
						this.exchangeInt++;
						vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						this.exchangeBoolean = true;
					/*
					 * �������ȣ���Ĭ���Ǵ���ߣ���һ��λ�ÿ�ʼ���в�ѯ��
					 * ��һ��λ������һ����ͨ�ı�ǩ������λ������һ���հױ�ǩ����ʾ��ͬ
					 * ͬʱ���ñ�־λ�������´��ƶ��Ƚ�ʱ������һ�ߵ��ĵ���Ҫ�ƶ�������һ�߲���
					 */
					} else {
						vbox2.getChildren().add(differentHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						vbox1.getChildren().add(emptyHBoxFactory());
						this.exchangeBoolean = false;
						this.exchangeInt++;
					}
				/*
				 * ��־λΪfalse���������Ҫͬ���ƶ�����Ȼֻ��Ҫ�ƶ�һ��
				 * ��ʱѭ���Ĵ����������Σ������ƶ����ұ�
				 */
				} else {
					entry2 = it2.next();
					int result = this.cfa.compareEqual(entry1.getValue(), entry2.getValue());
					if(0 == result){
						this.exchangeInt++;
						vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						this.exchangeBoolean = true;
					} else {
						vbox2.getChildren().add(differentHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						vbox1.getChildren().add(emptyHBoxFactory());
						this.exchangeBoolean = false;
						this.exchangeInt++;
					}
					//entry2 = it2.next();
				}
			}
		}
		/*
		 * ������˭��ʣ�£����ǲ�ͬ�Ĳ��֣����ֻ����ò�ͬ�ĺ��ӹ��̼���
		 */
		while (it1.hasNext()) {
			Map.Entry<Integer, String> entry = it1.next();
			vbox1.getChildren().add(differentHBoxFactory(entry.getKey().toString(), entry.getValue()));
			vbox2.getChildren().add(emptyHBoxFactory());
		}
		while (it2.hasNext()) {
			Map.Entry<Integer, String> entry = it2.next();
			vbox2.getChildren().add(differentHBoxFactory(entry.getKey().toString(), entry.getValue()));
			vbox1.getChildren().add(emptyHBoxFactory());
		}
		/*
		 * ���������Ӽ��뵽��������
		 */
		sp1.setContent(vbox1);
		sp2.setContent(vbox2);
	}
	
	/*
	 * ����һ���պ��ӣ�Ϊ�˷������ߵ�ͬ����ʾ
	 */
	public HBox emptyHBoxFactory(){
		HBox hbox = new HBox();
		Label emptyLabel = new Label();
		emptyLabel.setText("--_--_--_--_--_--_--_--_--_--_--_--_--_--");
		hbox.getChildren().add(emptyLabel);
		return hbox;
	}
	
	
	/*
	 * ÿһ�仰��Ҫ��Ϊһ����������label����������label��������� key��Map�еļ���value��Map�е�ֵ
	 * �˷����Żص���һ����д�õ�HBox
	 */
	/*
	 * ��ӽ�label�е�����ַ�������
	 */
	int minLength = 4;
	public HBox differentHBoxFactory(String key, String value) {
		HBox hbox = new HBox();
		// Label labelProNum = new Label(key);
		/*
		 * �ı�key�ַ����ĳ��ȣ�������ʾ ͬʱȡ����
		 */
		if (key.length() < this.minLength) {
			int temp = this.minLength - key.length();
			for (int i = 0; i < temp; i++) {
				key += "  ";
			}
		}
		Label labelProInfo = new Label(key + value);
		// labelProNum.setMinWidth(20);
		// hbox.setSpacing(20);
		// hbox.getChildren().add(labelProNum);
		hbox.getChildren().add(labelProInfo);
		return hbox;
	}
	/*
	 * key�����кţ�value������Ӧ�еĴ����
	 */
	public HBox sameHBoxFactory(String key, String value) {
		HBox hbox = new HBox();
		// Label labelProNum = new Label(key);
		/*
		 * �ı�key�ַ����ĳ��ȣ�������ʾ ͬʱȡ����
		 */
		if (key.length() < this.minLength) {
			int temp = this.minLength - key.length();
			for (int i = 0; i < temp; i++) {
				key += "  ";
			}
		}
		Label labelProInfo = new Label(key + value);
		labelProInfo.setTextFill(Color.RED);
		// labelProNum.setMinWidth(20);
		// hbox.setSpacing(20);
		// hbox.getChildren().add(labelProNum);
		hbox.getChildren().add(labelProInfo);
		return hbox;
		// HBox hbox = new HBox();
		// Label labelProNum = new Label(key);
		// Label labelProInfo = new Label(value);
		// labelProNum.setMinWidth(20);
		// labelProInfo.setTextFill(Color.RED);
		// hbox.setSpacing(20);
		// hbox.getChildren().add(labelProNum);
		// hbox.getChildren().add(labelProInfo);
		// return hbox;
	}
}
