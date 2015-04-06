package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;

import javax.annotation.Generated;
import static java.sql.Types.INTEGER;

import static com.mysema.query.sql.ColumnMetadata.named;

@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QAbstractVersioned<T> extends RelationalPathBase<T> {

    private static final long serialVersionUID = 1L;

    public final NumberPath<Integer> version = createNumber(VersionedEntity.PROP_VERSION, Integer.class);

    public QAbstractVersioned(Class<? extends T> type, String variable, String schema, String table) {
        super(type, variable, schema, table);
    }

    public QAbstractVersioned(Class<? extends T> type, PathMetadata<?> metadata, String schema, String table) {
        super(type, metadata, schema, table);
    }

    protected void addVersionedMetadata() {
        addMetadata(version, named("VERSION").ofType(INTEGER).notNull());
    }
}
