(ns clojure-rest.services.document-service
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clojure-rest.model.document :as document]
            [clojure-rest.config.database :as database]
            [clojure-rest.dao.document-dao :as documentDao]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn getAllDocuments
  "Gets all documents from the database"
  []
  (documentDao/getAllDocuments))

(defn getDocumentBydId
  "Gets only one document using its id"
  [idDocument]
  (documentDao/getDocumentBydId idDocument))

(defn createNewDocument
  "Adds a new document to the database, returns the id if ok, null if invalid"
  [documentToCreate]
  (let [validDocument (document/validate-document-map documentToCreate)]
    (if (nil? validDocument) nil
    (let [id (uuid)]
      (log/info (str "create-new-document generated id : " id))
      (let [document (assoc documentToCreate :id_document id)]
        (documentDao/createNewDocument document)
        id)))))

(defn updateDocument
  "Updates an existing document on the database"
  [idDocumentToUpdate documentToUpdate]
  (let [validDocument (document/validate-document-map documentToUpdate)]
    (if (nil? validDocument) nil
      (let [document (assoc documentToUpdate :id_document idDocumentToUpdate)]
        (documentDao/updateDocument idDocumentToUpdate documentToUpdate)
        idDocumentToUpdate))))

(defn deleteDocument
  "Deletes an existing document on the database, returns number of deleted rows"
  [idDocumentToDelete]
  (documentDao/deleteDocument idDocumentToDelete))
