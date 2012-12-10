/**
 * 
 */
package com.bluecloud.mvc.external.beans;


/**
 * @author Hanlu
 *
 */
public class PagingBean extends FragmentBean {

	private String location;// 分页url
	private int nextPage;// 下一页
	private int prePage;// 上一页
	private long allCount;// 所有记录数
	private int currentPage;// 当前页号
	private int pageCount;// 可以分成多少页
	private int numPerPage;// 每一页多少
	private int maxShow;// 最多显示多少页码
	private String classStyle;// css样式
	private String curPageClassStyle;
	private String nxtBtnName = "下一页";
	private String preBtnName = "上一页";
	private String firstBtnName = "首页";
	private String lastBtnName = "末页";
	
	/**
	 * 
	 * @param pageNum 当前页
	 * @param numPerPage 每一页的数据数
	 * @param allCount 所有的条数
	 */
	public PagingBean(int pageNum, int numPerPage, int allCount) {
		if (pageNum == 0) {
			pageNum = 1;
		}
		if (numPerPage == 0) {
			numPerPage = 10;
		}
		this.setMaxShow(20);
		this.currentPage = pageNum;
		this.numPerPage = numPerPage;
		this.allCount = allCount;
		this.countPageCount();
	}

	/**
	 * 
	 * @param strPageNum 当前页
	 * @param strNumPerPage 每一页的数据数
	 * @param allCount 所有的条数
	 */
	public PagingBean(String strPageNum, String strNumPerPage, int allCount) {
		int pageNum = 1;
		if (strPageNum.matches(RegexList.integer_regexp)) {
			pageNum = Integer.parseInt(strPageNum);
		}
		int numPerPage = 10;
		if (strNumPerPage.matches(RegexList.integer_regexp)) {
			numPerPage = Integer.parseInt(strNumPerPage);
		}

		this.setMaxShow(20);
		this.currentPage = pageNum;
		this.numPerPage = numPerPage;
		this.allCount = allCount;
		this.countPageCount();
	}
	
	/**
	 * 
	 */
	public PagingBean() {
		
	}

	private void countPageCount() {
		pageCount = (int) (allCount - 1) / numPerPage + 1;
	}

	private void doNextAndPre() {
		if (currentPage < pageCount)
			nextPage = currentPage + 1;
		else
			nextPage = pageCount;
		if (currentPage > 1)
			prePage = currentPage - 1;
		else
			prePage = 1;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public long getAllCount() {
		return allCount;
	}

	public void setAllCount(long allCount) {
		this.allCount = allCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage < 1) {
			this.currentPage = 1;
			return;
		}
		if (pageCount >= currentPage)
			this.currentPage = currentPage;
		else
			this.currentPage = pageCount;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setMaxShow(int maxShow) {
		this.maxShow = maxShow;
	}

	public int getMaxShow() {
		return maxShow;
	}

	public void setClassStyle(String classStyle) {
		this.classStyle = classStyle;
	}

	public String getClassStyle() {
		return classStyle;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCurPageClassStyle() {
		return curPageClassStyle;
	}

	public void setCurPageClassStyle(String curPageClassStyle) {
		this.curPageClassStyle = curPageClassStyle;
	}

	public String getNxtBtnName() {
		return nxtBtnName;
	}

	public void setNxtBtnName(String nxtBtnName) {
		this.nxtBtnName = nxtBtnName;
	}

	public String getPreBtnName() {
		return preBtnName;
	}

	public void setPreBtnName(String preBtnName) {
		this.preBtnName = preBtnName;
	}

	public String getFirstBtnName() {
		return firstBtnName;
	}

	public void setFirstBtnName(String firstBtnName) {
		this.firstBtnName = firstBtnName;
	}

	public String getLastBtnName() {
		return lastBtnName;
	}

	public void setLastBtnName(String lastBtnName) {
		this.lastBtnName = lastBtnName;
	}
}
