package com.uobfintech.nftpictures.service;

import java.io.IOException;

public interface IpfsService {
    byte[] getFileFromIpfs(String hash) throws IOException;
}
