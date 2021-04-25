package cz.muni.fi.pa165.service;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeanMappingServiceImpl implements BeanMappingService {
    @Autowired
    private Mapper dozer;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        return objects.stream()
                .map(o -> mapTo(o, mapToClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> T mapTo(Object obj, Class<T> mapToClass) {
        return dozer.map(obj, mapToClass);
    }

    @Override
    public Mapper getMapper() {
        return dozer;
    }
}
