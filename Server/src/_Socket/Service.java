package _Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

 
public class Service {
	public void startService() {
		try {
			// �������2222�˿ڼ����ͻ��������TCP����
			ServerSocket ss = new ServerSocket(2222);
			Socket socket = null;
			
			while(true) {//ѭ������
	            // �ȴ��ͻ��˵�����
				socket = ss.accept();
				// Ϊÿ���ͻ������ӿ���һ���߳�
				ServerThread thread = new ServerThread(socket);
				thread.start();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
