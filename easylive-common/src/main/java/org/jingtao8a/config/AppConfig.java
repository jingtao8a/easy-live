package org.jingtao8a.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {
    @Value("${project.folder}")
    private String projectFolder;

    @Value("${admin.account}")
    private String adminAccount;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${showFFmpegLog}")
    private Boolean showFFmpegLog;

    @Value("${es.host.port}")
    private String esHostPort;

    @Value("${es.index.video.name}")
    private String esIndexVideoName;
}
