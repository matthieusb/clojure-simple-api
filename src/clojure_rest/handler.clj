(ns clojure-rest.handler
  (:use cheshire.core)
  (:use ring.util.response)
  (:use clojure-rest.config.routes)
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.java.jdbc :as sql]
            [ring.middleware.json :as ring-json]))


; ------------------- APP CONFIG

(def app
  (-> (handler/api app-routes)
      (ring-json/wrap-json-body)
      (ring-json/wrap-json-response)))
