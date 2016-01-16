package io.hedwig.datasamples.mapper;

import io.hedwig.datasamples.models.PersonDest;
import io.hedwig.datasamples.models.PersonSource;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * Created by patrick on 16/1/16.
 */
public class DefaultMapper {

    public static void main(String[] args) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(PersonSource.class, PersonDest.class)
                .field("firstName", "givenName")
                .field("lastName", "sirName")
                .byDefault()
                .register();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        PersonSource source = new PersonSource();
        PersonDest destination = mapper.map(source, PersonDest.class);
        System.out.println(destination);

        BoundMapperFacade<PersonSource, PersonDest> boundMapper =
                mapperFactory.getMapperFacade(PersonSource.class, PersonDest.class);
         destination = boundMapper.map(source);
        System.out.println(destination);
        source=boundMapper.mapReverse(destination);
        System.out.println(source);

    }
}
