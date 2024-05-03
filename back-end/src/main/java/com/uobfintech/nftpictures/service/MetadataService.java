package com.uobfintech.nftpictures.service;

import java.util.List;

import org.bson.Document;

import com.uobfintech.nftpictures.entity.Pet;

public interface MetadataService {

    Pet findPetById(Long id);


    List<Pet> findAllPets();

    //Pet findById(Long id);
}
