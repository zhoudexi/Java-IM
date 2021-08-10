package Frame;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.net.InetAddress;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import Entity.ChatUIEntity;
import Entity.User;
import UserSocket.Client;
import _Util.ChatUIList;
import _Util.CommandTranser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
/*
 *  ͷ�񲿷�Ӧ��һ���û���Ӧһ���������������������û���������ͼ�񣬳�ʼΪͬһ��ͼ��
 *  //����ǩ����Ҫ�û��Զ��壬���Ľ���������ʼΪͬ�������ǩ����Ҳ���Բ������ǩ��)
 */

public class FriendsUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private User owner;// ��ǰ�û�
	private Client client;// �ͻ���
    private JButton changepwd_bt;
    private JButton addfriends_bt;
    private JButton world_bt;
    private JButton udp_bt;
    private DatagramSocket socket;
    private DatagramSocket serversocket;
    private int width = 400;
    private int height = 670;
    private int MAX = 11;
    
	public FriendsUI(User owner, Client client) throws IOException {
		this.owner = owner;
		this.client = client;
		//��ʼ������
		init();
		//receive_udp();
		
		setTitle(owner.getUsername() + "-����");
		setSize(width, height);
		setLocation(1050, 50);
		ImageIcon logo = new ImageIcon("image/friendsui/login_successful_image.jpg"); //���Ϸ�Сͼ��
		setIconImage(logo.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	// ����
	/*
	 * ���˽���һ�� ��ʽ���� �� �߿򣨽磩���� �ֱ���ο�
	 * https://blog.csdn.net/liujun13579/article/details/7771191
	 *  https://blog.csdn.net/liujun13579/article/details/7772215
	 */
	//��������ο��� https://www.cnblogs.com/qingyundian/p/8012527.html
	
	@SuppressWarnings("unused")
	private void receive_udp() throws IOException{
		//�����˿�20001
		serversocket = new DatagramSocket(20002);
		String response = "�յ���";
		byte[] arr = new byte[1024];
	    DatagramPacket packet = new DatagramPacket(arr, arr.length);
	    
	    serversocket.receive(packet);
	    
	    byte[] arr1 = packet.getData();
	    String s = new String(arr1);
	    System.out.println(s);
		serversocket.close();
	}
	
	private void init() throws IOException {
		
		//��¼�ɹ����ϲ��֣�����ͷ�� �û����� ����ǩ���� �������ǩ���̶�
		final JPanel upper_N = new JPanel();
		upper_N.setLayout(new BorderLayout()); // ���ñ߽粼��
		add(upper_N, BorderLayout.NORTH);
		
		//ͷ�񲿷�Ӧ��һ���û���Ӧһ���������������
		ImageIcon my_avata = new ImageIcon("image/friendsui/sdust.jpg"); //ͷ�񲿷�
		final JLabel upper_N_W = new JLabel(my_avata);
		upper_N.add(upper_N_W, BorderLayout.WEST);
		upper_N_W.setPreferredSize(new Dimension(79, 79));
		
		final JPanel upper_N_Cen = new JPanel(); 
		upper_N_Cen.setLayout(new BorderLayout());
		upper_N.add(upper_N_Cen, BorderLayout.CENTER);
		
		
		final JLabel upper_N_Cen_Cen = new JLabel(); //�û�������
		upper_N_Cen_Cen.setText(owner.getUsername());
		upper_N_Cen_Cen.setFont(new Font("����", 1, 16));
		upper_N_Cen.add(upper_N_Cen_Cen, BorderLayout.CENTER);
		
		//����ǩ����Ҫ�û��Զ��壬���Ľ�������
		final JLabel upper_N_Cen_S = new JLabel(); // ����ǩ������
		upper_N_Cen_S.setText("Hello World!");
		upper_N_Cen.add(upper_N_Cen_S, BorderLayout.SOUTH);
		
		
		//��½�ɹ����²��� �޸�����, ��Ӻ���
		final JPanel down_S = new JPanel();
		down_S.setLayout(new BorderLayout());
		add(down_S, BorderLayout.SOUTH);
		
		final JPanel down_S_W = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		down_S_W.setLayout(flowLayout);
		down_S.add(down_S_W); //�����EAST��ɾ��,���ñ�ʶλ���� 
		
		
		addfriends_bt = new JButton(); //��������� ȥ�� set...
		down_S_W.add(addfriends_bt);
		addfriends_bt.setHorizontalTextPosition(SwingConstants.LEFT);
		addfriends_bt.setHorizontalAlignment(SwingConstants.LEFT);
		addfriends_bt.setText("��Ӻ���");
		addfriends_bt.addActionListener(this);
		
		udp_bt = new JButton();
		down_S_W.add(udp_bt);
		udp_bt.setText("����������");
		udp_bt.addActionListener(this);
		
		changepwd_bt = new JButton();
		down_S_W.add(changepwd_bt);
		changepwd_bt.setText("�޸�����");
		changepwd_bt.addActionListener(this);
		
		final JTabbedPane jtp = new JTabbedPane();
		add(jtp, BorderLayout.CENTER);
		
		final JPanel friend_pal = new JPanel();
		final JPanel world_propaganda = new JPanel();
		final JPanel UDP_search = new JPanel();
		
		//�����б�
		int friendsnum = owner.getFriendsNum();//��ȡ�ҵĺ��ѵ�����
		friend_pal.setLayout(new GridLayout(50, 1, 4, 4));//��������ָ�������������Լ����ˮƽ������һ���������񲼾֡�
		final JLabel friendsname[];// ������ҵĺ��� = new JLabel[];
		friendsname = new JLabel[friendsnum];
		
		//
		int usersnum = owner.getAllOnlineNum();
		UDP_search.setLayout(new GridLayout(50, 1, 4, 4));//��������ָ�������������Լ����ˮƽ������һ���������񲼾֡�
		final JLabel usersname[];// ������ҵĺ��� = new JLabel[];
		usersname = new JLabel[usersnum];
		
		
		//MAX�ź���ͷ�� 
		ImageIcon icon[] = new ImageIcon[MAX];
		for(int i = 0; i < MAX; ++i) {
			//System.out.println("image/friendsui/" + Integer.toString(i) + ".jpg");
			icon[i] = new ImageIcon((String)("image/friendsui/" + Integer.toString(i) + ".jpg"));
			icon[i].setImage(icon[i].getImage().getScaledInstance(75, 75,
					Image.SCALE_DEFAULT));
		}	
		//����
		String insert = new String();
		ArrayList<String> friendslist = new ArrayList<String>(owner.getFriend());//�����б�
		for (int i = 0; i < friendsnum; ++i) {
			// ����icon��ʾλ����jlabel�����
			insert = (String)friendslist.get(i);//�����ڴ��б��е�ָ��λ�õ�Ԫ�أ����û�����
			while(insert.length() < 38) {//�û����ֳ��Ȳ�����38
				insert = (String)(insert + " ");
			}
			friendsname[i] = new JLabel(insert, icon[i % MAX], JLabel.LEFT);
			friendsname[i].addMouseListener(new MyMouseListener());
			friend_pal.add(friendsname[i]);
		}
		//�����û�
		System.out.println("�����û���"+usersnum);
		if(usersnum>0){
			String users = new String();
			ArrayList<String> userslist = new ArrayList<String>(owner.getAllOnline());//�û��б�
			System.out.println(userslist);
			for (int i = 0; i < usersnum; ++i) {
				// ����icon��ʾλ����jlabel�����
				users = (String)userslist.get(i);//�����ڴ��б��е�ָ��λ�õ�Ԫ�أ����û�����
				if(users.equals(owner.getUsername())){
					continue;
				}
				while(users.length() < 38) {//�û����ֳ��Ȳ�����38
					users = (String)(users + " ");
				}
				System.out.println(users);
				usersname[i] = new JLabel(users, icon[i % MAX], JLabel.LEFT);
				usersname[i].addMouseListener(new MyMouseListener());
				UDP_search.add(usersname[i]);
			}
		}
		
		//���纰������
		ImageIcon world_image = new ImageIcon("image/friendsui/world.jpg");
		world_image.setImage(world_image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		world_bt = new JButton();
		world_bt.setIcon(world_image);
		world_bt.setBackground(Color.white);
		world_bt.setBorderPainted(false); //���������Ӧ�û��Ʊ߿���Ϊ true������Ϊ false
		world_bt.setBorder(null); //���ô�����ı߿� ��
		world_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //�������Ϊ ��С�֡���״
		world_bt.addActionListener(this);
		world_propaganda.add(world_bt, BorderLayout.CENTER);
		
		
		final JScrollPane jsp = new JScrollPane(friend_pal);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		jtp.addTab("�ҵĺ���", jsp);
		jtp.addTab("Ⱥ����Ϣ", world_propaganda);
		
		final JScrollPane udp = new JScrollPane(UDP_search);
		udp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jtp.addTab("������ͨ��", udp);
		
		//���ڹر��¼�
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("logout");
				CommandTranser cmd = new CommandTranser();
				cmd.setCmd("logout");
				cmd.setData("hand");
				cmd.setReceiver("Server");
				cmd.setSender(owner.getUsername());
				client.sendData(cmd);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("������Ϣ��");
				/*
				CommandTranser cmd = new CommandTranser();
				cmd.setCmd("logout");
				cmd.setData("no_hand");
				cmd.setReceiver("Server");
				cmd.setSender(owner.getUsername());
				client.sendData(cmd);
				*/
			}
		});		
	}
	 
	@Override
	public void actionPerformed(ActionEvent e){
		//����޸����� �� ��Ӻ���
		
		//�޸�����
		if(e.getSource() == changepwd_bt){
			new ChangePwdUI(owner, client);
		}

		//��Ӻ���ҳ��
		if(e.getSource() == addfriends_bt){
			new AddFriendUI(owner, client);
		}

		if(e.getSource() == world_bt) {
			ArrayList<String> friends =  owner.getFriend();
			System.out.println(friends);
			ChatUI chatUI = ChatUIList.getChatUI("WorldChat");
			if(chatUI == null) {
				chatUI = new ChatUI("WorldChat", "WorldChat", owner.getUsername(), client, owner);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName("WorldChat");
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
			}
		}
		
		if(e.getSource() == udp_bt) {
			System.out.println("����������");
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("all_online");
			cmd.setData(owner.getUsername());
			cmd.setReceiver(owner.getUsername());
			cmd.setSender(owner.getUsername());
			
			client.sendData(cmd);
			System.out.println("�����ѷ���");
			//ArrayList<String> friends =  owner.getFriend();
			//System.out.println(friends);
			/*
			System.out.println("����������");
			//1.��������
			//�������ݱ��׽��ֲ�����󶨵������������κο��õĶ˿ڡ�
			try {
				socket = new DatagramSocket();
				String requestData = "check";
				byte[] requestDataBytes = requestData.getBytes();
				
				//����packet
				DatagramPacket requestPacket = new DatagramPacket(requestDataBytes,
						requestDataBytes.length);
				// 20000�˿�, �㲥��ַ
		        requestPacket.setAddress(InetAddress.getByName("255.255.255.255"));
		        requestPacket.setPort(20002);
				
		        socket.send(requestPacket);
		        socket.close();
		        
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		}
	}
	
	class MyMouseListener extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			//˫���ҵĺ��ѵ�����ú��ѵ������
			if(e.getClickCount() == 2) {
				JLabel label = (JLabel)e.getSource(); //getSource()���ص���Object,
				
				//ͨ��label�е�getText��ȡ�������
				String friendname = label.getText().trim();
				System.out.println(friendname + "*");
				//�鿴��ú����Ƿ񴴽�������
				ChatUI chatUI = ChatUIList.getChatUI(friendname);
				if(chatUI == null) {
					chatUI = new ChatUI(owner.getUsername(), friendname, owner.getUsername(), client, owner);
					ChatUIEntity chatUIEntity = new ChatUIEntity();
					chatUIEntity.setName(friendname);
					chatUIEntity.setChatUI(chatUI);
					ChatUIList.addChatUI(chatUIEntity);
				} else {
					chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
				}
				
			}	
		}
		
		//����ȥ�����б� ����ɫ��ɫ	
		@Override
		public void mouseEntered(MouseEvent e) {
			JLabel label = (JLabel)e.getSource();
			label.setOpaque(true); //���ÿؼ���͸��
			label.setBackground(new Color(255, 240, 230));
		}
		
		// �������˳��ҵĺ����б� ����ɫ��ɫ
		@Override
		public void mouseExited(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setOpaque(false);
			label.setBackground(Color.WHITE);
		}
	}

}
