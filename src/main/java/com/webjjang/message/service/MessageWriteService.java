package com.webjjang.message.service;

import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.vo.MessageVO;

public class MessageWriteService implements Service {

	private MessageDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (MessageDAO) dao;
	}
	@Override
	public Integer service(Object obj) throws Exception {
		MessageVO vo = (MessageVO) obj; //두번 세번 쓸 때 변수 선언해야됨 
		
		// DB 처리는 DAO에서 처리 - BoardDAO.write()
		// BoardController - (Execute) - [BoardListService] - BoardDAO.list()
		Integer result= dao.write((MessageVO)obj);
		
		//받는 사람의 아이디에 새로운 메시지 1증가 처리
		dao.increaseNewMsgCnt(vo.getAccepterId());
		
		return result;
	}

}
