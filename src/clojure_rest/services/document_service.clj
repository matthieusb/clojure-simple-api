(ns clojure-rest.services.document-service
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clojure-rest.model.document :as document]
            [clojure-rest.config.database :as database]
            [clojure-rest.dao.document-dao :as document-dao]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-all-documents
  "Gets all documents from the database"
  []
  (document-dao/get-all-documents))

(defn get-document-by-id
  "Gets only one document using its id"
  [id-document]
  (document-dao/get-document-by-id id-document))

(defn create-new-document
  "Adds a new document to the database, returns the id if ok, null if invalid"
  [document-to-create]
    (let [id (uuid)]
      (let [document (assoc document-to-create :id_document id)]
        (document-dao/create-new-document document)
        id)))

(defn update-document
  "Updates an existing document on the database"
  [id-document-to-update document-to-update]
    (let [document (assoc document-to-update :id_document id-document-to-update)]
      (document-dao/update-document id-document-to-update document-to-update)
      id-document-to-update))

(defn delete-document
  "Deletes an existing document on the database, returns id of deleted document"
  [id-document-to-delete]
  (document-dao/delete-document id-document-to-delete))
