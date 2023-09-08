package com.animalplatform.platform.adopt.service;

import com.animalplatform.platform.adopt.dto.AddAdoptRequest;
import com.animalplatform.platform.adopt.dto.AddAdoptResponse;
import com.animalplatform.platform.adopt.entity.Adopt;
import com.animalplatform.platform.adopt.repository.AdoptRepository;
import com.animalplatform.platform.define.ReturnStatus;
import com.animalplatform.platform.define.RsResponse;
import com.animalplatform.platform.user.entity.User;
import com.animalplatform.platform.user.exception.UserException;
import com.animalplatform.platform.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class AdoptService {

    private final AdoptRepository adoptRepository;
    private final UserRepository userRepository;


    public RsResponse<Object> addAdopt(AddAdoptRequest addAdoptRequest) {

        //사용자가 존재하는지 확인
        User user = findUser(addAdoptRequest);

        Adopt adopt = addAdoptRequest.toEntity(user);
        adoptRepository.save(adopt);

        //입양글 혹은 분양글 등록
        AddAdoptResponse response = adopt.toAddAdoptResponse();

        return new RsResponse<>(ReturnStatus.SUCCESS, null, response);

    }

    private User findUser(AddAdoptRequest addAdoptRequest) {
        return userRepository.findByUserNo(addAdoptRequest.getUserNo()).orElseThrow(() -> new UserException(ReturnStatus.FAIL_NOT_FOUND_SELF_USER, String.format("userNo : %s", addAdoptRequest.getUserNo())));
    }

}
