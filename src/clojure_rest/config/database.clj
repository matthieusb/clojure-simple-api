(ns clojure-rest.config.database
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:require [clojure.java.jdbc :as sql]))

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
