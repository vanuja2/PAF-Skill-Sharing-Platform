package com.skillshare.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.skillshare.model.MediaItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaService {
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;
    
    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final Map<String, String[]> ALLOWED_TYPES = new HashMap<>() {{
        put("image", new String[]{"image/jpeg", "image/png", "image/gif"});
        put("video", new String[]{"video/mp4", "video/quicktime"});
    }};
    
    