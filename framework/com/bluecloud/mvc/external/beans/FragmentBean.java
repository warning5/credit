/**
 * 
 */
package com.bluecloud.mvc.external.beans;

/**
 * @author Hanlu
 *2012-9-11
 */
public class FragmentBean {
	private int pageNum=1;
	private int numPerPage=10;
	private int offset=0;
	/**
	 * 
	 */
	public FragmentBean() {
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum=pageNum;
	}
	/**
	 * @param numPerPage the numPerPage to set
	 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		if(this.pageNum>=1){
			this.offset=this.pageNum-1;
		}
		if(this.offset>0){
			this.offset=this.numPerPage*this.offset;
		}
		return this.offset;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return this.getNumPerPage();
	}

	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @return the numPerPage
	 */
	public int getNumPerPage() {
		return numPerPage;
	}
	
}
