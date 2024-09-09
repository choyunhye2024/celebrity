package com.list;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Celebrity {

	static Connection con = null;
	static Statement st = null;
	static ResultSet result = null;
	static Scanner sc = new Scanner(System.in);

	public static void run() {

		dbInit();
		loop: while (true) {

			dbPostCount();

			System.out.println("1.리스트 / 2. 쓰기 / 3. 종료");
			String cmd = sc.next();
			System.out.println(cmd);
			switch (cmd) {

			case "1":
				// 리스트
				System.out.println("======== 명단 ==========");
				System.out.println("글번호 / 이름 / 생일 / 직업");
				System.out.println("-----------------------");
				try {

					result = st.executeQuery("select * from celebrity");

					while (result.next()) {

						String no = result.getString("l_no");
						String name = result.getString("l_name");
						String birthday = result.getString("l_birthday");
						String job = result.getString("l_job");

						System.out.println(no + "" + name + "" + birthday + "" + job);
					}

				} catch (Exception e) {

					e.printStackTrace();

				}
				break;
			case "2":
				sc.nextLine();
				System.out.println("이름을 입력해주세요");
				String name = sc.next();
				System.out.println("생일을 입력해주세요");
				String birthday = sc.next();
				System.out.println("직업을 입력해주세요");
				String job = sc.next();

				try {
					// insert into celebrity (l_name, l_birthday, l_job) values
					// ('강동원','810118','배우');
					String sql = "insert into celebrity (l_name, l_birthday, l_job)" + "values ('" + name + "','"
							+ birthday + "','" + job + "')";
					System.out.println(sql);
					st.executeUpdate(sql);

				} catch (Exception e) {

					e.printStackTrace();
				}
				break;
			case "3":
				// 종료
				break loop;

			}

		}

	}

	private static void dbInit() {

		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_cat", "root", "root");
			st = con.createStatement();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private static void dbPostCount() {

		try {

			result = st.executeQuery("select count(*) from celebrity");
			result.next();
			String count = result.getString("count(*)");

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private static void dbExecuteUpdate(String query) {

		try {
			int resultCount = st.executeUpdate(query);
			System.out.println("처리된 행 수:" + resultCount);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
