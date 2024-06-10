import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.swing.*;

public class SearchUserFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /* Panel */
    JPanel panel = new JPanel();

    /* Label */
    JLabel searchUserL = new JLabel("유저 검색");

    /* TextField */
    JTextField searchUserTf = new JTextField();

    /* Button */
    JButton searchUserBtn = new JButton("검색");
    JButton exitBtn = new JButton("이전 화면");

    Client c = null;

    final String searchUserTag = "SEARCHUSER"; // 유저 검색

    SearchUserFrame(Client _c) {
        c = _c;

        setTitle("유저 검색");

        /* Label 크기 */
        searchUserL.setPreferredSize(new Dimension(70, 30));

        /* TextField 크기 */
        searchUserTf.setPreferredSize(new Dimension(160, 30));

        /* Button 크기 */
        searchUserBtn.setPreferredSize(new Dimension(125, 30));
        exitBtn.setPreferredSize(new Dimension(125, 30));

        /* Panel 추가 작업 */
        setContentPane(panel);

        panel.add(searchUserL);
        panel.add(searchUserTf);
        panel.add(searchUserBtn);
        panel.add(exitBtn);

        /* Button 이벤트 리스너 추가 */
        ButtonListener b1 = new ButtonListener();

        searchUserBtn.addActionListener(b1);
        exitBtn.addActionListener(b1);

        /* Panel 크기 및 설정 추가 */
        setSize(280, 120);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /* Button 이벤트 리스너 */
    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();

            String usearch = searchUserTf.getText();

            /* 검색 버튼을 눌렀을 때 */
            if(b.getText().equals("검색")) {
                System.out.println("[Client] 유저 검색");
                /* 검색 창이 비었을 때 */
                if(usearch.equals("")) {
                    System.out.println("[Client] 유저 검색 실패");
                    JOptionPane.showMessageDialog(null, "검색할 유저를 작성하십시오.", "유저 검색 실패", JOptionPane.ERROR_MESSAGE);
                    textClear();
                }
                /* 검색 창에 글자가 있을 때 */
                else if(!usearch.equals("")) {
                    System.out.println("[Client] 유저 검색 성공");
                    c.sendMsg(searchUserTag + "//" + usearch);
                    textClear();
                    dispose();
                }
            }
            /* 이전 화면을 눌렀을 때 */
            if(b.getText().equals("이전 화면")) {
                System.out.println("[Client] 유저 검색 인터페이스 종료");
                textClear();
                dispose();
            }
        }
    }

    private void textClear() {
        searchUserTf.setText("");
    }
}
