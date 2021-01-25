package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//import Detection.Launch;
import Detection.*;

/*
 * ��������ͼ����Ϣ���ṩ����ָ�����ļ�����ȡ�ļ������ݣ�Ȼ����ת��
 * ת��Ϊͼ������Ҫ�ı����ʽ
 */
public class GetData {

	// public static void main(String args[]){
	// GetData gd = new GetData();
	// gd.getSimNum();
	// }
	/*
	 * �Ի���Ĵ���
	 */
	Dialog dialog = new Dialog();
	/*
	 * ��׼��ʹ�÷����������������������ã�ͨ��������������ȡ���е�����
	 */
	Launch launch = new Launch();

	/*
	 * ��ȡ�Ƚϵ����ƶȵ���Ϣ
	 */
	public void getSimNum() {
		/*
		 * ����ֵ����Ϊ-1����ÿ�μ������ݾ�����뵽ָ���ļ���
		 */
		launch.pd.setTHRESHOLD(-1);
		launch.setDefaultPath("D:/0SaveUploadFile");
		launch.readDirctoriesWithAll();
		launch.pd.launchSimiMeasWithAll();
		this.writeFile();
		System.out.println("*******************************************");
		this.readFile("D:\\111.pd");
	}

	/*
	 * ���û��鿴��ϸ��Ϣ���û������Լ��������Ϣ���鿴������ϸ����Ϣ
	 */
	public List<String> getMore(int index) {
		List<String> list = new ArrayList<String>();
		switch (index) {
		case 1:
			setSimpleList(1.0, 0.95, list);
			break;
		case 2:
			setSimpleList(0.95, 0.9, list);
			break;
		case 3:
			setSimpleList(0.9, 0.8, list);
			break;
		case 4:
			setSimpleList(0.8, 0.7, list);
			break;
		case 5:
			setSimpleList(0.7, 0.6, list);
			break;
		case 6:
			setSimpleList(0.6, -1.0, list);
			break;
		default:
			break;
		}
		return list;

	}

	public void setSimpleList(double d1, double d2, List<String> list) {
		for (int i = 0; i < simpleSimInfo.size(); i += 3) {
			/*
			 * �����������ƶ���Ϣ�洢�����飬Ȼ��Ƚ������е���Ϣ
			 */
			if (Double.valueOf(this.simpleSimInfo.get(i + 2)) > d2
					&& Double.valueOf(this.simpleSimInfo.get(i + 2)) <= d1) {
				list.add(simpleSimInfo.get(i));
				list.add(simpleSimInfo.get(i + 1));
				list.add(simpleSimInfo.get(i + 2));
			}
		}
	}

	/*
	 * �´��������飬���д�������ƶȱȽϵļ�Ҫ��Ϣ
	 */
	List<String> simpleSimInfo = new ArrayList<String>();
	List<Integer> simpleSimList = new ArrayList<Integer>();

	/*
	 * �����������ҳ��󣬶��Һܶ����ݿ��������õģ����ǵ����������ǽ���洢���� ������Ҫ��������Ϣ�ĸ�������ı���ֵ
	 * �����ȽϵĽ���ܶ࣬��ʱѡȡ��ֻ�����ĳ��ͬѧ�����ֵ
	 */
	public void simpleSimList() {
		String name1 = null;
		String name2 = null;
		double result = 0;
		/*
		 * ���ȸ���simInfo��ֵȡ����һλͬѧ��������ƶ�
		 */
		for (int i = 0; i < simInfo.size();) {
			/*
			 * ����Ĭ��ȡ��һ����һ��ֵ ��ֵ ����һ��ͬѧ��������siminfo��λ���ж�
			 */
			name1 = simInfo.get(i);
			name2 = simInfo.get(i + 1);
			result = Double.valueOf(simInfo.get(i + 2));
			/*
			 * ���Ȱɵ�һλͬѧ����Ϣ������������
			 */
			simpleSimInfo.add(name1);
			simpleSimInfo.add(name2);
			simpleSimInfo.add(String.valueOf(result));
			/*
			 * ��������һ�飬ȡ����һ���ֵȻ������ж�
			 */
			int j = i + 3;
			for (; j < simInfo.size(); j += 3) {
				if (!simInfo.get(j).equals(name1)) {
					/*
					 * ����ȽϺ���ȣ�˵��һ��ͬѧ�ı����ѽ����
					 */
					i = j;
					break;
				}
				/*
				 * �����ȣ���˵������ͬһ��ͬѧ����ʱ��Ҫ�������ƶȵ�ֵ���бȽ�
				 * �����֮ǰ�����ƶ���ȸ�����result��ֵ��name1��ֵ������
				 */
				if (result < Double.valueOf(simInfo.get(j + 2))) {
					result = Double.valueOf(simInfo.get(j + 2));
					name2 = simInfo.get(j + 1);
					/*
					 * ��֮ǰ�����ѧ������Ϣɾ�����ٽ��˴��ĸ��º����Ϣ�����ȥ
					 */
					int size = simpleSimInfo.size();
					simpleSimInfo.remove(size - 1);
					simpleSimInfo.remove(size - 2);
					simpleSimInfo.add(name2);
					simpleSimInfo.add(String.valueOf(result));
				}
			}
			if (j >= simInfo.size()) {
				break;
			}
		}
	}

	/*
	 * ���ݼ򻯺����Ϣ��ͳ������
	 */
	public void initSimpleList() {

		for (int i = 0; i < this.range.length; i++) {
			simpleSimList.add(0);
		}

		for (int i = 0; i < simpleSimInfo.size(); i += 3) {
			if (Double.valueOf(simpleSimInfo.get(i + 2)) > 0.95) {
				simpleSimList.set(0, simpleSimList.get(0) + 1);
			} else if (Double.valueOf(simpleSimInfo.get(i + 2)) > 0.90) {
				simpleSimList.set(1, simpleSimList.get(1) + 1);
			} else if (Double.valueOf(simpleSimInfo.get(i + 2)) > 0.80) {
				simpleSimList.set(2, simpleSimList.get(2) + 1);
			} else if (Double.valueOf(simpleSimInfo.get(i + 2)) > 0.70) {
				simpleSimList.set(3, simpleSimList.get(3) + 1);
			} else if (Double.valueOf(simpleSimInfo.get(i + 2)) > 0.60) {
				simpleSimList.set(4, simpleSimList.get(4) + 1);
			} else {
				simpleSimList.set(5, simpleSimList.get(5) + 1);
			}
		}

		System.out.println(simpleSimList);
	}

	public void showSimpleList() {
		for (int i = 0; i < simpleSimInfo.size(); i += 3) {
			System.out.print(simpleSimInfo.get(i) + " & ");
			System.out.print(simpleSimInfo.get(i + 1) + " @ ");
			System.out.println(simpleSimInfo.get(i + 2));
		}
	}

	/*
	 * ʹ�õ�ʱ��һ����Ҫ��ʼ��������飬���ֱ�ӵ���
	 */
	public GetData() {
		this.initSimMap();
		this.initSimList();
	}

	/*
	 * ��ȡ��ָ����Χ��ֵ
	 */
	public void getListInfo(double num1, double num2) {
		if (num1 < num2) {
			System.out.println("���ƶȲ鿴��Χ����");
			return;
		}
		/*
		 * simInfo�����д�������еıȽ���Ϣ��ÿ����Ϊһ��
		 */
		for (int i = 0; i < simInfo.size();) {
			double d = Double.valueOf(simInfo.get(i + 2));
			if (d >= num2 && d <= num1) {
				System.out.println("Get the value:" + simInfo.get(i) + " and " + simInfo.get(i + 1) + "" + d);
			}
			i++;
			i++;
			i++;
		}
	}

	/*
	 * ����ͳ�Ʊ�������ֵ��Χ��ͳ�� �������ڴ�ŷ�Χ�ڵ�ֵ map���ڴ����Ӧ��Χ���±�
	 */
	public List<Integer> simList = new ArrayList<Integer>();
	Map<Integer, Double> simMap = new TreeMap<Integer, Double>();
	/*
	 * ���������û�������Ҫ�鿴��ĳ���䷶Χ�ڵľ�����Ϣ ��ʱͨ����ȡ������飬�Ӷ���ȡ������Ϣ����ʾ��UI�ϡ�
	 */
	public List<String> simInfo = new ArrayList<String>();
	/*
	 * �жϵķ�Χ--ͳ�Ƶ�����
	 */
	double[] range = { 0.95, 0.90, 0.80, 0.70, 0.60, -1 };

	/*
	 * ��ʼ��map�����洢��Ϣ��ͳ��������±�ֵ
	 */
	public void initSimMap() {
		for (int i = 0; i < range.length; i++) {
			simMap.put(i, range[i]);
		}
	}

	/*
	 * ��ʼ��ͳ������--ͳ�����ƶȲ�ͬ��Χ�ڵ�ֵ
	 */
	public void initSimList() {
		for (int i = 0; i < range.length; i++) {
			simList.add(0);
		}
	}

	/*
	 * ���ݱȽϵĽ������ָ���±����������1���Ӷ�����ͳ�� ͬʱ��������뵽�����У�����֮��Ķ�ȡ����
	 */
	public void setSimList(String name1, String name2, double d) {
		for (int i = 0; i < simList.size(); i++) {
			/*
			 * �ȽϽ������0.95����Ӧλ�õ���ֵ��1���Դ�����
			 */
			if (d >= simMap.get(i)) {
				simList.set(i, simList.get(i) + 1);
				simInfo.add(name1);
				simInfo.add(name2);
				simInfo.add(String.valueOf(d));
				return;
			}
		}
	}

	/*
	 * ��ʾ��ϸ��Ϣ--��ͼ����ʾ�ĸ����������ϸ��Ϣ ���ܲ�����ָ����.pd�ļ���·��
	 */
	public void showDetialInfo(String path) {

		if (null == path) {
			dialog.showDialog("����ѡ���ļ���");
			return;
		}
		/*
		 * ͨ����ȡָ�����ļ����Ӷ���ʼ���������� simList��simInfo
		 */
		this.simpleSimInfo.clear();
		this.simpleSimList.clear();
		this.readFile(path);
		/*
		 * ��ʼ����Ϻ󣬿��Ի����Ӧ����ֵ����ʾ����
		 */
		this.simpleSimList();
		// this.showSimpleList();
		this.initSimpleList();
		// String info1 = null;
		// String info2 = null;
		// String info3 = null;
		// String info4 = null;
		// String info5 = null;
		// String info6 = null;
		// for(int i = 0; i < 6; i++){
		// this.simList
		// }
		dialog.showDetailDialog(this, 
				"1.0 -0.95->" + this.simpleSimList.get(0) +"��",
				"0.95- 0.9->" + this.simpleSimList.get(1) +"��",
				"0.9 - 0.8->" + this.simpleSimList.get(2) +"��",
				"0.8 - 0.7->" + this.simpleSimList.get(3) +"��", 
				"0.7 - 0.6->" + this.simpleSimList.get(4) +"��",
				"0.6 - 0.0->" + this.simpleSimList.get(5) +"��");

	}

	/*
	 * ȥ��ָ���ļ�������,ͬʱ�����е��������δ�����simList������ �ڶ�ȡ�ļ����ݵ�ͬʱ������simList�е���ֵ
	 */
	public String readFile(String path) {
		String str = "name&name@result";
		File file = new File(path);
		simList.clear();
		simInfo.clear();
		initSimList();
		if (file.exists()) {
			/*
			 * �жϽ��ܵ���������ͨ���ļ�����һ��Ŀ¼�������Ŀ¼���ٴε����ļ��з���
			 */
			try {
				/*
				 * ����Ǹ���ͨ�ļ�����ʼ��������ļ���ȡ
				 */
				if (true == file.isFile()) {
					// �������ֹܵ��������ȡ�ļ�������
					FileInputStream in = new FileInputStream(file);
					InputStreamReader ipr = new InputStreamReader(in, "GBK");
					BufferedReader bf = new BufferedReader(ipr);
					// �����ж��Ƿ���ָ�����ļ�
					if (!str.equals(bf.readLine())) {
						bf.close();
						ipr.close();
						in.close();
						return "�ⲻ��ָ�����ļ���";
					}
					// ÿ�ζ�ȡһ�У�����������������������Ĳ��������û��������
					while ((str = bf.readLine()) != null) {
						String name1;
						String name2;
						double result;
						name1 = str.substring(0, str.indexOf("&"));
						name2 = str.substring(str.indexOf("&") + 1, str.indexOf("@"));
						result = Double.valueOf(str.substring(str.indexOf("@") + 1, str.length()));
						// System.out.println(name1 + "&" + name2 + "@" +
						// result);
						simInfo.add(name1);
						simInfo.add(name2);
						simInfo.add(String.valueOf(result));
						setSimList(name1, name2, result);
					}
					// һ�ιرո��ֹܵ�
					bf.close();
					ipr.close();
					in.close();
					/*
					 * ����Ǹ��ļ��У���Ӧ�ñ�����Ϊ�Ҳ��ᴦ��=��=
					 */
				} else if (true == file.isDirectory()) {
					System.out.println("���Ǹ��ļ���Ŷ��");
					return "���Ǹ��ļ���Ŷ��";
				} else {
					System.out.println("whats the fuck!");
					return "whats the fuck!";
				}
			} catch (FileNotFoundException e) {
				System.out.println("�ļ�û���ҵ���ϵͳ�˳���");
				return "�ļ�û���ҵ���ϵͳ�˳���";
			} catch (IOException e) {
				System.out.println("�򿪴�ʧ�ܣ�ϵͳ�˳���");
				return "�򿪴�ʧ�ܣ�ϵͳ�˳���";
			}
		} else {
			System.out.println("����ָ���ļ������ڣ�");
			return "����ָ���ļ������ڣ�";
		}
		return "�ļ���ȡ���";
	}

	/*
	 * �������������ڽ��ȽϺ�Ľ��--��ֵ���뵽ָ�����ļ��� �洢��ʽ���ļ���1&�ļ���2@�ȽϽ��
	 */
	public void writeFile() {
		File file = null;
		if (!this.launch.pd.info.isEmpty()) {
			// System.out.println(this.launch.pd.info.get(0).substring(
			// 0, 8));
			file = crateFile(this.launch.pd.info.get(0).substring(0, 6));
		} else {
			return;
		}
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new FileWriter(file));
			output.append("name&name@result\n");
			for (int i = 0; i < this.launch.pd.info.size();) {
				output.append(this.launch.pd.info.get(i++));
				output.append("&");
				output.append(this.launch.pd.info.get(i++));
				output.append("@");
				setSimList(this.launch.pd.info.get(i - 2), this.launch.pd.info.get(i - 1),
						Double.valueOf(this.launch.pd.info.get(i)));
				output.append(this.launch.pd.info.get(i++));
				output.append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != output) {
					output.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * ��Ĭ�ϵ�·���´����ļ�
	 */
	public File crateFile(String name) {

		File file = new File(Launch.DefaultPath + "/" + name + ".pd");
		try {
			if (!file.exists()) {
				file.createNewFile();
				return file;
			} else {
				file.delete();
				return file;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

}
