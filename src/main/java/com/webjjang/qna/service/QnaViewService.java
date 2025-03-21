package com.webjjang.qna.service;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.qna.dao.QnaDAO;
import com.webjjang.qna.vo.QnaVO;

public class QnaViewService implements Service {

	private QnaDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (QnaDAO) dao;
	}

	@Override
	public QnaVO service(Object obj) throws Exception {
		// QnaController - (Execute) - [QnaListService] - QnaDAO.view()
		return dao.view((Long) obj);
	}

}
