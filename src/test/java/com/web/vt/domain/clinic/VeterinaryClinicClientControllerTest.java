package com.web.vt.domain.clinic;

import com.web.vt.common.ControllerTestSupporter;
import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.web.vt.common.RestDocsConfiguration.field;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VeterinaryClinicClientControllerTest extends ControllerTestSupporter {

    @Test @DisplayName("영업 중인 특정 동물병원을 찾습니다.")
    public void find() throws Exception {
        String id = "202";
        mvc.perform(RestDocumentationRequestBuilders.get("/v1/client/clinic/{id}", id))
                .andDo(
                        docs.document(
                                pathParameters(
                                        parameterWithName("id").attributes(field("type", "Number")).description("동물병원 id")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("영업 중이지 않은 동물병원을 찾으면 not found로 응답받습니다")
    public void notFound() throws Exception {
        String id = "2";
        mvc.perform(RestDocumentationRequestBuilders.get("/v1/client/clinic/{id}", id))
                .andDo(
                        docs.document(
                                pathParameters(
                                        parameterWithName("id").attributes(field("type", "Number")).description("동물병원 id")
                                )
                        )
                )
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

        mvc.perform(post("/v1/client/clinic/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(
                        docs.document(
                            requestFields(
                                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("동물병원 id"),
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름").optional(),
                                    fieldWithPath("contact").type(JsonFieldType.STRING).description("연락처").optional(),
                                    fieldWithPath("remark").type(JsonFieldType.STRING).description("비고").optional(),
                                    fieldWithPath("status").type(JsonFieldType.STRING).attributes(field("constraints", "[ Use | NotUse | Deleted ]")).description("상태"),
                                    fieldWithPath("createdAt").ignored(),
                                    fieldWithPath("updatedAt").ignored(),
                                    fieldWithPath("createBy").ignored(),
                                    fieldWithPath("updatedBy").ignored()
                            )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

}