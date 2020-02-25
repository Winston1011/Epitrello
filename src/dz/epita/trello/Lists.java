package dz.epita.trello;

public class Lists {
	private String listname;
	private Integer listnum;
	
	public String getListname() {
		return listname;
	}
	public void setListname(String listname) {
		this.listname = listname;
	}
	public Integer getListnum() {
		return listnum;
	}
	public void setListnum(Integer listnum) {
		this.listnum = listnum;
	}

	public Lists(String listname,Integer listnum) {
		super();
		this.listname=listname;
		this.listnum=listnum;
	}
}
