package com.android_red.intenter;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class Test {
	int sum = 0;
	 static List<String> list = new ArrayList<String>();
	 
    //�������list<String>,�ܷ�������ֵ���ܽ��
	public boolean AA(List<String> list,String fs,String lz,String summoney){
		list.add("12.23");
		list.add("12.23");
		list.add("12.23");
		list.add("12.23");
		list.add("12.23");
		if(Integer.parseInt(fs)-list.size()==1){   //ֻ�����һ����
			for(int i = 0;i<list.size();i++){
				double s = (Double.parseDouble(list.get(i))*100);
				sum = sum+ (int)(s);    //��ȡ��ǰ���е�ֵ
			}
			list.clear();
			System.out.println(sum);
			int money = (int)(Double.parseDouble(summoney)*100)-sum;	
			System.out.println(money);
			int yuan=(int)money/100;
			int jiao=(int)(money%100)/10;
			int fen=(int)(money%10);
			System.out.println(money+"Բ="+yuan+"Բ"+jiao+"��"+fen+"��");
			//��β�����õ���ʲô��
			if(Const.rbyouxian==1){//���ȸ�������
				System.out.println("���ȸ�������");
				if(Const.rb==1){//Ԫ
					System.out.println("���ڱȽϵ���ֵ��Ԫ����"+yuan);
					if(Const.ws == yuan){
						return false;
					}
				}else if(Const.rb==2){//��
					System.out.println("���ڱȽϵ���ֵ���ǣ���"+jiao);
					if(Const.ws == jiao){
						return false;
					}
				}else{//��
					System.out.println("���ڱȽϵ���ֵ���֣���"+fen);
					if(Const.ws == fen){
						return false;
					}
				}
			}else{//���ȳ������   -- ���ݳ����Զ�������ֵ
				System.out.println("���ȳ������");
				System.out.println("���ڱȽϵ���ֵ��"+Integer.parseInt(lz));
				if(Integer.parseInt(lz) == fen){
					return false;
				}
			}

		}else{
			return false;   //��ʱ�������һ��
		}
		list.clear();
		return true;
	}

	//���������String��,��ֵ(int),��ǰ�Ѿ����ķ�����String��,�ܷ�����String��
		public int AA1(String yu,int lz,String outfs,String numfs){
			int outfsint = Integer.parseInt(outfs);
			int numfsint = Integer.parseInt(numfs);
			if(numfsint - outfsint == 1){//���һ��
				int money = (int)(Double.parseDouble(yu)*100);	 //���һ�ݵĽ��
				System.out.println(money);
				int yuan=(int)money/100;
				int jiao=(int)(money%100)/10;
				int fen=(int)(money%10);
				System.out.println(money+"��Բ="+yuan+"Բ"+jiao+"��"+fen+"��");
				//��β�����õ���ʲô��
				if(Const.rbyouxian==1){//���ȸ�������
					System.out.println("���ȸ�������");
					if(Const.rb==1){//Ԫ
						System.out.println("�������õ�β����Ԫ����"+Const.ws);
						System.out.println("���ڱȽϵ���ֵ��Ԫ����"+yuan);
						if(Const.ws == yuan){
							return 2;
						}
					}else if(Const.rb==2){//��
						System.out.println("�������õ�β�����ǣ���"+Const.ws);
						System.out.println("���ڱȽϵ���ֵ���ǣ���"+jiao);
						if(Const.ws == jiao){
							return 2;
						}
					}else{//��
						System.out.println("�������õ�β�����֣���"+Const.ws);
						System.out.println("���ڱȽϵ���ֵ���֣���"+fen);
						if(Const.ws == fen){
							return 2;
						}
					}
				}else{//���ȳ������   -- ���ݳ����Զ�������ֵ
					System.out.println("���ȳ������");
					System.out.println("���������β�����֣���"+lz);
					System.out.println("���ڱȽϵ���ֵ��"+fen);
					if(lz==10||lz == fen){
						return 2;
					}
				}
			}else{
				return 1;   //��ʱ�������һ��
			}
			return 0;
		}
		
	public void BB(){	
		if(Const.switch5==false ||AA(list, "6", "4", "121.2")  ){  //Const.switch5==false δ�������ף�ֱ����
//		 try {
//			 if(Const.switch2==true){
//			     System.out.println("��ʱ2��...");
//				 Thread.currentThread().sleep(2000);//���2�� 
//			 }
//		        
//		 } catch (InterruptedException e) {
//		         e.printStackTrace();
//		 }
			//�����
			System.out.println("��ʼ�����");
		}else{
			System.out.println("��ʱ����");
			//��ʱ����
		}
	}

	
	public void BB1(){	
		if(Const.switch5==false ||AA1("60.05", 4,"19","20")==0 ){  //Const.switch5==false δ�������ף�ֱ����
//		 try {
//			 if(Const.switch2==true){
//					System.out.println("��ʱ2��...");
//				 Thread.currentThread().sleep(2000);//���2�� 
//			 }
//		        
//		 } catch (InterruptedException e) {
//		         e.printStackTrace();
//		 }
			//�����
			System.out.println("��ʼ�����");
		}else{
			System.out.println("��ʱ����");
			//��ʱ����
		}
	}


	


}
