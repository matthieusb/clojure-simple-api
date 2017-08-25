(ns clojure-rest.config.init
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clojure-rest.config.database :as database]
            [clojure-rest.utils.database-utils :as dbutils]))

(defn init-app
  "Launches several initialization operations"
  []
  (database/init-database))
