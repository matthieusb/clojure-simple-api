(ns clojure-rest.controllers.document-controller
  ; (:use clojure-rest.config.database)
  (:use ring.util.response)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [ring.middleware.json :as ring-json]
            [clojure-rest.config.database :as database]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-all-documents []
  (log/info "get-all-documents")
  (response
      (jdbc/query database/db-h2-connection ["select * from documents"])))

(defn get-document [id]
  (log/info (str "get-document with id : " id))
  (def results (jdbc/query database/db-h2-connection ["select * from documents where id_document = ?" id] {}))
    (cond (empty? results) {:status 404}
      :else (response (first results))))

(defn create-new-document [doc]
  (log/info (str "create-new-document : " doc))
  (let [id (uuid)]
    (log/info (str "create-new-document generated id : " id))
    (let [document (assoc doc "id_document" id)]
      (jdbc/insert! database/db-h2-connection :documents document))
      (get-document id)))

(defn update-document [id doc]
  (log/info (str "update-document with id : " id ". New values : " doc))
      (let [document (assoc doc "id_document" id)]
        (jdbc/update! database/db-h2-connection :documents document ["id_document=?" id]))
    (get-document id))

(defn delete-document [id]
  (log/info (str "delete-document with id : " id))
    (jdbc/delete! database/db-h2-connection :documents ["id_document=?" id])
  {:status 204})
