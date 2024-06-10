import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class findIdFrame extends JFrame {

    JPanel panel = new JPanel();

    JLabel nameL = new JLabel("이름");

    JLabel nicknameL = new JLabel("닉네임");
    JLabel emailL = new JLabel("이메일");

    JTextField nameTf = new JTextField();

    JTextField nicknameTf = new JTextField();

    JTextField emailTf = new JTextField();

    Client c = null;

    JButton findIdBtn = new JButton("찾기");
    JButton previousBtn = new JButton("이전 화면");

    public findIdFrame(Client client) {

        setTitle("아이디 찾기");

        c = client;

        nameL.setPreferredSize(new Dimension(100, 30));
        nicknameL.setPreferredSize(new Dimension(100, 30));
        emailL.setPreferredSize(new Dimension(100, 30));

        nameTf.setPreferredSize(new Dimension(145, 30));
        nicknameTf.setPreferredSize(new Dimension(145, 30));
        emailTf.setPreferredSize(new Dimension(145, 30));

        findIdBtn.setPreferredSize(new Dimension(105, 30));
        previousBtn.setPreferredSize(new Dimension(105, 30));

        setContentPane(panel);

        panel.add(nameL);
        panel.add(nameTf);

        panel.add(nicknameL);
        panel.add(nicknameTf);

        panel.add(emailL);
        panel.add(emailTf);

        panel.add(findIdBtn);
        panel.add(previousBtn);

        findIdBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/login?serverTimezone=Asia/Seoul";
                String user = "root";
                String passwd = "2558jun@";

                String name = nameTf.getText();
                String nickname = nicknameTf.getText();
                String email = emailTf.getText();


                System.out.println(name + " " + email + " " + nickname);

                try (Connection connection = DriverManager.getConnection(url, user, passwd)) {
                    String query = "SELECT id FROM member WHERE name = ? AND nickname = ? AND email = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, nickname);
                    statement.setString(3, email);

                    ResultSet resultSet = statement.executeQuery();

                    if(resultSet.next()) {
                        String findId = resultSet.getString("id");

                        JOptionPane.showMessageDialog(null, "아이디  : " + findId);
                    } else {
                        JOptionPane.showMessageDialog(null, "입력하신 정보가 없습니다.", "아이디 오류", JOptionPane.ERROR_MESSAGE);
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

