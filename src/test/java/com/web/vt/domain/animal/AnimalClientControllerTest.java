package com.web.vt.domain.animal;

import com.web.vt.common.RestDocsTestSupport;
import com.web.vt.domain.common.enums.Gender;
import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.web.vt.common.RestDocsConfiguration.field;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AnimalClientControllerTest extends RestDocsTestSupport {

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

        mvc.perform(post("/v1/animal/save")
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

        mvc.perform(post("/v1/animal/update")
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

        mvc.perform(post("/v1/animal/delete")
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

}