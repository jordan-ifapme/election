package be.ifapme.election.utils;

import org.modelmapper.ModelMapper;

public class ModelMapperUtils {

    private static ModelMapper instance;

    private ModelMapperUtils() {}

    public static ModelMapper getInstance() {
        if (instance == null) {
            instance = new ModelMapper();
        }
        return instance;
    }
}
