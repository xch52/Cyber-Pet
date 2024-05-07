package com.uobfintech.cyberpets.service;

import io.ipfs.api.IPFS;

import java.io.IOException;

 public interface IpfsService {
    byte[] getFileFromIpfs(String hash, IPFS ipfs) throws IOException;
}
