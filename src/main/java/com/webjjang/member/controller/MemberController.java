package com.webjjang.member.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.util.page.PageObject;
import com.webjjang.util.page.ReplyPageObject;
import com.webjjang.util.exe.Execute;

//Member Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class MemberController {

	public String execute(HttpServletRequest request) {
		System.out.println("BoardController.execute() --------------------------");
		//로그인 처리를 session으로 한다
		HttpSession session = request.getSession();
		
		//login된 정보 중에서 id
		String id = null;
		LoginVO login =(LoginVO) session.getAttribute("login");
		//login이 되어 있는 경우만 id를 꺼내 온다. 
		if(login != null) id = login.getId();
		
		// uri
		String uri = request.getRequestURI();
		Object result = null;
		
		String jsp = null;
		 session = request.getSession();
		//2.로그인 vo 끄네기
		LoginVO  loginVO = (LoginVO) session.getAttribute("login");
		//3.로그인한 경우에만 끄냄 
		id = null;
		if(loginVO !=null) id = loginVO.getId();
		
		//파일 업로드 설정 -------------------------
		//파일의 상대적인 저장 위치 
		String savePath = "upload/member";
		//파일 시스템에서는 절대 저장 위치가 필요하다. - 파일 업로드 시 필요
		// getServletContext-request>애플리케이션> getServletContext > getRealPath
		String realSavePath = request.getServletContext().getRealPath(savePath);
		
		System.out.println(realSavePath);
		//업로드 파일 용량 제한 
		int uploadFileSizeLimit = 100 * 1024 * 1024;
		File realSavePathFile = new File(realSavePath);
		if(!realSavePathFile.exists()) realSavePathFile.mkdirs();
		// 입력 받는 데이터 선언
		String encType="UTF-8";
	
		
		// 입력 받는 데이터 선언
		Long no = 0L;
		
		try { // 정상 처리
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			
			case "/member/loginForm.do":
				System.out.println("a. 로그인 폼");
				jsp="member/loginForm";
				break;
			case "/member/login.do":
				System.out.println("a-1 로그인 처리");
				
				// 데이터 수집(사용자->서버 : form - input - name)
				id = request.getParameter("id");
				String pw = request.getParameter("pw");
				
				// 변수 - vo 저장하고 Service
				LoginVO loginvo = new LoginVO();
				loginvo.setId(id);
				loginvo.setPw(pw);
			
				// [MemberController] - MemberWriteService - MemberDAO.write(vo)
				//session에 데이터를 담아서 로그인 처리한다. 
				session.setAttribute("login", Execute.execute(Init.get(uri), loginvo));
				
				// jsp 정보 앞에 "redirect:"가 붙어 있어 redirect를
				// 아니면 jsp로 forward로 시킨다.
				// 원래는 main이나 진행하려는 uri로 이동시킨다. 
				// 그러나 완성이 안되어 있었어 완성되어진 게시판 리스트로 보낸다.
				jsp = "redirect:/";
				//로그인 완료 메시지 처리 
				session.setAttribute("msg", "로그인 처리가 되었습니다.");
				break;
			case "/member/logout.do":
				System.out.println("a.로그아웃 처리");
				session.removeAttribute("login");
				jsp = "redirect:/";
				session.setAttribute("msg", "로그아웃 되었습니다.");
				break;
			case "/member/writeForm.do":
				System.out.println("3-1.일반게시판 글등록 폼");
				jsp="member/writeForm";
				break;
			case "/member/write.do":
				System.out.println("3.일반게시판 글등록 처리");
				
				// 데이터 수집(사용자->서버 : form - input - name)
				MultipartRequest multi = new MultipartRequest(
						request,realSavePath
						,uploadFileSizeLimit,encType
						,new DefaultFileRenamePolicy()); 

				id = multi.getParameter("id");
				pw =multi.getParameter("pw");
				String name = multi.getParameter("name");
				String gender = multi.getParameter("gender");
				String birth = multi.getParameter("birth");
				String tel = multi.getParameter("tel");
				String email = multi.getParameter("email");
				String imageName = multi.getFilesystemName("imageName");
				// 변수 - vo 저장하고 Service
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPw(pw);
				vo.setName(name);
				vo.setGender(gender);
				vo.setBirth(birth);
				vo.setTel(tel);
				vo.setEmail(email);
				if(!(imageName == null || imageName.equals("")))
				vo.setPhoto("/"+savePath + "/" +imageName);
				
				// [BoardController] - BoardWriteService - BoardDAO.write(vo)
				Execute.execute(Init.get(uri), vo);
				
				// jsp 정보 앞에 "redirect:"가 붙어 있어 redirect를
				// 아니면 jsp로 forward로 시킨다.
				jsp = "redirect:loginForm.do";
				session.setAttribute("msg", "회원가입이 정상적으로 처리 되었습니다.");
				break;
			case "/member/checkId.do":
				System.out.println("3.아이디 중복 체크");
				
				// 데이터 수집(사용자->서버 : form - input - name)
				id = request.getParameter("id");
				
				
				// [MemberController] - MemberWriteService - MemberDAO.write(vo)
				id =(String) Execute.execute(Init.get(uri), id);
				
				request.setAttribute("id", id);
				// jsp 
				// 아니면 jsp로 forward로 시킨다.
				jsp = "member/checkId";
				
				break;

				
			case "/member/updateForm.do":
				System.out.println("4-1.일반게시판 글수정 폼");
				
				// 사 -> 서버 : 글번호
				no = Long.parseLong(request.getParameter("no"));
				
				// no맞는 데이터 DB에서 가져온다. BoardViewService
				result = Execute.execute(Init.get("/member/view.do"),
						new Long[]{no, 0L});
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// jsp 정보
				jsp = "member/updateForm";
				
				break;
			case "/member/update.do":
				System.out.println("4-2.일반게시판 글수정 처리");
				
				// 데이터 수집(사용자->서버 : form - input - name)
//				no = Long.parseLong(request.getParameter("no"));
//				title = request.getParameter("title");
//				content = request.getParameter("content");
//				writer = request.getParameter("writer");
//				pw = request.getParameter("pw");
//				
				// 변수 - vo 저장하고 Service
//				vo = new BoardVO();
//				vo.setNo(no);
//				vo.setTitle(title);
//				vo.setContent(content);
//				vo.setWriter(writer);
//				vo.setPw(pw);
				
				// DB 적용하는 처리문 작성. BoardUpdateservice
//				Execute.execute(Init.get(uri), vo);
				
				// 페이지 정보 받기 & uri에 붙이기
//				pageObject = PageObject.getInstance(request);
//				// 글보기로 자동 이동 -> jsp 정보를 작성해서 넘긴다.
//				jsp = "redirect:view.do?no=" + no + "&inc=0"
//						+ "&" + pageObject.getPageQuery();
//				break;
			case "/member/delete.do":
				System.out.println("5.일반게시판 글삭제");
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글번호, 비밀번호 - BoardVO
				
				no = Long.parseLong(request.getParameter("no"));
				pw = request.getParameter("pw");
				
//				BoardVO deleteVO = new BoardVO();
//				deleteVO.setNo(no);
//				deleteVO.setPw(pw);
//				
//				// DB 처리
//				Execute.execute(Init.get(uri), deleteVO);
				System.out.println();
				System.out.println("***************************");
//				System.out.println("**  " + deleteVO.getNo()+ "글이 삭제되었습니다.  **");
				System.out.println("***************************");
				
				jsp ="redirect:list.do?perPageNum="
						+request.getParameter("perPageNum");
				
				break;
//			case "/member/update.do":
//				System.out.println("4-2.일반게시판 글수정 처리");
//				
//				// 데이터 수집(사용자->서버 : form - input - name)
////				no = Long.parseLong(request.getParameter("no"));
////				title = request.getParameter("title");
////				content = request.getParameter("content");
////				writer = request.getParameter("writer");
////				pw = request.getParameter("pw");
////				
//				// 변수 - vo 저장하고 Service
////				vo = new BoardVO();
////				vo.setNo(no);
////				vo.setTitle(title);
////				vo.setContent(content);
////				vo.setWriter(writer);
////				vo.setPw(pw);
//				
//				// DB 적용하는 처리문 작성. BoardUpdateservice
////				Execute.execute(Init.get(uri), vo);
//				
//				// 페이지 정보 받기 & uri에 붙이기
////				pageObject = PageObject.getInstance(request);
////				// 글보기로 자동 이동 -> jsp 정보를 작성해서 넘긴다.
////				jsp = "redirect:view.do?no=" + no + "&inc=0"
////						+ "&" + pageObject.getPageQuery();
////				break;
			case "/member/changeGrade.do":
				System.out.println("4-3 회원 등급 수정 처리");
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글번호, 비밀번호 - BoardVO
				
				id =request.getParameter("id");
				Integer gradeNo = Integer.parseInt(request.getParameter("gradeNo"));
				
				vo  = new MemberVO();
				vo.setId(id);
				vo.setGradeNo(gradeNo);
				
				// DB 처리
				Execute.execute(Init.get(uri), vo);
					
				PageObject pageObject = PageObject.getInstance(request);
				
				session.setAttribute("msg", "회원 ["+id+"]의 등급이 "+((gradeNo==1)?"일반회원":"관리자"));
				
				jsp = "redirect:list.do?"
						+ pageObject.getPageQuery();
				break;
			case "/member/changeStatus.do":
				System.out.println("4-3 회원 등급 수정 처리");
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글번호, 비밀번호 - BoardVO
				
				id =request.getParameter("id");
				String status = request.getParameter("status");
				
				vo  = new MemberVO();
				vo.setId(id);
				vo.setStatus(status);
				
				// DB 처리
				Execute.execute(Init.get(uri), vo);
				
				 pageObject = PageObject.getInstance(request);
				
				session.setAttribute("msg", "상태가 변경이 되었습니다.");
				
				jsp = "redirect:list.do?"
						+ pageObject.getPageQuery();
				break;

			case "/member/list.do": // 관리자만 가능하다.
				// [memberController] - (Execute) - memberListService - memberDAO.list()
				System.out.println("1.회원 리스트");
				// 페이지 처리를 위한 객체
				// getInstance - 기본 값이 있고 넘어오는 페이지와 검색 정보를 세팅 처리
				 pageObject = PageObject.getInstance(request);
				//id setting - 관리자 계정은 제외시키기 위해서 => accepter
				pageObject.setAccepter(id);
				// DB에서 데이터 가져오기 - 가져온 데이터는 List<BoardVO>
				result = Execute.execute(Init.get(uri), pageObject);
				
				// pageObject 데이터 확인
				System.out.println("MemberController.execute().pageObject = " + pageObject);
				// 가져온 데이터 request에 저장 -> jsp까지 전달된다.
				request.setAttribute("list", result);
				// pageObject 담기
				request.setAttribute("pageObject", pageObject);
				// /WEB-INF/views/ + member/list + .jsp
				jsp = "member/list";
				break;
			case "/member/view.do":
				System.out.println("2.일반게시판 글보기");
				// 1. 조회수 1증가(글보기), 2. 일반게시판 글보기 데이터 가져오기 : 글보기, 글수정
				// 넘어오는 글번호와 조회수 1증가를 수집한다.(request에 들어 있다.)
				String strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				String strInc = request.getParameter("inc");
				Long inc = Long.parseLong(strInc);
				// 전달 데이터 - 글번호, 조회수 증가 여부(1:증가, 0:증가 안함) : 배열 또는 Map
				result = Execute.execute(Init.get(uri),
						new Long[]{no, inc});
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// 댓글 페이지 객체
				// 데이터 전달 - page / perPageNum / no / replyPage / replyPerPageNum 
				ReplyPageObject replyPageObject 
					= ReplyPageObject.getInstance(request);
				request.setAttribute("replyList",
						Execute.execute(Init.get("/memberreply/list.do"),replyPageObject));
				
				request.setAttribute("replyPageObject", replyPageObject);
				jsp = "member/view";
				break;
			default:
				System.out.println("####################################");;
				System.out.println("## 잘못된 메뉴를 선택하셨습니다.          ##");;
				System.out.println("## [0~5, 0] 중에서 입력하셔야 합니다.    ##");;
				System.out.println("####################################");;
				break;
			} // end of switch
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			System.out.println();
			System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
			System.out.println("$%@ << 오류 출력 >>                         $%@");
			System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
			System.out.println("$%@ 타입 : " + e.getClass().getSimpleName());
			System.out.println("$%@ 내용 : " + e.getMessage());
			System.out.println("$%@ 조치 : 데이터를 확인 후 다시 실행해 보세요.");
			System.out.println("$%@     : 계속 오류가 나면 전산담당자에게 연락하세요.");
			System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
		} // end of try~catch
		return jsp;
	} // end of execute()
	
} // end of class
