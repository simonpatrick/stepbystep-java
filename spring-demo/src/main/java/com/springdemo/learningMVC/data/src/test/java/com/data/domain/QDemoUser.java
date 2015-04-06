package com.springdemo.learningMVC.data.src.test.java.com.data.domain;

import com.data.qdomain.QAbstractVersioned;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.sql.PrimaryKey;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;
import java.sql.Types;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

/**
 * QDemoUser is a Querydsl query type for DemoUser.
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDemoUser extends QAbstractVersioned<DemoUser> {

    public static final String DEMO_USER_TABLE = "DEMO_USER";

    public static final QDemoUser qdu = new QDemoUser("du", "test_xa_1");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final StringPath username = createString("username");

    public final StringPath password = createString("password");

    public final PrimaryKey<DemoUser> primary = createPrimaryKey(userId);

    public QDemoUser(String variable) {
        super(DemoUser.class, forVariable(variable), "public", DEMO_USER_TABLE);
        addMetadatas();
    }

    @SuppressWarnings("all")
    public QDemoUser(Path<? extends DemoUser> path) {
        super((Class) path.getType(), path.getMetadata(), "public", DEMO_USER_TABLE);
        addMetadatas();
    }

    public QDemoUser(PathMetadata<?> metadata) {
        super(DemoUser.class, metadata, "public", DEMO_USER_TABLE);
        addMetadatas();
    }

    public QDemoUser(String variable, String scheme) {
        super(DemoUser.class, forVariable(variable), scheme, DEMO_USER_TABLE);
        addMetadatas();
    }

    private void addMetadatas() {
        addMetadata(userId, ColumnMetadata.named("USER_ID").ofType(Types.INTEGER));
        addMetadata(username, ColumnMetadata.named("USERNAME").ofType(Types.VARCHAR).withSize(50));
        addMetadata(password, ColumnMetadata.named("PASSWORD").ofType(Types.VARCHAR).withSize(50));
        addVersionedMetadata();
    }
}