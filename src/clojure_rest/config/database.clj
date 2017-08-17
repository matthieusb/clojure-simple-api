(ns clojure-rest.config.database
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clojure-rest.utils.database-utils :as dbutils]))

(def db-h2-connection
      {:dbtype "h2"
       :dbname "./db/prod/clojure_rest_h2"
       :user "sa"
       :password ""})

(def db-h2-connection-test
     {:dbtype "h2"
      :dbname "./db/prod/clojure_rest_h2"
      :user "sa"
      :password ""})

(defn dropAllTables
  "Drops all tables before recreating them"
  []
  (log/info "Dropping all tables")
  (dbutils/dropSeveralTables db-h2-connection ["documents" "wrongtable"]))

; (defn dropAllTables
;   "Drops all tables before recreating them"
;   []
;   (log/info "Dropping all tables")
;   (dbutils/dropTable db-h2-connection "documents"))

(defn createDocumentsTable
  "Create documents table in the dabatase"
  []
  (log/info "Creating documents table")
  (dbutils/createTable db-h2-connection :documents [[:id_document "varchar(36)" "PRIMARY KEY"]
                                                    [:title "varchar(32)"]
                                                    [:text "varchar(64)"]]))
(defn initDatabase
  "Calls methods to intialize database at application startup and shows them at the end"
  []
  (log/info "Initializing whole database")
  (dropAllTables)
  (createDocumentsTable))
