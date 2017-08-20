(ns clojure-rest.config.app
  (:require [clojure-rest.utils.config-utils :as confutils]))

(def conf
  (confutils/load-config "./resources/test/application.edn"))
