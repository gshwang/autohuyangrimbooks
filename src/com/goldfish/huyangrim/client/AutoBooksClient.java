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
 *     Bundle명 : "com.camp.nationalpark.autobooks"
 *
 *    Package명 : [ com.camp.nationalpark.autobooks ]
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
 * 2013/2013. 7. 23.  황금석          Initial Release
 *
 ************************************************************************************************/

package com.goldfish.huyangrim.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.CookieStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.RedirectException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author 황금석(gshwang@lgcns.com)
 * @version 
 */
public class AutoBooksClient {
	private String MEMBER_PW = ""; //"07eva064520";
	private String MEMBER_ID = ""; //"java304";
	private String STR_USE_BGN_DTM = "2013년 09월 07일";
	private String USE_BGN_DTM =  ""; //"20130903";
	private String AVAIL_DAY = ""; //"20130902";
	private String FCLTID = ""; //"F01010400500201";
	private String AREA_CODE = "3000001"; //서울/경기
	
	static final String LOGON_SITE = "www.huyang.go.kr";
	static final int    LOGON_PORT = 80;
	private HttpClient httpclient = null;
	
	CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
	private Object jsessionId;
	private String PARK1 = "0";
	
	
	public void accessHomeURL() {
		httpclient  = new HttpClient();
		httpclient.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT, "http");
		httpclient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		
		GetMethod accessHomeGet = new GetMethod("http://www.huyang.go.kr/main.action");
		//GetMethod accessHomeGet = new GetMethod("http://www.huyang.go.kr");
		try {
			accessHomeGet.setRequestHeader(new Header("User-Agent"
					, "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"));
			httpclient.executeMethod(accessHomeGet);
			
		   // See if we got any cookies
           // The only way of telling whether logon succeeded is 
           // by finding a session cookie
           /*Cookie[] logoncookies = cookiespec.match(
             LOGON_SITE, LOGON_PORT, "/", false
             ,httpclient.getState().getCookies());
            System.out.println("Logon cookies:");
            
           if (logoncookies.length == 0) {
              System.out.println("None");    
           } else {
              for (int i = 0; i < logoncookies.length; i++) {
            	   System.out.println("- " + logoncookies[i].toString());  
             }
           }*/
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			accessHomeGet.releaseConnection();
		}
		
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int logonHuyangrimURL() {
		PostMethod authpost = new PostMethod("/member/memberLogin.action");
		NameValuePair rsa = new NameValuePair("RSA", "");
		NameValuePair mmberId = new NameValuePair("mmberId", MEMBER_ID);
		NameValuePair mmberPassword = new NameValuePair("mmberPassword", MEMBER_PW);
		authpost.setRequestBody(new NameValuePair[]{rsa,mmberId,mmberPassword});
		int status = 0;
		
		authpost.setRequestHeader(new Header("User-Agent"
									, "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"));
		
		GetMethod redirect = null;
		Document doc = null;
		
		try {
			status = httpclient.executeMethod(authpost);
			
			System.out.println("Login form post: " + authpost.getStatusLine().toString()); 
 
           // release any connection resources used by the method
           //authpost.releaseConnection();
 
           // See if we got any cookies
           // The only way of telling whether logon succeeded is 
           // by finding a session cookie
           Cookie[] logoncookies = cookiespec.match(
             LOGON_SITE, LOGON_PORT, "/", false
             ,httpclient.getState().getCookies());
           // System.out.println("Logon cookies:");
           //ACEFCID=UID-520C54828116BAC0175C6E33; 
           //elevisor_for_j2ee_uid=-2133439063344336143; 
           //popup417823=done; 
           //popup418233=done; 
           //JSESSIONID1=zkHbC4xeqaBPZHS6Z1wc0yN3uf1hAcsoa2hqtHdWAHyMyc7eS2dg1jEJE8zRXUVz.huyangrimwas2_servlet_engine3
            
           if (logoncookies.length == 0) {
              System.out.println("None");    
           } else {
              for (int i = 0; i < logoncookies.length; i++) {
            	   //System.out.println("- " + logoncookies[i].toString());  
        			jsessionId = logoncookies[i].toString();
		
             }
           }
         // Usually a successful form-based login results in a redicrect to 
         // another url
         int statuscode = authpost.getStatusCode();
         if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
             (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
             (statuscode == HttpStatus.SC_SEE_OTHER) ||
             (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
             Header header = authpost.getResponseHeader("Location");
             if (header != null) {
                 String newuri = header.getValue();
                 if ((newuri == null) || (newuri.equals(""))) {
                     newuri = "/";
                 }
                 System.out.println("Redirect target: " + newuri); 
                 redirect = new GetMethod("http://www.huyang.go.kr/main.action");
                 String cookieStr = jsessionId+"; ACEFCID=UID-51A2B29F10D49053A3597768";
                 redirect.setRequestHeader(new Header("User-Agent"
							, "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"));
                 redirect.setRequestHeader(new Header("Cookie",cookieStr));
                 httpclient.executeMethod(redirect);
                 // System.out.println("Redirect: " + redirect.getResponseBodyAsString());
                 
                 doc = Jsoup.parse(redirect.getResponseBodyAsString());
                 System.out.println(doc.getElementsByClass("btn_logout").size());
                		 
                 // release any connection resources used by the method
                 redirect.releaseConnection();
             } else {
                 System.out.println("Invalid redirect");
                 System.exit(1);
             }
         }
 

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
		}
		return doc.getElementsByClass("btn_logout").size();
	}

	/**
	 * 
	 */
	public void accessSearchPage() {
	    PostMethod searchPost = new PostMethod("/reservation/reservationSearch.action");
		String cookieStr = "WMONID=-q16Ooh-q26; "+jsessionId+"; ACEFCID=UID-51A2B29F10D49053A3597768";
		httpclient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

	    
		searchPost.setRequestHeader(new Header("User-Agent"
									, "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"));
		searchPost.setRequestHeader(new Header("Host", "www.huyang.go.kr"));
		searchPost.setRequestHeader(new Header("Accept"
									,"text/html, application/xhtml+xml, */*"));
		searchPost.setRequestHeader(new Header("Accept-Language","ko"));
		searchPost.setRequestHeader(new Header("Cache-Control","no-cache"));
		searchPost.setRequestHeader(new Header("Referer"
				                   ,"http://www.huyang.go.kr/reservation/reservationSearch.action"));
		//bookpost.setRequestHeader(new Header("Cookie", ))
		searchPost.setRequestHeader(new Header("Connection","keep-alive"));
		searchPost.setRequestHeader(new Header("Content-Type","application/x-www-form-urlencoded"));
		searchPost.setRequestHeader(new Header("Cookie",cookieStr));
		searchPost.setRequestHeader(new Header("Accept-Encoding","gzip, deflate"));
		
		try {
			httpclient.executeMethod(searchPost);
			//System.out.println("[accessSearchPage]["+searchPost.getStatusCode()+"]");
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			searchPost.releaseConnection();
		}
	}
	
	/**
	 * 
	 */	
	public int acessSelectPage() {
		PostMethod selectPost = new PostMethod("/reservation/reservationSelect.action");
		String cookieStr = "WMONID=-q16Ooh-q26; "+jsessionId+"; ACEFCID=UID-51A2B29F10D49053A3597768";
		httpclient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

	    
		selectPost.setRequestHeader(new Header("User-Agent"
									, "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"));
		selectPost.setRequestHeader(new Header("Host", "www.huyang.go.kr"));
		selectPost.setRequestHeader(new Header("Accept"
									,"text/html, application/xhtml+xml, */*"));
		selectPost.setRequestHeader(new Header("Accept-Language","ko"));
		selectPost.setRequestHeader(new Header("Cache-Control","no-cache"));
		selectPost.setRequestHeader(new Header("Referer"
				                   ,"http://www.huyang.go.kr/reservation/reservationSearch.action"));
		//bookpost.setRequestHeader(new Header("Cookie", ))
		selectPost.setRequestHeader(new Header("Connection","keep-alive"));
		selectPost.setRequestHeader(new Header("Content-Type","application/x-www-form-urlencoded"));
		selectPost.setRequestHeader(new Header("Cookie",cookieStr));
		selectPost.setRequestHeader(new Header("Accept-Encoding","gzip, deflate"));
		
		NameValuePair areaCode = new NameValuePair("areaCode", "3000001");
		//NameValuePair availDay = new NameValuePair("availDay", "20130926");
		NameValuePair fcltId = new NameValuePair("fcltId", FCLTID);
		NameValuePair useBgnDtm = new NameValuePair("useBgnDtm", USE_BGN_DTM);
		NameValuePair dprtmId = new NameValuePair("dprtmId", FCLTID.substring(1, 5));
		NameValuePair rsrvtQntt = new NameValuePair("rsrvtQntt", AVAIL_DAY);
		NameValuePair fcltMdclsCd = new NameValuePair("fcltMdclsCd", "04002");
		NameValuePair fcltMdclsCd2 = new NameValuePair("fcltMdclsCd", "04005");		
		NameValuePair strUseBgnDtm = new NameValuePair("strUseBgnDtm", STR_USE_BGN_DTM);
		NameValuePair waitState = new NameValuePair("waitState", "0");
		NameValuePair paramDprtmId = new NameValuePair("paramDprtmId", FCLTID.substring(1, 5));
		NameValuePair reserCheck = new NameValuePair("reserCheck", "Y");
		selectPost.setRequestBody(new NameValuePair[]{ 
				  									 areaCode
				  									//,availDay
				  									,dprtmId
													,fcltId
													,useBgnDtm
													,rsrvtQntt
													,dprtmId
													,fcltMdclsCd
													,fcltMdclsCd2
													,strUseBgnDtm
													,waitState
													,paramDprtmId
													,reserCheck
													,strUseBgnDtm
													});
		
		Document doc = null;
		try {
			httpclient.executeMethod(selectPost);
			//System.out.println(selectPost.getResponseBodyAsString());
			doc =Jsoup.parse( selectPost.getResponseBodyAsString());
			// System.out.println(doc.getElementsByAttributeValue("alt", "예약").toString());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
		return doc.getElementsByAttributeValue("alt", "예약").size();
		
		 
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int autoBooks() {
		
		Cookie[] cookies = httpclient.getState().getCookies();
	    //System.out.println("Before autoBooks cookie size::" + cookies.length);
	    String jsessionId = "";
		for(Cookie cookie : cookies){
			//System.out.println(cookie.toString());
			jsessionId = cookie.toString();
		}
		
		String cookieStr = "WMONID=-q16Ooh-q26; "+jsessionId+"; ACEFCID=UID-51A2B29F10D49053A3597768";
		httpclient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		
		
		int statuscode = 0;
		PostMethod bookpost = new PostMethod("/reservation/reservationSelectProc.action");
		bookpost.setRequestHeader(new Header("User-Agent"
									, "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"));
		bookpost.setRequestHeader(new Header("Host", "www.huyang.go.kr"));
		bookpost.setRequestHeader(new Header("Accept"
									,"text/html, application/xhtml+xml, */*"));
		bookpost.setRequestHeader(new Header("Accept-Language","ko"));
		bookpost.setRequestHeader(new Header("DNT","1"));
		bookpost.setRequestHeader(new Header("Cache-Control","no-cache"));
		bookpost.setRequestHeader(new Header("Referer"
				                   ,"http://www.huyang.go.kr/reservation/reservationSelect.action"));		
		bookpost.setRequestHeader(new Header("Connection","keep-alive"));
		bookpost.setRequestHeader(new Header("Content-Type","application/x-www-form-urlencoded"));
		bookpost.setRequestHeader(new Header("Cookie",cookieStr));
		bookpost.setRequestHeader(new Header("Accept-Encoding","gzip, deflate"));
		
		
		NameValuePair fcltId = new NameValuePair("fcltId", FCLTID);
		NameValuePair useBgnDtm = new NameValuePair("useBgnDtm", USE_BGN_DTM);
		NameValuePair rsrvtQntt = new NameValuePair("rsrvtQntt", AVAIL_DAY);
		NameValuePair paramDprtmId = new NameValuePair("paramDprtmId", FCLTID.substring(1, 5));
		NameValuePair fcltDfuseTpcd = new NameValuePair("fcltDfuseTpcd", "01");
		NameValuePair fcltRsrvtTpcd = new NameValuePair("fcltRsrvtTpcd", "01");
		NameValuePair park1 = new NameValuePair("park1", "0");
		NameValuePair park2 = new NameValuePair("park2", PARK1 );
		NameValuePair park3 = new NameValuePair("park3", "0");
		NameValuePair park0 = new NameValuePair("_park0", "on");
		NameValuePair agreeCheck = new NameValuePair("agreeCheck", "Y");
		NameValuePair setCk = new NameValuePair("setCk", "Y");
		bookpost.setRequestBody(new NameValuePair[]{fcltId
													,useBgnDtm
													,rsrvtQntt
													,paramDprtmId
													,fcltDfuseTpcd
													,fcltRsrvtTpcd
													,park1
													,park2
													,park3
													,park0
													,agreeCheck
													,setCk
													});
		Document doc = null;											
		try {
			httpclient.executeMethod(bookpost);
			statuscode = bookpost.getStatusCode();
			//System.out.println("bookpost status code::" + statuscode);
			//System.out.println(bookpost.getResponseBodyAsString());
			doc = Jsoup.parse(bookpost.getResponseBodyAsString());
			String hrefStr = doc.getElementsByAttribute("href").toString();
			System.out.println(hrefStr);
			//System.out.println(hrefStr.substring(hrefStr.indexOf('=', 85)+1   ,hrefStr.indexOf('&')));
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			bookpost.releaseConnection();
		}
		return statuscode;
	}
	
	

	public String getUSE_BGN_DTM() {
		return USE_BGN_DTM;
	}

	public void setUSE_BGN_DTM(String uSE_BGN_DTM) {
		USE_BGN_DTM = uSE_BGN_DTM;
	}

	public String getAVAIL_DAY() {
		return AVAIL_DAY;
	}

	public void setAVAIL_DAY(String aVAIL_DAY) {
		AVAIL_DAY = aVAIL_DAY;
	}

	public String getFCLTID() {
		return FCLTID;
	}

	public void setFCLTID(String fCLTID) {
		FCLTID = fCLTID;
	}

	public String getMEMBER_PW() {
		return MEMBER_PW;
	}

	public void setMEMBER_PW(String mEMBER_PW) {
		MEMBER_PW = mEMBER_PW;
	}

	public String getMEMBER_ID() {
		return MEMBER_ID;
	}

	public void setMEMBER_ID(String mEMBER_ID) {
		MEMBER_ID = mEMBER_ID;
	}

	public String getPARK1() {
		return PARK1;
	}

	public void setPARK1(String pARK1) {
		PARK1 = pARK1;
	}

	public String getAREA_CODE() {
		return AREA_CODE;
	}

	public void setAREA_CODE(String aREA_CODE) {
		AREA_CODE = aREA_CODE;
	}

	

    



	

}
