/*
 * Copyright (c) 2008 - 2012 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.acceptancetest.crud;

import org.bson.types.Document;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.MongoClient;
import org.mongodb.MongoCollection;
import org.mongodb.MongoCursor;
import org.mongodb.MongoDatabase;
import org.mongodb.QueryFilterDocument;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mongodb.acceptancetest.Fixture.getMongoClient;

public class InsertMongoDocumentAcceptanceTest {
    private static final String DB_NAME = "InsertMongoDocumentAcceptanceTest";
    private MongoCollection<Document> collection;

    @Before
    public void setUp() {
        final MongoClient mongoClient = getMongoClient();

        final MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        database.admin().drop();

        collection = database.getCollection("collection");
    }

    @Test
    public void shouldInsertSimpleUntypedDocument() {
        final Document simpleDocument = new Document("name", "Billy");
        collection.insert(simpleDocument);

        assertThat(collection.count(), is(1L));

        final QueryFilterDocument queryFilter = new QueryFilterDocument("name", "Billy");
        final MongoCursor<Document> insertTestDocumentMongoCursor = collection.filter(queryFilter).all();

        assertThat((String) insertTestDocumentMongoCursor.next().get("name"), is("Billy"));
    }

}
