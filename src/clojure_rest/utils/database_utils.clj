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
  [connection tableName]
  (log/info (str "Dropping table : " tableName))
  (try (jdbc/db-do-commands connection
                       [(jdbc/drop-table-ddl tableName)])
    (catch Exception e (log/warn (str "Caught exception : " (.getMessage e))))))

(defn create-table
  "Create a table"
  [connection tableName tableRowsSeq]
  (log/info (str "Creating table : " tableName))
  (jdbc/db-do-commands connection
      [(jdbc/create-table-ddl tableName tableRowsSeq)]))
