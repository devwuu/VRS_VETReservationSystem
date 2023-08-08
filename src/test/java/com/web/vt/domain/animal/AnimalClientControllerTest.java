package com.web.vt.domain.animal;

import com.web.vt.common.ControllerTestSupporter;
import com.web.vt.domain.common.enums.Gender;
import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithUserDetails;

import static com.web.vt.common.RestDocsConfiguration.field;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(userDetailsServiceBeanName = "employeeDetailService", value = "test")
class AnimalClientControllerTest extends ControllerTestSupporter {

    @Test
    @DisplayName("새로운 반려동물을 등록합니다")
    public void save() throws Exception {

        AnimalVO vo = new AnimalVO()
                .name("달이")
                .age(10L)
                .species("강아지")
                .status(UsageStatus.USE)
                .clinicId(1L)
                .guardianId(1L)
                .gender(Gender.Female);

        mvc.perform(post("/v1/client/animal/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vo)))
                .andDo(
                        docs.document(
                            requestFields(
                                    fieldWithPath("id").ignored(),
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("반려동물 이름"),
                                    fieldWithPath("species").type(JsonFieldType.STRING).description("반려동물 종").optional(),
                                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("반려동물 나이").optional(),
                                    fieldWithPath("remark").type(JsonFieldType.STRING).description("비고").optional(),
                                    fieldWithPath("gender").type(JsonFieldType.STRING).description("반려동물 성별").attributes(field("constraints", "[ Female | Male | ETC ]")).optional(),
                                    fieldWithPath("status").type(JsonFieldType.STRING).attributes(field("constraints", "[ Use | NotUse | Deleted ]")).description("상태"),
                                    fieldWithPath("clinicId").type(JsonFieldType.NUMBER).description("동물병원 id"),
                                    fieldWithPath("guardianId").type(JsonFieldType.NUMBER).description("반려동물 보호자 id").optional(),
                                    fieldWithPath("createdAt").ignored(),
                                    fieldWithPath("updatedAt").ignored(),
                                    fieldWithPath("createBy").ignored(),
                                    fieldWithPath("updatedBy").ignored()
                            )
                        )
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    // todo gender를 필수값으로 할 것인지 optional로 할 것인지 검토 필요
    // db에는 enum converter대로 default value(etc)가 적용되는데 return 되는 entity, vo에는 null로 들어옴
    @Test @DisplayName("등록된 반려동물의 정보를 수정합니다")
    public void update() throws Exception {
        AnimalVO vo = new AnimalVO()
                .id(1L)
                .name("샛별")
                .status(UsageStatus.USE);

        mvc.perform(post("/v1/client/animal/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("반려동물 id"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("반려동물 이름").optional(),
                                        fieldWithPath("species").type(JsonFieldType.STRING).description("반려동물 종").optional(),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("반려동물 나이").optional(),
                                        fieldWithPath("remark").type(JsonFieldType.STRING).description("비고").optional(),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("반려동물 성별").attributes(field("constraints", "[ Female | Male | ETC ]")).optional(),
                                        fieldWithPath("status").type(JsonFieldType.STRING).attributes(field("constraints", "[ Use | NotUse | Deleted ]")).description("상태"),
                                        fieldWithPath("clinicId").ignored(),
                                        fieldWithPath("guardianId").type(JsonFieldType.NUMBER).description("반려동물 보호자 id").optional(),
                                        fieldWithPath("createdAt").ignored(),
                                        fieldWithPath("updatedAt").ignored(),
                                        fieldWithPath("createBy").ignored(),
                                        fieldWithPath("updatedBy").ignored()
                                )
                        )
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("등록된 반려동물을 삭제처리 합니다")
    public void delete() throws Exception {
        AnimalVO vo = new AnimalVO()
                .id(252L);

        mvc.perform(post("/v1/client/animal/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("반려동물 id"),
                                        fieldWithPath("name").ignored(),
                                        fieldWithPath("species").ignored(),
                                        fieldWithPath("age").ignored(),
                                        fieldWithPath("remark").ignored(),
                                        fieldWithPath("gender").ignored(),
                                        fieldWithPath("status").ignored(),
                                        fieldWithPath("clinicId").ignored(),
                                        fieldWithPath("guardianId").ignored(),
                                        fieldWithPath("createdAt").ignored(),
                                        fieldWithPath("updatedAt").ignored(),
                                        fieldWithPath("createBy").ignored(),
                                        fieldWithPath("updatedBy").ignored()
                                )
                        )
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("등록된 반려동물과 반려동물 보호자 리스트를 조회합니다")
    public void findAll() throws Exception {

        mvc.perform(
                RestDocumentationRequestBuilders.get("/v1/client/animal/all")
                    .param("page", "0")
                    .param("size", "2")
                    .param("clinicId", "1")
                )
                .andDo(
                        docs.document(
                                queryParameters(
                                        parameterWithName("page").attributes(field("type", "Number")).description("현재 페이지(index)"),
                                        parameterWithName("size").attributes(field("type", "Number")).description("한 번에 보여줄 content 갯수"),
                                        parameterWithName("clinicId").attributes(field("type", "Number")).description("동물병원 id")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("등록된 특정 반려동물과 반려동물 보호자를 조회합니다")
    public void findById() throws Exception {

        mvc.perform(RestDocumentationRequestBuilders.get("/v1/client/animal/{id}", "252"))
                .andDo(
                        docs.document(
                                pathParameters(
                                        parameterWithName("id").attributes(field("type", "Number")).description("반려동물 id")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("등록된 반려동물과 보호자 리스트에서 반려동물의 이름으로 검색합니다")
    public void searchByAnimalName() throws Exception {
        mvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/client/animal/search")
                                .param("page", "0")
                                .param("size", "2")
                                .param("clinicId", "1")
                                .param("animalName", "달")
                )
                .andDo(
                        docs.document(
                                queryParameters(
                                        parameterWithName("page").attributes(field("type", "Number")).description("현재 페이지(index)"),
                                        parameterWithName("size").attributes(field("type", "Number")).description("한 번에 보여줄 content 갯수"),
                                        parameterWithName("clinicId").attributes(field("type", "Number")).description("동물병원 id"),
                                        parameterWithName("animalName").attributes(field("type", "String")).description("반려동물 이름")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("등록된 반려동물과 보호자 리스트에서 보호자 이름으로 검색합니다")
    public void searchByGuardianName() throws Exception {
        mvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/client/animal/search")
                                .param("page", "0")
                                .param("size", "2")
                                .param("clinicId", "1")
                                .param("guardianName", "abc")
                )
                .andDo(
                        docs.document(
                                queryParameters(
                                        parameterWithName("page").attributes(field("type", "Number")).description("현재 페이지(index)"),
                                        parameterWithName("size").attributes(field("type", "Number")).description("한 번에 보여줄 content 갯수"),
                                        parameterWithName("clinicId").attributes(field("type", "Number")).description("동물병원 id"),
                                        parameterWithName("guardianName").attributes(field("type", "String")).description("보호자 이름")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}