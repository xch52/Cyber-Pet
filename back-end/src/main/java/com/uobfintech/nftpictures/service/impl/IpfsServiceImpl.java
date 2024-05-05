package com.uobfintech.nftpictures.service.impl;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;

import org.springframework.stereotype.Service;

import java.io.IOException;

import com.uobfintech.nftpictures.service.IpfsService;

@Service
public class IpfsServiceImpl implements IpfsService {
    // private final IPFS ipfs;

    public IpfsServiceImpl() {
        // this.ipfs = new IPFS("https://ipfs.io/ipfs/");
    }

    public byte[] getFileFromIpfs(String hash, IPFS ipfs) throws IOException {
        Multihash filePointer = Multihash.fromBase58(hash);
        return ipfs.cat(filePointer);
    }
}

