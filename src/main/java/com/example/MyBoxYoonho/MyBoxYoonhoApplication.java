package com.example.MyBoxYoonho;

import com.example.MyBoxYoonho.dao.UserDAO;
import com.example.MyBoxYoonho.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@EnableJpaAuditing
public class MyBoxYoonhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBoxYoonhoApplication.class, args);
	}

	// createMultipleStudent(userDAO);
	// readStudent(userDAO);
	// queryForStudentsByLastName(userDAO);
	// updateStudent(userDAO);
	// deleteStudent(userDAO);
	// deleteAllStudent(userDAO);
	@Bean
	public CommandLineRunner commandLineRunner(UserDAO userDAO, String[] args){
		return runner -> {
			// createStudent(userDAO);
			// queryForUsers(userDAO);
			// createUserByCommand(userDAO);
			System.out.println("If you enter 1, we will create a new User instance for you.");
			System.out.println("If you enter 2, we will create a new User query for you.");
			System.out.println("If you enter 3, we will delete a User with your given Id.");
			System.out.println("If you enter 4, we will delete all the users.");
			System.out.println("If you enter 5, we will get your usedStorage and remainingStorage values.");

			Scanner scanner = new Scanner(System.in);
			int command = scanner.nextInt();
			System.out.println(command);
			while(true){
			if(command == 1){
				createUserByCommand(userDAO);
			}else if (command == 2){
				queryForUsers(userDAO);
			}else if (command == 3){
				String deleteId = scanner.next();
				deleteStudent(userDAO, Integer.parseInt(deleteId));
			}else if (command == 4){
				deleteAllStudent(userDAO);
			}else if (command == 5){
				System.out.println("not implemented yet");
			}else{
				break;
			}

			System.out.println("Please write the next command.");
			command = scanner.nextInt();
			}
		};
	}

	private void deleteAllStudent(UserDAO userDAO) {
		System.out.println("delete all");
		userDAO.deleteAll();
	}

	private void deleteStudent(UserDAO userDAO, Integer deleteId) {
		System.out.println("deleting id: " + deleteId);
		userDAO.delete(deleteId);
	}

	private void updateStudent(UserDAO userDAO) {
		// retrieve student based on the id: primary key
		int studentId = 1;
		System.out.println("Getting student with id: " + studentId);
		User myUser = userDAO.findById(studentId);

		// change first name to scooby
		System.out.println("Updating");
		myUser.setUsedStorage(3.3);

		// update the student
		userDAO.update(myUser);

		// display the updated student
		System.out.println("updated: " + myUser);
	}

	private void queryForUsers(UserDAO userDAO) {
		// get a list of students
		List<User> theUsers = userDAO.findAll();
		// display list of students
		for(User tempStudent: theUsers){
			System.out.println(tempStudent);
		}
	}

	private void readStudent(UserDAO userDAO) {
		// create a student object
		System.out.println("creating new student..");
		User tempStudent = new User(2.2 , 4.4);

		// save the student
		System.out.println("saving the student..");
		userDAO.save(tempStudent);

		// display id of the saved student
		System.out.println("student: " + tempStudent.getId());

		// retrieve student based on the id: primary key
		System.out.println("Retrieving ... ");
		User myStudent = userDAO.findById(tempStudent.getId());
	}

	/*
	private void createMultipleStudent(UserDAO userDAO) {
		// create multiple student object
		System.out.println("creating 3 student object");
		User tempStudent1 = new Student("Um", "Junsik", "junsik.com");
		User tempStudent2 = new Student("Yoonho", "Kim", "yoonho.com");
		User tempStudent3 = new Student("Hyunji", "Lee", "hyongding.com");


		// save multiple  student object
		System.out.println("saving the student");
		studentDAO.save(tempStudent1);
		studentDAO.save(tempStudent2);
		studentDAO.save(tempStudent3);

		// display id of the saved student
		System.out.println("Saved. : " +tempStudent1.getId());
		System.out.println("Saved. : " +tempStudent2.getId());
		System.out.println("Saved. : " +tempStudent3.getId());
	}
	 */

	private void createStudent(UserDAO userDAO) {
		// create the student object
		System.out.println("creating new student object");
		User tempStudent = new User(4.1, 2.2);

		// save the student object
		System.out.println("saving the student");
		userDAO.save(tempStudent);

		// display id of the saved student
		System.out.println("Saved. : " +tempStudent.getId());
	}

	void createUserByCommand(UserDAO userDAO){
		System.out.println("Create new User object.");
		System.out.println("Your total storage is 30GB.");
		User newUser = new User(0, 30);
		userDAO.save(newUser);
		System.out.println("New user saved, your id is : " + newUser.getId());
	}
}
