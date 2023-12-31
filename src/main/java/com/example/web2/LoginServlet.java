package com.example.web2;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 입력한 URI 내용을 user 테이블에서 조회할 변수 생성
        String uid = request.getParameter("uid");
        String upw = request.getParameter("upw");

        Connection conn = null; // DB 서버와 연결하는 객체
        //Statement stmt = null; // SQL을 실행하는 객체
        PreparedStatement psmt = null;
        ResultSet rs = null; // 실행 결과를 받아오는 객체
        try {
            String url = "jdbc:mysql://25.25.25.180:3306/jdbc";
            String id = "jdbctester";
            String pw = "qwer1234";
            conn = DriverManager.getConnection(url, id, pw);
//            stmt = conn.createStatement();

            // 입력한 URI 내용을 user 테이블에서 조회
            //String sql = "SELECT * FROM user WHERE id='"+uid+"' AND pw='"+upw+"'";

            // 여기부터 로그인 보안 설정
            String sql = "SELECT * FROM user WHERE id = ? AND pw = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, uid);
            psmt.setString(2, upw);

            rs = psmt.executeQuery();
            // 여기까지 로그인 보안 설정

            // 웹 브라우저에 한글 출력이 가능토록 설정
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");

            // 사용자에게 웹 브라우저에서 보여줄 메시지 작성
            PrintWriter out = response.getWriter();

            if(rs.next()) {
                    out.println("로그인에 성공하셨습니다.");
            } else {
                    out.println("로그인에 실패하셨습니다.");
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
