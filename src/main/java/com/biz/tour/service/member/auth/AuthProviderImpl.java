package com.biz.tour.service.member.auth;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.biz.tour.dao.member.MemberDao;
import com.biz.tour.domain.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthProviderImpl implements AuthenticationProvider {
	/*
	 * 서비스 중에서 @service (" " ) 안에 userDetailsService 라고 적혀져있는 서비스를 가져와라
	 */
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDService;

	@Autowired
	private MemberDao memDao;

	/*
	 * spring sec에 bean으로 등록되어있음
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * spring sec에서 customize 수행하여 login을 세세히 제어하고자 할때 코드를 작성해야하는 중요한 method
	 * 
	 * login을 하고나면 제일먼저 여기로 옴
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		/*
		 * authentication으로부터 로그인 폼에서 보낸 username과 password를 추출
		 */
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		// service->dao를 통해서 DB로부터 사용자 정보 가져오기
		MemberVO memberVO = (MemberVO) userDService.loadUserByUsername(username);
		if (!passwordEncoder.matches(password.trim(), memberVO.getPassword().trim())) {
			throw new BadCredentialsException("비밀번호 오류");
		}
		if (!memberVO.isEnabled()) {
			throw new BadCredentialsException(username + ": 접근권한 없음");
		}

		// 년 월 일 변수 생성
		SimpleDateFormat curDate = new SimpleDateFormat("yyyy-MM-dd");

		// 현재 날짜 데이터 생성 후 date 변수에 주입
		Date date = new Date();
		log.debug("date : " + date);

		// strcurDate 변수에 년월일 방식으로 현재 날짜 데이터를 주입
		String strcurDate = curDate.format(date);

		/*
		 * 현재 날짜와 u_date(마지막 로그인 날짜)가 틀리면 포인트 +1 같다면 오늘 로그인 했었단 뜻이니 포인트 중복 방지하기 위함
		 */
		if (!strcurDate.equals(memberVO.getU_date())) {
			log.debug("현재 포인트 : " + memberVO.getPoint());// 테스트용 로그
			int intPoint = memberVO.getPoint();
			log.debug("intPoint : " + intPoint);// 테스트용 로그
			// null이면 회원가입 후 최초 로그인
			if (memberVO.getU_date() == null) {
				intPoint = intPoint + 100;
				memberVO.setPoint(intPoint);
				log.debug("최초 로그인 포인트 + 100  : " + memberVO.getPoint());// 테스트용 로그

				// VO에 strcurDate 셋팅
				memberVO.setU_date(strcurDate);

				// u_date,point 칼럼만 업데이트(VO)
				memDao.date_update(memberVO);

			}
			// 포인트 + 1 해서 VO에 point 칼럼에 셋팅
			intPoint = intPoint + 1;
			memberVO.setPoint(intPoint);
			log.debug("일일 로그인 포인트 + 1 : " + memberVO.getPoint());// 테스트용 로그

			// VO에 strcurDate 셋팅
			memberVO.setU_date(strcurDate);

			// u_date,point 칼럼만 업데이트(VO)
			memDao.date_update(memberVO);
		} // point 적립 끝

		/*
		 * // 업뎃 전 olduserVO 를 수정후 VO 내용으로 세팅해주고 Authentication oldAuth =
		 * SecurityContextHolder.getContext().getAuthentication(); MemberVO oldMemberVO
		 * = (MemberVO) oldAuth.getPrincipal();
		 * oldMemberVO.setEmail(memberVO.getEmail());
		 * oldMemberVO.setPhone(memberVO.getPhone());
		 * oldMemberVO.setAddress(memberVO.getAddress());
		 * oldMemberVO.setPoint(memberVO.getPoint());
		 * oldMemberVO.setU_date(memberVO.getU_date()); // --- oldMemberVO가 새로운 유저 정보로
		 * 업뎃 됬음
		 * 
		 * // 회원정보 update후, olduservo를 인자로 줘서 Principal에 담겨있는 정보도 새로 갱신 // ---
		 * oldMemberVO가 새로운 유저 정보로 업뎃 됬음 Authentication newAuth = new
		 * UsernamePasswordAuthenticationToken(oldMemberVO, oldAuth.getCredentials(),
		 * oldAuth.getAuthorities());
		 * SecurityContextHolder.getContext().setAuthentication(newAuth);
		 */
		// UserDetailsService에서 보내준 사용자 정보를 Controller로 보내는 일을 수행
		return new UsernamePasswordAuthenticationToken(memberVO, null, memberVO.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
}
