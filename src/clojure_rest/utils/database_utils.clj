(ns clojure-rest.utils.database-utils
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]))

(declare drop-table)

(defn drop-several-tables
  "Drop several tables from a vector"
  [connection tables]
  (log/info (str "Beginning drop process on several tables : " tables))
  (run! (partial drop-table connection) tables))

(defn drop-table
  "Drop a table"
  [connection table-name]
  (log/info (str "Dropping table : " table-name))
  (try (jdbc/db-do-commands connection
                       [(jdbc/drop-table-ddl table-name)])
    (catch Exception e (log/warn (str "Caught exception : " (.getMessage e))))))

(defn create-table
  "Create a table"
  [connection table-name table-rows-seq]
  (log/info (str "Creating table : " table-name))
  (jdbc/db-do-commands connection
      [(jdbc/create-table-ddl table-name table-rows-seq)]))
