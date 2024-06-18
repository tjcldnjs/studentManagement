package ver1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 커넥션 풀을 활용하는 예제로 수정해 보자. HikariCP-5.1.0.jar lib 설정
 */
public class DBConnectionManager1 {

	private static HikariDataSource dataSource;

	
	private static final String ADD_STUDENT = " insert into students(name, age, email) values (?,?,?) ";
	private static final String VIEW_STUDENT = " select * from students ";
	private static final String DELETE_STUDENT = " delete from students where name = '?' ";
	private static final String UPDATE_STUDENT = " update set name =  where ";
	
	
	private static final String URL = "jdbc:mysql://localhost:3306/students?serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PASSWORD = "asd123";

	// static {} 블록 - 정적 초기화 블록
	// 클래스가 처음 로드될 때 한번 실행 됩니다.
	// 정적 변수의 초기화나 복잡한 초기화 작업을 수행할때 사용
	// static {} 블록안에 예외를 던질 수도 있다.

	static {
		// HikariCP 를 사용 하기 위한 설정이 필요 하다.
		// HikariConfig --> 제공해줘서 이 클래스를 활용해서 설정을 상세히 할 수 있다.
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(URL);
		config.setUsername(USER);
		config.setPassword(PASSWORD);
		config.setMaximumPoolSize(10); // 최대 연결 수 설정 10개

		dataSource = new HikariDataSource(config);

	} // end of static

	public static Connection getConnection() throws SQLException {
		System.out.println("HikariCP 를 사용한 Data Source 활용");
		return dataSource.getConnection();
	}

	public static void main(String[] args) {

		try (Connection conn = DBConnectionManager1.getConnection(); Scanner scanner = new Scanner(System.in)){
			while (true) {
				PrintMenu();

				int choice = scanner.nextInt(); // 블로킹

				if (choice == 1) {
					// 퀴즈 문제 추가 --> 함수로 만들기
					addStudent(conn, scanner);
				} else if (choice == 2) {
					// 퀴즈 문제 조회 --> 함수로 만들기
					//viewStudent(conn);
				} else if (choice == 3) {
					// 퀴즈 게임 시작 --> 함수로 만들기
					//updateStudent(conn, scanner);
				} else if (choice == 4) {
					//deleteStudent(conn,scanner);
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
		
		

	} // end of main

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

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
} // end of class