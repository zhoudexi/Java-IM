package _Util;

import java.util.HashMap;

import Entity.ChatUIEntity;
import Frame.ChatUI;

 
public class ChatUIList {
	private static HashMap<String, ChatUI> map = new HashMap<String, ChatUI>();
	
	//��map���桰ע�ᡱ
	public static void addChatUI(ChatUIEntity chatUIEntity) {
		map.put(chatUIEntity.getName(), chatUIEntity.getChatUI());	
	}
	
	//�رմ��ں�Ҫɾ��
	public static void deletChatUI(String chatUIName) {
		
		//ɾ��֮ǰ�鿴�Ƿ����������, ��ֹ����
		if(map.get(chatUIName) != null) {
			map.remove(chatUIName);
		}
		
	}
	
	//ͨ���ǳƷ��ش���
	public static ChatUI getChatUI(String name) {
		return map.get(name);
	}
}

