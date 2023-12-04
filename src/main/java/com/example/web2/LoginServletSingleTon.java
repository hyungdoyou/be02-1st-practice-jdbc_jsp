package com.example.web2;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

// 싱글톤 사용
@WebServlet("/logincp2")
public class LoginServletSingleTon extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String upw = request.getParameter("upw");


        Connection conn = null; // DB 서버와 연결하는 객체
        PreparedStatement pstmt = null;
        ResultSet rs = null; // 실행 결과를 받아오는 객체
        try {

            // 싱글톤 사용
            conn = DbConfig.getInstance().getConnection();
            String sql = "SELECT * FROM user WHERE id = ? AND pw = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uid);
            pstmt.setString(2, upw);

            rs = pstmt.executeQuery();

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");

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
        } finally {
            DbConfig.close(rs, pstmt, conn);
        }

    }
}
