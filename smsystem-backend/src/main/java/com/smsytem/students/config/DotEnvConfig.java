package com.smsytem.students.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Configuration class to load environment variables from .env file
 * This ensures environment variables are available before Spring starts
 */
@Configuration
public class DotEnvConfig {

    @PostConstruct
    public void loadEnvFile() {
        try {
            // Look for .env file in project root (parent of smsystem-backend)
            File projectRoot = new File(System.getProperty("user.dir")).getParentFile();
            File envFile = new File(projectRoot, ".env");
            
            if (envFile.exists()) {
                Dotenv dotenv = Dotenv.configure()
                    .directory(projectRoot.getAbsolutePath())
                    .filename(".env")
                    .load();
                
                // Set system properties so Spring can access them
                dotenv.entries().forEach(entry -> {
                    if (System.getProperty(entry.getKey()) == null) {
                        System.setProperty(entry.getKey(), entry.getValue());
                    }
                });
                
                System.out.println("✅ Loaded .env file from: " + envFile.getAbsolutePath());
            } else {
                // Try current directory as fallback
                Dotenv dotenv = Dotenv.configure()
                    .directory(System.getProperty("user.dir"))
                    .filename(".env")
                    .ignoreIfMissing()
                    .load();
                
                dotenv.entries().forEach(entry -> {
                    if (System.getProperty(entry.getKey()) == null) {
                        System.setProperty(entry.getKey(), entry.getValue());
                    }
                });
                
                System.out.println("⚠️  .env file not found in project root, using system environment variables");
            }
        } catch (Exception e) {
            System.err.println("❌ Error loading .env file: " + e.getMessage());
            System.err.println("ℹ️  Application will continue with system environment variables");
        }
    }
}
