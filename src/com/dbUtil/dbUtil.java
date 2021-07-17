package com.dbUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.entity.admin;
import com.entity.student;
import com.mysql.jdbc.PreparedStatement;


public class dbUtil {

	String URL ="jdbc:mysql://116.62.232.244:3306/xxq?characterEncoding=utf8";
	String USER ="xxq";
	String PWD = "scz000614";
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet res = null;
	student student = null;
	List<student> students =  new ArrayList<>();
	
	//��ȡconnection����
	public Connection getConnection () throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(URL, USER, PWD);
		
		
		return connection;
		
	}
	//���preparestatement����
	public PreparedStatement getPreparedStatement(String sql) throws ClassNotFoundException, SQLException {
		
		Connection connection = getConnection();
		pstmt = (PreparedStatement) connection.prepareStatement(sql);
		
		return pstmt;
	}
	
	//ͨ�õĲ�
	public ResultSet query(String sql,Object[] obj) {

		
		try {
			pstmt = getPreparedStatement(sql);
			
			for(int i=0;i<obj.length;i++) {
				
				pstmt.setObject(i+1, obj[i]);
				
			}
			 res = pstmt.executeQuery();
			
			
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return res;
		
		
	}

	//ͨ��ѧ�Ų�ѯѧ����Ϣ
	
	public student queryStudentBySno(String sql,Object[] obj) {
		
		res=query(sql, obj);
		try {
			while(res.next()) {
				 Long sno = (long) res.getLong("sno");
				 String sname = res.getString("name");
				 String sclass = res.getString("class");
				 String department = res.getString("department");
				 String ip = res.getString("ip");
				 String address = res.getString("address");
				 Date create_time = res.getDate("create_time");
				 Timestamp update_time = res.getTimestamp("update_time");
				 
				 
				 student=new student(sno, sname, sclass, department, ip, address, create_time, update_time);
				
				 
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return student;
		
	}
	
	
	//��ѯȫ��ѧ��
	public List<student> queryAll(String sql,Object[] obj) {
		 res = query(sql, obj);
		 try {
			while(res.next()) {
				 Long sno = (long) res.getLong("sno");
				 String sname = res.getString("name");
				 String sclass = res.getString("class");
				 String department = res.getString("department");
				 String ip = res.getString("ip");
				 String address = res.getString("address");
				 Date create_time = res.getDate("create_time");
				 Timestamp update_time = res.getTimestamp("update_time");
				 
				 
				 student=new student(sno, sname, sclass, department, ip, address, create_time, update_time);
				 students.add(student);
				 
			 }
			return students;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				shutdown(pstmt, connection, res);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
		return null;
		
	}
	
	//ͨ�õ���ɾ��
	public boolean Upload(String sql,Object[] obj) {
		
		try {
			pstmt = getPreparedStatement(sql);
			
			for(int i = 0; i<obj.length;i++) {
				pstmt.setObject(i+1, obj[i]);
//				System.out.println(obj[i]);
			}
		int count = pstmt.executeUpdate();
		
		if (count>0) {
			return true;
			
		}else {
			return false;
		}
		
		
		
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				shutdown(pstmt, connection, res);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return false;
		
	}
	
	
	//�ر���
	public void shutdown(PreparedStatement pstmt, Connection connection,ResultSet res) throws SQLException {
		
		if (pstmt!=null) {
			pstmt.close();
		}
		if (connection!=null) {
			connection.close();
		}
		if (res!=null) {
			res.close();
		}
		
	
	}
	//��ѯ����
	public int getTotality(String sql,Object[] obj) {
		int count = -1;
		
		 res = query(sql, obj);
		 try {
			
			 
			 if (res.next()) {
				count = res.getInt(1);
			
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				shutdown(pstmt, connection, res);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return count;
		
	}

	//��¼
		public boolean login(String sql,Object[] obj) {
			res = query(sql, obj);
			try {
				while (res.next()) {
					
					return true;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					shutdown(pstmt, connection, res);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return false;
			
			
			
		}

}
	
