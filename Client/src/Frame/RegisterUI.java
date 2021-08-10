package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Entity.User;
import UserSocket.ChatTread;
import UserSocket.Client;
import _Util.CommandTranser;

 
public class RegisterUI extends JFrame implements ActionListener, FocusListener {
	private static final long serialVersionUID = 1L;
	private JLabel upper_N, user_name_txt, user_pwd_txt, user_ques_txt, user_ans_txt; //��ʾ������
	private JButton register_button_S;
	private JTextField user_name, user_pwd, user_ques, user_ans; //���������ȡ�û�����
	private JPanel tmp_South, center_Center;
	private MainFrame mainFrame; //���ڹرյ�¼ҳ�� ���ע��ɹ��򽫸տ�ʼ��ע��ҳ��ر�
	//private Client client;
	
	
	
	public RegisterUI(MainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		
		//��ʼ������
		init();
		
		//�ϳ�����
		add(center_Center, BorderLayout.CENTER);
		add(upper_N, BorderLayout.NORTH);
		add(tmp_South, BorderLayout.SOUTH);
		
		//λ�á�ҳ���С����
		setSize(250, 400);
		setLocation(550, 300);
		ImageIcon logo = new ImageIcon("image/register_image.png"); //���Ϸ�Сͼ��
		setIconImage(logo.getImage());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	public void init() {
		
		//�Ϸ�ͼƬ���ֹ��� �� ��Ϊ ��
		ImageIcon upper_image = new ImageIcon("image/register_background_image.jpg");
		upper_image.setImage(upper_image.getImage().getScaledInstance(120, 120,
				Image.SCALE_DEFAULT));
		upper_N = new JLabel(upper_image);
		
		//�·�ע��ͼƬ���� �� ��Ϊ ��
		tmp_South = new JPanel(); //��Ϊһ�����ǡ���װȷ��ע�ᰴť
		register_button_S = new JButton();
		ImageIcon conform_register_image = new ImageIcon("image/conform_register_image.png");
		conform_register_image.setImage(conform_register_image.getImage().getScaledInstance(220, 40, Image.SCALE_DEFAULT));
		register_button_S.setIcon(conform_register_image);
		// �����Ʊ߿�
		register_button_S.setBorderPainted(false);
		// ���ñ߿�Ϊ��
		register_button_S.setBorder(null);
		register_button_S.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		register_button_S.addActionListener(this); //�����¼���Ӧ�û���Ϊ
		tmp_South.add(register_button_S);
		
		//�м䲿������
		center_Center = new JPanel();
		user_name_txt = new JLabel("�û��˺�", JLabel.CENTER);
		user_pwd_txt = new JLabel("�û�����", JLabel.CENTER);
		user_ques_txt = new JLabel("��ʾ����", JLabel.CENTER);
		user_ans_txt = new JLabel("�����", JLabel.CENTER);
		user_name = new JTextField();
		user_pwd = new JTextField();
		user_ques = new JTextField();
		user_ans = new JTextField();
		user_name.addFocusListener(this);
		user_pwd.addFocusListener(this);
		user_ans.addFocusListener(this);
		user_ques.addFocusListener(this);
		center_Center.setLayout(new GridLayout(4, 2));
		center_Center.add(user_name_txt);
		center_Center.add(user_name);
		center_Center.add(user_pwd_txt);
		center_Center.add(user_pwd);
		center_Center.add(user_ques_txt);
		center_Center.add(user_ques);
		center_Center.add(user_ans_txt);
		center_Center.add(user_ans);
		
	}
	public void actionPerformed(ActionEvent e) {
		/*
		 * �������˵�¼��ť �����ж��ʺŻ��������Ƿ�Ϊ�� Ȼ���װΪCommandTranser���� ��������������� ������ͨ�������ݿ�ıȶ�
		 * ����֤�ʺ�����
		 */
		if (e.getSource() == register_button_S) {
			String username = user_name.getText().trim();
			String password =  user_pwd.getText().trim();
			String userques = user_ques.getText().trim();
			String userans = user_ans.getText().trim();
			if ("".equals(username) || username == null) {
				JOptionPane.showMessageDialog(null, "�������ʺţ���");
				return;
			}
			if ("".equals(password) || password == null) {
				JOptionPane.showMessageDialog(null, "���������룡��");
				return;
			}
			
			if ("".equals(userques) || userques == null) {
				JOptionPane.showMessageDialog(null, "���������⣡��");
				return;
			}
			if ("".equals(userans) || userans == null) {
				JOptionPane.showMessageDialog(null, "������𰸣���");
				return;
			}
			
			User user = new User(username, password);
			user.setUserQuestion(userques);
			user.setUserAnswer(userans);
			
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("register");
			cmd.setData(user);
			cmd.setReceiver(username);
			cmd.setSender(username);
			
			// ʵ�����ͻ��� ���ҷ������� ���client�ͻ��� ֱ���������� ����һֱ����
			Client client = new Client(); //����Ψһ�Ŀͻ��ˣ����ڽ��ܷ�������������Ϣ�� socket�ӿڣ��� 
			client.sendData(cmd); //��������
			cmd = client.getData(); //���ܷ�������Ϣ
			
			if(cmd != null) {
				if(cmd.isFlag()) {
					
					this.dispose(); //�ر�ע��ҳ��
					//mainFrame.dispose(); //�ر�MainFrameҳ��
					/*
					 * ���ԸĽ���¼���ڵ����� һ��ʱ����Զ��ر� ��https://blog.csdn.net/qq_24448899/article/details/75731529
					 */
					System.out.println("ע��ɹ������¼��");
					JOptionPane.showMessageDialog(null,  "ע��ɹ������¼��");
					/*
					user = (User)cmd.getData(); 
					System.out.println(user.getUsername() + user.getUserpwd());
					FriendsUI friendsUI;
					try {
						friendsUI = new FriendsUI(user, client);
						ChatTread thread = new ChatTread(client, user, friendsUI); //���ﴫclientΪ������Ϣ�� �����ͻ�����һ�� ChatTread��һ��client
						thread.start();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} //��user��ȫ����Ϣ����FriendsUI�У�����Ψһ������������Ľӿڴ���FriendUI�� ���ﴫclient��Ϊ�˷�����Ϣ
					*/
				}else {
					/*
					 * ����this��null��ʲô����?
					 */
					JOptionPane.showMessageDialog(this, cmd.getResult());
				}
			}		

		}

	}
	//���ĵ�����ƶ�֮�����focuslistener ���ﲻ֪��Ϊɶû��ɫ ����
	@Override
	public void focusGained(FocusEvent e) {
		//�����˺������
	    if(e.getSource() == user_name){
	    	user_name.setForeground(Color.BLACK);
	    }
	    	
	    //�������������
	    if(e.getSource() == user_pwd){
	    	user_pwd.setForeground(Color.BLACK);
	    }
	    
	  //�������������
	    if(e.getSource() == user_ques){
	    	user_ques.setForeground(Color.BLACK);
	    }
	    	
	    //����������
	    if(e.getSource() == user_ans){
	    	user_ans.setForeground(Color.BLACK);
	    }
	    
	}
	@Override
	public void focusLost(FocusEvent e) {
		//�����˺������
	    if(e.getSource() == user_name){
	    	user_name.setForeground(Color.gray);
	    }
	    	
	    //�������������
	    if(e.getSource() == user_pwd){
	    	user_pwd.setForeground(Color.gray);
	    }
	    
	  //�������������
	    if(e.getSource() == user_ques){
	    	user_ques.setForeground(Color.gray);
	    }
	    	
	    //����������
	    if(e.getSource() == user_ans){
	    	user_ans.setForeground(Color.gray);
	    }
	}

}

