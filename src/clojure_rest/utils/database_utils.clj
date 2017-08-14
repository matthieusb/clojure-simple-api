(ns clojure-rest.utils.database-utils
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]))

(defn dropTable
  "Drop a table according to its name"
  [connection tableName]
  (log/info (str "Dropping table : " tableName))
  (try (jdbc/db-do-commands connection
                       [(jdbc/drop-table-ddl tableName)])
    (catch Exception e (log/warn (str "Caught exception : " (.getMessage e))))))
