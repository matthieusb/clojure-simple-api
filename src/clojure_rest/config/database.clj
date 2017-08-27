(ns clojure-rest.config.database
  (:use [korma.db :only [defdb h2]])
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [ragtime.jdbc :as ragtime-jdbc]
            [ragtime.repl :as ragtime-repl]
            [clojure-rest.config.app :as appconf]
            [clojure-rest.utils.database-utils :as dbutils]))

(defdb db-h2-korma (h2 (get (appconf/conf) :h2databasekorma)))
(def db-h2-connection (get (appconf/conf) :h2database))

(defn load-ragtime-config []
  {:datastore  (ragtime-jdbc/sql-database (get (appconf/conf) :ragtime-h2-url))
   :migrations (ragtime-jdbc/load-directory (io/resource "migrations"))})

(defn ragtime-migrate
  "Uses ragtime to apply migration scripts"
  []
  (log/info "Applying ragtime migrations")
  (let [ragtime-config (load-ragtime-config)]
    (log/info (str "Used configuration for ragtime : " ragtime-config))
  (ragtime-repl/migrate ragtime-config)))

(defn ragtime-rollback
  "Uses ragtime to apply migration rollback scripts"
  []
  (log/info "Rollbacking ragtime migrations")
  (ragtime-repl/rollback (load-ragtime-config)))

(defn reinit-database
  "Clears database and recreates it using ragtime"
  []
  (ragtime-rollback)
  (ragtime-migrate))
