package _Util;

import java.io.Serializable;
import java.util.ArrayList;

 
public class CommandTranser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sender = null;// ������
	private String receiver = null;// ������
	private Object data = null; // ���ݵ�����
	private boolean flag = false; // ָ��Ĵ�����
	private String cmd = null; // �����Ҫ����ָ��
	private String result = null; //������
	ArrayList<String> friends = null;
	ArrayList<String> all_online = null;
	
	public String getSender() {
		return sender;
	}

	public String setSender(String sender) {
		return this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String setReceiver(String receiver) {
		return this.receiver = receiver;
	}

	public Object getData() {
		return data;
	}

	public Object setData(Object data) {
		return this.data = data;
	}

	public boolean isFlag() {
		return flag;
	}

	public boolean setFlag(boolean flag) {
		return this.flag = flag;
	}

	public String getResult() {
		return result;
	}

	public String setResult(String result) {
		return this.result = result;
	}

	public String getCmd() {
		return cmd;
	}

	public String setCmd(String cmd) {
		return this.cmd = cmd;
	}

	public ArrayList<String> setFriends(ArrayList<String> friends){
		return this.friends = friends;
	}
	
	public ArrayList<String> getFriends(){
		return friends;
	}
	
	public ArrayList<String> setAllOnline(ArrayList<String> all_online){
		return this.all_online = all_online;
	}
	
	public ArrayList<String> getAllOnline(){
		return all_online;
	}
	
}
