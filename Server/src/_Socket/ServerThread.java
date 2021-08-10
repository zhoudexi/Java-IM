package _Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import Entity.SocketEntity;
import Entity.User;
import _Service.UserService;
import _Util.CommandTranser;
import _Util.SocketList;
import java.util.Set;
/**
 * ����Ϊ���߳��࣬���ڷ���˴����ͻ�������
 */
public class ServerThread extends Thread{
	private Socket socket;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		ObjectInputStream ois = null;
		ObjectOutputStream oos1 = null;//�����ͷ���ͬ��Ӻ��ѳ��⣩
		ObjectOutputStream oos2 = null;
		//ObjectOutputStream oos3 = null;
		
		while(socket != null) {
			try {
				//�����յ���ת��Ϊ����
				//oos1 = null;
				oos2 = null;
				ois = new ObjectInputStream(socket.getInputStream());
				CommandTranser cmd =  (CommandTranser) ois.readObject();
				//System.out.println("�������յ����������ͣ�"+cmd.getData().getClass());
				//ִ���������Կͻ��˵�����
				cmd = execute(cmd);//�����÷�������cmd
				System.out.println("�������յ��Ŀͻ�������: " + cmd.getCmd());
				System.out.println("�յ����ݣ�"+cmd.getData());
				//��Ϣ�Ի����󣬷�������sender��������Ϣ���͸�receiver
				if("message".equals(cmd.getCmd())) {
					//��� msg.ifFlag�� ����������ɹ� ���������ѷ�����Ϣ ���������������Ϣʧ�� ��Ϣ���͸������߱���
					if(cmd.isFlag()) {//�жϽ������Ƿ�����
						System.out.println("�Է�" + cmd.getReceiver() + "����");
						//cmd.getReceiver()��ȡ�����ߵ��ǳƣ�Ȼ��getSocket��ȡ��Ӧ��socket��Ȼ��getOutputStream()�������ͻ��˵�����
						oos1 = new ObjectOutputStream(SocketList.getSocket(cmd.getReceiver()).getOutputStream());
					} else {
						//JOptionPane.showMessageDialog(null,  "�Է������ߣ����Ժ��ٷ���");
						System.out.println("�Է�δ����");
						//�˴���socketΪ�����ߵ�socket
						cmd.setCmd("not_online");
						String sender = cmd.getSender();
						cmd.setReceiver(sender);
						oos1 = new ObjectOutputStream(SocketList.getSocket(cmd.getSender()).getOutputStream());

						//oos2 = new ObjectOutputStream(SocketList.getSocket(cmd.getSender()).getOutputStream());
					}
				}
				
				if ("WorldChat".equals(cmd.getCmd())) {
					HashMap<String, Socket> map = SocketList.getMap();//��ȡ���з������ϵ����ߵ�socket
					System.out.println(cmd.getFriends());
					ArrayList<String> friends = cmd.getFriends();
					//ͨ��Map.entrySetʹ��iterator����key��value
					Iterator<Map.Entry<String, Socket>> it = map.entrySet().iterator();
					while(it.hasNext()) {//key�ǿͻ��˵����ƣ�valueΪ��Ӧ��socket
						Map.Entry<String, Socket> entry = it.next();
						if(friends.contains(entry.getKey())) {
							cmd.setReceiver(entry.getKey());
							oos1 = new ObjectOutputStream(entry.getValue().getOutputStream());
							oos1.writeObject(cmd);
						}
						/*
						if(!entry.getKey().equals(cmd.getSender())) {
							oos1 = new ObjectOutputStream(entry.getValue().getOutputStream());
							oos1.writeObject(cmd);
						}*/
					}
					continue;
				}
				if("all_online".equals(cmd.getCmd())){
					System.out.println("����������Ա"+cmd.getAllOnline());
					ArrayList<String> all_online = cmd.getAllOnline();
					/*
					ArrayList<String> net_online = new ArrayList();
					Iterator it = all_online.iterator();
					String owner = SocketList.getNet(cmd.getSender());
					//�������û���ɸѡ�����������û�
					while(it.hasNext()){
						String name = (String)it.next();
						String net = SocketList.getNet(name);
						System.out.println(name+" "+net);
						if(net.equals(owner)){
							net_online.add(name);
						}
					}
					cmd.setAllOnline(net_online);*/
					System.out.println("���о�����������Ա"+cmd.getAllOnline());
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				//��¼���� �����ݷ��͸�sender
				if ("login".equals(cmd.getCmd())) {
					System.out.println("��¼");
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				//ע������ �����ݷ��͸�sender
				if ("register".equals(cmd.getCmd())) {
					System.out.println("ע��");
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				
				//��Ӻ����������ݷ��͸� receiver
				if ("requeste_add_friend".equals(cmd.getCmd())) {
					//���ߣ������󷢸�receiver
					System.out.println(cmd.getSender() + " ����� " + cmd.getReceiver() + " �ĺ���");
					if(cmd.isFlag()) {
						oos1 = new ObjectOutputStream(SocketList.getSocket(cmd.getReceiver()).getOutputStream());
//						oos3 = new ObjectOutputStream(socket.getOutputStream());
//						CommandTranser newcmd =  new CommandTranser();
//						newcmd = cmd;
//						newcmd.setCmd("successful");
//						oos3.writeObject(newcmd);
					} else {
						//�����ڲ����߶�Ҫ���ͷ���ʾ��Ϣ���ͳɹ�
						oos2 = new ObjectOutputStream(socket.getOutputStream());
					}
				}
				
				//ͬ����Ӻ����������ݷ��͸� receiver��sender
				if ("accept_add_friend".equals(cmd.getCmd())) {
					//�����Ƿ�ɹ��������ݿⶼҪ��������������п����������Ŀͻ�������
					System.out.println(cmd.getReceiver()+ " ͬ��� "+ cmd.getSender());
					oos1 = new ObjectOutputStream(socket.getOutputStream());
					if(SocketList.getSocket(cmd.getReceiver()) != null) {
						oos2 = new ObjectOutputStream(SocketList.getSocket(cmd.getReceiver()).getOutputStream());
					}
				}
				
				if("updatefriendlist".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				//�ܾ���Ӻ����������ݷ��͸� receiver
				if ("refuse_to_add".equals(cmd.getCmd())) {
					//���ܾ�������
					System.out.println(cmd.getReceiver()+ " �ܾ��� "+ cmd.getSender());
					if(cmd.isFlag()) {
						oos1 = new ObjectOutputStream(SocketList.getSocket(cmd.getReceiver()).getOutputStream());
					}else { //���ܷ�����������ܾ���������Ϣ
						oos2 = new ObjectOutputStream(socket.getOutputStream());
					}
				}
				
				//�޸��������� ���͸�sender ������δʵ��
				if ("changeinfo".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				//�޸��������� �����ݷ��͸�sender
				if ("changepwd".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				//�������� ���͸�sender
				if ("forgetpwd".equals(cmd.getCmd())) {
					oos1 = new ObjectOutputStream(socket.getOutputStream());
				}
				
				//�û�����
				if("logout".equals(cmd.getCmd())) {
					return;
				}
				
				if(oos1 != null) {
					//System.out.println("oos1"+oos1);
					oos1.writeObject(cmd);	
					//oos1 = null;
				}
				if(oos2 != null) {
					//System.out.println("oos2"+oos2);
					oos2.writeObject(cmd);	
					//oos2 = null;
				}
			} catch(IOException e) {
				socket = null;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//����ͻ��˷���������
	private CommandTranser execute(CommandTranser cmd) {
		
		//��¼����
		System.out.println("������" + cmd.getCmd());
		if("login".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();//��ȡ���͹���������
			System.out.println("login��¼��"+user.getUsername());
			cmd.setFlag(userservice.checkUser(user));
			//�����½�ɹ������ÿͻ��˼����Ѿ����ӳɹ���map�������� ���ҿ������û��Ľ����߳�
			
			if(cmd.isFlag()) {
				// �����̼߳������ӳɹ���map����
				SocketEntity socketEntity = new SocketEntity();
				socketEntity.setName(cmd.getSender());
				socketEntity.setSocket(socket);
				SocketList.addSocket(socketEntity);
				
				//�����ݿ��ȡ������б���������б������ͻ���
				cmd.setData(userservice.getFriendsList(user));//���ƺ����б�
				cmd.setResult("��½�ɹ�");
			} else {
				cmd.setResult("�������");
			}
		}
		
		//ע������
		if("register".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			cmd.setResult("hello");
			cmd.setFlag(userservice.registerUser(user));
			//���ע��ɹ�
			if(cmd.isFlag()) {
				SocketEntity socketEntity = new SocketEntity();
				socketEntity.setName(cmd.getSender());
				socketEntity.setSocket(socket);
				SocketList.addSocket(socketEntity);
				cmd.setData(userservice.getFriendsList(user));
				//��ע��Ŀ϶�û�к��� 
				cmd.setResult("ע��ɹ�");
			} else {
				cmd.setResult("ע��ʧ��");
			}
		}
		
		if("all_online".equals(cmd.getCmd())){
			System.out.println("all_online is here");
			//Set all_online = SocketList.getAllOnline();
			ArrayList<String> all_online = new ArrayList<String>(SocketList.getAllOnline());
			System.out.println(all_online);
			cmd.setAllOnline(all_online);
		}
		
		//�޸��������� ������δʵ��
		if("changeInfo".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			cmd.setFlag(userservice.changeInfo(user));
			if(cmd.isFlag()) {
				cmd.setResult("�޸���Ϣ�ɹ�");
			} else {
				cmd.setResult("�޸���Ϣʧ��");
			}
		}
		
		//��Ӻ���
		if("requeste_add_friend".equals(cmd.getCmd())) {
			//����û��Ƿ�����
			if(SocketList.getSocket(cmd.getReceiver()) != null) {
				cmd.setFlag(true);
				cmd.setResult("�Է��յ������ĺ�������");
			} else {
				cmd.setFlag(false);
				cmd.setResult("��ǰ�û������߻��߸��û�������");
			}
		}
		
		//ͬ����Ӻ�������
		if("accept_add_friend".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			cmd.setFlag(userservice.addFriend(cmd.getReceiver(), cmd.getSender()));
			if(cmd.isFlag()) {
				cmd.setResult("������ӳɹ������µ�½ˢ��");
			} else {
				cmd.setResult("���������ϵ�����Ӻ���ʧ�ܻ��������Ѿ�Ϊ����");
			}
		} 
		
		//���������б�
		if("updatefriendlist".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			cmd.setCmd("updatefriendlist");
			//System.out.println(x);
			user = userservice.getFriendsList(user);//��ȡuser�û��������б�
			if(user.getFriendsNum() == 0) {
				cmd.setFlag(false);
			} else {
				cmd.setFlag(true);
				cmd.setData(user);
			}
		}
		
		//�ܾ���Ӻ���
		if("refuse_to_add".equals(cmd.getCmd())) {
			//����Ƿ�����
			if(SocketList.getSocket(cmd.getReceiver()) != null) {
				cmd.setFlag(true);
				cmd.setResult("���� " + cmd.getSender() +  " �ܾ���");
			} else {
				cmd.setFlag(false);
				cmd.setResult("�Է������߲�֪����ܾ������ĺ�������");
			}
		}
		
		//������Ϣָ��
		if("message".equals(cmd.getCmd())) {
			//����Ƿ�����
			if(SocketList.getSocket(cmd.getReceiver()) != null) {
				//System.out.println(cmd.getData().getClass());
				cmd.setFlag(true);
				//ImageIcon data =  (ImageIcon) cmd.getData();
				//System.out.println(data);
				//cmd.setResult("�Է��ɹ��յ�������Ϣ");
			} else {
				//System.out.println(cmd.getData().getClass());
				//System.out.println(cmd.getData());
				cmd.setFlag(false);
				//JOptionPane.showMessageDialog(null,  "�Է������ߣ����Ժ��ٷ���");
			 
				cmd.setResult("��ǰ�û�������");
			}
		}
		
		if("WordChat".equals(cmd.getCmd())) {
			cmd.setFlag(true);
		}
		
		//��������ָ�� �������Ҫ���û�������ʹ𰸷���
		if("forgetpwd".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			user = userservice.getUser(user);
			//����û�����
			if(user != null ) {
				cmd.setResult("��ѯ�ɹ�");
				cmd.setData(user);
				cmd.setFlag(true);
			} else {
				cmd.setResult("�û����ܲ�����");
				cmd.setFlag(false);
			}
		}	
		
		if ("changepwd".equals(cmd.getCmd())) {
			UserService userservice = new UserService();
			User user = (User)cmd.getData();
			cmd.setFlag(userservice.changePassword(user));
			System.out.println("there 111 ");
			System.out.println(user.getUsername());
			if(cmd.isFlag()) {
				cmd.setResult("�޸�����ɹ�");
			}else {
				cmd.setResult("�޸�����ʧ��");
			}
		}
		
		if("logout".equals(cmd.getCmd())) {
			//SocketList.getSocket(cmd.getSender());
			if("hand".equals(cmd.getData())){
				System.out.println(cmd.getSender());
				String sender = cmd.getSender();
				SocketList.deleteSocket(sender);
				SocketList.deleteNet(sender);
			}
		}
		
		if("update_netlist".equals(cmd.getCmd())){
			System.out.println("�յ�update_netlist");
			String name = cmd.getSender();
			String net = (String)cmd.getData();
			System.out.println(name+"  "+net);
			SocketList.addNet(name, net);
		}
		
		return cmd;
	}
}
