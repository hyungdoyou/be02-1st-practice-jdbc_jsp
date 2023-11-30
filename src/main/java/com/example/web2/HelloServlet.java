package com.example.web2;

import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/yhd")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // request 변수에 클라이언트가 HTTP 프로토콜로 요청한 모든게 저장되어 있음
        // request 변수에 저장된 HttpServletRequest 객체에
        // getParameter() 메소드로 클라이언트가 보내준 데이터를 받아온다.
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
            String sql = "SELECT * FROM Student WHERE name='"+name+"'";
            rs = stmt.executeQuery(sql);

            // response 변수에 클라이언트에게 HTTP 프로토콜로 응답할 모든게 저장되어 있음
            // response 변수에 저장된 HttpServletResponse 객체에
            // getWriter() 메소드를 통해서 outputstream을 받아오고
            // 해당 스트림을 통해서 데이터를 출력하면
            // 클라이언트 웹 브라우저에 내용이 출력된다.
            PrintWriter out = response.getWriter();

            while(rs.next())
            {
                out.print(rs.getString("name"));
                out.print(" | ");
                out.print(rs.getInt("age"));
                out.print(" | ");
                out.print(rs.getString("gender"));
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        System.out.println("클라이언트가 웹 브라우저로 클래스 파일을 실행.");
    }
}