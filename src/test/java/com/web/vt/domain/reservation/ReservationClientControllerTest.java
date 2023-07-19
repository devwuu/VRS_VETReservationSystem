package com.web.vt.domain.reservation;

import com.web.vt.common.RestDocsTestSupport;
import com.web.vt.domain.common.enums.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.web.vt.common.RestDocsConfiguration.field;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationClientControllerTest extends RestDocsTestSupport {

    private final Instant RESERVATION_DATE_TIME = LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant();

    @Test @DisplayName("새로운 예약을 등록합니다")
    public void save() throws Exception {
        ReservationVO vo = new ReservationVO()
                .clinicId(1L)
                .animalId(1L)
                .status(ReservationStatus.APPROVED)
                .reservationDateTime(RESERVATION_DATE_TIME);

        mvc.perform(
                    post("/v1/reservation/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo))
                )
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").ignored(),
                                        fieldWithPath("clinicId").type(JsonFieldType.NUMBER).description("동물병원 id"),
                                        fieldWithPath("animalId").type(JsonFieldType.NUMBER).description("반려동물 id"),
                                        fieldWithPath("reservationDateTime").type(JsonFieldType.STRING).attributes(field("constraints", "YYYY-MM-DDTMM:mm:ss.sssZ")).description("예약일시"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).attributes(field("constraints", "[Approved | Revoked]")).description("예약 상태(확정, 취소)"),
                                        fieldWithPath("remark").type(JsonFieldType.STRING).description("비고").optional(),
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

    @Test @DisplayName("등록된 예약의 정보를 변경합니다")
    public void update() throws Exception {
        ReservationVO vo = new ReservationVO()
                .id(202L)
                .status(ReservationStatus.REVOKED);

        mvc.perform(
                        post("/v1/reservation/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(vo))
                )
                .andDo(
                        docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("예약 id"),
                                        fieldWithPath("clinicId").ignored(),
                                        fieldWithPath("animalId").ignored(),
                                        fieldWithPath("reservationDateTime").type(JsonFieldType.STRING).attributes(field("constraints", "YYYY-MM-DDTMM:mm:ss.sssZ")).description("예약일시").optional(),
                                        fieldWithPath("status").type(JsonFieldType.STRING).attributes(field("constraints", "[Approved | Revoked]")).description("예약 상태(확정, 취소)"),
                                        fieldWithPath("remark").type(JsonFieldType.STRING).description("비고").optional(),
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

    @Test @DisplayName("등록된 특정 예약을 조회합니다")
    public void findById() throws Exception {
        mvc.perform(RestDocumentationRequestBuilders.get("/v1/reservation/{id}", 202))
                .andDo(
                        docs.document(
                                pathParameters(
                                       parameterWithName("id").attributes(field("type", "Number")).description("예약 id")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("등록된 예약 리스트를 조회합니다")
    public void findAll() throws Exception {
        mvc.perform(get("/v1/reservation/all")
                        .param("clinicId", "202")
                        .param("page", "0")
                        .param("size", "5")
                )
                .andDo(
                        docs.document(
                                queryParameters(
                                        parameterWithName("clinicId").attributes(field("type", "Number")).description("동물병원 id"),
                                        parameterWithName("page").attributes(field("type", "Number")).description("현재 페이지(index)"),
                                        parameterWithName("size").attributes(field("type", "Number")).description("한 번에 보여줄 content 갯수")
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }


}