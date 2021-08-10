package _Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 
public class DBHelper {
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/myqquser?&useSSL=false&serverTimezone=UTC";
	private static final String username = "root";
	private static final String password = "root";
	private static Connection con = null;
	
	//��̬���븺���������
	static {
		try {
			Class.forName(driver); //Class.forName(xxx.xx.xx)��������Ҫ��JVM���Ҳ�����ָ�����࣬Ҳ����˵JVM��ִ�и���ľ�̬�����
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		if(con == null) {
			try {
				con = DriverManager.getConnection(url, username, password);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return con;
	}
}
