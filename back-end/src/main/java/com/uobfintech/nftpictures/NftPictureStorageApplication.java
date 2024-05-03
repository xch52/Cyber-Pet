package com.uobfintech.nftpictures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class NftPictureStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(NftPictureStorageApplication.class, args);
	}

}
