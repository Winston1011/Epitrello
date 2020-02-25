package dz.epita.trello;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class EpitrelloDataServerice {
		ConnectMysql conmysql = new ConnectMysql();
		Connection conn= null;
		int result = 0;
		int result2 = 0 ;

	/* add a user to the system*/
	public String addUser(User user) throws Exception {
		String result1 = "";
		conn=conmysql.getConn();
		try{
			String sql="insert into user(user_name) values(?)";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user.getUser_name());
			result = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			if(result == 1)
				result1 = "Add User Success"+"\n";
		}catch(Exception e) {
			result1 = "Excute Add User Failed"+"\n";
		}
		return result1;
	}
	
	/* add a list to the system*/
	public String addList(Lists list) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try{
			String sql="insert into list(list_name) values(?)";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,list.getListname());
			result = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			if(result == 1)
				result1 = "Add List Success"+"\n";
		}catch(Exception e) {
			result1 = "Excute Add List Failed"+"\n";
		}
		return result1;
	}
	
	/*add a task to a list which is already exists*/
	public String addTask(Task task) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try{
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("select * from list"); 
			while(rs.next()) {
				if(task.getListname().equals(rs.getString("list_name"))) {
					result2 = 1;
					break;
					}
				else 
					result2 = 2;
			}rs.close();
			if(result2 == 1) {
				String sql="insert into task(list_name,task_name,estimated_time,priority,description) values(?,?,?,?,?)";
				PreparedStatement pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, task.getListname());
				pstmt.setString(2, task.getTaskname());
				pstmt.setInt(3, task.getEstimatedtime());
				pstmt.setInt(4, task.getPriority());
				pstmt.setString(5, task.getDescription());

				String sql1="update list set num_of_task = (select count(task_name) from task where list_name='"+task.getListname()+"') where list_name='"+task.getListname()+"'";
				PreparedStatement pstmt1=conn.prepareStatement(sql1);
					
				result = pstmt.executeUpdate() + pstmt1.executeUpdate();
				pstmt.close();
				pstmt1.close();
				conn.close();
				if(result == 2)
					result1 = "Add Task Success"+"\n";
				}
			else 
					result1 = "List does not exist,add task failed"+"\n";
			}
		catch(Exception e) {
			result1 = "Task already exists,add task failed"+"\n";
		}
		return result1;
	}
	
	/*to edit a task*/
	public String editTask(Task task) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try{
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("select * from task"); 
			while(rs.next()) {
				if(task.getTaskname().equals(rs.getString("task_name"))) {
					result2 = 1;
					break;
					}
				else 
					result2 = 2;
			}rs.close();
			if(result2==1) {
				String sql="update task set estimated_time=?, priority=?, description=? where task_name=?";
				PreparedStatement pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, task.getEstimatedtime());
				pstmt.setInt(2, task.getPriority());
				pstmt.setString(3, task.getDescription());
				pstmt.setString(4, task.getTaskname());
				result = pstmt.executeUpdate();
//				ResultSet rs1 = stmt.executeQuery("select * from task where task_name='"+task.getTaskname()+"' ");
//				List<String> list=new ArrayList<String>();
//				while(rs1.next()) {
//					String a = rs1.getString("task_name");
//					String b = rs1.getString("description");
//					String c = "Priority: "+ rs1.getInt("priority");
//					String d = "Estimated Time: " + rs1.getInt("estimated_time");
//					list.add("\n"+a+"\n"+b+"\n"+c+"\n"+d+"\n");
//				}
//				rs1.close();
//				pstmt.close();
				stmt.close();
				conn.close();
				if(result == 1) {
//					for(int i=0;i<list.size();i++) {
//						result1=list.get(i);
					result1 = "Edit Task Success\n";
						}
					
			}
			else
				result1 = "Task does not exist,edit task failed\n";
			}catch(Exception e) {
				result1 = "Task isn't changed\n";
			}
			return result1;
	}
	
	/*only delete a task*/
	public String deleteTask(Task task) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try{
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("select * from task"); 
			while(rs.next()) {
				if(task.getTaskname().equals(rs.getString("task_name"))) {
					result2 = 1;
					break;
					}
				else 
					result2 = 2;
			}rs.close();
			if(result2 == 1) {
				int tem=0;
				String sql0="update list set num_of_task = (select count(task_name) from task where list_name =(select list_name from task where task_name='"+task.getTaskname()+"'))-1 where list_name = (select list_name from task where task_name='"+task.getTaskname()+"')";
				PreparedStatement pstmt0=conn.prepareStatement(sql0);
				tem = pstmt0.executeUpdate();
				pstmt0.close();
				if(tem==1) {
					String sql="delete from task where task_name='"+task.getTaskname()+"'";
					PreparedStatement pstmt=conn.prepareStatement(sql);
					result = pstmt.executeUpdate();
					pstmt.close();
				}
				conn.close();
				if(result == 1)
					result1 = "Delete Task Success"+"\n";
				else
					result1 = "update success but delete failed"+"\n";
			}
			else
				result1 = "Task does not exist,Delete Task Failed"+"\n";
		}catch(Exception e) {
			result1 = "Delete Task Failed"+"\n";
			}
		return result1;
	}
	
	//delete list with deleting tasks
	public String deleteList(Lists list) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try{
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("select * from list"); 
			while(rs.next()) {
				if(list.getListname().equals(rs.getString("list_name"))) {
					result2 = 1;
					break;
					}
				else 
					result2 = 2;
			}rs.close();
			if(result2 == 1) {
				String sql="delete from list where list_name=?";
				PreparedStatement pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, list.getListname());		
				
				String sql1="delete from task where list_name=?";
				PreparedStatement pstmt1=conn.prepareStatement(sql1);
				pstmt1.setString(1, list.getListname());	
				
				result = pstmt.executeUpdate()+pstmt1.executeUpdate();
				
				pstmt.close();
				pstmt1.close();
				conn.close();
				if(result == 2)
					result1 = "Delete List (with relevant tasks) Success"+"\n";
			}
			else
				result1 = "List does not exist,delete list failed"+"\n";
		}catch(Exception e) {
			result1 = "Delete List Failed"+"\n";
			}
		return result1;
	}
	
	/*assign a task to a user   (modify the other table at the same time)*/
	public String assignTask(User assign_user) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		int temp =0;
		try{
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("select * from user"); 
			while(rs.next()) {
				if(assign_user.getUser_name().equals(rs.getString("user_name"))) {
					result2 = 1;
					break;
					}
				else 
					result2 = 2;
			}rs.close();stmt.close();
			if(result2 == 1) {
				Statement stmt0 = conn.createStatement(); 
				ResultSet rs0 = stmt0.executeQuery("select count(task_name) as c1 from user where user_name='"+assign_user.getUser_name()+"' "); 
				while(rs0.next()) {
					if(temp==rs0.getInt("c1")) {
						temp = 1;
						break;
					}
					else
						temp = 2;
				}
				if(temp==1) {
					String sql="update user set list_name=(select list_name from task where task_name='"+assign_user.getTask_name()+"'),task_name='"+assign_user.getTask_name()+"' where user_name='"+assign_user.getUser_name()+"'";
					PreparedStatement pstmt=conn.prepareStatement(sql);
					
					String sql1 = "update task set user_name='"+assign_user.getUser_name()+"' where task_name = '"+assign_user.getTask_name()+"' ";
					PreparedStatement pstmt1=conn.prepareStatement(sql1);
					result = pstmt.executeUpdate() + pstmt1.executeUpdate();
					pstmt.close();
					pstmt1.close();
					if(result == 2)
						result1 = "Assigned to " + assign_user.getUser_name() ;
				}else if (temp==2) {
					String sql2 = "insert into user(user_name,list_name,task_name) values('"+assign_user.getUser_name()+"',(select list_name from task where task_name='"+assign_user.getTask_name()+"'), '"+assign_user.getTask_name()+"')";
					PreparedStatement pstmt2=conn.prepareStatement(sql2);
					
					String sql3 = "update task set user_name='"+assign_user.getUser_name()+"' where task_name = '"+assign_user.getTask_name()+"' ";
					PreparedStatement pstmt3=conn.prepareStatement(sql3);
					result = pstmt2.executeUpdate() + pstmt3.executeUpdate();
					pstmt2.close();
					pstmt3.close();
					if(result == 2)
						result1 = "Assigned Success\n" ;
				}
			conn.close();		
			}else
				result1 = "No this user"+"\n";
		}
		catch(Exception e) {
			result1 = "Already assign this task to this person"+"\n";
		}
		return result1;
	}
	
	/*move a task to another list*/
	public String moveTask(Task move_task) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try{
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("select * from list"); 
			while(rs.next()) {
				if(move_task.getListname().equals(rs.getString("list_name"))) {
					result2 = 1;
					break;
					}
				else 
					result2 = 2;
			}rs.close();
			if(result2 == 1) {
				String sql0="update list set num_of_task=(select count(task_name) from task where list_name=(select list_name from task where task_name='"+move_task.getTaskname()+"'))-1 where list_name=(select list_name from task where task_name='"+move_task.getTaskname()+"')";
				PreparedStatement pstmt0=conn.prepareStatement(sql0);	
				String sql="update task set list_name='"+move_task.getListname()+"' where task_name = '"+move_task.getTaskname()+"' ";
				PreparedStatement pstmt=conn.prepareStatement(sql);
				String sql1="update list set num_of_task = (select count(task_name) from task where list_name='"+move_task.getListname()+"') where list_name='"+move_task.getListname()+"'";
				PreparedStatement pstmt1=conn.prepareStatement(sql1);	
				result = pstmt0.executeUpdate()+pstmt.executeUpdate()+pstmt1.executeUpdate();	
				pstmt0.close();
				pstmt.close();
				pstmt1.close();
				conn.close();
				if(result == 3)
					result1 = "move task to " +move_task.getListname() + " success" +"\n";
			}else
				result1 = "List does not exist,move task failed"+"\n";
		}
		catch(Exception e) {
			result1 = "Move task failed"+"\n";
		}
		return result1;
	}
	
	/* print the imformation about the task*/
	public String printTask(Task print_task) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try{
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("select * from task"); 
			while(rs.next()) {
				if(print_task.getTaskname().equals(rs.getString("task_name"))) {
					result2 = 1;
					break;
					}
				else 
					result2 = 2;
			}
			if(result2==1) {
				Statement stmt1 = conn.createStatement();
				String sql = "select * from task where task_name = '"+print_task.getTaskname()+"'";
				ResultSet rs1 =stmt1.executeQuery(sql);	
				List<String> list=new ArrayList<String>();
				while (rs1.next()) {
					String a = rs1.getString("task_name");
					String b = rs1.getString("description");
					String c = "Priority: "+rs1.getInt("priority");
					String d = "Estimated Time: "+rs1.getInt("estimated_time");
					String f = "";
					if(rs1.getString("user_name")==null)
						f = "Unassigned" + "\n";
					else
						f = "Assigned to " +rs1.getString("user_name")+ "\n";
					list.add("\n"+a+"\n"+b+"\n"+c+"\n"+d+"\n"+f);				
				}
				for(int i=0;i<list.size();i++) {
					result1 = list.get(i);
				}
				rs1.close();
				stmt1.close();
				conn.close();
			}else
				result1 = "No such task to print"+"\n";
		}catch(Exception e) {
			return "Print task imformation failed"+"\n";
		}
		return result1;
	}
	
	/* print all the tasks about the list*/
	public String printList(Task find_tasks) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try{
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("select * from list"); 
			while(rs.next()) {
				if(find_tasks.getListname().equals(rs.getString("list_name"))) {
					result2 = 1;
					break;
					}
				else 
					result2 = 2;
			}
			if(result2==1) {
				Statement stmt1 = conn.createStatement();
				String sql = "select * from task where list_name = '"+find_tasks.getListname()+"' order by priority";
				ResultSet rs1 =stmt1.executeQuery(sql);	
				List<String> list1=new ArrayList<String>();
				String a = "List " +find_tasks.getListname()+"\n";
				String d = "";
				while (rs1.next()) {
					String b = rs1.getString("priority") ;
					String c = " | " + rs1.getString("task_name");
					if (rs1.getString("user_name")==null)
						d = " | Unassigned";
					else
						d = " | " + rs1.getString("user_name");
					String e = " | " + rs1.getInt("estimated_time")+ " h";
					list1.add(b+c+d+e+"\n");
				}
				Collections.reverse(list1);
				for(int i=0;i<list1.size();i++) {
					result1 += list1.get(i);
				}
				result1 = a + result1;
				rs1.close();
				stmt1.close();
				conn.close();
			}else
				result1 = "No such list to print"+"\n";
		}catch(Exception e) {
			return "Find tasks by list name failed"+"\n";
		}
		return result1;
	}
	
	/*the time to complete theis task*/
	public String completeTask(Task complete_task) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try {
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("select * from task"); 
			while(rs.next()) {
				if(complete_task.getTaskname().equals(rs.getString("task_name"))) {
					result2 = 1;
					break;
					}
				else 
					result2 = 2;
			}
			if(result2==1) {
				Statement stmt1 = conn.createStatement();
				String sql = "select * from task where task_name = '"+complete_task.getTaskname()+"'";
				ResultSet rs1 =stmt1.executeQuery(sql);	
//				List<String> list=new ArrayList<String>();
//				while (rs1.next()) {
//					String b = "This task is: " + rs1.getString("task_name");
//					String c = "To finish will use :"+rs1.getInt("estimated_time") + " h";
//					list.add(b + "\n" +c+"\n");
//				}
//				for(int i=0;i<list.size();i++) {
//					result1 = list.get(i);
//				}
				result1 = "Complete Success\n";
				stmt1.close();
				conn.close();
			}else
				result1 = "No such task to complete"+"\n";
		}catch(Exception e) {
			result1="Failed to comeplete this task"+"\n";
		}
		return result1;

	}
	
	Task print_all_lists = new Task();
	/* print all the tasks about the list*/
	public String printAllLists() throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try {
			Statement stmt1 = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			String sql = "select * from task ";
			String sql2 = "select list_name from task";
			ResultSet rs1 =stmt1.executeQuery(sql);	
			ResultSet rs2 = stmt2.executeQuery(sql2);
			List<String> list1=new ArrayList<String>();
			List<String> list2=new ArrayList<String>();
			
			while(rs2.next()) {
				list2.add(rs2.getString("list_name"));
			}
			while (rs1.next()) {

				String b = rs1.getInt("priority") + " | " + 
								rs1.getString("task_name") +" | " + 
									rs1.getString("user_name") +" | " + rs1.getInt("estimated_time" )+ "h";
				list1.add(b+"\n");
			}
			
			result1="list "+list2.get(0)+"\n"+list1.get(0);
			String t = "\n";
			for(int i=1;i<list1.size();i++) {
				if(list2.get(i).equals((list2.get(i-1))))
					result1 += list1.get(i);
					
				else
					result1 += t+ "list "+list2.get(i)+"\n"+list1.get(i);
			}
			rs1.close();
			stmt1.close();
			conn.close();
		}catch(Exception e) {
			return "Find lists failed"+"\n"; 
		}
		return result1;
	}
	
	/* print the user's tasks*/
	public String printUserTasks(Task user_tasks) throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try {
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("select * from task"); 
			while(rs.next()) {
				if(user_tasks.getUsername().equals(rs.getString("user_name"))) {
					result2 = 1;
					break;
					}
				else 
					result2 = 2;
			}
			if(result2==1) {
				Statement stmt1 = conn.createStatement();
				String sql = "select * from task where user_name = '"+user_tasks.getUsername()+"'";
				ResultSet rs1 =stmt1.executeQuery(sql);	
				List<String> list=new ArrayList<String>();
				while (rs1.next()) {
					String a =  rs1.getString("priority") ;
					String b = " | " + rs1.getString("task_name");
					String c = " | " + rs1.getString("user_name");
					String d = " | " + rs1.getInt("estimated_time") + " h";
					list.add(a+b+c+d+"\n");
				}
				for(int i=0;i<list.size();i++) {
					result1 = list.get(i);
				}
				stmt1.close();
				conn.close();
			}else
				result1 = "User " + user_tasks.getUsername() + "has no tasks"+"\n";
		}catch(Exception e) {
			result1="Failed to find " + user_tasks.getUsername() + "'s tasks"+"\n";
		}
		return result1;
		
	}
	
	/* print users by performances*/
	public String printUsersByPerformance() throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try {
			Statement stmt1 = conn.createStatement();
			String sql = "select user_name,sum(priority) as pri_level from task group by user_name having user_name is not null order by pri_level desc";
			ResultSet rs1 =stmt1.executeQuery(sql);	
			List<String> list=new ArrayList<String>();
			while (rs1.next()) {
				String b = rs1.getString("user_name")+"\n";
				list.add(b);
			}
			for(int i=0;i<list.size();i++) {
				result1 += list.get(i);
			}
			stmt1.close();
			conn.close();
		}catch(Exception e) {
			result1="Failed to sort users by performance"+"\n";
			}
		return result1;
	}
	
	
	/* print users by workload*/
	public String printUsersByWorkload() throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try {
			Statement stmt1 = conn.createStatement();
			String sql = "select user_name,sum(estimated_time) as sum_time from task group by user_name having user_name is not null order by sum_time";
			ResultSet rs1 =stmt1.executeQuery(sql);	
			List<String> list=new ArrayList<String>();
			while (rs1.next()) {
				String b = rs1.getString("user_name")+"\n";
				list.add(b);
			}
			for(int i=0;i<list.size();i++) {
				result1 += list.get(i);
			}
			stmt1.close();
			conn.close();
		}catch(Exception e) {
			result1="Failed to sort users by performance"+"\n";
			}
		return result1;
	}
	
	
	
	/* print the unassigned tasks*/
	public String printUnassignedTasksByPriority() throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try {
			Statement stmt1 = conn.createStatement();
			String sql = "select * from task where user_name is null order by priority";
			ResultSet rs1 =stmt1.executeQuery(sql);	
			List<String> list=new ArrayList<String>();
			while (rs1.next()) {
				String a = rs1.getString("priority") ;
				String b = " | " + rs1.getString("task_name");
				String c = " | Unassigned";;
				String d = " | " + rs1.getInt("estimated_time")+ " h";
				list.add(a+b+c+d+"\n");
			}
			for(int i=0;i<list.size();i++) {
				result1 = list.get(i);
			}
			stmt1.close();
			conn.close();
		}catch(Exception e) {
			result1="Failed to find unassigned tasks"+"\n";
			}
		return result1;
	}
	
	/* print all unfinished task by priority*/
	public String printAllUnfinishedTasksByPriority() throws Exception{
		String result1 = "";
		conn=conmysql.getConn();
		try {
			Statement stmt1 = conn.createStatement();
			String sql = "select * from task where user_name is not null and (priority - estimated_time)<0 ";
			ResultSet rs1 =stmt1.executeQuery(sql);	
			List<String> list=new ArrayList<String>();
			while (rs1.next()) {
				String a =  rs1.getString("priority") ;
				String b = " | " + rs1.getString("task_name");
				String c = " | " + rs1.getString("user_name");
				String d = " | " + rs1.getInt("estimated_time")+ " h";
				list.add(a+b+c+d+"\n");
			}
			for(int i=0;i<list.size();i++) {
				result1 += list.get(i);
			}
			stmt1.close();
			conn.close();
		}catch(Exception e) {
			result1="Failed to find unassigned tasks"+"\n";
			}
		return result1;
		
	}
}
