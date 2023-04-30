package br.com.delazeri.library.mapper.config;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DozerMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <OriginClass, DestinationClass> DestinationClass parseObject(OriginClass origin, Class<DestinationClass> destination) {
        return mapper.map(origin, destination);
    }

    public static <OriginClass, DestinationClass> List<DestinationClass> parseListObjects(List<OriginClass> origin, Class<DestinationClass> destination) {
        List<DestinationClass> destinationObjects = new ArrayList<DestinationClass>();

        for(OriginClass originObject : origin) {
            destinationObjects.add(mapper.map(originObject, destination));
        }

        return destinationObjects;
    }
}
