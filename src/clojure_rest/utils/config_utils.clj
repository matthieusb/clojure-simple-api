(ns clojure-rest.utils.config-utils
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.edn :as edn]))

(defn load-config
  "Given a filename, load & return a config file"
  [filename]
  (edn/read-string (slurp filename)))
