package com.web.vt.domain.user;

import com.web.vt.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public AdminVO findById(String id){
        Optional<Admin> find = adminRepository.findById(id);
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST USER");
        }
        return new AdminVO(find.get());
    }

}
