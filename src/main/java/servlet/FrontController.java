package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public FrontController() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
		
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();//클라이언트가 요청한 uri
		System.out.println(uri);
		
		String context = request.getContextPath();
		System.out.println(context);
		
		String command = uri.substring(context.length());
		System.out.println(command);
		
		HttpSession session = null;
		
		if(command.equals("/loginOk.do")) {//로그인 확인 하는 일 진행
			request.setCharacterEncoding("utf-8");
			String mid = request.getParameter("mid");
			String mpw = request.getParameter("mpw");
			
			if(mid.equals("tiger") && mpw.equals("12345")) {
				session = request.getSession();//세션 초기화
				session.setAttribute("loginId", mpw);//로그인 성공 처리
				request.setAttribute("mid", mid);
				
				//request 객체 싣기 후 해당 페이지로 보내기
				request.getRequestDispatcher("loginSuccess.jsp").forward(request, response);
				
			} else {//로그인 실패
				request.setAttribute("mid", mid);
				
				//request 객체 싣기 후 해당 페이지로 보내기
				request.getRequestDispatcher("loginFail.jsp").forward(request, response);
				
			}
			
		} else if(command.equals("/join.do")) {//회원가입 하는 일 진행
			
		} else if(command.equals("/login.do")) {
			//login.do 요청 -> 클라이언트 			
			request.getRequestDispatcher("loginFail.jsp").forward(request, response);
		} else if(command.equals("/joinOk.do")) {
			//login.do 요청 -> 클라이언트 			
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
			request.getRequestDispatcher("loginFail.jsp").forward(request, response);
		}
	} 

}
