package com.biz.tour.controller.mypage;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.service.fileupload.FileUploadToServerService;
import com.biz.tour.service.member.MemberService;
import com.biz.tour.service.mypage.MyPageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/mypage")
@SessionAttributes("memberVO")
public class MyPageController {
	private final MyPageService mypageService;
	private final MemberService memberService;
	private final FileUploadToServerService fUploadService;
	
	@ModelAttribute("memberVO")
	public MemberVO makeMemVO() {
		return new MemberVO();
	}
	
	@Secured(value = {"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/view",method=RequestMethod.GET)
	public String view(Model model) {
		MemberVO memberVO=(MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		if(memberVO==null ) return null;
		
		memberVO.setPassword(null);// 보안때문에 VO에 담겨있는 패스워드값 더미용으로 덮어쓰기
		model.addAttribute("memberVO", memberVO);
		//마이페이지 보여줄 jsp
		return "mypage/mypage";
	}
	
	// 마이페이지 업데이트
	@Secured(value = {"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/update",method=RequestMethod.GET)
	public String update(Model model) {
		MemberVO memberVO=(MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		if(memberVO==null ) return null;
		log.debug("## profile pic in update get: "+memberVO.toString());
		
		memberVO.setPassword("password");// 보안때문에 VO에 담겨있는 패스워드값 더미용으로 덮어쓰기
		model.addAttribute("memberVO", memberVO);
		//마이페이지 수정 form 보여줄 jsp
		return "mypage/update";
	}
	
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	public String update(@ModelAttribute("memberVO")MemberVO memberVO,MultipartHttpServletRequest uploaded_files,SessionStatus session) {
		
		log.debug("## memberVO in update1: "+memberVO);
		MemberVO _tempVO=memberService.findByUName(memberVO.getUsername());
		if(_tempVO.getId()!=memberVO.getId()) return null;
		
//		log.debug("##post업데이트 진입");
		//update을 하고나서, update한 VO의 ID값을 받아와야함
//		int ret=memberService.update(memberVO);
		memberVO.setEnabled(true);
		int ret=mypageService.update(memberVO);
		
//		log.debug("##update 마침");
		long fk=memberVO.getId();
		log.debug("## memberVO in update2: "+memberVO);
		
		//update한 VO의 ID값을 파일업로드+DB 업데이트까지 동시에 수행해주는 method에 Id값 전달
		fUploadService.filesUp(uploaded_files, "tbl_members",fk);
//		log.debug("##파일 업로드 마침");
		
		session.setComplete();// 로그인 완료 후 session에 남아있는 id,비번 정보 초기화
		
		return "redirect:/mypage/view";
	}
	
//	@RequestMapping(value = "/resetpassword",method=RequestMethod.GET)
//	public String resetPassword() {
//		
//		//이메일, 유저네임 입력받는 form jsp
//		return "mypage/repassword";
//	}
//	
//	@RequestMapping(value = "/resetpassword",method=RequestMethod.POST)
//	public String resetPassword(String username,String email) {
////		MemberVO memberVO=memberService.findByUsernameNemail(username,email);
//		MemberVO memberVO=mypageService.findByUsernameNemail(username,email);
//		
//		//비밀번호 1111 로 만듬과 동시에 유저한테 메일도 같이 보내줘야함
//		int ret=memberService.resetPassword(memberVO);
//		
//		//이메일, 유저네임 입력받는 form jsp
//		return "redirect:/";
//	}
	
	// 마이페이지에서 비밀번호 변경 페이지
	@Secured(value = {"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/changepassword",method=RequestMethod.GET)
	public String changePassword(MemberVO memberVO) {
		memberVO = (MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		if(memberVO==null ) return null;
		
		// 비밀번호만 변경 입력받는 form jsp
		return "mypage/repassword";
	}
	
	@RequestMapping(value = "/changepassword",method=RequestMethod.POST)
	public String changePassword(MemberVO memberVO,SessionStatus session) {
		int ret=mypageService.changePassword(memberVO);
		
		session.setComplete();// 로그인 완료 후 session에 남아있는 id,비번 정보 초기화
		// 비밀번호만 변경 입력받는 form jsp
		return "redirect:/";
	}
	
	
//	/*
//	 *  ID찾기,비번 재설정 이메일 인증키 체크 완료 후 비번 재설정 메서드
//	 *  db에서 이메일로 검색 후 비번만 초기화 하고 vo에 담아서 보내온 vo를 
//	 *  re_join.jsp로 보내기 
//	 */
////	@ResponseBody
//	@RequestMapping(value="/re_join",method=RequestMethod.GET)
//	public String re_join(@ModelAttribute("memberVO")MemberVO memberVO, Model model) {
//		
//		MemberVO re_join = memberService.findByIdresetpass(memberVO);
//		
//		model.addAttribute("memberVO",re_join);
//		
//		return "mypage/re_join";
////		return re_join;
//	}
//	/*
//	 * ID찾기,비번 재설정 이메일 인증키 체크 완료 후 비번 재설정 메서드
//	 * re_join.jsp에서 비번 변경값 받아서 비번 변경 
//	 */
//	@RequestMapping(value="/re_join",method=RequestMethod.POST)
//	public String re_join(@ModelAttribute("memberVO")MemberVO memberVO, Model model,String email,SessionStatus session) {
//		
//		int ret = memberService.re_member_join(memberVO);
//		
//		session.setComplete();// 로그인 완료 후 session에 남아있는 id,비번 정보 초기화
//		
//		return "redirect:/";
//		
//	}
}
