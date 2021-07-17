package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.security.auth.x500.X500Principal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bean.ipLocation;
import com.db.Idb;
import com.db.Impl.dbImpl;
import com.entity.student;


@WebServlet("/addStudentServlet")
public class addStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // �ϴ�����
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    //���ֶ�
    String sname ,sclass ,department ,ip ,picPath;
    Long sno ;
	
    public addStudentServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//�ļ��ϴ�
		// ����Ƿ�Ϊ��ý���ϴ�
	    if (!ServletFileUpload.isMultipartContent(request)) {
	        // ���������ֹͣ
	        PrintWriter writer = response.getWriter();
	        writer.println("Error: ��������� enctype=multipart/form-data");
	        writer.flush();
	        return;
	    }

	    // �����ϴ�����
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
	    factory.setSizeThreshold(MEMORY_THRESHOLD);
	    // ������ʱ�洢Ŀ¼
	    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

	    ServletFileUpload upload = new ServletFileUpload(factory);
	     
	    // ��������ļ��ϴ�ֵ
	    upload.setFileSizeMax(MAX_FILE_SIZE);
	     
	    // �����������ֵ (�����ļ��ͱ�����)
	    upload.setSizeMax(MAX_REQUEST_SIZE);

	    // ���Ĵ���
	    upload.setHeaderEncoding("UTF-8"); 

	    // ������ʱ·�����洢�ϴ����ļ�
	    // ���·����Ե�ǰӦ�õ�Ŀ¼
	    String uploadPath ="c:\\" + File.separator + UPLOAD_DIRECTORY;
	   
	     
	    // ���Ŀ¼�������򴴽�
	    File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdir();
	    }

	    try {
	        // ���������������ȡ�ļ�����
	        @SuppressWarnings("unchecked")
	        List<FileItem> formItems = upload.parseRequest(request);
	        
	        if (formItems != null && formItems.size() > 0) {
	        	
	            // ����������
	            for (FileItem item : formItems) {
	            	System.out.println(item.isFormField());
	                // �����ڱ��е��ֶ�
	                if (!item.isFormField()) {
	                    String fileName = new File(item.getName()).getName();
	                    String filePath = uploadPath + File.separator + fileName;
	                    File storeFile = new File(filePath);
	                    // �ڿ���̨����ļ����ϴ�·��
	                    System.out.println(filePath);
	                    picPath = filePath;
	                    // �����ļ���Ӳ��
	                    item.write(storeFile);
	                    request.setAttribute("message",
	                        "�ļ��ϴ��ɹ�!");
	                    String name = item.getFieldName();
	                    System.out.println(name);
	                }else  {
	                	String name = item.getFieldName();
	                    System.out.println(name);
	                    System.out.println("------------------------");
	                    String cString = "sclass";
//	                    sclass =item.getString("utf-8");
//                		System.out.println(sclass);
	                	if(name.equals("sclass")) {
	                		sclass =item.getString("utf-8");
	                		System.out.println(sclass);
	                	}else if (name.equals("sno")) {
	                		sno = Long.parseLong(item.getString("utf-8"));
						}else if (name.equals("name")) {
							sname = item.getString("utf-8");
						}else if (name.equals("department")) {
							department = item.getString("utf-8");
						}else if (name.equals("ip")) {
							ip = item.getString("utf-8");
						}
					}
	                
	                
	            }
	            System.out.println(ip);
		        ipLocation location = new ipLocation();
		    	
		    	String placeJson = location.getPlace(ip);
		    	
		    	String address = location.getAddress(placeJson);
		    	
		    	student student = new student(sno, sname, sclass, department, ip ,address, picPath);
		    	System.out.println(student);
		    	 Idb db = new dbImpl();
		    	 boolean exist = db.exist(sno);
		    	 if (exist==true) {
		    		request.setAttribute("exist", exist);
//		    		System.out.println(exist);
		    		request.getRequestDispatcher("./index.jsp").forward(request, response);
		    	}else {
		    		 boolean result = db.addStudent(student);
		    		 
		    		 if (result) {
		    			 request.setAttribute("result", "1");
		    			request.getRequestDispatcher("./index.jsp").forward(request, response);
		    		
		    		 }else {
		    			request.setAttribute("result", "2");
		    		}
		    		 
		    	}
	            
	        }
	        
	    } catch (Exception ex) {
	        request.setAttribute("message",
	                "������Ϣ: " + ex.getMessage());
	    }
//	request.setCharacterEncoding("utf-8");
//	response.setCharacterEncoding("utf-8");
//	response.setContentType("text/html;charset=utf-8");
//	String sname = request.getParameter("name");
//	String sclass = request.getParameter("sclass");
//	String sno1 = request.getParameter("sno");
//	System.out.println(sno1);
//	Long sno = Long.parseLong(request.getParameter("sno"));
//	String department = request.getParameter("department");
//	String ip = request.getParameter("ip");
//	
//	
//	
//	
//    
//    
//    
//    
//	ipLocation location = new ipLocation();
//	
//	String placeJson = location.getPlace(ip);
//	
//	String address = location.getAddress(placeJson);
//	
//	student student = new student(sno, sname, sclass, department, ip ,address);
//	System.out.println(student);
//	 Idb db = new dbImpl();
//	 boolean exist = db.exist(sno);
//	 if (exist==true) {
//		request.setAttribute("exist", exist);
////		System.out.println(exist);
//		request.getRequestDispatcher("./index.jsp").forward(request, response);
//	}else {
//		 boolean result = db.addStudent(student);
//		 
//		 if (result) {
//			 request.setAttribute("result", "1");
//			request.getRequestDispatcher("./index.jsp").forward(request, response);
//		
//		 }else {
//			request.setAttribute("result", "2");
//		}
//		 
//	}
		
	
	
	 
	
	
	
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
