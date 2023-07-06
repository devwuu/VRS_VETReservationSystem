package com.web.vt.domain.reservation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = 562646985L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final com.web.vt.domain.common.QBaseEntity _super = new com.web.vt.domain.common.QBaseEntity(this);

    public final com.web.vt.domain.animal.QAnimal animal;

    public final com.web.vt.domain.clinic.QVeterinaryClinic clinic;

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath remark = createString("remark");

    public final DateTimePath<java.time.Instant> reservationDateTime = createDateTime("reservationDateTime", java.time.Instant.class);

    public final EnumPath<com.web.vt.domain.common.enums.ReservationStatus> status = createEnum("status", com.web.vt.domain.common.enums.ReservationStatus.class);

    //inherited
    public final DateTimePath<java.time.Instant> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animal = inits.isInitialized("animal") ? new com.web.vt.domain.animal.QAnimal(forProperty("animal"), inits.get("animal")) : null;
        this.clinic = inits.isInitialized("clinic") ? new com.web.vt.domain.clinic.QVeterinaryClinic(forProperty("clinic")) : null;
    }

}

