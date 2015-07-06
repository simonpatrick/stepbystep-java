package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;

import javax.annotation.Generated;
import java.io.Serializable;
import static java.sql.Types.VARCHAR;
import static com.mysema.query.sql.ColumnMetadata.named;

@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QAbstractAuditable<T extends AbstractAuditable<? extends Serializable, T, Long>>
        extends QAbstractRecording<T> {

    public final NumberPath<Long> createdBy = createNumber("createdBy", Long.class);

    public final NumberPath<Long> lastModifiedBy = createNumber("lastModifiedBy", Long.class);

    public QAbstractAuditable(Class<? extends T> type, String variable, String schema, String table) {
        super(type, variable, schema, table);
    }

    public QAbstractAuditable(Class<? extends T> type, PathMetadata<?> metadata, String schema, String table) {
        super(type, metadata, schema, table);
    }

    protected void addAuditableMetadata() {
        addRecordingMetadata();
        addMetadata(createdBy, named("CREATED_BY").withSize(50).ofType(VARCHAR));
        addMetadata(lastModifiedBy, named("LAST_MODIFIED_BY").withSize(50).ofType(VARCHAR));
    }
}
