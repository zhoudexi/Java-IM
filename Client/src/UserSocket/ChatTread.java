package UserSocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import Entity.ChatUIEntity;
import Entity.User;
import Frame.ChatUI;
import Frame.FriendsUI;
import _Util.ChatUIList;
import _Util.CommandTranser;
import java.util.*;
import java.net.*;
 
public class ChatTread extends Thread{
	private Client client;
	private boolean isOnline = true; //û�� ------��ɾ
	private User user; //���ͬ��������� ��ˢ�º����б�
	private FriendsUI friendsUI; //ˢ�º����б���
	private String username; //��������µ����촰�ڣ�chatUI)��ô���뽫username����ȥ ����������Ϣ
	private StyledDocument doc; 
	InetAddress ip;// = InetAddress.getLocalHost();
	public ChatTread(Client client, User user, FriendsUI friendsUI) throws UnknownHostException {
		this.client = client;
		this.user = user;
		this.friendsUI = friendsUI;
		this.username = user.getUsername();
		ip = InetAddress.getLocalHost();
		//this.chat_windows = chat_windows;
	}
	
	public boolean isOnline() {
		return isOnline;
	}
	//����û�� �Ժ�ɾ---------------------------------------
	public void setOnline(boolean isOnline) {
		 this.isOnline = true; 
	}
	
	//run()�����ǲ���Ҫ�û������õģ���ͨ��start��������һ���߳�֮�󣬵��̻߳����CPUִ��ʱ�䣬
	//�����run������ȥִ�о��������ע�⣬�̳�Thread�������дrun��������run�����ж������Ҫִ�е�����
	@Override
	public void run() {
		if(!isOnline) {
			JOptionPane.showMessageDialog(null,  "unbelievable ������");
			return;
		}
		while(isOnline) {
			//System.out.println("����");
			CommandTranser cmd = client.getData();
			//�����������ͬ������յ�����Ϣ(����)
			//���ﴦ�����Է���������Ϣ(����)
			if(cmd != null) {
				
				 try {
					execute(cmd);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 //System.out.println(cmd.getCmd());	
			}
		}
	}
	
	//������Ϣ(����)
	@SuppressWarnings("null")
	private void execute(CommandTranser cmd) throws IOException {
		//��¼���������롢ע����Ϣδ�ڴ˴�����
		//System.out.println("execute");
		System.out.println(cmd.getReceiver()+"�յ�������Ϊ��"+cmd.getCmd());
		System.out.println("��������Ϊ��"+cmd.getData().getClass().toString());
		//������Ϣ���� 
		if("message".equals(cmd.getCmd())) {
			if(cmd.isFlag() == false) {
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			
			//��ѯ�Ƿ�����ú��ѵĴ��ڸô���
			String friendname = cmd.getSender();
			ChatUI chatUI = ChatUIList.getChatUI(friendname);
			if(chatUI == null) {
				chatUI = new ChatUI(username, friendname, username, client, user);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName(friendname);
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yy-MM-dd hh:mm:ss a");
			JTextPane chat_windows = chatUI.getChatWin();
			doc = chat_windows.getStyledDocument();
			String s = "123";
			ImageIcon i = null;
			System.out.println("s����������Ϊ��"+s.getClass().toString());
			
			if(cmd.getData().getClass().toString().equals(s.getClass().toString())){//����
				String message = friendname + "˵��"
						+ (String) cmd.getData() + "\t" + sdf.format(date)
						+ "\n";
				try {
					doc.insertString(doc.getLength(), message, null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				chat_windows.setCaretPosition(doc.getLength());//�����ӵ������λ�� 
			}else  {
				try {
					doc.insertString(doc.getLength(), friendname + "˵��", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ImageIcon I = (ImageIcon) cmd.getData();
				chat_windows.setCaretPosition(doc.getLength());//�����ӵ������λ��  
			    chat_windows.insertIcon(I);
			    doc = chat_windows.getStyledDocument();
				try {
					doc.insertString(doc.getLength(), sdf.format(date) + "\n", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			return;
		}
		
		if("not_online".equals(cmd.getCmd())){
			JOptionPane.showMessageDialog(null,  "�Է������ߣ����Ժ��ٷ���");
		}
		
		if("all_online".equals(cmd.getCmd())){
			System.out.println("�ͻ����յ�����������ԱΪ"+cmd.getAllOnline());
			ArrayList<String> all_online = cmd.getAllOnline();
			user.setAllOnline(all_online);
			
			//���������û�
			friendsUI.dispose();//�ر�ԭ���Ĵ���
			System.out.println("���´���");
			FriendsUI friendsUI = new FriendsUI(user, client);
			//ʹ�� validate ������ʹ�����ٴβ�������������Ѿ��������������޸Ĵ��������������ʱ��
			//������������ӻ��Ƴ���������߸����벼����ص���Ϣ����Ӧ�õ�������������
			friendsUI.validate();
			friendsUI.repaint();
			friendsUI.setVisible(true);
			this.friendsUI = friendsUI;
			return;
		}
		
		if("WorldChat".equals(cmd.getCmd())) {
//			if(cmd.isFlag() == false) {
//				JOptionPane.showMessageDialog(null, cmd.getResult()); 
//				return;
//			}
			//��ѯ�Ƿ�����ú��ѵĴ��ڸô���
			String friendname = cmd.getSender();
			String sender = cmd.getSender();
			String receiver = cmd.getReceiver();
			ChatUI chatUI = ChatUIList.getChatUI(sender);
			
			if(chatUI == null) {
				chatUI = new ChatUI(receiver, sender, user.getUsername(), client, user);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName(sender);
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yy-MM-dd hh:mm:ss a");
			String message = friendname + "˵��"
					+ (String) cmd.getData() + "\t" + sdf.format(date)
					+ "\n";
			JTextPane chat_windows = chatUI.getChatWin();
			doc = chat_windows.getStyledDocument();
			try {
				doc.insertString(doc.getLength(), message, null);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			chat_windows.setCaretPosition(doc.getLength());//�����ӵ������λ�� 
			return;
		}
		
		if("requeste_add_friend".equals(cmd.getCmd())) {
			if(cmd.isFlag() == false) {
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			String sendername = cmd.getSender();
			int flag = JOptionPane.showConfirmDialog(null, "�Ƿ�ͬ��" + sendername + "�ĺ�������", "��������", JOptionPane.YES_NO_OPTION);
			System.out.println(flag);
			if(flag == 0) {
				cmd.setCmd("accept_add_friend");
			} else {
				cmd.setCmd("refuse_add_friend");			
			}
			cmd.setSender(username);
			cmd.setReceiver(sendername);
			client.sendData(cmd);
			return;
		}
		
//		if("successful".equals(cmd.getCmd())) {
//			JOptionPane.showMessageDialog(null, cmd.getResult()); 
//			return;
//		}
		
		if("accept_add_friend".equals(cmd.getCmd())) {
			//JOptionPane.showMessageDialog(null, cmd.getResult());
			System.out.println(cmd.getResult()+" ���յ��������");
			CommandTranser newcmd = new CommandTranser();
			System.out.println("���ܺ��ѣ�"+username);
			newcmd.setCmd("updatefriendlist");
			newcmd.setReceiver(username);
			newcmd.setSender(username);
			newcmd.setData(user);
			client.sendData(newcmd);
			return;
		}
		
		if("updatefriendlist".equals(cmd.getCmd())) {
			System.out.println(cmd.getCmd()+"�ж�");
			if(cmd.isFlag() == false) {
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			User tmp = (User)cmd.getData();
			System.out.println(tmp.getUserpwd()+" ���յ���������");
			System.out.println(tmp.getFriend());
			user.setFriendsList(tmp.getFriend());
			//���º����б�
			friendsUI.dispose();//�ر�ԭ���Ĵ���
			FriendsUI friendsUI = new FriendsUI(user, client);
			//ʹ�� validate ������ʹ�����ٴβ�������������Ѿ��������������޸Ĵ��������������ʱ��
			//������������ӻ��Ƴ���������߸����벼����ص���Ϣ����Ӧ�õ�������������
			friendsUI.validate();
			friendsUI.repaint();
			friendsUI.setVisible(true);
			this.friendsUI = friendsUI;
			return;
		}
		
		if("refuse_to_add".equals(cmd.getCmd())) {
			JOptionPane.showMessageDialog(null, cmd.getResult());
			return;
		}
		
		if("changepwd".equals(cmd.getCmd())) {
			JOptionPane.showMessageDialog(null, cmd.getResult());
			return;
		}
		return;
	}
	
}

