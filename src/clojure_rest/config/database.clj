(ns clojure-rest.config.database
  (:use [korma.db :only [defdb h2]])
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [korma.db :as korma-db]
            [clojure-rest.config.app :as appconf]
            [clojure-rest.utils.database-utils :as dbutils]))

(declare drop-all-tables)
(declare create-documents-table)
(declare add-some-test-documents)

; -- Korma connection configuration
(defdb db-h2-korma (h2 (get (appconf/conf) :h2databasekorma)))

; -- Jdbc connection configuration

(def db-h2-connection
  (get (appconf/conf) :h2database))

(defn init-database
  "Calls methods to intialize database at application startup and shows them at the end"
  []
  (log/info "Initializing whole database")
  (drop-all-tables)
  (create-documents-table)
  (add-some-test-documents))

(defn drop-all-tables
  "Drops all tables before recreating them"
  []
  (log/info "Dropping all tables")
  (dbutils/drop-several-tables db-h2-connection ["documents" "wrongtable"]))

(defn create-documents-table
  "Create documents table in the dabatase"
  []
  (log/info "Creating documents table")
  (dbutils/create-table db-h2-connection :documents [[:id_document "varchar(36)" "PRIMARY KEY"]
                                                    [:title "varchar(32)"]
                                                    [:text "varchar(64)"]]))
(defn add-some-test-documents
  "Just adds some dummy documents for test purposes"
  []
  (log/info "Adding some dummy documents")
  (jdbc/insert! db-h2-connection :documents {:id_document "1" :title "titre1" :text "text1"})
  (jdbc/insert! db-h2-connection :documents {:id_document "2" :title "titre2" :text "text2"}))
