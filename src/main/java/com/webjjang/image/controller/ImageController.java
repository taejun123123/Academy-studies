package com.webjjang.image.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.util.page.PageObject;
import com.webjjang.util.exe.Execute;

// Board Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class ImageController {

	public String execute(HttpServletRequest request) {
		System.out.println("BoardController.execute() --------------------------");
		// uri
		String uri = request.getRequestURI();
		
		Object result = null;
		
		String jsp = null;
		
		//로그인 아이디 꺼내기  
		//1.세션끄네기
		HttpSession session = request.getSession();
		//2.로그인 vo 끄네기
		LoginVO  loginVO = (LoginVO) session.getAttribute("login");
		//3.로그인한 경우에만 끄냄 
		String id = null;
		if(loginVO !=null) id = loginVO.getId();
		
		//파일 업로드 설정 -------------------------
		//파일의 상대적인 저장 위치 
		String savePath = "upload/image";
		//파일 시스템에서는 절대 저장 위치가 필요하다. - 파일 업로드 시 필요
		// getServletContext-request>애플리케이션> getServletContext > getRealPath
		String realSavePath = request.getServletContext().getRealPath(savePath);
		System.out.println(realSavePath);
		//업로드 파일 용량 제한 
		int uploadFileSizeLimit = 100 * 1024 * 1024;
		// 입력 받는 데이터 선언
		String encType="UTF-8";
		
		Long no = 0L;
		
		try { // 정상 처리
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			case "/image/list.do":
				// [BoardController] - (Execute) - BoardListService - BoardDAO.list()
				System.out.println("1.이미지 게시판 리스트");
				// 페이지 처리를 위한 객체
				// getInstance - 기본 값이 있고 넘어오는 페이지와 검색 정보를 세팅 처리
				PageObject pageObject = PageObject.getInstance(request);
				// 이미지 게시판의 한페이지 이미지의 개수의 기본 값을 6으로 처리하자.
				// 중복처리를 하는 중 - 앞의 데이터에 덮어쓰기가 된다.
				String strPerPageNum = request.getParameter("perPageNum");
				if(strPerPageNum == null) pageObject.setPerPageNum(6);
				// DB에서 데이터 가져오기 - 가져온 데이터는 List<BoardVO>
				result = Execute.execute(Init.get(uri), pageObject);
				
				// pageObject 데이터 확인
				System.out.println("ImageController.execute().pageObject = " + pageObject);
				// 가져온 데이터 request에 저장 -> jsp까지 전달된다.
				request.setAttribute("list", result);
				// pageObject 담기
				request.setAttribute("pageObject", pageObject);
				// /WEB-INF/views/ + board/list + .jsp
				jsp = "image/list";
				break;
			case "/image/view.do":
				System.out.println("2.이미지게시판 글보기");
				// 1. 조회수 1증가(글보기), 2. 이미지게시판 글보기 데이터 가져오기 : 글보기, 글수정
				// 넘어오는 글번호와 조회수 1증가를 수집한다.(request에 들어 있다.)
				String strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				// 전달 데이터 - 글번호
				result = Execute.execute(Init.get(uri),
					no);
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				jsp = "image/view";
				break;
			case "/image/writeForm.do":
				System.out.println("3-1.이미지게시판 글등록 폼");
				jsp="image/writeForm";
				break;
			case "/image/write.do":
				System.out.println("3.이미지게시판 글등록 처리");
				
				// 이미지 업로드 처리 
				//정보가 필요함 사용할려면 MultipartRequest 
				// new MultipartRequest(request, 실제저장위치, 사이즈제한,
				// 			encoding, 중복처리객체)
				// request -> multi 바뀜
				//file 객체 업로드 시 input의 name이 같으면 한개만 처리 가능
				//name을 다르게 해서 올리세요.file1,file2
				MultipartRequest multi = new MultipartRequest(
						request,realSavePath
						,uploadFileSizeLimit,encType
						,new DefaultFileRenamePolicy()); 
				// 데이터 수집(사용자->서버 : form - input - name) :multi
				String title = multi.getParameter("title");
				String content = multi.getParameter("content");
				String fileName = multi.getFilesystemName("imageName");
				//id는 세션에서 받아야한다. ->위에서 했음 
				
				// 변수 - vo 저장하고 Service
				ImageVO vo = new ImageVO();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setId(id);
				// fileName은 위치 정보 + 파일명 
				vo.setFileName("/"+savePath + "/" +fileName);
				// [ImageController] - ImageWriteService - ImageDAO.write(vo)
				Execute.execute(Init.get(uri), vo);
				
				// 메시지 보내기
				session.setAttribute("msg", "이미지가 등록 되었습니다.");
				
				// jsp 정보 앞에 "redirect:"가 붙어 있어 redirect를
				// 아니면 jsp로 forward로 시킨다.
				jsp = "redirect:list.do?perPageNum=" 
						+ multi.getParameter("perPageNum");
				
				break;
			case "/image/updateForm.do":
				System.out.println("4-1.이미지게시판 글수정 폼");
				
				// 사 -> 서버 : 글번호
				no = Long.parseLong(request.getParameter("no"));
				
				// no맞는 데이터 DB에서 가져온다. BoardViewService 
				//위에 있는 uri이 틀리면 직접 써야한다.  << image/view.do
				result = Execute.execute(Init.get("/image/view.do"),no);
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// jsp 정보
				jsp = "image/updateForm";
				
				break;
			case "/image/update.do":
				System.out.println("4-2.이미지게시판 글수정 처리");
				
				// 데이터 수집(사용자->서버 : form - input - name)
				no = Long.parseLong(request.getParameter("no"));
				title = request.getParameter("title");
				content = request.getParameter("content");
				
				// 변수 - vo 저장하고 Service
				vo = new ImageVO();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setId(id); //session의 로그인 정보에서 꺼낸다.위쪽에 코드 참조 
				
				// DB 적용하는 처리문 작성. BoardUpdateservice
				Execute.execute(Init.get(uri), vo);
				
				//처리결과 메시지 처리 
				session.setAttribute("msg", "이미지 게시판 정보가 수정되었습니다.");
				
				// 페이지 정보 받기 & uri에 붙이기
				pageObject = PageObject.getInstance(request);
				// 글보기로 자동 이동 -> jsp 정보를 작성해서 넘긴다.
				jsp = "redirect:view.do?no=" + no + "&inc=0"
						+ "&" + pageObject.getPageQuery();
				break;
			case "/image/delete.do":
				System.out.println("5.이미지게시판 글삭제");
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글번호, 아이디 - ImageVO
				
				no = Long.parseLong(request.getParameter("no"));
				
				
				ImageVO deleteVO = new ImageVO();
				deleteVO.setNo(no);
				deleteVO.setId(id);
				// DB 처리
				Execute.execute(Init.get(uri), deleteVO);
				System.out.println();
				System.out.println("***************************");
				System.out.println("**  " + deleteVO.getNo()+ "글이 삭제되었습니다.  **");
				System.out.println("***************************");
				
				//파일 삭제 
				/*String deleteFile = request.getParameter("dleteFileName");
				 * 
				 * */
				//삭제할 파일 이름 
				String deleteFileName = request.getParameter("deleteFileName");
				File deleteFile =  new File(request.getServletContext()
				.getRealPath(deleteFileName));
				if(deleteFile.exists())
				deleteFile.delete();
				//여기 까지 파일 삭제 
				
				// 메시지 처리 
				session.setAttribute("msg", "삭제가 정상적으로 처리되었습니다");
				
				
				jsp ="redirect:list.do?perPageNum="
						+request.getParameter("perPageNum");
				
				break;
			case "/image/changeImage.do":
				System.out.println("6.이미지게시판 바꾸기 처리");
				
				//파일 업로드 해야한다 cos - Multipart
				//정보가 필요함 사용할려면 MultipartRequest 
				// new MultipartRequest(request, 실제저장위치, 사이즈제한,
				// 			encoding, 중복처리객체)
				// request -> multi 바뀜
				//file 객체 업로드 시 input의 name이 같으면 한개만 처리 가능
				//name을 다르게 해서 올리세요.file1,file2
				multi = new MultipartRequest(
						request,realSavePath
						,uploadFileSizeLimit,encType
						,new DefaultFileRenamePolicy()); 				
				
				// 데이터 수집(사용자->서버 : form - input - name)
				no = Long.parseLong(multi.getParameter("no"));
				fileName = multi.getFilesystemName("imageName");
				
				 deleteFileName = multi.getParameter("deleteFileName");
				
				// 변수 - vo 저장하고 Service : db에 처리할 데이터만 
				vo = new ImageVO();
				vo.setNo(no);
				vo.setFileName("/"+savePath +"/"+fileName);
				
				// DB 적용하는 처리문 작성. BoardUpdateservice
				Execute.execute(Init.get(uri), vo);
				
				//지난 이미지 파일이 존재하면 지운다 
				deleteFile = new File(request.getServletContext()
						.getRealPath(deleteFileName));
				if(deleteFile.exists())
				deleteFile.delete();
				
				session.setAttribute("msg", "이미지가 잘 처리 되었습니다.");
				
				// 페이지 정보 받기 & uri에 붙이기
				pageObject = PageObject.getInstance(request);
				// 글보기로 자동 이동 -> jsp 정보를 작성해서 넘긴다.
				jsp = "redirect:view.do?no=" + no  
						+ "&" + pageObject.getPageQuery();
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
