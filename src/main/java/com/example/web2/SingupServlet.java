package com.example.web2;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/signup")
public class SingupServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 회원가입 시 입력할 변수 생성
        String uid = request.getParameter("uid");
        String upw = request.getParameter("upw");
        String name = request.getParameter("name");

        Connection conn = null; // DB 서버와 연결하는 객체
        Statement stmt = null; // SQL을 실행하는 객체
        ResultSet rs = null; // 실행 결과를 받아오는 객체
        try {
            String url = "jdbc:mysql://25.25.25.180:3306/jdbc";
            String id = "jdbctester";
            String pw = "qwer1234";
            conn = DriverManager.getConnection(url, id, pw);
            stmt = conn.createStatement();

            // 입력한 URI 내용을 user 테이블(속성 : id, pw, name)에 삽입
            String sql = "INSERT INTO user(id, pw, name) VALUES('"+uid+"', '"+upw+"', '"+name+"')";
            int result = stmt.executeUpdate(sql);

            // 웹 브라우저에 한글 출력이 가능토록 설정
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");

            // 사용자에게 웹 브라우저에서 보여줄 메시지 작성
            PrintWriter out = response.getWriter();
            if(result > 0 ) {
                out.print("회원가입에 성공하였습니다.");
            } else{
                out.print("잘못된 정보를 입력하셨습니다. 다시 입력해주세요");
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        // 서버에서 볼 메시지 작성
        System.out.println("새로운 사용자가 회원가입하였습니다.");
    }
}
