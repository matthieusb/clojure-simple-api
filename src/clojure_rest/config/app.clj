(ns clojure-rest.config.app
  (:require [clojure.java.io :as io]
            [nomad :refer [defconfig]]))

(defconfig conf (io/resource "application.edn"))
