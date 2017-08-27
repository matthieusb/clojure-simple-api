(ns clojure-rest.dao.document-dao
  (:use [korma.core :only [select]])
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clojure-rest.model.document :as document-model]
            [clojure-rest.config.database :as database]))

; -----------------
; -- Korma versions
; -----------------

(defn get-all-documents
  "Gets all documents from the database"
  []
  (select document-model/document))

(defn get-document-by-id
  "Gets only one document using its id"
  [id-document]
  (jdbc/query database/db-h2-connection ["select * from documents where id_document = ?" id-document] {}))

(defn create-new-document
  "Adds a new document to the database"
  [document-to-create]
  (jdbc/insert! database/db-h2-connection :documents document-to-create))

(defn update-document
  "Updates an existing document on the database"
  [id-document-to-update document-to-update]
  (jdbc/update! database/db-h2-connection :documents document-to-update ["id_document=?" id-document-to-update]))

(defn delete-document
  "Deletes an existing document on the database, returns number of deleted rows"
  [id-document-to-delete]
  (jdbc/delete! database/db-h2-connection :documents ["id_document=?" id-document-to-delete]))

; -----------------
; -- Jdbc versions
; -----------------
(defn get-all-documents-jdbc
  "Gets all documents from the database"
  []
  (jdbc/query database/db-h2-connection ["select * from documents"]))

(defn get-document-by-id-jdbc
  "Gets only one document using its id"
  [id-document]
  (jdbc/query database/db-h2-connection ["select * from documents where id_document = ?" id-document] {}))

(defn create-new-document-jdbc
  "Adds a new document to the database"
  [document-to-create]
  (jdbc/insert! database/db-h2-connection :documents document-to-create))

(defn update-document-jdbc
  "Updates an existing document on the database"
  [id-document-to-update document-to-update]
  (jdbc/update! database/db-h2-connection :documents document-to-update ["id_document=?" id-document-to-update]))

(defn delete-document-jdbc
  "Deletes an existing document on the database, returns number of deleted rows"
  [id-document-to-delete]
  (jdbc/delete! database/db-h2-connection :documents ["id_document=?" id-document-to-delete]))
