package Detection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * ��ΪԴ�������࣬���ڼ��ָ���ļ����µ��ļ������ƶ�
 * ���ԣ�
 * 	���Դ������Ϣ������
 * ������
 * 	��Դ���������Դ����Ƚ϶���
 * 	���ƶȱȽϷ�������ȡԴ���������е�ֵ��Ȼ���䴫�������ļ��㷽���У�
 * 	ע��	������ʦ����������жϺ����޸�Ϊ�����������ƶ�
 * 		����cosine���ƶȵ�Ŀ���ǽ��cosine���ƶȽ���������ά�ȷ����ϵ����ƣ�
 * 		��û���ǵ�����ά�ȵ����ٵĲ����ԣ������ڼ������ƶȵ�ʱ��
 * 		����ÿ��ά�ȼ�ȥ��ֵ������������
 * 	�˴���Pearson���ϵ�����ɡ�
 * 	
 * 	�������������ƽ��ֵ
 * 	�������Һ��������ƶ�
 * 
 */
public class PlagiarismDetection {
	HashMap<String,Double>Simi=new HashMap<>();

	//ԭ������Ϣ��ŵ�����
	List<SourcePrograme> spList  = new ArrayList<SourcePrograme>();
	//������ĳ�����ֵ
	public double THRESHOLD = 0.95;
	
	public void setTHRESHOLD(double tHRESHOLD) {
		THRESHOLD = tHRESHOLD;
	}
//mmdgajk
	/*
	 * �Ƚ�����Դ��������Ƴ̶�
	 */
	public boolean launchSimiMeas() {
		double res = 0.0;
		info.clear();
		HashMap<String,Double>Similarity=new HashMap<>();
		//���������м������г�������Դ��������һ�ζ�ȡ�������бȶ�
		if(this.spList.size() >= 2){
				//����ǰȡ��һ��Ԫ�أ������Լ����Լ��Ƚ�
				List<Integer> l1 = this.spList.get(0).keyWordCountList;
				//ѭ��ȡ�������е�ֵ���бȽ�

			for(int i = 1; i < this.spList.size(); i++) {
				List<Integer> l2 = this.spList.get(i).keyWordCountList;
				String name=this.spList.get(i).getNameOfStudent();
				//���ü�����
				Double result = new Double(this.AdjustedCosineSimilarity(l1, l2));
				res = 0.0;
				/*
				 * ���õ��Ľ�����зָȥǰ����λ���֣����ڹ۲�
				 */
				if(result.toString().length() >= 5){
					res = Double.parseDouble(result.toString().substring(0, 5));	
				} else {
					res = result;
				}
				
				if(res >= -this.THRESHOLD){
//	                System.out.println("stname = " + this.spList.get(0).getNameOfStudent());
//	                System.out.println("stname = " + this.spList.get(i).getNameOfStudent());
//	                System.out.println(" res   = " + res);
}                   Similarity.put(name,res);
				    this.Simi=Similarity;
				//���ϴ����ļ�������ָ����ֵʱ������������false
//				if(res >= this.THRESHOLD){
//					return false;
//				}
			}			
			//��ѭ�������Ƚ�֮��û�з��أ�false����˵���Ƚ����֮����ϼ����򣬴���������ֵ֮��
			return true;
		} else {
		//˵����������û����Ҫ����Դ���򣬿���ֱ�ӷ���0.0������Ƚ�
			return true;
		}
		
	}
	
	/*
	 * ��������������ָ�����ļ����µ������ļ����������Ƚϣ���������뵽ָ����������
	 */
	public void launchSimiMeasWithAll(){
		//List<Integer> l1 = null;
		//List<Integer> l2 = null;
		double res;
		/*
		 * ����ѭ�������������Ƚϣ��ڵ�һ��ѭ��������1��Ϊ�˱�����ȡ�Լ�һ��
		 */
		for(int i = 0; i < this.spList.size() - 1; i++){
			System.out.println(this.spList.get(i).getNameOfStudent()
					+ "  compare with ");
			//l1 = this.spList.get(i).keyWordCountList;
			for(int j = i + 1; j < this.spList.size(); j++){
				System.out.print(this.spList.get(j).getNameOfStudent());
				//l2 = this.spList.get(j).keyWordCountList;
				Double result = new Double(this.AdjustedCosineSimilarity(
						this.spList.get(i).keyWordCountList, 
						this.spList.get(j).keyWordCountList));
				res = 0.0;
				/*
				 * ���õ��Ľ�����зָȥǰ����λ���֣����ڹ۲�
				 */
				if(result.toString().length() >= 5){
					res = Double.parseDouble(result.toString().substring(0, 5));	
				} else {
					res = result;
				}
				System.out.println(" = " + res);
				/*
				 * �Ƚ���Ϻ����ݴ�����
				 */
				this.setInfo(
						this.spList.get(i).getNameOfStudent(), 
						this.spList.get(j).getNameOfStudent(), 
						res);
			}

		}
		/*
		 * �������ܣ�����UI�����ͼ�λ���ʾ�����û���������ȽϵĽ������ָ���ļ���
		 */
//		this.setInfo(this.spList.get(0).getNameOfStudent(),
//				this.spList.get(i).getNameOfStudent(), res);
		
	}
	/* 
	 * ����û�л������������ã���ʱͨ��һ��������ֵ�����ȥ
	 */
	public List<String> info = new ArrayList<String>();
	public void setInfo(String name1, String name2, Double result){
		info.add(name1);
		info.add(name2);
		info.add(result.toString());
	}
	
	/*
	 * ���Һ�����
	 * 	��������a,b
	 * 	cossim = ab/|a||b|
	 * �������Һ�����
	 * 	��������a,b,mean
	 * 	adjcossim = (a-mean)(b-mean)/|a-mean||b-mean|
	 * �Ƚ�����Դ��������Ƴ̶�(�ؼ��ֵĳ��ִ���)�����رȽϽ��
	 * 
	 */
	public double AdjustedCosineSimilarity(List<Integer> list1,List<Integer> list2){
		//�Ƚ����ܵ�����������������
		List<Double> lista = this.adjustCount(list1);
		List<Double> listb = this.adjustCount(list2);
		/*
		 * ����ӣ�����a * ����b
		 * ��ĸ������a��ģ * ����b��ģ
		 */
		double aa = 0.0;
		double bb = 0.0;
		double ab = 0.0;
		for(int i = 0; i < lista.size(); i++){
			aa += lista.get(i) * lista.get(i);
			bb += listb.get(i) * listb.get(i);
			ab += lista.get(i) * listb.get(i);
		}
		return ab / (Math.sqrt(aa) * Math.sqrt(bb));
	}

	
	/*
	 * ͳ�ƽ����������
	 * �����������Һ�����Ҫ����Ҫ��ͳ�ƽ��������������
	 * ѧ���Ĵ���ؼ��ֳ��ִ�����ȥ��ѧ�������г������йؼ��ֵľ�ֵ�õ���������
	 * ���ݽ��ܵĲ������α������е�ֵ��Ȼ���ȥ��ֵ
	 */
	private List<Double> adjustCount(List<Integer> list){
		List<Double> l = new ArrayList<Double>();
		//��ø�ѧ�����йؼ���tͳ�Ƶ�������ֵ
		double mean = this.adjustMean(list);
		//���α������е�ֵ�������������ȥ�ؼ���������ִ�������ת��Ϊ����ͳ�ƴ���
		for(int i = 0; i < list.size(); i++) {
			//���ѭ���������з������������
			if(0 != list.get(i)) {
				l.add(list.get(i) - mean);
			} else {
				l.add(0.0);
			}
		}
		return l;
	}

	/*
	 * ͳ�ƽ����������֮ͳ���ܴ�������ͳ�ƹ��Ĺؼ��ִ���
	 * ���ݽ��ܵĲ�����ѭ��������ͳ�Ʒ�����
	 */
	private double adjustMean(List<Integer> list) {
		//�ؼ��ֵĳ����ܴ���
		int allNum = 0;
		//�ؼ���������ֵĴ���
		int num = 0;
		for(int i = 0; i < list.size(); i++){
			//�������еķ����ͳ�ƽ����1
			if(0 != list.get(i)) {
				allNum += list.get(i);
				num++;
			}
		}
		return allNum * 1.0 / num;
	}
	/*
	 * Դ������Ϣ�������
	 */
	public void addIn_spList(SourcePrograme sp) {
		this.spList.add(sp);
	}

	/*
	 * ��ʾ��ӽ����Դ�����ͳ�ƽ��
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.spList.size(); i++){
			System.out.println("ѧ�����ļ����ƣ�"+this.spList.get(i).getNameOfStudent());
			System.out.println(this.spList.get(i));
		}
		return null;
	}
}
