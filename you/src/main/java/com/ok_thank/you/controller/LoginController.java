package com.ok_thank.you.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ok_thank.you.dto.Member;
import com.ok_thank.you.service.SecurityService;
 
@Controller
public class LoginController {
    
    @Autowired
    SecurityService securityService;
    
    //회원가입 페이지 이동
    @GetMapping("/join/join")
    public String join() {
        return  "login/join";
    }
    
    //로그인 페이지 이동
    @RequestMapping("/login/login")
    public ModelAndView lgoin(@RequestParam(value="msg", required=false) String msg) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg",msg);
        mv.setViewName("/login/login");
        return mv;
    }
    
    //로그인 에러페이지 이동
    @GetMapping("/login/login-error")
    public String error() {
        return  "login/error";
    }
    
    //아이디 중복체크
    @PostMapping("/join/idCheck")
    @ResponseBody
    public String idCheck(@RequestParam String inputId) throws Exception{
    	String canUse = "";
    	//입력 아이디 null체크(빈값이거나 null일 경우 keyup 이벤트에서 체크 완료되지 않도록)
    	if(StringUtils.isNotEmpty(inputId)) {
    		Member member = securityService.getSelectMeberInfo(inputId);
            canUse = member != null ? "" : "Y";
    	}
    	return canUse;
    }
    
    //회원가입 Insert 
    @PostMapping("/join/insert")
    public String setInsertMember(Member member) throws Exception{
        if(securityService.setInsertMember(member) > 0){
            return  "login/login";    
        }else {
            return  "join/join";
        }
    }
    
    //로그인 성공후 이동페이지
    @RequestMapping("/home")
    public String home() {
        return  "home/home";
    }
    
}


