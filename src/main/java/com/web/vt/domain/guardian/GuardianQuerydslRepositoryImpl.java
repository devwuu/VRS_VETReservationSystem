package com.web.vt.domain.guardian;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.vt.domain.common.dto.GuardianSearchCondition;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.web.vt.domain.guardian.QGuardian.guardian;

@RequiredArgsConstructor
public class GuardianQuerydslRepositoryImpl implements GuardianQuerydslRepository{

    private final JPAQueryFactory query;

    @Override
    public Page<GuardianVO> searchAll(GuardianSearchCondition condition, Pageable pageable) {

        BooleanExpression expression = whereWith(condition);

        List<GuardianVO> content = query
                .select(guardian)
                .from(guardian)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(guardian.createdAt.desc())
                .fetch().stream().map(GuardianVO::new).toList();

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> countAll(expression).fetchOne()
        );
    }

    private BooleanExpression whereWith(GuardianSearchCondition condition) {

        BooleanExpression expression = guardian.clinic.id.eq(condition.clinicId())
                .and(guardian.status.eq(UsageStatus.USE));

        if(StringUtil.isNotEmpty(condition.name())){
            expression = expression.and(guardian.name.like("%"+ condition.name()+"%"));
        }
        if(StringUtil.isNotEmpty(condition.contact())){
            expression = expression.and(guardian.contact.like("%"+ condition.contact()+"%"));
        }

        return expression;
    }

    private JPAQuery<Long> countAll(BooleanExpression expression) {
        JPAQuery<Long> count = query
                .select(guardian.count())
                .from(guardian)
                .where(expression);
        return count;
    }


}
