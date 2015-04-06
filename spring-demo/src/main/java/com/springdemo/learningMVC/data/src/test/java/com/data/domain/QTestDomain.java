package com.springdemo.learningMVC.data.src.test.java.com.data.domain;

import com.data.qdomain.QAbstractRecording;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.sql.PrimaryKey;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.SimplePath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;
import java.sql.Types;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

/**
 * QTestDomain is a Querydsl query type for TestDomain.
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestDomain extends QAbstractRecording<TestDomain> {

    public static final String TEST_DOMAIN_TABLE = "TEST_DOMAIN";

    public static final QTestDomain qTestDomain = new QTestDomain("td");

    public static final QTestDomain qtd = new QTestDomain("td", "test_xa_2");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final SimplePath<TUserStatus> status = createSimple("status", TUserStatus.class);

    public final PrimaryKey<TestDomain> primary = createPrimaryKey(id);

    public QTestDomain(String variable) {
        super(TestDomain.class, forVariable(variable), "public", TEST_DOMAIN_TABLE);
        addMetadatas();
    }

    @SuppressWarnings("all")
    public QTestDomain(Path<? extends TestDomain> path) {
        super((Class) path.getType(), path.getMetadata(), "public", TEST_DOMAIN_TABLE);
        addMetadatas();
    }

    public QTestDomain(PathMetadata<?> metadata) {
        super(TestDomain.class, metadata, "public", TEST_DOMAIN_TABLE);
        addMetadatas();
    }

    public QTestDomain(String variable, String scheme) {
        super(TestDomain.class, forVariable(variable), scheme, TEST_DOMAIN_TABLE);
        addMetadatas();
    }

    private void addMetadatas() {
        addMetadata(id, ColumnMetadata.named("ID").ofType(Types.INTEGER));
        addMetadata(name, ColumnMetadata.named("NAME").ofType(Types.VARCHAR).withSize(50));
        addMetadata(status, ColumnMetadata.named("STATUS").ofType(Types.TINYINT).withSize(2));
        addRecordingMetadata();
    }
}