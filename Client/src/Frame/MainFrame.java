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
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Entity.User;
import UserSocket.ChatTread;
import UserSocket.Client;
import _Util.CommandTranser;


/**
*  
*/
//java��GUI����Ļ���˼·����JFrameΪ������������Ļ��window�Ķ����ܹ���󻯡���С�����رա�
public class MainFrame extends JFrame implements ActionListener, FocusListener {
	private static final long serialVersionUID = 1L;
	private static final String _txt_account = "΢�ź�";
	private static final String _txt_pwd = "����";
	private static final String _txt_title = "΢��";
	private static final String _txt_registe = "ע��";
	private static final String _txt_forget = "��������";
	private static final String _txt_blank = "";
	

	//JLabel ��ǩ��Ҫ����չʾ �ı� �� ͼƬ��Ҳ���� ͬʱ��ʾ�ı���ͼƬ
	//JButton ����¼�
	//JTextArea �༭���е��ı���
	//JPasswordArea һ��ֻ���������ֵ������ ������������������ַ�����
	//JTabledPlan ѡ���塣�������û�ͨ��������������ͼ���ѡ�����һ�����֮������л���ʾ
	//JCheckBox ��ѡ���Ƿ�ѡ��
	//JPlanel JPanel�൱�ڽ��������廮�ֳɼ�����壬Ȼ���������ʹ�ò��ֹ�����������ť�Ĳ��֣�һ������ֻ����һ��JFrame�����ж��JPlanel
	//Imagin.SCALE_DEFAULT ����ӦJLabel�Ĵ�С
	//getScaledInstance ������ͼ������Ű汾������һ���µ� image ����Ĭ������£��ö���ָ���� width �� height ����ͼ��
	//��ʹ�Ѿ���ȫ�����˳�ʼԴͼ���µ� image ����Ҳ���Ա��첽����
	//SetIconͼ�꽫�ᱻ�Զ��طŵ���ť������,ȱʡʱ���з���
	//setborderpainted ���������Ӧ�û��Ʊ߿���Ϊ true������Ϊ false


	//private JLable txt_account, txt_pwd;
	private JTextField account;//�˺�
	private JPasswordField pwd;//����

	//��������
	private JLabel upper_frame;
	private JPanel lower_frame, center_frame;

	private JButton login, register, forget;

	MainFrame() {
		//���ֵ��γ�
		init();
		//�����γ�
		/*
		 * ���ԸĽ��ο� https://blog.csdn.net/qq_33531400/article/details/52839439
		 */
		add(upper_frame, BorderLayout.NORTH);
		add(center_frame, BorderLayout.CENTER);
		add(lower_frame, BorderLayout.SOUTH);
		ImageIcon logo = new ImageIcon("image/logo.png"); //���Ϸ�Сͼ��
		setIconImage(logo.getImage());
		setBounds(500, 230, 430, 360); //�趨��С��λ�ã�ǰ�����ǳ��ֵ�λ�ã���������ˮƽ�����ֱ��
		setResizable(false); //��¼���С�̶���������ͨ���ϡ����ı��С
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //���ô������ϽǵĲ�ţ������Ŵ��ڹر� ע�ⲻ��EXIT_ON_CLOSE�������ģ�����ʱ��ʹ�õ���System.exit�����˳�Ӧ�ó��򡣹ʻ�ر����д��ڡ�
		setTitle(_txt_title);
		setVisible(true); //˵������ģ���Ѿ��������,����JVM���Ը�������ģ��ִ��paint������ʼ��ͼ����ʾ����Ļ�ϣ�һ��������һ��
	}
	public void init() {//��¼������������ڷ�
		//�˺������
		account = new JTextField(_txt_account);
		account.setName("account");
		account.setForeground(Color.gray);
		//account.setLocation(110, 165); //ȷ����С��λ��
		//account.setSize(210, 30);
		//setContentPane(account);
		account.addFocusListener(this); //�����¼���Ӧ�û���Ϊ

		//���������
		pwd = new JPasswordField(_txt_pwd);
		pwd.setName("pwd");
		pwd.setForeground(Color.gray);
		pwd.setEchoChar('\0'); //�������������һ��Ϊ �����롱
		//pwd.setLocation(110, 200);
		//pwd.setSize(210, 30);
		pwd.addFocusListener(this);

		//login��ťģ��
		login = new JButton();
		ImageIcon login_image = new ImageIcon("image/login_image.png");
		login_image.setImage(login_image.getImage().getScaledInstance(430, 35, Image.SCALE_DEFAULT));
		login.setIcon(login_image);
		login.setBackground(Color.white);
		login.setBorderPainted(false); //���������Ӧ�û��Ʊ߿���Ϊ true������Ϊ false
		login.setBorder(null); //���ô�����ı߿� ��
		login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //�������Ϊ ��С�֡���״
		login.addActionListener(this);
				
		//ע��ģ��
		register = new JButton(_txt_registe);
		register.setBorderPainted(false);
		register.setBorder(BorderFactory.createRaisedBevelBorder()); //б��߿�͹��
		register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		register.addActionListener(this);

		//��������ģ��
		forget = new JButton(_txt_forget);
		forget.setBorderPainted(false);
		forget.setBorder(BorderFactory.createRaisedBevelBorder());
		forget.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		forget.addActionListener(this);
				
		//qq��¼����ϰ벿�֣��ް�ť֮������ݣ�ֻ��һ��ͼƬ��
		ImageIcon upper_image = new ImageIcon("image/upper_image.png");
		upper_image.setImage(upper_image.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT));
		//upper_image.setImage(upper_image.getImage().getScaledInstance(430, 160, Image.SCALE_DEFAULT));
		upper_frame = new JLabel(upper_image);
		upper_frame.setOpaque(true);
		upper_frame.setBackground(Color.WHITE);
		//qq��¼�м䲿�� (�˺š����롢 ע�ᡢforget)
		center_frame = new JPanel();
		center_frame.setName("center_frame");
		center_frame.setLayout(null);
		center_frame.setLayout(new GridLayout(3, 3, 3, 15)); //�����õ�3��3�еĿռ�, �ÿո����
		center_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		center_frame.add(account);
		center_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		center_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		center_frame.add(pwd);
		center_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		center_frame.add(register);
		center_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		center_frame.add(forget);
		center_frame.setBackground(Color.white);
				
		//qq��¼��ܵ��°벿��login
		lower_frame = new JPanel();
		lower_frame.setName("lower_frame");
		lower_frame.setLayout(null);
		//lower_frame.setLayout(new GridLayout(1, 3, 3, 15));
		lower_frame.setLayout(new GridLayout(0, 1)); 
		lower_frame.add(login);
		/*
		lower_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		lower_frame.add(register);
		lower_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		lower_frame.add(forget);*/
	}
	
	public static long ipToLong(String strIP) {
        long[] ip = new long[4];
        // ���ҵ�IP��ַ�ַ�����.��λ��
        if(strIP.contains("/")){
        	strIP = strIP.substring(strIP.indexOf("/")+1);
        }
        int position1 = strIP.indexOf(".");
        int position2 = strIP.indexOf(".", position1 + 1);
        int position3 = strIP.indexOf(".", position2 + 1);
        // ��ÿ��.֮����ַ���ת��������
        ip[0] = Long.parseLong(strIP.substring(0, position1));
        ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIP.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }
	
	//��ť�ĵ���¼���actionPerformed
	@Override
	public void actionPerformed(ActionEvent e){
		/*
		 * 1���������˵�¼��ť �����ж��ʺŻ��������Ƿ�Ϊ�� Ȼ���װΪCommandTranser���� ��������������� ������ͨ�������ݿ�ıȶ�
		 * ����֤�ʺ����룬
		 * 2����������ע���˺ž͵���ע��ҳ��, ��Ϣ��д���������ӷ�����
		 * 3�����������������뵯���һ�����ҳ��
		 */
		//�����¼(login)ҳ��
		if(e.getSource() == login){
			String user_name = account.getText().trim();
			String user_pwd = new String(pwd.getPassword()).trim();
			if("".equals(user_name) || user_name == null || _txt_account.equals(user_name)) {
				JOptionPane.showMessageDialog(null, "�������ʺţ���");
				return;
			}
			if("".equals(user_pwd) || user_pwd == null || _txt_pwd.equals(user_pwd)) {
				JOptionPane.showMessageDialog(null, "���������룡��");
				return;
			}
			User user = new User(user_name, user_pwd);
			CommandTranser cmd = new CommandTranser();
			//����cmd��ֵ
			cmd.setCmd("login");
			cmd.setData(user);
			cmd.setReceiver(user_name);
			cmd.setSender(user_name);
			
			//ʵ�����ͻ��� ���ӷ����� �������� �����Ƿ���ȷ?
			
			Client client = new Client(); //����Ψһ�Ŀͻ��ˣ����ڽ��ܷ�������������Ϣ�� socket�ӿڣ��� 
			client.sendData(cmd); //��������
			cmd = client.getData(); //���ܷ�������Ϣ
			
			if(cmd != null) {
				if(cmd.isFlag()) {
					this.dispose(); //�ر�MainFrameҳ��
					/*
					 * ���ԸĽ���¼���ڵ����� һ��ʱ����Զ��ر� ��https://blog.csdn.net/qq_24448899/article/details/75731529
					 */
					System.out.println("��½�ɹ�,��ȷ��");
					//JOptionPane.showMessageDialog(null,  "��½�ɹ�,��ȷ��");
					
					CommandTranser cmd2 = new CommandTranser();
					cmd2.setCmd("update_netlist");
					cmd2.setReceiver(user_name);
					cmd2.setSender(user_name);
					//��ȡIP��mask,����Ǿ�����
					InetAddress ip = null;
			        NetworkInterface ni = null;
			        long IP10 = 0;
			        String IP2 = null;
			        long mask10 = 0;
			        String mask2 = null;
			        String net;
			        try {
			            ip = InetAddress.getLocalHost();
			            String IP = ip.toString();
			            IP10 = ipToLong(IP);
			            IP2 = Long.toBinaryString(IP10);
			            System.out.println("������IPΪ��    "+IP2);
			            System.out.println(ip);
			            ni = NetworkInterface.getByInetAddress(ip);// ��������ָ��IP��ַ������ӿ�
			        } catch (Exception e1) {
			            e1.printStackTrace();
			        }
			        List<InterfaceAddress> list = ni.getInterfaceAddresses();// ��ȡ������ӿڵ�ȫ���򲿷�
			                                                                    // InterfaceAddresses
			                                                                    // ����ɵ��б�
			        if (list.size() > 0) {
			            int mask = list.get(0).getNetworkPrefixLength(); // ��������Ķ�����1�ĸ���
			            StringBuilder maskStr = new StringBuilder();
			            int[] maskIp = new int[4];
			            for (int i = 0; i < maskIp.length; i++) {
			                maskIp[i] = (mask >= 8) ? 255 : (mask > 0 ? (mask & 0xff) : 0);
			                mask -= 8;
			                maskStr.append(maskIp[i]);
			                if (i < maskIp.length - 1) {
			                    maskStr.append(".");
			                }
			            }
			            String masks = maskStr.toString();
			            mask10 = ipToLong(masks);
			            //mask2 = Long.toBinaryString(mask10);
			            long netAdress = mask10 & IP10;
			            net = Long.toString(netAdress, 10);
			           
			            System.out.println("ʮ���ƽ���maskΪ��"+mask10);	
			            System.out.println("ʮ���ƽ���IPΪ��     "+IP10);
			            System.out.println("ʮ���ƽ���netΪ�� "+netAdress);	
			            cmd2.setData(net);
			            Client client2 = new Client();
			            client2.sendData(cmd2);
			        }
			       
					user = (User)cmd.getData(); 
					FriendsUI friendsUI;
					try {
						friendsUI = new FriendsUI(user, client);
						ChatTread thread = new ChatTread(client, user, friendsUI); //���ﴫclientΪ������Ϣ�������ͻ�����һ�� ChatTread��һ��client
						thread.start();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} //��user��ȫ����Ϣ����FriendsUI�У�����Ψһ������������Ľӿڴ���FriendUI�� ���ﴫclient��Ϊ�˷�����Ϣ
				}else {
					/*
					 * ����this��null��ʲô����?
					 */
					JOptionPane.showMessageDialog(this, cmd.getResult());
				}
			}		
		}

		//����ע��(register)ҳ��
		if(e.getSource() == register){
			RegisterUI registerUI = new RegisterUI(this);
			//
		}

		//�����һ�����(forget)ҳ��
		if(e.getSource() == forget){
			ForgetUI forgetUI = new ForgetUI(this);
				
		}
			
	}
	
	//���ĵ�����ƶ�֮�����focuslistener
	@Override
	public void focusGained(FocusEvent e) {
		//�����˺������
    	if(e.getSource() == account){
			if(_txt_account.equals(account.getText())){
				account.setText("");
				account.setForeground(Color.BLACK);
			}
		}
    	
		//�������������
		if(e.getSource() == pwd){
			if(_txt_pwd.equals(pwd.getText())){
				pwd.setText("");
				pwd.setEchoChar('*');
				pwd.setForeground(Color.BLACK);
			}
		}
		
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		//�����˺������
		if(e.getSource() == account){
			if("".equals(account.getText())){
				account.setForeground(Color.gray);
				account.setText(_txt_account);
			}
		}
    	
		//�������������
		if(e.getSource() == pwd){
			if("".equals(pwd.getText())){
				pwd.setForeground(Color.gray);
				pwd.setText(_txt_pwd);
				pwd.setEchoChar('\0');
			}
		}

	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame mainframe = new MainFrame();
	}

}
