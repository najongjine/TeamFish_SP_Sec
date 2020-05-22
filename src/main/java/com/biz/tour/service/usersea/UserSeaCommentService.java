package com.biz.tour.service.usersea;

import java.util.List;

import com.biz.tour.domain.usersea.FishUserSeaCommentVO;

public interface UserSeaCommentService {

	public List<FishUserSeaCommentVO> findByFk(long ufc_fk, int pageno, int itemLimit);

	public int insert(FishUserSeaCommentVO commentVO, String username);
	
	public int countFindByFk(long ufc_fk);
}
