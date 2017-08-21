(ns clojure-rest.utils.config-utils
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clojure.edn :as edn]))

(defn load-config
  "Given a filename, load & return a config file"
  [filename]
  (log/info (str "Loading configuration file : " filename))
  (edn/read-string (slurp filename)))
