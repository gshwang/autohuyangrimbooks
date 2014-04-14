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
 *    Package명 : [ com.goldfish.huyangrim.autobook.client ]
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

package com.goldfish.huyangrim.autobook.client;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *
 * @author 황금석(gshwang@lgcns.com)
 * @version 
 */
public class AutoBooksClientTest {

	@Test
	public void testSubString() {
		String hrefStr = "<a href='http://www.huyang.go.kr/reservation/reservationSelectResult.action?outResult=SUCCESS&amp;outNotice=010113073035120'>here</a>";
		
		assertEquals("SUCCESS", hrefStr.substring(hrefStr.indexOf('=', 85)+1   ,hrefStr.indexOf('&')));
	}
	
	@Test
	public void testSubstring(){
		String fctlid = "F01010400500201";
		
		assertEquals("0101", fctlid.substring(1, 5));
	}

}
