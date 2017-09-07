(ns clojure-rest.controllers.document-controller
  (:use [ring.util.response :only [response]])
  (:require [compojure.core :refer :all]
            [ring.util.http-response :refer :all]
            [compojure.route :as route]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [ring.middleware.json :as ring-json]
            [clojure-rest.services.document-service :as document-service]
            [clojure-rest.model.document :as document]
            [clojure-rest.config.database :as database]))

(defn get-all-documents []
  (log/info "get-all-documents")
  (response
      (document-service/get-all-documents)))

(defn get-document [id]
  (log/info (str "get-document with id : " id))
  (let [results (document-service/get-document-by-id id)]
    (cond (empty? results) {:status 404}
      :else (ok (first results)))))

(defn create-new-document [doc]
  (log/info (str "create-new-document : " doc))
  (let [id-document-created (document-service/create-new-document doc)]
    (if (nil? id-document-created) {:status 400}
       (created (str "/documents/" id-document-created) (first (document-service/get-document-by-id id-document-created))))))

(defn update-document [id doc]
  (log/info (str "update-document with id : " id ". New values : " doc))
  (let [id-document-updated (document-service/update-document id doc)]
    (if (nil? id-document-updated) {:status 400}
      (get-document id-document-updated))))

(defn delete-document [id]
  (log/info (str "delete-document with id : " id))
  (let [deleted-document-id (document-service/delete-document id)]
    (log/info (str "id of document deleted : " deleted-document-id))
    (if (= deleted-document-id 0) {:status 404} {:status 204})))
