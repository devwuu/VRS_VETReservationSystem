package com.web.vt.domain.clinic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVeterinaryClinic is a Querydsl query type for VeterinaryClinic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVeterinaryClinic extends EntityPathBase<VeterinaryClinic> {

    private static final long serialVersionUID = 1633089342L;

    public static final QVeterinaryClinic veterinaryClinic = new QVeterinaryClinic("veterinaryClinic");

    public final com.web.vt.domain.common.QBaseEntity _super = new com.web.vt.domain.common.QBaseEntity(this);

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

    public QVeterinaryClinic(String variable) {
        super(VeterinaryClinic.class, forVariable(variable));
    }

    public QVeterinaryClinic(Path<? extends VeterinaryClinic> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVeterinaryClinic(PathMetadata metadata) {
        super(VeterinaryClinic.class, metadata);
    }

}

