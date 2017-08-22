(ns clojure-rest.config.database
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clojure-rest.config.app :as appconf]
            [clojure-rest.utils.database-utils :as dbutils]))

(declare dropAllTables)
(declare createDocumentsTable)
(declare addSomeTestDocuments)

(def db-h2-connection
  (get appconf/conf :h2database))

(defn initDatabase
  "Calls methods to intialize database at application startup and shows them at the end"
  []
  (log/info "Initializing whole database")
  (dropAllTables)
  (createDocumentsTable)
  (addSomeTestDocuments))

(defn dropAllTables
  "Drops all tables before recreating them"
  []
  (log/info "Dropping all tables")
  (dbutils/dropSeveralTables db-h2-connection ["documents" "wrongtable"]))

(defn createDocumentsTable
  "Create documents table in the dabatase"
  []
  (log/info "Creating documents table")
  (dbutils/createTable db-h2-connection :documents [[:id_document "varchar(36)" "PRIMARY KEY"]
                                                    [:title "varchar(32)"]
                                                    [:text "varchar(64)"]]))
(defn addSomeTestDocuments
  "Just adds some dummy documents for test purposes"
  []
  (log/info "Adding some dummy documents")
  (jdbc/insert! db-h2-connection :documents {:id_document "1" :title "titre1" :text "text1"})
  (jdbc/insert! db-h2-connection :documents {:id_document "2" :title "titre2" :text "text2"}))
