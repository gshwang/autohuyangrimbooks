/************************************************************************************************
 *                                                                                               *
 *                  S M A R T    G R E E N    P L A T F O R M    S Y S T E M                     *
 *                                                                                               *
 *                                       LG CNS Co., Ltd.                                        *
 *                                   PUBLIC/SOC BIZ DIVISION                                     *
 *                                                                                               *
 *                  All rights reserved.  No part of this publication may be                     *
 *                  reproduced,  stored in a retrieval system  or transmitted                    *
 *                  in any form or by any means  -  electronic,  mechanical,                     *
 *                  photocopying, recording, or otherwise, without the prior                     *
 *                  written permission of LG CNS Co., Ltd.                                       *
 *                                                                                               *
 *************************************************************************************************
 *
 *   서브시스템 : Smart Connect 시스템
 *
 *     Bundle명 : "autohuyangrimbooks"
 *
 *    Package명 : [ com.goldfish.huyangrim.main ]
 *
 *     클래스명 : [  ]
 *
 * 베이스클래스 : [  ]
 *
 *         기능 : [  ]
 *
 *         설명 : [  ] 
 *
 *       입출력 : TYPE   X  FILE NAME               FILE DESC
 *              ------ -  ----------------------  ----------------------------------------------
 *              INPUT  F  N/A 
 *              OUTPUT F  N/A 
 *
 *
 *   References : 
 *		Interface 설계서 : N/A
 * 		참고 자료 (관련 프로토콜 자료의 위치 표기) : N/A
 *
 *      Special
 *        Logic : NONE
 *
 *************************************************************************************************
 *     수정이력 :
 *
 *   날  짜                       성 명                   수정  내용
 * ----------     ---------  ------------------------------------------------------------------------
 * 2013/2013. 7. 30.  황금석          Initial Release
 *
 ************************************************************************************************/

package com.goldfish.huyangrim.main;

import com.goldfish.huyangrim.client.AutoBooksClient;

/**
 *
 * @author 황금석(gshwang@lgcns.com)
 * @version 
 */
public class AutoBooksMain {

	private static final int RECONNECT_MILLIS = 1000*3;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AutoBooksClient autobooksclient = new AutoBooksClient();
		
		if(args.length<6){
			System.out.println("Usage: java -jar autohuyangrimbooks.jar F0101040xxxxxx 20130902 1 1 id pw");
			System.exit(1);
		}else{
			autobooksclient.setFCLTID(args[0]);
			autobooksclient.setUSE_BGN_DTM(args[1]);
			autobooksclient.setAVAIL_DAY(args[2]);
			autobooksclient.setPARK1(args[3]);
			autobooksclient.setMEMBER_ID(args[4]);
			autobooksclient.setMEMBER_PW(args[5]);
			System.out.println("입력예약정보....");
			System.out.println("데크번호: " + autobooksclient.getFCLTID());
			System.out.println("예약일자: " + autobooksclient.getUSE_BGN_DTM());
			System.out.println("예약일수: " + autobooksclient.getAVAIL_DAY());
			System.out.println("주차대수: " + autobooksclient.getPARK1());
			System.out.println("로그인ID: " + autobooksclient.getMEMBER_ID());
				
		}
		
		autobooksclient.accessHomeURL();
		if(autobooksclient.logonHuyangrimURL()>1){
			System.out.println("Login Success...");
		}else{
			System.out.println("Login Fail....");
			System.exit(1);
		}
		
		System.out.println("조회페이지접근.....");
		autobooksclient.accessSearchPage();
		while(true){
			
			try {
				Thread.sleep(RECONNECT_MILLIS);
				System.out.println("자동예약 시작.....");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
						
			if(autobooksclient.acessSelectPage()>0){
				System.out.println("예약가능상태.. 예약합니다..");
				autobooksclient.autoBooks();
				break;
			}else{
				System.out.println("예약가능상태가 아닙니다.");
			}
		}		    
	}	

}
