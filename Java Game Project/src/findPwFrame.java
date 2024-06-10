import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class findPwFrame extends JFrame {

    JPanel panel = new JPanel();

    JLabel nameL = new JLabel("이름");
    JLabel emailL = new JLabel("이메일");
    JLabel idL = new JLabel("아이디");

    JTextField nameTf = new JTextField();
    JTextField emailTf = new JTextField();
    JTextField idTf = new JTextField();

    Client c = null;

    JButton findPwBtn = new JButton("찾기");
    JButton previousBtn = new JButton("이전 화면");

    public findPwFrame(Client client) {

        setTitle("비밀번호 찾기");

        c = client;

        nameL.setPreferredSize(new Dimension(100, 30));
        emailL.setPreferredSize(new Dimension(100, 30));
        idL.setPreferredSize(new Dimension(100, 30));

        nameTf.setPreferredSize(new Dimension(145, 30));
        emailTf.setPreferredSize(new Dimension(145, 30));
        idTf.setPreferredSize(new Dimension(145, 30));

        findPwBtn.setPreferredSize(new Dimension(105, 30));
        previousBtn.setPreferredSize(new Dimension(105, 30));

        setContentPane(panel);

        panel.add(nameL);
        panel.add(nameTf);

        panel.add(emailL);
        panel.add(emailTf);

        panel.add(idL);
        panel.add(idTf);

        panel.add(findPwBtn);
        panel.add(previousBtn);

        findPwBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/login?serverTimezone=Asia/Seoul";
                String user = "root";
                String passwd = "2558jun@";

                String name = nameTf.getText();
                String email = emailTf.getText();
                String id = idTf.getText();

                System.out.println(name + " " + email + " " + id);

                try (Connection connection = DriverManager.getConnection(url, user, passwd)) {
                    String query = "SELECT password FROM member WHERE name = ? AND id = ? AND email = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, id);
                    statement.setString(3, email);

                    ResultSet resultSet = statement.executeQuery();

                    if(resultSet.next()) {
                        String findPassword = resultSet.getString("password");

                        JOptionPane.showMessageDialog(null, "비밀번호 : " + findPassword);
                    } else {
                        JOptionPane.showMessageDialog(null, "입력하신 정보가 없습니다.", "비밀번호 오류", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        previousBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setSize(320, 200);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
