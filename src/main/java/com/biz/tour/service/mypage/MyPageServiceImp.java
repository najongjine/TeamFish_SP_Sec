package com.biz.tour.service.mypage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.tour.dao.member.AuthoritiesDao;
import com.biz.tour.dao.member.MemberDao;
import com.biz.tour.domain.member.AuthorityVO;
import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.service.mail.MailSendServiceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageServiceImp implements MyPageService {

	private final BCryptPasswordEncoder passwordEncoder;
	private final MemberDao memDao;
	private final MailSendServiceImp mailService;
	private final AuthoritiesDao authDao;

	@Override
	public int update(MemberVO memberVO) {
		// 업뎃 전 olduserVO 를 수정후 VO 내용으로 세팅해주고
		Authentication oldAuth = SecurityContextHolder.getContext().getAuthentication();
		MemberVO oldMemberVO = (MemberVO) oldAuth.getPrincipal();
		oldMemberVO.setEmail(memberVO.getEmail());
		oldMemberVO.setPhone(memberVO.getPhone());
		oldMemberVO.setAddress(memberVO.getAddress());
		oldMemberVO.setPoint(memberVO.getPoint());
		oldMemberVO.setU_date(memberVO.getU_date());
		oldMemberVO.setProfile_pic(memberVO.getProfile_pic());
		oldMemberVO.setEnabled(memberVO.isEnabled());
		oldMemberVO.setAuthorities(getAuthorities(memberVO.getUsername()));
		// --- oldMemberVO가 새로운 유저 정보로 업뎃 됬음
		

		// 기본 유저정보 DB에 업뎃 하고
		int ret = memDao.update(memberVO);

		if (ret > 0) {

			// 회원정보 update후, olduservo를 인자로 줘서 Principal에 담겨있는 정보도 새로 갱신
			// --- oldMemberVO가 새로운 유저 정보로 업뎃 됬음
			Authentication newAuth = new UsernamePasswordAuthenticationToken(oldMemberVO, oldAuth.getCredentials(),
					oldAuth.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}
		return ret;
	}

	@Override
	public MemberVO findByUsernameNemail(String username, String email) {

		return null;
	}

	@Override
	public int resetPassword(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int changePassword(MemberVO memberVO) {

		// DB에 저장
		String encPassword = passwordEncoder.encode(memberVO.getPassword());
		memberVO.setPassword(encPassword);

		int ret = memDao.pwupdate(memberVO);
	
		return ret;
	}
	
	private Collection<GrantedAuthority> getAuthorities(String username){
		List<AuthorityVO> authList=authDao.findByUserName(username);
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		
		for(AuthorityVO vo:authList) {// db에 저장된 권한 목록들을
			if(!vo.getAuthority().trim().isEmpty()) {
				//spring security용 auth list로 복사
				SimpleGrantedAuthority sAuth=new SimpleGrantedAuthority(vo.getAuthority());
				authorities.add(sAuth);
			}
		}
		return authorities;
	}

}
