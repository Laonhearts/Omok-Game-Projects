import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.imageio.ImageIO;

//이미지를 데이터베이스에 저장하는 기능
public class ImageToDatabase {
    public static void main(String[] args) {
        imageToDatabase(new File("C:\\Users\\drago\\Desktop\\omok-main\\image\\1.jpg"));
    }

    /* 이미지를 데이터베이스에 저장하는 메서드 */
    private static File imageToDatabase(File image) {
        // 데이터베이스 연결 정보
        String url = "jdbc:mysql://localhost/sample?serverTimezone=Asia/Seoul";
        String user = "root";
        String passwd = "1234";
        String inputFilePath = image.getPath(); // 이미지 파일 경로
        File outputFile = null; // 리턴할 이미지 파일

        try {
            // 파일 확장자 변수
            String fileExt = "";

            Connection connection = DriverManager.getConnection(url, user, passwd);
            String sql = "INSERT INTO image_table (image_data) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // 이미지 파일을 읽어와서 BLOB 열에 저장
            File imageFile = new File(inputFilePath);
            FileInputStream imageStream = new FileInputStream(imageFile);
            BufferedImage originalImage = ImageIO.read(imageStream);

            imageStream.close();

            int newWidth = 80; // 원하는 너비
            int newHeight = 130; // 원하는 높이

            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
            resizedImage.getGraphics().drawImage(originalImage, 0, 0, newWidth, newHeight, null);

            // 파일 확장자 별로 원본파일 확장자에 맞는 파일로 생성하기
            if (inputFilePath != null) {
                int index = inputFilePath.lastIndexOf(".");
                if (index > 0) {
                    String extension = inputFilePath.substring(index + 1);
                    fileExt = extension;
                }
            }

            File outputImageFile = new File("C:\\Users\\drago\\Desktop\\omok-main\\image\\output." + fileExt);
            ImageIO.write(resizedImage, fileExt, outputImageFile);
            outputFile = outputImageFile;

            FileInputStream imageStream2 = new FileInputStream(outputImageFile);
            byte[] imageBytes = new byte[(int) outputImageFile.length()];
            imageStream2.read(imageBytes);
            statement.setBytes(1, imageBytes);

            imageStream2.close();

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("이미지가 성공적으로 저장되었습니다.");
            } else {
                System.out.println("이미지 저장 실패");
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile;
    }
}
