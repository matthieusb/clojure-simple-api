(ns clojure-rest.handler
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:use cheshire.core)
  (:use ring.util.response)
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.java.jdbc :as sql]
            [ring.middleware.json :as ring-json]))

; ------------------- DATABASE CONFIG

(def db-config
      {:classname "org.h2.Driver"
       :subprotocol "h2"
       :subname "mem:documents"
       :user ""
       :password ""})

(defn pool
     [config]
     (let [cpds (doto (ComboPooledDataSource.)
                  (.setDriverClass (:classname config))
                  (.setJdbcUrl (str "jdbc:" (:subprotocol config) ":" (:subname config)))
                  (.setUser (:user config))
                  (.setPassword (:password config))
                  (.setMaxPoolSize 6)
                  (.setMinPoolSize 1)
                  (.setInitialPoolSize 1))]
       {:datasource cpds}))

(def pooled-db (delay (pool db-config)))

(defn db-connection [] @pooled-db)

(sql/with-connection (db-connection)
      (sql/create-table :documents [:id "varchar(256)" "primary key"]
                                   [:title "varchar(1024)"]
                                   [:text :varchar]))


; ------------------- REST METHODS

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

; ------------------- ROUTES

(defroutes app-routes
      (context "/documents" [] (defroutes documents-routes)
        (GET  "/" [] (get-all-documents))
        (POST "/" {body :body} (create-new-document body))
        (context "/:id" [id] (defroutes document-routes)
          (GET    "/" [] (get-document id))
          (PUT    "/" {body :body} (update-document id body))
          (DELETE "/" [] (delete-document id))))
      (route/not-found "Not Found"))

; ------------------- APP CONFIG

(def app
  (-> (handler/api app-routes)
      (ring-json/wrap-json-body)
      (ring-json/wrap-json-response)))
