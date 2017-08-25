(ns clojure-rest.controllers.document-controller
  ; (:use clojure-rest.config.database)
  (:require [compojure.core :refer :all]
            [ring.util.response :as ring-response]
            [compojure.route :as route]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [ring.middleware.json :as ring-json]
            [clojure-rest.services.document-service :as document-service]
            [clojure-rest.model.document :as document]
            [clojure-rest.config.database :as database]))

(defn get-all-documents []
  (log/info "get-all-documents")
  (ring-response/response
      (document-service/get-all-documents)))

(defn get-document [id]
  (log/info (str "get-document with id : " id))
  (let [results (document-service/get-document-by-id id)]
    (cond (empty? results) {:status 404}
      :else (ring-response/response (first results)))))

(defn create-new-document [doc]
  (log/info (str "create-new-document : " doc))
  (let [id-document-created (document-service/create-new-document doc)]
    (if (nil? id-document-created) {:status 400}
      (get-document id-document-created))))

(defn update-document [id doc]
  (log/info (str "update-document with id : " id ". New values : " doc))
  (let [id-document-updated (document-service/update-document id doc)]
    (if (nil? id-document-updated) {:status 400}
      (get-document id-document-updated))))

(defn delete-document [id]
  (log/info (str "delete-document with id : " id))
  (let [number-of-rows-deleted-array (document-service/delete-document id)]
    (log/info (str "number of rows deleted : " number-of-rows-deleted-array))
    (if (> (first number-of-rows-deleted-array) 0) {:status 204} {:status 404})))
