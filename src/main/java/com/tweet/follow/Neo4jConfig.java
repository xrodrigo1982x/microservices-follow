package com.tweet.follow;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.TypeRepresentationStrategy;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.typerepresentation.NoopRelationshipTypeRepresentationStrategy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories
@EnableTransactionManagement
public class Neo4jConfig extends Neo4jConfiguration{

    public Neo4jConfig() {
        setBasePackage(this.getClass().getPackage().getName());
    }

    @Bean
    public GraphDatabaseService graphDatabaseService(@Value("${neo4j.url:http://localhost:7474/db/data}") String url,
                                                     @Value("${neo4j.user:neo4j}") String user,
                                                     @Value("${neo4j.password:neo4jp}") String password) {
        return new SpringRestGraphDatabase(url, user, password);
    }

    @Bean
    public Neo4jTemplate neo4jTemplate(GraphDatabaseService databaseService){
        return new Neo4jTemplate(databaseService);
    }

    @Override
    public TypeRepresentationStrategy<Relationship> relationshipTypeRepresentationStrategy() throws Exception {
        return new NoopRelationshipTypeRepresentationStrategy();
    }

}
