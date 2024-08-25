package com.webjjang.message.service;

import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.vo.MessageVO;

public class MessageViewService implements Service {

	private MessageDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (MessageDAO) dao;
	}

	@Override
	public MessageVO service(Object obj) throws Exception {
		
		//받은 메시지인 경우 (vo.accepterId !=null)받은 메시지 null이면 보낸 메시지 
		//처리 - 받은 날찌,새로운 메시지 
		MessageVO vo = (MessageVO) obj;
		Long no = vo.getNo();
		String id =vo.getAccepterId();
		
		//받은 메시지(vo.accepterId != null)인 경우의 처리
		if(vo.getAccepterId() != null) {
		//읽은 표시 처리
		//readed가 1이면 null->sysdate로 바뀜 , readed : 0 - acceptaDate 유지 
		int readed = dao.setReaded(no);
		if(readed ==1)dao.decreaseNewMsgCnt(id);
		}
		// DB 처리는 DAO에서 처리 - BoardDAO.list()
		// MessageController - (Execute) - [MessageViewService] - MessageDAO.view()
		return dao.view(no);
	}

}
