package com.api.imageinterpretor.service.interfaces;

import java.io.InputStream;
import java.util.List;

public interface RetriveService {
    List<Long> getAllByAUser();

    InputStream getImage(Long id);
}
