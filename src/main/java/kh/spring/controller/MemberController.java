package kh.spring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.spring.dto.MemberDTO;
import kh.spring.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private MemberService serviceM;
	
	@RequestMapping("signup")
	public String signup() {
		return "member/signup";
	}
	
	@ResponseBody 
	@RequestMapping(value="duplCheck", produces="text/html;charset=utf8")
	public String duplCheck(String id) throws Exception {
		int result = serviceM.idCheck(id);
		return String.valueOf(result);   
	}
	
	@RequestMapping("signupProc")
	public String signupProc(MemberDTO dto) throws Exception{
		serviceM.join(dto);
		return "redirect:/";
	}
	
	@RequestMapping("loginProc")
	public String loginProc(String id, String pw) throws Exception{
		int result = serviceM.login(id,pw);
		if(result>0) {
			session.setAttribute("loginId", id);
			return "redirect:/member/main";
		}else {
			return "redirect:/";
		}
	}
	
	@RequestMapping("main")
	public String memberList(Model model) throws Exception{
		return "member/main";
	}
	
	//로그아웃
	@RequestMapping("logout")
	public String logout() {
		session.invalidate();
		return "redirect:/member/logoutComplete";
	}
	
	@GetMapping("logoutComplete")
	public String logoutCom() {
		return "member/logoutComplete";
	}
	
	//회원탈퇴
	@GetMapping("/signout")
	public String signout(String id) throws Exception {
		System.out.println(id);
		int result  = serviceM.signout(id);
		if(result > 0) {
			return "redirect:/";
		}
		return "/";
	}
	//마이페이지 이동
	@GetMapping("mypage")
	public String mypage(Model model) throws Exception {
		System.out.println("경로 확인");
		String id = (String) session.getAttribute("loginId");
		//이거 지금 값이 아예안나와연? 
		System.out.println(id);
		List<MemberDTO> mypage = serviceM.mypage(id);
		model.addAttribute("mypage",mypage);
			return "member/mypage";
		
	}
	
	@RequestMapping("back")
	public String back() {
		return "redirect:/";
	}
	
	
	
	@ExceptionHandler
	public String exceptionHandler(Exception e) {
		e.printStackTrace();
		return "error";
	}
}
