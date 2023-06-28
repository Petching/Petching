package com.Petching.petching.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class URICreator {
    public static URI createUri (String defaultUrl, long resourceId) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }
}
