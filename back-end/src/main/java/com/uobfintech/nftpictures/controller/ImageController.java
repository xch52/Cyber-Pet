package com.uobfintech.nftpictures.controller;

import io.ipfs.api.IPFS;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uobfintech.nftpictures.service.IpfsService;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final IpfsService ipfsService;

    public ImageController(IpfsService ipfsService) {
        this.ipfsService = ipfsService;
    }

    @GetMapping(value = "/{hash}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImageFromIpfs(@PathVariable String hash) {
        try {
            IPFS ipfs = new IPFS("https://ipfs.io/ipfs/"+hash);
            byte[] image = ipfsService.getFileFromIpfs(hash, ipfs);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("exception.....");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

