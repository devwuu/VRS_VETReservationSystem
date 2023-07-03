package com.web.vt.domain.clinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class VeterinaryClinicControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test @DisplayName("동물병원을 저장합니다")
    public void save() throws Exception {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .name("TEST")
                .contact("0211112222")
                .remark("TEST DATA");

        mvc.perform(post("/v1/rest/clinic/save")
                .content(mapper.writeValueAsString(vo))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(document("clinic/save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test @DisplayName("영업 중인 특정 동물병원을 찾습니다.")
    public void findTest() throws Exception {

        mvc.perform(get("/v1/rest/clinic")
                .param("id", "52"))
                .andDo(document("clinic/findByIdAndStatus",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test @DisplayName("동물병원 정보를 수정합니다.")
    public void updateTest() throws Exception {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .id(52L)
                .name("Updated")
                .remark("Updated");

        mvc.perform(post("/v1/rest/clinic/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(document("clinic/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test @DisplayName("동물병원을 삭제(영업중지)합니다.")
    public void deleteTest() throws Exception {

        VeterinaryClinicVO vo = new VeterinaryClinicVO().id(452L);

        mvc.perform(post("/v1/rest/clinic/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(document("clinic/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test @DisplayName("영업중인 동물병원을 모두 가져옵니다.")
    public void findAllTest() throws Exception {

        mvc.perform(get("/v1/rest/clinic/all")
                .param("page", "0")
                .param("size", "2"))
                .andDo(document("clinic/all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isOk());
    }



}