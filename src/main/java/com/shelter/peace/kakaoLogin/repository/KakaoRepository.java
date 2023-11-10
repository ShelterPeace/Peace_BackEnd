package com.shelter.peace.kakaoLogin.repository;

import com.shelter.peace.kakaoLogin.entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoRepository extends JpaRepository<KakaoUser,Long>{

}
