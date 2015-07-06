package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import org.joda.time.DateTime;

import javax.annotation.Generated;

import static com.mysema.query.sql.ColumnMetadata.named;
import static java.sql.Types.TIMESTAMP;

@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QAbstractRecording<T> extends RelationalPathBase<T> {

    private static final long serialVersionUID = 7582941692523143830L;

    public final DateTimePath<DateTime> createdDate = createDateTime(
            Recording.PROP_CREATED_DATE, DateTime.class);

    public final DateTimePath<DateTime> lastModifiedDate = createDateTime(
            Recording.PROP_LAST_MODIFIED_DATE, DateTime.class);

    public QAbstractRecording(Class<? extends T> type, String variable, String schema, String table) {
        super(type, variable, schema, table);
    }

    public QAbstractRecording(Class<? extends T> type, PathMetadata<?> metadata, String schema, String table) {
        super(type, metadata, schema, table);
    }

    protected void addRecordingMetadata() {
        addMetadata(lastModifiedDate, named("LAST_MODIFIED_DATE").ofType(TIMESTAMP).notNull());
        addMetadata(createdDate, named("CREATED_DATE").ofType(TIMESTAMP).notNull());
    }
}