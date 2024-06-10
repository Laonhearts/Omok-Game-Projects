import java.sql.*;

//프로그램 첫 실행 시 프로그램에 필요한 테이블을 생성하는 클래스.
//Server 클래스에도 main메소드가 있기 때문에 Server와 상관없이 단독으로 실행된다.
public class DBTable {

    public static void main(String[] args) { // 클래스 실행 시 main메소드가 바로 시작한다.
        /* 데이터베이스와의 연결에 사용할 변수들 */
        Connection con = null;
        Statement stmt = null;
        String url = "jdbc:mysql://localhost/login?serverTimezone=Asia/Seoul";
        String user = "root";
        String passwd = "2558jun@";

        try { // 데이터베이스 연결은 try-catch문으로 예외를 잡아준다.
            // 데이터베이스와 연결한다.
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, passwd);
            stmt = con.createStatement();

            // member라는 테이블 생성하고 테이블 안의 칼럼은 name, nickname, id, password, email, win, lose가
            // 존재. nickname과 id를 기본키로 잡아준다.
            String createStr = "CREATE TABLE member (name varchar(20) not null, nickname varchar(20) not null, "
                    + "id varchar(20) not null, password varchar(20) not null, email varchar(40) not null, "
                    + "birth varchar(30) not null, gender varchar(6) not null, win int not null, lose int not null, PRIMARY KEY (nickname, id)"
                    + ")";
            stmt.executeUpdate(createStr); // 업데이트문을 수행한다.

            createStr = "CREATE TABLE IF NOT EXISTS image_table ("
                    + "id varchar(20) not null AUTO_INCREMENT PRIMARY KEY, " + "image_data BLOB" + ")";
            stmt.executeUpdate(createStr);

            System.out.println("[Server] 테이블 생성 성공"); // 업데이트문이 성공하면 테이블 생성 성공을 콘솔로 알린다.
            stmt.close();
            con.close();
        } catch (Exception e) { // 데이터베이스 연결 및 테이블 생성에 예외가 발생했을 때 실패를 콘솔로 알린다.
            System.out.println("[Server] 데이터베이스 연결 혹은 테이블 생성에 문제 발생 > " + e.toString());
        }

    }
}
