package com.entity;

public class Page {

	private int page;//��ǰҳ��
	private int pageSize;//ҳ���С
	private int totality;//��������
	private int pages;//��ҳ��
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotality() {
		return totality;
	}
	public void setTotality(int totality) {
		this.totality = totality;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public Page(int page, int pageSize, int totality, int pages) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.totality = totality;
		this.pages = pages;
	}
	
	
	

}
