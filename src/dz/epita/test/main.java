package dz.epita.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import dz.epita.trello.EpitrelloDataServerice;
import dz.epita.trello.Lists;
import dz.epita.trello.Task;
import dz.epita.trello.User;

public class main {
	public static void main(String[] args) throws Exception{
		
		EpitrelloDataServerice dataserverice = new EpitrelloDataServerice();
		
		User user1 = new User("Thomas",null,null);
		User user2 = new User("AmirAli",null,null);
		User user3 = new User("Rabih",null,null);
		
		Lists list1 = new Lists("Code",null);
		Lists list2 = new Lists("Description",null);
		Lists list3 = new Lists("Misc",null);
		
		Task task1 = new Task("Code",null, "Do Everything", 12, 1, "Write the whole code");
		Task task2 = new Task("Code", null,"Destroy code formatting", 1, 2, "Rewrite the whole code in a worse format");
		Task task3 = new Task("Description",null, "Write Description", 3, 1, "Write the damn description");
		Task task4 = new Task("Misc",null, "Upload Assignment", 1, 1, "Upload it");
		Task task5 = new Task("Misc", null,"Have fun", 10, 2, "Just do it");
		
		Task edit_task = new Task(null,null,"Do Everything", 12, 10, "Write the whole code");
		Task delete_task1 = new Task(null,null,"Upload Assignment", null, null, null);
		
		
		Task move_task1 = new Task("Code",null,"Have fun",null,null,null);
		Task print_task1 = new Task(null,null,"Do Everything",null,null,null);
		Task print_task2 = new Task(null,null,"Have fun",null,null,null);
		
		Task complete_task1 = new Task(null,null,"Do Everything",null,null,null);
		Task find_tasks = new Task("Code",null,null,null,null,null);
		
		User assign_to_user1 = new User("Rabih",null,"Do Everything");
		User assign_to_user2 = new User("Thomas",null,"Destroy code formatting");
		User assign_to_user3 = new User("AmirAli",null,"Write Description");
		
		Task usertasks = new Task(null,"AmirAli",null,null,null,null);
		
//		User assign_to_user4 = new User("AmirAli",null,"Have fun");
//		System.out.println( dataserverice.assignTask(assign_to_user4) ); // assignTask(string task, string user)
//		
//		System.out.println( dataserverice.addUser(user1)); // addUser(string username)
//		System.out.println( dataserverice.addUser(user2) );
//		System.out.println( dataserverice.addUser(user3) );
//
//		System.out.println( dataserverice.addList(list1) ); //addList(string name)
//		System.out.println( dataserverice.addList(list2) );
//		System.out.println( dataserverice.addList(list3) );
		
//		System.out.println( dataserverice.addTask(task1) ); 
//	    /* addTask(string list, string name, unsigned int estimatedTime, unsigned int priority, string description) */
//			
//		System.out.println( dataserverice.editTask(edit_task) );
//	    /* editTask(string task, unsigned int estimatedTime, unsigned int priority, string description) */
//
//		System.out.println( dataserverice.assignTask(assign_to_user1) ); // assignTask(string task, string user)
//	    System.out.println( dataserverice.printTask(print_task1) ); // printTask(string task)
//	
//	    System.out.println( dataserverice.addTask(task2) );
//		System.out.println( dataserverice.assignTask(assign_to_user2) );
//
//		System.out.println( dataserverice.addTask(task3) );
//		
//		System.out.println( dataserverice.assignTask(assign_to_user3) );
//		System.out.println( dataserverice.addTask(task4) );
//		
//		System.out.println( dataserverice.completeTask(complete_task1) ); // completeTask(string task)
//		
//		System.out.println( dataserverice.printUsersByPerformance() );
//		
//	    System.out.println( dataserverice.printUsersByWorkload() );
//
//		System.out.println( dataserverice.printUnassignedTasksByPriority() );
//		System.out.println( dataserverice.deleteTask(delete_task1) ); // deleteTask(string task)
//
//		System.out.println( dataserverice.printAllUnfinishedTasksByPriority() );
//
//		System.out.println( dataserverice.addTask(task5) );
//
//		System.out.println( dataserverice.moveTask(move_task1) ); // moveTask(string task, string list)
//		
//		System.out.println( dataserverice.printTask(print_task2) );
//
//	    System.out.println( dataserverice.printList(find_tasks) ); // printList(string list)
//  
//	    System.out.println(dataserverice.printAllLists());
//		
//	    System.out.println( dataserverice.printUserTasks(usertasks) ); // printUserTasks(string user)
//
//	    System.out.println( dataserverice.printUnassignedTasksByPriority() );
//
//	    System.out.println( dataserverice.printAllUnfinishedTasksByPriority() );
		
		
		//TODO Write all the prints into a file.  
		
		try {
			File file = new File("out_put.txt");
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file,true);
//			String add_u1 = dataserverice.addUser(user1);
//			String add_u2 = dataserverice.addUser(user2);
//			String add_u3 = dataserverice.addUser(user3);		
//			out.write(add_u1.getBytes());
//			out.write(add_u2.getBytes());
//			out.write(add_u3.getBytes());
			
//			String add_l1 = dataserverice.addList(list1);
//			String add_l2 = dataserverice.addList(list2);
//			String add_l3 = dataserverice.addList(list3);			
//			out.write(add_l1.getBytes());
//			out.write(add_l2.getBytes());
//			out.write(add_l3.getBytes());
			
			String add_t1 =  dataserverice.addTask(task1); 
			out.write(add_t1.getBytes());
			String edit_t =  dataserverice.editTask(edit_task); 
			out.write(edit_t.getBytes());
			String assign_u1 = dataserverice.assignTask(assign_to_user1);
			out.write(assign_u1.getBytes());
			String print_t1 = dataserverice.printTask(print_task1);
			out.write(print_t1.getBytes());		
			String add_t2 = dataserverice.addTask(task2);
			out.write(add_t2.getBytes());
			String assign_u2 = dataserverice.assignTask(assign_to_user2);
			out.write(assign_u2.getBytes());
			String add_t3 = dataserverice.addTask(task3);
			out.write(add_t3.getBytes());
			String assign_u3 = dataserverice.assignTask(assign_to_user3);
			out.write(assign_u3.getBytes());
			String add_t4 = dataserverice.addTask(task4);
			out.write(add_t4.getBytes());
			String complete_t1 = dataserverice.completeTask(complete_task1);
			out.write(complete_t1.getBytes());
			
//			System.out.println( dataserverice.printUsersByPerformance() );
//			
//		    System.out.println( dataserverice.printUsersByWorkload() );
	//
//			System.out.println( dataserverice.printUnassignedTasksByPriority() );
//			System.out.println( dataserverice.deleteTask(delete_task1) ); // deleteTask(string task)
	//
//			System.out.println( dataserverice.printAllUnfinishedTasksByPriority() );
	//
//			System.out.println( dataserverice.addTask(task5) );
	//
//			System.out.println( dataserverice.moveTask(move_task1) ); // moveTask(string task, string list)
//			
//			System.out.println( dataserverice.printTask(print_task2) );
	//
//		    System.out.println( dataserverice.printList(find_tasks) ); // printList(string list)
	//  
//		    System.out.println(dataserverice.printAllLists());
//			
//		    System.out.println( dataserverice.printUserTasks(usertasks) ); // printUserTasks(string user)
	//
//		    System.out.println( dataserverice.printUnassignedTasksByPriority() );
	//
//		    System.out.println( dataserverice.printAllUnfinishedTasksByPriority() );
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

			
			

			out.flush();
			out.close();
			
			FileInputStream in = new FileInputStream(file);
			byte [] b = new byte[4096];
			in.read(b, 0, b.length);
			System.out.println(new String(b));
			in.close();
			
	    
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	    
	    
	}

}
