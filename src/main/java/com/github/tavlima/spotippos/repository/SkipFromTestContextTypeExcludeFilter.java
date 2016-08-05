package com.github.tavlima.spotippos.repository;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;

/**
 * Created by thiago on 8/5/16.
 */
public class SkipFromTestContextTypeExcludeFilter extends TypeExcludeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        if (isSkipFromTestContext(metadataReader)) {
            return true;
        }

        String enclosing = metadataReader.getClassMetadata().getEnclosingClassName();

        if (enclosing != null) {
            try {
                if (match(metadataReaderFactory.getMetadataReader(enclosing), metadataReaderFactory)) {
                    return true;
                }
            } catch (Exception ex) {
                // Ignore
            }
        }

        return false;
    }

    private boolean isSkipFromTestContext(MetadataReader metadataReader) {
        return (metadataReader.getAnnotationMetadata()
                .isAnnotated(SkipFromTestContext.class.getName()));
    }

}
