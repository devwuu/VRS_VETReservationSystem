package com.web.vt.domain.guardian;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGuardian is a Querydsl query type for Guardian
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGuardian extends EntityPathBase<Guardian> {

    private static final long serialVersionUID = -200450067L;

    public static final QGuardian guardian = new QGuardian("guardian");

    public final com.web.vt.domain.common.QBaseEntity _super = new com.web.vt.domain.common.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath contact = createString("contact");

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath remark = createString("remark");

    public final EnumPath<com.web.vt.domain.common.enums.UsageStatus> status = createEnum("status", com.web.vt.domain.common.enums.UsageStatus.class);

    //inherited
    public final DateTimePath<java.time.Instant> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QGuardian(String variable) {
        super(Guardian.class, forVariable(variable));
    }

    public QGuardian(Path<? extends Guardian> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGuardian(PathMetadata metadata) {
        super(Guardian.class, metadata);
    }

}

