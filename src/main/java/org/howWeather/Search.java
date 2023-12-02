package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Search extends JPanel implements ActionListener {

    private Frame motherFrm;
    private JTextField searchFld = new JTextField(20);
    private JButton searchBtn = new JButton("검색");
    private JPanel infoPnl;
    private Stack<JPanel> infoPnlStack = new Stack<>();
    private JButton resetBtn = new JButton("초기화"); // 초기화 버튼 추가

    public Search(Frame frm){
        motherFrm = frm;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        searchBtn.addActionListener(this);
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        setPreferredSize(new Dimension(250,800));
        VisibleSearch();
    }

    public void VisibleSearch(){
        JPanel searchPnl = new JPanel();

        searchPnl.add(searchFld);
        searchPnl.add(searchBtn);
        searchPnl.add(resetBtn); // searchPnl에 초기화 버튼 추가
        searchBtn.setFont(new Font("맑은 고딕", Font.BOLD,13));
        resetBtn.setFont(new Font("맑은 고딕", Font.BOLD,13));

        add(searchPnl);

        setVisible(true);
    }


    public void pushinfoPnl(JPanel pnl){
        infoPnlStack.push(pnl);
        drawInfoPnl();
    }

    public void popinfoPnl(){
        infoPnlStack.pop();
    }

    public void drawInfoPnl(){
        if(infoPnl != null){
            clearinfoPnl();
        }
        infoPnl = infoPnlStack.peek();
        add(infoPnl);
        motherFrm.refresh();
    }

    public void clearinfoPnl(){
        if(infoPnl != null){
            remove(infoPnl);
            infoPnl = null;
        }
        motherFrm.refresh();
        if(!(infoPnlStack.empty())){
            drawInfoPnl();
        }
    }

    public void reset() {
        // 필터 초기화
        motherFrm.filter.uncheckAll();

        searchFld.setText("");

        // 결과 초기화
        infoPnlStack.clear();
        clearinfoPnl();

        // 지도 초기화
        NaverMap2.setBasicMap();
    }

    public void noneMassage(){
        JPanel nonePnl = new JPanel();
        nonePnl.add(new JLabel("결과가 없습니다."));
        pushinfoPnl(nonePnl);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String text = searchFld.getText();
        if(!(text.isEmpty())){
            motherFrm.filter.uncheckAll();
            infoPnlStack.clear();
            List<CourseData> list = DataBase.getCourseDataList(Long.parseLong(text));
            if(list.isEmpty()){
                noneMassage();
            } else{
                NaverMap2.setCourseMap(list);
                List<List<CourseData>> list2 = new ArrayList<List<CourseData>>();
                list2.add(list);
                CourseInfo courseInfo = new CourseInfo(this,list2);
                pushinfoPnl(courseInfo);
            }

        }
    }

}
