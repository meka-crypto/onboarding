package com.mdsl.onboarding.common.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommonUtils {

    private static ModelMapper modelMapper;

    @Autowired
    private CommonUtils(ModelMapper modelMapper) {
        CommonUtils.modelMapper = modelMapper;
    }

    public static <S, D> List<D> mapList(List<S> sourceList, Class<D> destinationClass) {
        return sourceList.stream()
                .map(source -> modelMapper.map(source, destinationClass))
                .collect(Collectors.toList());
    }
}
