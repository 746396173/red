package com.android_red.intenter;

import java.net.Socket;

public class Const {
	public static String WEB_PATH ="http://test.tx15.eccentertool.com:39011/cms/";
	//http://ds.tx15.dianshangshikong.com:39003/Testimg/test?name=1
	//public static String WEB_IP ="192.168.191.1";
	public static boolean switch1 = false;   //�����������ÿ���
	public static boolean switch2 = false;   //�����ʱ���������
	public static boolean switch3 = false;   //�����������ÿ���
	public static boolean switch4 = false;   //���׹������ÿ���
	public static boolean switch5 = false;   //��ֵ���ÿ���
	public static String time = "";
	public static int  rbyouxian= 2;         //��������-Ĭ�����ȳ�������
	public static int  rb= 3;                //��ֵ����-Ԫ�Ƿ�����
	public static int  ws= 0;                //��ֵβ������
	public static int  jdt_min= 20;               //������-��ʱ�����
	public static int  jdt_max= 150;               //������-��ʱ�����
	public static int  wifi= 1;               //�������          1��ͬʱ����·����    2��һ̨���ȵ�
	public static int  cs= 1;               //��ǰ�ǲ���IP  1���������     2���յ���������ظ�    3���������������
	public static int  ser= 1;               //��ǰserver
	public static String  ip= "";               //��ǰip
	public static int  qiang= 1;               //�������ʽ    1������  2��ɨ��
	public static boolean  miaoqiang= false;               //�����
	public static String  mess= "";               //��Ϣ
	public static String  socketip= "";               //�ж�socket
	public static Socket socket=null;
	public static int port=12348;     //�˿�
}
