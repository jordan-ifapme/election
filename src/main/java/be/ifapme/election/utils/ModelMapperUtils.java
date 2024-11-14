package be.ifapme.election.utils;

import org.modelmapper.ModelMapper;

public abstract class ModelMapperUtils {

    private static ModelMapper instance;

    public static ModelMapper getInstance() {
        if (instance == null) {
            instance = new ModelMapper();
        }
        return instance;
    }
}
