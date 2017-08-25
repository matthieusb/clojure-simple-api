(ns clojure-rest.services.document-service
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clojure-rest.model.document :as document]
            [clojure-rest.config.database :as database]
            [clojure-rest.dao.document-dao :as documentDao]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-all-documents
  "Gets all documents from the database"
  []
  (documentDao/get-all-documents))

(defn get-document-by-id
  "Gets only one document using its id"
  [idDocument]
  (documentDao/get-document-by-id idDocument))

(defn create-new-document
  "Adds a new document to the database, returns the id if ok, null if invalid"
  [documentToCreate]
  (let [validDocument (document/validate-document-map documentToCreate)]
    (if (nil? validDocument) nil
    (let [id (uuid)]
      (log/info (str "create-new-document generated id : " id))
      (let [document (assoc documentToCreate :id_document id)]
        (documentDao/create-new-document document)
        id)))))

(defn update-document
  "Updates an existing document on the database"
  [idDocumentToUpdate documentToUpdate]
  (let [validDocument (document/validate-document-map documentToUpdate)]
    (if (nil? validDocument) nil
      (let [document (assoc documentToUpdate :id_document idDocumentToUpdate)]
        (documentDao/update-document idDocumentToUpdate documentToUpdate)
        idDocumentToUpdate))))

(defn delete-document
  "Deletes an existing document on the database, returns number of deleted rows"
  [idDocumentToDelete]
  (documentDao/delete-document idDocumentToDelete))
