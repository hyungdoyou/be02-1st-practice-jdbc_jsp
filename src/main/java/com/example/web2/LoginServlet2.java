package com.example.web2;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

// hikari CP 이용한 커넥션 설정 코드
@WebServlet("/login2")
public class LoginServlet2 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HikariConfig config = new HikariConfig("/hikari.properties");
        HikariDataSource ds = new HikariDataSource(config);

        // 입력한 URI 내용을 user 테이블에서 조회할 변수 생성
        String uid = request.getParameter("uid");
        String upw = request.getParameter("upw");

        Connection conn = null; // DB 서버와 연결하는 객체
        //Statement stmt = null; // SQL을 실행하는 객체
        PreparedStatement psmt = null;
        ResultSet rs = null; // 실행 결과를 받아오는 객체

        try {
            conn = ds.getConnection();
            //ds.setJdbcUrl("jdbc:mysql://25.25.25.180:3306/jdbc");
            //ds.setUsername("jdbctester");
            //ds.setPassword("qwer1234");
            //ds.setMaximumPoolSize(6);

            String sql = "SELECT * FROM user WHERE id = ? AND pw = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, uid);
            psmt.setString(2, upw);

            rs = psmt.executeQuery();

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            if(rs.next()) {
                out.println("로그인에 성공하셨습니다.");
            } else {
                out.println("로그인에 실패하셨습니다.");
            }

            conn.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try{
                if(rs != null) {
                    rs.close();
                }
                if(psmt != null) {
                    psmt.close();
                }
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
