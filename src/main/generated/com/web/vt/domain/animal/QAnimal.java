package com.web.vt.domain.animal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimal is a Querydsl query type for Animal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimal extends EntityPathBase<Animal> {

    private static final long serialVersionUID = 1185287245L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnimal animal = new QAnimal("animal");

    public final com.web.vt.domain.common.QBaseEntity _super = new com.web.vt.domain.common.QBaseEntity(this);

    public final NumberPath<Long> age = createNumber("age", Long.class);

    public final com.web.vt.domain.clinic.QVeterinaryClinic clinic;

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final com.web.vt.domain.guardian.QGuardian guardian;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath remark = createString("remark");

    public final StringPath species = createString("species");

    public final EnumPath<com.web.vt.domain.common.enums.UsageStatus> status = createEnum("status", com.web.vt.domain.common.enums.UsageStatus.class);

    //inherited
    public final DateTimePath<java.time.Instant> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QAnimal(String variable) {
        this(Animal.class, forVariable(variable), INITS);
    }

    public QAnimal(Path<? extends Animal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnimal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnimal(PathMetadata metadata, PathInits inits) {
        this(Animal.class, metadata, inits);
    }

    public QAnimal(Class<? extends Animal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clinic = inits.isInitialized("clinic") ? new com.web.vt.domain.clinic.QVeterinaryClinic(forProperty("clinic")) : null;
        this.guardian = inits.isInitialized("guardian") ? new com.web.vt.domain.guardian.QGuardian(forProperty("guardian")) : null;
    }

}

