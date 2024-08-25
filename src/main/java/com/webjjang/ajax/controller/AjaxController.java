package com.webjjang.ajax.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.util.exe.Execute;

//Member Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class AjaxController {

	public String execute(HttpServletRequest request) {
		System.out.println("BoardController.execute() --------------------------");
		//login된 정보 중에서 id
		String id = null;
		String uri = request.getRequestURI();
		String jsp = null;
		//session 꺼내기
		HttpSession session = request.getSession();
		//로그인 정보 꺼내기
		LoginVO login= (LoginVO) session.getAttribute("login");
		if(login != null)id = login.getId();
		try { // 정상 처리
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			case "/ajax/checkId.do":
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
			case "/ajax/getNewMsgCnt.do":
				System.out.println("2.새로운 메세지 가져오기");
				
				// 데이터 수집(사용자->서버 : form - input - name)
				
				
				// [AjaxController] - MemberNewMsgCntService - MemberDAO.write(vo)
				Long result =(Long) Execute.execute(Init.get(uri), id);
				
				request.setAttribute("newMsgCnt", result);
				// jsp 
				// 아니면 jsp로 forward로 시킨다.
				jsp = "member/newMsgCnt";
				
				break;
			default:
				jsp = "error/noModule_404";
				break;
			} // end of switch
		} catch (Exception e) {
			request.setAttribute("e", e);
			
			jsp = "error/noModule_500";
		} // end of try~catch
		return jsp;
	} // end of execute()
	
} // end of class
