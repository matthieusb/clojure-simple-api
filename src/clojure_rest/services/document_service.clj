(ns clojure-rest.services.document-service
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clojure-rest.model.document :as document]
            [clojure-rest.config.database :as database]
            [clojure-rest.dao.document-dao :as documentDao]))

(defn getAllDocuments
  "Gets all documents from the database"
  []
  (documentDao/getAllDocuments))

(defn getDocumentBydId
  "Gets only one document using its id"
  [idDocument]
  (documentDao/getDocumentBydId idDocument))

(defn createNewDocument
  "Adds a new document to the database"
  [documentToCreate]
  (documentDao/createNewDocument documentToCreate))

(defn updateDocument
  "Updates an existing document on the database"
  [idDocumentToUpdate documentToUpdate]
  (documentDao/updateDocument idDocumentToUpdate documentToUpdate))

(defn deleteDocument
  "Deletes an existing document on the database, returns number of deleted rows"
  [idDocumentToDelete]
  (documentDao/deleteDocument idDocumentToDelete))
