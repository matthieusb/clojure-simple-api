(ns clojure-rest.controllers.document-controller
  (:use clojure-rest.config.database)
  (:use ring.util.response)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.java.jdbc :as sql]
            [ring.middleware.json :as ring-json]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-all-documents []
  (response
    (sql/with-connection (db-connection)
      (sql/with-query-results results
        ["select * from documents"]
        (into [] results)))))

(defn get-document [id]
  (sql/with-connection (db-connection)
    (sql/with-query-results results
      ["select * from documents where id = ?" id]
      (cond
        (empty? results) {:status 404}
        :else (response (first results))))))

(defn create-new-document [doc]
  (let [id (uuid)]
    (sql/with-connection (db-connection)
      (let [document (assoc doc "id" id)]
        (sql/insert-record :documents document)))
    (get-document id)))

(defn update-document [id doc]
    (sql/with-connection (db-connection)
      (let [document (assoc doc "id" id)]
        (sql/update-values :documents ["id=?" id] document)))
    (get-document id))

(defn delete-document [id]
  (sql/with-connection (db-connection)
    (sql/delete-rows :documents ["id=?" id]))
  {:status 204})
