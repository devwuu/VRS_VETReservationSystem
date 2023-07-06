package com.web.vt.domain.reservation.management;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservationManagement is a Querydsl query type for ReservationManagement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservationManagement extends EntityPathBase<ReservationManagement> {

    private static final long serialVersionUID = 1712780711L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservationManagement reservationManagement = new QReservationManagement("reservationManagement");

    public final com.web.vt.domain.common.QBaseEntity _super = new com.web.vt.domain.common.QBaseEntity(this);

    public final com.web.vt.domain.clinic.QVeterinaryClinic clinic;

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final DateTimePath<java.time.Instant> endDateTime = createDateTime("endDateTime", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> startDateTime = createDateTime("startDateTime", java.time.Instant.class);

    public final EnumPath<com.web.vt.domain.common.enums.UsageStatus> status = createEnum("status", com.web.vt.domain.common.enums.UsageStatus.class);

    //inherited
    public final DateTimePath<java.time.Instant> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QReservationManagement(String variable) {
        this(ReservationManagement.class, forVariable(variable), INITS);
    }

    public QReservationManagement(Path<? extends ReservationManagement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservationManagement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservationManagement(PathMetadata metadata, PathInits inits) {
        this(ReservationManagement.class, metadata, inits);
    }

    public QReservationManagement(Class<? extends ReservationManagement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clinic = inits.isInitialized("clinic") ? new com.web.vt.domain.clinic.QVeterinaryClinic(forProperty("clinic")) : null;
    }

}

