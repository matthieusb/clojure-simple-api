(ns clojure-rest.dao.document-dao
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clojure-rest.model.document :as document]
            [clojure-rest.config.database :as database]))

(defn getAllDocuments
  "Gets all documents from the database"
  []
  (jdbc/query database/db-h2-connection ["select * from documents"]))

(defn getDocumentBydId
  "Gets only one document using its id"
  [idDocument]
  (jdbc/query database/db-h2-connection ["select * from documents where id_document = ?" idDocument] {}))

(defn createNewDocument
  "Adds a new document to the database"
  [documentToCreate]
  (jdbc/insert! database/db-h2-connection :documents documentToCreate))

(defn updateDocument
  "Updates an existing document on the database"
  [idDocumentToUpdate documentToUpdate]
  (jdbc/update! database/db-h2-connection :documents documentToUpdate ["id_document=?" idDocumentToUpdate]))

(defn deleteDocument
  "Deletes an existing document on the database, returns number of deleted rows"
  [idDocumentToDelete]
  (jdbc/delete! database/db-h2-connection :documents ["id_document=?" idDocumentToDelete]))
