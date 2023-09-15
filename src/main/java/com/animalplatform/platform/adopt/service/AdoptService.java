package com.animalplatform.platform.adopt.service;

import com.animalplatform.platform.adopt.dto.AddAdoptRequest;
import com.animalplatform.platform.adopt.dto.AdoptResponse;
import com.animalplatform.platform.adopt.dto.DelAdoptRequest;
import com.animalplatform.platform.adopt.dto.ModAdoptRequest;
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

import java.util.ArrayList;
import java.util.List;


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
        //입양글 혹은 분양글 등록
        adoptRepository.save(adopt);

        AdoptResponse response = adopt.toAddAdoptResponse();

        return new RsResponse<>(ReturnStatus.SUCCESS, null, response);

    }

    public RsResponse<Object> getAdopt(Long adoptNo) {
        Adopt adopt = adoptRepository.findByAdoptNo(adoptNo).orElseThrow(() -> new UserException(ReturnStatus.FAIL_NOT_FOUND_SELF_USER, String.format("adoptNo : %s", adoptNo)));
        return new RsResponse<>(ReturnStatus.SUCCESS, null, adopt.toAddAdoptResponse());
    }

    public RsResponse<Object> getAdoptList() {
        List<Adopt> adoptList = adoptRepository.findAdoptAll();
        if(adoptList.isEmpty()) {
            return new RsResponse<>(ReturnStatus.FAIL_NOT_EXISTED_DATA, null, null);
        }

        List<AdoptResponse> adoptResponseList = getAdoptResponses(adoptList);

        return new RsResponse<>(ReturnStatus.SUCCESS, null, adoptResponseList);
    }

    public RsResponse<Object> updateAdopt(ModAdoptRequest request) {

        //사용자가 존재하는지 확인/
        //수정이 필요한 게시글이 존재하는지 확인
        Adopt adopt = findAdopt(request);
        adopt.updateAdopt(request);
        adoptRepository.save(adopt);
        AdoptResponse response = adopt.toAddAdoptResponse();

        return new RsResponse<>(ReturnStatus.SUCCESS, null, response);
    }

    public RsResponse<Object> deleteAdopt(DelAdoptRequest request) {
        Adopt adopt = adoptRepository.findByAdoptNo(request.getAdoptNo()).orElseThrow(() -> new UserException(ReturnStatus.FAIL_NOT_EXISTED_DATA, String.format("adoptNo : %s", request.getAdoptNo())));
        adopt.delete(adopt);
        adoptRepository.save(adopt);
        return new RsResponse<>(ReturnStatus.SUCCESS, null, null);
    }





    private Adopt findAdopt(ModAdoptRequest request) {
        return adoptRepository.findByAdoptNo(request.getAdoptNo()).orElseThrow(() -> new UserException(ReturnStatus.FAIL_NOT_EXISTED_DATA, String.format("adoptNo : %s", request.getAdoptNo())));
    }

    private User findUser(AddAdoptRequest addAdoptRequest) {
        return userRepository.findByUserNo(addAdoptRequest.getUserNo()).orElseThrow(() -> new UserException(ReturnStatus.FAIL_NOT_FOUND_SELF_USER, String.format("userNo : %s", addAdoptRequest.getUserNo())));
    }

    private static List<AdoptResponse> getAdoptResponses(List<Adopt> adoptList) {
        List<AdoptResponse> adoptResponseList = new ArrayList<>();
        for(Adopt adopt : adoptList) {
            adoptResponseList.add(adopt.toAddAdoptResponse());
        }
        return adoptResponseList;
    }
}
