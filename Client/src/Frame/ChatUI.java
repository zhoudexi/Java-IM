package Frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Entity.User;
import UserSocket.Client;
import _Util.ChatUIList;
import _Util.CommandTranser;
/*
 * ��Ϣ��
 */
 
public class ChatUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextPane chat_windows; //˫��������Ϣ���ı���
	private JTextField message_txt; //д��Ϣ���ı���
	private JButton send_btn; //���Ͱ�ť
	private JButton img_btn;
	private JPanel panel;
	private String owner_name;
	private String friend_name;
	private String who;
	private User owner;
	private Client client; //�ڷ���Ϣ�õ��� ����Ϣ�����������
	private StyledDocument doc; 
	private int width = 500;
	private int height = 500;
	//private ChatTread thread; //������Ϣ�߳�
	
	public ChatUI(String ower_name, String friend_name, String who, Client client, User owner){
		this.owner_name = ower_name;
		this.friend_name = friend_name;
		this.client = client;
		this.who = who;
		this.owner = owner;
		//��������ҳ��
		init();
		
		setTitle(ower_name + "���ں�" + friend_name + "����");
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		// �����ͻ��˽�����Ϣ�߳�,���յ�����Ϣ������ chat_windows
		//thread = new ChatTread(client); //��Ψһclient����
		//thread.start();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		panel = new JPanel();
		message_txt = new JTextField(20);
		send_btn = new JButton("����");
		img_btn = new JButton("ѡ��ͼƬ");
		/*
		JFileChooser chooser = new JFileChooser("ѡ��ͼƬ");//����һ���ļ�ѡ����
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & PNG Images", "jpg", "png");//��������ļ��Ĺ淶
		File f =null;
		chooser.setFileFilter(filter);//��������ļ��淶
		    */
		
		panel.add(message_txt);
		panel.add(send_btn);
		panel.add(img_btn);
		chat_windows = new JTextPane();
		chat_windows.setEditable(false);
		chat_windows.add(new JScrollBar(JScrollBar.VERTICAL)); //������
		add(chat_windows, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		send_btn.addActionListener(this);
		img_btn.addActionListener(this);
		//���ڹر��¼�
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ChatUIList.deletChatUI(friend_name);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				ChatUIList.deletChatUI(friend_name);
			}
		});
	}
	/*
	private void insertIcon(File file) {
		  chat_windows.setCaretPosition(chat_windows.getLength()); // ���ò���λ��
		  chat_windows.insertIcon(new ImageIcon(file.getPath())); // ����ͼƬ
		  insert(new FontAttrib()); // ���������Ի���
	}*/
	//���ͷ���Ϣ
	public JTextPane getChatWin() {
		return chat_windows;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//������Ͱ�ť
		if(e.getSource() == send_btn) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
			
			String message = "��˵ : " + message_txt.getText() + "\t"
					+ sdf.format(date) + "\n";
			//����������Ϣ
			//chat_windows.setText(chat_windows.getText());
			//chat_windows.setText(chat_windows.getText()+message);
			doc = chat_windows.getStyledDocument();
			try {
				doc.insertString(doc.getLength(), message, null);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			chat_windows.setCaretPosition(doc.getLength());//�����ӵ������λ��  
			//����
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("message");
			if("WorldChat".equals(owner_name)) {
				cmd.setCmd("WorldChat");
			}
			cmd.setSender(who);
			cmd.setReceiver(friend_name);
			cmd.setData(message_txt.getText());
			
			ArrayList<String> friends = owner.getFriend();
			cmd.setFriends(friends);
			//����
			client.sendData(cmd);
			
			//��������Ϣ���������������
			message_txt.setText(null);
		}else if(e.getSource() == img_btn) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
			
			String message = "��˵ : " + message_txt.getText() + "\t"
					+ sdf.format(date) + "\n";
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("message");
			if("WorldChat".equals(owner_name)) {
				cmd.setCmd("WorldChat");
			}
			cmd.setSender(who);
			cmd.setReceiver(friend_name);
			ArrayList<String> friends = owner.getFriend();
			cmd.setFriends(friends);
			
			System.out.println("����ͼƬ");
			JFileChooser chooser = new JFileChooser();//����һ���ļ�ѡ����
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & PNG Images", "jpg", "png");//��������ļ��Ĺ淶
		    File f =null;
		    chooser.setFileFilter(filter);//��������ļ��淶
		    int returnVal = chooser.showOpenDialog( new  TextField());//����ֵ ��ȷ��
		    System.out.println(returnVal);
		    if(returnVal == JFileChooser.APPROVE_OPTION) { //�ж��Ƿ�ѡ��Ϊȷ��
		    	f= chooser.getSelectedFile(); //ѡ����ļ� ���ظ� File
		          //  chooser.getSelectedFile();
			    String flujin =f.getParent()+"\\"+f.getName();//���ļ���·�� ����ΪString
			    System.out.print(flujin);
			    ImageIcon i = new ImageIcon(flujin); //����һ��ImageIcon ����һ������ļ�an��·��
			    i.setImage(i.getImage().getScaledInstance(110,100,Image.SCALE_DEFAULT));//���ã�ͼƬ���ļ��Ĵ�С
			    //chat_windows.setText(chat_windows.getText()+"��˵��");
			    doc = chat_windows.getStyledDocument();
				try {
					doc.insertString(doc.getLength(), "��˵��", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				chat_windows.setCaretPosition(doc.getLength());//�����ӵ������λ��  
			    chat_windows.insertIcon(i);
			    doc = chat_windows.getStyledDocument();
				try {
					doc.insertString(doc.getLength(), sdf.format(date) + "\n", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				chat_windows.setCaretPosition(doc.getLength());
				cmd.setData(i);
				client.sendData(cmd);
		    }
		}
	}
}
