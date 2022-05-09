package com.example.geologicalclassesservice.util;

public interface UrlMappings {

    String SECTIONS_MAPPING_URL = "/api/v1/sections";
    String GEOLOGICAL_MAPPING_URL = "/api/v1/geologicalClasses";
    String EXCEL_PARSER_MAPPING_URL = "/api/v1/excel";
    String ONLY_SLASH = "/";
    String BY_ID = "/{id}";
    String GET_SECTIONS_BY_CODE = "/by-code/{code}";
    String POST_IMPORT = "/import";
    String GET_IMPORT = "/import/{id}";
    String GET_EXPORT = "/export";
    String GET_EXPORT_ID = "/export/{id}";
    String GET_EXPORT_FILE = "/export/{id}/file";
}
