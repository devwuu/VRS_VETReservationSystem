package com.web.vt.domain.clinic;

import com.web.vt.common.RestDocsTestSupport;
import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.web.vt.common.RestDocsConfiguration.field;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class VeterinaryClinicAdminControllerTest extends RestDocsTestSupport {

    @Test @DisplayName("신규 동물병원을 등록합니다")
    public void save() throws Exception {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .name("고양이 동물병원")
                .contact("0211112222")
                .remark("고양이 전문")
                .status(UsageStatus.USE);

        mvc.perform(post("/v1/admin/clinic/save")
                        .content(mapper.writeValueAsString(vo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).ignored(),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
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
                .andExpect(status().isCreated());
    }

    @Test @DisplayName("동물병원을 삭제(영업중지)합니다.")
    public void delete() throws Exception {

        VeterinaryClinicVO vo = new VeterinaryClinicVO().id(352L);

        mvc.perform(post("/v1/admin/clinic/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("동물병원 id"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).ignored(),
                                        fieldWithPath("contact").type(JsonFieldType.STRING).ignored(),
                                        fieldWithPath("remark").type(JsonFieldType.STRING).ignored(),
                                        fieldWithPath("status").type(JsonFieldType.STRING).ignored(),
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

    @Test @DisplayName("특정 동물병원을 조회합니다")
    public void findById() throws Exception {
        String id = "1";
        mvc.perform(RestDocumentationRequestBuilders.get("/v1/admin/clinic/{id}", id))
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

    @Test @DisplayName("동물병원을 리스트를 조회합니다")
    public void findAll() throws Exception {

        mvc.perform(get("/v1/admin/clinic/all")
                        .param("page", "0")
                        .param("size", "2"))
                .andDo(
                        docs.document(
                                queryParameters(
                                        parameterWithName("page").attributes(field("type", "Number")).description("현재 페이지(index)"),
                                        parameterWithName("size").description("한 번에 보여줄 content 갯수")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }



}