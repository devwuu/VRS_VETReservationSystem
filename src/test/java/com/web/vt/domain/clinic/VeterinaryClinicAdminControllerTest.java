package com.web.vt.domain.clinic;

import com.web.vt.common.RestDocsTestSupport;
import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class VeterinaryClinicAdminControllerTest extends RestDocsTestSupport {

    @Test @DisplayName("신규 동물병원을 등록합니다")
    public void saveTest() throws Exception {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .name("고양이 동물병원")
                .contact("0211112222")
                .remark("고양이 전문")
                .status(UsageStatus.USE);

        mvc.perform(post("/v1/admin/clinic/save")
                        .content(mapper.writeValueAsString(vo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(document("clinic/save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test @DisplayName("동물병원을 삭제(영업중지)합니다.")
    public void deleteTest() throws Exception {

        VeterinaryClinicVO vo = new VeterinaryClinicVO().id(352L);

        mvc.perform(post("/v1/admin/clinic/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(document("clinic/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("특정 동물병원을 조회합니다")
    public void findByIdTest() throws Exception {
        String id = "1";
        mvc.perform(get("/v1/admin/clinic/"+id))
                .andDo(document("clinic/findById",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("동물병원을 모두 가져옵니다.")
    public void findAllTest() throws Exception {

        mvc.perform(get("/v1/admin/clinic/all")
                        .param("page", "0")
                        .param("size", "2"))
                .andDo(document("clinic/all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isOk());
    }



}