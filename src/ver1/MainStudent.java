package ver1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class MainStudent {
	private static final String ADD_STUDENT = " insert into students(name, age, email) values (?,?,?) ";
	private static final String VIEW_STUDENT = " select * from students ";
	private static final String UPDATE_STUDENT_NAME = " update students set name =  ? where id = ? ";
	private static final String UPDATE_STUDENT_AGE = " update students set age =  ? where id = ? ";
	private static final String UPDATE_STUDENT_EMAIL = " update students set email =  ? where id = ? ";
	private static final String DELETE_STUDENT = " delete from students where id = ? ";

	private static final Logger LOGGER = Logger.getLogger(MainStudent.class.getName());

	public static void main(String[] args) {

		try (Connection conn = DBConnectionManager.getConnection(); Scanner scanner = new Scanner(System.in)) {

			while (true) {
				PrintMenu();

				int choice = scanner.nextInt();

				if (choice == 1) {
					addStudent(conn, scanner);
				} else if (choice == 2) {
					viewStudent(conn);
				} else if (choice == 3) {
					System.out.println("1. 학생 이름 변경");
					System.out.println("2. 학생 나이 변경");
					System.out.println("3. 학생 이메일 변경");
					System.out.print("옵션을 선택 하세요 : ");
					int updatechoice = scanner.nextInt();
					if (updatechoice == 1) {
						updateStudentName(conn, scanner);
					} else if (updatechoice == 2) {
						updateStudentAge(conn, scanner);
					} else if (updatechoice == 3) {
						updateStudentEmail(conn, scanner);
					} else {
						System.out.println("다시 선택해주세요");
					}
				} else if (choice == 4) {
					deleteStudent(conn, scanner);
				} else if (choice == 5) {
					System.out.println("프로그램을 종료 합니다.");
					break;
				} else {
					System.out.println("잘못된 선택 입니다. 다시 시도하세요.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void PrintMenu() {
		System.out.println();
		System.out.println("==========================================");
		System.out.println("1. 학생 정보 추가");
		System.out.println("2. 학생 정보 조회");
		System.out.println("3. 학생 정보 수정");
		System.out.println("4. 학생 정보 삭제");
		System.out.println("5. 종료");
		System.out.print("옵션을 선택 하세요 : ");
	}

	private static void addStudent(Connection conn, Scanner sc) {
		System.out.println("학생 이름을 입력하세요 : ");
		sc.nextLine();
		String name = sc.nextLine();
		System.out.println("학생 나이을 입력하세요 : ");
		String age = sc.nextLine();
		System.out.println("학생 이메일을 입력하세요 : ");
		String email = sc.nextLine();

		try (PreparedStatement pstmt = conn.prepareStatement(ADD_STUDENT)) {
			pstmt.setString(1, name);
			pstmt.setString(2, age);
			pstmt.setString(3, email);
			int rowsInsertedCount = pstmt.executeUpdate();
			System.out.println("추가된 행의 수 : " + rowsInsertedCount);
			System.out.println("학생의 정보가 추가 되었습니다. ");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void viewStudent(Connection conn) {

		try (PreparedStatement pstmt = conn.prepareStatement(VIEW_STUDENT)) {

			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				System.out.println("학생의 ID : " + resultSet.getInt("id"));
				System.out.println("학생의 이름 : " + resultSet.getString("name"));
				System.out.println("학생의 나이 : " + resultSet.getString("age"));
				System.out.println("학생의 이메일 : " + resultSet.getString("email"));
				if (!resultSet.isLast()) {
					System.out.println("==========================================");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateStudentName(Connection conn, Scanner sc) {
		System.out.println("수정할 학생의 ID를 입력하세요 : ");
		sc.nextLine();
		String studentID = sc.nextLine();

		System.out.println("학생 이름을 입력하세요 : ");
		String name = sc.nextLine();

		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_STUDENT_NAME)) {
			pstmt.setString(1, name);
			pstmt.setString(2, studentID);

			int rowsInsertedCount = pstmt.executeUpdate();
			System.out.println("추가된 행의 수 : " + rowsInsertedCount);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateStudentAge(Connection conn, Scanner sc) {
		System.out.println("수정할 학생의 ID를 입력하세요 : ");
		sc.nextLine();
		String studentID = sc.nextLine();

		System.out.println("학생 나이을 입력하세요 : ");
		String age = sc.nextLine();

		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_STUDENT_AGE)) {
			pstmt.setString(1, age);
			pstmt.setString(2, studentID);

			int rowsInsertedCount = pstmt.executeUpdate();
			System.out.println("추가된 행의 수 : " + rowsInsertedCount);
			System.out.println("ID가 " + studentID + "인 학생의 나이 변경 되었습니다. ");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateStudentEmail(Connection conn, Scanner sc) {
		System.out.println("수정할 학생의 ID를 입력하세요 : ");
		sc.nextLine();
		String studentID = sc.nextLine();

		System.out.println("학생 이메일을 입력하세요 : ");
		String email = sc.nextLine();

		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_STUDENT_EMAIL)) {
			pstmt.setString(1, email);
			pstmt.setString(2, studentID);

			int rowsInsertedCount = pstmt.executeUpdate();
			System.out.println("추가된 행의 수 : " + rowsInsertedCount);
			System.out.println("ID가 " + studentID + "인 학생의 이메일이 변경 되었습니다. ");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deleteStudent(Connection conn, Scanner sc) {
		System.out.println("삭제할 학생의 ID를 입력하세요 : ");
		sc.nextLine();
		String studentID = sc.nextLine();

		try (PreparedStatement pstmt = conn.prepareStatement(DELETE_STUDENT)) {
			pstmt.setString(1, studentID);

			int rowsInsertedCount = pstmt.executeUpdate();
			System.out.println("추가된 행의 수 : " + rowsInsertedCount);
			System.out.println("ID가 " + studentID + " 인 학생의 정보가 삭제 되었습니다. ");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
