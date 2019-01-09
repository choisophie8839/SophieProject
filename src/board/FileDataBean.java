package board;

import java.sql.Timestamp;

public class FileDataBean {
	private int num;
	private String org_filenm;
	private String std_filenm;
	private Timestamp filereg_date;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getOrg_filenm() {
		return org_filenm;
	}
	public void setOrg_filenm(String org_filenm) {
		this.org_filenm = org_filenm;
	}
	public String getStd_filenm() {
		return std_filenm;
	}
	public void setStd_filenm(String std_filenm) {
		this.std_filenm = std_filenm;
	}
	public Timestamp getFilereg_date() {
		return filereg_date;
	}
	public void setFilereg_date(Timestamp filereg_date) {
		this.filereg_date = filereg_date;
	}
	
}
