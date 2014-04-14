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
 *   ����ý��� : Smart Connect �ý���
 *
 *     Bundle�� : "autohuyangrimbooks"
 *
 *    Package�� : [ com.goldfish.huyangrim.main ]
 *
 *     Ŭ������ : [  ]
 *
 * ���̽�Ŭ���� : [  ]
 *
 *         ��� : [  ]
 *
 *         ���� : [  ] 
 *
 *       ����� : TYPE   X  FILE NAME               FILE DESC
 *              ------ -  ----------------------  ----------------------------------------------
 *              INPUT  F  N/A 
 *              OUTPUT F  N/A 
 *
 *
 *   References : 
 *		Interface ���輭 : N/A
 * 		���� �ڷ� (���� �������� �ڷ��� ��ġ ǥ��) : N/A
 *
 *      Special
 *        Logic : NONE
 *
 *************************************************************************************************
 *     �����̷� :
 *
 *   ��  ¥                       �� ��                   ����  ����
 * ----------     ---------  ------------------------------------------------------------------------
 * 2013/2013. 7. 30.  Ȳ�ݼ�          Initial Release
 *
 ************************************************************************************************/

package com.goldfish.huyangrim.main;

import com.goldfish.huyangrim.client.AutoBooksClient;

/**
 *
 * @author Ȳ�ݼ�(gshwang@lgcns.com)
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
			System.out.println("�Է¿�������....");
			System.out.println("��ũ��ȣ: " + autobooksclient.getFCLTID());
			System.out.println("��������: " + autobooksclient.getUSE_BGN_DTM());
			System.out.println("�����ϼ�: " + autobooksclient.getAVAIL_DAY());
			System.out.println("�������: " + autobooksclient.getPARK1());
			System.out.println("�α���ID: " + autobooksclient.getMEMBER_ID());
				
		}
		
		autobooksclient.accessHomeURL();
		if(autobooksclient.logonHuyangrimURL()>1){
			System.out.println("Login Success...");
		}else{
			System.out.println("Login Fail....");
			System.exit(1);
		}
		
		System.out.println("��ȸ����������.....");
		autobooksclient.accessSearchPage();
		while(true){
			
			try {
				Thread.sleep(RECONNECT_MILLIS);
				System.out.println("�ڵ����� ����.....");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
						
			if(autobooksclient.acessSelectPage()>0){
				System.out.println("���డ�ɻ���.. �����մϴ�..");
				autobooksclient.autoBooks();
				break;
			}else{
				System.out.println("���డ�ɻ��°� �ƴմϴ�.");
			}
		}		    
	}	

}
