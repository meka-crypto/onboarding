package com.mdsl.onboarding.common.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// Utility class for common operations
@Component
public class CommonUtils {

    private static ModelMapper modelMapper;

    /**
     * Constructor for injecting the ModelMapper dependency.
     *
     * @param modelMapper the ModelMapper instance to be used by this utility class
     */
    @Autowired
    private CommonUtils(ModelMapper modelMapper) {
        CommonUtils.modelMapper = modelMapper;
    }

    /**
     * Maps a list of objects from one type to another using ModelMapper.
     *
     * @param sourceList the list of source objects to be mapped
     * @param destinationClass the class of the destination type to map to
     * @param <S> the source type
     * @param <D> the destination type
     * @return a list of mapped objects of the destination type
     */
    public static <S, D> List<D> mapList(List<S> sourceList, Class<D> destinationClass) {
        return sourceList.stream()  // Streams through the source list
                .map(source -> modelMapper.map(source, destinationClass)) // Maps each source object to destination type
                .collect(Collectors.toList()); // Collects the mapped objects into a list
    }
}
