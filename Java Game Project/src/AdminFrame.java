import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AdminFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    // 리스트와 리스트 모델 구성
    private JList<String> itemList;
    private DefaultListModel<String> listModel;
    String selectedUser = null;

    Client c = null;
    Database db = null;
    String registerID; // 관리자가 콤보박스에서 선택할 id
    String msg = "";

    /* Panel */
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel titlePanel = new JPanel();
    JPanel panel = new JPanel();

    /* Label */
    JLabel titleL = new JLabel("전체 유저 목록");

    /*Button */
    JButton findUserBtn = new JButton("유저 조회");
    JButton searchUserBtn = new JButton("유저 검색");
    JButton insertUserBtn = new JButton("유저 추가");
    JButton updateUserBtn = new JButton("유저 수정");
    JButton deleteUserBtn = new JButton("유저 삭제");
    JButton exitBtn = new JButton("종료");

    final String unRegisterIdTag = "UNREGI"; // 회원 탈퇴 태그
    final String pexitTag = "PEXIT"; // 프로그램 종료 기능 태그
    final String changeTag = "CHANGE"; // 회원정보 변경 기능 태그
    final String chSIdTag = "CHSID"; // 서버에서 처리할 아이디 변경
    final String allViewTag = "VIEWUSER";

    AdminFrame(Client _c) {
        this.c = _c;

        setTitle("관리자 모드");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 600);
        setLocationRelativeTo(null);;

        // 리스트 모델 설정
        listModel = new DefaultListModel<>();
        JList<String> dataList = new JList<>(listModel);

        itemList = new JList<>(listModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 전체 레이아웃 설정
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(dataList);

        panel.add(scrollPane);

        // 위쪽에 타이틀 글자 표시와 테두리, 간격을 가진 패널을 생성하고 추가
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));

        add(titlePanel, BorderLayout.NORTH);

        titleL.setPreferredSize(new Dimension(100, 30));
        titlePanel.add(titleL, BorderLayout.NORTH);

        Font font = new Font("맑은 고딕", Font.PLAIN, 15);
        titleL.setFont(font);

        // 왼쪽에 텍스트 필드와 테두리, 간격을 가진 패널을 생성하고 추가
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        dataList.setPreferredSize(new Dimension(240, 10));
        leftPanel.add(dataList, BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);

        // 오른쪽에 5개의 버튼을 가진 패널을 생성하고 추가
        rightPanel.setLayout(new GridLayout(6, 1, 0, 50));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        rightPanel.add(findUserBtn);
        rightPanel.add(searchUserBtn);
        rightPanel.add(insertUserBtn);
        rightPanel.add(updateUserBtn);
        rightPanel.add(deleteUserBtn);
        rightPanel.add(exitBtn);

        ButtonListener b1 = new ButtonListener();

        findUserBtn.addActionListener(b1);
        searchUserBtn.addActionListener(b1);
        insertUserBtn.addActionListener(b1);
        updateUserBtn.addActionListener(b1);
        deleteUserBtn.addActionListener(b1);
        exitBtn.addActionListener(b1);

        add(rightPanel, BorderLayout.EAST);

        dataList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    selectedUser = dataList.getSelectedValue();
                    if(selectedUser != null) {
                        System.out.println("선택된 유저 : " + selectedUser);
                    }
                }
            }
        });
    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JButton b = (JButton) e.getSource();

            if(b.getText().equals("유저 조회")) {
                listModel.clear();
                c.sendMsg(allViewTag + "//");
                System.out.println("[Client] 전체 유저 조회");
            }
            if(b.getText().equals("유저 검색")) {
                listModel.clear();
                c.suf.setVisible(true);
            }
            if(b.getText().equals("유저 추가")) {
                System.out.println("[Client] 회원 추가 인터페이스 열림");
                c.jf.setVisible(true);
            }
            if (b.getText().equals("유저 수정")) {
                if ("admin".equals(selectedUser)) {
                    JOptionPane.showMessageDialog(null, "Admin 계정은 수정하실 수 없습니다.", "유저 수정 실패", JOptionPane.ERROR_MESSAGE);
                } else if (selectedUser != null) {
                    c.sendMsg(chSIdTag + "//" + selectedUser);
                    c.cinf.setVisible(true);
                    System.out.println("[Client] 회원 정보 변경 인터페이스 열림");
                } else {
                    JOptionPane.showMessageDialog(null, "계정을 선택하삽시오.", "유저 수정 실패", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (b.getText().equals("유저 삭제")) {
                if ("admin".equals(selectedUser)) {
                    JOptionPane.showMessageDialog(null, "Admin 계정은 삭제하실 수 없습니다.", "유저 수정 실패", JOptionPane.ERROR_MESSAGE);
                } else if (selectedUser != null) {
                    c.sendMsg(unRegisterIdTag + "//" + selectedUser);
                } else {
                    JOptionPane.showMessageDialog(null, "계정을 선택하삽시오.", "유저 수정 실패", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (b.getText().equals("종료")) {
                System.out.println("[Client] 관리자 프레임 종료");
                c.sendMsg(pexitTag + "//"); // 서버에 프로그램 종료 태그 전송
                System.exit(0);
            }
        }
    }

    public void setMsg(String user) {
        listModel.addElement(user);
    }
}
