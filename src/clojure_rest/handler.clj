(ns clojure-rest.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.java.jdbc :as sql]
            [ring.middleware.json :as ring-json]
            [clojure-rest.config.routes :as config-routes]))


; ------------------- APP CONFIG

(def app
  (-> config-routes/app-routes
      (ring-json/wrap-json-body  {:keywords? true :bigdecimals? true})
      (ring-json/wrap-json-response)))
