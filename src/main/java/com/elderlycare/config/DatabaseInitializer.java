package com.elderlycare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        try {
            checkAndInitializeDatabase();
        } catch (Exception e) {
            System.err.println("数据库初始化失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void checkAndInitializeDatabase() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            String databaseName = connection.getCatalog();
            
            if (!"elderlycare".equals(databaseName)) {
                System.out.println("当前数据库不是elderlycare，跳过初始化");
                return;
            }

            Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
            if (userCount != null && userCount > 0) {
                System.out.println("数据库已初始化，跳过SQL脚本执行");
                return;
            }

            System.out.println("开始执行数据库初始化脚本...");
            executeSqlScript();
            System.out.println("数据库初始化完成！");
        }
    }

    private void executeSqlScript() throws Exception {
        Resource resource = new ClassPathResource("init_database.sql");
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            boolean inDelimiter = false;
            String delimiter = ";";

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }

                if (line.startsWith("DELIMITER")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length > 1) {
                        delimiter = parts[1];
                        inDelimiter = true;
                    }
                    continue;
                }

                sqlBuilder.append(line).append(" ");

                if (line.endsWith(delimiter)) {
                    String sql = sqlBuilder.toString().trim();
                    if (sql.endsWith(delimiter)) {
                        sql = sql.substring(0, sql.length() - delimiter.length()).trim();
                    }

                    if (!sql.isEmpty()) {
                        try (Connection connection = dataSource.getConnection();
                             Statement statement = connection.createStatement()) {
                            statement.execute(sql);
                        }
                    }

                    sqlBuilder.setLength(0);
                }
            }
        }
    }
}
