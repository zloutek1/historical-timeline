package cz.muni.fi.pa165.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

public interface BeanMappingService {
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);
    <T> T mapTo(Object obj, Class<T> mapToClass);
    Mapper getMapper();
}
