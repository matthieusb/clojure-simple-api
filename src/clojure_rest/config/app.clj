(ns clojure-rest.config.app
  (:require [clojure.java.io :as io]
            [clojure-rest.utils.config-utils :as confutils]))

(def conf
  (confutils/load-config (io/resource "application.edn")))
