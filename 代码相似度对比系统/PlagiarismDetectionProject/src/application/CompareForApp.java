package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/*
 * �ı��Ƚ���
 * ������Ҫ�������洢��������Դ�������Ϣ������Mapֵ��
 * �������е�ֵ��������Ӧ�ıȽ�
 */
public class   CompareForApp {
	
		public static void main(String args[]){
//			int[][] a = {
//					{1,2},{2,3},{3,4}
//				};
			String[][] a = new String[3][2];
			System.out.println(a.length);
			int b = a.length;
			for (int i = 0; i < b; i++){
				System.out.println("��άa[" + i + "]����Ϊ" + a[i].length);
			}
			
			List<String> list = new ArrayList<String>();
			list.add("qwe");
			list.add(null);
			list.add("asd");
			list.add(0, "dasda");
			System.out.println(list.get(1));
		}
		/*
		 * ��Ϊһ�ν���ʾ�����ļ��ıȽ��������ʱ����������������Map���������ڴ洢��ص���Ϣ
		 * ��Ŵ�����Դ�����������Ϣ
		 * ��Ӧ��ʵ�ı������������󲿷�ֵ
		 */
		Map<Integer, String> programeInfo1 = new TreeMap<Integer, String>();
		Map<Integer, String> programeInfo2 = new TreeMap<Integer, String>();
		
		/*
		 * �������µ�˼·��������жϺ�����
		 * 	���ܵĲ���Ϊһ����ά�ַ�������
		 * 	���ܹ����Ĳ���һ��һ�н��бȽϣ�Ϊһ�Զ�Ƚϣ�ÿһ�αȽ϶�����ͬ�������¼����ַ����Ƚ�
		 * 	�ȳ��Է���һ����ά��������--�������
		 * 	���ַ�����һ����ά��������Ҫ�������޷�ȷ������ʱ����һ��ArrayList�ȽϺ���
		 * 	list:���ArrayList���ڱ�ʶÿ��ά�ȵ�����
		 * 	numList�����ڴ���һ����ʱ���飬�������������ֱ�ʶ��ʾÿһ�е�����
		 * 		�ȽϷ�����
		 * 			�����ܹ����Ķ�ά�����е�ֵ���бȽϣ���0����ͬ�㼶�͵Ͳ㼶�����£����ַ����Ƚ�
		 * 			��1����ͬ�㼶�͸߲㼶���ַ����Ƚϣ����ϣ������ȽϵĽ����¼��������
		 * 
		 * 	���õ����ֽ��ͣ�
		 * 		-1��ʾû���ҵ�ƥ����ַ���,0~n��ʾ�ҵ���ƥ�䴮�������±�
		 */
		public List<Integer> compareStringWithList(List<String> proList1, List<String> proList2){
//			System.out.println(proList1.size() + " " + proList2.size());
//			for(int i = 0; i < proList1.size(); i++){
//				System.out.println("proList1 = "+proList1.get(i));
//				System.out.println("proList2 = "+proList2.get(i));
//			}
			/*
			 * ����һ����ʶ�����ڲ����������ʶ���ϵ�����ʮ�ı�����
			 * ֮��ת�����ַ������������У����ֵ�������֮ǰ��˵��һ��
			 */
			List<Integer> numList1 = new ArrayList<Integer>();
			List<Integer> numList2 = new ArrayList<Integer>();
			/*
			 * ��������ĳ�ʼ����Ĭ�ϵ�ֵ��Ϊ-1����δ�ҵ�
			 * ��ʼ������Ϊ֮ǰ�趨������ֵ��Ĭ��ֵΪ3
			 */
			for(int i = 0; i < proList1.size(); i++) {
				numList1.add(-1);
				numList2.add(-1);
			}
			/*
			 * ����ȡ����ά�ַ������������
			 */
			int row = proList1.size();
			/*
			 * ����ѭ���������ж��ַ����Ƿ����
			 * �жϵĶ���Ϊͬ��ε��ַ������²��ַ���
			 * 
			 * ��ʱʵ�����������м����飬����֮����ݴ������������Ĵ�������
			 */
			for(int i = 0; i < row; i++){
				/*
				 * ����������������ɨ��ʽ�ıȽ�
				 */
				for(int j = i; j < row; j++){
					/*
					 * �ж϶ԵȻ������²㼶���Ƿ����
					 * �����ȣ�����Ҫbreak���˴αȽ�
					 */
					if(proList1.get(i).equals(proList2.get(j))){
						/*
						 * ͬʱ�����е��±����ҵ���Ӧ���е��±����������
						 */
						numList1.set(i, j);
						/*
						 * break��Ϊ�˲���Ӱ�������ͬ�������´ε��жϲ���Ӱ��
						 */
						break;
					}
				}
				/*
				 * �������һ�ֵ�ѭ������û��,�����������Ϊ֮ǰ�Ѿ���������г�ʼ����Ĭ�Ͼ���û�ҵ�
				 */
			}
			/*
			 * ��������һ�ֵĲ��ң��ѽ�����һ��Ѱ�����
			 * ���ڿ�ʼ�ӵڶ��п�ʼѰ�ң���ʱ��Ѱ�ҹ���Ϊ��
			 * 	�Ƚ�ͬ�����ַ�����ͬ�����ϣ����ϱȽϣ���Ȼ���ٽ��±��¼����
			 */
			for(int i = 0; i < row; i++){
				/*
				 * ����ֻ��ͬ����ͬ�����ϵıȽϣ�������ֵ�಻�ɳ�����ʱ�Ƚϵĵȼ�
				 */
				for(int j = i; j >= 0; j--){
					/*
					 * �ж϶ԵȻ������ϲ㼶������ȣ�����Ҫ����Ӧ�±��Ӧ��ֵ��λ
					 * ͬʱbreak���˴αȽ�
					 */
					if(proList2.get(i).equals(proList1.get(j))){
						/*
						 * ����ҵ���ȵ�ֵ����Ҫ����Ӧλ�õ�ֵ����Ϊ��Ӧ���±�ֵ
						 */
						numList2.set(i, j);
						break;
					}
				}
			}
			/*
			 * �������������αȽϣ��ѽ����������������飬���б�ʶ���ҵ���Ϣ
			 */
			return createLabelInfor2(numList1, numList2);
		}
		/*
		 * ��һ���·�������������
		 * ���ܲ���Ϊ�������ַ���ƥ���±�����
		 * ����ֵΪ��ǩ��������
		 */
		public List<Integer> createLabelInfor2(List<Integer> numList1, List<Integer> numList2){
			adjustArrayList(numList1,numList2);
			
//			for(int i = 0; i < numList1.size(); i++){
//				System.out.print("numlist1 = " + numList1.get(i) + " numlist2 = ");
//				System.out.println(numList2.get(i));
//			}
//			System.out.println("a arraylist with after adjust");
			/*
			 * rightUp�����ж������е��ƶ���࣬�������ƶ�һ�ξͼ�һ�������ƶ�һ�ξͼ�һ
			 * move�ж��Ƿ�Ϊ��һ���ƶ�����Ϊ��������㷨������ǵ�һ���ƶ�����������-1
			 * ���Ĭ�����2-1������2-2
			 */
			int rightUp = 0;
			boolean move = false;
			/*
			 * ����һ���������ڴ����Ҫ���ص�ֵ
			 * ���������ʾ���壺0����ԭֵ������ȱ�ǩ��1��ʾ�հױ�ǩ��2��ʾ����ȵı�ǩ
			 */
			List<Integer> stringList = new ArrayList<Integer>();
			/*
			 * ����һ��ȡֵ
			 */
			for(int i = 0; i < numList1.size(); i++){
				/*
				 * �ж����Ƿ�Ϊ-1�����Ƿ���������ƥ��ֵ
				 */
				if(-1 != numList1.get(i)){
					/*
					 * �����жϹ�ʽ��
					 * 	���׶���number = value - index - rightUP
					 * 	number	��ʾ�������ӿ�����
					 * 	value	��ʾ��ֵ
					 * 	index	��ʾvalueֵ���±�
					 * 	rightUp	��ʾ�����ѽ����ӵĿ���������ֵΪ0
					 * �����жϿ������ӵĿ�����Ŀ
					 */
					int count = numList1.get(i) - i - rightUp;
					if(count < 0){
						System.out.println("count meet error in createLabelInfor2");
						System.exit(0);
					}
					if(0 != count ){
						/*
						 * ���ݼ�������ѭ�����ӿ�����Ŀ��Ҳ������������
						 * ���Ƶ�ͬʱ����rightUp��ֵ��������Ϊmove�������Ѿ��ƶ�������
						 */
						for(int j = 0; j < count; j++){
							stringList.add(1);
							stringList.add(2);
							rightUp++;
							move = true;
						}
						/*
						 * �ƶ����֮�����е���ֵ�ѽ����±�Ϊi��ֵ��ͬ����ʱ��������Ϊ0-0
						 */
						stringList.add(0);
						stringList.add(0);
					} else {
						/*
						 * ���Ϊ0�����������ƶ��������ѽ�ƥ�䵽����ͬ���ַ���
						 */
						stringList.add(0);
						stringList.add(0);
					}			
				/*
				 * ���Ϊ-1�������û���ҵ�ƥ����ַ���
				 * ��ʱ���������������
				 * 	��һ�������ƶ�
				 *  �ڶ���������Ϊ2���������벻ͬ
				 */
				} else {
					/*
					 * �ж��Ƿ��ǵ�һ���ƶ����飬����ƶ��������ٴ��ƶ�
					 */
					if(move){
						/*
						 * rightUpΪ0��ʾ�������ѽ�ͬ�����������ƶ���ֱ������Ϊ2
						 */
						if( 0 == rightUp){
							stringList.add(2);
							stringList.add(2);
						/*
						 * ��Ϊ0˵��������û��ͬ�������ǵ���������ͬ��ֵ��Ҫ�ȶ�
						 * ��ʱͬ�����ȣ��������������--Ϊ�������ӿձ��ǩ
						 * ͬʱ��rightUpֵ��1������ͬ����һ�Ρ�
						 */
						} else {
							stringList.add(2);
							stringList.add(1);
							rightUp--;
						}
					/*
					 * ֮ǰû���ƶ������飬ֱ�ӽ�������Ϊ2���������벻ͬ
					 */
					} else {
						stringList.add(2);
						stringList.add(2);
					}
					
				}
			}
			
//			for(int i = 0; i < stringList.size(); i++){
//				System.out.println("stringList = " + stringList.get(i));
//			}
//			System.out.println("a arraylist with after adjust");
			/*
			 * �����ֵ��Ϊ0�������ͬ�����̳���֮��Ĺ�����������
			 */
			if(0 != rightUp){
				System.out.println("rightUp is not zero!");
				System.exit(0);
			}
			return stringList;
		}
		/*
		 * �ƶ���ߣ��ұߵĴ���,�涨�ı����ӿհ��У��ı߾������ƶ�һ��
		 */
		int moveRight = 0;
		int moveLeft = 0;
		public List<Integer> createLabelInfoWithList(List<Integer> numList1, List<Integer> numList2){
//			System.out.println("createLabelInfo function!----start");
//			for(int i = 0; i < numList1.size(); i++){
//				System.out.print("numlist1 = " + numList1.get(i) + " numlist2 = ");
//				System.out.println(numList2.get(i));
//			}
//			System.out.println("createLabelInfo function!----over");
			/*
			 * ������������е���
			 */
			adjustArrayList(numList1,numList2);
			
//			for(int i = 0; i < numList1.size(); i++){
//				System.out.print("numlist1 = " + numList1.get(i) + " numlist2 = ");
//				System.out.println(numList2.get(i));
//			}
//			System.out.println("a arraylist with after adjust");
			/*
			 * ����һ���������ڴ����Ҫ���ص�ֵ
			 * ���������ʾ���壺0����ԭֵ������ȱ�ǩ��1��ʾ�հױ�ǩ��2��ʾ����ȵı�ǩ
			 */
			List<Integer> stringList = new ArrayList<Integer>();
			/*
			 * ������������ѭ��ȡ����ֵ���ж��Ƿ���Ҫ���бȽ�
			 */
			if(numList1.size() != numList2.size()){
				System.out.println("System Account Error!numList'size not equals!");
				System.exit(0);
			}
			/*
			 * �����ж��������������λ�ò����ã�����ǰ������˿հױ�ǩ���������е���ֵ����
			 * ��ֱ�ӵ��¶�Ӧ�е���ֵ���ƣ��޷�ƥ�䣬���ƥ�䣬�м�͵������հ���
			 * 
			 * ����һ����־λ�������ж��Ƿ�������ӿհ���
			 * �������е���˵����һ�α�����ֻ������һ�οհ��У���Ϊÿһ�����ӿ��ж�������һ��countֵ
			 * �������Ӧ�ڶ��е���ʵ��ֵ���������޷�����ֵ��������
			 */
//			int emptyCount = 0;
//			int notEmptyCount = 0;
			moveRight = 0;
			moveLeft = 0;
			for(int i = 0; i < numList1.size(); i++){
				/*
				 * �������-1�����û���ҵ���Ӧ��ֵ��ֱ�ӱȽ���һ��
				 * ����ҵ��ˣ�����Ҫ�����жϣ��м��ж��ٸ������Ҫ���ձ�ǩ
				 */
				if(-1 != numList1.get(i)){
					/*
					 * �ж��Ƿ���Ҫ�����н����ƶ�����Ҫ��Ϊ��ʵ��������ͬ��
					 */
					int right = numList1.get(i) - i;
					if(0 != right){
						for(int j = 0; j < right; j++){
							stringList.add(1);
							stringList.add(2);
							moveLeft++;
						}
					} else {
						stringList.add(0);
						stringList.add(0);
					}
				/*
				 * ����Ƚϳ������ֲ�����ڣ�����Ҫ����ӦΪλ��ֱ����Ϊ����2
				 */
				} else {
					/*
					 * �����Լ򵥵��ж�һ�ߣ�����Ҫ�ж�����һ��
					 */
					if(i + moveRight < numList2.size()){
						if(numList2.get(i + moveRight) != -1){
							stringList.add(2);
							stringList.add(1);
							moveRight++;
						} else {
							stringList.add(2);
							stringList.add(2);
						}
					} else {
						stringList.add(2);
						stringList.add(2);
						moveLeft++;
					}
					
					/*
					 * �ж϶��ڵڶ��������1�������2
					 * �ж�������
					 * DesktopApplication.getStringNum��Ԥ���������
					 * notEmptyCount���ǿ�����
					 * removeNullNum��ȡ������null����
					 */
//					if( numList1.size() == notEmptyCount ){
//						stringList.add(2);
//						stringList.add(1);
//						moveRight++;
//					} else {
//						stringList.add(2);
//						stringList.add(2);
//						notEmptyCount++;
//					}
				}
			}
//			System.out.println(moveRight + "   " +moveLeft);
//			if(moveRight != moveLeft){
//				System.out.println("not equals!");
//			}
			return stringList;
		}

		
		/*
		 * ����һ������ֱ�������������ǩ������
		 * ���ܵĲ���Ϊ���������������洢�˱Ƚ���Ϣ������
		 * 
		 * �Ƴ���unllֵ��Ŀ��һ���Ƴ�����nullֵ����������һ��
		 * ���Ӧ�ĵ�ֵΪ-1ʱ�����Ա����Ǹ�����1-2��������2-2
		 */
		int removeNullNum = 0;
		public List<Integer> createLabelInfo(List<Integer> numList1, List<Integer> numList2){
//			System.out.println("createLabelInfo function!----start");
//			for(int i = 0; i < numList1.size(); i++){
//				System.out.print("numlist1 = " + numList1.get(i) + " numlist2 = ");
//				System.out.println(numList2.get(i));
//			}
//			System.out.println("createLabelInfo function!----over");
			/*
			 * ������������е���
			 */
			adjustArrayList(numList1,numList2);
			
//			for(int i = 0; i < numList1.size(); i++){
//				System.out.print("numlist1 = " + numList1.get(i) + " numlist2 = ");
//				System.out.println(numList2.get(i));
//			}
//			System.out.println("a arraylist with after adjust");
			/*
			 * ����һ���������ڴ����Ҫ���ص�ֵ
			 * ���������ʾ���壺0����ԭֵ������ȱ�ǩ��1��ʾ�հױ�ǩ��2��ʾ����ȵı�ǩ
			 */
			List<Integer> stringList = new ArrayList<Integer>();
			/*
			 * ������������ѭ��ȡ����ֵ���ж��Ƿ���Ҫ���бȽ�
			 */
			if(numList1.size() != numList2.size()){
				System.out.println("System Account Error!numList'size not equals!");
				System.exit(0);
			}
			/*
			 * �����ж��������������λ�ò����ã�����ǰ������˿հױ�ǩ���������е���ֵ����
			 * ��ֱ�ӵ��¶�Ӧ�е���ֵ���ƣ��޷�ƥ�䣬���ƥ�䣬�м�͵������հ���
			 * 
			 * ����һ����־λ�������ж��Ƿ�������ӿհ���
			 * �������е���˵����һ�α�����ֻ������һ�οհ��У���Ϊÿһ�����ӿ��ж�������һ��countֵ
			 * �������Ӧ�ڶ��е���ʵ��ֵ���������޷�����ֵ��������
			 */
			int emptyCount = 0;
			int notEmptyCount = 0;
			moveRight = 0;
			moveLeft = 0;
			for(int i = 0; i < numList1.size(); i++){
				/*
				 * �������-1�����û���ҵ���Ӧ��ֵ��ֱ�ӱȽ���һ��
				 * ����ҵ��ˣ�����Ҫ�����жϣ��м��ж��ٸ������Ҫ���ձ�ǩ
				 */
				if(-1 != numList1.get(i)){
					/*
					 * �ж��Ƿ���Ҫ�����н����ƶ�����Ҫ��Ϊ��ʵ��������ͬ��
					 */
					int right = numList1.get(i) - i;
					if(0 != right){
						for(int j = 0; j < right; j++){
							stringList.add(1);
							stringList.add(2);
							moveLeft++;
						}
						continue;
					}
					/*
					 * �ж������˶��ٸ������ƥ����ַ������±��ѽ��Ƴ�ָ������������ƥ��
					 */
					if(numList1.get(i) - (numList1.size() - emptyCount) > 0){
						stringList.add(2);
						stringList.add(1);
						moveRight++;
						continue;
					}
					/*
					 * ��ǰ�ж��Ƿ��п�λ������ӱ�Ҫ��Ϣ
					 */
					if( 0 == ShowWithDetail.getStringNum - notEmptyCount - removeNullNum ){
						stringList.add(2);
						stringList.add(1);
						moveRight++;
						continue;
					}
					
					int index = i - numList1.get(i);
					index = Math.abs(index);
					/*
					 * index��ʾ�м����Ƕ��٣������������һ�߶�Ӧ�Ŀձ�ǩ
					 */
					if(0 == index){
						/*
						 * ����ҵ������м���Ϊ0�����ʾ�ǶԵȲ����
						 * ֱ����������0����ʱ��ȡ��ʱ��������˳��ɨ��
						 */
						stringList.add(0);
						stringList.add(0);
						notEmptyCount++;
					/*
					 * ���index������0.˵���м��м��
					 */
					} else {
						/*
						 * �����жϹ�ʽ��
						 * 	���׶���number = value - index - emptyCount
						 * 	number	��ʾ�������ӿ�����
						 * 	value	��ʾ��ֵ
						 * 	index	��ʾvalueֵ���±�
						 * 	emptyCount	��ʾ�����ѽ����ӵĿ���������ֵΪ0
						 */
						int number = numList1.get(i) - i - emptyCount;
						if(0 == number){
							stringList.add(0);
							stringList.add(0);
							notEmptyCount++;
						} else {
							for(int j = 0; j < number; j++){
								emptyCount++;
								stringList.add(1);
								stringList.add(2);
								notEmptyCount++;
								moveLeft++;
							}
							stringList.add(0);
							stringList.add(0);
							notEmptyCount++;
						}	
					}
				/*
				 * ����Ƚϳ������ֲ�����ڣ�����Ҫ����ӦΪλ��ֱ����Ϊ����2
				 */
				} else {
					/*
					 * �����Լ򵥵��ж�һ�ߣ�����Ҫ�ж�����һ��
					 */
//					if(i + moveRight < numList2.size()){
//						if(numList2.get(i + moveRight) != -1){
//							stringList.add(2);
//							stringList.add(1);
//							moveRight++;
//							continue;
//						}
//					}
					
					/*
					 * �ж϶��ڵڶ��������1�������2
					 * �ж�������
					 * DesktopApplication.getStringNum��Ԥ���������
					 * notEmptyCount���ǿ�����
					 * removeNullNum��ȡ������null����
					 */
					if( numList1.size() == notEmptyCount ){
						stringList.add(2);
						stringList.add(1);
						moveRight++;
					} else {
						stringList.add(2);
						stringList.add(2);
						notEmptyCount++;
					}
				}
			}
//			System.out.println(moveRight + "   " +moveLeft);
//			if(moveRight != moveLeft){
//				System.out.println("not equals!");
//			}
			return stringList;
		}
		
		/*
		 * Ϊ�˱��⵱ͬʱ������������������������±�Խ�磬�м�����ȶ�ֵ���ִ�������
		 * �������������ܲ���Ϊ�м����飬����Ϊ�����м����飬�����������ֵ
		 * 
		 * ���Ƚ��յȴ����������飬Ȼ��ɨ�����У�����һ��
		 * ��ɨ�赽��-1ֵ�����������ж�Ӧ��ƥ���ַ���ʱ��������е�index-1����ʼɨ��
		 * ��valueֵ����Ӧ�������±괦��rightIndex = value����
		 * �����;�����˷�-1ֵ��������ֹͣɨ�裬��������valueΪ-1
		 * ��ɨ����ɺ���Ȼû�з���Խ������������κδ�������ɨ��������ֵ
		 */
		public void adjustArrayList(List<Integer> numList1, List<Integer> numList2){
			/*
			 * ������¼�ѽ��������ƥ���±�
			 */
			List<Integer> numList = new ArrayList<Integer>();
			/*
			 * �ӵ�һ��ֵ��ʼ����ɨ��
			 */
			for(int i = 0; i < numList1.size(); i++) {
				/*
				 * �ж��Ƿ���ַ�-1��ֵ
				 */
				if(-1 != numList1.get(i)){
					/*
					 * ���ƥ����ַ�����֮ǰ���ֹ�����ֱ�ӽ�����Ϊ-1��ʾ����ƥ��
					 */
					if(numList.contains(numList1.get(i))){
						numList1.set(i, -1);
						//numList2.set(numList1.get(i), -1);
						continue;
					} else {
						numList.add(numList1.get(i));
					}
					/*
					 * ��������˷�-1��ֵ������Ҫɨ��ڶ��е�ָ��λ��
					 * ��index+1 �� value
					 */
					for(int j = i+1; j < numList1.get(i); j++){
						/*
						 * ��������˷�-1����ɨ�跶Χ�ڣ�����������ѭ�����������б���Ϊ-1
						 */
						if(-1 != numList2.get(j) && i < numList2.get(j)){
							numList1.set(i, -1);
							//numList2.set(numList1.get(i), -1);
							break;
						}
					}
				}
			}
		}
		
		public List<Integer> compareString(String[][] string){
//			System.out.println("compareString function!----start\n���ܵ��ַ�������");
//			System.out.println(string.length);
//			for (int i = 0; i < string.length; i++){
//				for(int j = 0; j < string[i].length; j++){
//					System.out.println(string[i][j]);
//				}
//			}
//			System.out.println("compareString function!----end");
			/*
			 * ����һ����ʶ�����ڲ����������ʶ���ϵ�����ʮ�ı�����
			 * ֮��ת�����ַ������������У����ֵ�������֮ǰ��˵��һ��
			 */
			//String numStr = "";
			List<Integer> numList1 = new ArrayList<Integer>();
			List<Integer> numList2 = new ArrayList<Integer>();
			/*
			 * ��������ĳ�ʼ����Ĭ�ϵ�ֵ��Ϊ-1����δ�ҵ�
			 * ��ʼ������Ϊ֮ǰ�趨������ֵ��Ĭ��ֵΪ3
			 */
//			for(int i = 0; i < proList1.size(); i++) {
//				numList1.add(-1);
//				numList2.add(-1);
//			}
			for(int i = 0; i < string.length; i++){
				numList1.add(-1);
				numList2.add(-1);
			}
			/*
			 * ����ȡ����ά�ַ������������
			 */
			int row = string.length;
//			int row = proList1.size();
			/*
			 * ����ѭ���������ж��ַ����Ƿ����
			 * �жϵĶ���Ϊͬ��ε��ַ������²��ַ���
			 * 
			 * ��ʱʵ�����������м����飬����֮����ݴ������������Ĵ�������
			 */
			for(int i = 0; i < row; i++){
				/*
				 * ����������������ɨ��ʽ�ıȽ�
				 */
				for(int j = i; j < row; j++){
					/*
					 * ���������unllֵ��˵��֮���ѽ�û�д��е�Դ����
					 */
					if(null == string[i][0]){
						break;
					}
					/*
					 * �ж϶ԵȻ������²㼶���Ƿ����
					 * �����ȣ�����Ҫbreak���˴αȽ�
					 */
					if(string[i][0].equals(string[j][1])){
						/*
						 * ͬʱ�����е��±����ҵ���Ӧ���е��±����������
						 */
						numList1.set(i, j);
						/*
						 * break��Ϊ�˲���Ӱ�������ͬ�������´ε��жϲ���Ӱ��
						 */
						break;
					}
				}
				/*
				 * �������һ�ֵ�ѭ������û��,�����������Ϊ֮ǰ�Ѿ���������г�ʼ����Ĭ�Ͼ���û�ҵ�
				 */
			}
			/*
			 * ��������һ�ֵĲ��ң��ѽ�����һ��Ѱ�����
			 * ���ڿ�ʼ�ӵڶ��п�ʼѰ�ң���ʱ��Ѱ�ҹ���Ϊ��
			 * 	�Ƚ�ͬ�����ַ�����ͬ�����ϣ����ϱȽϣ���Ȼ���ٽ��±��¼����
			 */
			for(int i = 0; i < row; i++){
				/*
				 * ����ֻ��ͬ����ͬ�����ϵıȽϣ�������ֵ�಻�ɳ�����ʱ�Ƚϵĵȼ�
				 */
				for(int j = i; j >= 0; j--){
					/*
					 * ���������unllֵ��˵��֮���ѽ�û�д��е�Դ����
					 */
					if(null == string[i][0]){
						//numList2.set(i, -2);
						break;
					}
					/*
					 * �ж϶ԵȻ������ϲ㼶������ȣ�����Ҫ����Ӧ�±��Ӧ��ֵ��λ
					 * ͬʱbreak���˴αȽ�
					 */
					if(string[i][1].equals(string[j][0])){
						/*
						 * ����ҵ���ȵ�ֵ����Ҫ����Ӧλ�õ�ֵ����Ϊ��Ӧ���±�ֵ
						 */
						numList2.set(i, j);
						break;
					}
				}
			}
			/*
			 * �������������αȽϣ��ѽ����������������飬���б�ʶ���ҵ���Ϣ
			 */
			return createLabelInfo(numList1, numList2);
		}
		
		/*
		 * �˷��������жϴ����������������Map�е�ֵ����ȡ����
		 * 
		 * ����˼·���ϣ�
		 * ���ص�ֵΪ���飬���ڱ�ʾ��Щ������ȵ�
		 * ���ݱ��߶�ultraCompare��ʹ�ã�����������Ƕ����ߵ��ı�����ͬʱ���бȽ�
		 * ���������ұߵĴ�����ͬ��ֵ�Ż�������бȽϣ����������ȡ��ȥ��ֱ����ȡ���
		 * ������һ�����Ǳ����ĵȴ���������ͬ�ıȽϽ������ʱ�Ż�ͬ���ƽ�
		 * 
		 * ��˼·��
		 * ����򻯣�������������ֵ��0,1
		 * 0������ȣ�1�������
		 */
		/*
		 * ����һ��ArrayList�����ڴ�űȽϺ���ȵ�ֵ���±�
		 */
		//ArrayList<Integer> sameList = new ArrayList<Integer>();
		public int compareEqual(String str1, String str2){
			
			/*
			 * ����ȥ�����յ��ַ����Ŀո���Ϊ����Ƶ�ʱ��Ϊ����ʾЧ�����ӿո�
			 */
			str1 = str1.replace(" ", "");
			str2 = str2.replace(" ", "");
			/*
			 * ȥ���ո����ַ��������ϸ�ıȶ�
			 * �˴��ȶԵķ�����Ϊequals����
			 */
			if(str1.equals(str2)){
//				System.out.println(str1 + " = " +str2);
				return 0;
			} else {
				return 1;
			}
		}
	}
