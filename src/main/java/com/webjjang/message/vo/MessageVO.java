package com.webjjang.message.vo;

import lombok.Data;

@Data
public class MessageVO {

	// private 변수
	private Long no;
	private String content;
	private String senderId;
	private String senderName;
	private String sendDate;
	private String accepterId;
	private String accepterName;
	private String acceptDate;
	private String acceptPhoto;
	private String sendPhoto;
	private int allUser; //0 - 개인,1 - 전체 메시지
	
	
}
