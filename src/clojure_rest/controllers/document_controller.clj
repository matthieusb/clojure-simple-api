(ns clojure-rest.controllers.document-controller
  ; (:use clojure-rest.config.database)
  (:use ring.util.response)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [ring.middleware.json :as ring-json]
            [clojure-rest.services.document-service :as documentService]
            [clojure-rest.model.document :as document]
            [clojure-rest.config.database :as database]))

(defn get-all-documents []
  (log/info "get-all-documents")
  (response
      (documentService/get-all-documents)))

(defn get-document [id]
  (log/info (str "get-document with id : " id))
  (def results (documentService/get-document-by-id id))
    (cond (empty? results) {:status 404}
      :else (response (first results))))

(defn create-new-document [doc]
  (log/info (str "create-new-document : " doc))
  (let [idDocumentCreated (documentService/create-new-document doc)]
    (if (nil? idDocumentCreated) {:status 400}
      (get-document idDocumentCreated))))

(defn update-document [id doc]
  (log/info (str "update-document with id : " id ". New values : " doc))
  (let [idDocumentUpdated (documentService/update-document id doc)]
    (if (nil? idDocumentUpdated) {:status 400}
      (get-document idDocumentUpdated))))

(defn delete-document [id]
  (log/info (str "delete-document with id : " id))
  (let [numberOfRowsDeletedArray (documentService/delete-document id)]
    (log/info (str "number of rows deleted : " numberOfRowsDeletedArray))
    (if (> (first numberOfRowsDeletedArray) 0) {:status 204} {:status 404})))
