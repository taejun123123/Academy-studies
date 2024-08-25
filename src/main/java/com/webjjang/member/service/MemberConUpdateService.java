package com.webjjang.member.service;

import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.member.dao.MemberDAO;

public class MemberConUpdateService implements Service {

	private MemberDAO dao;
	
	// dao setter
	public void setDAO(DAO dao) {
		this.dao = (MemberDAO) dao;
	}

	public Integer service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return dao.updateConDate((String) obj);
		
	}

}
