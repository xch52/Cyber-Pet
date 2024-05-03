package com.uobfintech.nftpictures.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import com.uobfintech.nftpictures.entity.Pet;
import com.uobfintech.nftpictures.result.Result;
import com.uobfintech.nftpictures.service.MetadataService;

@Slf4j
@RestController
@RequestMapping("/api/pets")

public class MetadataController {
    @Autowired
    private MetadataService metadataService;

    @GetMapping()
    public Result getPets(@RequestParam(required = false) String filter,
                          @RequestParam(required = false) String sort,
                          @RequestParam(required = false) Integer limit) {
        List<Pet> pets = metadataService.findAllPets();

        if (filter != null){

        }
        if (sort != null){

        }
        if (limit != null){

        }
        return Result.success(pets);
    }



    @GetMapping("{id}")
    public Result getPetById(@PathVariable Long id) {
        return Result.success(metadataService.findPetById(id));
    }


//    @GetMapping("/test/{id}")
//    public Result test(@PathVariable Long id){
//        return Result.success(metadataService.findById(id));
//    }
}
