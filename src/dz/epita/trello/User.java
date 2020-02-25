package dz.epita.trello;

public class User {
	private String user_name;
	private String list_name;
	private String task_name;
	
	
	public String getUser_name() {
		return user_name;
	}
	
	public String getList_name() {
		return list_name;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setList_name(String list_name) {
		this.list_name = list_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public User(String user_name, String list_name,String task_name) {
		super();
		this.user_name=user_name;
		this.list_name=list_name;
		this.task_name=task_name;
	}



	
	
}
