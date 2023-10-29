package org.howWeather;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Frame {
    JTextField searchFld;
    JLabel mapLbl = new JLabel();
    Container frameCnt;

    public void initGUI() {
        JFrame frm = new JFrame("howWeather");                // 프레임 생성
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 프레임의 X 클릭 시 종료.
        frameCnt = frm.getContentPane();                     // JFrame 안쪽 영역.

        JPanel searchPan = new JPanel();
        searchFld = new JTextField(10);
        JButton searchBtn = new JButton("검색");                      // JFrame 안쪽 영역에 들어갈 클릭 버튼
        JButton filterBtn = new JButton("필터");
        searchPan.add(filterBtn);
        searchPan.add(searchFld);
        searchPan.add(searchBtn);
        searchBtn.addActionListener(new Search(this));              // pan에 생성한 버튼(searchBtn) 클릭 시 처리하는 이벤트 핸들러.
        filterBtn.addActionListener(new Filter(this));

        frameCnt.add(BorderLayout.WEST, mapLbl);                          // 센터 mapLbl 세팅
        frameCnt.add(BorderLayout.EAST, searchPan);                         // 상단 searchPan 세팅

        frm.setSize(1000, 660);
        frm.setVisible(true);

    }
}
