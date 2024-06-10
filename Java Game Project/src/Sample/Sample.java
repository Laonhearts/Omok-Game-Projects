package Sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Sample {
    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        String url = "jdbc:mysql://localhost/login?serverTimezone=Asia/Seoul";
        String user = "root";
        String passwd = "2558jun@";

        try {
            // 데이터베이스 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, passwd);
            stmt = con.createStatement();

            // 테이블 생성 쿼리 (이미지를 저장할 BLOB 열 추가)
            String createStr = "CREATE TABLE IF NOT EXISTS image_table (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "image_data BLOB" +
                    ")";

            // 쿼리 실행
            stmt.executeUpdate(createStr); // 업데이트문을 수행한다.
            System.out.println("[Server] 테이블 생성 성공"); // 업데이트문이 성공하면 테이블 생성 성공을 콘솔로 알린다.

            // 연결 및 자원 닫기
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}