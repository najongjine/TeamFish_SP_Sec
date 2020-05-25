package com.biz.tour.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.tour.domain.UserSearchVO;
import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.domain.usersea.FishUserSeaCommentVO;
import com.biz.tour.domain.usersea.FishUserSeaPicsVO;
import com.biz.tour.domain.usersea.FishUserSeaVO;
import com.biz.tour.domain.util.PageDTO;
import com.biz.tour.service.fileupload.FileUploadToServerService;
import com.biz.tour.service.usersea.UserSeaCommentService;
import com.biz.tour.service.usersea.UserSeaPicsService;
import com.biz.tour.service.usersea.UserSeaService;
import com.biz.tour.service.util.GetCurrentDateService;
import com.biz.tour.service.util.PagiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes({"userSearchVO","userVO","commentVO"})
@RequestMapping(value = "/fishUserSea")
public class FishUserSeaController {
	int itemLimit = 10;
	private final UserSeaService uSeaService;
	private final UserSeaPicsService uSPicsService;
	private final FileUploadToServerService fUploadService;
	private final UserSeaCommentService uSCommentService;
	private final PagiService pagiService;
	private final GetCurrentDateService dateService;

	@ModelAttribute("userSearchVO")
	public UserSearchVO makeSearchVO() {
		UserSearchVO userSearchVO = new UserSearchVO();
		return userSearchVO;
	}

	@ModelAttribute("userVO")
	public FishUserSeaVO makeUserVO() {
		FishUserSeaVO userVO=new FishUserSeaVO();
		return userVO;
	}
	
	@ModelAttribute("commentVO")
	FishUserSeaCommentVO makeFishUserSeaCommentVO() {
		FishUserSeaCommentVO commentVO=new FishUserSeaCommentVO();
		return commentVO;
	}
	@RequestMapping(value = "/findAndShow", method = RequestMethod.GET)
	public String findAndShow(UserSearchVO userSearchVO,
			@RequestParam(value = "pageno", defaultValue = "1") String strPageno, Model model) {
		int pageno = Integer.valueOf(strPageno);
		int maxListSize=-1;
		List<FishUserSeaVO> userList = new ArrayList<FishUserSeaVO>();
		if (userSearchVO.getSearchOption().equalsIgnoreCase("titleSearch")) {
			maxListSize=uSeaService.countFindByTitle(userSearchVO.getInputStr());
			userList = uSeaService.findByTitle(userSearchVO.getInputStr(), (pageno-1)*itemLimit, itemLimit);
		} else {
			maxListSize=uSeaService.countFindAll();
			userList = uSeaService.findAll((pageno-1)*itemLimit,itemLimit);
		}
		if (!userList.isEmpty()) {
			try {
				for (FishUserSeaVO vo : userList) {
					if (vo.getPicsList().size() > 0 && vo.getPicsList() != null) {
						vo.setMainPic(vo.getPicsList().get(0).getUfp_uploadedFName());
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}// 메인 사진 고르기
		PageDTO pageDTO=pagiService.makePageNation(maxListSize, pageno, itemLimit);
		model.addAttribute("userSearchVO", userSearchVO);
		model.addAttribute("userList", userList);
		model.addAttribute("PAGE", pageDTO);
		model.addAttribute("MODE", "sea");
		return "fishing/userList";
	}

	@Secured(value = {"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/seaInsert", method = RequestMethod.GET)
	public String insert(Model model) {
		MemberVO memberVO=(MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		FishUserSeaVO userVO = new FishUserSeaVO();
		userVO.setUf_username(memberVO.getUsername());
		userVO.setUf_date(dateService.getCurDate());
		model.addAttribute("userVO", userVO);
		model.addAttribute("loggedName", memberVO.getUsername());
		model.addAttribute("MODE", "insert");
		return "fishing/userInput";
	}

	@Secured(value = {"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/seaInsert", method = RequestMethod.POST)
	public String insert(FishUserSeaVO userVO, MultipartHttpServletRequest uploaded_files,
			Model model) {
		MemberVO memberVO=(MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		int ret = uSeaService.insert(userVO, memberVO.getUsername());
		long fk=userVO.getUf_id();

		// 파일 업로드와 파일 이름을 DB에 저장을 같이함.
		fUploadService.filesUp(uploaded_files, "sea",fk);
		return "redirect:/fish/sea";
	}

	@Secured(value = {"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/seaUpdate", method = RequestMethod.GET)
	public String update(Model model, String strId) {
		MemberVO memberVO=(MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		Long uf_id = (long) -1;
		Long fk = (long) -1;
		try {
			uf_id = Long.valueOf(strId);
			fk = uf_id;
		} catch (Exception e) {
			log.debug("문자열 변환중 오류: " + uf_id);
		}

		FishUserSeaVO userVO = uSeaService.findById(uf_id);
		if(!userVO.getUf_username().equals(memberVO.getUsername())) {
			return null;
		}
		List<FishUserSeaPicsVO> picsList = uSPicsService.findByFK(fk);
		model.addAttribute("userVO", userVO);
		model.addAttribute("picsList", picsList);
		model.addAttribute("MODE", "sea");
		return "fishing/userInput";
	}

	@RequestMapping(value = "/seaUpdate", method = RequestMethod.POST)
	public String update(FishUserSeaVO userVO, MultipartHttpServletRequest uploaded_files, Model model,
			String strId) {
		MemberVO memberVO=(MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		FishUserSeaVO _tempVO=uSeaService.findById(userVO.getUf_id());
		if(!memberVO.getUsername().equals(_tempVO.getUf_username())) {
			log.debug("## 원글 작성자와 수정하려는 사람이 틀림");
			return null;
		}

		int ret=uSeaService.update(userVO);
		fUploadService.filesUp(uploaded_files, "sea",userVO.getUf_id());
		model.addAttribute("uf_id", userVO.getUf_id());
		return "redirect:/fishUserSea/view";
	}

	@RequestMapping(value = "/deletePic", method = RequestMethod.GET)
	public String deletePic(String strUfp_id, String strFk, Model model) {
		Long ufp_id = (long) -1;
		Long fk = (long) -1;
		try {
			ufp_id = Long.valueOf(strUfp_id);
			fk = Long.valueOf(strFk);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("문자열 변환중 오류: " + fk);
			log.debug("문자열 변환중 오류: " + ufp_id);
		}
		int ret = uSPicsService.deleteById(ufp_id);
		model.addAttribute("strId", fk);
		return "redirect:/fishUserSea/seaUpdate";
	}

	// FishUserSeaVO 글 삭제 + sub table 들 cascade delete 구현필요

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(FishUserSeaCommentVO commentVO,@RequestParam("uf_id") String strUf_id, Model model) {
		MemberVO memberVO=(MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		long uf_id = -1;
		try {
			uf_id = Long.valueOf(strUf_id);
		} catch (Exception e) {
			// TODO: handle exception
			log.debug("view 문자열 변환중 오류: " + uf_id);
		}
		FishUserSeaVO userVO = uSeaService.findById(uf_id);
		List<FishUserSeaPicsVO> picsList = uSPicsService.findByFK(uf_id);
		commentVO=makeFishUserSeaCommentVO();
		commentVO.setUfc_fk(uf_id);
		commentVO.setUfc_username(memberVO.getUsername());
		model.addAttribute("commentVO", commentVO);
		model.addAttribute("userVO", userVO);
		model.addAttribute("picsList", picsList);
		model.addAttribute("loggedName", memberVO.getUsername());
		model.addAttribute("MODE", "sea");
		return "fishing/userView";
	}

	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public String comments(@RequestParam("ufc_fk") String strFk,@RequestParam(value = "pageno", defaultValue = "1") String strPageno
			, Model model) {
		int pageno=Integer.valueOf(strPageno);
		long ufc_fk = -1;
		int maxListSize=-1;
		try {
			ufc_fk = Long.valueOf(strFk);
		} catch (Exception e) {
			// TODO: handle exception
			log.debug("cmt 문자열 변환중 오류: " + ufc_fk);
		}
		maxListSize=uSCommentService.countFindByFk(ufc_fk);
		List<FishUserSeaCommentVO> commentList = uSCommentService.findByFk(ufc_fk,(pageno-1),itemLimit);
		model.addAttribute("commentList", commentList);
		
		PageDTO pageDTO=pagiService.makePageNation(maxListSize, pageno, itemLimit);
		model.addAttribute("PAGE", pageDTO);
		return "fishing/userComment";
	}

	@RequestMapping(value = "/comments", method = RequestMethod.POST)
	public String comments(FishUserSeaCommentVO commentVO,@RequestParam(value = "pageno", defaultValue = "1") String strPageno
			, Model model) {
		MemberVO memberVO=(MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		int pageno=Integer.valueOf(strPageno);
		int maxListSize=-1;
		log.debug("## commentVO in post : "+commentVO.toString());
		int ret = uSCommentService.insert(commentVO,memberVO.getUsername());
		maxListSize=uSCommentService.countFindByFk(commentVO.getUfc_fk());
		List<FishUserSeaCommentVO> commentList = uSCommentService.findByFk(commentVO.getUfc_fk(),(pageno-1),itemLimit);
		model.addAttribute("commentList", commentList);
		
		PageDTO pageDTO=pagiService.makePageNation(maxListSize, pageno, itemLimit);
		model.addAttribute("PAGE", pageDTO);
		return "fishing/userComment";
	}

	@RequestMapping(value = "/replyForm", method = RequestMethod.GET)
	public String replyForm(@RequestParam("ufc_pid") String strPid, @RequestParam("ufc_fk") String strFK, Model model) {
		long ufc_pid = -1;
		long ufc_fk = -1;
		try {
			ufc_pid = Long.valueOf(strPid);
			ufc_fk = Long.valueOf(strFK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		FishUserSeaCommentVO vo = new FishUserSeaCommentVO();
		vo.setUfc_pid(ufc_pid);
		vo.setUfc_fk(ufc_fk);
		model.addAttribute("vo", vo);
		return "fishing/userReplyForm";
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.GET)
	public String delete(String strId) {
		boolean allow=false;
		MemberVO memberVO=(MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		long uf_id=Long.valueOf(strId);
		FishUserSeaVO userVO = uSeaService.findById(uf_id);
		if(memberVO.getUsername().equals(userVO.getUf_username())) allow=true;
		if(memberVO.getUsername().equals("admin")) allow=true;
		if(allow==false) return null;
		// ---------여기까지가 유저 게시물 삭제 보안 검증
		
		int ret=uSeaService.delete(uf_id);
		return "redirect:/";
	}
}
