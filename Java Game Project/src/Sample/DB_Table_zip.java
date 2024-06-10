import java.sql.*;

//프로그램 첫 실행 시 프로그램에 필요한 테이블을 생성하는 클래스.
//Server 클래스에도 main메소드가 있기 때문에 Server와 상관없이 단독으로 실행된다.
public class DB_Table_zip {

    public static void main(String[] args) {	//클래스 실행 시 main메소드가 바로 시작한다.
        /* 데이터베이스와의 연결에 사용할 변수들 */
        Connection con = null;
        Statement stmt = null;
        String url = "jdbc:mysql://localhost:3306/login?serverTimezone=Asia/Seoul";
        String user = "root";
        String passwd = "2558jun@";

        try {	//데이터베이스 연결은 try-catch문으로 예외를 잡아준다.
            //데이터베이스와 연결한다.
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, passwd);
            stmt = con.createStatement();

            String createzip = "CREATE TABLE zipcode (" +
                    "zipcode        VARCHAR(5) NULL," +
                    "sido           VARCHAR(25) NULL," +
                    "sido_en        VARCHAR(20) NULL," +
                    "sigungu        VARCHAR(30) NULL," +
                    "sigungu_en     VARCHAR(30) NULL," +
                    "eupmyun        VARCHAR(20) NULL," +
                    "eupmyun_en     VARCHAR(25) NULL," +
                    "doro_code      VARCHAR(12) NULL," +
                    "doro           VARCHAR(40) NULL," +
                    "doro_en        VARCHAR(50) NULL," +
                    "under_yn       VARCHAR(1) NULL," +
                    "buildno1       VARCHAR(5) NULL," +
                    "buildno2       VARCHAR(4) NULL," +
                    "buildnum       VARCHAR(25) NULL," +
                    "multiple       VARCHAR(1) NULL," +
                    "buildname      VARCHAR(70) NULL," +
                    "dong_code      VARCHAR(10) NULL," +
                    "dong           VARCHAR(20) NULL," +
                    "ri             VARCHAR(20) NULL," +
                    "dong_hj        VARCHAR(30) NULL," +
                    "mount_yn       VARCHAR(1) NULL," +
                    "jibun1         VARCHAR(4) NULL," +
                    "eupmyundong_no VARCHAR(2) NULL," +
                    "jibun2         VARCHAR(4) NULL," +
                    "zipcode_old    VARCHAR(7) NULL," +
                    "zipcode_seq    VARCHAR(3) NULL," +
                    "idx            INT(10)    UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;";

            stmt.executeUpdate(createzip);
            System.out.println("[Server] 테이블 생성 성공");	//업데이트문이 성공하면 테이블 생성 성공을 콘솔로 알린다.
        } catch(Exception e) {	//데이터베이스 연결 및 테이블 생성에 예외가 발생했을 때 실패를 콘솔로 알린다.
            System.out.println("[Server] 데이터베이스 연결 혹은 테이블 생성에 문제 발생 > " + e.toString());
        }
    }
}
