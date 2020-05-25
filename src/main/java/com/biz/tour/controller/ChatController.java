package com.biz.tour.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.tour.domain.member.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Controller
public class ChatController {
	
	@Secured(value = {"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "",method=RequestMethod.GET)
	public String chat(Model model) {
		MemberVO memberVO=(MemberVO) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		String u_name=memberVO.getUsername();
		model.addAttribute("U_NAME", u_name);
		return "chat/chat";
	}
}
