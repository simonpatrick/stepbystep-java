package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import org.joda.time.DateTime;

import javax.annotation.Generated;

import static com.mysema.query.sql.ColumnMetadata.named;
import static com.data.qdomain.Recording.PROP_CREATED_DATE;
import static com.data.qdomain.Recording.PROP_LAST_MODIFIED_DATE;
import static java.sql.Types.TIMESTAMP;

@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QAbstractVersionedRecording<T> extends QAbstractVersioned<T> {

    private static final long serialVersionUID = 1L;

    public final NumberPath<Integer> version = createNumber(VersionedEntity.PROP_VERSION, Integer.class);

    public final DateTimePath<DateTime> createdDate = createDateTime(
            PROP_CREATED_DATE, DateTime.class);

    public final DateTimePath<DateTime> lastModifiedDate = createDateTime(
            PROP_LAST_MODIFIED_DATE, DateTime.class);

    public QAbstractVersionedRecording(Class<? extends T> type, String variable, String schema, String table) {
        super(type, variable, schema, table);
    }

    public QAbstractVersionedRecording(Class<? extends T> type, PathMetadata<?> metadata, String schema, String table) {
        super(type, metadata, schema, table);
    }

    @Override
    protected void addVersionedMetadata() {
        super.addVersionedMetadata();
        addMetadata(lastModifiedDate, named("LAST_MODIFIED_DATE").ofType(TIMESTAMP).notNull());
        addMetadata(createdDate, named("CREATED_DATE").ofType(TIMESTAMP).notNull());
    }
}