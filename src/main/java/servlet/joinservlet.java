package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


@WebServlet("/signup")
public class joinservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public joinservlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		String uname = request.getParameter("username");
		
		//회원가입 sql
		String sql = "INSERT INTO member(userid, userpw, username) VALUES(?,?,?)";
		
		//DB 커넥션 준비를 위한 4개의 변수
		//사용하려는 DB의 드라이버이름, DB 서버의 주소(URL), 계정아이디, 계정비번
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/boardtest";//DB이름(스키마 이름)
		String username = "root";
		String password = "12345";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driverName);//jdbc 드라이버 로등
			conn = DriverManager.getConnection(url, username, password);
			//DB와 jsp 서버간의 connection(연결통로)가 생성
			
			//만들어진 커넥션을 통해서 sql문을 실행시키기 위한 단계
			pstmt = conn.prepareStatement(sql);
			 
			//sql문의 ? 값 채우기
			pstmt.setString(1, userid);
			pstmt.setString(2, userpw);
			pstmt.setString(3, uname);
			
			int result = pstmt.executeUpdate();//sql문 실행
			//새로운 글작성 sql문이 실행 성공하면 정수 1의 반환
			
			if(result == 1) {
				System.out.println("회원 가입 성공!");
				request.setAttribute("uid", userid);
				request.setAttribute("upw", userpw);
				request.setAttribute("uname", uname);
				
				//request객체가 joinSuccess.jsp 전달이 안됨
				//전잘하는 값이 필요없이 다른 페이지로 이동하는 방법
				//response.sendRedirect("joinSuccess.jsp");
				
				//servlet이 현제 가지고 있는 request객체와 response객첼를 같이 joinSuccess.jsp로 보냄 
				request.getRequestDispatcher("joinSuccess.jsp").forward(request, response); ;
				
			} else {
				System.out.println("회원 가입 실패!");
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				} if(conn != null) {
					conn.close();
				}
				
			} catch(Exception e) {
				
			}
		}
		
	}

}
