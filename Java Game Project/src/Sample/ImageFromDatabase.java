import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImageFromDatabase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/login?serverTimezone=Asia/Seoul";
        String user = "root";
        String passwd = "2558jun@";

        try {
            Connection connection = DriverManager.getConnection(url, user, passwd);
            String sql = "SELECT image_data FROM image_table WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, 9); // 이미지의 ID에 따라 적절한 값을 설정

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                InputStream imageStream = result.getBinaryStream("image_data");
                FileOutputStream fileOutput = new FileOutputStream("retrieved_image.png");

                int bytesRead;
                byte[] buffer = new byte[4096];

                while ((bytesRead = imageStream.read(buffer)) != -1) {
                    fileOutput.write(buffer, 0, bytesRead);
                }

                fileOutput.close();
                imageStream.close();
                System.out.println("이미지가 성공적으로 추출되었습니다.");
            } else {
                System.out.println("이미지를 찾을 수 없습니다.");
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
