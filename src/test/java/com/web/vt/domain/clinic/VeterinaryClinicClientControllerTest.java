package com.web.vt.domain.clinic;

import com.web.vt.common.RestDocsTestSupport;
import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.web.vt.common.RestDocsConfiguration.field;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VeterinaryClinicClientControllerTest extends RestDocsTestSupport {

    @Test @DisplayName("영업 중인 특정 동물병원을 찾습니다.")
    public void find() throws Exception {
        String id = "202";
        mvc.perform(get("/v1/clinic/"+id))
                .andDo(document("clinic/findByIdAndStatus",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("영업 중이지 않은 동물병원을 찾으면 not found로 응답받습니다")
    public void findTest2() throws Exception {
        String id = "2";
        mvc.perform(get("/v1/clinic/"+id))
                .andDo(document("clinic/findByIdAndStatus",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("동물병원 정보를 수정합니다.")
    public void update() throws Exception {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .id(352L)
                .name("Updated name")
                .remark("Updated remark")
                .status(UsageStatus.USE);

        mvc.perform(post("/v1/clinic/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(
                        docs.document(
                            requestFields(
                                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("동물병원 id"),
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름").optional(),
                                    fieldWithPath("contact").type(JsonFieldType.STRING).description("연락처").optional(),
                                    fieldWithPath("remark").type(JsonFieldType.STRING).description("비고").optional(),
                                    fieldWithPath("status").type(JsonFieldType.STRING).attributes(field("constraints", "[ Y | N | D ]")).description("상태(사용, 미사용, 삭제)"),
                                    fieldWithPath("createdAt").ignored(),
                                    fieldWithPath("updatedAt").ignored(),
                                    fieldWithPath("createBy").ignored(),
                                    fieldWithPath("updatedBy").ignored()
                            )
//                            responseFields(
//                                    fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
//                            )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

}