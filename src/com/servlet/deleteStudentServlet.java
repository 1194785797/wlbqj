package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.Idb;
import com.db.Impl.dbImpl;


@WebServlet("/deleteStudentServlet")
public class deleteStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public deleteStudentServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html ;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String[] snos = request.getParameterValues("sno");
		Idb dbimpl = new dbImpl();
		boolean result=false;
		if (snos!=null) {
			
		
		for (String ssno:snos) {
			
			Long sno = Long.parseLong(ssno);
			 result = dbimpl.deleteStudent(sno);
			
		}
		}
		response.setHeader("refresh", "0;url=./queryAllStudent");

		if (result) {
			out.print("<script > alert(\"ɾ���ɹ���\") ;</script> ");
		}else {
			out.print("<script> alert(\"ɾ��ʧ�ܣ������ԣ�\") ; </script> ");

		}
	
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
