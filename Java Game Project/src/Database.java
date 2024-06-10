import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

//클라이언트가 요청한 데이터베이스 업데이트 및 쿼리 작업을 수행하는 클래스.
//서버에서 객체 생성 시에 데이터베이스 연동 작업을 수행하고 다른 부가적인 작업들은 메소드를 통해 서버에서 불려지면 수행하도록 한다.
public class Database {

    /* 데이터베이스와의 연결에 사용할 변수들 */
    Connection con = null;
    Statement stmt = null;
    String url = "jdbc:mysql://localhost/login?serverTimezone=Asia/Seoul";
    String user = "root";
    String passwd = "2558jun@";

    Database() { // Database 객체 생성 시 데이터베이스 서버와 연결한다.
        try { // 데이터베이스 연결은 try-catch문으로 예외를 잡아준다.
            // 데이터베이스와 연결한다.
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, passwd);
            stmt = con.createStatement();
            System.out.println("[Server] MySQL 서버 연동 성공"); // 데이터베이스 연결에 성공하면 성공을 콘솔로 알린다.
        } catch (Exception e) { // 데이터베이스 연결에 예외가 발생했을 때 실패를 콘솔로 알린다.
            System.out.println("[Server] MySQL 서버 연동 실패> " + e.toString());
        }
    }

    // 로그인 여부를 확인하는 메소드. 서버에 닉네임을 String 형식으로 반환한다.
    String loginCheck(String _i, String _p) {
        String nickname = "null"; // 반환할 닉네임 변수를 "null"로 초기화.

        // 매개변수로 받은 id와 password값을 id와 pw값에 초기화한다.
        String id = _i;
        String pw = _p;

        try {
            // id와 일치하는 비밀번호와 닉네임이 있는지 조회한다.
            String checkingStr = "SELECT password, nickname FROM member WHERE id='" + id + "'";
            ResultSet result = stmt.executeQuery(checkingStr);

            int count = 0;
            while (result.next()) {
                // 조회한 비밀번호와 pw 값을 비교.
                if (pw.equals(result.getString("password"))) { // true일 경우 nickname에 조회한 닉네임에 반환하고 로그인 성공을 콘솔로 알린다.
                    nickname = result.getString("nickname");
                    System.out.println("[Server] 로그인 성공");
                }

                else { // false일 경우 nickname을 "null"로 초기화하고 로그인 실패를 콘솔로 알린다.
                    nickname = "null";
                    System.out.println("[Server] 로그인 실패");
                }
                count++;
            }
        } catch (Exception e) { // 조회에 실패했을 때 nickname을 "null"로 초기화. 실패를 콘솔로 알린다.
            nickname = "null";
            System.out.println("[Server] 로그인 실패 > " + e.toString());
        }

        return nickname; // nickname 반환
    }

    // 회원가입을 수행하는 메소드. 회원가입에 성공하면 true, 실패하면 false를 반환한다.
    boolean joinCheck(String _n, String _nn, String _i, String _p, String _a, String _g, String _b, String _e) {
        boolean flag = false; // 참거짓을 반환할 flag 변수. 초기값은 false

        // 매개변수로 받은 각 문자열들을 각 변수에 초기화한다.
        String na = _n;
        String nn = _nn;
        String id = _i;
        String pw = _p;
        String ad = _a;
        String ge = _g;
        String bi = _b;
        String em = _e;

        try {
            // member 테이블에 각 문자열들을 순서대로 업데이트하는 문장. 승, 패는 초기값을 숫자 0으로 한다.
            String insertStr = "INSERT INTO member VALUES('" + na + "', '" + nn + "', '" + id + "', '" + pw + "', '"
                    + ad + "', '" + ge + "', '" + bi + "', '" + em + "', 0, 0)";
            stmt.executeUpdate(insertStr);

            flag = true; // 업데이트문이 정상적으로 수행되면 flag를 true로 초기화하고 성공을 콘솔로 알린다.
            System.out.println("[Server] 회원가입 성공");
        } catch (Exception e) { // 회원가입 절차를 수행하지 못하면 flag를 false로 초기화하고 실패를 콘솔로 알린다.
            flag = false;
            System.out.println("[Server] 회원가입 실패 > " + e.toString());
        }

        return flag; // flag 반환
    }

    // 아이디나 닉네임이 중복되었는지 확인해주는 메소드. 중복 값이 존재하면 false, 존재하지 않으면 true를 반환한다.
    boolean overCheck(String _a, String _v) {
        boolean flag = false; // 참거짓을 반환할 flag 변수. 초기값은 false

        // att는 속성(아이디, 닉네임)을 구분하고, val은 확인할 값이 초기화.
        String att = _a;
        String val = _v;

        try {
            // member 테이블에 존재하는 아이디(혹은 닉네임)를 모두 찾는다.
            String selcectStr = "SELECT " + att + " FROM member";
            ResultSet result = stmt.executeQuery(selcectStr);

            int count = 0;
            while (result.next()) {
                // 조회한 아이디(혹은 닉네임)과 val을 비교.
                if (!val.equals(result.getString(att))) { // val과 같은 것이 존재하지 않으면 flag를 true로 변경한다.
                    flag = true;
                }

                else { // val과 같은 것이 존재하면 flag를 false로 변경한다.
                    flag = false;
                    break;
                }
                count++;
            }
            System.out.println("[Server] 중복 확인 성공"); // 정상적으로 수행되었을 때 성공을 콘솔로 알린다.
        } catch (Exception e) { // 정상적으로 수행하지 못하면 실패를 콘솔로 알린다.
            System.out.println("[Server] 중복 확인 실패 > " + e.toString());
        }

        return flag; // flag 반환
    }

    // 데이터베이스에 저장된 자신의 정보를 조회하는 메소드. 조회한 정보들을 String 형태로 반환한다.
    String viewInfo(String _nn) {
        String msg = "null"; // 반환할 문자열 변수를 "null"로 초기화.

        // 매개변수로 받은 닉네임을 nick에 초기화한다.
        String nick = _nn;

        try {
            // member 테이블에서 nick이라는 닉네임을 가진 회원의 이름과 이메일 정보를 조회한다.
            String viewStr = "SELECT name, email, address FROM member WHERE nickname='" + nick + "'";
            ResultSet result = stmt.executeQuery(viewStr);

            int count = 0;
            while (result.next()) {
                // msg에 "이름//닉네임//이메일" 형태로 초기화한다.
                msg = result.getString("name") + "//" + nick + "//" + result.getString("email") + "//" + result.getString("address");
                count++;
            }
            System.out.println("[Server] 회원정보 조회 성공"); // 정상적으로 수행되면 성공을 콘솔로 알린다.
        } catch (Exception e) { // 정상적으로 수행하지 못하면 실패를 콘솔로 알린다.
            System.out.println("[Server] 회원정보 조회 실패 > " + e.toString());
        }

        return msg; // msg 반환
    }

    // 데이터베이스에 저장된 모든 회원의 id를 조회하여 클라이언트로 전송
    ArrayList<String> getAllUserId() {
        ArrayList<String> user = new ArrayList<>();

        try {
            String changeStr = "SELECT id FROM member";
            ResultSet rs = stmt.executeQuery(changeStr);

            while (rs.next()) {
                user.add(rs.getString("id"));
            }

            System.out.println("[Server] 회원조회 검색 성공");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    ArrayList<String> searchUser(String su) {
        ArrayList<String> searchUsers = new ArrayList();

        try {
            String searchUserStr = "SELECT id FROM member WHERE id LIKE '" + su + "%'";
            ResultSet rs = stmt.executeQuery(searchUserStr);

            while(rs.next()) {
                searchUsers.add(rs.getString("id"));
            }

            System.out.println("[Client] 유저 검색 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[Client] 유저 검색 실패");
        }

        return searchUsers;
    }

    // id에 해당하는 계정 삭제
    String unRegisterId (String _id){
        String msg = "";
        String id = _id;
        try {
            if(id.equals("admin")) {
                return msg = "관리자 계정은 삭제할 수 없습니다.";
            }else {
                String changeStr = "DELETE FROM member WHERE id = '" + id + "'";
                stmt.executeUpdate(changeStr);
                return msg = "해당 계정이 삭제되었습니다.";
            }
        } catch (SQLException e) {
            System.out.println("[Server] 회원탈퇴 실패 > " + e.toString());
            return msg = "회원 탈퇴에 실패했습니다.";
        }
    }


    // 회원정보 변경을 수행하는 메소드. 변경에 성공하면 true, 실패하면 false를 반환한다.
    boolean changeInfo(String _i, String _a, String _v) {
        boolean flag = false; // 참거짓을 반환할 flag 변수. 초기값은 false.

        // 매개변수로 받은 정보들을 초기화한다. att는 속성(이름, 이메일, 비밀번호) 구분용이고 val은 바꿀 값.
        String id = _i;
        String att = _a;
        String val = _v;

        try {

            // 현재 값 가져오기
            String currentVal = getCurrentValue(id, att);

            // 현재 값과 변경하려는 값이 다를 경우 업데이트 문을 실행
            if(!val.equals(currentVal)) {
                //member 테이블에서 nick이라는 닉네임을 가진 회원의 att(이름, 이메일, 비밀번호)를 val로 변경한다.
                String changeStr = "UPDATE member SET " + att + "='" + val + "' WHERE id ='" + id +"'";

                stmt.executeUpdate(changeStr);

                flag = true;	//정상적으로 수행되면 flag를 true로 바꾸고 성공을 콘솔로 알린다.

                System.out.println("[Server] 회원정보 변경 성공");
            } else {

                System.out.println("[Server] 회원정보 변경 실패");

            }
        } catch(Exception e) {	//정상적으로 수행하지 못하면 flag를 false로 바꾸고 실패를 콘솔로 알린다.

            flag = false;

            System.out.println("[Server] 회원정보 변경 실패 > " + e.toString());

        }

        return flag; // flag 반환

    }

    // 전체 회원의 전적을 조회하는 메소드. 모든 회원의 전적을 String 형태로 반환한다.
    String viewRank() {
        String msg = ""; // 전적을 받을 문자열. 초기값은 ""로 한다.

        try {
            // member 테이블의 닉네임, 승, 패를 모두 조회한다.
            String viewStr = "SELECT nickname, win, lose FROM member";
            ResultSet result = stmt.executeQuery(viewStr);

            int count = 0;
            while (result.next()) {
                // 기존의 msg에 "닉네임 : n승 n패@" 형태의 문자열을 계속해서 추가한다.
                msg = msg + result.getString("nickname") + " : " + result.getInt("win") + "승 " + result.getInt("lose")
                        + "패@";
                count++;
            }
            System.out.println("[Server] 전적 조회 성공"); // 정상적으로 수행되면 성공을 콘솔로 알린다.
        } catch (Exception e) { // 정상적으로 수행하지 못하면 실패를 콘솔로 알린다.
            System.out.println("[Server] 전적 조회 실패 > " + e.toString());
        }

        return msg; // msg 반환
    }

    // 한 명의 회원의 전적을 조회하는 메소드. 해당 회원의 전적을 String 형태로 반환한다.
    String searchRank(String _nn) {
        String msg = "null"; // 전적을 받을 문자열. 초기값은 "null"로 한다.

        // 매개변수로 받은 닉네임을 초기화한다.
        String nick = _nn;

        try {
            // member 테이블에서 nick이라는 닉네임을 가진 회원의 승, 패를 조회한다.
            String searchStr = "SELECT win, lose FROM member WHERE nickname='" + nick + "'";
            ResultSet result = stmt.executeQuery(searchStr);

            int count = 0;
            while (result.next()) {
                // msg에 "닉네임 : n승 n패" 형태의 문자열을 초기화한다.
                msg = nick + " : " + result.getInt("win") + "승 " + result.getInt("lose") + "패";
                count++;
            }
            System.out.println("[Server] 전적 조회 성공"); // 정상적으로 수행되면 성공을 콘솔로 알린다.
        } catch (Exception e) { // 정상적으로 수행하지 못하면 실패를 콘솔로 알린다.
            System.out.println("[Server] 전적 조회 실패 > " + e.toString());
        }

        return msg; // msg 반환
    }

    // 게임 승리 시 전적을 업데이트하는 메소드. 조회 및 업데이트에 성공하면 true, 실패하면 false를 반환한다.
    boolean winRecord(String _nn) {
        boolean flag = false; // 참거짓을 반환할 flag 변수. 초기값은 false.

        // 매개변수로 받은 닉네임과 조회한 승리 횟수를 저장할 변수. num의 초기값은 0.
        String nick = _nn;
        int num = 0;

        try {
            // member 테이블에서 nick이라는 닉네임을 가진 회원의 승리 횟수를 조회한다.
            String searchStr = "SELECT win FROM member WHERE nickname='" + nick + "'";
            ResultSet result = stmt.executeQuery(searchStr);

            int count = 0;
            while (result.next()) {
                // num에 조회한 승리 횟수를 초기화.
                num = result.getInt("win");
                count++;
            }
            num++; // 승리 횟수를 올림

            // member 테이블에서 nick이라는 닉네임을 가진 회원의 승리 횟수를 num으로 업데이트한다.
            String changeStr = "UPDATE member SET win=" + num + " WHERE nickname='" + nick + "'";
            stmt.executeUpdate(changeStr);
            flag = true; // 조회 및 업데이트 성공 시 flag를 true로 바꾸고 성공을 콘솔로 알린다.
            System.out.println("[Server] 전적 업데이트 성공");
        } catch (Exception e) { // 조회 및 업데이트 실패 시 flag를 false로 바꾸고 실패를 콘솔로 알린다.
            flag = false;
            System.out.println("[Server] 전적 업데이트 실패 > " + e.toString());
        }

        return flag; // flag 반환
    }

    // 게임 패배 시 전적을 업데이트하는 메소드. 조회 및 업데이트에 성공하면 true, 실패하면 false를 반환한다.
    boolean loseRecord(String _nn) {
        boolean flag = false; // 참거짓을 반환할 flag 변수. 초기값은 false.

        // 매개변수로 받은 닉네임과 조회한 패배 횟수를 저장할 변수. num의 초기값은 0.
        String nick = _nn;
        int num = 0;

        try {
            // member 테이블에서 nick이라는 닉네임을 가진 회원의 패배 횟수를 조회한다.
            String searchStr = "SELECT lose FROM member WHERE nickname='" + nick + "'";
            ResultSet result = stmt.executeQuery(searchStr);

            int count = 0;
            while (result.next()) {
                // num에 조회한 패배 횟수를 초기화.
                num = result.getInt("lose");
                count++;
            }
            num++; // 패배 횟수를 올림

            // member 테이블에서 nick이라는 닉네임을 가진 회원의 승리 횟수를 num으로 업데이트한다.
            String changeStr = "UPDATE member SET lose=" + num + " WHERE nickname='" + nick + "'";
            stmt.executeUpdate(changeStr);
            flag = true; // 조회 및 업데이트 성공 시 flag를 true로 바꾸고 성공을 콘솔로 알린다.
            System.out.println("[Server] 전적 업데이트 성공");
        } catch (Exception e) { // 조회 및 업데이트 실패 시 flag를 false로 바꾸고 실패를 콘솔로 알린다.
            flag = false;
            System.out.println("[Server] Error: > " + e.toString());
        }

        return flag; // flag 반환
    }
    // 이미지를 디코딩해서 BLOB 필드에 저장하는 메소드
    void imageToDatabase(String encodedImage) {
        try {
            // Base64 디코딩
            byte[] decodedBytes = Base64.getDecoder().decode(encodedImage);

            // BLOB 필드에 저장
            String saveImgStr = "INSERT INTO image_table (image_data) VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(saveImgStr);
            pstmt.setBytes(1, decodedBytes);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 현재 값 가져오기
    private String getCurrentValue(String id, String att) throws SQLException {
        String query = "SELECT " + att + " FROM member WHERE id = '" + id + "'";
        ResultSet rs = stmt.executeQuery(query);

        if(rs.next()) {
            return rs.getString(att);
        }

        return null;
    }
}
