package com.db;

import java.util.List;

import com.entity.admin;
import com.entity.student;

public interface Idb {
	//��ѯ�Ƿ����
	public boolean exist(Long sno);
	//����ѧ�Ų�ѯѧ����Ϣ 
	public student queryStudentBySno(Long sno);
	//������Ϣ
	public boolean addStudent(student student);
	//�޸���Ϣ
	public boolean updateStudentBySno(student student);
	//ɾ����Ϣ
	public boolean deleteStudent(Long sno);
	//��ѯȫ��ѧ����Ϣ
	public List<student> queryAllStudent(int page,int pagesize);
	//��ѯ��������
	public int getTotality();
	//��¼��̨����ҳ�� 
	public boolean loginDao(admin admin);
	
	
	
}
